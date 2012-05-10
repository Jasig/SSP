Ext.define('Ssp.controller.admin.ChallengeAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	confidentialityLevelsStore: 'confidentialityLevelsStore'
    },
	init: function() {
		this.confidentialityLevelsStore.load();	
		return this.callParent(arguments);
    }
});