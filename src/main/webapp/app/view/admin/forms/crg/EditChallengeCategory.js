Ext.define('Ssp.view.admin.forms.crg.EditChallengeCategory',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editchallengecategory',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.crg.EditChallengeCategoryViewController',
	height: '100%',
	width: '100%',
	anchor: '100%',
	title: 'Edit Category',
	initComponent: function() {
        Ext.applyIf(this, {
            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: 'Category Name',
                    anchor: '100%',
                    name: 'name'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Description',
                    anchor: '100%',
                    name: 'description'
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