Ext.define('Ssp.controller.admin.crg.AssociateChallengeReferralsAdminViewController', {
	extend: 'Ssp.controller.admin.AdminItemAssociationViewController',
    config: {
        associatedItemType: 'challengeReferral',
        parentItemType: 'challenge',
        parentIdAttribute: 'challengeId',
        associatedItemIdAttribute: 'challengeReferralId'
    },
	constructor: function(){
		var me=this;
		me.callParent(arguments);
		me.clear();
		me.getParentItems();		
		return me;
	}	
});