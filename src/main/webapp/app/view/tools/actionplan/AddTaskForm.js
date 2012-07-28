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
	initComponent: function() {
		var me=this;
		Ext.apply(me, 
				{
			        autoScroll: true,
			        border: 0,
			        padding: 0,
		            fieldDefaults: {
		                msgTarget: 'side',
		                labelAlign: 'right',
		                labelWidth: 150
		            },
				    items: [{
				            xtype: 'fieldset',
				            title: 'Add Task',
				            defaultType: 'textfield',
					        border: 0,
					        padding: 0,
				            defaults: {
				                anchor: '95%'
				            },
				       items: [{
					    	xtype: 'displayfield',
					        fieldLabel: 'Task Name',
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
				        store: me.store,
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
				    	altFormats: 'm/d/Y|m-d-Y',
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
		
		return me.callParent(arguments);
	}
});
