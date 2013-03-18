Ext.require(['Ext.data.*', 'Ext.grid.*', 'Ext.tree.*']);

Ext.onReady(function() {
	// we want to setup a model and store instead of using dataUrl
	Ext.define('Task', {
				extend : 'Ext.data.Model',
				fields : [{
							name : 'id',
							type : 'int'
						}, {
							name : 'IfDefineId',
							type : 'int'
						}, {
							name : 'ClassName',
							type : 'string'
						}, {
							name : 'ClassType',
							type : 'string'
						}, {
							name : 'ErrorRetryTimes',
							type : 'int'
						}, {
							name : 'IfCat',
							type : 'string'
						}, {
							name : 'IfCode',
							type : 'string'
						}, {
							name : 'IfName',
							type : 'string'
						}, {
							name : 'IfType',
							type : 'string'
						}, {
							name : 'RetryWaitSeconds',
							type : 'int'
						}, {
							name : 'RunMethod',
							type : 'string'
						}, {
							name : 'Status',
							type : 'int'
						}]
			});

	var store = Ext.create('Ext.data.TreeStore', {
				model : 'Task',
				proxy : {
					type : 'ajax',
					// the store will get the content from the .json file
					url : 'ifDefineTreeList'
				},
				folderSort : true
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
	var ifDefineTypeStore = Ext.create('Ext.data.Store', {
				model : 'combo_model',
				remoteSort : true,
				proxy : {
					type : 'ajax',
					url : 'ifDefineTypeList'
				}
			});
	var tree = Ext.create('Ext.tree.Panel', {
				title : '菜单列表',
				collapsible : false,
				useArrows : true,
				rootVisible : false,
				store : store,
				multiSelect : false,
				singleExpand : false,
				region : 'center',
				// the 'columns' property is now 'headers'
				columns : [{
							xtype : 'treecolumn',
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
							sortable : true
						}, {
							header : "类方式",
							dataIndex : 'ClassType',
							width : 100,
							sortable : true
						}, {
							header : "方法",
							dataIndex : 'RunMethod',
							width : 130,
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
							align : 'right',
							sortable : true
						}, {
							header : "状态",
							dataIndex : 'Status',
							width : 100,
							align : 'right',
							sortable : true
						}],
				bbar : Ext.create('Ext.PagingToolbar', {
							displayInfo : false
						})
			});
	tree.child('pagingtoolbar').add(['->', {
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
	var viewport = Ext.create('Ext.Viewport', {
				id : 'module-main',
				title : '菜单管理',
				layout : 'border',
				items : [tree]
			});
	
	// 刷新当前页查询结果
	function refreshGrid() {
		var delNode;  
  
		while (delNode = tree.getRootNode().childNodes[0]) {  
		    tree.getRootNode().removeChild(delNode);  
		}  
		  
		tree.store.load(); 
	}
	function newIfDefineHandler(widget, event) {
		showIfDefineForm();
	}
	// 修改接口
	function updateIfDefineHandler(widget, event) {
		var row = tree.getSelectionModel().getSelection()[0];
		if (!row||row.get("IfDefineId")==0) {

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
				layout : {
					type : 'vbox',
					align : 'stretch'
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
									fieldLabel : '接口分类',
									displayField : 'name',
									valueField : 'value',
									store : Ext.create('Ext.data.Store', {
												model : 'combo_model',
												data : [{
															"name" : "订单",
															"value" : "订单"
														},{
															"name" : "销售",
															"value" : "销售"
														}, {
															"name" : "商品",
															"value" : "商品"
														}, {
															"name" : "物流",
															"value" : "物流"
														}, {
															"name" : "报表",
															"value" : "报表"
														}, {
															"name" : "基础",
															"value" : "基础"
														}, {
															"name" : "分销",
															"value" : "分销"
														}]
											}),
									queryMode : 'local',
									editable : false,
									name : 'IfCat',
									allowBlank : false,
									id : 'info-if-cat'
								}), {
							fieldLabel : '类名',
							name : 'ClassName',
							allowBlank : false,
							id : 'info-class-name'
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
									store : Ext.create('Ext.data.Store', {
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
									allowBlank : false,
									name : 'Status',
									id : 'info-status'
								})]
			});
			ifDefineWin = Ext.widget('window', {
				title : '接口信息',
				closeAction : 'hide',
				width : 500,
				height : 350,
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
								ifCat : Ext.getCmp('info-if-cat').getValue(),
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
	function deleteIfDefineHandler(widget, event) {
		var row = tree.getSelectionModel().getSelection()[0];
		if (!row||row.get("IfDefineId")==0) {

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
							ifDefineId : tree.getSelectionModel()
									.getSelection()[0].get('IfDefineId')
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
		} else {
			return;
		}
	}
});