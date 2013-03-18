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
	var searchPanel = Ext.create('Ext.form.Panel', {
				id : 'search-condition-panel',
				title : '查询条件',
				region : 'north',
				frame : true, // 设置窗体为圆角
				method : "POST",
				bodyPadding : 10,
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
				items : [{
							fieldLabel : "登录名", // 标签内容
							name : "userNo",
							id : "user-no",
							margin : "5 5 5 10"
						}, {
							fieldLabel : "姓名", // 标签内容
							name : "userName",
							id : "user-name",
							margin : "5 5 5 10"
						}, {
							fieldLabel : "邮件", // 标签内容
							name : "email",
							id : "email",
							margin : "5 5 5 10"
						}, {
							fieldLabel : "公司", // 标签内容
							name : "companyName",
							id : "company-name",
							margin : "5 5 5 10"
						}, Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '状态',
									displayField : 'name',
									store : enabledstore,
									queryMode : 'local',
									editable : false,
									name : "enabled",
									id : "enabled",
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

	var gridHeight = getWindownHeight()-searchPanel.getHeight( );
	Ext.define('SearchModel', {
				extend : 'Ext.data.Model',
				fields : ['UserNo', 'Email', 'UserName', 'Enabled', {
							name : 'UserId',
							type : 'int'
						}, {
							name : 'CompanyId',
							type : 'int'
						}, 'CompanyName', 'CompanyStatus'],
				idProperty : 'UserId'
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
							url : 'searchUser',
							reader : {
								root : 'resultList',
								totalProperty : 'rowCount',
								fields : ['UserNo', 'Email', 'UserName',
										'Enabled', 'UserId', 'CompanyId',
										'CompanyName', 'CompanyStatus']
							},
							// sends single sort as multi parameter
							simpleSortMode : true
						}),
				sorters : [{
							property : 'UserNo',
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
							header : "登录名",
							dataIndex : 'UserNo',
							flex : 1,
							sortable : false
						}, {
							header : "姓名",
							dataIndex : 'UserName',
							width : 100,
							sortable : true
						}, {
							header : "邮件",
							dataIndex : 'Email',
							width : 70,
							align : 'right',
							sortable : true
						}, {
							header : "公司",
							dataIndex : 'CompanyName',
							width : 150,
							sortable : true
						}, {
							header : "状态",
							dataIndex : 'Enabled',
							width : 150,
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
				text : '增加新用户',
				scope : this,
				handler : newUserHandler
			}, {
				iconCls : 'icon-delete',
				text : '修改用户信息',
				scope : this,
				handler : updateUserHandler
			}, {
				iconCls : 'icon-delete',
				text : '修改密码',
				scope : this,
				handler : modifyPasswordHandler
			}, {
				iconCls : 'icon-delete',
				text : '权限分配',
				scope : this,
				handler : securityGroupHandler
			}]);
	resultGrid.addListener('itemdblclick', updateUserHandler, this);
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
						userNo : Ext.getCmp("user-no").getValue(),
						userName : Ext.getCmp("user-name").getValue(),
						email : Ext.getCmp("email").getValue(),
						companyName : Ext.getCmp("company-name").getValue(),
						enabled : Ext.getCmp("enabled").getValue()
					};
					Ext.apply(store.proxy.extraParams, new_params);
				});
		store.load({
					params : {
						start : 0,
						limit : pageSize
					}
				});
	}
	// 新增用户
	function newUserHandler() {
		showNewUserForm();
	}
	// 修改用户
	function updateUserHandler() {
		var row = resultGrid.getSelectionModel().getSelection()[0];
		if (!row) {

			Ext.MessageBox.alert('提示', "请选择一条记录!");
			return;
		}
		showSaveUserForm(row.get('UserId'));
	}
	// 修改密码
	function modifyPasswordHandler() {
		var row = resultGrid.getSelectionModel().getSelection()[0];
		if (!row) {

			Ext.MessageBox.alert('提示', "请选择一条记录!");
			return;
		}
		showModifyPassword(row.get('UserId'), row.get('UserNo'));
	}
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
					// load using script tags for cross domain,
					// if the
					// data in on the same domain as
					// this page, an HttpProxy would be better
					type : 'ajax',
					url : 'allCompanyList'
				}
			});
	companyliststore.load();
	// 密码一致性检查
	Ext.apply(Ext.form.field.VTypes, {

				password : function(val, field) {
					if (field.initialPassField) {
						var pwd = field.up('form').down('#'
								+ field.initialPassField);
						return (val == pwd.getValue());
					}
					return true;
				},
				passwordText : '密码不一致'
			});
	var winNew;
	var winSave;
	// 新增用户窗口
	function showNewUserForm() {
		if (!winNew) {
			var form = Ext.widget('form', {
				border : false,
				bodyPadding : 10,
				url : 'newUser',
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
							xtype : 'hidden',
							allowBlank : false,
							name : 'userId',
							value : 0
						}, {
							xtype : 'textfield',
							fieldLabel : '登录名',
							allowBlank : false,
							name : 'userNo'
						}, {
							xtype : 'textfield',
							fieldLabel : '密码',
							inputType : "password",
							allowBlank : false,
							name : 'password',
							id : 'password'
						}, {
							xtype : 'textfield',
							fieldLabel : '确认密码',
							inputType : "password",
							allowBlank : false,
							name : 'repassword',
							vtype : 'password',
							initialPassField : 'password'
						}, {
							xtype : 'textfield',
							fieldLabel : '姓名',
							name : 'userName'
						}, {
							xtype : 'textfield',
							fieldLabel : '邮件',
							vtype : 'email',
							name : 'email'
						}, Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '公司',
									displayField : 'companyName',
									valueField : 'id',
									store : companyliststore,
									allowBlank : false,
									queryMode : 'remote',
									editable : false,
									name : "companyId",
									id : "form-company-id"
								}), Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '状态',
									displayField : 'name',
									valueField : 'value',
									store : enabledstore,
									allowBlank : false,
									queryMode : 'local',
									editable : false,
									name : "enabled",
									id : "enabled"
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
						title : '新增用户',
						closeAction : 'hide',
						width : 400,
						height : 280,
						layout : 'fit',
						resizable : true,
						modal : true,
						items : form
					});
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
								Ext.getCmp('form-company-id').setDisabled(true);								
								Ext.getCmp('form-company-id')
									.setValue(responseArray.companyId);
							}
							else
							{
								Ext.getCmp('form-company-id').setDisabled(false);
							}
						} else {
							Ext.MessageBox.alert("错误", "用户信息没找到");
						}
					}
				});
		winNew.show();
	}
	// 修改用户信息窗口
	function showSaveUserForm(userId) {
		if (!winSave) {
			var form = Ext.widget('form', {
				border : false,
				bodyPadding : 10,
				url : 'saveUser',
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
							xtype : 'hidden',
							allowBlank : false,
							name : 'userId',
							id : 'new-user-id'
						}, {
							xtype : 'textfield',
							fieldLabel : '登录名',
							allowBlank : false,
							disabled : true,
							name : 'userNo',
							id : 'new-user-no'
						}, {
							xtype : 'textfield',
							fieldLabel : '姓名',
							name : 'userName',
							id : 'new-user-name'
						}, {
							xtype : 'textfield',
							fieldLabel : '邮件',
							vtype : 'email',
							name : 'email',
							id : 'new-email'
						}, Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '公司',
									displayField : 'companyName',
									valueField : 'id',
									store : companyliststore,
									queryMode : 'remote',
									allowBlank : false,
									editable : false,
									name : "companyId",
									id : 'new-company-id'
								}), Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '状态',
									displayField : 'name',
									valueField : 'value',
									store : enabledstore,
									allowBlank : false,
									queryMode : 'local',
									editable : false,
									name : "enabled",
									id : "new-enabled"
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

			winSave = Ext.widget('window', {
						title : '修改用户信息',
						closeAction : 'hide',
						width : 400,
						height : 280,
						layout : 'fit',
						resizable : true,
						modal : true,
						items : form
					});
		}
		// 得到用户的信息
		Ext.Ajax.request({
					url : 'userInfo',
					params : {
						userId : userId
					},
					success : function(response, options) {
						var text = unicodeToString(response.responseText);
						var responseArray = Ext.JSON.decode(text);
						if (responseArray.userNo) {
							//非管理员不能更改用户的公司
							if(responseArray.userType==null||responseArray.userType!='SYSTEM')
							{
								Ext.getCmp('new-company-id').setDisabled(true);
							}
							else
							{
								Ext.getCmp('new-company-id').setDisabled(false);
							}
							Ext.getCmp('new-user-id')
									.setValue(responseArray.userId);
							Ext.getCmp('new-user-no')
									.setValue(responseArray.userNo);
							Ext.getCmp('new-user-name')
									.setValue(responseArray.userName);
							Ext.getCmp('new-email')
									.setValue(responseArray.email);
							Ext.getCmp('new-company-id')
									.setValue(responseArray.companyId);
							Ext.getCmp('new-enabled')
									.setValue(responseArray.enabled);
							winSave.show();
						} else {
							Ext.MessageBox.alert("错误", "用户信息没找到");
						}
					}
				});
	}
	var psWin;
	function showModifyPassword(userId, userNo) {
		if (!psWin) {
			var form = Ext.widget('form', {
				border : false,
				bodyPadding : 10,
				url : 'modifyPasswordByAdmin',
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
							xtype : 'hidden',
							allowBlank : false,
							name : 'userId',
							id : 'ps-user-id'
						}, {
							xtype : 'textfield',
							fieldLabel : '登录名',
							allowBlank : false,
							disabled : true,
							name : 'userNo',
							id : 'ps-user-no'
						}, {
							xtype : 'textfield',
							fieldLabel : '旧密码',
							inputType : "password",
							allowBlank : false,
							name : 'password'
						}, {
							xtype : 'textfield',
							fieldLabel : '新密码',
							inputType : "password",
							allowBlank : false,
							name : 'newPassword',
							id : 'password'
						}, {
							xtype : 'textfield',
							fieldLabel : '确认新密码',
							inputType : "password",
							allowBlank : false,
							name : 'repassword',
							vtype : 'password',
							initialPassField : 'password'
						}],

				buttons : [{
							text : '取消',
							handler : function() {
								this.up('form').getForm().reset();
								this.up('window').hide();
							}
						}, {
							text : '修改密码',
							handler : function() {
								var form = this.up('form').getForm();
								if (form.isValid()) {
									form.submit({
										success : function(form, action) {
											Ext.MessageBox.alert('提示', "处理成功!");
											psWin.hide();
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
			Ext.getCmp('ps-user-id').setValue(userId);
			Ext.getCmp('ps-user-no').setValue(userNo);
			psWin = Ext.widget('window', {
						title : '修改密码',
						closeAction : 'hide',
						width : 400,
						height : 280,
						layout : 'fit',
						resizable : true,
						modal : true,
						items : form
					});
		}
		psWin.show();
	}
	function securityGroupHandler()
	{
		var row = resultGrid.getSelectionModel().getSelection()[0];
		if (!row) {

			Ext.MessageBox.alert('提示', "请选择一条记录!");
			return;
		}
		showSGPForm(row.get('UserId'));
	}
	Ext.define('SecurityGroupModel', {
				extend : 'Ext.data.Model',
				fields : [{
							name : 'id',
							type : 'string'
						}, {
							name : 'text',
							type : 'string'
						}, {
							name : 'securityGroupId',
							type : 'string'
						}, {
							name : 'securityGroupName',
							type : 'string'
						}]
			});
	var spstore = Ext.create('Ext.data.TreeStore', {
				model : 'SecurityGroupModel',
				autoSync : true,
				autoLoad : true,
				proxy : {
					type : 'ajax',
					url : 'securityGroupList'
				}
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
	// 修改用户信息窗口
	var sgWin;
	function showSGPForm(userId) {
		spstore.on('beforeload', function() {
					var new_params = {
						userId : userId
					};
					Ext.apply(spstore.proxy.extraParams, new_params);
				});
		if (!sgWin) {
			var form = Ext.widget('form', {
				border : false,
				margins : '0 0 0 5',
				layout : 'border',
				defaults : {
					margins : '0 0 10 0'
				},
				items : [{
							xtype : 'hidden',
							allowBlank : false,
							name : 'userId',
							id : 'sg-user-id'
						},Ext.create('Ext.tree.Panel', {
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
											pids += rec.get('securityGroupId');
										});
								Ext.Ajax.request({
									url : 'saveUserSecurityGroup',
									params : {
										crumb : Ext.get('crumb').getValue(),
										userId : Ext.getCmp("sg-user-id").getValue(),
										groupIds : pids
									},
									success : function(response, options) {
										var text = unicodeToString(response.responseText);
										var responseArray = Ext.JSON
												.decode(text);
										if (responseArray.success) {
											Ext.MessageBox.alert('提示', "处理成功!");
											form.getForm().reset();
											sgWin.hide();
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
			sgWin = Ext.widget('window', {
						title : '用户角色',
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
		Ext.getCmp("sg-user-id").setValue(userId);
		sgWin.show();
	}
});