Ext.define('Ssp.controller.person.ReferralSourcesViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        referralSourcesStore: 'referralSourcesStore'
    },
    
	init: function() {
		this.referralSourcesStore.load();
		
		return this.callParent(arguments);
    }
});