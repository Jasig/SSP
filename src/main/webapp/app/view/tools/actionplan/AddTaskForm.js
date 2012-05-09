Ext.define('Ssp.view.tools.actionplan.AddTaskForm', {
	extend: 'Ext.form.Panel',
	alias: 'widget.addtaskform',
	id: 'AddTaskForm',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        confidentialityLevelsStore: 'confidentialityLevelsStore'
    },
	width: '100%',
    height: '100%',
	autoScroll: true,
    defaults: {
        anchor: '100%'
    },
    fieldDefaults: {
        msgTarget: 'side',
        labelAlign: 'right',
        labelWidth: 150
    },    
	initComponent: function() {
		Ext.apply(this, 
				{
				    items: [{
				            xtype: 'fieldset',
				            title: 'Add Task',
				            defaultType: 'textfield',
				            defaults: {
				                anchor: '100%'
				            },
				       items: [{
					    	xtype: 'displayfield',
					        fieldLabel: 'Name',
					        name: 'name'
					    },{
				    	xtype: 'textarea',
				        fieldLabel: 'Description',
				        name: 'description',
				        maxLength: 1000,
				        allowBlank:false
				    },{
				        xtype: 'combobox',
				        name: 'confidentialityLevelId',
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
				    	xtype: 'datefield',
				    	fieldLabel: 'Target Date',
				        name: 'dueDate',
				        allowBlank:false    	
				    }]
				    }]
				});
		
		return this.callParent(arguments);
	}
});
