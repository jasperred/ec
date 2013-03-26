Ext.Loader.setConfig({
			enabled : true
		});

Ext.Loader.setPath('Ext.ux', 'js/ext/ux/');
Ext.require(['Ext.grid.*', 'Ext.data.*', 'Ext.util.*', 'Ext.tip.QuickTipManager']);
Ext.onReady(function() {
	Ext.tip.QuickTipManager.init();
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
	// 查询条件panel
	
	var searchPanel = Ext.create('Ext.form.Panel', {
				border : false,
				bodyPadding : 10,
				url : 'modifyPasswordByUser',
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
							xtype : 'textfield',
							fieldLabel : '旧密码',
							inputType : "password",
							allowBlank : false,
							name : 'password',
							id : 'oldPassword'
						}, {
							xtype : 'textfield',
							fieldLabel : '新密码',
							inputType : "password",
							allowBlank : false,
							name : 'newPassword',
							id : 'newPassword'
						}, {
							xtype : 'textfield',
							fieldLabel : '确认新密码',
							inputType : "password",
							allowBlank : false,
							name : 'repassword',
							vtype : 'password',
							initialPassField : 'newPassword'
						}],

				buttons : [ {
							text : '修改密码',
							handler : function() {
								var form = this.up('form').getForm();
								if (form.isValid()) {
									
									form.submit({
										success : function(form, action) {
											Ext.MessageBox.alert('提示', "处理成功!");
											form.reset();
										},
										failure : function(form, action) {
											Ext.MessageBox.alert('错误',
													action.result.message);
										}
									});
								}
							}
						}]
			,
				renderTo : 'search-panel'
			});
});