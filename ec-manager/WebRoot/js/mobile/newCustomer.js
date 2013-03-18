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
	var form = Ext.create('Ext.form.Panel', {
		renderTo : "new-panel", // 设置渲染的Div
		bodyPadding : 10,
		frame : true, // 设置窗体为圆角
		id : 'customer-form',
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
					xtype : 'hidden',
					allowBlank : true,
					name : 'custId',
					id : 'info-cust-id'
				}, {
					xtype : 'textfield',
					fieldLabel : '会员编号',
					disabled : true,
					anchor : '100%',
					name : 'custNo',
					id : 'info-cust-no'
				}, {
					xtype : 'textfield',
					fieldLabel : '会员名称*',
					allowBlank : false,
					anchor : '100%',
					name : 'custName',
					id : 'info-cust-name'
				}, {
					xtype : 'textfield',
					fieldLabel : '手机*',
					allowBlank : false,
					anchor : '100%',
					name : 'mobile',
					id : 'info-mobile'
				}, {
					xtype : 'textfield',
					fieldLabel : '电话',
					anchor : '100%',
					name : 'tel',
					id : 'info-tel'
				}, {
					xtype : 'datefield',
					fieldLabel : '会员生日',
					anchor : '100%',
					name : 'birthDay',
					format : 'Y-m-d',
					id : 'info-birth-day'
				}, {
					xtype : 'textfield',
					fieldLabel : '邮件',
					anchor : '100%',
					name : 'email',
					id : 'info-email'
				}, Ext.create('Ext.form.field.ComboBox', {
							fieldLabel : '性别',
							displayField : 'name',
							anchor : '100%',
							valueField : 'value',
							store : Ext.create('Ext.data.Store', {
										model : 'combo_model',
										data : [{
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
							id : "info-sex"
						}), Ext.create('Ext.form.field.ComboBox', {
							fieldLabel : '省份',
							displayField : 'name',
							anchor : '100%',
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
							allowBlank : true,
							value : "",
							name : "province",
							id : "info-province"
						}), Ext.create('Ext.form.field.ComboBox', {
							fieldLabel : '城市',
							displayField : 'name',
							anchor : '100%',
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
							allowBlank : true,
							value : "",
							name : "city",
							id : "info-city"
						}), Ext.create('Ext.form.field.ComboBox', {
							fieldLabel : '地区',
							displayField : 'name',
							anchor : '100%',
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
							allowBlank : true,
							value : "",
							name : "district",
							id : "info-district"
						}), {
					xtype : 'textfield',

					fieldLabel : '详细地址',
					width : 515,
					allowBlank : true,
					anchor : '100%',
					colspan : 2,
					name : "address",
					id : "info-address"
				}, {
					xtype : 'textfield',
					fieldLabel : '邮编',
					anchor : '100%',
					name : 'zipcode',
					id : 'info-zipcode'
				}],

		buttons : [{
					text : '返回',
					handler : function() {
						location.href = 'mobilemenu';
					}
				}, {
					text : '保存',
					handler : function() {
						var form = this.up('form').getForm();
						if (form.isValid()) {
							var custId = Ext.getCmp('info-cust-id').getValue();
							var url = '';
							if (Ext.String.trim(custId).length == 0)
								url = 'newCustomer';
							else
								url = 'updateCustomer';
							form.submit({
										url : url,
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
	});
	// 初始化省市区
	initCity('info-province', 'info-city', 'info-district');

});