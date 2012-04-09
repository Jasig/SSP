Ext.define('Ssp.view.Main', {
	extend: 'Ext.panel.Panel',
    alias: 'widget.main',
    id: 'Main',
    title: 'Student Success Plan',
    layout: {
    	type: 'hbox',
    	align: 'stretch'
    },
    dockedItems: {
    	id: 'MainNav',
        itemId: 'toolbar',
        xtype: 'toolbar',
        items: [ 
		        {
		            xtype: 'button',
		            id: 'studentViewNav',
		            text: 'Students'
		        }, {
		            xtype: 'button',
		            id: 'adminViewNav',
		            text: 'Admin'
		        }
        ]
    }
});	