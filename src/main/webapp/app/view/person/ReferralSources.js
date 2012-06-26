Ext.define('Ssp.view.person.ReferralSources', {
	extend: 'Ext.form.Panel',
	alias: 'widget.personreferralsources',
	mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.ReferralSourcesViewController',
	width: '100%',
    height: '100%',
    autoScroll: true,
	initComponent: function() {	
		var me=this;
		Ext.apply(this, 
				{
				    bodyPadding: 5,
				    layout: 'anchor'
				});
		
		return me.callParent(arguments);
	}
});