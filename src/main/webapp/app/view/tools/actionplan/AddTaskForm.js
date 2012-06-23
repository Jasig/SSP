Ext.define('Ssp.view.tools.actionplan.AddTaskForm', {
	extend: 'Ext.form.Panel',
	alias: 'widget.addtaskform',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.AddTasksFormViewController',
    inject: {
        store: 'confidentialityLevelsStore'
    },
	width: '100%',
    height: '100%',
	autoScroll: true,
	padding: 0,
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
				        itemId: 'confidentialityLevel',
				        name: 'confidentialityLevelId',
				        fieldLabel: 'Confidentiality Level',
				        emptyText: 'Select One',
				        store: this.store,
				        valueField: 'id',
				        displayField: 'name',
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
				    }],
				    
				    dockedItems: [{
				        dock: 'bottom',
				        xtype: 'toolbar',
				        items: [{xtype: 'button', 
				        	     itemId: 'addButton', 
				        	     text:'Save', 
				        	     action: 'add' },
				        	     {
				            	   xtype: 'button',
				            	   itemId: 'closeButton',
				            	   text: 'Finished',
				            	   action: 'close'}]
				    }]
				});
		
		return this.callParent(arguments);
	}
});
