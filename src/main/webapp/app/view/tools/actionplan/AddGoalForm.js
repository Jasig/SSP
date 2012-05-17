Ext.define('Ssp.view.tools.actionplan.AddGoalForm', {
	extend: 'Ext.form.Panel',
	alias: 'widget.addgoalform',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.AddGoalFormViewController',
    inject: {
        store: 'confidentialityLevelsStore'
    },
	initComponent: function() {
        Ext.applyIf(this, {
        	title: 'Add Goal',
            fieldDefaults: {
                msgTarget: 'side',
                labelAlign: 'right',
                labelWidth: 150
            },            
        	items: [
                {
                    xtype: 'textfield',
                    fieldLabel: 'Name',
                    anchor: '100%',
                    name: 'name'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Description',
                    anchor: '100%',
                    name: 'description'
                },{
			        xtype: 'combobox',
			        name: 'confidentialityLevelId',
			        fieldLabel: 'Confidentiality Level',
			        emptyText: 'Select One',
			        store: this.store,
			        valueField: 'id',
			        displayField: 'acronym',
			        mode: 'local',
			        typeAhead: true,
			        queryMode: 'local',
			        allowBlank: false,
			        forceSelection: true
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