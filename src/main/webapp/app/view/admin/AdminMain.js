Ext.define('Ssp.view.admin.AdminMain', {
	extend: 'Ext.panel.Panel',
    alias: 'widget.AdminMain',
    id: 'AdminMain',
    title: 'Admin Main',
	initComponent: function() {	
		Ext.apply(this, 
				{
				    layout: {
				    	type: 'hbox',
				    	align: 'stretch'
				    }
				});
		
	     this.callParent(arguments);
	}
});