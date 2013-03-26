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

	var searchPanel = Ext.create('Ext.form.Panel', {
				// id : 'search-panel',
				title : '订单新建',
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
				items : [{
							xtype : 'hidden',
							allowBlank : true,
							fieldLabel : '会员ID',
							margin : '2 5 2 0',
							colspan : 3,
							id : "cust-id"
						}, Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '店铺',
									displayField : 'name',
									valueField : 'value',
									store : shopstore,
									queryMode : 'remote',
									editable : false,
									allowBlank : false,
									id : "order-shop",
									margin : "2 5 2 0"
								}), {
							xtype : 'textfield',
							allowBlank : true,
							readOnly : true,
							fieldLabel : '会员',
							margin : '2 5 2 0',
							id : "cust-no"
						}, {
							xtype : 'button',

							text : '查询',
							margin : '2 5 2 0',
							colspan : 3,
							id : "cust-button",
							handler : showCustView
						}, {
							xtype : 'textfield',
							allowBlank : false,
							fieldLabel : '收货人',
							margin : '2 5 2 0',
							id : "receiver-name"
						}, {
							xtype : 'textfield',
							allowBlank : false,
							fieldLabel : '手机',
							margin : '2 5 2 0',
							id : "receiver-mobile"
						}, {
							xtype : 'textfield',
							allowBlank : false,
							fieldLabel : '电话',
							margin : '2 5 2 0',
							id : "receiver-tel"
						}, Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '省份',
									displayField : 'name',
									valueField : 'value',
									store : Ext.create('Ext.data.Store', {
												fields : ['name', 'value'],
												data : [{
															"name" : "请选择省份",
															"value" : ""
														}]
											}),
									queryMode : 'local',
									editable : false,
									allowBlank : false,
									value : "",
									name : "state",
									id : "receiver-state",
									margin : "2 5 2 0"
								}), Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '城市',
									displayField : 'name',
									valueField : 'value',
									store : Ext.create('Ext.data.Store', {
												fields : ['name', 'value'],
												data : [{
															"name" : "请选择城市",
															"value" : ""
														}]
											}),
									queryMode : 'local',
									editable : false,
									allowBlank : false,
									value : "",
									name : "city",
									id : "receiver-city",
									margin : "2 5 2 0"
								}), Ext.create('Ext.form.field.ComboBox', {
									fieldLabel : '地区',
									displayField : 'name',
									valueField : 'value',
									store : Ext.create('Ext.data.Store', {
												fields : ['name', 'value'],
												data : [{
															"name" : "请选择地区",
															"value" : ""
														}]
											}),
									queryMode : 'local',
									editable : false,
									allowBlank : false,
									value : "",
									name : "district",
									id : "receiver-district",
									margin : "2 5 2 0"
								}), {
							xtype : 'textfield',

							fieldLabel : '详细地址',
							width : 515,
							allowBlank : false,
							margin : '2 5 2 0',
							colspan : 2,
							id : "receiver-address"
						}, {
							xtype : 'textfield',

							fieldLabel : '邮编',
							margin : '2 5 2 0',
							id : "receiver-zip"
						}, {
							xtype : 'textfield',
							fieldLabel : '邮件',
							margin : '2 5 2 0',
							width : 515,
							colspan : 3,
							id : "buyer-email"
						}, {
							xtype : 'textarea',
							fieldLabel : '备注',
							margin : '2 5 2 0',
							width : 515,
							colspan : 3,
							id : "buyer-memo"
						}, {
							xtype : 'numberfield',
							readOnly : true,
							fieldLabel : '总金额',
							margin : '2 5 2 0',
							id : "order-amt"
						}, {
							xtype : 'numberfield',
							fieldLabel : '折扣',
							minValue : 0,
							margin : '2 5 2 0',
							id : "discount-fee",
							onChange : calculationAmount
						}, {
							xtype : 'numberfield',
							allowBlank : false,
							fieldLabel : '运费',
							margin : '2 5 2 0',
							id : "post-fee",
							onChange : calculationAmount
						}, {
							xtype : 'numberfield',
							readOnly : true,
							fieldLabel : '实付金额',
							margin : '2 5 2 0',
							colspan : 1,
							id : "payment"
						}, {
							xtype : 'numberfield',
							fieldLabel : '赠送积分',
							margin : '2 5 2 0',
							minValue : 0,
							colspan : 2,
							id : "obtain-point"
						}, {
							xtype : 'textfield',
							fieldLabel : '扫描',
							listeners : {
								specialKey : barcodeEvent
							},
							colspan : 3,
							margin : '2 5 2 0',
							id : "barcode"
						}],
				buttons : [{
							text : "新增",
							handler : saveOrder
						}, {
							text : "取消",
							handler : cancelOrder
						}],
				renderTo : 'search-panel'
			});
	// 初始化省市区
	initCity('receiver-state', 'receiver-city', 'receiver-district');
	// 查询结果grid
	var store = Ext.create('Ext.data.Store', {
				fields : ['itemCd', 'skuCd', 'name', 'skuProp1', 'skuProp2', {
							name : 'price',
							type : 'float'
						}, {
							name : 'qty',
							type : 'int'
						}, {
							name : 'discount',
							type : 'float'
						}, {
							name : 'subAmt',
							type : 'float'
						}]
			});
	store.on('update', function() {
				Ext.TaskManager.start({
							run : updateOrderDetail,
							interval : 1000,
							repeat : 1
						});
			});
	var gridHeight = getWindownHeight() - searchPanel.getHeight();
	var pluginExpanded = true;
	var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
				clicksToEdit : 1
			});
	var resultGrid = Ext.create('Ext.grid.Panel', {
				title : '明细',
				region : 'center',
				height : gridHeight,
				store : store,
				multiSelect : false,
				// grid columns
				columns : [{
							xtype : 'rownumberer'
						}, {
							header : "商品编码",
							dataIndex : 'itemCd',
							width : 100,
							sortable : false
						}, {
							header : "SKU",
							dataIndex : 'skuCd',
							width : 100,
							sortable : true
						}, {
							header : "商品名称",
							dataIndex : 'name',
							flex : 1,
							sortable : true
						}, {
							header : "颜色",
							dataIndex : 'skuProp1',
							width : 120,
							sortable : true
						}, {
							header : "尺码",
							dataIndex : 'skuProp2',
							width : 80,
							sortable : true
						}, {
							header : "价格",
							dataIndex : 'price',
							width : 100,
							renderer : 'usMoney',
							sortable : true
						}, {
							header : "数量",
							dataIndex : 'qty',
							editor : {
								xtype : 'numberfield',
								allowBlank : false
							},
							width : 100,
							sortable : true
						}, {
							header : "折扣",
							dataIndex : 'discount',
							editor : {
								xtype : 'numberfield',
								allowBlank : false
							},
							width : 100,
							renderer : 'usMoney',
							sortable : true
						}, {
							header : "小计",
							dataIndex : 'subAmt',
							width : 100,
							renderer : 'usMoney',
							sortable : true
						}],
				bbar : Ext.create('Ext.PagingToolbar', {
							store : store,
							displayInfo : true,
							displayMsg : '显示 {0} - {1}/共{2}条',
							emptyMsg : "没有查询结果"
						}),
				renderTo : 'result-grid',
				plugins : [cellEditing]
			});
	resultGrid.child('pagingtoolbar').add(['->', {
				iconCls : 'icon-add',
				text : '增加商品',
				scope : this,
				handler : itemView
			}, {
				iconCls : 'icon-add',
				text : '删除商品',
				scope : this,
				handler : removeItem
			}]);
	function saveOrder() {
		if (!searchPanel.getForm().isValid())
			return;
		var detailStore = resultGrid.getStore();
		if (detailStore.getCount() == 0)
			return;
		var orderItemStr = '[';
		for (i = 0; i < detailStore.getCount(); i++) {
			if (i > 0)
				orderItemStr += ',';
			var a = detailStore.getAt(i).data;
			orderItemStr += Ext.JSON.encode(a);
		}
		orderItemStr += ']';
		Ext.Ajax.request({
					url : 'newOrder',
					params : {
						crumb : Ext.get('crumb').getValue(),
						shopId : Ext.getCmp("order-shop").getValue(),
						shopName : Ext.getCmp("order-shop").getRawValue(),
						receiverAddress : Ext.getCmp("receiver-address")
								.getValue(),
						receiverCity : Ext.getCmp('receiver-city')
								.getRawValue(),
						receiverDistrict : Ext.getCmp('receiver-district')
								.getRawValue(),
						receiverMobile : Ext.getCmp('receiver-mobile')
								.getValue(),
						receiverName : Ext.getCmp('receiver-name').getValue(),
						receiverState : Ext.getCmp('receiver-state')
								.getRawValue(),
						receiverPhone : Ext.getCmp('receiver-tel').getValue(),
						receiverZip : Ext.getCmp('receiver-zip').getValue(),
						buyerEmail : Ext.getCmp('buyer-email').getValue(),
						totalFee : Ext.getCmp('order-amt').getValue(),
						payment : Ext.getCmp('payment').getValue(),
						postFee : Ext.getCmp('post-fee').getValue(),
						discountFee : Ext.getCmp('discount-fee').getValue(),
						buyerMemo : Ext.getCmp('buyer-memo').getValue(),
						custId : Ext.getCmp('cust-id').getValue(),
						custNo : Ext.getCmp('cust-no').getValue(),
						obtainPoint : Ext.getCmp('obtain-point').getValue(),
						orderItems : orderItemStr
					},
					success : function(response, options) {
						var text = unicodeToString(response.responseText);
						var responseArray = Ext.JSON.decode(text);
						if (responseArray.success) {
							Ext.MessageBox.alert('提示', "处理成功!");
							resultGrid.getStore().removeAll();
							searchPanel.getForm().reset();

						} else {
							Ext.MessageBox.alert("错误", responseArray.message);
						}
					}
				});
	}
	function cancelOrder() {
		searchPanel.getForm().reset();
	}
	// Item查询界面
	var winItemNew;
	var itemStore;
	function itemView() {
		if (winItemNew == undefined) {
			var itemsearch = Ext.create('Ext.form.Panel', {
						title : "会员查询",
						id : "item-search",
						region : 'north',
						frame : true, // 设置窗体为圆角
						method : "POST",
						bodyPadding : 10,
						layout : {
							type : 'table',
							columns : '2',
							align : 'center'
						},
						defaultType : 'textfield',
						fieldDefaults : {
							msgTarget : 'side',
							labelAlign : 'left'
						},
						items : [{
									xtype : 'textfield',
									fieldLabel : '商品编码',
									margin : '5 5 5 10',
									name : 'itemCd',
									id : "s-item-cd"
								}, {
									xtype : 'textfield',
									fieldLabel : 'SKU编码',
									name : 'skuCd',
									margin : '5 5 5 10',
									id : "s-sku-cd"
								}, {
									xtype : 'textfield',
									fieldLabel : '商品名称',
									name : 'itemName',
									margin : '5 5 5 10',
									id : "s-item-name"
								}, {
									xtype : 'fieldcontainer',
									fieldLabel : '商品价格',
									combineErrors : true,
									msgTarget : 'side',
									colspan : 3,
									layout : 'hbox',
									width : 380,
									margin : "5 5 5 10",
									defaults : {
										flex : 1,
										hideLabel : true
									},
									items : [{
												xtype : 'numberfield',
												name : 'fromPrice',
												id : 's-from-price',
												fieldLabel : '',
												margin : '0 5 0 0'
											}, {
												xtype : 'numberfield',
												name : 'endPrice',
												id : 's-end-price',
												fieldLabel : '-'
											}]
								}],
						buttons : [{
									text : "查询",
									handler : searchItem
								}, {
									text : "返回",
									handler : function() {
										itemsearch.getForm().reset();
										itemdetail.getStore().removeAll();
										winItemNew.hide();
									}
								}]
					});
			Ext.define('ItemSearchModel', {
						extend : 'Ext.data.Model',
						fields : ["ItemCd", "SkuCd", "ItemName", "SkuProp1",
								"SkuProp2", "SkuProp3", "SkuPrice1"],
						idProperty : 'SkuCd'
					});
			itemStore = Ext.create('Ext.data.Store', {
						model : 'ItemSearchModel',
						remoteSort : true,
						pageSize : pageSize,
						proxy : new Ext.data.HttpProxy({
									// load using script tags for cross domain,
									// if the
									// data in on the same domain as
									// this page, an HttpProxy would be better
									type : 'jsonp',
									url : 'skuSearchAction',
									reader : {
										root : 'resultList',
										totalProperty : 'rowCount',
										fields : ["ItemCd", "SkuCd",
												"ItemName", "SkuProp1",
												"SkuProp2", "SkuProp3",
												"SkuPrice1"]
									},
									// sends single sort as multi parameter
									simpleSortMode : true
								}),
						sorters : [{
									property : 'SkuCd',
									direction : 'ASC'
								}]
					});
			var itemdetail = Ext.create('Ext.grid.Panel', {
						title : "会员列表",
						id : "item-detail",
						height : 360,
						store : itemStore,
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
						columns : [{
									xtype : 'rownumberer'
								}, {
									header : "商品编码",
									dataIndex : 'ItemCd',
									editor : {
										xtype : 'textfield'
									},
									width : 80,
									sortable : false
								}, {
									header : "SKU编码",
									dataIndex : 'SkuCd',
									editor : {
										xtype : 'textfield'
									},
									width : 100,
									sortable : true
								}, {
									header : "商品名称",
									dataIndex : 'ItemName',
									editor : {
										xtype : 'textfield'
									},
									width : 300,
									flex : 1,
									sortable : true
								}, {
									header : "属性1",
									dataIndex : 'SkuProp1',
									width : 80,
									align : 'right',
									sortable : true
								}, {
									header : "属性2",
									dataIndex : 'SkuProp2',
									width : 80,
									sortable : true
								}, {
									header : "属性3",
									dataIndex : 'SkuProp3',
									width : 80,
									sortable : true
								}, {
									header : "价格",
									dataIndex : 'SkuPrice1',
									width : 80,
									sortable : true
								}],
						bbar : Ext.create('Ext.PagingToolbar', {
									store : store,
									displayInfo : true,
									displayMsg : '显示 {0} - {1}/共{2}条',
									emptyMsg : "没有查询结果"
								})
					});
			itemdetail.child('pagingtoolbar').add(['->', {
						iconCls : 'icon-add',
						text : '选择',
						scope : this,
						handler : selectItem
					}]);

			itemdetail.addListener('itemdblclick', selectItem, this);
			winItemNew = Ext.widget('window', {
						title : '商品信息',
						closeAction : 'hide',
						width : 900,
						height : 600,
						x : 0,
						y : 0,
						layout : 'anchor',
						resizable : true,
						modal : true,
						items : [itemsearch, itemdetail]
					});
		}
		winItemNew.show();
	}

	// 商品查询
	function searchItem() {
		itemStore.on('beforeload', function() { // =======翻页时 查询条件
					var new_params = {
						limit : pageSize,
						itemCd : Ext.getCmp("s-item-cd").getValue(),
						skuCd : Ext.getCmp("s-sku-cd").getValue(),
						itemName : Ext.getCmp("s-item-name").getValue(),
						fromPrice : Ext.getCmp("s-from-price").getValue(),
						endPrice : Ext.getCmp("s-end-price").getValue()
					};
					Ext.apply(itemStore.proxy.extraParams, new_params);
				});
		itemStore.loadPage(1);
	}
	// 商品选择
	function selectItem() {
		var row = Ext.getCmp('item-detail').getSelectionModel().getSelection()[0];
		if (!row) {
			return;
		}
		var detail = [row.get('ItemCd'), row.get('SkuCd'), row.get('ItemName'),
				row.get('SkuProp1'), row.get('SkuProp2'), row.get('SkuPrice1'),
				1, 0, row.get('SkuPrice1')];
		addOrderDetail(detail);
		Ext.getCmp('item-search').getForm().reset();
		Ext.getCmp('item-detail').getStore().removeAll();
		winItemNew.hide();
	}
	// 增加Item
	function addItem() {
		var ds = resultGrid.getStore();
		ds.insert(0, new Ext.data.Record({
							'value' : '0',
							'display' : '内容'
						}, '0'));
		ds.reload();
		calculationAmount();
	}
	// 删除Item
	function removeItem() {
		var ds = resultGrid.getStore();
		var selectedRow = resultGrid.getSelectionModel().getLastSelected();
		if (selectedRow) {
			ds.remove(selectedRow);
		}
		calculationAmount();
	}

	// 条码扫描
	function barcodeEvent(field, e) {
		var ds = resultGrid.getStore();
		if (e.getKey() == e.ENTER && this.getValue().length > 0) {
			Ext.Ajax.request({
						url : 'itemSkuSearchByBarcode',
						params : {
							barcode : this.getValue()
						},
						success : function(response, options) {
							var text = unicodeToString(response.responseText);
							var responseArray = Ext.JSON.decode(text);
							if (responseArray != null && responseArray.skuInfo) {
								var detail = [responseArray.skuInfo.itemCd,
										responseArray.skuInfo.skuCd,
										responseArray.skuInfo.itemName,
										responseArray.skuInfo.skuProp1,
										responseArray.skuInfo.skuProp2,
										responseArray.skuInfo.skuPrice1, 1, 0,
										responseArray.skuInfo.skuPrice1];
								addOrderDetail(detail);
							} else {
								Ext.MessageBox.alert("错误", "条码未找到");
							}
							Ext.getCmp('barcode').setValue('');
							Ext.getCmp('barcode').focus(true, true);
						}
					});
		}
	}

	function addOrderDetail(detail) {
		var ds = resultGrid.getStore();
		for (i = 0; i < ds.getCount(); i++) {
			var a = ds.getAt(i);
			if (a.get('itemCd') + a.get('skuCd') == detail[0] + detail[1]) {
				a.set('qty', a.get('qty') + 1);
				a.set('subAmt', a.get('price') * a.get('qty')
								- a.get('discount'));
				calculationAmount();
				return;
			}
		}
		ds.add([detail]);
		calculationAmount();
	}
	function updateOrderDetail() {
		calculationDetailAmount();
		calculationAmount();
	}
	function calculationDetailAmount() {
		var ds = resultGrid.getStore();
		for (i = 0; i < ds.getCount(); i++) {
			var a = ds.getAt(i);
			var amt = a.get('price') * a.get('qty') - a.get('discount');
			if (amt == a.get('subAmt'))
				continue;
			a.set('subAmt', a.get('price') * a.get('qty') - a.get('discount'));
		}
	}
	// 计算金额
	function calculationAmount() {
		var ds = resultGrid.getStore();
		// 计算明细金额
		var detailAmount = 0;
		for (i = 0; i < ds.getCount(); i++) {
			var a = ds.getAt(i);
			detailAmount += a.get('subAmt');
		}
		// 总金额
		Ext.getCmp('order-amt').setValue(detailAmount);
		// 实付金额
		var postFee = Ext.getCmp('post-fee').getValue();
		var discount = Ext.getCmp('discount-fee').getValue();
		var pay = detailAmount + postFee - discount;
		Ext.getCmp('payment').setValue(pay);
	}
	var winNew = null;
	var store = null;
	// 显示地址查询界面
	function showCustView() {
		if (winNew == undefined) {
			var custinfo = Ext.create('Ext.form.Panel', {
						title : "会员查询",
						id : "cust-info",
						region : 'north',
						frame : true, // 设置窗体为圆角
						method : "POST",
						bodyPadding : 10,
						layout : {
							type : 'table',
							columns : '2',
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
											store : Ext.create(
													'Ext.data.Store', {
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
									handler : searchCustomer
								}, {
									text : "返回",
									handler : function() {
										custinfo.getForm().reset();
										custdetail.getStore().removeAll();
										winNew.hide();
									}
								}]
					});
			Ext.define('SearchModel', {
				extend : 'Ext.data.Model',
				fields : ['id', 'custNo', 'custName', 'email', 'mobile', 'tel',
						'sex', {
							name : 'birthDay',
							type : 'date',
							dateFormat : 'Y-m-d'
						}, 'province', 'city', 'district', 'zipcode', 'address'],
				idProperty : 'id'
			});
			store = Ext.create('Ext.data.Store', {
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
										fields : ['id', 'custNo', 'custName',
												'email', 'mobile', 'tel',
												'sex', 'birthDay', 'province',
												'city', 'district', 'zipcode',
												'address']
									},
									// sends single sort as multi parameter
									simpleSortMode : true
								}),
						sorters : [{
									property : 'id',
									direction : 'ASC'
								}]
					});
			var custdetail = Ext.create('Ext.grid.Panel', {
						title : "会员列表",
						id : "cust-detail",
						height : 360,
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
						columns : [{
									xtype : 'rownumberer'
								}, {
									header : "会员ID",
									dataIndex : 'id',
									align : 'center',
									width : 30,
									sortable : false
								}, {
									header : "会员编号",
									dataIndex : 'custNo',
									align : 'center',
									width : 60,
									sortable : false
								}, {
									header : "会员名称",
									dataIndex : 'custName',
									align : 'center',
									width : 100,
									sortable : true
								}, {
									header : "省份",
									dataIndex : 'province',
									width : 60,
									sortable : true
								}, {
									header : "城市",
									dataIndex : 'city',
									sortable : true
								}, {
									header : "区",
									dataIndex : 'district',
									sortable : true
								}, {
									header : "详细地址",
									dataIndex : 'address',
									sortable : true
								}, {
									header : "邮编",
									dataIndex : 'zipcode',
									sortable : true
								}, {
									header : "邮件",
									dataIndex : 'email',
									align : 'center',
									align : 'center',
									sortable : true
								}, {
									header : "手机号",
									dataIndex : 'mobile',
									align : 'center',
									sortable : true
								}, {
									header : "电话",
									dataIndex : 'tel',
									align : 'center',
									sortable : true
								}, {
									header : "性别",
									dataIndex : 'sex',
									align : 'center',
									sortable : true
								}, {
									header : "会员生日",
									dataIndex : 'birthDay',
									renderer : Ext.util.Format
											.dateRenderer('Y-m-d'),
									align : 'center',
									sortable : true
								}],
						bbar : Ext.create('Ext.PagingToolbar', {
									store : store,
									displayInfo : true,
									displayMsg : '显示 {0} - {1}/共{2}条',
									emptyMsg : "没有查询结果"
								})
					});
			custdetail.child('pagingtoolbar').add(['->', {
						iconCls : 'icon-add',
						text : '选择',
						scope : this,
						handler : selectCust
					}]);
			custdetail.addListener('itemdblclick', selectCust, this);
			winNew = Ext.widget('window', {
						title : '地址信息',
						closeAction : 'hide',
						width : 900,
						height : 600,
						x : 0,
						y : 0,
						layout : 'anchor',
						resizable : true,
						modal : true,
						items : [custinfo, custdetail]
					});
		}
		winNew.show();
	}
	// 地址查询
	function searchCustomer() {
		store.on('beforeload', function() { // =======翻页时 查询条件
					var new_params = {
						custNo : Ext.getCmp("s-cust-no").getValue(),
						custName : Ext.getCmp("s-cust-name").getValue(),
						email : Ext.getCmp("s-email").getValue(),
						mobile : Ext.getCmp("s-mobile").getValue(),
						sex : Ext.getCmp("s-sex").getValue(),
						birthDayFrom : Ext.getCmp("s-birth-day-from")
								.getRawValue(),
						birthDayEnd : Ext.getCmp("s-birth-day-end")
								.getRawValue()
					};
					Ext.apply(store.proxy.extraParams, new_params);
				});
		store.loadPage(1);
	}
	// 地址选择
	function selectCust() {
		var row = Ext.getCmp('cust-detail').getSelectionModel().getSelection()[0];
		if (!row) {
			return;
		}
		Ext.getCmp("receiver-address").setValue(row.get('address'));
		Ext.getCmp('receiver-city').setRawValue(row.get('city'));
		Ext.getCmp('receiver-district').setRawValue(row.get('district'));
		Ext.getCmp('receiver-mobile').setValue(row.get('mobile'));
		Ext.getCmp('receiver-name').setValue(row.get('custName'));
		Ext.getCmp('receiver-state').setRawValue(row.get('province'));
		Ext.getCmp('receiver-tel').setValue(row.get('tel'));
		Ext.getCmp('receiver-zip').setValue(row.get('zipcode'));
		Ext.getCmp('buyer-email').setValue(row.get('email'));
		Ext.getCmp('cust-id').setValue(row.get('id'));
		Ext.getCmp('cust-no').setValue(row.get('custNo'));
		Ext.getCmp('cust-info').getForm().reset();
		Ext.getCmp('cust-detail').getStore().removeAll();
		winNew.hide();
	}
});
