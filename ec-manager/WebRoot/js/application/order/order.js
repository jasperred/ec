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
	var retain_store = Ext.create('Ext.data.Store', {
				fields : ['name', 'value'],
				remoteSort : true,
				proxy : {
					type : 'ajax',
					url : 'receiveStatusList'
				}
			});
	retain_store.load();
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
				defaultType : 'textfield',
				fieldDefaults : {
					msgTarget : 'side',
					labelAlign : 'left'
				},
				items : [Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '店铺',
									displayField : 'name',
									valueField : 'value',
									store : shopstore,
									queryMode : 'remote',
									editable : false,
									name : "storeId",
									id : "s-store-id",
									margin : "5 5 5 10"
								}), Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '原订单状态',
									displayField : 'name',
									valueField : 'value',
									store : tbstatusstore,
									queryMode : 'remote',
									editable : false,
									name : "tbStatus",
									id : "s-tb-status",
									margin : "5 5 5 10"
								}), {
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
							items : [{
										xtype : 'textfield',
										name : 'orderPropsValue',
										fieldLabel : '属性值',
										margin : '0 5 0 0',
										id : "s-order-props-value"
									}, Ext.create('Ext.form.field.ComboBox', {
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
								}), Ext.create('Ext.form.field.ComboBox', {
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
							xtype : 'displayfield'
						}, {
							xtype : 'displayfield'
						}, {
							xtype : 'fieldcontainer',
							fieldLabel : '发货时间',
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
										name : 'deliveryFromDate',
										id : 's-delivery-from-date',
										fieldLabel : '开始时间',
										margin : '0 5 0 0'
									}, {
										xtype : 'datefield',
										name : 'deliveryEndDate',
										id : 's-delivery-end-date',
										fieldLabel : '结束时间'
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
				fields : [{
							name : 'check',
							type : 'bool'
						}, 'orderHeadId', 'orderNo', 'orderStatus',
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
							name : 'payTime',
							type : 'date',
							dateFormat : 'Y-m-d H:i:s'
						}, {
							name : 'paymentAmt',
							type : 'float'
						}, {
							name : 'freightAmt',
							type : 'float'
						}, {
							name : 'actualTotalAmt',
							type : 'float'
						}],
				idProperty : 'orderHeadId'
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
			url : 'searchOrder',
			reader : {
				root : 'resultList',
				totalProperty : 'rowCount',
				fields : ['check', 'orderHeadId', 'orderNo', 'orderStatus',
						'origOrderNo', 'origOrderStatus', 'requestDate',
						'shipNo', 'storeId', 'orderAmt', 'orderStatusName',
						'storeName', 'buyerNick', 'origOrderStatusName',
						'payTime', 'paymentAmt', 'freightAmt', 'actualTotalAmt']
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
							xtype : 'checkcolumn',
							dataIndex : 'check',
							width : 30
						}, {
							header : "订单号",
							dataIndex : 'orderNo',
							width : 150,
							flex : 1,
							sortable : false
						}, {
							header : "原订单号",
							dataIndex : 'origOrderNo',
							width : 150,
							sortable : true
						}, {
							header : "运单号",
							dataIndex : 'shipNo',
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
						}, {
							header : "金额",
							dataIndex : 'actualTotalAmt',
							renderer : Ext.util.Format.numberRenderer('00.00'),
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
	resultGrid.child('pagingtoolbar').add(['->', {
				iconCls : 'icon-add',
				text : '全选',
				scope : this,
				handler : selectCheckHandler
			}, {
				iconCls : 'icon-add',
				text : '取消',
				scope : this,
				handler : cancelSelectCheckHandler
			}, {
				iconCls : 'icon-add',
				text : '审核',
				scope : this,
				handler : auditedOrderHandler
			}, {
				iconCls : 'icon-delete',
				text : '保留',
				scope : this,
				handler : receiveOrderHandler
			}, {
				iconCls : 'icon-delete',
				text : '查看',
				scope : this,
				handler : showOrderHandler
			}]);
	resultGrid.addListener('itemdblclick', showOrderHandler, this);
	// trigger the data store load
//	store.load({
//				params : {
//					start : 0,
//					limit : pageSize
//				}
//			});
	// 全部选择/取消
	function selectCheckHandler() {
		var count = resultGrid.getStore().getCount();
		for (i = 0; i < count; i++) {
			var row = resultGrid.getStore().getAt(i);
			if (row.get("orderStatus") == 'AUDITED'
					|| (row.get('orderStatus') == 'START' && row
							.get('origOrderStatus') == 'WAIT_SELLER_SEND_GOODS')) {
				row.set('check', true);
			} else {
				row.set('check', false);
			}
			row.endEdit(true);
		}
	}
	function cancelSelectCheckHandler() {
		var count = resultGrid.getStore().getCount();
		for (i = 0; i < count; i++) {
			var row = resultGrid.getStore().getAt(i);
			row.set('check', false);
			row.endEdit(true);
		}
	}
	// 查询
	function searchHandler() {
		store.on('beforeload', function() { // =======翻页时 查询条件
					var new_params = {
						limit : pageSize,
						storeId : Ext.getCmp("s-store-id").getValue(),
						orderPropsValue : Ext.getCmp("s-order-props-value")
								.getValue(),
						orderPropsName : Ext.getCmp("s-order-props-name")
								.getValue(),
						tbStatus : Ext.getCmp("s-tb-status").getValue(),
						erpStatus : Ext.getCmp("s-erp-status").getValue(),
						receiveMessage : Ext.getCmp("s-receive-message")
								.getValue(),
						orderFromDate : Ext.getCmp("s-order-from-date")
								.getValue(),
						orderEndDate : Ext.getCmp("s-order-end-date")
								.getValue(),
						deliveryFromDate : Ext.getCmp("s-delivery-from-date")
								.getValue(),
						deliveryEndDate : Ext.getCmp("s-delivery-end-date")
								.getValue()
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
						storeId : Ext.getCmp("s-store-id").getValue(),
						orderPropsValue : Ext.getCmp("s-order-props-value")
								.getValue(),
						orderPropsName : Ext.getCmp("s-order-props-name")
								.getValue(),
						tbStatus : Ext.getCmp("s-tb-status").getValue(),
						erpStatus : Ext.getCmp("s-erp-status").getValue(),
						receiveMessage : Ext.getCmp("s-receive-message")
								.getValue(),
						orderFromDate : Ext.getCmp("s-order-from-date")
								.getValue(),
						orderEndDate : Ext.getCmp("s-order-end-date")
								.getValue(),
						deliveryFromDate : Ext.getCmp("s-delivery-from-date")
								.getValue(),
						deliveryEndDate : Ext.getCmp("s-delivery-end-date")
								.getValue()
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
	// 审核订单
	function auditedOrderHandler() {
		var count = resultGrid.getStore().getCount();
		var orderHeadIds = '';
		for (i = 0; i < count; i++) {
			var row = resultGrid.getStore().getAt(i);
			if (row.get('check') && row.get('orderStatus') == 'START'
					&& (row.get('origOrderStatus') == 'WAIT_SELLER_SEND_GOODS'||row.get('origOrderNo')==null)) {
				if (orderHeadIds.length > 0)
					orderHeadIds += ',';
				orderHeadIds += row.get('orderHeadId');
			}
		}
		if (orderHeadIds.length == 0) {
			Ext.MessageBox.alert('提示', "请选择[未审核]状态的订单!");
			return;
		}
		Ext.Ajax.request({
					url : 'ordersAuditedAction',
					params : {
						crumb : Ext.get('crumb').getValue(),
						orderHeadIds : orderHeadIds
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
	}
	// 预留订单
	var receiveWin;// 选择原因
	function receiveOrderHandler() {
		var count = resultGrid.getStore().getCount();
		var orderHeadIds = '';
		for (i = 0; i < count; i++) {
			var row = resultGrid.getStore().getAt(i);
			if (row.get('check') && row.get('orderStatus') == 'AUDITED') {
				if (orderHeadIds.length > 0)
					orderHeadIds += ',';
				orderHeadIds += row.get('orderHeadId');
			}
		}
		if (orderHeadIds.length == 0) {
			Ext.MessageBox.alert('提示', "请选择[已审核]状态的订单!");
			return;
		}
		if (!receiveWin) {
			receiveWin = Ext.widget('window', {
				title : '请选择保留原因',
				closeAction : 'hide',
				width : 300,
				height : 60,
				layout : 'fit',
				resizable : true,
				modal : true,
				items : [Ext.create('Ext.form.field.ComboBox', {
							fieldLabel : '保留原因',
							displayField : 'name',
							valueField : 'value',
							store : retain_store,
							queryMode : 'remote',
							editable : false,
							value : "",
							id : "order-receive-status",
							margin : "5 5 5 5"
						})],
				buttons : [{
					text : "保留订单",
					handler : function() {
						var receiveMessage = Ext.getCmp('order-receive-status')
								.getValue();
						if (receiveMessage.length == 0) {
							Ext.MessageBox.alert('提示', "请选择保留原因!");
							return;
						}
						Ext.Ajax.request({
							url : 'ordersReceiveAction',
							params : {
								crumb : Ext.get('crumb').getValue(),
								orderHeadIds : orderHeadIds,
								receiveMessage : receiveMessage
							},
							success : function(response, options) {
								var text = unicodeToString(response.responseText);
								var responseArray = Ext.JSON.decode(text);
								if (responseArray.success) {
									Ext.MessageBox.alert('提示', "处理成功!");
									receiveWin.hide();
									refreshGrid();
								} else {
									Ext.MessageBox.alert("错误",
											responseArray.message);
								}
							}
						});
					},
					id : 'recover-order-button'
				}, {
					text : "取消",
					handler : function() {
						Ext.getCmp('order-receive-status').setValue('');
						receiveWin.hide();
					}
				}]
			});
		}
		receiveWin.show();

	}
	// 显示订单
	function showOrderHandler() {
		var row = resultGrid.getSelectionModel().getSelection()[0];
		if (!row) {

			Ext.MessageBox.alert('提示', "请选择一条记录!");
			return;
		}
		showOrderInfoForm(row.get('orderHeadId'));
	}
	var winNew;
	var orderHeadId;
	// 订单信息窗口
	function showOrderInfoForm(orderHeadId) {
		if (!winNew) {
			var orderinfo = Ext.create('Ext.form.Panel', {
						title : "订单内容",
						layout : {
							type : 'table',
							columns : 3
						},
						animCollapse : true,
						bodyPadding : 2,
						collapsible : true,
						items : [{
									xtype : 'textfield',
									readOnly : true,
									fieldLabel : '订单编号',
									margin : '2 5 2 0',
									id : "order-no"
								}, {
									xtype : 'textfield',
									readOnly : true,
									fieldLabel : '原订单编号',
									margin : '2 5 2 0',
									id : "orig-order-no"
								}, {
									xtype : 'textfield',
									readOnly : true,
									fieldLabel : '店铺',
									margin : '2 5 2 0',
									id : "seller-nick"
								}, {
									xtype : 'textfield',
									readOnly : true,
									fieldLabel : '订单时间',
									format : 'Y-m-d H:m:s',
									margin : '2 5 2 0',
									id : "order-date"
								}, {
									xtype : 'textfield',
									readOnly : true,
									fieldLabel : '支付时间',
									format : 'Y-m-d H:m:s',
									margin : '2 5 2 0',
									id : "pay-time"
								}, {
									xtype : 'textfield',
									readOnly : true,
									fieldLabel : '发货时间',
									format : 'Y-m-d H:m:s',
									margin : '2 5 2 0',
									id : "delivery-date"
								}, {
									xtype : 'textfield',
									readOnly : true,
									fieldLabel : '订单状态',
									margin : '2 5 2 0',
									id : "order-status"
								}, {
									xtype : 'textfield',
									readOnly : true,
									fieldLabel : '原订单状态',
									margin : '2 5 2 0',
									id : "orig-order-status"
								}, Ext.create('Ext.form.field.ComboBox', {
											fieldLabel : '保留原因',
											displayField : 'name',
											valueField : 'value',
											store : retain_store,
											queryMode : 'remote',
											editable : false,
											value : "",
											id : "order-retain",
											margin : "2 5 2 0"
										}), {
									xtype : 'textfield',
									readOnly : true,
									fieldLabel : '会员名',
									margin : '2 5 2 0',
									id : "buyer-nick"
								}, {
									xtype : 'textfield',
									readOnly : true,
									fieldLabel : '邮件',
									margin : '2 5 2 0',
									colspan : 2,
									id : "buyer-email"
								}, {
									xtype : 'textfield',
									readOnly : true,
									fieldLabel : '总金额',
									margin : '2 5 2 0',
									id : "order-amt"
								}, {
									xtype : 'textfield',
									readOnly : true,
									fieldLabel : '货款',
									margin : '2 5 2 0',
									id : "total-fee"
								}, {
									xtype : 'textfield',
									readOnly : true,
									fieldLabel : '运费',
									margin : '2 5 2 0',
									id : "post-fee"
								}, {
									xtype : 'textfield',
									readOnly : true,
									fieldLabel : '实付金额',
									margin : '2 5 2 0',
									id : "payment"
								}, {
									xtype : 'textfield',
									readOnly : true,
									fieldLabel : '实付积分',
									margin : '2 5 2 0',
									colspan : 1,
									id : "payment-point"
								}, {
									xtype : 'textfield',
									readOnly : true,
									fieldLabel : '赠送积分',
									margin : '2 5 2 0',
									colspan : 1,
									id : "obtain-point"
								}, {
									xtype : 'textfield',
									readOnly : true,
									fieldLabel : '运输方式',
									margin : '2 5 2 0',
									id : "post-method"
								}, {
									xtype : 'textfield',
									readOnly : true,
									fieldLabel : '快递公司',
									margin : '2 5 2 0',
									id : "post-company"
								}, {
									xtype : 'textfield',
									readOnly : true,
									fieldLabel : '快递单号',
									margin : '2 5 2 0',
									id : "post-no"
								}, {
									xtype : 'textarea',
									readOnly : true,
									fieldLabel : '买家留言',
									margin : '2 5 2 0',
									width : 775,
									colspan : 3,
									id : "buyer-memo"
								}, {
									xtype : 'textarea',
									readOnly : true,
									fieldLabel : '订单备注',
									margin : '2 5 2 0',
									width : 775,
									colspan : 3,
									id : "seller-memo"
								}, {
									xtype : 'textarea',

									fieldLabel : '客服留言',
									margin : '2 5 2 0',
									width : 775,
									colspan : 3,
									id : "cust-memo"
								}, {
									xtype : 'hidden',
									margin : '2 5 2 0'
								}, {
									xtype : 'hidden',
									margin : '2 5 2 0'
								}, {
									xtype : 'button',
									text : "保存客服留言",
									margin : '2 5 2 0',
									handler : saveCustMemo,
									id : 'save-cust-memo-button'
								}]
					});
			var orderaddress = Ext.create('Ext.form.Panel', {
						title : "地址",
						layout : {
							type : 'table',
							columns : 3
						},
						animCollapse : true,
						collapsible : true,
						bodyPadding : 2,
						items : [
								Ext.create('Ext.form.field.ComboBox', {
											fieldLabel : '省份',
											displayField : 'name',
											valueField : 'value',
											store : Ext.create(
													'Ext.data.Store', {
														fields : ['name',
																'value'],
														data : [{
																	"name" : "请选择省份",
																	"value" : ""
																}]
													}),
											queryMode : 'local',
											editable : false,
											value : "",
											name : "state",
											id : "receiver-state",
											margin : "2 5 2 0"
										}),
								Ext.create('Ext.form.field.ComboBox', {
											fieldLabel : '城市',
											displayField : 'name',
											valueField : 'value',
											store : Ext.create(
													'Ext.data.Store', {
														fields : ['name',
																'value'],
														data : [{
																	"name" : "请选择城市",
																	"value" : ""
																}]
													}),
											queryMode : 'local',
											editable : false,
											value : "",
											name : "city",
											id : "receiver-city",
											margin : "2 5 2 0"
										}),
								Ext.create('Ext.form.field.ComboBox', {
											fieldLabel : '地区',
											displayField : 'name',
											valueField : 'value',
											store : Ext.create(
													'Ext.data.Store', {
														fields : ['name',
																'value'],
														data : [{
																	"name" : "请选择地区",
																	"value" : ""
																}]
													}),
											queryMode : 'local',
											editable : false,
											value : "",
											name : "district",
											id : "receiver-district",
											margin : "2 5 2 0"
										}), {
									xtype : 'textfield',

									fieldLabel : '详细地址',
									width : 515,
									margin : '2 5 2 0',
									colspan : 2,
									id : "receiver-address"
								}, {
									xtype : 'textfield',

									fieldLabel : '邮编',
									margin : '2 5 2 0',
									id : "receiver-zip"
								}, {
									xtype : 'textfield',

									fieldLabel : '收货人',
									margin : '2 5 2 0',
									id : "receiver-name"
								}, {
									xtype : 'textfield',

									fieldLabel : '手机',
									margin : '2 5 2 0',
									id : "receiver-mobile"
								}, {
									xtype : 'textfield',

									fieldLabel : '电话',
									margin : '2 5 2 0',
									id : "receiver-tel"
								}, {
									xtype : 'hidden',
									margin : '2 5 2 0'
								}, {
									xtype : 'hidden',
									margin : '2 5 2 0'
								}, {
									xtype : 'button',
									width : 100,
									text : "修改收货地址",
									margin : '2 5 2 0',
									colspan : 3,
									handler : saveAddress,
									id : 'save-address-button'
								}]
					});
			var invoice = Ext.create('Ext.form.Panel', {
				title : "发票",
				layout : {
					type : 'table',
					columns : 3
				},
				animCollapse : true,
				collapsible : true,
				bodyPadding : 2,
				items : [Ext.create('Ext.form.field.ComboBox', {
					fieldLabel : '发票',
					displayField : 'name',
					valueField : 'value',
					store : Ext.create('Ext.data.Store', {
								fields : ['name', 'value'],
								data : [{
											"name" : "不需要",
											"value" : ""
										}, {
											"name" : "个人",
											"value" : "PEOPLE"
										}, {
											"name" : "公司",
											"value" : "COMPANY"
										}]
							}),
					queryMode : 'local',
					editable : false,
					value : "",
					id : "invoice-type",
					margin : "2 5 2 0",
					listeners : {
						scope : this,
						'select' : function(combo, records, eOpts) {
							if (records[0].get('value') == 'PEOPLE') {
								Ext.getCmp('invoice-content')
										.setDisabled(false);
								if (Ext.getCmp('invoice-content').getValue().length == 0)
									Ext.getCmp('invoice-content')
											.setValue('个人');

							} else if (records[0].get('value') == 'COMPANY') {
								Ext.getCmp('invoice-content')
										.setDisabled(false);
								Ext.getCmp('invoice-content').setValue('公司');
							} else {
								Ext.getCmp('invoice-content').setValue('');
								Ext.getCmp('invoice-content').setDisabled(true);
							}
						}
					}
				}), {
					xtype : 'textfield',

					fieldLabel : '抬头',
					margin : '2 5 2 0',
					id : "invoice-content"
				}, {
					xtype : 'button',
					width : 100,
					text : '保存',
					handler : saveInvoice,
					margin : '2 5 2 0',
					id : "save-invoice-button"
				}]
			});
			var orderdetail = Ext.create('Ext.grid.Panel', {
						title : "订单明细",
						id : "order-detail",
						animCollapse : true,
						collapsible : true,
						minHeight : 40,
						store : Ext.create('Ext.data.Store', {
									fields : ['itemCd', 'skuCd', 'name',
											'skuProp1', 'skuProp2', {
												name : 'price',
												type : 'float'
											}, {
												name : 'qty',
												type : 'int'
											}]
								}),
						columns : [{
									xtype : 'rownumberer'
								}, {
									header : "商品编码",
									dataIndex : 'itemCd',
									width : 100,
									sortable : false
								}, {
									header : "SKU",
									dataIndex : 'skuCd',
									width : 100,
									sortable : true
								}, {
									header : "商品名称",
									dataIndex : 'name',
									flex : 1,
									sortable : true
								}, {
									header : "颜色",
									dataIndex : 'skuProp1',
									width : 120,
									sortable : true
								}, {
									header : "尺码",
									dataIndex : 'skuProp2',
									width : 80,
									sortable : true
								}, {
									header : "价格",
									dataIndex : 'price',
									width : 100,
									renderer : 'usMoney',
									sortable : true
								}, {
									header : "数量",
									dataIndex : 'qty',
									width : 100,
									sortable : true
								}]
					});
			var infoPanel = Ext.widget('panel', {
						border : false,
						layout : 'anchor',
						autoScroll : true,
						items : [orderinfo, orderaddress, invoice, orderdetail]
					});

			winNew = Ext.widget('window', {
						title : '订单信息',
						closeAction : 'hide',
						width : 900,
						height : 600,
						x : 0,
						y : 0,
						layout : 'fit',
						resizable : true,
						modal : true,
						items : infoPanel,
						buttons : [{
									text : "取消订单",
									handler : cancelOrder,
									id : 'cancel-order-button'
								}, {
									text : "恢复订单",
									handler : recoverOrder,
									id : 'recover-order-button'
								}, {
									text : "返回",
									handler : function() {
										orderinfo.getForm().reset();
										orderaddress.getForm().reset();
										invoice.getForm().reset();
										orderdetail.getStore().removeAll();
										winNew.hide();
									}
								}]
					});
		}
		// 初始化省市区
		initCity('receiver-state', 'receiver-city', 'receiver-district');
		// 得到订单的信息
		Ext.Ajax.request({
			url : 'orderInfoAction',
			params : {
				orderHeadId : orderHeadId
			},
			waitMsg : '正在加载信息...',
			success : function(response, options) {
				var text = unicodeToString(response.responseText);
				var responseArray = Ext.JSON.decode(text);
				if (responseArray.OrderHead.id) {
					// 订单ID
					orderHeadId = responseArray.OrderHead.id;
					// 订单内容
					Ext.getCmp("order-no")
							.setValue(responseArray.OrderHead.orderNo);
					Ext.getCmp("orig-order-no")
							.setValue(responseArray.OrderHead.origOrderNo);
					Ext.getCmp("seller-nick")
							.setValue(responseArray.OrderHead.sellerNick);
					Ext.getCmp("order-date")
							.setValue(responseArray.OrderHead.orderDate);
					Ext.getCmp("pay-time")
							.setValue(responseArray.OrderHead.payTime);
					Ext.getCmp("delivery-date")
							.setValue(responseArray.OrderHead.deliveryDate);
					Ext.getCmp("order-status")
							.setValue(responseArray.OrderStatusDesc);
					Ext.getCmp("orig-order-status")
							.setValue(responseArray.OrigOrderStatusDesc);
					Ext.getCmp("order-retain")
							.setValue(responseArray.OrderHead.receiveField1);
					Ext.getCmp("buyer-nick")
							.setValue(responseArray.OrderHead.buyerNick);
					Ext.getCmp("buyer-email")
							.setValue(responseArray.OrderHead.buyerEmail);
					Ext.getCmp("order-amt")
							.setValue(responseArray.OrderHead.orderAmt);
					Ext.getCmp("total-fee")
							.setValue(responseArray.OrderHead.totalFee);
					Ext.getCmp("post-fee")
							.setValue(responseArray.OrderHead.postFee);
					Ext.getCmp("payment")
							.setValue(responseArray.OrderHead.payment);
					Ext.getCmp("payment-point")
							.setValue(responseArray.OrderHead.paymentPoint);
					Ext.getCmp("obtain-point")
					.setValue(responseArray.OrderHead.obtainPoint);
					Ext.getCmp("post-method")
							.setValue(responseArray.OrderHead.postMethod);
					Ext.getCmp("post-company")
							.setValue(responseArray.OrderHead.postCompany);
					Ext.getCmp("post-no")
							.setValue(responseArray.OrderHead.postNo);
					Ext.getCmp("buyer-memo")
							.setValue(responseArray.BUYER_MESSAGE);
					Ext.getCmp("seller-memo")
							.setValue(responseArray.SELLER_MEMO);
					Ext.getCmp("cust-memo").setValue(responseArray.CUST_MEMO);
					// 订单地址
					setCityValue('receiver-state',
							responseArray.OrderHead.receiverState,
							'receiver-city',
							responseArray.OrderHead.receiverCity,
							'receiver-district',
							responseArray.OrderHead.receiverDistrict);
					Ext.getCmp("receiver-address")
							.setValue(responseArray.OrderHead.receiverAddress);
					Ext.getCmp("receiver-zip")
							.setValue(responseArray.OrderHead.receiverZip);
					Ext.getCmp("receiver-mobile")
							.setValue(responseArray.OrderHead.receiverMobile);
					Ext.getCmp("receiver-tel")
							.setValue(responseArray.OrderHead.receiverTel);
					Ext.getCmp("receiver-name")
							.setValue(responseArray.OrderHead.receiverName);
					// 发票
					if (responseArray.OrderHead.invoiceType != null) {
						Ext.getCmp("invoice-type")
								.setValue(responseArray.OrderHead.invoiceType);
						Ext
								.getCmp("invoice-content")
								.setValue(responseArray.OrderHead.invoiceContent);
					}

					// 明细
					Ext.getCmp('order-detail').getStore()
							.loadData(responseArray.OrderItem);
					buttonCtr(responseArray.OrderHead.orderStatus);
					winNew.show();
				} else {
					Ext.MessageBox.alert("错误", "用户信息没找到");
				}
			}
		});
		winNew.show();
	}
	function buttonCtr(orderStatus) {
		if (orderStatus == "START") {
			Ext.getCmp("save-cust-memo-button").show();
			Ext.getCmp("save-address-button").show();
			Ext.getCmp("cancel-order-button").show();
			Ext.getCmp("recover-order-button").show();

			Ext.getCmp('cust-memo').setReadOnly(false);
			Ext.getCmp('receiver-state').setReadOnly(false);
			Ext.getCmp('receiver-city').setReadOnly(false);
			Ext.getCmp('receiver-district').setReadOnly(false);
			Ext.getCmp("receiver-address").setReadOnly(false);
			Ext.getCmp("receiver-zip").setReadOnly(false);
			Ext.getCmp("receiver-mobile").setReadOnly(false);
			Ext.getCmp("receiver-tel").setReadOnly(false);
			Ext.getCmp("receiver-name").setReadOnly(false);
		} else {
			Ext.getCmp("save-cust-memo-button").hide();
			Ext.getCmp("save-address-button").hide();
			Ext.getCmp("cancel-order-button").hide();
			Ext.getCmp("recover-order-button").hide();

			Ext.getCmp('cust-memo').setReadOnly(true);
			Ext.getCmp('receiver-state').setReadOnly(true);
			Ext.getCmp('receiver-city').setReadOnly(true);
			Ext.getCmp('receiver-district').setReadOnly(true);
			Ext.getCmp("receiver-address").setReadOnly(true);
			Ext.getCmp("receiver-zip").setReadOnly(true);
			Ext.getCmp("receiver-mobile").setReadOnly(true);
			Ext.getCmp("receiver-tel").setReadOnly(true);
			Ext.getCmp("receiver-name").setReadOnly(true);

		}
	}
	// 保存客服留言
	function saveCustMemo() {
		Ext.Ajax.request({
					url : 'custNoteSaveAction',
					params : {
						crumb : Ext.get('crumb').getValue(),
						orderHeadId : orderHeadId,
						note : Ext.getCmp('cust-memo').getValue()
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
	}
	// 修改地址
	function saveAddress() {
		Ext.Ajax.request({
					url : 'updateReceiverInfoAction',
					params : {
						crumb : Ext.get('crumb').getValue(),
						orderHeadId : orderHeadId,
						receiverName : Ext.getCmp('receiver-name').getValue(),
						receiverState : Ext.getCmp('receiver-state').getValue(),
						receiverCity : Ext.getCmp('receiver-city').getValue(),
						receiverDistrict : Ext.getCmp('receiver-district')
								.getValue(),
						receiverAddress : Ext.getCmp('receiver-address')
								.getValue(),
						receiverZip : Ext.getCmp('receiver-zip').getValue(),
						receiverMobile : Ext.getCmp('receiver-mobile')
								.getValue(),
						receiverPhone : Ext.getCmp('receiver-tel').getValue()
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
	}
	// 保存发票信息
	function saveInvoice() {
		Ext.Ajax.request({
					url : 'saveInvoiceAction',
					params : {
						crumb : Ext.get('crumb').getValue(),
						orderHeadId : orderHeadId,
						invoice : Ext.getCmp('invoice-type').getValue(),
						invoiceMessage : Ext.getCmp('invoice-content')
								.getValue()
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
	}
	// 取消订单
	function cancelOrder() {
		Ext.Ajax.request({
					url : 'cancelOrderAction',
					params : {
						crumb : Ext.get('crumb').getValue(),
						orderHeadId : orderHeadId
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
	}
	// 恢复订单
	function recoverOrder() {
		Ext.Ajax.request({
					url : 'recoverOrderAction',
					params : {
						crumb : Ext.get('crumb').getValue(),
						orderHeadId : orderHeadId
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
	}

});