Ext.define('MobileApp.store.ModuleStore', {
    extend: 'Ext.data.Store',

    config: {
        model: 'MobileApp.model.ModuleModel',
        autoLoad: true,
        grouper: {
            groupFn: function(record) {
                return record.get('moduleName')[0];
            }
        },
        proxy: {
            type: 'ajax',
            url: 'mobile/modules.json'
        }
    }
});
