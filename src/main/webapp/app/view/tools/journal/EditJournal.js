Ext.define('Ssp.view.tools.journal.EditJournal',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editjournal',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.journal.EditJournalViewController',
	initComponent: function() {
		Ext.applyIf(this, {
        	title: 'Edit Journal',
            items: [
                { xtype: 'journaltracktree', flex:1 },
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