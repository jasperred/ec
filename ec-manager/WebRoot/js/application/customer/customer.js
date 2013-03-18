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
	Ext.regModel('combo_model', {
				fields : [{
							type : 'string',
							name : 'name'
						}, {
							type : 'string',
							name : 'value'
						}]
			});
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
							fieldLabel : "会员编号", // 标签内容
							name : "custNo",
							id : "s-cust-no",
							margin : "5 5 5 10"
						}, {
							fieldLabel : "会员名称", // 标签内容
							name : "custName",
							id : "s-cust-name",
							margin : "5 5 5 10"
						}, {
							fieldLabel : "邮件", // 标签内容
							name : "email",
							id : "s-email",
							margin : "5 5 5 10"
						}, {
							fieldLabel : "手机号", // 标签内容
							name : "mobile",
							id : "s-mobile",
							margin : "5 5 5 10"
						}, Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '性别',
									displayField : 'name',
									valueField : 'value',
									store : Ext.create('Ext.data.Store', {
												model : 'combo_model',
												data : [{
															"name" : '全部',
															"value" : ''
														}, {
															"name" : '男',
															"value" : '男'
														}, {
															"name" : '女',
															"value" : '女'
														}]
											}),
									queryMode : 'local',
									editable : false,
									name : "sex",
									id : "s-sex",
									margin : "5 5 5 10"
								}), {
							xtype : 'fieldcontainer',
							fieldLabel : '会员生日',
							combineErrors : true,
							msgTarget : 'side',
							layout : 'hbox',
							width : 380,
							margin : "5 5 5 10",
							defaults : {
								flex : 1,
								hideLabel : true
							},
							items : [{
										xtype : 'datefield',
										name : 'birthDayFrom',
										id : 's-birth-day-from',
										format : 'Y-m-d',
										fieldLabel : '',
										margin : '0 5 0 0'
									}, {
										xtype : 'datefield',
										name : 'birthDayEnd',
										id : 's-birth-day-end',
										format : 'Y-m-d',
										fieldLabel : ''
									}]
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

	var gridHeight = getWindownHeight() - searchPanel.getHeight();
	Ext.define('SearchModel', {
				extend : 'Ext.data.Model',
				fields : ['id', 'custNo', 'custName', 'email', 'mobile', 'sex',
						{
							name : 'birthDay',
							type : 'date',
							dateFormat : 'Y-m-d'
						}],
				idProperty : 'id'
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
							url : 'customerSearch',
							reader : {
								root : 'resultList',
								totalProperty : 'rowCount',
								fields : ['id', 'custNo', 'custName', 'email',
										'mobile', 'sex', 'birthDay']
							},
							// sends single sort as multi parameter
							simpleSortMode : true
						}),
				sorters : [{
							property : 'id',
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
							header : "会员ID",
							dataIndex : 'id',
							align : 'center',
							width : 60,
							sortable : false
						}, {
							header : "会员编号",
							dataIndex : 'custNo',
							align : 'center',
							width : 100,
							sortable : false
						}, {
							header : "会员名称",
							dataIndex : 'custName',
							align : 'center',
							width : 150,
							sortable : true
						}, {
							header : "邮件",
							dataIndex : 'email',
							align : 'center',
							width : 200,
							align : 'center',
							sortable : true
						}, {
							header : "手机号",
							dataIndex : 'mobile',
							align : 'center',
							width : 100,
							sortable : true
						}, {
							header : "性别",
							dataIndex : 'sex',
							align : 'center',
							width : 50,
							sortable : true
						}, {
							header : "会员生日",
							dataIndex : 'birthDay',
							renderer : Ext.util.Format.dateRenderer('Y-m-d'),
							align : 'center',
							flex : 1,
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
				text : '增加新会员',
				scope : this,
				handler : newCustomerHandler
			}, {
				iconCls : 'icon-delete',
				text : '修改会员信息',
				scope : this,
				handler : updateCustomerHandler
			}, {
				iconCls : 'icon-delete',
				text : '删除会员',
				scope : this,
				handler : deleteCustomerHandler
			}]);
	resultGrid.addListener('itemdblclick', updateCustomerHandler, this);
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
						custNo : Ext.getCmp("s-cust-no").getValue(),
						custName : Ext.getCmp("s-cust-name").getValue(),
						email : Ext.getCmp("s-email").getValue(),
						mobile : Ext.getCmp("s-mobile").getValue(),
						sex : Ext.getCmp("s-sex").getValue(),
						birthDayFrom : Ext.getCmp("s-birth-day-from")
								.getRawValue(),
						birthDayEnd : Ext.getCmp("s-birth-day-end").getRawValue()
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
	function newCustomerHandler() {
		showCustomerInfoForm();
		Ext.getCmp('customer-form').getForm().reset();
		Ext.getCmp('info-cust-no').setDisabled(true);
	}
	// 修改公司
	function updateCustomerHandler() {
		var row = resultGrid.getSelectionModel().getSelection()[0];
		if (!row) {

			Ext.MessageBox.alert('提示', "请选择一条记录!");
			return;
		}
		showCustomerInfoForm();
		Ext.getCmp('info-cust-no').setDisabled(true);
		Ext.Ajax.request({
					url : 'customerInfo',
					params : {
						custId : row.get('id')
					},
					success : function(response, options) {
						var text = unicodeToString(response.responseText);
						var responseArray = Ext.JSON.decode(text);
						var info = responseArray.customerInfo;
						Ext.getCmp('info-cust-id').setValue(info.id);
						Ext.getCmp('info-cust-no').setValue(info.custNo);
						Ext.getCmp('info-cust-name').setValue(info.custName);
						Ext.getCmp('info-mobile').setValue(info.mobile);
						Ext.getCmp('info-tel').setValue(info.tel);
						Ext.getCmp('info-birth-day').setValue(info.birthDay);
						Ext.getCmp('info-email').setValue(info.email);
						Ext.getCmp('info-sex').setValue(info.sex);
						Ext.getCmp('info-province').setValue(info.province);
						Ext.getCmp('info-city').setValue(info.city);
						Ext.getCmp('info-district').setValue(info.district);
						Ext.getCmp('info-address').setValue(info.address);
						Ext.getCmp('info-zipcode').setValue(info.zipcode);
					}
				});

	}
	// 删除公司
	function deleteCustomerHandler() {
		var row = resultGrid.getSelectionModel().getSelection()[0];
		if (!row) {

			Ext.MessageBox.alert('提示', "请选择一条记录!");
			return;
		}
		Ext.MessageBox.confirm('确认', '是否删除?', deleteCustomer)
	}
	function deleteCustomer(btn) {
		if (btn == 'yes') {
			Ext.Msg.wait('处理中，请稍后...', '提示');
			Ext.Ajax.request({
						url : 'deleteCustomer',
						params : {
							crumb : Ext.get('crumb').getValue(),
							custId : resultGrid.getSelectionModel()
									.getSelection()[0].get('id')
						},
						success : function(response, options) {
							var text = unicodeToString(response.responseText);
							var responseArray = Ext.JSON.decode(text);
							if (responseArray.success) {
								Ext.MessageBox.alert('提示', "处理成功!");
								refreshGrid();
							} else {
								Ext.MessageBox.alert("错误",
										responseArray.message);
							}
						}
					});
		} else {
			return;
		}
	}
	// 修改公司信息窗口
	var winInfo;
	function showCustomerInfoForm() {
		if (!winInfo) {
			var form = Ext.widget('form', {
				border : false,
				bodyPadding : 10,
				id : 'customer-form',
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
							allowBlank : true,
							name : 'custId',
							id : 'info-cust-id'
						}, {
							xtype : 'textfield',
							fieldLabel : '会员编号',
							disabled : true,
							name : 'custNo',
							id : 'info-cust-no'
						}, {
							xtype : 'textfield',
							fieldLabel : '会员名称*',
							allowBlank : false,
							name : 'custName',
							id : 'info-cust-name'
						}, {
							xtype : 'textfield',
							fieldLabel : '手机*',
							allowBlank : false,
							name : 'mobile',
							id : 'info-mobile'
						}, {
							xtype : 'textfield',
							fieldLabel : '电话',
							name : 'tel',
							id : 'info-tel'
						}, {
							xtype : 'datefield',
							fieldLabel : '会员生日',
							name : 'birthDay',
							format : 'Y-m-d',
							id : 'info-birth-day'
						}, {
							xtype : 'textfield',
							fieldLabel : '邮件',
							name : 'email',
							id : 'info-email'
						}, Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '性别',
									displayField : 'name',
									valueField : 'value',
									store : Ext.create('Ext.data.Store', {
												model : 'combo_model',
												data : [{
															"name" : '男',
															"value" : '男'
														}, {
															"name" : '女',
															"value" : '女'
														}]
											}),
									queryMode : 'local',
									editable : false,
									name : "sex",
									id : "info-sex"
								}), Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '省份',
									displayField : 'name',
									valueField : 'value',
									store : Ext.create('Ext.data.Store', {
												fields : ['name', 'value'],
												data : [{
															"name" : "请选择省份",
															"value" : ""
														}]
											}),
									queryMode : 'local',
									editable : false,
									allowBlank : true,
									value : "",
									name : "province",
									id : "info-province"
								}), Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '城市',
									displayField : 'name',
									valueField : 'value',
									store : Ext.create('Ext.data.Store', {
												fields : ['name', 'value'],
												data : [{
															"name" : "请选择城市",
															"value" : ""
														}]
											}),
									queryMode : 'local',
									editable : false,
									allowBlank : true,
									value : "",
									name : "city",
									id : "info-city"
								}), Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '地区',
									displayField : 'name',
									valueField : 'value',
									store : Ext.create('Ext.data.Store', {
												fields : ['name', 'value'],
												data : [{
															"name" : "请选择地区",
															"value" : ""
														}]
											}),
									queryMode : 'local',
									editable : false,
									allowBlank : true,
									value : "",
									name : "district",
									id : "info-district"
								}), {
							xtype : 'textfield',

							fieldLabel : '详细地址',
							width : 515,
							allowBlank : true,
							colspan : 2,
							name : "address",
							id : "info-address"
						}, {
							xtype : 'textfield',
							fieldLabel : '邮编',
							name : 'zipcode',
							id : 'info-zipcode'
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
									var custId = Ext.getCmp('info-cust-id')
											.getValue();
									var url = '';
									if (Ext.String.trim(custId).length == 0)
										url = 'newCustomer';
									else
										url = 'updateCustomer';
									form.submit({
										url : url,
										success : function(form, action) {
											Ext.MessageBox.alert('提示', "处理成功!");
											form.reset();
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
						title : '会员信息',
						closeAction : 'hide',
						width : 400,
						height : 450,
						layout : 'fit',
						resizable : true,
						modal : true,
						items : form
					});

			// 初始化省市区
			initCity('info-province', 'info-city', 'info-district');
		}
		winInfo.show();
	}
});