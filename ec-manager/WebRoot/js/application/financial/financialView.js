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
									allowBlank : false,
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
										allowBlank : false,
												value : 2012,
										minValue : 2010,
										maxValue : 2020
									}, {
										xtype : 'numberfield',
										fieldLabel : '月份',
										id : 's-month',
										allowBlank : false,
										value : 1,
										minValue : 1,
										maxValue : 12
									}]
						}],
				buttons : [{
							text : "查看报表",
							handler : viewReportHandler
						}, {
							text : "清空",
							handler : function() {
								searchPanel.getForm().reset();
							}
						}],
				renderTo : 'search-panel'
			});
	var infoPanel = Ext.create('Ext.form.Panel', {
				width : '100%',
				renderTo : 'info-panel',
				layout : {
					type : 'table',
					columns : 4
				},
				hidden : true,
				animCollapse : true,
				bodyPadding : 2,
				items : [{
							xtype : 'hiddenfield',
							id : "info-account_id"
						},{
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
									width : (Ext.get("component").getWidth()-4)/2,
									height : 400,
									multiSelect : false,
									colspan : 2,
									hideHeaders : true,
									store : Ext.create('Ext.data.Store', {
												fields : ['AmoutName',
														'DiscountName', {
															name : 'Amount'
														}, { 
															name : 'Discount'
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
									width : (Ext.get("component").getWidth()-4)/2,
									height : 400,
									multiSelect : false,
									colspan : 2,
									hideHeaders : true,
									store : Ext.create('Ext.data.Store', {
												fields : ['AmoutName',
														'DiscountName', {
															name : 'Amount'
														}, {
															name : 'Discount'
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
								})],
				buttons : [{
							text : "刷新报表",
							handler : refreshReportHandler
						}, {
							text : "导出报表",
							handler : exportReportHandler
						}]
			});
	function viewReportHandler() {
		if (!infoPanel.getForm().isValid())
			return;
		infoPanel.show();
		Ext.Ajax.request({
			url : 'taobaoAccountHeadInfo',
			params : {
				storeId : Ext.getCmp("s-store-id").getValue(),
				month : Ext.getCmp("s-year").getValue() + '-'
						+ Ext.getCmp("s-month").getValue()
			},
			waitMsg : '正在加载信息...',
			success : function(response, options) {
				var text = unicodeToString(response.responseText);
				var responseArray = Ext.JSON.decode(text);
				if (responseArray.success) {
					Ext.getCmp("info-account_id").setValue(responseArray.taobaoAccountInfo.AccountId);
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
					refreshReportHandler();
					
				}
			}
		});
	}
	function refreshReportHandler() {
		if (!infoPanel.getForm().isValid())
			return;
		Ext.Msg.wait('处理中，请稍后...', '提示');
		Ext.Ajax.request({
					url : 'genericTaobaoAccountReport',
					timeout : 3600000,
					params : {
						crumb : Ext.get('crumb').getValue(),
						storeId : Ext.getCmp("s-store-id").getValue(),
						month : Ext.getCmp("s-year").getValue() + '-'
								+ Ext.getCmp("s-month").getValue()
					},
					success : function(response, options) {
						Ext.Msg.hide();
						var text = unicodeToString(response.responseText);
						var responseArray = Ext.JSON.decode(text);
						if (responseArray.success) {
							Ext.MessageBox.alert('提示', "处理成功!");
							viewReportHandler();
						} else {
							Ext.MessageBox.alert("错误", responseArray.message);
						}
					}
				});
	}
	function exportReportHandler() {
		var accountId = Ext.getCmp("info-account_id").getValue();
		if (accountId==null) {

			Ext.MessageBox.alert('提示', "没有报表信息!");
			return;
		}
		Ext.Msg.wait('处理中，请稍后...', '提示');
		Ext.Ajax.request({
					url : 'exportTaobaoAccountReport',
					timeout : 3600000,
					params : {
						accountId : accountId
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