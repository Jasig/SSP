Ext.define('Ssp.view.person.ReferralSources', {
	extend: 'Ext.form.Panel',
	alias: 'widget.personreferralsources',
	mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.ReferralSourcesViewController',
    inject: {
    	store: 'referralSourcesStore'
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
			            name: 'referralSourceIds',
			            anchor: '100%',
			            fieldLabel: 'Referral Sources',
			            store: this.store,
			            displayField: 'name',
			            valueField: 'id',
			            allowBlank: true
			        }]
				});
		
		return this.callParent(arguments);
	}
});