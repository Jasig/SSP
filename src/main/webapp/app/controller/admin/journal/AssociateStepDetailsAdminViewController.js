Ext.define('Ssp.controller.admin.journal.AssociateStepDetailsAdminViewController', {
	extend: 'Ssp.controller.admin.AdminItemAssociationViewController',
    config: {
        associatedItemType: 'journalStepDetail',
        parentItemType: 'journalStep',
        parentIdAttribute: 'journalStepId',
        associatedItemIdAttribute: 'journalStepDetailId'
    },
	constructor: function(){
		var me=this;
		me.callParent(arguments);
		me.clear();
		me.getParentItems();	
		return me;
	}
});