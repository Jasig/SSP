Ext.define('Ssp.view.tools.studentintake.Funding', {
	extend: 'Ext.form.Panel',
	alias: 'widget.studentintakefunding',
	id : 'StudentIntakeFunding',   
    width: '100%',
    height: '100%',
	initComponent: function() {	
		Ext.apply(this, 
				{
					autoScroll: true,
				    bodyPadding: 5,
				    layout: 'anchor',
				    defaults: {
				        anchor: '100%'
				    },
				    defaultType: 'checkbox'
				});
		
		return this.callParent(arguments);
	}
});