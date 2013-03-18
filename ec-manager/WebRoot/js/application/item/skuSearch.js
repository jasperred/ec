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

	var searchPanel = Ext.create('Ext.form.Panel', {
				id : 'search-condition-panel',
				title : '查询条件',
				region : 'north',
				frame : true, // 设置窗体为圆角
				method : "POST",
				url : 'skuSearchAction',
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
							fieldLabel : '商品编码',
							margin : '5 5 5 10',
							name : 'itemCd',
							id : "s-item-cd"
						}, {
							xtype : 'textfield',
							fieldLabel : 'SKU编码',
							name : 'skuCd',
							margin : '5 5 5 10',
							id : "s-sku-cd"
						}, {
							xtype : 'textfield',
							fieldLabel : '商品名称',
							name : 'itemName',
							margin : '5 5 5 10',
							id : "s-item-name"
						}, {
							xtype : 'fieldcontainer',
							fieldLabel : '商品价格',
							combineErrors : true,
							msgTarget : 'side',
							colspan : 3,
							layout : 'hbox',
							width : 380,
							margin : "5 5 5 10",
							defaults : {
								flex : 1,
								hideLabel : true
							},
							items : [{
										xtype : 'numberfield',
										name : 'fromPrice',
										id : 's-from-price',
										fieldLabel : '',
										margin : '0 5 0 0'
									}, {
										xtype : 'numberfield',
										name : 'endPrice',
										id : 's-end-price',
										fieldLabel : '-'
									}]
						}],
				buttons : [ {
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
				fields : ["ItemCd","SkuCd","ItemName","SkuProp1","SkuProp2","SkuProp3","SkuPrice1"],
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
							url : 'skuSearchAction',
							reader : {
								root : 'resultList',
								totalProperty : 'rowCount',
								fields : ["ItemCd","SkuCd","ItemName","SkuProp1","SkuProp2","SkuProp3","SkuPrice1"]
							},
							// sends single sort as multi parameter
							simpleSortMode : true
						})
			});
	var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
				clicksToEdit : 1
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
							header : "商品编码",
							dataIndex : 'ItemCd',
							editor : {
								xtype : 'textfield'
							},
							width : 80,
							sortable : false
						}, {
							header : "SKU编码",
							dataIndex : 'SkuCd',
							editor : {
								xtype : 'textfield'
							},
							width : 100,
							sortable : true
						}, {
							header : "商品名称",
							dataIndex : 'ItemName',
							editor : {
								xtype : 'textfield'
							},
							width : 300,
							flex : 1,
							sortable : true
						}, {
							header : "属性1",
							dataIndex : 'SkuProp1',
							width : 80,
							align : 'right',
							sortable : true
						}, {
							header : "属性2",
							dataIndex : 'SkuProp2',
							width : 80,
							sortable : true
						}, {
							header : "属性3",
							dataIndex : 'SkuProp3',
							width : 80,
							sortable : true
						}, {
							header : "价格",
							dataIndex : 'SkuPrice1',
							width : 80,
							sortable : true
						}],
				// paging bar on the bottom
				bbar : Ext.create('Ext.PagingToolbar', {
							store : store,
							displayInfo : true,
							displayMsg : '显示 {0} - {1}/共{2}条',
							emptyMsg : "没有查询结果"
						}),
				renderTo : 'result-grid',
				plugins : [cellEditing]
			});
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
						itemCd : Ext.getCmp("s-item-cd").getValue(),
						skuCd : Ext.getCmp("s-sku-cd").getValue(),
						itemName : Ext.getCmp("s-item-name").getValue(),
						fromPrice : Ext.getCmp("s-from-price").getValue(),
						endPrice : Ext.getCmp("s-end-price").getValue()
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
						itemCd : Ext.getCmp("s-item-cd").getValue(),
						skuCd : Ext.getCmp("s-sku-cd").getValue(),
						itemName : Ext.getCmp("s-item-name").getValue(),
						fromPrice : Ext.getCmp("s-from-price").getValue(),
						endPrice : Ext.getCmp("s-end-price").getValue()
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
	

});