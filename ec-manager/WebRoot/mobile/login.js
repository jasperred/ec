

// Define our simple application
Ext.application({

	// Require the components we will be using in this example
	requires : ['Ext.form.*', 'Ext.field.*', 'Ext.Button', 'Ext.Toolbar',

			'Ext.data.Store'],

	/**
	 * The launch method of our application gets called when the application is
	 * good to launch. In here, we are going to build the structure of our
	 * application and add it into the Viewport.
	 */
	launch : function() {
		// Get all the items for our form.
		var items = this.getFormItems(), config, form;

		// Create the configuration of our form.
		// We give it the `formpanel` xtype and give it the items we got above.
		config = {
			xtype : 'formpanel',
			items : items
		};

		// If we are on a phone, we just want to add the form into the viewport
		// as is.
		// This will make it stretch to the size of the Viewport.
		if (Ext.os.deviceType == 'Phone') {
			form = Ext.Viewport.add(config);
		} else {
			// If we are not on a phone, we want to make the formpanel model and
			// give it a fixed with and height.
			Ext.apply(config, {
						modal : true,
						height : 505,
						width : 480,
						centered : true,

						// Disable hideOnMaskTap so it cannot be hidden
						hideOnMaskTap : false
					});

			// Add it to the Viewport and show it.
			form = Ext.Viewport.add(config);
			form.show();
		}

		this.form = form;
	},

	/**
	 * This method returns an array of all items we should add into the form
	 * panel we create above in our launch function. We have created this
	 * function to simply make things cleaner and easier to read and understand.
	 * You could just put these items inline up above in the form `config`.
	 * 
	 * @return {Array} items
	 */
	getFormItems : function() {
		return [{
			xtype : 'fieldset',
			title : '用户登录',
			id : 'login',
			instructions : '请输入用户名和密码.',
			defaults : {
				required : true,
				labelAlign : 'left',
				labelWidth : '40%'
			},
			items : [{
						xtype : 'textfield',
						name : "companyNo",
						id : "companyno",
						label : '公司编号',
						autoCapitalize : false
					}, {
						xtype : 'textfield',
						name : "userName",
						id : "username",
						label : '用户名',
						autoCapitalize : false
					}, {
						xtype : 'passwordfield',
						name : "password",
						id : "password",
						label : '密码'
					}, {
						label : '验证码',
						xtype : 'textfield',
						name : 'rand',
						id : 'randCode',
						allowBlank : false,
						blankText : '验证码不能为空'
					}, {
						xtype : 'label',
						id : 'randImage',
						html : "<iframe src='framework/image.jsp' width='100%' height='30px;' align=right frameborder='0'></iframe>"

					}, {
						xtype : 'label',
						id : 'message-label',
						hidden : true,
						text : '',
						style : 'color:red',
						margins : '0 0 0 10'
					}, {
						xtype : 'button',
						text : '登录',
						ui : 'confirm',
						scope : this,
						handler : function() {
							var form = this.form;
							var companyNo = Ext.getCmp('companyno').getValue();
							var rand = Ext.getCmp('randCode').getValue();
							var userName = Ext.getCmp('username').getValue();
							var password = Ext.getCmp('password').getValue();
							if (Ext.String.trim(companyNo).length == 0) {
								Ext.getCmp('login').setInstructions('请输入公司编号');
								return;
							}
							if (Ext.String.trim(userName).length == 0) {
								Ext.getCmp('login').setInstructions('请输入用户名');
								return;
							}
							if (Ext.String.trim(password).length == 0) {
								Ext.getCmp('login').setInstructions('请输入密码');
								return;
							}
							if (Ext.String.trim(rand).length == 0) {
								Ext.getCmp('login').setInstructions('请输入验证码');
								return;
							}
							// Mask the form
							form.setMasked({
										xtype : 'loadmask',
										message : '登录中...'
									});
							Ext.Ajax.request({
								url : 'processLogin',
								params : {
									companyNo : companyNo,
									rand : rand,
									userName : userName,
									password : password
								},
								success : function(response, options) {
									var text = response.responseText;
									var responseArray = Ext.JSON.decode(text);
									if (responseArray.success) {
										location.href = 'mobilemenu';
									} else {
										form.setMasked(false);
										Ext
												.getCmp('login')
												.setInstructions(responseArray.message);
									}
								}
							});
						}
					}]
		}

		];
	}
});
