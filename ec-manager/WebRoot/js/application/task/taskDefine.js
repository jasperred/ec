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
								}), Ext.create('Ext.form.field.ComboBox', {
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
				fields : ['TaskDefineId', 'CycleType', 'DayCycle', {
							name : 'Cycle',
							type : 'int'
						}, 'DayCycleType', 'DayEndTime', 'DayStartTime', {
							name : 'EndDate',
							type : 'date',
							dateFormat : 'Y-m-d'
						}, 'IfId', 'LastEndTime', 'LastStartTime',
						'NextExecTime', {
							name : 'StartDate',
							type : 'date',
							dateFormat : 'Y-m-d'
						}, 'Status', 'StoreId', 'IsLastSuccess', 'IfName',
						'IfCode', 'StoreName', 'CronExpression'],
				idProperty : 'TaskDefineId'
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
							url : 'taskDefineSearch',
							reader : {
								root : 'resultList',
								totalProperty : 'rowCount',
								fields : ['TaskDefineId', 'CycleType',
										'DayCycle', 'Cycle', 'DayCycleType',
										'DayEndTime', 'DayStartTime',
										'EndDate', 'IfId', 'LastEndTime',
										'LastStartTime', 'NextExecTime',
										'StartDate', 'Status', 'StoreId',
										'IsLastSuccess', 'IfName', 'IfCode',
										'StoreName', 'CronExpression']
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
	var gridHeight = getWindownHeight() - searchPanel.getHeight();
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
							header : "接口",
							dataIndex : 'IfName',
							width : 100,
							sortable : false
						}, {
							header : "开始日期",
							dataIndex : 'StartDate',
							renderer : Ext.util.Format.dateRenderer('Y-m-d'),
							width : 100,
							sortable : true
						}, {
							header : "结束日期",
							dataIndex : 'EndDate',
							renderer : Ext.util.Format.dateRenderer('Y-m-d'),
							width : 100,
							align : 'right',
							sortable : true
						}, {
							header : "店铺",
							dataIndex : 'StoreName',
							width : 100,
							align : 'right',
							sortable : true
						}, {
							header : "周期",
							dataIndex : 'CronExpression',
							width : 100,
							align : 'right',
							sortable : true
						}, {
							header : "状态",
							dataIndex : 'Status',
							width : 100,
							sortable : true
						}, {
							header : "下次执行时间",
							dataIndex : 'NextExecTime',
							width : 150,
							sortable : true
						}, {
							header : "最后开始时间",
							dataIndex : 'LastStartTime',
							width : 150,
							sortable : true
						}, {
							header : "最后结束时间",
							dataIndex : 'LastEndTime',
							width : 150,
							sortable : true
						}, {
							header : "是否成功",
							dataIndex : 'IsLastSuccess',
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
				text : '立即执行任务',
				scope : this,
				handler : nowExecTaskHandler
			}, {
				text : '新增任务',
				scope : this,
				handler : newTaskDefineHandler
			}, {
				text : '修改任务',
				scope : this,
				handler : updateTaskDefineHandler
			}, {
				text : '删除任务',
				scope : this,
				handler : deleteTaskDefineHandler
			}]);
	resultGrid.addListener('itemdblclick', updateTaskDefineHandler, this);
	// trigger the data store load
