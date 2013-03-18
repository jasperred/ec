Ext.onReady(function fn() {
			var myLoginForm = new Ext.FormPanel({ // 定义一个FormPanel对象

				frame : true, // 设置窗体为圆角
				renderTo : "menu", // 设置渲染的Div
				title : "菜单",
				layout : 'anchor',
				bodyStyle : 'padding-top:6px;',
				method : "POST",
				defaultType : 'button',
				// height : '100%',
				border : false,
				items : [ // 设置FormPanel的子对象
				{
							text : "新建会员",
							height : 50,
							anchor : '100%',
							margin : '10 0 0 0 ',
							handler : function() {
								location.href = 'mobileNewCustomer';
							}
						}, {
							text : "查询会员",
							height : 50,
							anchor : '100%',
							margin : '10 0 0 0 ',
							handler : function() {
								location.href = 'mobileSearchCustomer';
							}
						}, {
							text : "退出",
							height : 50,
							anchor : '100%',
							margin : '10 0 0 0 ',
							handler : function() {
								location.href = 'mobilelogout';
							}
						}]
			});

		});