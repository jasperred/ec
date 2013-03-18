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
							text : "查询",
							handler : searchHandler
						}, {
							text : "清空",
							handler : function() {
								searchPanel.getForm().reset();
							}
						}, {
							text : "返回",
							handler : function() {
								location.href = 'mobilemenu';
							}
						}],
				renderTo : 'search-panel'
			});
	// 查询结果grid

	var gridHeight = getWindownHeight() - searchPanel.getHeight();
	Ext.define('SearchModel', {
				extend : 'Ext.data.Model',
				fields : ['id', 'custNo', 'custName', 'email', 'mobile', 'sex',
						{
							name : 'birthDay',
							type : 'date',
							dateFormat : 'Y-m-d'
						}],
				idProperty : 'id'
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
							url : 'customerSearch',
							reader : {
								root : 'resultList',
								totalProperty : 'rowCount',
								fields : ['id', 'custNo', 'custName', 'email',
										'mobile', 'sex', 'birthDay']
							},
							// sends single sort as multi parameter
							simpleSortMode : true
						}),
				sorters : [{
							property : 'id',
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
							header : "会员ID",
							dataIndex : 'id',
							align : 'center',
							width : 60,
							sortable : false
						}, {
							header : "会员编号",
							dataIndex : 'custNo',
							align : 'center',
							width : 100,
							sortable : false
						}, {
							header : "会员名称",
							dataIndex : 'custName',
							align : 'center',
							width : 150,
							sortable : true
						}, {
							header : "邮件",
							dataIndex : 'email',
							align : 'center',
							width : 200,
							align : 'center',
							sortable : true
						}, {
							header : "手机号",
							dataIndex : 'mobile',
							align : 'center',
							width : 100,
							sortable : true
						}, {
							header : "性别",
							dataIndex : 'sex',
							align : 'center',
							width : 50,
							sortable : true
						}, {
							header : "会员生日",
							dataIndex : 'birthDay',
							renderer : Ext.util.Format.dateRenderer('Y-m-d'),
							align : 'center',
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
	resultGrid.addListener('itemdblclick', updateCustomerHandler, this);
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
						custNo : Ext.getCmp("s-cust-no").getValue(),
						custName : Ext.getCmp("s-cust-name").getValue(),
						email : Ext.getCmp("s-email").getValue(),
						mobile : Ext.getCmp("s-mobile").getValue(),
						sex : Ext.getCmp("s-sex").getValue(),
						birthDayFrom : Ext.getCmp("s-birth-day-from")
								.getRawValue(),
						birthDayEnd : Ext.getCmp("s-birth-day-end").getRawValue()
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
});