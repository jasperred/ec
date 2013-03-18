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
				items : [Ext.create('Ext.form.field.ComboBox', {
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
							xtype : 'button',

							text : '收货地址',
							margin : '2 5 2 0',
							colspan : 3,
							id : "receiver-address-button",
							handler : showAddressView
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
							colspan : 3,
							id : "payment"
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
						orderItems : orderItemStr
					},
					success : function(response, options) {
						var text = unicodeToString(response.responseText);
						var responseArray = Ext.JSON.decode(text);
						if (responseArray.success) {
							Ext.MessageBox.alert('提示', "处理成功!");
							searchPanel.getForm().reset();
							resultGrid.getStore().removeAll();

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
	function itemView() {
		Ext.Ajax.request({
					url : 'itemSkuSearch',
					params : {
						barcode : '123'
					},
					success : function(response, options) {
						var text = unicodeToString(response.responseText);
						var responseArray = Ext.JSON.decode(text);
						if (responseArray.success) {
							Ext.MessageBox.alert('提示', "处理成功!");
							Ext.getCmp('search-panel').getForm().reset();
							Ext.getCmp('result-grid').getStore().removeAll();

						} else {
							Ext.MessageBox.alert("错误", responseArray.message);
						}
					}
				});
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
	// 显示地址查询界面
	function showAddressView() {
		if (winNew == undefined) {
			var addressinfo = Ext.create('Ext.form.Panel', {
						title : "地址查询",
						id : "address-info",
						layout : {
							type : 'table',
							columns : 2
						},
						animCollapse : true,
						bodyPadding : 2,
						collapsible : true,
						items : [{
									xtype : 'textfield',
									fieldLabel : '收件人',
									margin : '2 5 2 0',
									id : "s-consignee"
								}, {
									xtype : 'textfield',
									fieldLabel : '手机',
									margin : '2 5 2 0',
									id : "s-mobile"
								}, {
									xtype : 'textfield',
									fieldLabel : '邮件',
									margin : '2 5 2 0',
									id : "s-email"
								}, {
									xtype : 'textfield',
									fieldLabel : '电话',
									margin : '2 5 2 0',
									id : "s-tel"
								}],
						buttons : [{
									text : "查询",
									handler : searchAddress
								}, {
									text : "返回",
									handler : function() {
										addressinfo.getForm().reset();
										addressdetail.getStore().removeAll();
										winNew.hide();
									}
								}]
					});
			var addressdetail = Ext.create('Ext.grid.Panel', {
						title : "地址明细",
						id : "address-detail",
						animCollapse : true,
						collapsible : true,
						minHeight : 40,
						store : Ext.create('Ext.data.Store', {
									fields : ['consignee', 'province', 'city',
											'district', 'address', 'mobile',
											'tel', 'email', 'zipcode']
								}),
						columns : [{
									xtype : 'rownumberer'
								}, {
									header : "收货人",
									dataIndex : 'consignee',
									width : 100,
									sortable : false
								}, {
									header : "省份",
									dataIndex : 'province',
									width : 100,
									sortable : true
								}, {
									header : "城市",
									dataIndex : 'city',
									flex : 1,
									sortable : true
								}, {
									header : "区",
									dataIndex : 'district',
									width : 120,
									sortable : true
								}, {
									header : "详细地址",
									dataIndex : 'address',
									width : 80,
									sortable : true
								}, {
									header : "手机",
									dataIndex : 'mobile',
									width : 100,
									sortable : true
								}, {
									header : "电话",
									dataIndex : 'tel',
									width : 100,
									sortable : true
								}, {
									header : "邮件",
									dataIndex : 'email',
									width : 100,
									sortable : true
								}, {
									header : "邮编",
									dataIndex : 'zipcode',
									width : 100,
									sortable : true
								}]
					});
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
						items : [addressinfo, addressdetail],
						buttons : [{
									text : "选择",
									handler : selectAddress,
									id : 'search-address-button'
								}]
					});
		}
		winNew.show();
	}
	// 地址查询
	function searchAddress() {
		Ext.Ajax.request({
					url : 'addressSearchJsonAction',
					params : {
						consignee : Ext.getCmp('s-consignee').getValue(),
						email : Ext.getCmp('s-email').getValue(),
						tel : Ext.getCmp('s-tel').getValue(),
						mobile : Ext.getCmp('s-mobile').getValue()
					},
					waitMsg : '正在加载信息...',
					success : function(response, options) {
						var text = unicodeToString(response.responseText);
						var responseArray = Ext.JSON.decode(text);

						Ext.getCmp('address-detail').getStore()
								.loadData(responseArray.addressList);
					}
				});
	}
	// 地址选择
	function selectAddress() {
		var row = Ext.getCmp('address-detail').getSelectionModel()
				.getSelection()[0];
		if (!row) {
			return;
		}
		Ext.getCmp("receiver-address").setValue(row.get('address'));
		Ext.getCmp('receiver-city').setRawValue(row.get('city'));
		Ext.getCmp('receiver-district').setRawValue(row.get('district'));
		Ext.getCmp('receiver-mobile').setValue(row.get('mobile'));
		Ext.getCmp('receiver-name').setValue(row.get('consignee'));
		Ext.getCmp('receiver-state').setRawValue(row.get('province'));
		Ext.getCmp('receiver-tel').setValue(row.get('tel'));
		Ext.getCmp('receiver-zip').setValue(row.get('zipcode'));
		Ext.getCmp('buyer-email').setValue(row.get('email'));
		Ext.getCmp('address-info').getForm().reset();
		Ext.getCmp('address-detail').getStore().removeAll();
		winNew.hide();
	}
});
