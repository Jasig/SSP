Ext.define('Ssp.controller.admin.crg.AssociateChallengeCategoriesAdminViewController', {
	extend: 'Ssp.controller.admin.AdminItemAssociationViewController',
    config: {
        associatedItemType: 'challenge',
        parentItemType: 'category',
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