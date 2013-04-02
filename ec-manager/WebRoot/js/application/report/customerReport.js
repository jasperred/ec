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
		items : [{
					fieldLabel : "会员编号", // 标签内容
					name : "custNo",
					id : "s-cust-no",
					margin : "5 5 5 10"
				}, {
					fieldLabel : "会员名称", // 标签内容
					name : "custName",
					id : "s-cust-name",
					margin : "5 5 5 10"
				}, {
					fieldLabel : "邮件", // 标签内容
					name : "email",
					id : "s-email",
					margin : "5 5 5 10"
				}, {
					fieldLabel : "手机号", // 标签内容
					name : "mobile",
					id : "s-mobile",
					margin : "5 5 5 10"
				}, Ext.create('Ext.form.field.ComboBox', {
							fieldLabel : '性别',
							displayField : 'name',
							valueField : 'value',
							store : Ext.create('Ext.data.Store', {
										model : 'combo_model',
										data : [{
													"name" : '全部',
													"value" : ''
												}, {
													"name" : '男',
													"value" : '男'
												}, {
													"name" : '女',
													"value" : '女'
												}]
									}),
							queryMode : 'local',
							editable : false,
							name : "sex",
							id : "s-sex",
							margin : "5 5 5 10"
						}), {
					xtype : 'fieldcontainer',
					fieldLabel : '会员生日',
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
								name : 'birthDayFrom',
								id : 's-birth-day-from',
								format : 'Y-m-d',
								fieldLabel : '',
								margin : '0 5 0 0'
							}, {
								xtype : 'datefield',
								name : 'birthDayEnd',
								id : 's-birth-day-end',
								format : 'Y-m-d',
								fieldLabel : ''
							}]
				}],
		buttons : [{
			text : "生成客户报表",
			handler : function() {
				Ext.Msg.wait('处理中，请稍后...', '提示');
				Ext.Ajax.request({
							url : 'exportCustomer',
							timeout : 3600000,
							params : {
								limit : 100000,
								custNo : Ext.getCmp("s-cust-no").getValue(),
								custName : Ext.getCmp("s-cust-name").getValue(),
								email : Ext.getCmp("s-email").getValue(),
								mobile : Ext.getCmp("s-mobile").getValue(),
								sex : Ext.getCmp("s-sex").getValue(),
								birthDayFrom : Ext.getCmp("s-birth-day-from")
										.getRawValue(),
								birthDayEnd : Ext.getCmp("s-birth-day-end")
										.getRawValue()
							},
							success : function(response, options) {
								Ext.Msg.hide();
								refreshGrid();
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
			url : 'reportFileListAction?filePath=report.customer.path&fileUrl=report.customer.url',
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