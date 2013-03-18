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
							"name" : '退货单号',
							"value" : 'TB_REFUND_NO'
						}, {
							"name" : '淘宝订单号',
							"value" : 'TB_ORDER_NO'
						}, {
							"name" : '快递单号',
							"value" : 'TB_SHIP_NO'
						}, {
							"name" : '买家昵称',
							"value" : 'BUYER_NICK'
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
					url : 'returnStatusList'
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
									fieldLabel : '是否需要退货',
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
									id : "s-has-good-return",
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
									fieldLabel : '退款状态',
									displayField : 'name',
									valueField : 'value',
									store : Ext.create('Ext.data.Store', {
												model : 'combo_model',
												remoteSort : true,
												proxy : {
													type : 'ajax',
													url : 'tbReturnStatusList'
												}
											}),
									queryMode : 'remote',
									editable : false,
									id : "s-refund-status",
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
						}, Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '淘宝订单状态',
									displayField : 'name',
									valueField : 'value',
									store : tbstatusstore,
									queryMode : 'remote',
									editable : false,
									id : "s-tb-status",
									margin : "5 5 5 10"
								}), Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '货物状态',
									displayField : 'name',
									valueField : 'value',
									store : Ext.create('Ext.data.Store', {
												model : 'combo_model',
												remoteSort : true,
												proxy : {
													type : 'ajax',
													url : 'tbGoodStatusList'
												}
											}),
									queryMode : 'remote',
									editable : false,
									id : "s-good-status",
									margin : "5 5 5 10"
								})],
				buttons : [{
							text : "新增",
							handler : newRefundHandler
						}, {
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
				fields : ['check', 'orderHeadId', 'orderNo', 'orderStatus',
						'refundNo', 'refundStatus', {
							name : 'requestDate',
							type : 'date',
							dateFormat : 'Y-m-d H:i:s'
						}, 'postCompany', 'shipNo', {
							name : 'storeId',
							type : 'int'
						}, {
							name : 'orderAmt',
							type : 'float'
						}, 'orderStatusName', 'storeName', 'buyerNick',
						'refundStatusName', {
							name : 'refundDate',
							type : 'date',
							dateFormat : 'Y-m-d H:i:s'
						}, 'tbOrderNo', 'tbOrderStatus', 'tbOrderStatusName',
						'goodStatus', 'goodStatusName', 'hasGoodReturn',
						'hasGoodReturnName', {
							name : 'payment',
							type : 'float'
						}, {
							name : 'refundAmt',
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
							url : 'refundSearchAction',
							reader : {
								root : 'resultList',
								totalProperty : 'rowCount',
								fields : ['check', 'orderHeadId', 'orderNo',
										'orderStatus', 'refundNo',
										'refundStatus', 'requestDate',
										'postCompany', 'shipNo', 'storeId',
										'orderAmt', 'orderStatusName',
										'storeName', 'buyerNick',
										'refundStatusName', 'refundDate',
										'tbOrderNo', 'tbOrderStatus',
										'tbOrderStatusName', 'goodStatus',
										'goodStatusName', 'hasGoodReturn',
										'hasGoodReturnName', 'payment',
										'refundAmt']
							},
							// sends single sort as multi parameter
							simpleSortMode : true
						})
			});
	function validationCheck(value, p, r, rowIndex, colIndex) {
		var orderStatus = r.get('orderStatus');
		if (orderStatus == '' || orderStatus == '') {

		}
		r.endEdit(false);
		return value;
	}
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
							width : 30,
							editor : {
								xtype : 'checkbox',
								cls : 'x-grid-checkheader-editor'
							}
						}, {
							header : "系统单号",
							dataIndex : 'orderNo',
							renderer : validationCheck,
							width : 100,
							flex : 1,
							sortable : false
						}, {
							header : "淘宝退款单号",
							dataIndex : 'refundNo',
							width : 100,
							sortable : true
						}, {
							header : "淘宝订单号",
							dataIndex : 'tbOrderNo',
							width : 100,
							align : 'right',
							sortable : true
						}, {
							header : "买家",
							dataIndex : 'buyerNick',
							width : 100,
							align : 'right',
							sortable : true
						}, {
							header : "交易金额",
							dataIndex : 'orderAmt',
							width : 100,
							align : 'right',
							sortable : true
						}, {
							header : "退款金额",
							dataIndex : 'refundAmt',
							width : 100,
							align : 'right',
							sortable : true
						}, {
							header : "申请时间",
							dataIndex : 'refundDate',
							renderer : Ext.util.Format
									.dateRenderer('Y-m-d H:i:s'),
							width : 120,
							sortable : true
						}, {
							header : "退款状态",
							dataIndex : 'refundStatusName',
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
	resultGrid.child('pagingtoolbar').add(['->', {
				text : '审核',
				scope : this,
				handler : auditedRefundHandler
			}, {
				text : '保留',
				scope : this,
				handler : receiveRefundHandler
			}, {
				text : '查看',
				scope : this,
				handler : showRefundHandler
			}]);
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
						limit : pageSize,
						storeId : Ext.getCmp("s-store-id").getValue(),
						refundPropsValue : Ext.getCmp("s-order-props-value")
								.getValue(),
						refundPropsName : Ext.getCmp("s-order-props-name")
								.getValue(),
						tbStatus : Ext.getCmp("s-tb-status").getValue(),
						erpStatus : Ext.getCmp("s-erp-status").getValue(),
						refundStatus : Ext.getCmp("s-refund-status").getValue(),
						goodStatus : Ext.getCmp("s-good-status").getValue(),
						hasGoodReturn : Ext.getCmp("s-has-good-return")
								.getValue(),
						refundFromDate : Ext.getCmp("s-order-from-date")
								.getValue(),
						refundEndDate : Ext.getCmp("s-order-end-date")
								.getValue()
					};
					Ext.apply(store.proxy.extraParams, new_params);
				});
		store.load({
					params : {
						start : 0,
						limit : pageSize
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
	function auditedRefundHandler() {
		var count = resultGrid.getStore().getCount();
		var orderHeadIds = '';
		for (i = 0; i < count; i++) {
			var row = resultGrid.getStore().getAt(i);
			if (row.get('check') && row.get('orderStatus') == 'REFUND_START') {
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
					url : 'refundsAuditedAction',
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
	function receiveRefundHandler() {
		var count = resultGrid.getStore().getCount();
		var orderHeadIds = '';
		for (i = 0; i < count; i++) {
			var row = resultGrid.getStore().getAt(i);
			if (row.get('check') && row.get('orderStatus') == 'REFUND_AUDITED') {
				if (orderHeadIds.length > 0)
					orderHeadIds += ',';
				orderHeadIds += row.get('orderHeadId');
			}
		}
		if (orderHeadIds.length == 0) {
			Ext.MessageBox.alert('提示', "请选择[审核]状态的订单!");
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
							store : Ext.create('Ext.data.Store', {
										fields : ['name', 'value'],
										remoteSort : true,
										proxy : {
											type : 'ajax',
											url : 'refundReceiveStatusList'
										}
									}),
							queryMode : 'remote',
							editable : false,
							value : "",
							name : "district",
							id : "refund-receive-status",
							margin : "5 5 5 5"
						})],
				buttons : [{
					text : "保留订单",
					handler : function() {
						var receiveMessage = Ext
								.getCmp('refund-receive-status').getValue();
						if (receiveMessage.length == 0) {
							Ext.MessageBox.alert('提示', "请选择保留原因!");
							return;
						}
						Ext.Ajax.request({
							url : 'refundsReceiveAction',
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
									Ext.getCmp('refund-receive-status')
											.setValue('');
									receiveWin.hide();
									refreshGrid();
								} else {
									Ext.MessageBox.alert("错误",
											responseArray.message);
								}
							}
						});
					},
					id : 'recover-refund-button'
				}, {
					text : "取消",
					handler : function() {
						Ext.getCmp('refund-receive-status').setValue('');
						receiveWin.hide();
					}
				}]
			});
		}
		receiveWin.show();

	}
	// 新增退货单
	function newRefundHandler() {
		createWindonw();
		infoCtr('ERP', 'REFUND_START');
		orderHeadId = null;
		winNew.show();
	}
	// 显示退货单
	function showRefundHandler() {
		var row = resultGrid.getSelectionModel().getSelection()[0];
		if (!row) {

			Ext.MessageBox.alert('提示', "请选择一条记录!");
			return;
		}
		createWindonw();
		showRefundInfoForm(row.get('orderHeadId'));
	}
	var winNew;
	var orderHeadId;
	// 订单信息窗口
	function createWindonw() {
		if (!winNew) {
			var orderinfo = Ext.create('Ext.form.Panel', {
				title : "退货详情",
				layout : {
					type : 'table',
					columns : 3
				},
				animCollapse : true,
				bodyPadding : 2,
				collapsible : true,
				id : 'refund-form-panel',
				items : [{
							xtype : 'textfield',
							readOnly : true,
							fieldLabel : '退货单号',
							readOnly : true,
							margin : '2 5 2 0',
							id : "order-no"
						}, {
							xtype : 'textfield',
							readOnly : true,
							fieldLabel : '系统状态',
							margin : '2 5 2 0',
							id : "order-status"
						}, {
							xtype : 'textfield',
							readOnly : true,
							fieldLabel : '买家昵称',
							allowBlank : false,
							margin : '2 5 2 0',
							id : "buyer-name"
						}, {
							xtype : 'textfield',
							readOnly : true,
							fieldLabel : '淘宝退款单号',
							margin : '2 5 2 0',
							id : "orig-order-no"
						}, Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '退款状态',
									displayField : 'name',
									valueField : 'value',
									store : Ext.create('Ext.data.Store', {
												model : 'combo_model',
												remoteSort : true,
												proxy : {
													type : 'ajax',
													url : 'tbReturnStatusList'
												}
											}),
									queryMode : 'remote',
									editable : false,
									allowBlank : false,
									id : "refund-status",
									margin : "2 5 2 0"
								}), {
							xtype : 'textfield',
							readOnly : true,
							fieldLabel : '退还给买家的金额',
							allowBlank : false,
							vtype : 'money',
							margin : '2 5 2 0',
							id : "order-amt",
							listeners : {
								'change' : function(field, newValue, oldValue,
										eOpts) {
									var totalfee = Ext.getCmp('total-fee')
											.getValue();
									if (totalfee.length == 0 || totalfee == 0)
										return;
									if (newValue.length == 0)
										newValue = 0;
									Ext.getCmp('payment').setValue(totalfee
											- newValue);
								}
							}
						}, {
							xtype : 'textfield',
							readOnly : true,
							fieldLabel : '淘宝订单号',
							allowBlank : false,
							margin : '2 5 2 0',
							id : "ref-order-no"
						}, {
							xtype : 'textfield',
							readOnly : true,
							fieldLabel : '淘宝状态',
							margin : '2 5 2 0',
							id : "ref-order-status"
						}, {
							xtype : 'textfield',
							readOnly : true,
							fieldLabel : '订单金额',
							allowBlank : false,
							vtype : 'money',
							margin : '2 5 2 0',
							id : "total-fee"
						}, {
							xtype : 'datefield',
							readOnly : true,
							fieldLabel : '申请退款时间',
							format : 'Y-m-d H:i:s',
							allowBlank : false,
							margin : '2 5 2 0',
							id : "refund-date"
						}, Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '货物状态',
									displayField : 'name',
									valueField : 'value',
									store : Ext.create('Ext.data.Store', {
												model : 'combo_model',
												remoteSort : true,
												proxy : {
													type : 'ajax',
													url : 'goodStatusList'
												}
											}),
									queryMode : 'remote',
									editable : false,
									id : "good-status",
									margin : "2 5 2 0"
								}), {
							xtype : 'textfield',
							readOnly : true,
							fieldLabel : '支付给卖家的金额',
							allowBlank : false,
							margin : '2 5 2 0',
							vtype : 'money',
							id : "payment",
							listeners : {
								'change' : function(field, newValue, oldValue,
										eOpts) {
									var totalfee = Ext.getCmp('total-fee')
											.getValue();
									if (totalfee.length == 0 || totalfee == 0)
										return;
									if (newValue.length == 0)
										newValue = 0;
									Ext.getCmp('order-amt').setValue(totalfee
											- newValue);
								}
							}
						}, Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '淘宝店铺',
									displayField : 'name',
									valueField : 'value',
									store : shopstore,
									queryMode : 'local',
									editable : false,
									allowBlank : false,
									id : "order-shop",
									margin : "2 5 2 0"
								}), Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '保留原因',
									displayField : 'name',
									valueField : 'value',
									store : Ext.create('Ext.data.Store', {
												fields : ['name', 'value'],
												remoteSort : true,
												proxy : {
													type : 'ajax',
													url : 'refundReceiveStatusList'
												}
											}),
									queryMode : 'remote',
									editable : false,
									value : "",
									id : "receive-message",
									margin : "2 5 2 0"
								}), Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '需要退货',
									displayField : 'name',
									valueField : 'value',
									store : Ext.create('Ext.data.Store', {
												fields : ['name', 'value'],
												data : [{
															"name" : "是",
															"value" : "true"
														}, {
															"name" : "否",
															"value" : "false"
														}]
											}),
									queryMode : 'local',
									editable : false,
									allowBlank : false,
									value : "",
									id : "has-good-return",
									margin : "2 5 2 0"
								}), {
							xtype : 'textfield',
							readOnly : true,
							fieldLabel : '支付宝交易号',
							margin : '2 5 2 0',
							colspan : 3,
							id : "alipay-no"
						}, {
							xtype : 'textarea',
							readOnly : true,
							fieldLabel : '退款原因',
							margin : '2 5 2 0',
							width : 775,
							colspan : 3,
							id : "refund-reason"
						}, {
							xtype : 'textarea',
							readOnly : true,
							fieldLabel : '退款说明',
							margin : '2 5 2 0',
							width : 775,
							colspan : 3,
							id : "refund-memo"
						}, {
							xtype : 'textarea',

							fieldLabel : '客服留言',
							margin : '2 5 2 0',
							width : 775,
							colspan : 3,
							id : "cust-memo"
						}, {
							xtype : 'hidden',
							margin : '2 5 2 0',
							id : 'order-head-id'
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

			var orderdetail = Ext.create('Ext.grid.Panel', {
						title : "订单明细",
						id : "refund-detail",
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
						items : [orderinfo, orderdetail]
					});

			winNew = Ext.widget('window', {
						title : '退货单',
						closeAction : 'hide',
						width : "100%",
						height : "100%",
						x : 0,
						y : 0,
						layout : 'fit',
						resizable : true,
						modal : true,
						items : infoPanel,
						buttons : [{
									text : "导入淘宝订单",
									handler : importOrder,
									id : 'import-order-button'
								}, {
									text : "关闭退货单",
									handler : cancelRefund,
									id : 'cancel-refund-button'
								}, {
									text : "恢复退货单",
									handler : recoveryRefund,
									id : 'recovery-refund-button'
								}, {
									text : "保存",
									handler : saveRefund,
									id : 'save-refund-button'
								}, {
									text : "返回",
									handler : function() {
										orderinfo.getForm().reset();
										orderdetail.getStore().removeAll();
										winNew.hide();
									}
								}]
					});
			Ext.getCmp("refund-status").getStore().load();
			Ext.getCmp("order-shop").getStore().load();
			Ext.getCmp("receive-message").getStore().load();
		}
	}
	function showRefundInfoForm(orderHeadId) {

		// 得到订单的信息
		Ext.Ajax.request({
			url : 'refundInfoAction',
			params : {
				orderHeadId : orderHeadId
			},
			success : function(response, options) {
				var text = unicodeToString(response.responseText);
				var responseArray = Ext.JSON.decode(text);
				if (responseArray.OrderHead.id) {
					// 订单ID
					orderHeadId = responseArray.OrderHead.id;
					Ext.getCmp("order-head-id").setValue(orderHeadId);
					// 订单内容
					Ext.getCmp("order-no")
							.setValue(responseArray.OrderHead.orderNo);
					Ext.getCmp("order-status")
							.setValue(responseArray.orderStatusName);
					Ext.getCmp("buyer-name")
							.setValue(responseArray.OrderHead.buyerNick);
					Ext.getCmp("orig-order-no")
							.setValue(responseArray.OrderHead.origOrderNo);
					Ext.getCmp("refund-status")
							.setValue(responseArray.OrderHead.origOrderStatus);

					Ext.getCmp("good-status")
							.setValue(responseArray.OrderHead.receiveField5);
					Ext.getCmp("order-amt")
							.setValue(responseArray.OrderHead.orderAmt);
					Ext.getCmp("ref-order-no")
							.setValue(responseArray.OrderHead.refOrderNo);
					Ext.getCmp("ref-order-status")
							.setValue(responseArray.tbOrderStatusName);
					Ext.getCmp("total-fee")
							.setValue(responseArray.OrderHead.totalFee);
					Ext.getCmp("order-shop")
							.setValue(responseArray.OrderHead.storeId);
					Ext.getCmp("alipay-no")
							.setValue(responseArray.OrderTaobaoinfo.alipayNo);
					Ext.getCmp("payment")
							.setValue(responseArray.OrderHead.payment);
					Ext.getCmp("receive-message")
							.setValue(responseArray.OrderHead.receiveField1);
					Ext.getCmp("has-good-return")
							.setValue(responseArray.OrderHead.receiveField6);
					Ext
							.getCmp("refund-date")
							.setValue(new Date(responseArray.OrderHead.requestDate));
					Ext.getCmp("refund-reason")
							.setValue(responseArray.REFUND_REASON);
					Ext.getCmp("refund-memo")
							.setValue(responseArray.REFUND_DESC);
					Ext.getCmp("cust-memo").setValue(responseArray.CUST_MEMO);
					// 明细
					Ext.getCmp('refund-detail').getStore()
							.loadData(responseArray.OrderItem);
					infoCtr(responseArray.OrderHead.origin,
							responseArray.OrderHead.orderStatus);
					winNew.show();
				} else {
					Ext.MessageBox.alert("错误", "用户信息没找到");
				}
			}
		});
		winNew.show();
	}
	// 控制按钮和内容是否可编辑
	function infoCtr(origin, orderStatus) {
		// 7天后退货｜手工创建的退货单
		if (orderStatus == "REFUND_START") {
			if (origin == "ERP") {
				// Ext.getCmp("order-no").setReadOnly(false);
				Ext.getCmp("order-status").setReadOnly(true);
				Ext.getCmp("buyer-name").setReadOnly(false);
				Ext.getCmp("orig-order-no").setReadOnly(false);
				Ext.getCmp("refund-status").setDisabled(false);
				Ext.getCmp("order-amt").setReadOnly(false);
				Ext.getCmp("ref-order-no").setReadOnly(false);
				Ext.getCmp("ref-order-status").setReadOnly(true);
				Ext.getCmp("total-fee").setReadOnly(false);
				Ext.getCmp("order-shop").setDisabled(false);
				Ext.getCmp("alipay-no").setReadOnly(false);
				Ext.getCmp("payment").setReadOnly(false);
				Ext.getCmp("receive-message").setDisabled(false);
				Ext.getCmp("has-good-return").setDisabled(false);
				Ext.getCmp("good-status").setDisabled(false);
				Ext.getCmp("refund-date").setReadOnly(false);
				Ext.getCmp("refund-reason").setReadOnly(false);
				Ext.getCmp("refund-memo").setReadOnly(false);
				Ext.getCmp("save-refund-button").show();
				Ext.getCmp("save-cust-memo-button").hide();
				Ext.getCmp("import-order-button").show();
			} else {
				// Ext.getCmp("order-no").setReadOnly(true);
				Ext.getCmp("order-status").setReadOnly(true);
				Ext.getCmp("buyer-name").setReadOnly(true);
				Ext.getCmp("orig-order-no").setReadOnly(true);
				Ext.getCmp("refund-status").setDisabled(true);
				Ext.getCmp("order-amt").setReadOnly(true);
				Ext.getCmp("ref-order-no").setReadOnly(true);
				Ext.getCmp("ref-order-status").setReadOnly(true);
				Ext.getCmp("total-fee").setReadOnly(true);
				Ext.getCmp("order-shop").setDisabled(true);
				Ext.getCmp("alipay-no").setReadOnly(true);
				Ext.getCmp("payment").setReadOnly(true);
				Ext.getCmp("receive-message").setDisabled(true);
				Ext.getCmp("has-good-return").setDisabled(true);
				Ext.getCmp("good-status").setDisabled(true);
				Ext.getCmp("refund-date").setReadOnly(true);
				Ext.getCmp("refund-reason").setReadOnly(true);
				Ext.getCmp("refund-memo").setReadOnly(true);
				Ext.getCmp("save-refund-button").hide();
				Ext.getCmp("save-cust-memo-button").show();
				Ext.getCmp("import-order-button").hide();
			}
			Ext.getCmp("cust-memo").setReadOnly(false);
			Ext.getCmp("cancel-refund-button").show();
			Ext.getCmp("recovery-refund-button").hide();
		} else {
			// Ext.getCmp("order-no").setReadOnly(true);
			Ext.getCmp("order-status").setReadOnly(true);
			Ext.getCmp("buyer-name").setReadOnly(true);
			Ext.getCmp("orig-order-no").setReadOnly(true);
			Ext.getCmp("refund-status").setDisabled(true);
			Ext.getCmp("order-amt").setReadOnly(true);
			Ext.getCmp("ref-order-no").setReadOnly(true);
			Ext.getCmp("ref-order-status").setReadOnly(true);
			Ext.getCmp("total-fee").setReadOnly(true);
			Ext.getCmp("order-shop").setDisabled(true);
			Ext.getCmp("alipay-no").setReadOnly(true);
			Ext.getCmp("payment").setReadOnly(true);
			Ext.getCmp("receive-message").setDisabled(true);
			Ext.getCmp("has-good-return").setDisabled(true);
			Ext.getCmp("good-status").setDisabled(true);
			Ext.getCmp("refund-date").setReadOnly(true);
			Ext.getCmp("refund-reason").setReadOnly(true);
			Ext.getCmp("refund-memo").setReadOnly(true);
			Ext.getCmp("cust-memo").setReadOnly(true);
			Ext.getCmp("save-cust-memo-button").hide();
			Ext.getCmp("save-refund-button").hide();
			Ext.getCmp("import-order-button").hide();
			Ext.getCmp("cancel-refund-button").hide();
			Ext.getCmp("recovery-refund-button").hide();
			if (orderStatus == "REFUND_CLOSED") {
				Ext.getCmp("recovery-refund-button").show();
			}
		}
	}
	// 导入淘宝订单
	var importWin;
	function importOrder() {
		var refOrderNo = Ext.getCmp("ref-order-no").getValue();
		if (!importWin) {
			importWin = Ext.widget('window', {
				title : '导入淘宝订单',
				closeAction : 'hide',
				width : 300,
				height : 100,
				resizable : true,
				modal : true,
				items : [{
							xtype : 'textfield',
							fieldLabel : '淘宝订单号',
							allowBlank : false,
							margin : '5 5 5 5',
							id : "import-ref-order-no"
						}],
				buttons : [{
					text : "导入",
					handler : function() {
						if (Ext.getCmp("import-ref-order-no").getValue().length == 0) {
							return;
						}
						refOrderNo = Ext.getCmp("import-ref-order-no")
								.getValue();
						Ext.Ajax.request({
							url : 'importTbOrderInfoAction',
							params : {
								refOrderNo : refOrderNo
							},
							success : function(response, options) {
								var text = unicodeToString(response.responseText);
								var responseArray = Ext.JSON.decode(text);
								if (responseArray != null
										&& responseArray.OrderHead.orderNo) {
									// 订单内容
									Ext
											.getCmp("buyer-name")
											.setValue(responseArray.OrderHead.buyerNick);
									Ext
											.getCmp("total-fee")
											.setValue(responseArray.OrderHead.orderAmt);
									Ext
											.getCmp("ref-order-no")
											.setValue(responseArray.OrderHead.origOrderNo);
									Ext
											.getCmp("ref-order-status")
											.setValue(responseArray.OrigOrderStatusDesc);
									Ext
											.getCmp("order-shop")
											.setValue(responseArray.OrderHead.storeId);
									Ext
											.getCmp("alipay-no")
											.setValue(responseArray.OrderTaobaoinfo.alipayNo);
									Ext
											.getCmp("has-good-return")
											.setValue(responseArray.OrderHead.receiveField6);
									// 明细
									Ext.getCmp('refund-detail').getStore()
											.removeAll(true);
									Ext.getCmp('refund-detail').getStore()
											.loadData(responseArray.OrderItem);
									Ext.getCmp("import-ref-order-no")
											.setValue("");
									importWin.hide();
								} else {
									Ext.MessageBox.alert("错误", "订单信息没找到");
								}
							}
						});

					}
				}, {
					text : "取消",
					handler : function() {
						Ext.getCmp("import-ref-order-no").setValue("");
						importWin.hide();
					}
				}]
			});
		}
		Ext.getCmp("import-ref-order-no").setValue(refOrderNo);
		importWin.show();
	}
	// 保存客服留言
	function saveCustMemo() {
		Ext.Ajax.request({
					url : 'custNoteSaveAction',
					params : {
						crumb : Ext.get('crumb').getValue(),
						orderHeadId : Ext.getCmp("order-head-id").getValue(),
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
	// 关闭退货单
	function cancelRefund() {
		Ext.Ajax.request({
					url : 'refundClose',
					params : {
						crumb : Ext.get('crumb').getValue(),
						orderHeadId : Ext.getCmp("order-head-id").getValue()
					},
					success : function(response, options) {
						var text = unicodeToString(response.responseText);
						var responseArray = Ext.JSON.decode(text);
						if (responseArray.success) {
							Ext.MessageBox.alert('提示', "处理成功!");
							Ext.getCmp('refund-form-panel').getForm().reset();
							Ext.getCmp('refund-detail').getStore().removeAll();
							winNew.hide();
							refreshGrid();
						} else {
							Ext.MessageBox.alert("错误", responseArray.message);
						}
					}
				});
	}
	// 恢复退货单
	function recoveryRefund() {
		Ext.Ajax.request({
					url : 'refundRecovery',
					params : {
						crumb : Ext.get('crumb').getValue(),
						orderHeadId : Ext.getCmp("order-head-id").getValue()
					},
					success : function(response, options) {
						var text = unicodeToString(response.responseText);
						var responseArray = Ext.JSON.decode(text);
						if (responseArray.success) {
							Ext.MessageBox.alert('提示', "处理成功!");
							Ext.getCmp('refund-form-panel').getForm().reset();
							Ext.getCmp('refund-detail').getStore().removeAll();
							winNew.hide();
							refreshGrid();
						} else {
							Ext.MessageBox.alert("错误", responseArray.message);
						}
					}
				});
	}
	// 保存退货单
	function saveRefund() {
		if (!Ext.getCmp('refund-form-panel').getForm().isValid())
			return;
		var detailStore = Ext.getCmp('refund-detail').getStore();
		var orderItemStr = '[';
		for (i = 0; i < detailStore.getCount(); i++) {
			if (i > 0)
				orderItemStr += ',';
			var a = detailStore.getAt(i).data;
			orderItemStr += Ext.JSON.encode(a);
		}
		orderItemStr += ']';
		Ext.Ajax.request({
					url : 'refundSaveAction',
					params : {
						crumb : Ext.get('crumb').getValue(),
						orderHeadId : Ext.getCmp("order-head-id").getValue(),
						refundNo : Ext.getCmp('orig-order-no').getValue(),
						refundStatus : Ext.getCmp('refund-status').getValue(),
						goodStatus : Ext.getCmp('good-status').getValue(),
						hasGoodReturn : Ext.getCmp('has-good-return')
								.getValue(),
						refOrderNo : Ext.getCmp('ref-order-no').getValue(),
						storeId : Ext.getCmp('order-shop').getValue(),
						totalFee : Ext.getCmp('total-fee').getValue(),
						refundDate : Ext.getCmp('refund-date').getValue(),
						paymentAmt : Ext.getCmp('payment').getValue(),
						refundAmt : Ext.getCmp('order-amt').getValue(),
						refundReason : Ext.getCmp('refund-reason').getValue(),
						refundDesc : Ext.getCmp('refund-memo').getValue(),
						custMemo : Ext.getCmp('cust-memo').getValue(),
						buyerNick : Ext.getCmp('buyer-name').getValue(),
						alipayNo : Ext.getCmp('alipay-no').getValue(),
						orderItemsStr : orderItemStr
					},
					success : function(response, options) {
						var text = unicodeToString(response.responseText);
						var responseArray = Ext.JSON.decode(text);
						if (responseArray.success) {
							Ext.MessageBox.alert('提示', "处理成功!");
							Ext.getCmp('refund-form-panel').getForm().reset();
							Ext.getCmp('refund-detail').getStore().removeAll();
							winNew.hide();
							refreshGrid();
						} else {
							Ext.MessageBox.alert("错误", responseArray.message);
						}
					}
				});
	}

});