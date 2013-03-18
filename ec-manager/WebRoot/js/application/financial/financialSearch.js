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
								}), {
							xtype : 'fieldcontainer',
							fieldLabel : '对账月份',
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
										xtype : 'numberfield',
										fieldLabel : '年',
										id : 's-year',
										minValue : 2010,
										maxValue : 2020
									}, {
										xtype : 'numberfield',
										fieldLabel : '月份',
										id : 's-month',
										minValue : 1,
										maxValue : 12
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
				fields : ['accountId', 'month', 'storeId'],
				idProperty : 'accountId'
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
							url : 'searchFinancial',
							reader : {
								root : 'resultList',
								totalProperty : 'rowCount',
								fields : ['accountId', 'month', 'storeId']
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
							header : "店铺",
							dataIndex : 'storeId',
							width : 150,
							flex : 1,
							sortable : false
						}, {
							header : "月份",
							dataIndex : 'month',
							width : 150,
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
				iconCls : 'icon-delete',
				text : '生成报表',
				scope : this,
				handler : genericReportHandler
			}, {
				iconCls : 'icon-delete',
				text : '查看',
				scope : this,
				handler : showInfoHandler
			}, {
				iconCls : 'icon-delete',
				text : '导出报表',
				scope : this,
				handler : exportReportHandler
			}]);
	resultGrid.addListener('itemdblclick', showInfoHandler, this);
	// trigger the data store load
	store.load({
				params : {
					start : 0,
					limit : pageSize
				}
			});
	// 查询
	function searchHandler() {
		var month;
		if (Ext.getCmp("s-year").getValue() != null
				&& Ext.getCmp("s-month").getValue() != null)
			month = Ext.getCmp("s-year").getValue() + '-'
					+ Ext.getCmp("s-month").getValue();
		store.on('beforeload', function() { // =======翻页时 查询条件
					var new_params = {
						limit : pageSize,
						storeId : Ext.getCmp("s-store-id").getValue(),
						month : month
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
	// 显示报表
	function showInfoHandler() {
		var row = resultGrid.getSelectionModel().getSelection()[0];
		if (!row) {

			Ext.MessageBox.alert('提示', "请选择一条记录!");
			return;
		}
		showReportInfoForm();
		Ext.Ajax.request({
			url : 'taobaoAccountHeadInfo',
			params : {
				accountId : row.get('accountId')
			},
			waitMsg : '正在加载信息...',
			success : function(response, options) {
				var text = unicodeToString(response.responseText);
				var responseArray = Ext.JSON.decode(text);
				if (responseArray.success) {
					Ext.getCmp("info-report-title")
							.setValue(responseArray.taobaoAccountInfo.Month
									+ '月份'
									+ responseArray.taobaoAccountInfo.StoreName
									+ '销售汇总');
					Ext
							.getCmp("info-price-amount")
							.setValue(responseArray.taobaoAccountInfo.PriceAmount);
					Ext
							.getCmp("info-price-discount")
							.setValue(responseArray.taobaoAccountInfo.PriceDiscount);
					// 明细
					Ext
							.getCmp('info-received-grid')
							.getStore()
							.loadData(responseArray.taobaoAccountInfo.ReceivedList);
					Ext.getCmp('info-paid-grid').getStore()
							.loadData(responseArray.taobaoAccountInfo.PaidList);
				} else {
					Ext.MessageBox.alert("错误", "信息没找到");
				}
			}
		});
	}
	var infoWin;
	function showReportInfoForm() {
		var infoPanel = Ext.create('Ext.form.Panel', {
					width : '100%',
					layout : {
						type : 'table',
						columns : 4
					},
					animCollapse : true,
					bodyPadding : 2,
					items : [{
								xtype : 'displayfield',
								id : "info-report-title",
								width : 500,
								value : '报表',
								colspan : 4
							}, {
								xtype : 'displayfield',
								id : "info-price-amount",
								fieldLabel : "淘宝原价成交额",
								value : ''
							}, {
								xtype : 'displayfield',
								id : "info-price-discount",
								fieldLabel : "整体折扣率",
								value : '',
								colspan : 3
							}, Ext.create('Ext.grid.Panel', {
										title : '收入',
										id : 'info-received-grid',
										width : 500,
										height : 400,
										multiSelect : false,
										colspan : 2,
										hideHeaders : true,
										store : Ext.create('Ext.data.Store', {
													fields : ['AmoutName',
															'DiscountName', {
																name : 'Amount',
																type : 'float'
															}, {
																name : 'Discount',
																type : 'float'
															}]
												}),
										// grid columns
										columns : [{
													dataIndex : 'AmoutName',
													width : 150,
													flex : 1,
													sortable : false
												}, {
													dataIndex : 'Amount',
													width : 150,
													flex : 1,
													sortable : false
												}, {
													dataIndex : 'DiscountName',
													width : 150,
													flex : 1,
													sortable : false
												}, {
													dataIndex : 'Discount',
													width : 150,
													flex : 1,
													sortable : false
												}]
									}), Ext.create('Ext.grid.Panel', {
										title : '支出',
										id : 'info-paid-grid',
										width : 500,
										height : 400,
										multiSelect : false,
										colspan : 2,
										hideHeaders : true,
										store : Ext.create('Ext.data.Store', {
													fields : ['AmoutName',
															'DiscountName', {
																name : 'Amount',
																type : 'float'
															}, {
																name : 'Discount',
																type : 'float'
															}]
												}),
										// grid columns
										columns : [{
													dataIndex : 'AmoutName',
													width : 150,
													flex : 1,
													sortable : false
												}, {
													dataIndex : 'Amount',
													width : 150,
													flex : 1,
													sortable : false
												}, {
													dataIndex : 'DiscountName',
													width : 150,
													flex : 1,
													sortable : false
												}, {
													dataIndex : 'Discount',
													width : 150,
													flex : 1,
													sortable : false
												}]
									})]
				});
		if (!infoWin) {
			infoWin = Ext.widget('window', {
						title : '报表信息',
						closeAction : 'hide',
						width : '100%',
						height : 600,
						x : 0,
						y : 0,
						resizable : true,
						modal : true,
						items : infoPanel,
						buttons : [{
									text : "返回",
									handler : function() {
										infoWin.hide();
									}
								}]
					});
		}
		infoWin.show();
	}
	// 生成报表
	function genericReportHandler() {
		genericReportForm();
	}
	var winNew;
	// 报表信息窗口
	function genericReportForm() {
		if (!winNew) {
			var info = Ext.create('Ext.form.Panel', {
						layout : {
							type : 'table',
							columns : 2
						},
						bodyPadding : 2,
						items : [Ext.create('Ext.form.field.ComboBox', {
											fieldLabel : '店铺',
											displayField : 'name',
											valueField : 'value',
											store : shopstore,
											queryMode : 'remote',
											editable : false,
											allowBlank : false,
											name : "storeId",
											id : "i-store-id",
											margin : "5 5 5 10"
										}), {
									xtype : 'fieldcontainer',
									fieldLabel : '对账月份',
									combineErrors : true,
									msgTarget : 'side',
									layout : 'hbox',
									width : 280,
									margin : "5 5 5 10",
									defaults : {
										flex : 1,
										hideLabel : true
									},
									items : [{
												xtype : 'numberfield',
												fieldLabel : '年',
												id : 'i-year',
												allowBlank : false,
												value : 2012,
												minValue : 2010,
												maxValue : 2020
											}, {
												xtype : 'numberfield',
												fieldLabel : '月份',
												id : 'i-month',
												allowBlank : false,
												value : 1,
												minValue : 1,
												maxValue : 12
											}]
								}, {
									xtype : 'fieldcontainer',
									fieldLabel : '支付宝时间',
									colspan : 2,
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
												name : 'alipayFromDate',
												id : 'i-alipay-from-date',
												allowBlank : false,
												fieldLabel : '开始时间',
												margin : '0 5 0 0'
											}, {
												xtype : 'datefield',
												name : 'alipayEndDate',
												id : 'i-alipay-end-date',
												allowBlank : false,
												fieldLabel : '结束时间'
											}]
								}]
					});

			winNew = Ext.widget('window', {
				closeAction : 'hide',
				title : '生成报表',
				width : 600,
				height : 140,
				layout : 'fit',
				resizable : true,
				modal : true,
				items : info,
				buttons : [{
					text : "确定",
					handler : function() {
						if (!info.getForm().isValid())
							return;
						Ext.Msg.wait('处理中，请稍后...', '提示');
						winNew.hide();
						Ext.Ajax.request({
							url : 'genericTaobaoAccountReport',
							timeout : 3600000,
							params : {
								crumb : Ext.get('crumb').getValue(),
								storeId : Ext.getCmp("i-store-id").getValue(),
								month : Ext.getCmp("i-year").getValue() + '-'
										+ Ext.getCmp("i-month").getValue(),
								alipayFromDate : Ext
										.getCmp("i-alipay-from-date")
										.getValue(),
								alipayEndDate : Ext.getCmp("i-alipay-end-date")
										.getValue()
							},
							success : function(response, options) {
								Ext.Msg.hide();
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

					}
				}, {
					text : "取消",
					handler : function() {
						info.getForm().reset();
						winNew.hide();
					}
				}]
			});
		}

		winNew.show();
	}
	function exportReportHandler() {
		var row = resultGrid.getSelectionModel().getSelection()[0];
		if (!row) {

			Ext.MessageBox.alert('提示', "请选择一条记录!");
			return;
		}
		Ext.Msg.wait('处理中，请稍后...', '提示');
		Ext.Ajax.request({
					url : 'exportTaobaoAccountReport',
					timeout : 3600000,
					params : {
						accountId : row.get("accountId")
					},
					success : function(response, options) {
						Ext.Msg.hide();
						var text = unicodeToString(response.responseText);
						var responseArray = Ext.JSON.decode(text);
						if (Ext.String.trim(responseArray.exportUrl).length > 0) {
							window.open(responseArray.exportUrl);
						} else {
							Ext.MessageBox.alert("错误", responseArray.message);
						}
					}
				});
	}
});