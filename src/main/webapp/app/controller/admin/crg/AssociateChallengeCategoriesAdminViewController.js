Ext.define('Ssp.controller.admin.crg.AssociateChallengeCategoriesAdminViewController', {
	extend: 'Ssp.controller.admin.AdminItemAssociationViewController',
    config: {
        associatedItemUrl: 'reference/challenge/',
        associatedItemType: 'challenge',
        parentItemUrl: 'reference/challengeCategory/',
        parentItemType: 'challengeCategory',
        parentIdAttribute: 'categoryId',
        associatedItemIdAttribute: 'challengeId'
    },
	constructor: function(){
		this.callParent(arguments);
		
		this.clear();
		this.getParentItems();
		
		return this;
	}
});