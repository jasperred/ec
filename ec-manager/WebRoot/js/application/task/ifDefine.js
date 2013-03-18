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
	var gridHeight = 330;
	var searchPanelHeight = 180;
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
	var ifDefineTypeStore = Ext.create('Ext.data.Store', {
				model : 'combo_model',
				remoteSort : true,
				proxy : {
					type : 'ajax',
					url : 'ifDefineTypeList'
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
				height : searchPanelHeight,
				defaultType : 'textfield',
				fieldDefaults : {
					msgTarget : 'side',
					labelAlign : 'left'
				},
				items : [{
							xtype : 'textfield',
							fieldLabel : '接口编号',
							margin : '5 5 5 10',
							id : "s-if-code"
						}, {
							xtype : 'textfield',
							fieldLabel : '接口名称',
							margin : '5 5 5 10',
							id : "s-if-name"
						}, Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '状态',
									displayField : 'name',
									valueField : 'value',
									store : Ext.create('Ext.data.Store', {
												model : 'combo_model',
												data : [{
															"name" : "有效",
															"value" : "1"
														}, {
															"name" : "无效",
															"value" : "-1"
														}]
											}),
									queryMode : 'local',
									editable : false,
									id : "s-status",
									margin : "5 5 5 10"
								}), Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '接口类型',
									displayField : 'name',
									valueField : 'value',
									store : ifDefineTypeStore,
									queryMode : 'remote',
									editable : false,
									id : "s-if-type",
									margin : "5 5 5 10"
								})],
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
				fields : ['IfDefineId', 'ClassName', 'ClassType', 'IfCode', {
							name : 'ErrorRetryTimes',
							type : 'int'
						}, 'IfName', 'IfType', {
							name : 'RetryWaitSeconds',
							type : 'int'
						}, 'RunMethod', 'Status'],
				idProperty : 'IfDefineId'
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
							url : 'ifDefineSearch',
							reader : {
								root : 'resultList',
								totalProperty : 'rowCount',
								fields : ['IfDefineId', 'ClassName',
										'ClassType', 'IfCode',
										'ErrorRetryTimes', 'IfName', 'IfType',
										'RetryWaitSeconds', 'RunMethod',
										'Status']
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
							header : "接口名称",
							dataIndex : 'IfName',
							width : 100,
							flex : 1,
							sortable : false
						}, {
							header : "接口编号",
							dataIndex : 'IfCode',
							width : 100,
							flex : 1,
							sortable : false
						}, {
							header : "接口类型",
							dataIndex : 'IfType',
							width : 100,
							sortable : true
						}, {
							header : "类名",
							dataIndex : 'ClassName',
							width : 100,
							align : 'right',
							sortable : true
						}, {
							header : "类方式",
							dataIndex : 'ClassType',
							width : 100,
							align : 'right',
							sortable : true
						}, {
							header : "方法",
							dataIndex : 'RunMethod',
							width : 100,
							align : 'right',
							sortable : true
						}, {
							header : "错误重试次数",
							dataIndex : 'ErrorRetryTimes',
							width : 100,
							align : 'right',
							sortable : true
						}, {
							header : "重试等待时间",
							dataIndex : 'RetryWaitSeconds',
							width : 100,
							sortable : true
						}, {
							header : "状态",
							dataIndex : 'Status',
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
				text : '新增接口',
				scope : this,
				handler : newIfDefineHandler
			}, {
				text : '修改接口',
				scope : this,
				handler : updateIfDefineHandler
			}, {
				text : '删除接口',
				scope : this,
				handler : deleteIfDefineHandler
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
						ifCode : Ext.getCmp("s-if-code").getValue(),
						ifName : Ext.getCmp("s-if-name").getValue(),
						ifType : Ext.getCmp("s-if-type").getValue(),
						status : Ext.getCmp("s-status").getValue()
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
	// 新增接口
	function newIfDefineHandler() {
		showIfDefineForm();
	}
	// 修改接口
	function updateIfDefineHandler() {
		var row = resultGrid.getSelectionModel().getSelection()[0];
		if (!row) {

			Ext.MessageBox.alert('提示', "请选择一条记录!");
			return;
		}
		showIfDefineForm();
		ifDefineForm.getForm().loadRecord(row);
	}
	var ifDefineWin;// 接口定义界面
	var ifDefineForm;
	function showIfDefineForm() {
		if (!ifDefineWin) {
			ifDefineForm = Ext.create('Ext.form.Panel', {
						defaultType : 'textfield',
						bodyStyle : 'padding:5px 5px 0',
						defaults : {
							anchor : '100%'
						},
						border : false,
						layout: {
					        type: 'vbox',
					        align: 'stretch'
					    },
						items : [{
									xtype : 'hidden',
									fieldLabel : '接口ID',
									name : 'IfDefineId',
									id : 'info-if-define-id'
								}, {
									fieldLabel : '接口名称',
									name : 'IfName',
									allowBlank : false,
									id : 'info-if-name'
								}, {
									fieldLabel : '接口编号',
									name : 'IfCode',
									allowBlank : false,
									id : 'info-if-code'
								}, Ext.create('Ext.form.field.ComboBox', {
											fieldLabel : '接口类型',
											displayField : 'name',
											valueField : 'value',
											store : ifDefineTypeStore,
											queryMode : 'remote',
											editable : false,
											name : 'IfType',
											allowBlank : false,
											id : 'info-if-type'
										}), {
									fieldLabel : '类名',
									name : 'ClassName',
									allowBlank : false,
									id : 'info-class-name'
								}, Ext.create('Ext.form.field.ComboBox', {
											fieldLabel : '类方式',
											displayField : 'name',
											valueField : 'value',
											store : Ext.create(
													'Ext.data.Store', {
														model : 'combo_model',
														data : [{
																	"name" : "Spring Id",
																	"value" : "Spring"
																}, {
																	"name" : "类名",
																	"value" : "ClassName"
																}]
													}),
											queryMode : 'local',
											editable : false,
											allowBlank : false,
											name : 'ClassType',
											id : 'info-class-type'
										}), {
									fieldLabel : '方法',
									name : 'RunMethod',
									allowBlank : false,
									id : 'info-run-method'
								}, {
									fieldLabel : '错误重试次数',
									name : 'ErrorRetryTimes',
									id : 'info-error-retry-times'
								}, {
									fieldLabel : '重试等待时间',
									name : 'RetryWaitSeconds',
									id : 'info-retry-wait-seconds'
								}, Ext.create('Ext.form.field.ComboBox', {
											fieldLabel : '状态',
											displayField : 'name',
											valueField : 'value',
											store : Ext.create(
													'Ext.data.Store', {
														model : 'combo_model',
														data : [{
																	"name" : "有效",
																	"value" : "1"
																}, {
																	"name" : "无效",
																	"value" : "-1"
																}]
													}),
											queryMode : 'local',
											editable : false,
											allowBlank : false,
											name : 'Status',
											id : 'info-status'
										})]
					});
			ifDefineWin = Ext.widget('window', {
				title : '接口信息',
				closeAction : 'hide',
				width : 500,
				height : 330,
				layout : 'fit',
				resizable : true,
				modal : true,
				items : ifDefineForm,
				buttons : [{
					text : "保存",
					handler : function() {
						if (!ifDefineForm.getForm().isValid())
							return;
						Ext.Ajax.request({
							url : 'ifDefineSave',
							params : {
								crumb : Ext.get('crumb').getValue(),
								ifDefineId : Ext.getCmp('info-if-define-id')
										.getValue(),
								ifCode : Ext.getCmp('info-if-code').getValue(),
								ifName : Ext.getCmp('info-if-name').getValue(),
								status : Ext.getCmp('info-status').getValue(),
								errorRetryTimes : Ext
										.getCmp('info-error-retry-times')
										.getValue(),
								retryWaitSeconds : Ext
										.getCmp('info-retry-wait-seconds')
										.getValue(),
								runMethod : Ext.getCmp('info-run-method')
										.getValue(),
								className : Ext.getCmp('info-class-name')
										.getValue(),
								ifType : Ext.getCmp('info-if-type').getValue(),
								classType : Ext.getCmp('info-class-type')
										.getValue()
							},
							success : function(response, options) {
								var text = unicodeToString(response.responseText);
								var responseArray = Ext.JSON.decode(text);
								if (responseArray.success) {
									Ext.MessageBox.alert('提示', "处理成功!");
									ifDefineForm.getForm().reset();
									ifDefineWin.hide();
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
						ifDefineForm.getForm().reset();
						ifDefineWin.hide();
					}
				}]
			});
		}
		ifDefineWin.show();
	}
	// 删除接口
	function deleteIfDefineHandler() {
		var row = resultGrid.getSelectionModel().getSelection()[0];
		if (!row) {

			Ext.MessageBox.alert('提示', "请选择一条记录!");
			return;
		}
		Ext.MessageBox.confirm('确认', '是否删除?', deleteIfDefine)

	}
	function deleteIfDefine(btn) {
		if (btn == 'yes') {
			Ext.Msg.wait('处理中，请稍后...', '提示');
			Ext.Ajax.request({
				url : 'ifDefineDelete',
				params : {
					crumb : Ext.get('crumb').getValue(),
					ifDefineId : resultGrid.getSelectionModel().getSelection()[0]
							.get('IfDefineId')
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
		} else {
			return;
		}
	}

});