Ext.require( [ 'Ext.data.*', 'Ext.grid.*', 'Ext.tree.*' ]);

Ext.onReady(function() {
	// we want to setup a model and store instead of using dataUrl
		Ext.define('Task', {
			extend : 'Ext.data.Model',
			fields : [ {
				name : 'id',
				type : 'int'
			},{
				name : 'moduleId',
				type : 'int'
			}, {
				name : 'parentModuleId',
				type : 'int'
			}, {
				name : 'moduleName',
				type : 'string'
			}, {
				name : 'moduleAlias',
				type : 'string'
			} , {
				name : 'moduleUrl',
				type : 'string'
			} , {
				name : 'showMenu',
				type : 'string'
			} , {
				name : 'sequence',
				type : 'int'
			}  ]
		});

		var store = Ext.create('Ext.data.TreeStore', {
			model : 'Task',
			proxy : {
				type : 'ajax',
				// the store will get the content from the .json file
				url : 'moduleList'
			},
			folderSort : true
		});
		// Define the model for a State
		Ext.regModel('combo_model', {
		    fields: [
		        {type: 'string', name: 'name'},
		        {type: 'string', name: 'value'}
		    ]
		});
		var isshowdata = [
		                  {"name":"Y","value":"Y"},{"name":"N","value":"N"}];
		var sequencedata = [
		                  {"name":"1","value":"1"},{"name":"2","value":"2"},{"name":"3","value":"3"},{"name":"4","value":"4"},{"name":"5","value":"5"},{"name":"6","value":"6"},{"name":"7","value":"7"}
		                  ,{"name":"8","value":"8"},{"name":"9","value":"9"},{"name":"10","value":"10"},{"name":"11","value":"11"},{"name":"12","value":"12"},{"name":"13","value":"13"},{"name":"14","value":"14"}
		                  ,{"name":"15","value":"15"},{"name":"16","value":"16"},{"name":"17","value":"17"},{"name":"18","value":"18"},{"name":"19","value":"19"},{"name":"20","value":"20"}];
		// The data store holding the states
		var isshowstore = Ext.create('Ext.data.Store', {
		    model: 'combo_model',
		    data: isshowdata
		});
		var sequencestore = Ext.create('Ext.data.Store', {
		    model: 'combo_model',
		    data: sequencedata
		});
		 var addRootAction = Ext.create('Ext.Action', {
		        text: '增加根菜单',
		        handler: function(widget, event) {
		            info.getForm().reset();
		        }
		    });
		    var addLeafAction = Ext.create('Ext.Action', {
		        text: '增加子菜单',
		        handler: function(widget, event) {
		            var rec = tree.getSelectionModel().getSelection()[0];
		            if (rec&&rec.get('parentModuleId')==0) {
		            	info.getForm().reset();
		            	Ext.getCmp('parent-module-id').setValue(rec.get('moduleId'));
		            	Ext.getCmp('parent-module-name').setValue(rec.get('moduleName'));
		            	Ext.getCmp('module-url').setDisabled(false);
		            } else {
		            	Ext.MessageBox.alert('提示', "请选择一个根菜单!");
		            }
		        }
		    });

		    var contextMenu = Ext.create('Ext.menu.Menu', {
		        items: [
		            addRootAction,
		            addLeafAction
		        ]
		    });
		var tree = Ext.create('Ext.tree.Panel', {
			title : '菜单列表',
			region : 'center',
			collapsible : false,
			useArrows : true,
			rootVisible : false,
			store : store,
			multiSelect : false,
			singleExpand : false,
			// the 'columns' property is now 'headers'
			columns : [ {
				xtype : 'treecolumn', // this is so we know which column will
										// show the tree
				text : '标题',
				flex : 2,
				sortable : true,
				dataIndex : 'moduleName'
			}, {
				text : '别名',
				flex : 1,
				sortable : true,
				dataIndex : 'moduleAlias'
			}, {
				text : '链接',
				flex : 1,
				dataIndex : 'moduleUrl',
				sortable : true
			}, {
				text : '是否显示',
				flex : 1,
				dataIndex : 'showMenu',
				sortable : true
			}, {
				text : '排序',
				flex : 1,
				dataIndex : 'sequence',
				sortable : true
			} ],
	        dockedItems: [{
	            xtype: 'toolbar',
	            items: [
	                addRootAction, addLeafAction
	            ]
	        }],
	        viewConfig: {
	            stripeRows: true,
	            listeners: {
	                itemcontextmenu: function(view, rec, node, index, e) {
	                    e.stopEvent();
	                    contextMenu.showAt(e.getXY());
	                    return false;
	                }
	            }
	        }
		});
		var info = Ext.create('Ext.form.Panel', {
			id : 'module-info',
			title : '菜单信息',
			region : 'east',
			frame : true, // 设置窗体为圆角
			method : "POST",
			defaultType : 'textfield',
			layout : 'vbox',
			//autoHeight : true,
			fieldDefaults : {
				msgTarget : 'side',
				labelAlign : 'left'
			},
			items : [ {
				fieldLabel : "菜单ID", // 标签内容
				xtype : "hidden", // 对象的类型,默认为 textfield
				name : "moduleId",
				id : "module-id",
				value : 0
			},{
				fieldLabel : "上级菜单ID", // 标签内容
				xtype : "hidden", // 对象的类型,默认为 textfield
				name : "parentModuleId",
				id : "parent-module-id",
				value : 0
			},{
				fieldLabel : "上级菜单", // 标签内容
				xtype : "textfield", // 对象的类型,默认为 textfield
				name : "parentModuleName",
				id : "parent-module-name",
				disabled : true
			},{
				fieldLabel : "菜单名称", // 标签内容
				xtype : "textfield", // 对象的类型,默认为 textfield
				allowBlank : false, // 是否允许为空,默认为 true
				blankText : "用户名不能为空", // 显示错误提示信息
				name : "moduleName",
				id : "module-name"
			},{
				fieldLabel : "菜单别名", // 标签内容
				xtype : "textfield", // 对象的类型,默认为 textfield
				name : "moduleAlias",
				id : "module-alias"
			},{
				fieldLabel : "菜单链接", // 标签内容
				xtype : "textfield", // 对象的类型,默认为 textfield
				name : "moduleUrl",
				width : 400,
				id : "module-url"
			},
			Ext.create('Ext.form.field.ComboBox',{
			    fieldLabel: '是否显示',
			    displayField: 'name',
			    store: isshowstore,
			    queryMode: 'local',
			    editable : false,
			    name : "showMenu",
				id : "show-menu"
			}),
			Ext.create('Ext.form.field.ComboBox',{
			    fieldLabel: '菜单排序',
			    displayField: 'name',
			    store: sequencestore,
			    queryMode: 'local',
			    editable : false,
			    name : "sequence",
				id : "sequence"
			})
			],
			buttons : [{
				text : "保存",
				handler : saveHandler
			}, {
				text : "删除",
				handler : deleteHandler
			}]
		});
		var viewport = Ext.create('Ext.Viewport', {
			id : 'module-main',
			title : '菜单管理',
			layout : 'border',
			items : [ tree, info ]
		});
	function refreshTree() {
		var delNode;  
		while (delNode = tree.getRootNode().childNodes[0]) {  
		    tree.getRootNode().removeChild(delNode);  
		}  
		  
		tree.store.load(); 
	}
		//删除
		function deleteHandler()
		{
			if(Ext.getCmp("module-id").getValue()==0)
			{
				Ext.MessageBox.alert('提示', "请选择菜单!");
				return;
			}
			Ext.MessageBox.confirm('提示', '确认要删除菜单吗？', deleteModule);   
		}
		function deleteModule(btn)
		{
			if (btn == 'yes')   
	        {    
	        	Ext.Msg.wait('处理中，请稍后...', '提示');
	        	Ext.Ajax.request({
					url : 'deleteModule',
					params : {
	        			crumb : Ext.get("crumb").getValue(),
	        			moduleId : Ext.getCmp('module-id').getValue()
					},
					success : function(response, options) {
						Ext.Msg.hide();
						var text = unicodeToString(response.responseText);
						var responseArray = Ext.JSON.decode(text);
						if (responseArray.success) {
							Ext.MessageBox.alert('提示', "处理成功!");
							refreshTree();
						} else {
							Ext.MessageBox.alert("错误",responseArray.message);
						}
					}
				});
	        }    
	        else  
	        {    
	            return;  
	        }    
		}
		//保存
		function saveHandler()
		{
			if(!info.getForm().isValid())
				return;
			Ext.MessageBox.confirm('提示', '确认保存信息吗？', saveModule);   
		}
		 function saveModule(btn)   
	     {   
	        if (btn == 'yes')   
	        {    
	        	Ext.Msg.wait('处理中，请稍后...', '提示');
	        	Ext.Ajax.request({
					url : 'saveModule',
					params : {
	        			crumb : Ext.get("crumb").getValue(),
	        			moduleId : Ext.getCmp('module-id').getValue(),
	        			parentModuleId : Ext.getCmp('parent-module-id').getValue(),
	        			sequence : Ext.getCmp('sequence').getValue(),
	        			moduleName : Ext.getCmp('module-name').getValue(),
	        			moduleAlias : Ext.getCmp('module-alias').getValue(),
	        			showMenu : Ext.getCmp('show-menu').getValue(),
	        			url : Ext.getCmp('module-url').getValue()
					},
					success : function(response, options) {
						Ext.Msg.hide();
						var text = unicodeToString(response.responseText);
						var responseArray = Ext.JSON.decode(text);
						if (responseArray.success) {
							Ext.MessageBox.alert('提示', "处理成功!");
							refreshTree();
						} else {
							Ext.MessageBox.alert("错误",responseArray.message);
						}
					}
				});
	        }    
	        else  
	        {    
	            return;  
	        }     
	    }
		tree.getSelectionModel().on('selectionchange', function(sm, selectedRecord) {
	        if (selectedRecord.length) {
	        	info.form.loadRecord( selectedRecord[0]);
        		Ext.getCmp('module-url').setDisabled(false);
	        	var parentModel = selectedRecord[0].store.getById(selectedRecord[0].get('parentModuleId'));
	        	if(parentModel)
	        	{
	        		Ext.getCmp('parent-module-name').setValue(parentModel.get('moduleName'));
	        	}
	        	else
	        	{
	        		Ext.getCmp('parent-module-name').setValue('无');
	        		Ext.getCmp('module-url').setDisabled(true);
	        	}
	        }
	    });
	});