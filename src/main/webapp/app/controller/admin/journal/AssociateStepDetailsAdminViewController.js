Ext.define('Ssp.controller.admin.journal.AssociateStepDetailsAdminViewController', {
	extend: 'Ssp.controller.admin.AdminItemAssociationViewController',
    config: {
        associatedItemType: 'journalStepDetail',
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