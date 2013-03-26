Ext.define('MobileApp.controller.Application', {
			extend : 'Ext.app.Controller',

			config : {
				refs : {
					main : 'menuview',
					saveCustomerButton : '#saveCustomerButton',
					modules : 'modules',
					newCustomer : 'new-customer',
					searchCustomer : 'search-customer',
					searchCustomerButton : '#searchCustomerButton'
				},

				control : {
					main : {
						push : 'onMainPush',
						pop : 'onMainPop'
					},
					saveCustomerButton : {
						tap : 'onCustomerSave'
					},
					modules : {
						itemtap : 'onModuleSelect'
					},
					searchCustomerButton : {
						action : 'onSearchCustomer'
					}
				}
			},

			onMainPush : function(view, item) {
				var saveCustomerButton = this.getSaveCustomerButton();
				if (item.xtype == "new-customer") {
					this.getModules().deselectAll();

					this.showSaveCustomerButton();
				} else {
					this.hideSaveCustomerButton();
				}
				var saveCustomerButton = this.getSaveCustomerButton();
				if (item.xtype == "search-customer") {
					this.getModules().deselectAll();

					this.showSearchCustomerButton();
					this.onSearchCustomer();
				} else {
					this.hideSearchCustomerButton();
				}
			},

			onMainPop : function(view, item) {
				this.hideSaveCustomerButton();
				this.hideSearchCustomerButton();
			},

			onModuleSelect : function(list, index, node, record) {
				// 新建会员
				if (record.get('moduleId') == 'NewCustomer') {
					if (!this.newCustomer) {
						this.newCustomer = Ext
								.create('MobileApp.view.customer.NewCustomer');

						// 初始化省市区
						initCity('customer-province', 'customer-city',
								'customer-district');
					}

					this.getMain().push(this.newCustomer);
				}
				if (record.get('moduleId') == 'SearchCustomer') {
					if (!this.searchCustomer) {
						this.searchCustomer = Ext
								.create('MobileApp.view.customer.SearchCustomer');

					}
					this.getMain().push(this.searchCustomer);
				}
			},

			onCustomerSave : function() {
				var custName = Ext.getCmp('new-cust-name').getValue();
				var mobile = Ext.getCmp('new-mobile').getValue();
				if (Ext.String.trim(custName).length == 0) {
					Ext.Msg.alert('提示', "请填写客户名称!");
					return;
				}
				var ab = /^[1][358]\d{9}$/;

				if (ab.test(mobile) == false) {
					Ext.Msg.alert('提示', "请填写正确手机号码!");
					return;
				}
				var form = this.newCustomer;
				form.setMasked({
							xtype : 'loadmask',
							message : '处理中...'
						});
				form.submit({
							url : 'newCustomer',
							success : function(form, action) {
								form.setMasked(false);
								Ext.Msg.alert('提示', "处理成功!");
								form.reset();
							},
							failure : function(form, action) {
								form.setMasked(false);
								Ext.Msg.alert('错误', action.result.message);
							}
						});
			},

			onSearchCustomer : function() {
				var store = this.searchCustomer.getStore();
				store.load({
							params : {
								q : this.getSearchCustomerButton().getValue(),
								start : 0,
								limit : 25
							}
						});
			},

			showSaveCustomerButton : function() {
				var saveCustomerButton = this.getSaveCustomerButton();

				if (!saveCustomerButton.isHidden()) {
					return;
				}

				this.hideSearchCustomerButton();

				saveCustomerButton.show();
			},

			hideSaveCustomerButton : function() {
				var saveCustomerButton = this.getSaveCustomerButton();

				if (saveCustomerButton.isHidden()) {
					return;
				}

				saveCustomerButton.hide();
			},

			showSearchCustomerButton : function() {
				var searchCustomerButton = this.getSearchCustomerButton();

				if (!searchCustomerButton.isHidden()) {
					return;
				}

				searchCustomerButton.show();
			},

			hideSearchCustomerButton : function() {
				var searchCustomerButton = this.getSearchCustomerButton();

				if (searchCustomerButton.isHidden()) {
					return;
				}

				searchCustomerButton.hide();
			}
		});
