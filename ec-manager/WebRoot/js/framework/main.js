Ext.Loader.setConfig({enabled: true});

Ext.Loader.setPath('Ext.ux', 'js/ext/ux/');
    Ext.require(['*',
                 'Ext.ux.TabScrollerMenu']);

    Ext.onReady(function() {
        Ext.QuickTips.init();
        Ext.tip.QuickTipManager.init();

        // NOTE: This is an example showing simple state management. During development,
        // it is generally best to disable state management as dynamically-generated ids
        // can change across page loads, leading to unpredictable results.  The developer
        // should ensure that stable state ids are set for stateful components in real apps.
        Ext.state.Manager.setProvider(Ext.create('Ext.state.CookieProvider'));
        Ext.define('Task', {
            extend: 'Ext.data.Model',
            fields: [
                {name: 'id',     type: 'string'},
                {name: 'text',     type: 'string'},
                {name: 'url',     type: 'string'},
                {name: 'sequence',     type: 'int'},
                {name: 'iframe',     type: 'boolean'}
            ]
        });
        var store = Ext.create('Ext.data.TreeStore', {
        	model: 'Task',
            proxy: {
                type: 'ajax',
                url: 'menu'
            },
            sorters: [{
                property: 'leaf',
                direction: 'ASC'
            }, {
                property: 'sequence',
                direction: 'ASC'
            }]
        });
        
        var viewport = Ext.create('Ext.Viewport', {
            id: 'base-main',
            layout: 'border',
            items: [
            // create instance immediately
            Ext.create('Ext.Component', {
                region: 'north',
                contentEl: 'top',
                split: true,
                height: 32
            })
//            , {
//                // lazily created panel (xtype:'panel' is default)
//                region: 'south',
//                contentEl: 'bottom',
//                split: true,
//                height: 50,
//                minSize: 100,
//                maxSize: 200,
//                collapsible: true,
//                collapsed: true,
//                title: '',
//                margins: '0 0 0 0'
//            }
//            , {
//                xtype: 'tabpanel',
//                region: 'east',
//                title: 'East Side',
//                dockedItems: [{
//                    dock: 'top',
//                    xtype: 'toolbar',
//                    items: [ '->', {
//                       xtype: 'button',
//                       text: 'test',
//                       tooltip: 'Test Button'
//                    }]
//                }],
//                animCollapse: true,
//                collapsible: true,
//                split: true,
//                width: 225, // give east and west regions a width
//                minSize: 175,
//                maxSize: 400,
//                margins: '0 5 0 0',
//                activeTab: 1,
//                tabPosition: 'bottom',
//                items: [{
//                    html: '<p>A TabPanel component can be a region.</p>',
//                    title: 'A Tab',
//                    autoScroll: true
//                }
//                , Ext.create('Ext.grid.PropertyGrid', {
//                        title: 'Property Grid',
//                        closable: true,
//                        source: {
//                            "(name)": "Properties Grid",
//                            "grouping": false,
//                            "autoFitColumns": true,
//                            "productionQuality": false,
//                            "created": Ext.Date.parse('10/15/2006', 'm/d/Y'),
//                            "tested": false,
//                            "version": 0.01,
//                            "borderWidth": 1
//                        }
//                    })]
//            }
            ,{
                region: 'west',
                stateId: 'navigation-panel',
                id: 'west-panel', // see Ext.getCmp() below
                title: '菜单',
                split: true,
                width: 200,
                layout: 'fit',
                minWidth: 175,
                maxWidth: 400,
                collapsible: true,
                animCollapse: true,
                margins: '0 0 0 5',
                items: [{
                id: 'tree-panel',
                xtype: 'treepanel',
                store: store,
                rootVisible: false,
                useArrows: true,
                frame: false
            }]
            }, 
            // in this instance the TabPanel is not wrapped by another panel
            // since no title is needed, this Panel is added directly
            // as a Container
            {
                xtype: 'tabpanel',
                id:'tab-panel',
                region: 'center', // a center region is ALWAYS required for border layout
                //deferredRender: false,
                activeTab: 0,     // first tab initially active
//                plugins: [{
//                    ptype: 'tabscrollermenu',
//                    maxText  : 15,
//                    pageSize : 5
//                }],
                items: [ {
                    contentEl: 'center',
                    title: 'Center Panel', 
                    autoScroll: true
                }]
            }]
        });
        tabPanel = Ext.getCmp('tab-panel');
        treePanel = Ext.getCmp('tree-panel');
        treePanel.getSelectionModel().addListener('select', function(selModel, record) {            
            if (record.get('leaf')) {
                selectTab = Ext.getCmp('tab-'+record.get('id'));
                if(selectTab==undefined)
                {
                	var tab = [];
                	if(record.get('iframe'))
                	{
	                	tab.push({
	                    	id: 'tab-'+record.get('id'),
	                    	title: record.get('text'),
	                    	closable: true,
	    	                html:'<iframe src="'+record.get('url')+'" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>'});
                	}
                	else
                	{                		
	                	tab.push({
	                    	id: 'tab-'+record.get('id'),
	                    	title: record.get('text'),
	                    	closable: true,
	    	                loader: {
	    	                    url: record.get('url'),
	    	                    contentType: 'html',
	    	                    autoLoad: true,
	    	                    scripts: true
	                    }});
                	}
                	t = tabPanel.add(tab);
                	tabPanel.setActiveTab('tab-'+record.get('id'));
                }
                else
                {
                	tabPanel.setActiveTab(selectTab);
                }
            	
            }
        });
    });