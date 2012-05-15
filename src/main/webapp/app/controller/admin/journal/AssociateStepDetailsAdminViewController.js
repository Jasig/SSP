Ext.define('Ssp.controller.admin.journal.AssociateStepDetailsAdminViewController', {
	extend: 'Ssp.controller.admin.AdminItemAssociationViewController',
    config: {
        associatedItemUrl: 'reference/journalStepDetail/',
        associatedItemType: 'journalStepDetail',
        parentItemUrl: 'reference/journalStep/',
        parentItemType: 'journalStep',
        parentIdAttribute: 'journalStepId',
        associatedItemIdAttribute: 'journalStepDetailId'
    },
	constructor: function(){
		this.callParent(arguments);
		
		this.clear();
		this.getParentItems();
		
		return this;
	}
});