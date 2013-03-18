Ext.Loader.setConfig({
			enabled : true
		});

Ext.Loader.setPath('Ext.ux', 'js/ext/ux/');
Ext
		.require(['Ext.grid.*', 'Ext.data.*', 'Ext.util.*',
				'Ext.ux.TabScrollerMenu']);
Ext.onReady(function() {
	Ext.tip.QuickTipManager.init();
	Ext.QuickTips.init();
	Ext.define('SearchModel', {
				extend : 'Ext.data.Model',
				fields : ['fileName', 'fileDate', 'filePath', 'fileUrl']
			});

	// create the Data Store
	var deliver_store = Ext.create('Ext.data.Store', {
		model : 'SearchModel',
		autoDestroy : true,
		remoteSort : true,
		proxy : new Ext.data.HttpProxy({
			// load using script tags for cross domain,
			// if the
			// data in on the same domain as
			// this page, an HttpProxy would be better
			type : 'jsonp',
			url : 'fileListAction?filePath=order.to_erp.path&fileUrl=order.to_erp.url',
			reader : {
				root : 'fileList',
				fields : ['fileName', 'fileDate', 'filePath', 'fileUrl']
			},
			// sends single sort as multi parameter
			simpleSortMode : true
		})
	});
	var deliver_return_store = Ext.create('Ext.data.Store', {
		model : 'SearchModel',
		autoDestroy : true,
		remoteSort : true,
		proxy : new Ext.data.HttpProxy({
			// load using script tags for cross domain,
			// if the
			// data in on the same domain as
			// this page, an HttpProxy would be better
			type : 'jsonp',
			url : 'fileListAction?filePath=order.to_wms.path&fileUrl=order.to_wms.url',
			reader : {
				root : 'fileList',
				fields : ['fileName', 'fileDate', 'filePath', 'fileUrl']
			},
			// sends single sort as multi parameter
			simpleSortMode : true
		})
	});
	var return_store = Ext.create('Ext.data.Store', {
		model : 'SearchModel',
		autoDestroy : true,
		remoteSort : true,
		proxy : new Ext.data.HttpProxy({
			// load using script tags for cross domain,
			// if the
			// data in on the same domain as
			// this page, an HttpProxy would be better
			type : 'jsonp',
			url : 'fileListAction?filePath=refund.import.path&fileUrl=refund.import.url',
			reader : {
				root : 'fileList',
				fields : ['fileName', 'fileDate', 'filePath', 'fileUrl']
			},
			// sends single sort as multi parameter
			simpleSortMode : true
		})
	});
	var return_return_store = Ext.create('Ext.data.Store', {
		model : 'SearchModel',
		autoDestroy : true,
		remoteSort : true,
		proxy : new Ext.data.HttpProxy({
			// load using script tags for cross domain,
			// if the
			// data in on the same domain as
			// this page, an HttpProxy would be better
			type : 'jsonp',
			url : 'fileListAction?filePath=refund.export.path&fileUrl=refund.export.url',
			reader : {
				root : 'fileList',
				fields : ['fileName', 'fileDate', 'filePath', 'fileUrl']
			},
			// sends single sort as multi parameter
			simpleSortMode : true
		})
	});
	var inv_store = Ext.create('Ext.data.Store', {
		model : 'SearchModel',
		autoDestroy : true,
		remoteSort : true,
		proxy : new Ext.data.HttpProxy({
			// load using script tags for cross domain,
			// if the
			// data in on the same domain as
			// this page, an HttpProxy would be better
			type : 'jsonp',
			url : 'fileListAction?filePath=inventory.import.path&fileUrl=inventory.url',
			reader : {
				root : 'fileList',
				fields : ['fileName', 'fileDate', 'filePath', 'fileUrl']
			},
			// sends single sort as multi parameter
			simpleSortMode : true
		})
	});
	var deliverPanel = Ext.create('Ext.form.Panel', {
		width : 500,
		frame : true,
		title : '发货实绩文件上传',
		bodyPadding : '10 10 0',
		listeners : {
			activate : function(tab) {
				deliver_store.load();
			}
		},

		items : [Ext.create('Ext.form.Panel', {
					id : 'deliver-form',
					frame : true, // 设置窗体为圆角
					layout : 'hbox',
					height : 100,
					items : [{
								xtype : 'hidden',
								name : 'filePath',
								value : 'order.to_erp.path'
							}, {
								xtype : 'filefield',
								fieldLabel : '文件',
								name : 'uploadFile',
								width : 400,
								margin : '5 5 5 10'
							}, {
								xtype : 'button',
								text : '上传',
								width : 100,
								handler : function() {
									var form = Ext.getCmp('deliver-form')
											.getForm();
									if (form.isValid()) {
										form.submit({
													method : "POST",
													url : 'fileUploadAction',
													waitMsg : '文件正在上传...',
													success : function(
															response, options) {
														Ext.MessageBox.alert(
																'提示', "处理成功!");
														deliver_store.load();
													},
													failure : function(form,
															action) {
														Ext.MessageBox
																.alert(
																		'错误',
																		action.result.message);
													}
												});
									}
								},
								margin : '5 5 5 10'
							}]
				}), Ext.create('Ext.grid.Panel', {
					title : '文件列表',
					id : 'deliver-grid',
					height : getWindownHeight()-200,
					store : deliver_store,
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
							}]
				})],

		buttons : [{
			text : '删除文件',
			handler : function() {
				var resultGrid = Ext.getCmp('deliver-grid');
				var row = resultGrid.getSelectionModel().getSelection()[0];
				if (!row) {

					Ext.MessageBox.alert('提示', "请选择一条记录!");
					return;
				}
				Ext.Ajax.request({
							url : 'fileDeleteAction',
							params : {
								filePath : 'order.to_erp.path',
								deleteFileName : row.get('fileName')
							},
							success : function(response, options) {
								var text = unicodeToString(response.responseText);
								var responseArray = Ext.JSON.decode(text);
								if (responseArray.success) {
									Ext.MessageBox.alert('提示', "处理成功!");
									resultGrid.getStore().load();
								} else {
									Ext.MessageBox.alert("错误",
											responseArray.message);
								}
							}
						});
			}
		}, {
			text : '下载文件',
			handler : function() {
				var resultGrid = Ext.getCmp('deliver-grid');
				var row = resultGrid.getSelectionModel().getSelection()[0];
				if (!row) {

					Ext.MessageBox.alert('提示', "请选择一条记录!");
					return;
				}
				window.open(row.get('fileUrl'));
			}
		}]
	});
	var deliverReturnPanel = Ext.create('Ext.form.Panel', {
		width : 500,
		frame : true,
		title : '出库指示文件',
		bodyPadding : '10 10 0',
		listeners : {
			activate : function(tab) {
				deliver_return_store.load();
			}
		},

		items : [ Ext.create('Ext.grid.Panel', {
					title : '文件列表',
					id : 'deliver-return-grid',
					height : getWindownHeight()-100,
					store : deliver_return_store,
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
							}]
				})],

		buttons : [{
			text : '删除文件',
			handler : function() {
				var resultGrid = Ext.getCmp('deliver-return-grid');
				var row = resultGrid.getSelectionModel().getSelection()[0];
				if (!row) {

					Ext.MessageBox.alert('提示', "请选择一条记录!");
					return;
				}
				Ext.Ajax.request({
							url : 'fileDeleteAction',
							params : {
								filePath : 'order.to_wms.path',
								deleteFileName : row.get('fileName')
							},
							success : function(response, options) {
								var text = unicodeToString(response.responseText);
								var responseArray = Ext.JSON.decode(text);
								if (responseArray.success) {
									Ext.MessageBox.alert('提示', "处理成功!");
									resultGrid.getStore().load();
								} else {
									Ext.MessageBox.alert("错误",
											responseArray.message);
								}
							}
						});
			}
		}, {
			text : '下载文件',
			handler : function() {
				var resultGrid = Ext.getCmp('deliver-return-grid');
				var row = resultGrid.getSelectionModel().getSelection()[0];
				if (!row) {

					Ext.MessageBox.alert('提示', "请选择一条记录!");
					return;
				}
				window.open(row.get('fileUrl'));
			}
		}]
	});
	var returnPanel = Ext.create('Ext.form.Panel', {
		width : 500,
		frame : true,
		title : '退货实绩文件上传',
		bodyPadding : '10 10 0',
		listeners : {
			activate : function(tab) {
				return_store.load();
			}
		},
		items : [Ext.create('Ext.form.Panel', {
					id : 'return-form',
					frame : true, // 设置窗体为圆角
					layout : 'hbox',
					method : "POST",
					height : 100,
					items : [{
								xtype : 'hidden',
								name : 'filePath',
								value : 'refund.import.path'
							}, {
								xtype : 'filefield',
								fieldLabel : '文件',
								name : 'uploadFile',
								width : 400,
								margin : '5 5 5 10'
							}, {
								xtype : 'button',
								text : '上传',
								width : 100,
								handler : function() {
									var form = Ext.getCmp('return-form')
											.getForm();
									if (form.isValid()) {
										form.submit({
													method : "POST",
													url : 'fileUploadAction',
													waitMsg : '文件正在上传...',
													success : function(
															response, options) {
														Ext.MessageBox.alert(
																'提示', "处理成功!");
														return_store.load();
													}
												});
									}
								},
								margin : '5 5 5 10'
							}]
				}), Ext.create('Ext.grid.Panel', {
					title : '文件列表',
					id : 'return-grid',
					height : getWindownHeight()-200,
					store : return_store,
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
							}]
				})],

		buttons : [{
			text : '删除文件',
			handler : function() {
				var resultGrid = Ext.getCmp('return-grid');
				var row = resultGrid.getSelectionModel().getSelection()[0];
				if (!row) {

					Ext.MessageBox.alert('提示', "请选择一条记录!");
					return;
				}
				Ext.Ajax.request({
							url : 'fileDeleteAction',
							params : {
								filePath : 'refund.import.path',
								deleteFileName : row.get('fileName')
							},
							success : function(response, options) {
								var text = unicodeToString(response.responseText);
								var responseArray = Ext.JSON.decode(text);
								if (responseArray.success) {
									Ext.MessageBox.alert('提示', "处理成功!");
									resultGrid.getStore().load();
								} else {
									Ext.MessageBox.alert("错误",
											responseArray.message);
								}
							}
						});
			}
		}, {
			text : '下载文件',
			handler : function() {
				var resultGrid = Ext.getCmp('return-grid');
				var row = resultGrid.getSelectionModel().getSelection()[0];
				if (!row) {

					Ext.MessageBox.alert('提示', "请选择一条记录!");
					return;
				}
				window.open(row.get('fileUrl'));
			}
		}]
	});
	var returnReturnPanel = Ext.create('Ext.form.Panel', {
		width : 500,
		frame : true,
		title : '退货指示文件',
		bodyPadding : '10 10 0',
		listeners : {
			activate : function(tab) {
				return_return_store.load();
			}
		},
		items : [ Ext.create('Ext.grid.Panel', {
					title : '文件列表',
					id : 'return-return-grid',
					height : getWindownHeight()-100,
					store : return_return_store,
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
							}]
				})],

		buttons : [{
			text : '删除文件',
			handler : function() {
				var resultGrid = Ext.getCmp('return-return-grid');
				var row = resultGrid.getSelectionModel().getSelection()[0];
				if (!row) {

					Ext.MessageBox.alert('提示', "请选择一条记录!");
					return;
				}
				Ext.Ajax.request({
							url : 'fileDeleteAction',
							params : {
								filePath : 'refund.export.path',
								deleteFileName : row.get('fileName')
							},
							success : function(response, options) {
								var text = unicodeToString(response.responseText);
								var responseArray = Ext.JSON.decode(text);
								if (responseArray.success) {
									Ext.MessageBox.alert('提示', "处理成功!");
									resultGrid.getStore().load();
								} else {
									Ext.MessageBox.alert("错误",
											responseArray.message);
								}
							}
						});
			}
		}, {
			text : '下载文件',
			handler : function() {
				var resultGrid = Ext.getCmp('return-return-grid');
				var row = resultGrid.getSelectionModel().getSelection()[0];
				if (!row) {

					Ext.MessageBox.alert('提示', "请选择一条记录!");
					return;
				}
				window.open(row.get('fileUrl'));
			}
		}]
	});
	var invPanel = Ext.create('Ext.form.Panel', {
		width : 500,
		frame : true,
		title : '库存导入文件上传',
		bodyPadding : '10 10 0',
		listeners : {
			activate : function(tab) {
				inv_store.load();
			}
		},
		items : [Ext.create('Ext.form.Panel', {
					id : 'inv-form',
					frame : true, // 设置窗体为圆角
					layout : 'hbox',
					method : "POST",
					height : 100,
					items : [{
								xtype : 'hidden',
								name : 'filePath',
								value : 'inventory.import.path'
							}, {
								xtype : 'filefield',
								fieldLabel : '文件',
								name : 'uploadFile',
								width : 400,
								margin : '5 5 5 10'
							}, {
								xtype : 'button',
								text : '上传',
								width : 100,
								handler : function() {
									var form = Ext.getCmp('inv-form').getForm();
									if (form.isValid()) {
										form.submit({
													method : "POST",
													url : 'fileUploadAction',
													waitMsg : '文件正在上传...',
													success : function(
															response, options) {
														Ext.MessageBox.alert(
																'提示', "处理成功!");
														inv_store.load();
													}
												});
									}
								},
								margin : '5 5 5 10'
							}]
				}), Ext.create('Ext.grid.Panel', {
					title : '文件列表',
					id : 'inv-grid',
					height : getWindownHeight()-200,
					store : inv_store,
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
							}]
				})],

		buttons : [{
			text : '删除文件',
			handler : function() {
				var resultGrid = Ext.getCmp('inv-grid');
				var row = resultGrid.getSelectionModel().getSelection()[0];
				if (!row) {

					Ext.MessageBox.alert('提示', "请选择一条记录!");
					return;
				}
				Ext.Ajax.request({
							url : 'fileDeleteAction',
							params : {
								filePath : 'inventory.import.path',
								deleteFileName : row.get('fileName')
							},
							success : function(response, options) {
								var text = unicodeToString(response.responseText);
								var responseArray = Ext.JSON.decode(text);
								if (responseArray.success) {
									Ext.MessageBox.alert('提示', "处理成功!");
									resultGrid.getStore().load();
								} else {
									Ext.MessageBox.alert("错误",
											responseArray.message);
								}
							}
						});
			}
		}, {
			text : '下载文件',
			handler : function() {
				var resultGrid = Ext.getCmp('inv-grid');
				var row = resultGrid.getSelectionModel().getSelection()[0];
				if (!row) {

					Ext.MessageBox.alert('提示', "请选择一条记录!");
					return;
				}
				window.open(row.get('fileUrl'));
			}
		}]
	});
	var tabs = Ext.createWidget('tabpanel', {
				renderTo : 'content',
				width : '100%',
				activeTab : 0,
				defaults : {
					bodyPadding : 10
				},
				items : [deliverPanel,deliverReturnPanel, returnPanel,returnReturnPanel, invPanel]
			});

});