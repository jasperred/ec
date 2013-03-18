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
			var ifdefinestore = Ext.create('Ext.data.Store', {
						model : 'combo_model',
						remoteSort : true,
						proxy : {
							type : 'ajax',
							url : 'ifDefineList'
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
										}),
								Ext.create('Ext.form.field.ComboBox', {
											fieldLabel : '接口',
											displayField : 'name',
											valueField : 'value',
											store : ifdefinestore,
											queryMode : 'remote',
											editable : false,
											name : "ifId",
											id : "s-if-id",
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
						fields : ['IfDefineId', 'IfName', 'StoreId',
								'StoreName', 'TaskId', 'TaskLogId', {
									name : 'LogStartTime',
									type : 'date',
									dateFormat : 'Y-m-d H:i:s'
								}, {
									name : 'LogEndTime',
									type : 'date',
									dateFormat : 'Y-m-d H:i:s'
								}, 'LogContent'],
						idProperty : 'TaskLogId'
					});

			// create the Data Store
			var store = Ext.create('Ext.data.Store', {
						model : 'SearchModel',
						autoDestroy : true,
						remoteSort : true,
						pageSize : pageSize,
						proxy : new Ext.data.HttpProxy({
									type : 'jsonp',
									url : 'taskLogSearch',
									reader : {
										root : 'resultList',
										totalProperty : 'rowCount',
										fields : ['IfDefineId', 'IfName',
												'StoreId', 'StoreName',
												'TaskId', 'TaskLogId', {
													name : 'LogStartTime',
													type : 'date',
													dateFormat : 'Y-m-d H:i:s'
												}, {
													name : 'LogEndTime',
													type : 'date',
													dateFormat : 'Y-m-d H:i:s'
												}, 'LogContent']
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
									header : "店铺",
									dataIndex : 'StoreName',
									width : 150,
									sortable : false
								}, {
									header : "接口",
									dataIndex : 'IfName',
									width : 150,
									sortable : false
								}, {
									header : "开始时间",
									dataIndex : 'LogStartTime',
									renderer : Ext.util.Format
											.dateRenderer('Y-m-d H:i:s'),
									width : 150,
									sortable : true
								}, {
									header : "结束时间",
									dataIndex : 'LogEndTime',
									renderer : Ext.util.Format
											.dateRenderer('Y-m-d H:i:s'),
									width : 150,
									sortable : true
								}, {
									header : "信息",
									dataIndex : 'LogContent',
									width : 400,
									flex : 1,
									align : 'right',
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
								storeId : Ext.getCmp("s-store-id").getValue(),
								ifId : Ext.getCmp("s-if-id").getValue()
							};
							Ext.apply(store.proxy.extraParams, new_params);
						});
				store.loadPage(1);
			}

		});