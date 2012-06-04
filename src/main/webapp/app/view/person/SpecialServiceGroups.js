Ext.define('Ssp.view.person.SpecialServiceGroups', {
	extend: 'Ext.form.Panel',
	alias: 'widget.personspecialservicegroups',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	store: 'specialServiceGroupsStore'
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
			            name: 'specialServiceGroupIds',
			            anchor: '100%',
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