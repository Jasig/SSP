Ext.define('Ssp.view.admin.forms.crg.EditReferral',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editreferral',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.crg.EditReferralViewController',
	title: 'Edit Referral',
	initComponent: function() {
        Ext.applyIf(this, {
            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: 'Referral Name',
                    anchor: '100%',
                    name: 'name'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Description',
                    anchor: '100%',
                    name: 'description'
                },{
                    xtype: 'textareafield',
                    fieldLabel: 'Public Description',
                    anchor: '100%',
                    name: 'publicDescription'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Show in Self Help Guide',
                    anchor: '100%',
                    name: 'showInSelfHelpGuide'
                }
            ],
            
            dockedItems: [{
       		               xtype: 'toolbar',
       		               items: [{
		       		                   text: 'Save',
		       		                   xtype: 'button',
		       		                   action: 'save',
		       		                   itemId: 'saveButton'
		       		               }, '-', {
		       		                   text: 'Cancel',
		       		                   xtype: 'button',
		       		                   action: 'cancel',
		       		                   itemId: 'cancelButton'
		       		               }]
       		           }]
        });

        return this.callParent(arguments);
    }	
});