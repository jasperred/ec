Ext.Loader.setConfig({
			enabled : true
		});

Ext.Loader.setPath('Ext.ux', 'js/ext/ux/');
Ext.require(['Ext.grid.*', 'Ext.data.*', 'Ext.util.*', 'Ext.toolbar.Paging',
		'Ext.ux.PreviewPlugin', 'Ext.ModelManager', 'Ext.tip.QuickTipManager']);
Ext.onReady(function() {
	Ext.tip.QuickTipManager.init();
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
	var enabledstore = Ext.create('Ext.data.Store', {
				model : 'combo_model',
				data : [{
							"name" : "Y",
							"value" : "Y"
						}, {
							"name" : "N",
							"value" : "N"
						}]
			});
	Ext.regModel('company_combo_model', {
				fields : [{
							type : 'string',
							name : 'companyName'
						}, {
							type : 'int',
							name : 'id'
						}]
			});
	var companyliststore = Ext.create('Ext.data.Store', {
				model : 'company_combo_model',
				remoteSort : true,
				proxy : {
					type : 'ajax',
					url : 'allCompanyList'
				}
			});
	companyliststore.load();
	var searchPanel = Ext.create('Ext.form.Panel', {
				id : 'search-condition-panel',
				title : '查询条件',
				region : 'north',
				layout : 'table',
				frame : true, // 设置窗体为圆角
				method : "POST",
				bodyPadding : 10,
				defaultType : 'textfield',
				fieldDefaults : {
					msgTarget : 'side',
					labelAlign : 'left'
				},
				items : [{
							fieldLabel : "角色ID", // 标签内容
							name : "groupId",
							id : "group-id",
							margin : "5 5 5 10"
						}, {
							fieldLabel : "角色名称", // 标签内容
							name : "groupName",
							id : "group-name",
							margin : "5 5 5 10"
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

	var gridHeight = getWindownHeight()-searchPanel.getHeight( );
	Ext.define('SearchModel', {
				extend : 'Ext.data.Model',
				fields : ['GroupId', 'GroupName','CompanyId'],
				idProperty : 'GroupId'
			});

	// create the Data Store
	var store = Ext.create('Ext.data.Store', {
				model : 'SearchModel',
				remoteSort : true,
				pageSize : pageSize,
				proxy : new Ext.data.HttpProxy({
							// load using script tags for cross domain,
							// if the
							// data in on the same domain as
							// this page, an HttpProxy would be better
							type : 'jsonp',
							url : 'searchSecurityGroup',
							reader : {
								root : 'resultList',
								totalProperty : 'rowCount',
								fields : ['GroupId', 'GroupName','CompanyId']
							},
							// sends single sort as multi parameter
							simpleSortMode : true
						}),
				sorters : [{
							property : 'GroupId',
							direction : 'ASC'
						}]
			});
	var pluginExpanded = true;
	var resultGrid = Ext.create('Ext.grid.Panel', {
				title : '查询结果',
				region : 'south',
				height : gridHeight,
				store : store,
				disableSelection : false,
				loadMask : true,
				multiSelect : false,
				itemSelector : '.feed-list-item',
				overItemCls : 'feed-list-item-hover',
				viewConfig : {
					id : 'gv',
					trackOver : false,
					stripeRows : false,
					plugins : [{
								ptype : 'preview',
								bodyField : 'excerpt',
								expanded : true,
								pluginId : 'preview'
							}]
				},
				// grid columns
				columns : [{
							xtype : 'rownumberer'
						}, {
							header : "角色ID",
							dataIndex : 'GroupId',
							flex : 1,
							sortable : false
						}, {
							header : "角色名称",
							dataIndex : 'GroupName',
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
				iconCls : 'icon-add',
				text : '新增角色',
				scope : this,
				handler : newSGHandler
			}, {
				iconCls : 'icon-add',
				text : '修改角色名称',
				scope : this,
				handler : updateSGHandler
			}, {
				iconCls : 'icon-delete',
				text : '分配权限',
				scope : this,
				handler : updateSGPHandler
			}]);
	resultGrid.addListener('itemdblclick', updateSGHandler, this);
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
						groupId : Ext.getCmp("group-id").getValue(),
						groupName : Ext.getCmp("group-name").getValue()
					};
					Ext.apply(store.proxy.extraParams, new_params);
				});

		store.loadPage(1);
	}
	// 新增角色
	function newSGHandler() {
		showSGForm();
	}
	// 修改角色名称
	function updateSGHandler() {
		var row = resultGrid.getSelectionModel().getSelection()[0];
		if (!row) {

			Ext.MessageBox.alert('提示', "请选择一条记录!");
			return;
		}
		showSGForm(row.get('GroupId'), row.get('GroupName'), row.get('CompanyId'));
	}
	var infoGroupId;
	// 分配权限
	function updateSGPHandler() {
		var row = resultGrid.getSelectionModel().getSelection()[0];
		if (!row) {

			Ext.MessageBox.alert('提示', "请选择一条记录!");
			return;
		}		
		infoGroupId = row.get('GroupId');
		showSGPForm();
	}

	var winNew;
	var winSave;
	// 新增用户窗口
	function showSGForm(groupId, groupName,companyId) {
		if (!winNew) {
			var form = Ext.widget('form', {
				border : false,
				bodyPadding : 10,
				url : 'saveSecurityGroup',
				fieldDefaults : {
					labelWidth : 100,
					labelStyle : 'font-weight:bold'
				},
				defaults : {
					margins : '0 0 10 0'
				},

				items : [{
							xtype : 'hidden',
							allowBlank : false,
							name : 'crumb',
							value : Ext.get('crumb').getValue()
						}, {
							xtype : 'textfield',
							fieldLabel : '角色ID',
							allowBlank : false,
							name : 'groupId',
							id : 'save-group-id'
						}, {
							xtype : 'textfield',
							fieldLabel : '角色名称',
							allowBlank : false,
							name : 'groupName',
							id : 'save-group-name'
						}, Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '公司',
									displayField : 'companyName',
									valueField : 'id',
									store : companyliststore,
									queryMode : 'remote',
									editable : false,
									name : "companyId",
									id : "save-company-id"
								})],

				buttons : [{
							text : '取消',
							handler : function() {
								this.up('form').getForm().reset();
								this.up('window').hide();
							}
						}, {
							text : '保存',
							handler : function() {
								var form = this.up('form').getForm();
								if (form.isValid()) {
									form.submit({
										success : function(form, action) {
											Ext.MessageBox.alert('提示', "处理成功!");
											winNew.hide();
											var pageIndex = resultGrid
													.getStore().currentPage;
											store.load({
														params : {
															start : (pageIndex - 1)
																	* pageSize,
															limit : pageSize
														}
													});
										},
										failure : function(form, action) {
											Ext.MessageBox.alert('错误',
													action.result.message);
										}
									});
								}
							}
						}]
			});
			winNew = Ext.widget('window', {
						title : '角色',
						closeAction : 'hide',
						width : 400,
						height : 280,
						layout : 'fit',
						resizable : true,
						modal : true,
						items : form
					});
		}
		if (groupId != undefined && groupId.length > 0) {
			Ext.getCmp('save-group-id').setValue(groupId);
			Ext.getCmp('save-group-id').setReadOnly(true);
			Ext.getCmp('save-group-name').setValue(groupName);
			Ext.getCmp('save-company-id').setValue(companyId);
		} else {
			Ext.getCmp('save-group-id').setReadOnly(false);
		}
		// 得到登录用户的信息
		Ext.Ajax.request({
					url : 'userLoginInfo',
					success : function(response, options) {
						var text = unicodeToString(response.responseText);
						var responseArray = Ext.JSON.decode(text);
						if (responseArray.userNo) {
							//非管理员不能更改用户的公司
							if(responseArray.userType==null||responseArray.userType!='SYSTEM')
							{
								Ext.getCmp('save-company-id').hide();
							}
							else
							{
								Ext.getCmp('save-company-id').show();
							}
						} else {
							Ext.MessageBox.alert("错误", "用户信息没找到");
						}
					}
				});
		winNew.show();
	}

	Ext.define('SecurityPermissionModel', {
				extend : 'Ext.data.Model',
				fields : [{
							name : 'id',
							type : 'string'
						}, {
							name : 'text',
							type : 'string'
						}, {
							name : 'permissionId',
							type : 'string'
						}, {
							name : 'permissionName',
							type : 'string'
						}, {
							name : 'parentPermissionId',
							type : 'string'
						}, {
							name : 'dispIndex',
							type : 'int'
						}]
			});
	var spstore = Ext.create('Ext.data.TreeStore', {
				model : 'SecurityPermissionModel',
				autoSync : true,
				autoLoad : true,
				proxy : {
					type : 'ajax',
					url : 'securityGroupPermissionList'
				},
				sorters : [{
							property : 'dispIndex',
							direction : 'ASC'
						}]
			});
	//刷新tree，不能直接通过store.load()，要先删除结点
	function refreshTree() {
		var delNode;  
  		var tree = Ext.getCmp("sp-tree");
		while (delNode = tree.getRootNode().childNodes[0]) {  
		    tree.getRootNode().removeChild(delNode);  
		}  
		  
		tree.store.load(); 
	}
	// 修改安全组权限窗口
	function showSGPForm() {
		spstore.on('beforeload', function() {
					var new_params = {
						groupId : infoGroupId
					};
					Ext.apply(spstore.proxy.extraParams, new_params);
				});
		if (!winSave) {
			var form = Ext.widget('form', {
				border : false,
				margins : '0 0 0 5',
				layout : 'border',
				defaults : {
					margins : '0 0 10 0'
				},
				items : [Ext.create('Ext.tree.Panel', {
							store : spstore,
							region : 'center',
							expanded : true,
							rootVisible : false,
							id : 'sp-tree',
							frame : false
						})],

				buttons : [{
							text : '取消',
							handler : function() {
								this.up('form').getForm().reset();
								this.up('window').hide();
							}
						}, {
							text : '保存',
							handler : function() {
								var records = Ext.getCmp('sp-tree').getView()
										.getChecked();
								if (records.length == 0) {
									return;
								}
								var pids = '';
								var i = 0;
								Ext.Array.each(records, function(rec) {
											i++;
											if (i > 1)
												pids += ',';
											pids += rec.get('permissionId');
										});
								Ext.Ajax.request({
									url : 'saveSecurityGroupPermission',
									params : {
										crumb : Ext.get('crumb').getValue(),
										groupId : infoGroupId,
										permissionIds : pids
									},
									success : function(response, options) {
										var text = unicodeToString(response.responseText);
										var responseArray = Ext.JSON
												.decode(text);
										if (responseArray.success) {
											Ext.MessageBox.alert('提示', "处理成功!");
											form.getForm().reset();
											winSave.hide();
											var pageIndex = resultGrid
													.getStore().currentPage;
											store.load({
														params : {
															start : (pageIndex - 1)
																	* pageSize,
															limit : pageSize
														}
													});
										} else {
											Ext.MessageBox.alert("错误",
													responseArray.message);
										}
									}
								});
							}
						}]
			});
			winSave = Ext.widget('window', {
						title : '角色权限分配',
						closeAction : 'hide',
						width : 400,
						height : 380,
						layout : 'fit',
						resizable : true,
						modal : true,
						items : form
					});
		} else {
			refreshTree();
		}
		winSave.show();
	}
});