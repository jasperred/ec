Ext.define('MobileApp.view.Module', {
    extend: 'Ext.List',
    xtype: 'modules',

    config: {
        title: '菜单',
        cls: 'x-contacts',

        store: 'ModuleStore',
        itemTpl: [
            '{moduleName}'
        ].join('')
    }
});
