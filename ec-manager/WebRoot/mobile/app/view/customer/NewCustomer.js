Ext.define('MobileApp.view.customer.NewCustomer', {
			extend : 'Ext.form.Panel',
			xtype : 'new-customer',
			scrollable : {
				direction : 'vertical',
				directionLock : true
			},

			requires : ['Ext.Map'],

			config : {
				title : '新建会员',
				xtype : 'formpanel',

				items : [{
							xtype : 'fieldset',
							title : '填写会员信息',
							instructions : '请填写会员信息.',
							defaults : {
								required : true,
								labelAlign : 'left',
								labelWidth : '40%'
							},
							items : [{
										xtype : 'hiddenfield',
										name : 'crumb',
										value : Ext.get('crumb').getValue()
									}, {
										xtype : 'textfield',
										name : 'custName',
										label : '会员名称',
										id : 'new-cust-name',
										autoCapitalize : false
									}, {
										xtype : 'numberfield',
										name : 'mobile',
										label : '手机',
										id : 'new-mobile',
										maxLength : 11,
										autoCapitalize : false
									}, {
										xtype : 'textfield',
										name : 'tel',
										label : '电话',
										autoCapitalize : false
									}, {
										xtype : 'datepickerfield',
										name : 'birthDay',
										label : '会员生日',
										value : new Date('1980-01-01'),
										dateFormat : 'y-M-d',
										picker : {
											yearFrom : 1900
										}
									}, {
										xtype : 'emailfield',
										name : 'email',
										label : '邮件',
										placeHolder : 'you@sencha.com',
										autoCapitalize : false
									}, {
										xtype : 'selectfield',
										name : 'sex',
										label : '性别',
										valueField : 'rank',
										displayField : 'title',
										store : {
											data : [{
														rank : '男',
														title : '男'
													}, {
														rank : '女',
														title : '女'
													}]
										}
									}, {
										xtype : 'selectfield',
										id : 'customer-province',
										name : 'province',
										label : '省份',
										valueField : 'value',
										displayField : 'name',
										store : Ext.create('Ext.data.Store', {
													fields : ['name', 'value'],
													data : [{
																"name" : "请选择省份",
																"value" : ""
															}]
												})
									}, {
										xtype : 'selectfield',
										id : 'customer-city',
										name : 'city',
										label : '城市',
										valueField : 'value',
										displayField : 'name',
										store : Ext.create('Ext.data.Store', {
													fields : ['name', 'value'],
													data : [{
																"name" : "请选择城市",
																"value" : ""
															}]
												})
									}, {
										xtype : 'selectfield',
										id : 'customer-district',
										name : 'district',
										label : '地区',
										valueField : 'value',
										displayField : 'name',
										store : Ext.create('Ext.data.Store', {
													fields : ['name', 'value'],
													data : [{
																"name" : "请选择地区",
																"value" : ""
															}]
												})
									}, {
										xtype : 'textfield',
										name : 'address',
										label : '详细地址',
										autoCapitalize : false
									}, {
										xtype : 'textfield',
										name : 'zipcode',
										label : '邮编',
										autoCapitalize : false
									}]
						}]
			}
		});