//	store.load({
//				params : {
//					start : 0,
//					limit : pageSize
//				}
//			});
	// 查询用户
	function searchHandler() {
		store.on('beforeload', function() { // =======翻页时 查询条件
					var new_params = {
						limit : pageSize,
						storeId : Ext.getCmp("s-store-id").getValue(),
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
	// 立即执行任务
	function nowExecTaskHandler() {
		var row = resultGrid.getSelectionModel().getSelection()[0];
		if (!row) {

			Ext.MessageBox.alert('提示', "请选择一条记录!");
			return;
		}
		Ext.Ajax.request({
					url : 'nowExecTask',
					params : {
						crumb : Ext.get('crumb').getValue(),
						taskId : row.get("TaskDefineId")

					},
					success : function(response, options) {
						var text = unicodeToString(response.responseText);
						var responseArray = Ext.JSON.decode(text);
						if (responseArray.success) {
							Ext.MessageBox.alert('提示', "处理成功!");
						} else {
							Ext.MessageBox.alert("错误", responseArray.message);
						}
					}
				});
	}
	// 新增接口
	function newTaskDefineHandler() {
		showTaskDefineForm();
	}
	// 修改接口
	function updateTaskDefineHandler() {
		var row = resultGrid.getSelectionModel().getSelection()[0];
		if (!row) {

			Ext.MessageBox.alert('提示', "请选择一条记录!");
			return;
		}
		showTaskDefineForm();
		changeCron(row.get("CronExpression"));
		Ext.getCmp("cycle-fieldcontainer").setDisabled(true);
		Ext.getCmp("info-cron-expression").setDisabled(false);
		showFieldset('');
		taskDefineForm.getForm().loadRecord(row);
	}
	function changeCron(cron) {
		if (cron != null && cron.length > 0) {
			var cycle = cron.split(" ");
			if (cycle.length == 6) {
				Ext.getCmp("info-month").setValue(cycle[4]);
				Ext.getCmp("info-day").setValue(cycle[3]);
				Ext.getCmp("info-week").setValue(cycle[5]);
				Ext.getCmp("info-hours").setValue(cycle[2]);
				Ext.getCmp("info-minute").setValue(cycle[1]);
				if (cycle[3] == "?") {
					Ext.getCmp("use-week").setValue(true);
				} else {
					Ext.getCmp("use-week").setValue(false);
				}
				isUseWeek();
			}
		}
	}
	var taskDefineWin;// 接口定义界面
	var taskDefineForm;
	function showTaskDefineForm() {
		if (!taskDefineWin) {
			var month_fieldset = {
				xtype : 'fieldset',
				flex : 1,
				title : '月份设置',
				id : 'month-fieldset',
				defaultType : 'checkbox',
				hidden : true,
				layout : 'anchor',
				defaults : {
					hideEmptyLabel : false
				},
				items : [{
					xtype : 'fieldcontainer',
					combineErrors : true,
					fieldLabel : '每月',
					labelWidth : 50,
					labelAlign : 'right',
					items : [{
						xtype : 'radiofield',
						boxLabel : '',
						checked : false,
						listeners : {
							change : {
								fn : function() {
									var f = Ext.getCmp("month-fieldset")
											.query('checkboxfield');
									for (i = 2; i < f.length; i++) {
										f[i].setDisabled(this.getValue());
									}
								}
							}
						},
						width : 35,
						name : 'month-radio'
					}]
				}, {
					xtype : 'fieldcontainer',
					fieldLabel : '指定',
					labelWidth : 50,
					labelAlign : 'right',
					layout : {
						type : 'hbox',
						align : 'left'
					},
					items : [{
								xtype : 'radiofield',
								boxLabel : '',
								checked : true,
								width : 35,
								margin : '0 5 0 0',
								name : 'month-radio'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '1月',
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '2月',
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '3月',
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '4月',
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '5月',
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '6月',
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '7月',
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '8月',
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '9月',
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '10月',
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '11月',
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '12月',
								margin : '0 5 0 0'
							}]
				}, {
					xtype : 'fieldcontainer',
					combineErrors : true,
					fieldLabel : '',
					labelWidth : 50,
					labelAlign : 'right',
					items : [Ext.create('Ext.Button', {
								text : '确定',
								handler : function() {
									var f = Ext.getCmp("month-fieldset")
											.query('checkboxfield');
									if (f[0].getValue()) {
										Ext.getCmp("info-month").setValue("*");
										return;
									}
									var month = '';
									for (i = 2; i < f.length; i++) {
										if (!f[i].getValue())
											continue;
										if (month.length > 0)
											month += ',';
										month += i - 1;
									}
									if (month.length == 0) {
										alert("请指定月份");
										retturn;
									}
									Ext.getCmp("info-month").setValue(month);
								},
								margin : '0 0 0 45',
								width : 60
							})]
				}]
			};
			var day_fieldset = {
				xtype : 'fieldset',
				flex : 1,
				title : '天设置',
				id : 'day-fieldset',
				defaultType : 'checkbox',
				hidden : true,
				layout : 'anchor',
				defaults : {
					hideEmptyLabel : false
				},
				items : [{
					xtype : 'fieldcontainer',
					combineErrors : true,
					fieldLabel : '每天',
					labelWidth : 50,
					labelAlign : 'right',
					items : [{
						xtype : 'radiofield',
						boxLabel : '',
						checked : false,
						listeners : {
							change : {
								fn : function() {
									var f = Ext.getCmp("day-fieldset")
											.query('checkboxfield');
									for (i = 2; i < f.length; i++) {
										f[i].setDisabled(this.getValue());
									}
								}
							}
						},
						width : 35,
						name : 'day-radio'
					}]
				}, {
					xtype : 'fieldcontainer',
					fieldLabel : '指定',
					labelWidth : 50,
					labelAlign : 'right',
					layout : {
						type : 'hbox',
						align : 'left'
					},
					items : [{
								xtype : 'radiofield',
								boxLabel : '',
								checked : true,
								width : 35,
								margin : '0 5 0 0',
								name : 'day-radio'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '1',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '2',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '3',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '4',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '5',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '6',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '7',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '8',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '9',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '10',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '11',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '12',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '13',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '14',
								width : 35,
								margin : '0 5 0 0'
							}]
				}, {
					xtype : 'fieldcontainer',
					fieldLabel : '',
					labelWidth : 50,
					labelAlign : 'right',
					layout : {
						type : 'hbox',
						align : 'left'
					},
					items : [{
								xtype : 'displayfield',
								boxLabel : '',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '15',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '16',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '17',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '18',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '19',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '20',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '21',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '22',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '23',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '24',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '25',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '26',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '27',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '28',
								width : 35,
								margin : '0 5 0 0'
							}]
				}, {
					xtype : 'fieldcontainer',
					fieldLabel : '',
					labelWidth : 50,
					labelAlign : 'right',
					layout : {
						type : 'hbox',
						align : 'left'
					},
					items : [{
								xtype : 'displayfield',
								boxLabel : '',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '29',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '30',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '31',
								width : 35,
								margin : '0 5 0 0'
							}]
				}, {
					xtype : 'fieldcontainer',
					combineErrors : true,
					fieldLabel : '',
					labelWidth : 50,
					labelAlign : 'right',
					items : [Ext.create('Ext.Button', {
								text : '确定',
								handler : function() {
									var f = Ext.getCmp("day-fieldset")
											.query('checkboxfield');
									if (f[0].getValue()) {
										Ext.getCmp("info-day").setValue("*");
										return;
									}
									var month = '';
									for (i = 2; i < f.length; i++) {
										if (!f[i].getValue())
											continue;
										if (month.length > 0)
											month += ',';
										month += i - 1;
									}
									if (month.length == 0) {
										alert("请指定天");
										retturn;
									}
									Ext.getCmp("info-day").setValue(month);
								},
								margin : '0 0 0 45',
								width : 60
							})]
				}]
			};
			var week_fieldset = {
				xtype : 'fieldset',
				flex : 1,
				title : '星期设置',
				id : 'week-fieldset',
				defaultType : 'checkbox',
				hidden : true,
				layout : 'anchor',
				defaults : {
					hideEmptyLabel : false
				},
				items : [{
					xtype : 'fieldcontainer',
					combineErrors : true,
					fieldLabel : '每星期',
					labelWidth : 50,
					labelAlign : 'right',
					items : [{
						xtype : 'radiofield',
						boxLabel : '',
						width : 35,
						checked : false,
						listeners : {
							change : {
								fn : function() {
									var f = Ext.getCmp("week-fieldset")
											.query('checkboxfield');
									for (i = 2; i < f.length; i++) {
										f[i].setDisabled(this.getValue());
									}
								}
							}
						},
						name : 'week-radio'
					}]
				}, {
					xtype : 'fieldcontainer',
					fieldLabel : '指定',
					labelWidth : 50,
					labelAlign : 'right',
					layout : {
						type : 'hbox',
						align : 'left'
					},
					items : [{
								xtype : 'radiofield',
								boxLabel : '',
								width : 35,
								checked : true,
								margin : '0 5 0 0',
								name : 'week-radio'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '星期日',
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '星期一',
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '星期二',
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '星期三',
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '星期四',
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '星期五',
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '星期六',
								margin : '0 5 0 0'
							}]
				}, {
					xtype : 'fieldcontainer',
					combineErrors : true,
					fieldLabel : '',
					labelWidth : 50,
					labelAlign : 'right',
					items : [Ext.create('Ext.Button', {
								text : '确定',
								handler : function() {
									var f = Ext.getCmp("week-fieldset")
											.query('checkboxfield');
									if (f[0].getValue()) {
										Ext.getCmp("info-week").setValue("*");
										return;
									}
									var month = '';
									for (i = 2; i < f.length; i++) {
										if (!f[i].getValue())
											continue;
										if (month.length > 0)
											month += ',';
										month += i - 1;
									}
									if (month.length == 0) {
										alert("请指定星期");
										retturn;
									}
									Ext.getCmp("info-week").setValue(month);
								},
								margin : '0 0 0 30',
								width : 60
							})]
				}]
			};
			var hours_fieldset = {
				xtype : 'fieldset',
				flex : 1,
				title : '小时设置',
				id : 'hours-fieldset',
				defaultType : 'checkbox',
				hidden : true,
				layout : 'anchor',
				defaults : {
					hideEmptyLabel : false
				},
				items : [{
					xtype : 'fieldcontainer',
					combineErrors : true,
					fieldLabel : '每小时',
					labelWidth : 50,
					labelAlign : 'right',
					items : [{
						xtype : 'radiofield',
						boxLabel : '',
						width : 35,
						checked : false,
						listeners : {
							change : {
								fn : function() {
									var f = Ext.getCmp("hours-fieldset")
											.query('checkboxfield');
									for (i = 2; i < f.length; i++) {
										f[i].setDisabled(this.getValue());
									}
								}
							}
						},
						name : 'hours-radio'
					}]
				}, {
					xtype : 'fieldcontainer',
					fieldLabel : '指定',
					labelWidth : 50,
					labelAlign : 'right',
					layout : {
						type : 'hbox',
						align : 'left'
					},
					items : [{
								xtype : 'radiofield',
								boxLabel : '',
								width : 35,
								checked : true,
								margin : '0 5 0 0',
								name : 'hours-radio'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '0点',
								width : 45,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '1点',
								width : 45,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '2点',
								width : 45,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '3点',
								width : 45,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '4点',
								width : 45,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '5点',
								width : 45,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '6点',
								width : 45,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '7点',
								width : 45,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '8点',
								width : 45,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '9点',
								width : 45,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '10点',
								width : 45,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '11点',
								width : 45,
								margin : '0 5 0 0'
							}]
				}, {
					xtype : 'fieldcontainer',
					fieldLabel : '',
					labelWidth : 50,
					labelAlign : 'right',
					layout : {
						type : 'hbox',
						align : 'left'
					},
					items : [{
								xtype : 'displayfield',
								boxLabel : '',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '12点',
								width : 45,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '13点',
								width : 45,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '14点',
								width : 45,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '15点',
								width : 45,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '16点',
								width : 45,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '17点',
								width : 45,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '18点',
								width : 45,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '19点',
								width : 45,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '20点',
								width : 45,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '21点',
								width : 45,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '22点',
								width : 45,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '23点',
								width : 45,
								margin : '0 5 0 0'
							}]
				}, {
					xtype : 'fieldcontainer',
					combineErrors : true,
					fieldLabel : '',
					labelWidth : 50,
					labelAlign : 'right',
					items : [Ext.create('Ext.Button', {
								text : '确定',
								handler : function() {
									var f = Ext.getCmp("hours-fieldset")
											.query('checkboxfield');
									if (f[0].getValue()) {
										Ext.getCmp("info-hours").setValue("*");
										return;
									}
									var month = '';
									for (i = 2; i < f.length; i++) {
										if (!f[i].getValue())
											continue;
										if (month.length > 0)
											month += ',';
										month += i - 2;
									}
									if (month.length == 0) {
										alert("请指定小时");
										retturn;
									}
									Ext.getCmp("info-hours").setValue(month);
								},
								margin : '0 0 0 45',
								width : 60
							})]
				}]
			};
			var minute_fieldset = {
				xtype : 'fieldset',
				flex : 1,
				title : '分钟设置',
				id : 'minute-fieldset',
				defaultType : 'checkbox',
				hidden : true,
				layout : 'anchor',
				defaults : {
					hideEmptyLabel : false
				},
				items : [{
					xtype : 'fieldcontainer',
					combineErrors : true,
					fieldLabel : '每分钟',
					labelWidth : 50,
					labelAlign : 'right',
					layout : {
						type : 'hbox',
						align : 'left'
					},
					items : [{
						xtype : 'radiofield',
						boxLabel : '',
						width : 35,
						margin : '0 5 0 0',
						checked : false,
						listeners : {
							change : {
								fn : function() {
									var f = Ext.getCmp("minute-fieldset")
											.query('checkboxfield');
									for (i = 2; i < f.length; i++) {
										f[i].setDisabled(this.getValue());
									}
									var n = Ext.getCmp("minute-fieldset")
											.query('numberfield');
									for (i = 0; i < n.length; i++) {
										n[i].setDisabled(!this.getValue());
									}
								}
							}
						},
						name : 'minute-radio'
					}, {
						xtype : 'displayfield',
						fieldLabel : '从第',
						width : 50
					}, {
						xtype : 'numberfield',
						width : 50,
						minValue : 0,
						maxValue : 59,
						disabled : true,
						value : 0
					}, {
						xtype : 'displayfield',
						fieldLabel : '分钟开始，间隔'
					}, {
						xtype : 'numberfield',
						width : 50,
						minValue : 1,
						maxValue : 50,
						disabled : true,
						value : 5
					}, {
						xtype : 'displayfield',
						fieldLabel : '分钟'
					}]
				}, {
					xtype : 'fieldcontainer',
					fieldLabel : '指定',
					labelWidth : 50,
					labelAlign : 'right',
					layout : {
						type : 'hbox',
						align : 'left'
					},
					items : [{
								xtype : 'radiofield',
								boxLabel : '',
								width : 35,
								checked : true,
								margin : '0 5 0 0',
								name : 'minute-radio'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '0',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '1',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '2',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '3',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '4',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '5',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '6',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '7',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '8',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '9',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '10',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '11',
								width : 35,
								margin : '0 5 0 0'
							}]
				}, {
					xtype : 'fieldcontainer',
					fieldLabel : '',
					labelWidth : 50,
					labelAlign : 'right',
					layout : {
						type : 'hbox',
						align : 'left'
					},
					items : [{
								xtype : 'displayfield',
								boxLabel : '',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '12',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '13',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '14',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '15',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '16',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '17',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '18',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '19',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '20',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '21',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '22',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '23',
								width : 35,
								margin : '0 5 0 0'
							}]
				}, {
					xtype : 'fieldcontainer',
					fieldLabel : '',
					labelWidth : 50,
					labelAlign : 'right',
					layout : {
						type : 'hbox',
						align : 'left'
					},
					items : [{
								xtype : 'displayfield',
								boxLabel : '',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '24',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '25',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '26',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '27',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '28',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '29',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '30',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '31',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '32',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '33',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '34',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '35',
								width : 35,
								margin : '0 5 0 0'
							}]
				}, {
					xtype : 'fieldcontainer',
					fieldLabel : '',
					labelWidth : 50,
					labelAlign : 'right',
					layout : {
						type : 'hbox',
						align : 'left'
					},
					items : [{
								xtype : 'displayfield',
								boxLabel : '',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '36',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '37',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '38',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '39',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '40',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '41',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '42',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '43',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '44',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '45',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '46',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '47',
								width : 35,
								margin : '0 5 0 0'
							}]
				}, {
					xtype : 'fieldcontainer',
					fieldLabel : '',
					labelWidth : 50,
					labelAlign : 'right',
					layout : {
						type : 'hbox',
						align : 'left'
					},
					items : [{
								xtype : 'displayfield',
								boxLabel : '',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '48',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '49',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '50',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '51',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '52',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '53',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '54',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '55',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '56',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '57',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '58',
								width : 35,
								margin : '0 5 0 0'
							}, {
								xtype : 'checkboxfield',
								boxLabel : '59',
								width : 35,
								margin : '0 5 0 0'
							}]
				}, {
					xtype : 'fieldcontainer',
					combineErrors : true,
					fieldLabel : '',
					labelWidth : 50,
					labelAlign : 'right',
					items : [Ext.create('Ext.Button', {
								text : '确定',
								handler : function() {
									var f = Ext.getCmp("minute-fieldset")
											.query('checkboxfield');
									if (f[0].getValue()) {
										var n = Ext.getCmp("minute-fieldset")
												.query('numberfield');
										Ext.getCmp("info-minute").setValue(n[0]
												.getValue()
												+ "/" + n[1].getValue());
										return;
									}
									var month = '';
									for (i = 2; i < f.length; i++) {
										if (!f[i].getValue())
											continue;
										if (month.length > 0)
											month += ',';
										month += i - 2;
									}
									if (month.length == 0) {
										alert("请指定分钟");
										retturn;
									}
									Ext.getCmp("info-minute").setValue(month);
								},
								margin : '0 0 0 45',
								width : 60
							})]
				}]
			};
			taskDefineForm = Ext.create('Ext.form.Panel', {
				defaultType : 'textfield',
				bodyStyle : 'padding:5px 5px 0',
				defaults : {
					anchor : '100%'
				},
				border : false,

				items : [
						{
							xtype : 'hidden',
							fieldLabel : '任务ID',
							name : 'TaskDefineId',
							id : 'info-task-define-id'
						},
						Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '接口',
									displayField : 'name',
									valueField : 'value',
									store : ifdefinestore,
									queryMode : 'remote',
									editable : false,
									name : 'IfId',
									allowBlank : false,
									id : 'info-if-id'
								}),
						{
							xtype : 'fieldcontainer',
							fieldLabel : '起止日期',
							combineErrors : true,
							msgTarget : 'side',
							layout : 'hbox',
							defaults : {
								flex : 1,
								hideLabel : true
							},
							items : [{
								xtype : 'datefield',
								renderer : Ext.util.Format
										.dateRenderer('Y-m-d'),
								name : 'StartDate',
								allowBlank : false,
								id : 'info-start-date'
							}, {
								xtype : 'datefield',
								renderer : Ext.util.Format
										.dateRenderer('Y-m-d'),
								name : 'EndDate',
								id : 'info-end-date'
							}]
						},
						Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '店铺',
									displayField : 'name',
									valueField : 'value',
									store : shopstore,
									queryMode : 'remote',
									editable : false,
									allowBlank : false,
									name : "StoreId",
									id : "info-store-id"
								}),
						Ext.create('Ext.form.field.ComboBox', {
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
								}),
						Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '接口',
									displayField : 'name',
									valueField : 'value',
									store : ifdefinestore,
									queryMode : 'remote',
									editable : false,
									name : 'IfId',
									allowBlank : false,
									id : 'info-if-id'
								}),
						{
							xtype : 'fieldcontainer',
							combineErrors : true,
							fieldLabel : '',
							labelWidth : 50,
							labelAlign : 'right',
							layout : {
								type : 'hbox',
								align : 'left'
							},
							items : [{
										xtype : 'displayfield',
										fieldLabel : '',
										width : 50
									}, {
										xtype : 'radiofield',
										boxLabel : '使用表达式',
										margin : '0 5 0 0',
										checked : true,
										id : 'info-use-cron',
										listeners : {
											change : {
												fn : function() {
													Ext
															.getCmp("cycle-fieldcontainer")
															.setDisabled(this
																	.getValue());
													Ext
															.getCmp("info-cron-expression")
															.setDisabled(!this
																	.getValue());
													showFieldset('');
												}
											}
										},
										name : 'cron-radio'
									}, {
										xtype : 'radiofield',
										boxLabel : '使用执行频率',
										margin : '0 5 0 0',
										checked : false,
										name : 'cron-radio'
									}]
						}, {
							fieldLabel : '表达式',
							name : 'CronExpression',
							allowBlank : false,
							id : 'info-cron-expression'
						}, {
							xtype : 'fieldcontainer',
							fieldLabel : '执行频率',
							disabled : true,
							id : 'cycle-fieldcontainer',
							msgTarget : 'side',
							layout : 'hbox',
							defaults : {
								flex : 1,
								hideLabel : true
							},
							items : [{
										xtype : 'textfield',
										name : 'Month',
										allowBlank : false,
										id : 'info-month'
									}, {
										xtype : 'displayfield',
										value : '月'
									}, {
										xtype : 'checkboxfield',
										boxLabel : '使用星期',
										name : 'UseWeek',
										handler : isUseWeek,
										id : 'use-week'
									}, {
										xtype : 'textfield',
										name : 'Day',
										allowBlank : false,
										id : 'info-day'
									}, {
										xtype : 'displayfield',
										value : '天',
										id : 'info-day-display'
									}, {
										xtype : 'textfield',
										name : 'Week',
										value : '?',
										allowBlank : false,
										id : 'info-week',
										hidden : true
									}, {
										xtype : 'displayfield',
										value : '星期',
										id : 'info-week-display',
										hidden : true
									}, {
										xtype : 'textfield',
										name : 'Hours',
										allowBlank : false,
										id : 'info-hours'
									}, {
										xtype : 'displayfield',
										value : '时'
									}, {
										xtype : 'textfield',
										name : 'Minute',
										allowBlank : false,
										id : 'info-minute'
									}, {
										xtype : 'displayfield',
										value : '分'
									}]
						}, month_fieldset, day_fieldset, week_fieldset,
						hours_fieldset, minute_fieldset]
			});
			// 增加获得焦点事件
			Ext.getCmp("info-month").on({
						focus : {
							fn : cycleEvents,
							scope : this
						}
					});
			Ext.getCmp("info-day").on({
						focus : {
							fn : cycleEvents,
							scope : this
						}
					});
			Ext.getCmp("info-week").on({
						focus : {
							fn : cycleEvents,
							scope : this
						}
					});
			Ext.getCmp("info-hours").on({
						focus : {
							fn : cycleEvents,
							scope : this
						}
					});
			Ext.getCmp("info-minute").on({
						focus : {
							fn : cycleEvents,
							scope : this
						}
					});
			taskDefineWin = Ext.widget('window', {
				title : '任务信息',
				closeAction : 'hide',
				width : 800,
				height : 450,
				layout : 'fit',
				resizable : true,
				modal : true,
				items : taskDefineForm,
				buttons : [{
					text : "保存",
					handler : function() {
						if (!taskDefineForm.getForm().isValid())
							return;
						var cron = Ext.getCmp('info-cron-expression')
								.getValue();
						if (!Ext.getCmp("info-use-cron").getValue()) {
							var day = Ext.String.trim(Ext.getCmp('info-day')
									.getValue());
							var week = Ext.String.trim(Ext.getCmp('info-week')
									.getValue());
							if (Ext.getCmp('use-week').getValue()) {
								day = "?";
							} else {
								week = "?";
							}
							cron = "0 "
									+ Ext.String.trim(Ext.getCmp('info-minute')
											.getValue())
									+ " "
									+ Ext.String.trim(Ext.getCmp('info-hours')
											.getValue())
									+ " "
									+ day
									+ " "
									+ Ext.String.trim(Ext.getCmp('info-month')
											.getValue()) + " " + week;
						}
						Ext.Ajax.request({
							url : 'taskDefineSave',
							params : {
								crumb : Ext.get('crumb').getValue(),
								taskId : Ext.getCmp('info-task-define-id')
										.getValue(),
								ifId : Ext.getCmp('info-if-id').getValue(),
								startDate : Ext.getCmp('info-start-date')
										.getValue(),
								endDate : Ext.getCmp('info-end-date')
										.getValue(),
								status : Ext.getCmp('info-status').getValue(),
								storeId : Ext.getCmp('info-store-id')
										.getValue(),
								cronExpression : cron
							},
							success : function(response, options) {
								var text = unicodeToString(response.responseText);
								var responseArray = Ext.JSON.decode(text);
								if (responseArray.success) {
									Ext.MessageBox.alert('提示', "处理成功!");
									taskDefineForm.getForm().reset();
									taskDefineWin.hide();
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
						taskDefineForm.getForm().reset();
						taskDefineWin.hide();
					}
				}]
			});
			Ext.getCmp("info-store-id").getStore().load();
			Ext.getCmp("info-if-id").getStore().load();
		}
		taskDefineWin.show();
	}
	// 是否使用星期
	function isUseWeek() {
		if (Ext.getCmp("use-week").getValue()) {
			Ext.getCmp("info-day-display").setVisible(false);
			Ext.getCmp("info-day").setVisible(false);
			Ext.getCmp("info-day").setDisabled(true);
			Ext.getCmp("info-day").setValue("?");
			Ext.getCmp("info-week-display").setVisible(true);
			Ext.getCmp("info-week").setVisible(true);
			Ext.getCmp("info-week").setDisabled(false);
			showFieldset("week-fieldset");
		} else {
			Ext.getCmp("info-day-display").setVisible(true);
			Ext.getCmp("info-day").setVisible(true);
			Ext.getCmp("info-day").setDisabled(false);
			Ext.getCmp("info-week-display").setVisible(false);
			Ext.getCmp("info-week").setVisible(false);
			Ext.getCmp("info-week").setDisabled(true);
			Ext.getCmp("info-week").setValue("?");
			showFieldset("day-fieldset");
		}
	}
	// 执行频率输入框执行事件
	function cycleEvents(field, opt) {
		var id = field.getId();
		switch (id) {
			case 'info-month' :
				showFieldset("month-fieldset");
				break
			case 'info-day' :
				showFieldset("day-fieldset");
				break
			case 'info-week' :
				showFieldset("week-fieldset");
				break
			case 'info-hours' :
				showFieldset("hours-fieldset");
				break
			case 'info-minute' :
				showFieldset("minute-fieldset");
				break
		}
	}
	function showFieldset(id) {
		Ext.getCmp("month-fieldset").setVisible(false);
		Ext.getCmp("day-fieldset").setVisible(false);
		Ext.getCmp("week-fieldset").setVisible(false);
		Ext.getCmp("hours-fieldset").setVisible(false);
		Ext.getCmp("minute-fieldset").setVisible(false);
		if (id.length > 0)
			Ext.getCmp(id).setVisible(true);
	}
	// 删除接口
	function deleteTaskDefineHandler() {
		var row = resultGrid.getSelectionModel().getSelection()[0];
		if (!row) {

			Ext.MessageBox.alert('提示', "请选择一条记录!");
			return;
		}
		Ext.MessageBox.confirm('确认', '是否删除?', deleteTaskDefine)

	}
	function deleteTaskDefine(btn) {
		if (btn == 'yes') {
			Ext.Msg.wait('处理中，请稍后...', '提示');
			Ext.Ajax.request({
						url : 'taskDefineDelete',
						params : {
							crumb : Ext.get('crumb').getValue(),
							taskId : resultGrid.getSelectionModel()
									.getSelection()[0].get('TaskDefineId')
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