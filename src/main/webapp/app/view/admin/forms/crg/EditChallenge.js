Ext.define('Ssp.view.admin.forms.crg.EditChallenge',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editchallenge',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.crg.EditChallengeViewController',
    inject: {
        confidentialityLevelsStore: 'confidentialityLevelsStore'
    },
	title: 'Edit Challenge',
	initComponent: function() {
        Ext.applyIf(this, {
            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: 'Challenge Name',
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
                    xtype: 'textfield',
                    fieldLabel: 'Tags',
                    anchor: '100%',
                    name: 'tags'
                },{
                    xtype: 'combobox',
                    name: 'defaultConfidentialityLevelId',
                    fieldLabel: 'Confidentiality Level',
                    emptyText: 'Select One',
                    store: this.confidentialityLevelsStore,
                    valueField: 'id',
                    displayField: 'acronym',
                    mode: 'local',
                    typeAhead: true,
                    queryMode: 'local',
                    allowBlank: false,
                    forceSelection: true
            	},{
                    xtype: 'textareafield',
                    fieldLabel: 'Self Help Guide Description',
                    anchor: '100%',
                    name: 'selfHelpGuideDescription'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Self Help Guide Question',
                    anchor: '100%',
                    name: 'selfHelpGuideQuestion'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Show in Student Intake',
                    anchor: '100%',
                    name: 'showInStudentIntake'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Show in Self Help Search',
                    anchor: '100%',
                    name: 'showInSelfHelpSearch'
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