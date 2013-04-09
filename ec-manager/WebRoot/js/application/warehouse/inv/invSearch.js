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
	var shopstore = Ext.create('Ext.data.Store', {
				model : 'combo_model',
				remoteSort : true,
				proxy : {
					type : 'ajax',
					url : 'warehouseListByCompany'
				}
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
				items : [Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '仓库',
									displayField : 'name',
									valueField : 'value',
									store : shopstore,
									queryMode : 'remote',
									editable : false,
									name : "whId",
									id : "s-wh-id",
									margin : "5 5 5 10"
								}), {
							fieldLabel : "商品编号", // 标签内容
							name : "itemCd",
							id : "s-item-cd",
							margin : "5 5 5 10"
						}, {
							fieldLabel : "SKU", // 标签内容
							name : "skuCd",
							id : "s-sku-cd",
							margin : "5 5 5 10"
						}, Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '库存类型',
									displayField : 'name',
									valueField : 'value',
									store : Ext.create('Ext.data.Store', {
												model : 'combo_model',
												data : [{
															"name" : '全部',
															"value" : ''
														}, {
															"name" : 'A',
															"value" : 'A'
														}, {
															"name" : 'B',
															"value" : 'B'
														}, {
															"name" : 'C',
															"value" : 'C'
														}]
											}),
									queryMode : 'local',
									editable : false,
									name : "invType",
									id : "s-inv-type",
									margin : "5 5 5 10"
								})],
				buttons : [{
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

	var gridHeight = getWindownHeight() - searchPanel.getHeight();
	Ext.define('SearchModel', {
				extend : 'Ext.data.Model',
				fields : ['WhId', 'WhName', 'ItemCd', 'ItemName', 'SkuCd',
						'SkuProp1', 'SkuProp2', 'InvType', 'InvStatus',
						'Quantity', 'AvailableQuantity'],
				idProperty : 'SkuCd'
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
							url : 'searchInv',
							reader : {
								root : 'resultList',
								totalProperty : 'rowCount',
								fields : ['WhId', 'WhName', 'ItemCd',
										'ItemName', 'SkuCd', 'SkuProp1',
										'SkuProp2', 'InvType', 'InvStatus',
										'Quantity', 'AvailableQuantity']
							},
							// sends single sort as multi parameter
							simpleSortMode : true
						})
			});
	var pluginExpanded = true;
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
							header : "仓库",
							dataIndex : 'WhName',
							width : 150,
							flex : 1,
							sortable : false
						}, {
							header : "商品编码",
							dataIndex : 'ItemCd',
							width : 150,
							sortable : true
						}, {
							header : "商品名称",
							dataIndex : 'ItemName',
							width : 150,
							sortable : true
						}, {
							header : "SKU",
							dataIndex : 'SkuCd',
							width : 100,
							sortable : true
						}, {
							header : "属性1",
							dataIndex : 'SkuProp1',
							width : 120,
							sortable : true
						}, {
							header : "属性2",
							dataIndex : 'SkuProp2',
							width : 120,
							sortable : true
						}, {
							header : "库存类型",
							dataIndex : 'InvType',
							width : 100,
							sortable : true
						}, {
							header : "库存状态",
							dataIndex : 'InvStatus',
							width : 100,
							sortable : true
						}, {
							header : "库存数量",
							dataIndex : 'Quantity',
							align : 'right',
							width : 60,
							sortable : true
						}, {
							header : "可用数量",
							dataIndex : 'AvailableQuantity',
							align : 'right',
							width : 60,
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

	// 查询
	function searchHandler() {
		store.on('beforeload', function() { // =======翻页时 查询条件
					var new_params = {
						limit : pageSize,
						whId : Ext.getCmp("s-wh-id").getValue(),
						itemCd : Ext.getCmp("s-item-cd").getValue(),
						skuCd : Ext.getCmp("s-sku-cd").getValue(),
						invType : Ext.getCmp("s-inv-type").getValue()
					};
					Ext.apply(store.proxy.extraParams, new_params);
				});
		store.loadPage(1);
	}
	// 导出CSV
	function exportHandler() {
		Ext.Msg.wait('处理中，请稍后...', '提示');
		Ext.Ajax.request({
					url : 'exportOrder',
					params : {
						limit : 100000,
						whId : Ext.getCmp("s-wh-id").getValue(),
						itemCd : Ext.getCmp("s-item-cd").getValue(),
						skuCd : Ext.getCmp("s-sku-cd").getValue(),
						invType : Ext.getCmp("s-inv-type").getValue()
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

});