Ext.Loader.setConfig({
			enabled : true
		});

Ext.Loader.setPath('Ext.ux', 'js/ext/ux/');
Ext.require(['Ext.grid.*', 'Ext.data.*', 'Ext.util.*', 'Ext.toolbar.Paging',
		'Ext.ux.PreviewPlugin', 'Ext.ModelManager', 'Ext.tip.QuickTipManager',
		'Ext.state.*', 'Ext.ux.CheckColumn', 'Ext.form.field.ComboBox']);
Ext.onReady(function() {
	Ext.tip.QuickTipManager.init();
	Ext.QuickTips.init();
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

	var brandStore = Ext.create('Ext.data.Store', {
				model : 'combo_model',
				remoteSort : true,
				proxy : {
					type : 'ajax',
					url : 'searchItemGenerateType?type=品牌'
				}
			});
	var catStore = Ext.create('Ext.data.Store', {
				model : 'combo_model',
				remoteSort : true,
				proxy : {
					type : 'ajax',
					url : 'searchItemGenerateType?type=系列'
				}
			});
	var subCatStore = Ext.create('Ext.data.Store', {
				model : 'combo_model',
				remoteSort : true,
				proxy : {
					type : 'ajax',
					url : 'searchItemGenerateType?type=子系列'
				}
			});
	var yearStore = Ext.create('Ext.data.Store', {
				model : 'combo_model',
				remoteSort : true,
				proxy : {
					type : 'ajax',
					url : 'searchItemGenerateType?type=年'
				}
			});
	var seasonStore = Ext.create('Ext.data.Store', {
				model : 'combo_model',
				remoteSort : true,
				proxy : {
					type : 'ajax',
					url : 'searchItemGenerateType?type=季节'
				}
			});
	var sexStore = Ext.create('Ext.data.Store', {
				model : 'combo_model',
				remoteSort : true,
				proxy : {
					type : 'ajax',
					url : 'searchItemGenerateType?type=性别'
				}
			});
	var deptStore = Ext.create('Ext.data.Store', {
				model : 'combo_model',
				remoteSort : true,
				proxy : {
					type : 'ajax',
					url : 'searchItemGenerateType?type=部门'
				}
			});
	var detailStore = Ext.create('Ext.data.Store', {
				model : 'combo_model',
				remoteSort : true,
				proxy : {
					type : 'ajax',
					url : 'searchItemGenerateType?type=细分'
				}
			});

	var searchPanel = Ext.create('Ext.form.Panel', {
				id : 'search-condition-panel',
				title : '查询条件',
				region : 'north',
				frame : true, // 设置窗体为圆角
				method : "POST",
				autoHeight : true,
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
							xtype : 'textfield',
							fieldLabel : '品牌',
							margin : '5 5 5 10',
							name : 'brand',
							id : "s-brand"
						}, {
							xtype : 'textfield',
							fieldLabel : '系列',
							name : 'cat',
							margin : '5 5 5 10',
							id : "s-cat"
						}, {
							xtype : 'textfield',
							fieldLabel : '子系列',
							name : 'subCat',
							margin : '5 5 5 10',
							id : "s-sub-cat"
						}, {
							xtype : 'textfield',
							fieldLabel : '年',
							name : 'year',
							margin : '5 5 5 10',
							id : "s-year"
						}, {
							xtype : 'textfield',
							fieldLabel : '季节',
							name : 'season',
							margin : '5 5 5 10',
							id : "s-season"
						}, {
							xtype : 'textfield',
							fieldLabel : '性别',
							name : 'sex',
							margin : '5 5 5 10',
							id : "s-sex"
						}, {
							xtype : 'textfield',
							fieldLabel : '部门',
							name : 'dept',
							margin : '5 5 5 10',
							id : "s-dept"
						}, {
							xtype : 'textfield',
							fieldLabel : '细分',
							name : 'detail',
							margin : '5 5 5 10',
							id : "s-detail"
						}, Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '是否选用',
									displayField : 'name',
									valueField : 'value',
									store : Ext.create('Ext.data.Store', {
												model : 'combo_model',
												data : [{
															"name" : "是",
															"value" : "Y"
														}, {
															"name" : "否",
															"value" : "N"
														}]
											}),
									queryMode : 'local',
									editable : false,
									id : "s-sel",
									margin : "5 5 5 10"
								}), {
							xtype : 'fieldcontainer',
							fieldLabel : '时间',
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
										name : 'fromDate',
										id : 's-from-date',
										fieldLabel : '开始时间',
										margin : '0 5 0 0'
									}, {
										xtype : 'datefield',
										name : 'endDate',
										id : 's-end-date',
										fieldLabel : '结束时间'
									}]
						}],
				buttons : [{
							text : "新增",
							handler : newHandler
						}, {
							text : "查询",
							handler : searchHandler
						}, {
							text : "清空",
							handler : function() {
								searchPanel.getForm().reset();
							}
						}, {
							text : "导出CSV",
							handler : exportHandler
						}],
				renderTo : 'search-panel'
			});
	// 查询结果grid
	var gridHeight = getWindownHeight()-searchPanel.getHeight( );
	Ext.define('SearchModel', {
				extend : 'Ext.data.Model',
				fields : ['itemId', 'brand', 'cat', 'subCat', 'year', 'season',
						'sex', 'dept', 'detail', 'serial', 'itemCode', 'sel',
						'itemName', {
							name : 'ctime',
							type : 'date',
							dateFormat : 'Y-m-d H:i:s'
						}],
				idProperty : 'itemId'
			});

	// create the Data Store
	var store = Ext.create('Ext.data.Store', {
				model : 'SearchModel',
				autoDestroy : true,
				remoteSort : true,
				pageSize : pageSize,
				proxy : new Ext.data.HttpProxy({
							// load using script tags for cross domain,
							// if the
							// data in on the same domain as
							// this page, an HttpProxy would be better
							type : 'jsonp',
							url : 'searchGenerateItemCode',
							reader : {
								root : 'resultList',
								totalProperty : 'rowCount',
								fields : ['itemId', 'brand', 'cat', 'subCat',
										'year', 'season', 'sex', 'dept',
										'detail', 'serial', 'itemCode', 'sel',
										'itemName', 'ctime']
							},
							// sends single sort as multi parameter
							simpleSortMode : true
						})
			});
	var resultGrid = Ext.create('Ext.grid.Panel', {
				title : '查询结果',
				region : 'center',
				height : gridHeight,
				store : store,
				multiSelect : false,
				// grid columns
				columns : [{
							xtype : 'rownumberer'
						}, {
							header : "品牌",
							dataIndex : 'brand',
							width : 80,
							sortable : false
						}, {
							header : "系列",
							dataIndex : 'cat',
							width : 60,
							sortable : true
						}, {
							header : "子系列",
							dataIndex : 'subCat',
							width : 60,
							sortable : true
						}, {
							header : "年",
							dataIndex : 'year',
							width : 60,
							align : 'right',
							sortable : true
						}, {
							header : "季节",
							dataIndex : 'season',
							width : 60,
							sortable : true
						}, {
							header : "性别",
							dataIndex : 'sex',
							width : 60,
							sortable : true
						}, {
							header : "部门",
							dataIndex : 'dept',
							width : 60,
							sortable : true
						}, {
							header : "细分",
							dataIndex : 'detail',
							width : 60,
							sortable : true
						}, {
							header : "品番",
							dataIndex : 'serial',
							width : 60,
							sortable : true
						}, {
							header : "编码",
							dataIndex : 'itemCode',
							width : 150,
							sortable : true
						}, {
							header : "商品名称",
							dataIndex : 'itemName',
							width : 235,
							sortable : true
						}, {
							header : "是否选用",
							dataIndex : 'sel',
							width : 60,
							sortable : true
						}, {
							header : "时间",
							dataIndex : 'ctime',
							renderer : Ext.util.Format
									.dateRenderer('Y-m-d H:i:s'),
							width : 120,
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
				text : '查看',
				scope : this,
				handler : showItemHandler
			}]);
	resultGrid.addListener('itemdblclick', showItemHandler, this);
	// trigger the data store load
	store.load({
				params : {
					start : 0,
					limit : pageSize
				}
			});
	// 查询
	function searchHandler() {
		store.on('beforeload', function() { // =======翻页时 查询条件
					var new_params = {
						limit : pageSize,
						brand : Ext.getCmp("s-brand").getValue(),
						cat : Ext.getCmp("s-cat").getValue(),
						subCat : Ext.getCmp("s-sub-cat").getValue(),
						year : Ext.getCmp("s-year").getValue(),
						season : Ext.getCmp("s-season").getValue(),
						sex : Ext.getCmp("s-sex").getValue(),
						dept : Ext.getCmp("s-dept").getValue(),
						detail : Ext.getCmp("s-detail").getValue(),
						sel : Ext.getCmp("s-sel").getValue(),
						fromDate : Ext.getCmp("s-from-date").getValue(),
						endDate : Ext.getCmp("s-end-date").getValue()
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
	// 导出CSV
	function exportHandler() {
		Ext.Msg.wait('处理中，请稍后...', '提示');
		Ext.Ajax.request({
					url : 'exportGenerateItemCode',
					params : {
						brand : Ext.getCmp("s-brand").getValue(),
						cat : Ext.getCmp("s-cat").getValue(),
						subCat : Ext.getCmp("s-sub-cat").getValue(),
						year : Ext.getCmp("s-year").getValue(),
						season : Ext.getCmp("s-season").getValue(),
						sex : Ext.getCmp("s-sex").getValue(),
						dept : Ext.getCmp("s-dept").getValue(),
						detail : Ext.getCmp("s-detail").getValue(),
						sel : Ext.getCmp("s-sel").getValue(),
						fromDate : Ext.getCmp("s-from-date").getValue(),
						endDate : Ext.getCmp("s-end-date").getValue()
					},
					success : function(response, options) {
						Ext.Msg.hide();
						var text = unicodeToString(response.responseText);
						var responseArray = Ext.JSON.decode(text);
						if (Ext.String.trim(responseArray.exportUrl).length > 0) {
							window.open(Ext.String
									.trim(responseArray.exportUrl));
						} else {
							Ext.MessageBox.alert("提示", "无导出文件");
						}
					}
				});
	}
	// 新增Item
	var newItemWin;
	function newHandler() {
		if (!newItemWin) {
			var form = Ext.create('Ext.form.Panel', {
						defaultType : 'textfield',
						bodyStyle : 'padding:5px 5px 0',
						defaults : {
							anchor : '100%'
						},
						border : false,
						layout : {
							type : 'table',
							columns : '3',
							align : 'stretch'
						},
						items : [
								Ext.create('Ext.form.field.ComboBox', {
											fieldLabel : '品牌',
											displayField : 'name',
											valueField : 'value',
											store : brandStore,
											queryMode : 'remote',
											editable : false,
											name : 'brand',
											allowBlank : false,
											id : 'new-brand'
										}),
								Ext.create('Ext.form.field.ComboBox', {
											fieldLabel : '系列',
											displayField : 'name',
											valueField : 'value',
											store : catStore,
											queryMode : 'remote',
											editable : false,
											name : 'cat',
											allowBlank : false,
											id : 'new-cat'
										}),
								Ext.create('Ext.form.field.ComboBox', {
											fieldLabel : '子系列',
											displayField : 'name',
											valueField : 'value',
											store : Ext.create(
													'Ext.data.Store', {
														model : 'combo_model'
													}),
											queryMode : 'local',
											editable : false,
											name : 'subCat',
											allowBlank : false,
											id : 'new-sub-cat'
										}),
								Ext.create('Ext.form.field.ComboBox', {
											fieldLabel : '年',
											displayField : 'name',
											valueField : 'value',
											store : yearStore,
											queryMode : 'remote',
											editable : false,
											name : 'year',
											allowBlank : false,
											id : 'new-year'
										}),
								Ext.create('Ext.form.field.ComboBox', {
											fieldLabel : '季节',
											displayField : 'name',
											valueField : 'value',
											store : seasonStore,
											queryMode : 'remote',
											editable : false,
											name : 'season',
											allowBlank : false,
											id : 'new-season',
											colspan : 2
										}),
								Ext.create('Ext.form.field.ComboBox', {
											fieldLabel : '性别',
											displayField : 'name',
											valueField : 'value',
											store : sexStore,
											queryMode : 'remote',
											editable : false,
											name : 'sex',
											allowBlank : false,
											id : 'new-sex'
										}),
								Ext.create('Ext.form.field.ComboBox', {
											fieldLabel : '部门',
											displayField : 'name',
											valueField : 'value',
											store : deptStore,
											queryMode : 'remote',
											editable : false,
											name : 'dept',
											allowBlank : false,
											id : 'new-dept'
										}),
								Ext.create('Ext.form.field.ComboBox', {
											fieldLabel : '细分',
											displayField : 'name',
											valueField : 'value',
											store : Ext.create(
													'Ext.data.Store', {
														model : 'combo_model'
													}),
											queryMode : 'local',
											editable : false,
											name : 'detail',
											allowBlank : false,
											id : 'new-detail',
											colspan : 2
										}), {
									fieldLabel : '品番',
									name : 'serial',
									disabled : true,
									id : 'new-serial'
								}, {
									fieldLabel : '样品号',
									name : 'itemCode',
									disabled : true,
									id : 'new-item-code',
									colspan : 2
								}, {
									fieldLabel : '商品名称',
									name : 'itemName',
									id : 'new-item-name',
									width : 510,
									colspan : 3
								}, Ext.create('Ext.form.field.ComboBox', {
											fieldLabel : '是否选用',
											displayField : 'name',
											valueField : 'value',
											store : Ext.create(
													'Ext.data.Store', {
														model : 'combo_model',
														data : [{
																	"name" : "是",
																	"value" : "Y"
																}, {
																	"name" : "否",
																	"value" : "N"
																}]
													}),
											queryMode : 'local',
											editable : false,
											id : "new-sel"
										})]
					});
			Ext.getCmp("new-cat").on('change', function() {
				Ext.getCmp("new-sub-cat").clearValue();
				Ext.Ajax.request({
							url : 'searchItemGenerateType',
							params : {
								type : '子系列',
								parent : Ext.getCmp("new-cat").getRawValue()
							},
							success : function(response, options) {
								var text = unicodeToString(response.responseText);
								var responseArray = Ext.JSON.decode(text);
								Ext.getCmp("new-sub-cat").getStore()
										.loadData(responseArray);
							}
						});
			}, this);
			Ext.getCmp("new-dept").on('change', function() {
				Ext.getCmp("new-detail").clearValue();
				Ext.Ajax.request({
							url : 'searchItemGenerateType',
							params : {
								type : '细分',
								parent : Ext.getCmp("new-dept").getRawValue()
							},
							success : function(response, options) {
								var text = unicodeToString(response.responseText);
								var responseArray = Ext.JSON.decode(text);
								Ext.getCmp("new-detail").getStore()
										.loadData(responseArray);
							}
						});
			}, this);
			Ext.getCmp("new-year").on('change', function() {
				Ext.Ajax.request({
					url : 'getItemSerial',
					params : {
						year : Ext.getCmp("new-year").getRawValue()
					},
					success : function(response, options) {
						var text = unicodeToString(response.responseText);
						var responseArray = Ext.JSON.decode(text);
						Ext.getCmp("new-serial").setValue(responseArray.serial);
					}
				});
				checkItemCode();
			}, this);
			Ext.getCmp("new-brand").on('change', function() {
						checkItemCode();
					}, this);
			Ext.getCmp("new-season").on('change', function() {
						checkItemCode();
					}, this);
			Ext.getCmp("new-sex").on('change', function() {
						checkItemCode();
					}, this);
			Ext.getCmp("new-sub-cat").on('change', function() {
						checkItemCode();
					}, this);
			Ext.getCmp("new-detail").on('change', function() {
						checkItemCode();
					}, this);
			newItemWin = Ext.widget('window', {
				title : '信息',
				closeAction : 'hide',
				width : 800,
				height : 430,
				layout : 'fit',
				resizable : true,
				modal : true,
				items : form,
				buttons : [{
					text : "保存",
					handler : function() {
						if (!form.getForm().isValid())
							return;
						Ext.Ajax.request({
							url : 'saveItemCode',
							params : {
								brand : Ext.getCmp('new-brand').getRawValue(),
								cat : Ext.getCmp('new-cat').getRawValue(),
								subCat : Ext.getCmp('new-sub-cat')
										.getRawValue(),
								year : Ext.getCmp('new-year').getRawValue(),
								season : Ext.getCmp('new-season').getRawValue(),
								sex : Ext.getCmp('new-sex').getRawValue(),
								dept : Ext.getCmp('new-dept').getRawValue(),
								detail : Ext.getCmp('new-detail').getRawValue(),
								sel : Ext.getCmp('new-sel').getValue(),
								itemName : Ext.getCmp('new-item-name')
										.getValue(),
								brandCode : Ext.getCmp('new-brand').getValue(),
								subCatCode : Ext.getCmp('new-sub-cat')
										.getValue(),
								yearCode : Ext.getCmp('new-year').getValue(),
								seasonCode : Ext.getCmp('new-season')
										.getValue(),
								sexCode : Ext.getCmp('new-sex').getValue(),
								detailCode : Ext.getCmp('new-detail')
										.getValue()
							},
							success : function(response, options) {
								var text = unicodeToString(response.responseText);
								var responseArray = Ext.JSON.decode(text);
								if (responseArray.success) {
									form.getForm().reset();
									newItemWin.hide();
									showItemWin(responseArray.itemId);
									refreshGrid();
								} else {
									Ext.MessageBox.alert("错误",
											responseArray.message);
								}
							}
						});
					}
				}, {
					text : "取消",
					handler : function() {
						form.getForm().reset();
						newItemWin.hide();
					}
				}]
			});
		}
		newItemWin.show();
	}

	// 生成商品編碼
	function checkItemCode() {
		if (Ext.getCmp("new-brand").getValue() == null
				|| Ext.getCmp("new-sub-cat").getValue() == null
				|| Ext.getCmp("new-year").getValue() == null
				|| Ext.getCmp("new-season").getValue() == null
				|| Ext.getCmp("new-sex").getValue() == null
				|| Ext.getCmp("new-detail").getValue() == null
				|| Ext.getCmp("new-serial").getValue() == null
				|| Ext.getCmp("new-serial").getValue().length == 0)
			return;
		var itemCode = Ext.getCmp("new-brand").getValue()
				+ Ext.getCmp("new-sub-cat").getValue() + "-"
				+ Ext.getCmp("new-year").getValue()
				+ Ext.getCmp("new-season").getValue() + "-"
				+ Ext.getCmp("new-sex").getValue()
				+ Ext.getCmp("new-detail").getValue()
				+ Ext.getCmp("new-serial").getValue();
		Ext.getCmp("new-item-code").setValue(itemCode);
	}
	function showItemHandler() {
		var row = resultGrid.getSelectionModel().getSelection()[0];
		if (!row) {

			Ext.MessageBox.alert('提示', "请选择一条记录!");
			return;
		}
		showItemWin(row.get("itemId"));
	}
	// 查看Item
	var viewItemWin;
	function showItemWin(itemId) {

		if (!viewItemWin) {
			var form = Ext.create('Ext.form.Panel', {
				defaultType : 'textfield',
				bodyStyle : 'padding:5px 5px 0',
				defaults : {
					anchor : '100%'
				},
				border : false,
				layout : {
					type : 'table',
					columns : '3',
					align : 'stretch'
				},
				items : [{
							fieldLabel : '品牌',
							name : 'brand',
							disabled : true,
							id : 'view-brand'
						}, {
							fieldLabel : '系列',
							name : 'cat',
							disabled : true,
							id : 'view-cat'
						}, {
							fieldLabel : '子系列',
							name : 'subCat',
							disabled : true,
							id : 'view-sub-cat'
						}, {
							fieldLabel : '年',
							name : 'year',
							disabled : true,
							id : 'view-year'
						}, {
							fieldLabel : '季节',
							name : 'season',
							disabled : true,
							id : 'view-season'
						}, {
							xtype : 'hidden',
							fieldLabel : 'id',
							name : 'itemId',
							id : 'view-item-id'
						}, {
							fieldLabel : '性别',
							name : 'sex',
							disabled : true,
							id : 'view-sex'
						}, {
							fieldLabel : '部门',
							name : 'dept',
							disabled : true,
							id : 'view-dept'
						}, {
							fieldLabel : '细分',
							name : 'detail',
							disabled : true,
							id : 'view-detail'
						}, {
							fieldLabel : '品番',
							name : 'serial',
							disabled : true,
							id : 'view-serial'
						}, {
							fieldLabel : '样品号',
							name : 'itemCode',
							disabled : true,
							id : 'view-item-code',
							colspan : 2
						}, {
							fieldLabel : '商品名称',
							name : 'itemName',
							width : 510,
							allowBlank : false,
							id : 'view-item-name',
							colspan : 3
						}, Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '是否选用',
									displayField : 'name',
									valueField : 'value',
									store : Ext.create('Ext.data.Store', {
												model : 'combo_model',
												data : [{
															"name" : "是",
															"value" : "Y"
														}, {
															"name" : "否",
															"value" : "N"
														}]
											}),
									queryMode : 'local',
									editable : false,
									id : "view-sel",
									colspan : 3
								}), {
							fieldLabel : '设计样图',
							xtype : 'image',
							width : 300,
							height : 250,
							name : 'imageUrl',
							id : 'view-image-url',
							colspan : 3
						}, Ext.create('Ext.form.Panel', {
							id : 'image-form',
							layout : 'hbox',
							colspan : 3,
							frame : false,
							border : false,
							bodyStyle : 'background-color: transparent;',
							items : [{
										xtype : 'hidden',
										name : 'subDir',
										value : 'item/image'
									}, {
										xtype : 'hidden',
										name : 'newFileName',
										id : 'upload-item-code'
									}, {
										xtype : 'filefield',
										fieldLabel : '设计样图',
										name : 'uploadFile',
										width : 400,
										buttonText : '选择'
									}, {
										xtype : 'button',
										text : '上传',
										width : 100,
										handler : function() {
											var form = Ext.getCmp('image-form')
													.getForm();
											if (form.isValid()) {
												Ext
														.getCmp("upload-item-code")
														.setValue(Ext
																.getCmp("view-item-code")
																.getValue());
												form.submit({
													method : "POST",
													url : 'singleFileUploadAction',
													waitMsg : '文件正在上传...',
													success : function(form,
															action) {
														Ext
																.getCmp("view-image-url")
																.setSrc(action.result.serverUrl
																		+ action.result.fileUrl);
														Ext.Ajax.request({
															url : 'saveItemImage',
															params : {
																itemId : Ext
																		.getCmp('view-item-id')
																		.getValue(),
																imageUrl : action.result.fileUrl
															},
															success : function(
																	response,
																	options) {
																var text = unicodeToString(response.responseText);
																var responseArray = Ext.JSON
																		.decode(text);
																if (responseArray.success) {
																} else {
																	Ext.MessageBox
																			.alert(
																					"错误",
																					responseArray.message);
																}
															}
														});
													},
													failure : function(form,
															action) {
														Ext.MessageBox
																.alert(
																		'错误',
																		action.result.message);
													}
												});
											}
										}
									}]
						})]
			});
			viewItemWin = Ext.widget('window', {
				title : '信息',
				closeAction : 'hide',
				width : 800,
				height : 530,
				layout : 'fit',
				resizable : true,
				modal : true,
				items : form,
				buttons : [{
					text : "保存",
					handler : function() {
						if (!form.getForm().isValid())
							return;
						Ext.Ajax.request({
							url : 'saveItemCode',
							params : {
								itemId : Ext.getCmp('view-item-id').getValue(),
								sel : Ext.getCmp('view-sel').getValue(),
								itemName : Ext.getCmp('view-item-name')
										.getValue()
							},
							success : function(response, options) {
								var text = unicodeToString(response.responseText);
								var responseArray = Ext.JSON.decode(text);
								if (responseArray.success) {
									Ext.MessageBox.alert('提示', "处理成功!");
									form.getForm().reset();
									viewItemWin.hide();
									refreshGrid();
								} else {
									Ext.MessageBox.alert("错误",
											responseArray.message);
								}
							}
						});
					}
				}, {
					text : "取消",
					handler : function() {
						form.getForm().reset();
						viewItemWin.hide();
					}
				}]
			});
		}
		Ext.Ajax.request({
			url : 'getItemCodeInfo',
			params : {
				itemId : itemId
			},
			success : function(response, options) {
				var text = unicodeToString(response.responseText);
				var responseArray = Ext.JSON.decode(text);
				if (responseArray.success) {
					Ext.getCmp("view-item-id").setValue(itemId);
					Ext.getCmp("view-brand").setValue(responseArray.brand);
					Ext.getCmp("view-cat").setValue(responseArray.cat);
					Ext.getCmp("view-sub-cat").setValue(responseArray.subCat);
					Ext.getCmp("view-year").setValue(responseArray.year);
					Ext.getCmp("view-season").setValue(responseArray.season);
					Ext.getCmp("view-sex").setValue(responseArray.sex);
					Ext.getCmp("view-dept").setValue(responseArray.dept);
					Ext.getCmp("view-detail").setValue(responseArray.detail);
					Ext.getCmp("view-serial").setValue(responseArray.serial);
					Ext.getCmp("view-item-code")
							.setValue(responseArray.itemCode);
					Ext.getCmp("view-item-name")
							.setValue(responseArray.itemName);
					Ext.getCmp("view-sel").setValue(responseArray.sel);
					Ext.getCmp("view-image-url").setSrc(responseArray.imageUrl);
				} else {
					Ext.MessageBox.alert("错误", responseArray.message);
				}
			}
		});
		viewItemWin.show();
	}

});