Ext.define('Ssp.view.person.SpecialServiceGroups', {
	extend: 'Ext.form.Panel',
	alias: 'widget.personspecialservicegroups',
	mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.SpecialServiceGroupsViewController',
    inject: {
    	store: 'specialServiceGroupsBindStore'
    },
	width: '100%',
    height: '100%',
    autoScroll: true,
	initComponent: function() {	
		Ext.apply(this, 
				{
				    bodyPadding: 5,
				    layout: 'anchor',
				    items:[{
			            xtype: 'itemselectorfield',
			            itemId: 'specialServiceGroupsItemSelector',
			            name: 'specialServiceGroupIds',
			            anchor: '100%',
			            height: 200,
			            fieldLabel: 'Service Groups',
			            store: this.store,
			            displayField: 'name',
			            valueField: 'id',
			            allowBlank: true
			        }]
				});
		
		return this.callParent(arguments);
	}
});