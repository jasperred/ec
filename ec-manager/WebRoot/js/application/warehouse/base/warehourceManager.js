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
							fieldLabel : "仓库编号", // 标签内容
							name : "whCode",
							id : "s-wh-code",
							margin : "5 5 5 10"
						}, {
							fieldLabel : "仓库名称", // 标签内容
							name : "whName",
							id : "s-wh-name",
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

	var gridHeight = getWindownHeight() - searchPanel.getHeight();
	Ext.define('SearchModel', {
				extend : 'Ext.data.Model',
				fields : [ 'whType', 'whName', 'companyId', 'status',
						'whCode', 'reserveInv', 'allowNegativeInv',
						'validTime', 'whId', 'companyName'],
				idProperty : 'whId'
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
							url : 'searchWarehouse',
							reader : {
								root : 'resultList',
								totalProperty : 'rowCount',
								fields : [ 'whType', 'whName',
										'companyId', 'status', 'whCode',
										'reserveInv', 'allowNegativeInv',
										'validTime', 'whId', 'companyName']
							},
							// sends single sort as multi parameter
							simpleSortMode : true
						}),
				sorters : [{
							property : 'whId',
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
							header : "仓库编号",
							dataIndex : 'whCode',
							align : 'center',
							width : 100,
							sortable : false
						}, {
							header : "仓库名称",
							dataIndex : 'whName',
							align : 'center',
							width : 250,
							sortable : true
						}, {
							header : "公司",
							dataIndex : 'companyName',
							align : 'center',
							width : 200,
							sortable : true
						}, {
							header : "有效时间",
							dataIndex : 'validTime',
							align : 'right',
							width : 100,
							sortable : true
						}, {
							header : "预留库存",
							dataIndex : 'reserveInv',
							align : 'right',
							width : 100,
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
				text : '增加新仓库',
				scope : this,
				handler : newWarehouseHandler
			}, {
				iconCls : 'icon-delete',
				text : '修改仓库信息',
				scope : this,
				handler : updateWarehouseHandler
			}, {
				iconCls : 'icon-delete',
				text : '删除仓库',
				scope : this,
				handler : deleteWarehouseHandler
			}]);
	resultGrid.addListener('itemdblclick', updateWarehouseHandler, this);
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
						whCode : Ext.getCmp("s-wh-code").getValue(),
						whName : Ext.getCmp("s-wh-name").getValue()
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
	function newWarehouseHandler() {
		showWarehouseInfoForm();
		Ext.getCmp('warehouse-form').getForm().reset();
		Ext.getCmp('info-wh-code').setDisabled(false);
	}
	// 修改公司
	function updateWarehouseHandler() {
		var row = resultGrid.getSelectionModel().getSelection()[0];
		if (!row) {

			Ext.MessageBox.alert('提示', "请选择一条记录!");
			return;
		}
		showWarehouseInfoForm();
		Ext.getCmp('info-wh-code').setDisabled(true);
		Ext.Ajax.request({
					url : 'warehouseInfo',
					params : {
						whId : row.get('whId')
					},
					success : function(response, options) {
						var text = unicodeToString(response.responseText);
						var info = Ext.JSON.decode(text);
						Ext.getCmp('info-wh-id').setValue(info.whId);
						Ext.getCmp('info-wh-code').setValue(info.whCode);
						Ext.getCmp('info-wh-name').setValue(info.whName);
						// Ext.getCmp('info-company-id').setValue(info.companyId);
						Ext.getCmp('info-status').setValue(info.status);
						Ext.getCmp('info-reserve-inv')
								.setValue(info.reserveInv);
						Ext.getCmp('info-allow-negative-inv')
								.setValue(info.allowNegativeInv);
						Ext.getCmp('info-valid-time').setValue(info.validTime);
					}
				});

	}
	// 删除公司
	function deleteWarehouseHandler() {
		var row = resultGrid.getSelectionModel().getSelection()[0];
		if (!row) {

			Ext.MessageBox.alert('提示', "请选择一条记录!");
			return;
		}
		Ext.MessageBox.confirm('确认', '是否删除?', deleteWarehouse)
	}
	function deleteWarehouse(btn) {
		if (btn == 'yes') {
			Ext.Msg.wait('处理中，请稍后...', '提示');
			Ext.Ajax.request({
						url : 'deleteWarehouse',
						params : {
							crumb : Ext.get('crumb').getValue(),
							whId : resultGrid.getSelectionModel()
									.getSelection()[0].get('whId')
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
	function showWarehouseInfoForm() {
		if (!winInfo) {
			var form = Ext.widget('form', {
				border : false,
				bodyPadding : 10,
				id : 'warehouse-form',
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
							name : 'whId',
							id : 'info-wh-id'
						}, {
							xtype : 'hidden',
							allowBlank : true,
							name : 'status',
							id : 'info-status',
							value : 'ACTIVE'
						}, {
							xtype : 'textfield',
							fieldLabel : '仓库编号',
							allowBlank : false,
							name : 'whCode',
							id : 'info-wh-code'
						}, {
							xtype : 'textfield',
							fieldLabel : '仓库名称',
							allowBlank : false,
							name : 'whName',
							id : 'info-wh-name'
						}, {
							xtype : 'numberfield',
							fieldLabel : '预留库存',
							allowBlank : false,
							name : 'reserveInv',
							id : 'info-reserve-inv'
						}, {
							xtype : 'numberfield',
							fieldLabel : '有效时间(分钟)',
							name : 'validTime',
							id : 'info-valid-time'
						}, Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '允许负库存',
									displayField : 'name',
									valueField : 'value',
									store : Ext.create('Ext.data.Store', {
												model : 'combo_model',
												data : [{
															"name" : 'Y',
															"value" : 'Y'
														}, {
															"name" : 'N',
															"value" : 'N'
														}]
											}),
									queryMode : 'local',
									editable : false,
									name : "allowNegativeInv",
									id : "info-allow-negative-inv"
								})],

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
									var whId = Ext.getCmp('info-wh-id')
											.getValue();
									var url = 'saveWarehouse';
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
						title : '仓库信息',
						closeAction : 'hide',
						width : 400,
						height : 450,
						layout : 'fit',
						resizable : true,
						modal : true,
						items : form
					});
		}
		winInfo.show();
	}
});