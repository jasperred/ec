
Ext.define('MobileApp.view.customer.CustomerListItem', {
    extend: 'Ext.dataview.component.DataItem',
    xtype : 'customerlistitem',

    config: {
        dataMap: {
            getName: {
                setHtml: 'custNo'
            },

            getUse: {
                setHtml: 'custName'
            },

            getAvatar: {
                setHtml: 'mobile'
            }
        },

        cls: Ext.baseCSSPrefix + 'list-item',

        name: {
            cls: 'name'
        },

        use: {
            cls: 'use'
        },

        avatar: {
            cls: 'avatar'
        },

        layout: {
            type: 'hbox',
            pack: 'left'
        }
    },

    applyName: function(config) {
        return Ext.factory(config, Ext.Component, this.getName());
    },

    updateName: function(newName) {
        if (newName) {
            this.add(newName);
        }
    },

    applyUse: function(config) {
        return Ext.factory(config, Ext.Component, this.getUse());
    },

    updateUse: function(newUse) {
        if (newUse) {
            this.add(newUse);
        }
    },

    applyAvatar: function(config) {
        return Ext.factory(config, Ext.Img, this.getAvatar());
    },

    updateAvatar: function(newAvatar) {
        if (newAvatar) {
            this.add(newAvatar);
        }
    }
});
