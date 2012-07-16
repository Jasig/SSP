Ext.define('Ssp.view.admin.forms.journal.EditStep',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editjournalstep',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.journal.EditStepViewController',
	title: 'Edit Step',
	initComponent: function() {
        Ext.applyIf(this, {
            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: 'Step Name',
                    anchor: '100%',
                    name: 'name'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Description',
                    anchor: '100%',
                    name: 'description'
                },
                {
                    xtype: 'checkbox',
                    fieldLabel: 'Used for Transition',
                    name: 'usedForTransition'
                }],
            
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