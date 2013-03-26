Ext.define('MobileApp.store.CustomerStore', {
			extend : 'Ext.data.Store',

			config : {
				model : 'MobileApp.model.CustomerModel',
				proxy : {
					type : "ajax",
					url : "customerSearchByQ",
					reader : {
						type : "json",
						rootProperty : "resultList",
						totalProperty : "rowCount"
					}
				},
				pageSize : 50,
				autoLoad : false
			}
		});
