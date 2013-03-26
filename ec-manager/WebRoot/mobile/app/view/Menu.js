Ext.define('MobileApp.view.Menu', {
    extend: 'Ext.navigation.View',
    xtype: 'menuview',

    requires: [
        'MobileApp.view.Module',
        'MobileApp.view.customer.NewCustomer',
        'MobileApp.view.customer.SearchCustomer'
    ],

    config: {
        autoDestroy: false,

        navigationBar: {
            ui: 'sencha',
            items: [
                {
                    xtype: 'button',
                    id: 'saveCustomerButton',
                    text: '保存',
                    align: 'right',
                    hidden: true,
                    hideAnimation: Ext.os.is.Android ? false : {
                        type: 'fadeOut',
                        duration: 200
                    },
                    showAnimation: Ext.os.is.Android ? false : {
                        type: 'fadeIn',
                        duration: 200
                    }
                },
                {
                    xtype: 'searchfield',
                    id: 'searchCustomerButton',
                    align: 'right',
                    hidden: true,
                    hideAnimation: Ext.os.is.Android ? false : {
                        type: 'fadeOut',
                        duration: 200
                    },
                    showAnimation: Ext.os.is.Android ? false : {
                        type: 'fadeIn',
                        duration: 200
                    }
                }
            ]
        },

        items: [
            { xtype: 'modules' }
        ]
    }
});
