Ext.define('Ssp.view.person.ServiceReasons', {
	extend: 'Ext.form.Panel',
	alias: 'widget.personservicereasons',
	id: 'personservicereasons',
	mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.ServiceReasonsViewController',
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