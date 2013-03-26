Ext.define('MobileApp.model.CustomerModel', {
    extend: 'Ext.data.Model',

    config: {
        fields: ['id', 'custNo', 'custName', 'email',
										'mobile', 'sex', 'birthDay']
    }
});
