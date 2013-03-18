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
	var pageSize = 100000;
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
					columns : '3',
					align : 'center'
				},
				defaultType : 'textfield',
				fieldDefaults : {
					msgTarget : 'side',
					labelAlign : 'left'
				},
				items : [Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '类型',
									displayField : 'name',
									valueField : 'value',
									store : Ext.create('Ext.data.Store', {
												model : 'combo_model',
												data : [{
															"name" : "品牌",
															"value" : "品牌"
														}, {
															"name" : "系列",
															"value" : "系列"
														}, {
															"name" : "子系列",
															"value" : "子系列"
														}, {
															"name" : "年",
															"value" : "年"
														}, {
															"name" : "季节",
															"value" : "季节"
														}, {
															"name" : "性别",
															"value" : "性别"
														}, {
															"name" : "部门",
															"value" : "部门"
														}, {
															"name" : "细分",
															"value" : "细分"
														}]
											}),
									queryMode : 'local',
									editable : false,
									name : 'type',
									id : "s-type",
									margin : "5 5 5 10"
								}), {
							xtype : 'textfield',
							fieldLabel : '名称',
							name : 'name',
							margin : '5 5 5 10',
							id : "s-name"
						}],
				buttons : [{
							text : "新增",
							handler : newHandler
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
	var gridHeight = getWindownHeight()-searchPanel.getHeight( );

	Ext.define('SearchModel', {
				extend : 'Ext.data.Model',
				fields : ['typeId', 'name', 'value', 'type', 'parent'],
				idProperty : 'typeId'
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
							url : 'searchItemGenerateType',
							reader : {
								root : 'resultList',
								fields : ['typeId', 'name', 'value', 'type',
										'parent']
							},
							// sends single sort as multi parameter
							simpleSortMode : true
						})
			});
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
							header : "类型",
							dataIndex : 'type',
							flex : 1,
							sortable : false
						}, {
							header : "名称",
							dataIndex : 'name',
							flex : 1,
							sortable : true
						}, {
							header : "代码",
							dataIndex : 'value',
							flex : 1,
							sortable : true
						}, {
							header : "父名称",
							dataIndex : 'parent',
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
				iconCls : 'icon-add',
				text : '查看',
				scope : this,
				handler : showInfoHandler
			},{
				iconCls : 'icon-delete',
				text : '删除',
				scope : this,
				handler : deleteInfoHandler
			}]);
	resultGrid.addListener('itemdblclick', showInfoHandler, this);
	// 查询
	function searchHandler() {
		store.on('beforeload', function() { // =======翻页时 查询条件
					var new_params = {
						limit : pageSize,
						type : Ext.getCmp("s-type").getValue(),
						name : Ext.getCmp("s-name").getValue()
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
	// 新增
	function newHandler() {
		showInfoWin();
	}
	//删除
	function deleteInfoHandler() {
		var row = resultGrid.getSelectionModel().getSelection()[0];
		if (!row) {

			Ext.MessageBox.alert('提示', "请选择一条记录!");
			return;
		}
		Ext.Ajax.request({
					url : 'deleteItemGenerateType',
					params : {
						typeId : row.get("typeId")
					},
					success : function(response, options) {
						var text = unicodeToString(response.responseText);
						var responseArray = Ext.JSON.decode(text);
						var text = unicodeToString(response.responseText);
								var responseArray = Ext.JSON.decode(text);
								if (responseArray.success) {
									refreshGrid();
								} else {
									Ext.MessageBox.alert("错误",
											responseArray.message);
								}
					}
				});
	}

	function showInfoHandler() {
		var row = resultGrid.getSelectionModel().getSelection()[0];
		if (!row) {

			Ext.MessageBox.alert('提示', "请选择一条记录!");
			return;
		}
		showInfoWin();
		Ext.getCmp("info-form").getForm().loadRecord(row);
	}
	// 查看Item
	var viewInfoWin;
	function showInfoWin() {
		if (!viewInfoWin) {
			var form = Ext.create('Ext.form.Panel', {
						defaultType : 'textfield',
						bodyStyle : 'padding:5px 5px 0',
						id : 'info-form',
						defaults : {
							anchor : '100%'
						},
						border : false,
						layout : {
							type : 'vbox',
							align : 'stretch'
						},
						items : [Ext.create('Ext.form.field.ComboBox', {
											fieldLabel : '类型',
											displayField : 'name',
											valueField : 'value',
											store : Ext.create(
													'Ext.data.Store', {
														model : 'combo_model',
														data : [{
																	"name" : "品牌",
																	"value" : "品牌"
																}, {
																	"name" : "系列",
																	"value" : "系列"
																}, {
																	"name" : "子系列",
																	"value" : "子系列"
																}, {
																	"name" : "年",
																	"value" : "年"
																}, {
																	"name" : "季节",
																	"value" : "季节"
																}, {
																	"name" : "性别",
																	"value" : "性别"
																}, {
																	"name" : "部门",
																	"value" : "部门"
																}, {
																	"name" : "细分",
																	"value" : "细分"
																}]
													}),
											queryMode : 'local',
											editable : false,
											name : 'type',
											id : "info-type"
										}), {
									fieldLabel : '名称',
									name : 'name',
									id : 'info-name'
								}, {
									fieldLabel : '代码',
									name : 'value',
									id : 'info-value'
								}, {
									fieldLabel : '父名称',
									name : 'parent',
									id : 'info-parent'
								}, {
									xtype : 'hidden',
									fieldLabel : 'id',
									name : 'typeId',
									id : 'info-type-id'
								}]
					});
			viewInfoWin = Ext.widget('window', {
				title : '信息',
				closeAction : 'hide',
				width : 600,
				height : 210,
				layout : 'fit',
				resizable : true,
				modal : true,
				items : form,
				buttons : [{
					text : "保存",
					handler : function() {
						if (!form.getForm().isValid())
							return;
						Ext.Ajax.request({
							url : 'saveItemGenerateType',
							params : {
								typeId : Ext.getCmp('info-type-id').getValue(),
								type : Ext.getCmp('info-type').getValue(),
								name : Ext.getCmp('info-name').getValue(),
								value : Ext.getCmp('info-value').getValue(),
								parent : Ext.getCmp('info-parent').getValue()
							},
							success : function(response, options) {
								var text = unicodeToString(response.responseText);
								var responseArray = Ext.JSON.decode(text);
								if (responseArray.success) {
									Ext.MessageBox.alert('提示', "处理成功!");
									form.getForm().reset();
									viewInfoWin.hide();
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
						form.getForm().reset();
						viewInfoWin.hide();
					}
				}]
			});
		}
		Ext.getCmp("info-form").getForm().reset();
		viewInfoWin.show();
	}

});