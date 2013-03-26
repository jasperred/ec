Ext.define('MobileApp.view.customer.SearchCustomer', {
			extend : 'Ext.DataView',
			xtype : 'search-customer',
			requires : ['MobileApp.view.customer.CustomerListItem'],

			config : {
				ui : 'loans',
				store : 'CustomerStore',
				useComponents : true,
				defaultType : 'customerlistitem',
				deselectOnContainerClick : false
			},
			plugins : [{
						xclass : 'Ext.plugin.ListPaging',
						noMoreRecordsText : '没有更多记录了',
						loadMoreText : '更多',
						autoPaging : true
					}]

			,
			scrollDockHeightRefresh : function() {

			}
		});
