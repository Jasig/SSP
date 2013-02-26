/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
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
					        name: 'name',
					        allowBlank: false
					    },{
				    	xtype: 'textarea',
				        fieldLabel: 'Description',
				        name: 'description',
				        maxLength: 1000,
				        allowBlank:false
				    },{
				    	xtype: 'textarea',
				        fieldLabel: 'Link (HTML Supported)',
				        name: 'link',
				        maxLength: 1000,
				        allowBlank:true
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
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{xtype: 'button', 
				        	     itemId: 'addButton', 
				        	     text:'Save', 
				        	     action: 'add' },
								 , '-',
				        	     {
				            	   xtype: 'button',
				            	   itemId: 'closeButton',
				            	   text: 'Cancel',
				            	   action: 'close'}]
				    }]
				});
		
		return me.callParent(arguments);
	}
});
