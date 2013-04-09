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
	Ext.regModel('company_combo_model', {
				fields : [{
							type : 'string',
							name : 'companyName'
						}, {
							type : 'int',
							name : 'id'
						}]
			});
	var companyliststore = Ext.create('Ext.data.Store', {
				model : 'company_combo_model',
				remoteSort : true,
				proxy : {
					type : 'ajax',
					url : 'allCompanyList'
				}
			});
	companyliststore.load();
	Ext.regModel('combo_model', {
				fields : [{
							type : 'string',
							name : 'name'
						}, {
							type : 'string',
							name : 'value'
						}]
			});
			
	var whliststore = Ext.create('Ext.data.Store', {
				model : 'combo_model',
				remoteSort : true,
				proxy : {
					type : 'ajax',
					url : 'warehouseListByCompany'
				}
			});
	whliststore.load();
	Ext.regModel('unit_model', {
				fields : [{
							type : 'string',
							name : 'unitName'
						}, {
							type : 'string',
							name : 'unitNo'
						}]
			});
	var type_store = Ext.create('Ext.data.Store', {
				model : 'unit_model',
				remoteSort : true,
				proxy : {
					type : 'ajax',
					url : 'storeTypeList'
				}
			});
	type_store.load();
	Ext.regModel('area_model', {
				fields : [{
							type : 'string',
							name : 'id'
						}, {
							type : 'string',
							name : 'name'
						}]
			});
	var area_store = Ext.create('Ext.data.Store', {
				model : 'area_model',
				remoteSort : true,
				proxy : {
					type : 'ajax',
					url : 'tbStoreAreaList'
				}
			});
	area_store.load();
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
							fieldLabel : "店铺名称", // 标签内容
							name : "StoreName",
							id : "s-store-name",
							margin : "5 5 5 10"
						}, Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '店铺类型',
									displayField : 'unitName',
									valueField : 'unitNo',
									store : type_store,
									queryMode : 'remote',
									editable : false,
									name : "StoreType",
									id : 's-store-type'
								}), Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '公司',
									displayField : 'companyName',
									valueField : 'id',
									store : companyliststore,
									queryMode : 'remote',
									editable : false,
									name : "CompanyId",
									id : 's-company-id'
								})],
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
				fields : ['StoreId', 'StoreName', 'StoreType', 'Status',
						'rcnNo', 'CompanyId', 'CompanyName','WhId','Level'],
				idProperty : 'StoreId'
			});

	// create the Data Store
	var store = Ext.create('Ext.data.Store', {
				model : 'SearchModel',
				remoteSort : true,
				pageSize : pageSize,
				proxy : new Ext.data.HttpProxy({
							type : 'jsonp',
							url : 'searchStore',
							reader : {
								root : 'resultList',
								totalProperty : 'rowCount',
								fields : ['StoreId', 'StoreName', 'StoreType',
										'Status', 'rcnNo', 'CompanyId',
										'CompanyName','WhId','Level']
							},
							simpleSortMode : true
						}),
				sorters : [{
							property : 'StoreId',
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
							header : "店铺编号",
							dataIndex : 'StoreId',
							flex : 1,
							sortable : false
						}, {
							header : "店铺名称",
							dataIndex : 'StoreName',
							width : 100,
							sortable : true
						}, {
							header : "公司",
							dataIndex : 'CompanyName',
							width : 70,
							align : 'right',
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
				text : '增加新店铺',
				scope : this,
				handler : newStoreHandler
			}, {
				iconCls : 'icon-delete',
				text : '修改店铺信息',
				scope : this,
				handler : updateStoreHandler
			}, {
				iconCls : 'icon-delete',
				text : '删除店铺',
				scope : this,
				handler : deleteStoreHandler
			}, {
				iconCls : 'icon-delete',
				text : '淘宝店铺设置',
				scope : this,
				handler : tbStoreInfoHandler
			}]);
	resultGrid.addListener('itemdblclick', updateStoreHandler, this);
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
						storeType : Ext.getCmp("s-store-type").getValue(),
						storeName : Ext.getCmp("s-store-name").getValue(),
						companyId : Ext.getCmp("s-company-id").getValue()
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
	// 新增店铺
	function newStoreHandler() {
		showStoreInfoForm();
		Ext.getCmp('store-form').getForm().reset();
	}
	// 修改店铺
	function updateStoreHandler() {
		var row = resultGrid.getSelectionModel().getSelection()[0];
		if (!row) {

			Ext.MessageBox.alert('提示', "请选择一条记录!");
			return;
		}
		showStoreInfoForm();
		Ext.getCmp('store-form').getForm().loadRecord(row);
	}
	// 删除店铺
	function deleteStoreHandler() {
		var row = resultGrid.getSelectionModel().getSelection()[0];
		if (!row) {

			Ext.MessageBox.alert('提示', "请选择一条记录!");
			return;
		}
		Ext.MessageBox.confirm('确认', '是否删除?', deleteStore)
	}
	function deleteStore(btn) {
		if (btn == 'yes') {
			Ext.Msg.wait('处理中，请稍后...', '提示');
			Ext.Ajax.request({
						url : 'deleteStore',
						params : {
							crumb : Ext.get('crumb').getValue(),
							storeId : resultGrid.getSelectionModel()
									.getSelection()[0].get('StoreId')
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
	// 淘宝店铺信息
	function tbStoreInfoHandler() {
		var row = resultGrid.getSelectionModel().getSelection()[0];
		if (!row) {

			Ext.MessageBox.alert('提示', "请选择一条记录!");
			return;
		}
		showTbStoreInfoForm();
		Ext.getCmp('tb-store-form').getForm().reset();

		Ext.Ajax.request({
			url : 'tbStoreInfo',
			params : {
				storeId : row.get("StoreId")
			},
			success : function(response, options) {
				var text = unicodeToString(response.responseText);
				var responseArray = Ext.JSON.decode(text);
				if (responseArray.storeId) {
					Ext.getCmp("tbinfo-store-id")
							.setValue(responseArray.storeId);
					Ext.getCmp("tbinfo-status").setValue(responseArray.status);
					Ext.getCmp("tbinfo-store-type")
							.setValue(responseArray.storeType);
					Ext.getCmp("tbinfo-address")
							.setValue(responseArray.address);
					Ext.getCmp("tbinfo-app-key").setValue(responseArray.appKey);
					Ext.getCmp("tbinfo-app-sercet")
							.setValue(responseArray.appSercet);
					Ext.getCmp("tbinfo-area-id").setValue(responseArray.areaId);
					Ext.getCmp("tbinfo-memo").setValue(responseArray.memo);
					Ext.getCmp("tbinfo-mobile").setValue(responseArray.mobile);
					Ext.getCmp("tbinfo-phone").setValue(responseArray.phone);
					Ext.getCmp("tbinfo-sandbox-url")
							.setValue(responseArray.sandboxUrl);
					Ext.getCmp("tbinfo-session-key")
							.setValue(responseArray.sessionKey);
					Ext.getCmp("tbinfo-session-url")
							.setValue(responseArray.sessionUrl);
					if(responseArray.storeNick)
					Ext.getCmp("tbinfo-store-nick")
							.setValue(responseArray.storeNick);
					else
					Ext.getCmp("tbinfo-store-nick")
							.setValue(row.get('StoreName'));
					
					Ext.getCmp("tbinfo-zip").setValue(responseArray.zip);
				} else {
				}
			}
		});
	}
	// 修改店铺信息窗口
	var winInfo;
	function showStoreInfoForm() {
		if (!winInfo) {
			var form = Ext.widget('form', {
				border : false,
				bodyPadding : 10,
				layout : 'vbox',
				url : 'saveStore',
				id : 'store-form',
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
							xtype : 'textfield',
							fieldLabel : '店铺编号',
							disabled : true,
							name : 'StoreId',
							id : 'info-store-id'
						}, {
							xtype : 'textfield',
							fieldLabel : '店铺名称',
							name : 'StoreName',
							allowBlank : false,
							id : 'info-store-name'
						}, Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '店铺类型',
									displayField : 'unitName',
									valueField : 'unitNo',
									store : type_store,
									queryMode : 'remote',
									editable : false,
									name : "StoreType",
									id : 'info-store-type'
								}), Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '状态',
									displayField : 'name',
									valueField : 'value',
									store : Ext.create('Ext.data.Store', {
												model : 'combo_model',
												data : [{
															"name" : "可用",
															"value" : "ACTIVE"
														}, {
															"name" : "不可用",
															"value" : "INACTIVE"
														}]
											}),
									queryMode : 'local',
									editable : false,
									name : "Status",
									id : "info-status"
								}), Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '公司',
									displayField : 'companyName',
									valueField : 'id',
									store : companyliststore,
									allowBlank : false,
									queryMode : 'remote',
									editable : false,
									name : "CompanyId",
									id : 'info-company-id'
								}), Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '仓库',
									displayField : 'name',
									valueField : 'value',
									store : whliststore,
									allowBlank : true,
									queryMode : 'remote',
									editable : false,
									name : "WhId",
									id : 'info-wh-id'
								}), {
							xtype : 'numberfield',
							fieldLabel : '级别',
							allowBlank : true,
							name : 'Level',
							id : 'info-level'
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
									Ext.Ajax.request({
										url : 'saveStore',
										params : {
											crumb : Ext.get('crumb').getValue(),
											storeId : Ext
													.getCmp("info-store-id")
													.getValue(),
											storeType : Ext
													.getCmp("info-store-type")
													.getValue(),
											storeName : Ext
													.getCmp("info-store-name")
													.getValue(),
											companyId : Ext
													.getCmp("info-company-id")
													.getValue(),
											status : Ext.getCmp("info-status")
													.getValue(),
											whId : Ext.getCmp("info-wh-id")
													.getValue(),
											level : Ext.getCmp("info-level")
													.getValue()
										},
										success : function(form, action) {
											var text = unicodeToString(form.responseText);
											var responseArray = Ext.JSON
													.decode(text);
											if (responseArray.success) {
												Ext.MessageBox.alert('提示',
														"处理成功!");
												winInfo.hide();
												refreshGrid();
											} else {
												Ext.MessageBox.alert("错误",
														responseArray.message);
											}
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
						title : '修改店铺信息',
						closeAction : 'hide',
						width : 400,
						height : 380,
						layout : 'fit',
						resizable : true,
						modal : true,
						items : form
					});
		}// 得到登录用户的信息
		Ext.Ajax.request({
					url : 'userLoginInfo',
					success : function(response, options) {
						var text = unicodeToString(response.responseText);
						var responseArray = Ext.JSON.decode(text);
						if (responseArray.userNo) {
							//非管理员不能更改用户的公司
							if(responseArray.userType==null||responseArray.userType!='SYSTEM')
							{
								Ext.getCmp('info-company-id').setDisabled(true);								
								Ext.getCmp('info-company-id')
									.setValue(responseArray.companyId);
							}
							else
							{
								Ext.getCmp('info-company-id').setDisabled(false);
							}
						} else {
							Ext.MessageBox.alert("错误", "用户信息没找到");
						}
					}
				});
		winInfo.show();
	}
	// 修改淘宝店铺信息窗口
	var winTbInfo;
	function showTbStoreInfoForm() {
		if (!winTbInfo) {
			var form = Ext.widget('form', {
				border : false,
				bodyPadding : 10,
				url : 'saveTbStore',
				id : 'tb-store-form',
				layout : {
					type : 'table',
					columns : '2',
					align : 'center'
				},
				fieldDefaults : {
					labelWidth : 100,
					labelStyle : 'font-weight:bold'
				},
				items : [{
							xtype : 'hidden',
							allowBlank : false,
							name : 'crumb',
							colspan : 2,
							value : Ext.get('crumb').getValue()
						}, {
							xtype : 'hidden',
							allowBlank : false,
							name : 'storeType',
							colspan : 2,
							id : 'tbinfo-store-type'
						}, {
							xtype : 'hidden',
							allowBlank : false,
							name : 'status',
							colspan : 2,
							id : 'tbinfo-status'
						}, {
							xtype : 'textfield',
							fieldLabel : '店铺编号',
							readOnly : true,
							name : 'storeId',
							id : 'tbinfo-store-id'
						}, {
							xtype : 'textfield',
							fieldLabel : 'App Key',
							name : 'appKey',
							id : 'tbinfo-app-key'
						}, {
							xtype : 'textfield',
							fieldLabel : '店铺昵称',
							name : 'storeNick',
							allowBlank : false,
							id : 'tbinfo-store-nick'
						}, {
							xtype : 'textfield',
							fieldLabel : 'App Sercet',
							name : 'appSercet',
							id : 'tbinfo-app-sercet'
						}, {
							xtype : 'textfield',
							fieldLabel : '手机',
							name : 'mobile',
							id : 'tbinfo-mobile'
						}, {
							xtype : 'textfield',
							fieldLabel : 'Session Key',
							name : 'sessionKey',
							id : 'tbinfo-session-key'
						}, {
							xtype : 'textfield',
							fieldLabel : '电话',
							name : 'phone',
							id : 'tbinfo-phone'
						}, {
							xtype : 'textfield',
							fieldLabel : 'Session Url',
							name : 'sessionUrl',
							id : 'tbinfo-session-url'
						}, {
							xtype : 'textfield',
							fieldLabel : '邮编',
							name : 'zip',
							id : 'tbinfo-zip'
						}, {
							xtype : 'textfield',
							fieldLabel : 'Sandbox Url',
							name : 'sandboxUrl',
							id : 'tbinfo-sandbox-url'
						}, Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '店铺区域',
									displayField : 'companyName',
									valueField : 'id',
									store : area_store,
									allowBlank : true,
									queryMode : 'remote',
									editable : false,
									name : "areaId",
									id : 'tbinfo-area-id'
								}), {
							xtype : 'textfield',
							fieldLabel : '店铺地址',
							name : 'address',
							width : 300,
							id : 'tbinfo-address'
						}, {
							xtype : 'textfield',
							fieldLabel : '备注',
							name : 'memo',
							width : 700,
							colspan : 2,
							id : 'tbinfo-memo'
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
											winTbInfo.hide();
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

			winTbInfo = Ext.widget('window', {
						title : '修改淘宝店铺信息',
						closeAction : 'hide',
						width : 800,
						height : 380,
						layout : 'fit',
						resizable : true,
						modal : true,
						items : form
					});
		}
		winTbInfo.show();
	}
});