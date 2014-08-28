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
Ext.define('Ssp.controller.tool.actionplan.TasksGridViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        apiProperties: 'apiProperties',
        appEventsController: 'appEventsController',
        authenticatedPerson: 'authenticatedPerson',
        formUtils: 'formRendererUtils',
        model: 'currentTask',
        personLite: 'personLite',
        store: 'addTasksStore',
        confidentialityLevelsStore: 'confidentialityLevelsAllUnpagedStore',
		configStore: 'configStore'
    },
	
	config: {
		taskDueDate: '',
		confLevel: []
    },
    
    control: {
    
        view: {
            beforeedit: 'onBeforeEdit',
            edit: 'editRecord'
        },
		
		'gridView': {
    	   drop: 'onDrop'
        },
        
        addCustomButton: {
            selector: '#addCustomButton',
            listeners: {
                click: 'onAddCustomButtonClick'
            }
        },
        
        removeTaskButton: {
            selector: '#removeTaskButton',
            listeners: {
                click: 'onRemoveTaskButtonClick'
            }
        },
        
        removeAllTaskButton: {
            selector: '#removeAllTaskButton',
            listeners: {
                click: 'onRemoveAllTaskButtonClick'
            }
        }
    },
    
    init: function(){
        var me = this;
        me.store.removeAll();
		
		taskDueDate = new Date();
		
		var confLevelId = me.confidentialityLevelsStore.findRecord('name', 'EVERYONE' , 0, false, false, true).get('id');
        
		confLevel = {
            id: confLevelId,
            name: 'EVERYONE'
        };
		
		me.configStore.each(function(rec){
             if (rec.get('name') == 'student_intake_default_due_date_offset') {
				 var s = rec.get('value');
                 var date_offset  = parseInt(s);
				 if (date_offset) {
				 	taskDueDate = Ext.Date.add(new Date(), Ext.Date.DAY, date_offset);
				 }
				 return false;
             }
		
             
         });

		
		 
        return me.callParent(arguments);
    },
    
    onRemoveTaskButtonClick: function(button){
        var me = this;
        var grid, record;
        grid = button.up('grid');
        record = grid.getView().getSelectionModel().getSelection()[0];
        if (record) {
            this.model.data = record.data;
            me.store.remove(record);
        }
        else {
            Ext.Msg.alert('SSP Error', 'Please select a task to delete.');
        }
        
        
    },
    
    onRemoveAllTaskButtonClick: function(button){
        var me = this;
        me.deleteAllConfirmation();
        
        
    },
    
    deleteAllConfirmation: function(){
        Ext.Msg.confirm({
            title: 'Delete Tasks?',
            msg: 'You are about to delete all tasks in the task list. Would you like to continue?',
            buttons: Ext.Msg.YESNO,
            fn: this.deleteAllTasks,
            scope: this
        });
        
    },
    
    deleteAllTasks: function(btnId){
        var store = this.store;
        var id = this.model.get('id');
        if (btnId == "yes") {
            store.removeAll();
        }
    },
    
    deleteConfirmation: function(){
    
        Ext.Msg.confirm({
            title: 'Delete Task?',
            msg: 'You are about to delete the task in the task list: "' + this.model.get('name') + '". Would you like to continue?',
            buttons: Ext.Msg.YESNO,
            fn: this.deleteTask,
            scope: this
        });
        
    },
    
    deleteTask: function(btnId){
        var store = this.store;
        
        var id = this.model.get('id');
        if (btnId == "yes") {
            store.remove(store.getById(id));
        }
    },
    
    
    onAddCustomButtonClick: function(button){
        var me = this;
        
        me.customTaskFormPopUp = Ext.create('Ssp.view.tools.actionplan.CustomActionPlan');
        me.customTaskFormPopUp.show();
        
    },
    
    onBeforeEdit: function(editor, e, eOpts){
        var me = this;
      
        var items = editor.editor.items.items;
        me.formUtils.applyAssociativeStoreFilter(me.confidentialityLevelsStore, me.model.get('confidentialityLevel').id);
        Ext.Array.each(items, function(item, index, count){
            if (item.xtype == 'combo') {
            	me.confidentialityLevelsStore.on({
						load: {
							fn: function(store, records, state, operation, opts){
								if (e.record.get('confidentialityLevel').id == null || e.record.get('confidentialityLevel').id == '') {
									var confidentialityLevelId = me.confidentialityLevelsStore.findRecord('name', 'EVERYONE', 0, false, false, true).get('id');
									item.setValue(confidentialityLevelId);
								}
								else {
									item.setValue(e.record.get('confidentialityLevel').id);
								}
							},
							scope: this,
							single: true
						}
					});
                me.confidentialityLevelsStore.load();
                
            }
        });
        
        return true;
    },
    
    editRecord: function(editor, e, eOpts){
        var me = this;
        var record = e.record;
        var todayDateJSON = me.formUtils.toJSONStringifiableDate(new Date());
        var origDueDateJSON = me.formUtils.toJSONStringifiableDate(record.data.dueDate);
        if (origDueDateJSON['formattedStr'] < todayDateJSON['formattedStr']) {
            Ext.Msg.alert('Error', 'The Target Date must be the current or a future date.');
            return;
        }
        
        var confLevelId = e.newValues.confidentialityLevel;
        var confLevelName = me.confidentialityLevelsStore.findRecord('id', confLevelId, 0, false, false, true).get('name');
        var confLevel = {
            id: confLevelId,
            name: confLevelName
        };
        record.data.confidentialityLevel = confLevel;
        
        var store = editor.grid.getStore();
        
        Ext.each(editor.editor.items.items, function(item){
            if (item.store != undefined && item.store != null) {
                item.store.clearFilter(true);
            }
            
        });
        
        if (record.dirty) {
            record.commit();
            
            editor.grid.getSelectionModel().select(record);
            var h = editor.grid.getView().getSelectedNodes()[0];
            Ext.get(h).highlight(Ssp.util.Constants.SSP_EDITED_ROW_HIGHLIGHT_COLOR, Ssp.util.Constants.SSP_EDITED_ROW_HIGHLIGHT_OPTIONS);
            
        }
    },
    
   onDrop:function(node, data, overModel, dropPosition, options)
	{   
    	var me=this;
		
		var posId = me.getView().getView().indexOf(data.records[0]);
		var badRecord = me.store.getAt(posId);
		var task = new Ssp.model.tool.actionplan.Task();
            task.set('name', badRecord.data.challengeReferralName);
            task.set('description', badRecord.data.challengeReferralDescription);
            task.set('link',badRecord.data.challengeReferralLink);
            task.set('challengeReferralId', badRecord.data.challengeReferralId);
            task.set('challengeId', badRecord.data.challengeId);
			task.set('dueDate', taskDueDate);
			task.set('confidentialityLevel',confLevel);
		if(badRecord)
        {
        	me.store.data.replace(this.store.data.getKey(badRecord),task);
			me.getView().getStore().loadRecords(me.store.getRange());
        }
        else
        {
        	me.store.add(task);
			
        }
		
	},
	
    
    getTStore: function(){
        var me = this;
        return me.store;
    }
});
