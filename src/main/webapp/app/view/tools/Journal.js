Ext.define('Ssp.view.tools.Journal', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.journal',
	id: 'Journal',
    title: 'Journal',
	width: '100%',
	height: '100%',
	initComponent: function() {	
		Ext.apply(this, 
				{
					autoScroll: true
				});
		
	     this.callParent(arguments);
	}
});