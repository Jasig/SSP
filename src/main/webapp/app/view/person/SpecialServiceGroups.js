Ext.define('Ssp.view.person.SpecialServiceGroups', {
	extend: 'Ext.form.Panel',
	alias: 'widget.personspecialservicegroups',
	mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.SpecialServiceGroupsViewController',
	width: '100%',
    height: '100%',
    autoScroll: true,
	initComponent: function() {	
		var me=this;
		Ext.apply(me, 
				{
				    bodyPadding: 5,
				    layout: 'anchor'
				});
		
		return me.callParent(arguments);
	}
});