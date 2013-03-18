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
			columns : '2',
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
						}), {
					xtype : 'fieldcontainer',
					fieldLabel : '退款申请时间',
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
					xtype : 'fieldcontainer',
					fieldLabel : '退款确认时间',
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
								name : 'completeFromDate',
								id : 's-complete-from-date',
								fieldLabel : '开始时间',
								margin : '0 5 0 0'
							}, {
								xtype : 'datefield',
								name : 'completeEndDate',
								id : 's-complete-end-date',
								fieldLabel : '结束时间'
							}]
				}],
		buttons : [{
			text : "生成退款报表",
			handler : function() {
				var storeId = Ext.getCmp("s-store-id").getValue();
				if (storeId == null || storeId.length == 0) {
					alert("请选择店铺");
					return;
				}
				var fromDate = Ext.getCmp("s-order-from-date").getValue();
				var endDate = Ext.getCmp("s-order-end-date").getValue();
				var completeFromDate = Ext.getCmp("s-complete-from-date")
						.getValue();
				var completeEndDate = Ext.getCmp("s-complete-end-date")
						.getValue();
				if ((fromDate == null) && (endDate == null)
						&& (completeFromDate == null)
						&& (completeEndDate == null)) {
					alert("请选择时间");
					return;
				}
				Ext.Msg.wait('处理中，请稍后...', '提示');
				Ext.Ajax.request({
							url : 'downloadRefundReportAction',
							timeout : 3600000,
							params : {
								storeId : storeId,
								fromDate : fromDate,
								endDate : endDate,
								completeFromDate : completeFromDate,
								completeEndDate : completeEndDate
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
			text : "SKU别退款报表",
			handler : function() {
				var storeId = Ext.getCmp("s-store-id").getValue();
				if (storeId == null || storeId.length == 0) {
					alert("请选择店铺");
					return;
				}
				var fromDate = Ext.getCmp("s-order-from-date").getValue();
				var endDate = Ext.getCmp("s-order-end-date").getValue();
				var completeFromDate = Ext.getCmp("s-complete-from-date")
						.getValue();
				var completeEndDate = Ext.getCmp("s-complete-end-date")
						.getValue();
				if ((fromDate == null) && (endDate == null)
						&& (completeFromDate == null)
						&& (completeEndDate == null)) {
					alert("请选择时间");
					return;
				}
				Ext.Msg.wait('处理中，请稍后...', '提示');
				Ext.Ajax.request({
							url : 'downloadRefundReportBySkuAction',
							timeout : 3600000,
							params : {
								storeId : storeId,
								fromDate : fromDate,
								endDate : endDate,
								completeFromDate : completeFromDate,
								completeEndDate : completeEndDate
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
		}],
		renderTo : 'search-panel'
	});
	// 查询结果grid
	var gridHeight = getWindownHeight() - searchPanel.getHeight();

	Ext.define('SearchModel', {
				extend : 'Ext.data.Model',
				fields : ['fileName', 'fileDate', 'filePath', 'fileUrl']
			});

	// create the Data Store
	var store = Ext.create('Ext.data.Store', {
		model : 'SearchModel',
		autoDestroy : true,
		remoteSort : true,
		proxy : new Ext.data.HttpProxy({
			// load using script tags for cross domain,
			// if the
			// data in on the same domain as
			// this page, an HttpProxy would be better
			type : 'jsonp',
			url : 'reportFileListAction?filePath=report.refund.path&fileUrl=report.refund.url',
			reader : {
				root : 'fileList',
				totalProperty : 'rowCount',
				fields : ['fileName', 'fileDate', 'filePath', 'fileUrl']
			},
			// sends single sort as multi parameter
			simpleSortMode : true
		})
	});
	var resultGrid = Ext.create('Ext.grid.Panel', {
				title : '文件列表',
				region : 'center',
				height : gridHeight,
				store : store,
				multiSelect : false,
				// grid columns
				columns : [{
							xtype : 'rownumberer'
						}, {
							header : "文件",
							dataIndex : 'fileName',
							width : 100,
							flex : 1,
							sortable : false
						}, {
							header : "时间",
							dataIndex : 'fileDate',
							width : 200,
							sortable : true
						}, {
							header : "下载地址",
							dataIndex : 'fileUrl',
							width : 500,
							sortable : true
						}],
				// paging bar on the bottom
				bbar : Ext.create('Ext.PagingToolbar', {
							store : store,
							displayInfo : false,
							displayMsg : '显示 {0} - {1}/共{2}条',
							emptyMsg : "没有查询结果"
						}),
				renderTo : 'result-grid'
			});
	resultGrid.child('pagingtoolbar').add(['->', {
				iconCls : 'icon-add',
				text : '下载',
				scope : this,
				handler : function() {
					var row = resultGrid.getSelectionModel().getSelection()[0];
					if (!row) {

						Ext.MessageBox.alert('提示', "请选择一条记录!");
						return;
					}
					window.open(row.get('fileUrl'));
				}
			}, {
				iconCls : 'icon-delete',
				text : '删除',
				scope : this,
				handler : function() {
					var row = resultGrid.getSelectionModel().getSelection()[0];
					if (!row) {

						Ext.MessageBox.alert('提示', "请选择一条记录!");
						return;
					}
					Ext.Ajax.request({
								url : 'fileDeleteAction',
								params : {
									filePath : 'report.refund.path',
									deleteFileName : row.get('fileName')
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
				}
			}]);
	// trigger the data store load
	store.load();

	// 刷新当前页查询结果
	function refreshGrid() {
		store.load();
	}

});