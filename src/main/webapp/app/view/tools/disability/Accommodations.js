Ext.define('Ssp.view.tools.disability.Accommodations', {
	extend: 'Ext.form.Panel',
	alias: 'widget.disabilityaccommodations',
    width: '100%',
    height: '100%',
    autoScroll: true,
	initComponent: function() {	
        var me = this;

		Ext.apply(me, 
				{
				    bodyPadding: 5,
				    layout: 'anchor',
				    defaults: {
				        anchor: '100%'
				    },
				    defaultType: 'checkbox'
				});
		
		return me.callParent(arguments);
	}
});