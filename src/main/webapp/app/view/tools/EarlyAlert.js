Ext.define('Ssp.view.tools.EarlyAlert', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.earlyalert',
	id: 'EarlyAlert',
    title: 'Early Alert',
	width: '100%',
	height: '100%',
	initComponent: function() {	
		Ext.apply(this, 
				{
					autoScroll: true
				});
		
		return this.callParent(arguments);
	}
});