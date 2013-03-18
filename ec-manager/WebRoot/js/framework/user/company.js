Ext.Loader.setConfig({
			enabled : true
		});

Ext.Loader.setPath('Ext.ux', 'js/ext/ux/');
Ext.require(['Ext.grid.*', 'Ext.data.*', 'Ext.util.*', 'Ext.toolbar.Paging',
		'Ext.ux.PreviewPlugin', 'Ext.ModelManager', 'Ext.tip.QuickTipManager']);
Ext.onReady(function() {
	Ext.tip.QuickTipManager.init();
	var pageSize = 10;
	// 查询条件panel
	Ext.regModel('province_model', {
				fields : [{
							type : 'string',
							name : 'provinceName'
						}, {
							type : 'string',
							name : 'id'
						}]
			});
	var provincestore = Ext.create('Ext.data.Store', {
				model : 'province_model',
				remoteSort : true,
				proxy : {
					type : 'ajax',
					url : 'provinceList'
				}
			});
	provincestore.load();
	Ext.regModel('unit_model', {
				fields : [{
							type : 'string',
							name : 'unitName'
						}, {
							type : 'string',
							name : 'unitNo'
						}]
			});
	var catestore = Ext.create('Ext.data.Store', {
				model : 'unit_model',
				remoteSort : true,
				proxy : {
					type : 'ajax',
					url : 'companyCategoryList'
				}
			});
	catestore.load();
	var searchPanel = Ext.create('Ext.form.Panel', {
				id : 'search-condition-panel',
				title : '查询条件',
				region : 'north',
				frame : true, // 设置窗体为圆角
				method : "POST",
				bodyPadding : 10,
				layout : {
					type : 'table',
					columns : '3',
					align : 'center'
				},
				defaultType : 'textfield',
				fieldDefaults : {
					msgTarget : 'side',
					labelAlign : 'left'
				},
				items : [{
							fieldLabel : "公司编号", // 标签内容
							name : "CompanyNo",
							id : "s-company-no",
							margin : "5 5 5 10"
						}, {
							fieldLabel : "公司名称", // 标签内容
							name : "CompanyName",
							id : "s-company-name",
							margin : "5 5 5 10"
						}, {
							fieldLabel : "公司类型", // 标签内容
							name : "CompanyCategoryNo",
							id : "s-company-category-no",
							margin : "5 5 5 10"
						}, {
							fieldLabel : "省份", // 标签内容
							name : "ProvinceNo",
							id : "s-province-no",
							margin : "5 5 5 10"
						}],
				buttons : [{
							text : "查询",
							handler : searchHandler
						}, {
							text : "清空",
							handler : function() {
								searchPanel.getForm().reset();
							}
						}],
				renderTo : 'search-panel'
			});
	// 查询结果grid

	var gridHeight = getWindownHeight()-searchPanel.getHeight( );
	Ext.define('SearchModel', {
				extend : 'Ext.data.Model',
				fields : ['companyId', 'companyNo', 'companyName', 'companyCategoryNo','rcnNo', 'provinceNo', 'companyAddress', 'companyCity', 'companyContact', 'companyTel', 'status', 'companyZip'],
				idProperty : 'companyId'
			});

	// create the Data Store
	var store = Ext.create('Ext.data.Store', {
				model : 'SearchModel',
				remoteSort : true,
				pageSize : pageSize,
				proxy : new Ext.data.HttpProxy({
							// load using script tags for cross domain,
							// if the
							// data in on the same domain as
							// this page, an HttpProxy would be better
							type : 'jsonp',
							url : 'searchCompany',
							reader : {
								root : 'resultList',
								totalProperty : 'rowCount',
								fields : ['companyId', 'companyNo', 'companyName', 'companyCategoryNo','rcnNo', 'provinceNo', 'companyAddress', 'companyCity', 'companyContact', 'companyTel', 'status', 'companyZip']
							},
							// sends single sort as multi parameter
							simpleSortMode : true
						}),
				sorters : [{
							property : 'companyId',
							direction : 'ASC'
						}]
			});
	var pluginExpanded = true;
	var resultGrid = Ext.create('Ext.grid.Panel', {
				title : '查询结果',
				region : 'south',
				height : gridHeight,
				store : store,
				disableSelection : false,
				loadMask : true,
				multiSelect : false,
				itemSelector : '.feed-list-item',
				overItemCls : 'feed-list-item-hover',
				viewConfig : {
					id : 'gv',
					trackOver : false,
					stripeRows : false,
					plugins : [{
								ptype : 'preview',
								bodyField : 'excerpt',
								expanded : true,
								pluginId : 'preview'
							}]
				},
				// grid columns
				columns : [{
							xtype : 'rownumberer'
						}, {
							header : "公司编号",
							dataIndex : 'companyNo',
							flex : 1,
							sortable : false
						}, {
							header : "公司名称",
							dataIndex : 'companyName',
							width : 100,
							sortable : true
						}, {
							header : "联系人",
							dataIndex : 'companyContact',
							width : 70,
							align : 'right',
							sortable : true
						}, {
							header : "电话",
							dataIndex : 'companyTel',
							width : 150,
							sortable : true
						}],
				// paging bar on the bottom
				bbar : Ext.create('Ext.PagingToolbar', {
							store : store,
							displayInfo : true,
							displayMsg : '显示 {0} - {1}/共{2}条',
							emptyMsg : "没有查询结果"
						}),
				renderTo : 'result-grid'
			});
	resultGrid.child('pagingtoolbar').add(['->', {
				iconCls : 'icon-add',
				text : '增加新公司',
				scope : this,
				handler : newCompanyHandler
			}, {
				iconCls : 'icon-delete',
				text : '修改公司信息',
				scope : this,
				handler : updateCompanyHandler
			}, {
				iconCls : 'icon-delete',
				text : '删除公司',
				scope : this,
				handler : deleteCompanyHandler
			}]);
	resultGrid.addListener('itemdblclick', updateCompanyHandler, this);
	// trigger the data store load
	store.load({
				params : {
					start : 0,
					limit : pageSize
				}
			});
	// 查询用户
	function searchHandler() {
		store.on('beforeload', function() { // =======翻页时 查询条件
					var new_params = {
						companyNo : Ext.getCmp("s-company-no").getValue(),
						companyName : Ext.getCmp("s-company-name").getValue(),
						companyCategoryNo : Ext.getCmp("s-company-category-no").getValue(),
						provinceNo : Ext.getCmp("s-province-no").getValue()
					};
					Ext.apply(store.proxy.extraParams, new_params);
				});
		store.loadPage(1);
	}
	// 刷新当前页查询结果
	function refreshGrid() {
		var pageIndex = resultGrid.getStore().currentPage;
		store.load({
					params : {
						start : (pageIndex - 1) * pageSize,
						limit : pageSize
					}
				});
	}
	// 新增公司
	function newCompanyHandler() {
		showCompanyInfoForm();
		Ext.getCmp('company-form').getForm().reset();
	}
	// 修改公司
	function updateCompanyHandler() {
		var row = resultGrid.getSelectionModel().getSelection()[0];
		if (!row) {

			Ext.MessageBox.alert('提示', "请选择一条记录!");
			return;
		}
		showCompanyInfoForm();
		Ext.getCmp('company-form').getForm().loadRecord(row);
	}
	// 删除公司
	function deleteCompanyHandler() {
		var row = resultGrid.getSelectionModel().getSelection()[0];
		if (!row) {

			Ext.MessageBox.alert('提示', "请选择一条记录!");
			return;
		}
		Ext.MessageBox.confirm('确认', '是否删除?', deleteCompany)
	}
	function deleteCompany(btn) {
		if (btn == 'yes') {
			Ext.Msg.wait('处理中，请稍后...', '提示');
			Ext.Ajax.request({
				url : 'deleteCompany',
				params : {
					crumb : Ext.get('crumb').getValue(),
					companyId : resultGrid.getSelectionModel().getSelection()[0]
							.get('companyId')
				},
				success : function(response, options) {
					var text = unicodeToString(response.responseText);
					var responseArray = Ext.JSON.decode(text);
					if (responseArray.success) {
						Ext.MessageBox.alert('提示', "处理成功!");
						refreshGrid();
					} else {
						Ext.MessageBox.alert("错误", responseArray.message);
					}
				}
			});
		} else {
			return;
		}
	}
	// 修改公司信息窗口
	var winInfo;
	function showCompanyInfoForm() {
		if (!winInfo) {
			var form = Ext.widget('form', {
				border : false,
				bodyPadding : 10,
				url : 'saveCompany',
				id : 'company-form',
				fieldDefaults : {
					labelWidth : 100,
					labelStyle : 'font-weight:bold'
				},
				defaults : {
					margins : '0 0 10 0'
				},
				items : [{
							xtype : 'hidden',
							allowBlank : false,
							name : 'crumb',
							value : Ext.get('crumb').getValue()
						}, {
							xtype : 'hidden',
							allowBlank : false,
							name : 'companyId',
							id : 'info-company-id'
						}, {
							xtype : 'textfield',
							fieldLabel : '公司编号',
							allowBlank : false,
							name : 'companyNo',
							id : 'info-company-no'
						}, {
							xtype : 'textfield',
							fieldLabel : '公司名称',
							name : 'companyName',
							id : 'info-company-name'
						}, Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '类型',
									displayField : 'unitName',
									valueField : 'unitNo',
									store : catestore,
									queryMode : 'remote',
									editable : false,
									name : "companyCategoryNo",
									id : 'info-category'
								}), Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '省份',
									displayField : 'provinceName',
									valueField : 'id',
									store : provincestore,
									queryMode : 'remote',
									editable : false,
									name : "provinceNo",
									id : "info-province-cd"
								}), {
							xtype : 'textfield',
							fieldLabel : '城市',
							name : 'companyCity',
							id : 'info-city'
						}, {
							xtype : 'textfield',
							fieldLabel : '地址',
							name : 'companyAddress',
							id : 'info-address'
						}, {
							xtype : 'textfield',
							fieldLabel : '邮编',
							name : 'companyZip',
							id : 'info-zip'
						}, {
							xtype : 'textfield',
							fieldLabel : '联系人',
							name : 'companyContact',
							id : 'info-contact'
						}, {
							xtype : 'textfield',
							fieldLabel : '电话',
							name : 'companyTel',
							id : 'info-phone'
						}],

				buttons : [{
							text : '取消',
							handler : function() {
								this.up('form').getForm().reset();
								this.up('window').hide();
							}
						}, {
							text : '保存',
							handler : function() {
								var form = this.up('form').getForm();
								if (form.isValid()) {
									form.submit({
										success : function(form, action) {
											Ext.MessageBox.alert('提示', "处理成功!");
											winInfo.hide();
											refreshGrid();
										},
										failure : function(form, action) {
											Ext.MessageBox.alert('错误',
													action.result.message);
										}
									});
								}
							}
						}]
			});

			winInfo = Ext.widget('window', {
						title : '修改公司信息',
						closeAction : 'hide',
						width : 400,
						height : 380,
						layout : 'fit',
						resizable : true,
						modal : true,
						items : form
					});
		}
		winInfo.show(); 
	}
});