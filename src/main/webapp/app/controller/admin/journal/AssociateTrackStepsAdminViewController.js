Ext.define('Ssp.controller.admin.journal.AssociateTrackStepsAdminViewController', {
	extend: 'Ssp.controller.admin.AdminItemAssociationViewController',
    config: {
        associatedItemType: 'journalStep',
        parentItemType: 'journalTrack',
        parentIdAttribute: 'journalTrackId',
        associatedItemIdAttribute: 'journalStepId'
    },
	constructor: function(){
		this.callParent(arguments);
		
		this.clear();
		this.getParentItems();
		
		return this;
	}	
});