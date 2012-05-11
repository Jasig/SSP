Ext.define('Ssp.controller.admin.crg.ChallengeAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	confidentialityLevelsStore: 'confidentialityLevelsStore'
    },
	init: function() {
		this.confidentialityLevelsStore.load();	
		return this.callParent(arguments);
    }
});