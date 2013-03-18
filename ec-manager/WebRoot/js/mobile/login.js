Ext.onReady(function fn() {
	var myLoginForm = new Ext.FormPanel({ // 定义一个FormPanel对象
		
		frame : true, // 设置窗体为圆角
		renderTo : "login-form", // 设置渲染的Div
		title : "登录",
        layout: 'anchor',
		bodyStyle : 'padding-top:6px;',
		method : "POST",
		defaultType : 'textfield',
		autoHeight : true,
		border : false,
		standardSubmit : false,
		onSubmit : Ext.emptyFn,
		bodyPadding : 10,

		fieldDefaults : {
			msgTarget : 'side',
			labelAlign : 'left',
			labelStyle : 'font-weight:bold',
			height : 50
		},
		// 以下两个为Submit的主要方法
		// onSubmit : Ext.emptyFn,
		// submit : function() {
		// this.getEl().dom.action = "login";
		// this.getEl().dom.submit();
		// },
		items : [ // 设置FormPanel的子对象
		{
					fieldLabel : "公司编号", 
					xtype : "textfield",
					allowBlank : false, // 是否允许为空,默认为 true
					blankText : "公司编号不能为空", // 显示错误提示信息
					listeners : {
						specialKey : keyEvent
					},
					name : "companyNo",
					id : "companyno",
					value : "",
            anchor:'100%'
				}, {
					fieldLabel : "用户名", // 标签内容
					xtype : "textfield", // 对象的类型,默认为 textfield
					allowBlank : false, // 是否允许为空,默认为 true
					blankText : "用户名不能为空", // 显示错误提示信息
					listeners : {
						specialKey : keyEvent
					},
					name : "userName",
					id : "username",
            anchor:'100%'
				}, {
					fieldLabel : "密码",
					xtype : "textfield",
					allowBlank : false,
					blankText : "密码不能为空",
					inputType : "password", // 输入类型为 password
					listeners : {
						specialKey : keyEvent
					},
					name : "password",
					id : "password",
            anchor:'100%'
				}, {
					xtype : 'fieldcontainer',
					fieldLabel : '验证码',
					layout : 'hbox',
					defaultType : 'textfield',
					fieldDefaults : {
						msgTarget : 'side',
						labelAlign : 'top',
			height : 50
					},

					items : [{
								cls : 'key',
								name : 'rand',
								id : 'randCode',
								listeners : {
									specialKey : keyEvent
								},
								width : 85,
								allowBlank : false,
								blankText : '验证码不能为空'
							}, {
								id : 'randImage',
								width : 65,
								html : "<iframe src='framework/image.jsp' width='100%' height='50px;' frameborder='0'></iframe>"

							}]
				}, {
					xtype : 'label',
					id : 'message-label',
					hidden : true,
					text : '',
					style : 'color:red',
					margins : '0 0 0 10'
				}],
		buttons : [{
					text : "登录",
					handler : ajaxSubmit,
					width : 80,
					height : 50
				}, {
					text : "取消",
					handler : function() {
						myLoginForm.form.reset(); // 重置表单
					},
					width : 80,
					height : 50
				}]
	});
	//myLoginForm.el.center();
	function submit() {
		if (!myLoginForm.getForm().isValid())
			return;
		myLoginForm.getForm().submit({
					waitMsg : '登录中',
					waitTitle : '提示',
					url : 'processLogin',
					success : function(form, action) {
						alert(action);
					},
					failure : function(form, action) {
						alert(action);
					}
				});
	}
	function keyEvent(field, e) {
		if (e.getKey() == e.ENTER && this.getValue().length > 0) {
			if (!myLoginForm.getForm().isValid())
				return;
			ajaxSubmit();
		}
	}
	function ajaxSubmit() {
		Ext.Msg.wait('登录中，请稍后...', '提示');
		Ext.getCmp('message-label').setVisible(false);
		Ext.Ajax.request({
					url : 'processLogin',
					params : {
						companyNo : Ext.getCmp('companyno').getValue(),
						rand : Ext.getCmp('randCode').getValue(),
						userName : Ext.getCmp('username').getValue(),
						password : Ext.getCmp('password').getValue()
					},
					success : function(response, options) {
						var text = unicodeToString(response.responseText);
						var responseArray = Ext.JSON.decode(text);
						if (responseArray.success) {
							location.href = 'mobilemenu';
						} else {
							Ext.Msg.hide();
							Ext.getCmp('message-label')
									.setText(responseArray.message);
							Ext.getCmp('message-label').setVisible(true);
						}
					}
				});
	}
	// 以下为显示验证提示的主要设置
	Ext.QuickTips.init();
});