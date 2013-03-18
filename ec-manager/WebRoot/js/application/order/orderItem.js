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
							url : 'companyStoreList'
						}
					});
			var orderpropsstore = Ext.create('Ext.data.Store', {
						model : 'combo_model',
						data : [{
									"name" : '系统单号',
									"value" : 'ORDER_NO'
								}, {
									"name" : '原订单号',
									"value" : 'TB_ORDER_NO'
								}, {
									"name" : '快递单号',
									"value" : 'TB_SHIP_NO'
								}, {
									"name" : '买家昵称',
									"value" : 'BUYER_NICK'
								}, {
									"name" : '收货人',
									"value" : 'RECEIVER_NAME'
								}, {
									"name" : '联系电话',
									"value" : 'RECEIVER_TEL'
								}, {
									"name" : '支付宝',
									"value" : 'BUYER_ALIPAY_NO'
								}]
					});

			var erpstatusstore = Ext.create('Ext.data.Store', {
						model : 'combo_model',
						remoteSort : true,
						proxy : {
							type : 'ajax',
							url : 'erpStatusList'
						}
					});

			var tbstatusstore = Ext.create('Ext.data.Store', {
						model : 'combo_model',
						remoteSort : true,
						proxy : {
							type : 'ajax',
							url : 'tbStatusList'
						}
					});
			var receivestatusstore = Ext.create('Ext.data.Store', {
						model : 'combo_model',
						remoteSort : true,
						proxy : {
							type : 'ajax',
							url : 'receiveStatusList'
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
						bodyPadding : 10,
						defaultType : 'textfield',
						fieldDefaults : {
							msgTarget : 'side',
							labelAlign : 'left'
						},
						items : [
								Ext.create('Ext.form.field.ComboBox', {
											fieldLabel : '店铺',
											displayField : 'name',
											valueField : 'value',
											store : shopstore,
											queryMode : 'remote',
											editable : false,
											name : "storeId",
											id : "s-store-id",
											margin : "5 5 5 10"
										}),
								Ext.create('Ext.form.field.ComboBox', {
											fieldLabel : '原订单状态',
											displayField : 'name',
											valueField : 'value',
											store : tbstatusstore,
											queryMode : 'remote',
											editable : false,
											name : "tbStatus",
											id : "s-tb-status",
											margin : "5 5 5 10"
										}),
								{
									xtype : 'fieldcontainer',
									fieldLabel : '订单属性',
									combineErrors : true,
									msgTarget : 'side',
									width : 380,
									layout : 'hbox',
									margin : "5 5 5 10",
									defaults : {
										flex : 1,
										hideLabel : true
									},
									items : [
											{
												xtype : 'textfield',
												name : 'orderPropsValue',
												fieldLabel : '属性值',
												margin : '0 5 0 0',
												id : "s-order-props-value"
											},
											Ext.create(
													'Ext.form.field.ComboBox',
													{
														displayField : 'name',
														valueField : 'value',
														store : orderpropsstore,
														queryMode : 'local',
														editable : false,
														name : "orderPropsName",
														id : "s-order-props-name"
													})]
								}, Ext.create('Ext.form.field.ComboBox', {
											fieldLabel : '保留原因',
											displayField : 'name',
											valueField : 'value',
											store : receivestatusstore,
											queryMode : 'remote',
											editable : false,
											name : "receiveMessage",
											id : "s-receive-message",
											margin : "5 5 5 10"
										}),
								Ext.create('Ext.form.field.ComboBox', {
											fieldLabel : '系统状态',
											displayField : 'name',
											valueField : 'value',
											store : erpstatusstore,
											queryMode : 'remote',
											editable : false,
											name : "erpStatus",
											id : "s-erp-status",
											margin : "5 5 5 10"
										}), {
									xtype : 'fieldcontainer',
									fieldLabel : '订单时间',
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
												name : 'orderFromDate',
												id : 's-order-from-date',
												fieldLabel : '开始时间',
												margin : '0 5 0 0'
											}, {
												xtype : 'datefield',
												name : 'orderEndDate',
												id : 's-order-end-date',
												fieldLabel : '结束时间'
											}]
								}, {
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
			// 查询结果grid

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
												'origOrderStatusName', 'payment',
												'totalFee', 'postFee',
												'discount', 'itemCd', 'skuCd',
												'itemName', 'itemPrice',
												'itemQty']
									},
									// sends single sort as multi parameter
									simpleSortMode : true
								})
					});
			var gridHeight = getWindownHeight()-searchPanel.getHeight( );
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
			// trigger the data store load
//			store.load({
//						params : {
//							start : 0,
//							limit : pageSize
//						}
//					});

			// 查询用户
			function searchHandler() {
				store.on('beforeload', function() { // =======翻页时 查询条件
							var new_params = {
								limit : pageSize,
								storeId : Ext.getCmp("s-store-id").getValue(),
								orderPropsValue : Ext
										.getCmp("s-order-props-value")
										.getValue(),
								orderPropsName : Ext
										.getCmp("s-order-props-name")
										.getValue(),
								tbStatus : Ext.getCmp("s-tb-status").getValue(),
								erpStatus : Ext.getCmp("s-erp-status")
										.getValue(),
								receiveMessage : Ext
										.getCmp("s-receive-message").getValue(),
								orderFromDate : Ext.getCmp("s-order-from-date")
										.getValue(),
								orderEndDate : Ext.getCmp("s-order-end-date")
										.getValue(),
								itemCd : Ext.getCmp("s-item-cd").getValue(),
								skuCd : Ext.getCmp("s-sku-cd").getValue(),
								itemName : Ext.getCmp("s-item-name").getValue()
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
		});