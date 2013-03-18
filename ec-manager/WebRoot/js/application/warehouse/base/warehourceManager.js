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
						bodyPadding : 10,
						defaultType : 'textfield',
						fieldDefaults : {
							msgTarget : 'side',
							labelAlign : 'left'
						},
						items : [{
									xtype : 'textfield',
									fieldLabel : '商品名称',
									margin : '5 5 5 10',
									id : "s-item-name"
								}, {
									xtype : 'textfield',
									fieldLabel : '商品编码',
									margin : '5 5 5 10',
									id : "s-item-cd"
								}, {
									xtype : 'textfield',
									fieldLabel : 'SKU编码',
									margin : '5 5 5 10',
									id : "s-sku-cd2"
								},{
									xtype : 'textfield',
									fieldLabel : '商品名称',
									margin : '5 5 5 10',
									id : "s-item-name2"
								}, {
									xtype : 'textfield',
									fieldLabel : '商品编码',
									margin : '5 5 5 10',
									id : "s-item-cd2"
								}, {
									xtype : 'textfield',
									fieldLabel : 'SKU编码',
									margin : '5 5 5 10',
									id : "s-sku-cd"
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
			Ext.define('SearchModel', {
						extend : 'Ext.data.Model',
						fields : ['orderHeadId', 'orderNo', 'orderStatus',
								'origOrderNo', 'origOrderStatus', {
									name : 'requestDate',
									type : 'date',
									dateFormat : 'Y-m-d H:i:s'
								}, 'shipNo', {
									name : 'storeId',
									type : 'int'
								}, {
									name : 'orderAmt',
									type : 'float'
								}, 'orderStatusName', 'storeName', 'buyerNick',
								'origOrderStatusName', {
									name : 'payment',
									type : 'float'
								}, {
									name : 'totalFee',
									type : 'float'
								}, {
									name : 'postFee',
									type : 'float'
								}, {
									name : 'discount',
									type : 'float'
								}, 'itemCd', 'skuCd', 'itemName', {
									name : 'itemPrice',
									type : 'float'
								}, {
									name : 'itemQty',
									type : 'int'
								}]
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
									url : 'orderItemSearchAction',
									reader : {
										root : 'resultList',
										totalProperty : 'rowCount',
										fields : ['orderHeadId', 'orderNo',
												'orderStatus', 'origOrderNo',
												'origOrderStatus',
												'requestDate', 'shipNo',
												'storeId', 'orderAmt',
												'orderStatusName', 'storeName',
												'buyerNick',
												'origOrderStatusName',
												'payment', 'totalFee',
												'postFee', 'discount',
												'itemCd', 'skuCd', 'itemName',
												'itemPrice', 'itemQty']
									},
									// sends single sort as multi parameter
									simpleSortMode : true
								})
					});
			var gridHeight = getWindownHeight()-searchPanel.getHeight( );
			var resultGrid = Ext.create('Ext.grid.Panel', {
						title : '查询结果',
						region : 'center',
						store : store,
						height : gridHeight,
						multiSelect : false,
						// grid columns
						columns : [{
									xtype : 'rownumberer'
								}, {
									header : "商品名称",
									dataIndex : 'itemName',
									width : 150,
									sortable : false
								}, {
									header : "价格",
									dataIndex : 'itemPrice',
									width : 150,
									sortable : false
								}, {
									header : "数量",
									dataIndex : 'itemQty',
									width : 150,
									sortable : false
								}, {
									header : "支付金额",
									dataIndex : 'payment',
									width : 150,
									sortable : false
								}, {
									header : "运费",
									dataIndex : 'postFee',
									width : 150,
									sortable : false
								}, {
									header : "商品编码",
									dataIndex : 'itemCd',
									width : 150,
									sortable : false
								}, {
									header : "SKU编码",
									dataIndex : 'skuCd',
									width : 150,
									sortable : false
								}, {
									header : "订单号",
									dataIndex : 'orderNo',
									width : 150,
									sortable : false
								}, {
									header : "原订单号",
									dataIndex : 'origOrderNo',
									width : 150,
									sortable : true
								}, {
									header : "买家",
									dataIndex : 'buyerNick',
									width : 100,
									align : 'right',
									sortable : true
								}, {
									header : "订单时间",
									dataIndex : 'requestDate',
									renderer : Ext.util.Format
											.dateRenderer('Y-m-d H:i:s'),
									width : 120,
									sortable : true
								}, {
									header : "付款时间",
									dataIndex : 'payTime',
									renderer : Ext.util.Format
											.dateRenderer('Y-m-d H:i:s'),
									width : 120,
									sortable : true
								}, {
									header : "原订单状态",
									dataIndex : 'origOrderStatusName',
									width : 100,
									sortable : true
								}, {
									header : "系统状态",
									dataIndex : 'orderStatusName',
									width : 100,
									sortable : true
								}, {
									header : "店铺",
									dataIndex : 'storeName',
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

		});
function searchHandler() {

}