Ext.define('Ssp.view.admin.AdminMain', {
	extend: 'Ext.panel.Panel',
    alias: 'widget.adminmain',
    id: 'AdminMain',
    title: 'Admin Main',
    height: '100%',
    width: '100%',
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