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
					url : 'fileListAction?filePath=system.file.path&subDir=item_code&fileUrl=',
					reader : {
						root : 'fileList',
						fields : ['fileName', 'fileDate', 'filePath', 'fileUrl']
					},
					// sends single sort as multi parameter
					simpleSortMode : true
				})
	});
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
		title : '文件上传',
		region : 'north',
		frame : true, // 设置窗体为圆角
		method : "POST",
		autoHeight : true,
		layout : {
			type : 'table',
			columns : '4',
			align : 'center'
		},
		bodyPadding : 10,
		defaultType : 'textfield',
		fieldDefaults : {
			msgTarget : 'side',
			labelAlign : 'left'
		},
		items : [{
					xtype : 'hidden',
					name : 'filePath',
					value : 'system.file.path'
				}, {
					xtype : 'hidden',
					name : 'subDir',
					value : 'item_code'
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
						var form = Ext.getCmp('search-condition-panel')
								.getForm();
						if (form.isValid()) {
							form.submit({
										method : "POST",
										url : 'fileUploadAction',
										waitMsg : '文件正在上传...',
										success : function(response, options) {
											Ext.MessageBox.alert('提示', "处理成功!");
											resultGrid.getStore().load();
										},
										failure : function(form, action) {
											Ext.MessageBox.alert('错误',
													action.result.message);
										}
									});
						}
					},
					margin : '5 5 5 10'
				}],
		renderTo : 'search-panel'
	});
	// 查询结果grid
	var gridHeight = getWindownHeight()-searchPanel.getHeight( );
	var storeWin;
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
		text : '删除文件',
		handler : function() {
			var row = resultGrid.getSelectionModel().getSelection()[0];
			if (!row) {

				Ext.MessageBox.alert('提示', "请选择一条记录!");
				return;
			}
			Ext.Ajax.request({
						url : 'fileDeleteAction',
						params : {
							filePath : 'system.file.path',
							subDir : 'item_code',
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
		text : '导入',
		handler : function() {
			var row = resultGrid.getSelectionModel().getSelection()[0];
			if (!row) {
	
				Ext.MessageBox.alert('提示', "请选择一条记录!");
				return;
			}
			Ext.Msg.wait('处理中，请稍后...', '提示');
			Ext.Ajax.request({
						url : 'generateItemCode',
						timeout : 3600000,
						params : {
							filePath : row.get('filePath')
						},
						success : function(response, options) {
							Ext.Msg.hide();
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
	}]);
	// trigger the data store load
	store.load({
				params : {
					start : 0,
					limit : pageSize
				}
			});
});