{ 
text: '.',
children: [{
    text:'菜单1',
    expanded: true,
    children:[{
        text:'子菜单1',
        id:'absolute',
        leaf:true,
        url:'tabsdemo1.action'
    },{
        text:'子菜单2',
        id:'accordion',
        leaf:true,
        url:'http://www.sina.com.cn',
        iframe: true
    },{
        text:'子菜单3',
        id:'anchor',
        leaf:true
    },{
        text:'Border',
        id:'border',
        leaf:true
    },{
        text:'Card (TabPanel)',
        id:'card-tabs',
        leaf:true
    },{
        text:'Card (Wizard)',
        id:'card-wizard',
        leaf:true
    },{
        text:'Column',
        id:'column',
        leaf:true
    },{
        text:'Fit',
        id:'fit',
        leaf:true
    },{
        text:'Table',
        id:'table',
        leaf:true
    },{
        text:'vBox',
        id:'vbox',
        leaf:true
    },{
        text:'hBox',
        id:'hbox',
        leaf:true
    }]
},{
    text:'Custom Layouts',
    children:[{
        text:'Center',
        id:'center',
        leaf:true
    }]
},{
    text:'Combination Examples',
    children:[{
        text:'Absolute Layout Form',
        id:'abs-form',
        leaf:true
    },{
        text:'Tabs with Nested Layouts',
        id:'tabs-nested-layouts',
        leaf:true
    }]
}]
}