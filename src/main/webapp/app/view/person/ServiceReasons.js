Ext.define('Ssp.view.person.ServiceReasons', {
	extend: 'Ext.form.Panel',
	alias: 'widget.personservicereasons',
	id: 'personservicereasons',
    width: '100%',
    height: '100%',
    autoScroll: true,
	initComponent: function() {	
		Ext.apply(this, 
				{
				    bodyPadding: 0,
				    border: 0,
				    layout: 'anchor',
				    defaults: {
				        anchor: '100%'
				    },
				    defaultType: 'checkbox'
				});
		
		return this.callParent(arguments);
	}
});