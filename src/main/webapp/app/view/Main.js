Ext.define('Ssp.view.Main', {
	extend: 'Ext.panel.Panel',
    alias: 'widget.Main',
    id: 'Main',
    title: 'Student Success Plan',
    layout: {
    	type: 'hbox',
    	align: 'stretch'
    },
    dockedItems: {
        itemId: 'toolbar',
        xtype: 'toolbar',
        items: [ 
		        {
		            xtype: 'button',
		            text: 'Student Record'
		        }, {
		            xtype: 'button',
		            text: 'Admin'
		        }, {
		            xtype: 'button',
		            text: 'Reports'
		        }
        ]
    }
});	