/*
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
Ext.define('Ssp.controller.tool.actionplan.TasksViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        apiProperties: 'apiProperties',
        appEventsController: 'appEventsController',
        formUtils: 'formRendererUtils',
        model: 'currentTask',
        personLite: 'personLite',
        store: 'tasksStore',
        authenticatedPerson: 'authenticatedPerson',
		confidentialityLevelsStore: 'confidentialityLevelsAllUnpagedStore'
    },
    
    config: {
        appEventsController: 'appEventsController',
        containerToLoadInto: 'tools',
        formToDisplay: 'addtaskview',
        url: '',
        filterAuthenticated: false,
        filteredTaskStatus: null
    },
    
    control: {
        view: {
            viewready: 'onViewReady'
        },
        
        addTaskButton: {
            selector: '#addTaskButton',
            listeners: {
                click: 'onAddTaskClick'
            }
        },
        
        'filterTasksBySelfCheck': {
            change: 'onFilterTasksBySelfChange'
        },
        
        'filterTasksByIncompleteCheck': {
            change: 'onFilterTasksByIncompleteCheck'
        },
        
        selectAllTaskButton: {
            selector: '#selectAllTaskButton',
            listeners: {
                click: 'onSelectAllTaskButtonClick'
            }
        }
    },
    
    init: function(){
        this.url = this.apiProperties.createUrl(this.apiProperties.getItemUrl('personTask'));
        this.url = this.url.replace('{id}', this.personLite.get('id'));
        
        this.getAddTaskButton().setDisabled(!this.authenticatedPerson.hasPermission('ROLE_PERSON_TASK_WRITE'));
        
        this.filteredTaskStatus = null;
        this.filterTasks();
		
		
        
        return this.callParent(arguments);
    },
    
    onViewReady: function(comp, obj){
        this.appEventsController.assignEvent({
            eventName: 'addTask',
            callBackFunc: this.onAddTask,
            scope: this
        });
        this.appEventsController.assignEvent({
            eventName: 'editTask',
            callBackFunc: this.onEditTask,
            scope: this
        });
        this.appEventsController.assignEvent({
            eventName: 'closeTask',
            callBackFunc: this.onCloseTask,
            scope: this
        });
        this.appEventsController.assignEvent({
            eventName: 'deleteTask',
            callBackFunc: this.deleteConfirmation,
            scope: this
        });
    },
    
    destroy: function(){
        this.appEventsController.removeEvent({
            eventName: 'addTask',
            callBackFunc: this.onAddTask,
            scope: this
        });
        this.appEventsController.removeEvent({
            eventName: 'editTask',
            callBackFunc: this.onEditTask,
            scope: this
        });
        this.appEventsController.removeEvent({
            eventName: 'closeTask',
            callBackFunc: this.onCloseTask,
            scope: this
        });
        this.appEventsController.removeEvent({
            eventName: 'deleteTask',
            callBackFunc: this.deleteConfirmation,
            scope: this
        });
		if(this.editTaskFormPopUp != null && !this.editTaskFormPopUp.isDestroyed)
			this.editTaskFormPopUp.close();
        return this.callParent(arguments);
    },
    
    onAddTask: function(){
        var task = new Ssp.model.tool.actionplan.Task();
        this.model.data = task.data;
        this.loadEditor();
    },
    
    onEditTask: function(){
		 this.editTaskFormPopUp = Ext.create('Ssp.view.tools.actionplan.EditTaskForm', {});
        this.editTaskFormPopUp.show();
    },
    
    onCloseTask: function(){
        var me = this;
        var store, id, model, groupName;
		me.getView().setLoading(true);
        model = me.model;
        id = model.get('id');
        store = me.store;
        if (id != "") {
            model.set('completed', true);
            model.set('completedDate', new Date());
            // remove group property before save, since
            // the group property was added dynamically for
            // sorting and will invalidate the model on the
            // server side.
            groupName = model.data.group;
            delete model.data.group;
            this.apiProperties.makeRequest({
                url: this.url + "/" + id,
                method: 'PUT',
                jsonData: model.data,
                successFunc: function(response, responseText){
                    var r = Ext.decode(response.responseText);
                    // ensure proper save
                    if (r.id != "") {
                        model.set("completedDate", r.completedDate);
                        model.set("completed", r.completed);
                        // reset the group for sorting purposes
                        model.set('group', groupName);
                        model.commit();
                        
                        store.sync();
						 store.sort([{
                    property: 'completedDate',
                    direction: 'ASC'
                }, {
                    property: 'dueDate',
                    direction: 'DESC'
                }]);
				me.getView().setLoading(false);
                        
                        me.filterTasks();
                    }
                },
                scope: me
            });
        }
        else {
            Ext.Msg.alert('SSP Error', 'Unable to delete. No id was specified to delete.');
			me.getView().setLoading(false);
        }
    },
    
    deleteConfirmation: function(){
        var message = 'You are about to delete the task: "' + this.model.get('name') + '". Would you like to continue?';
        var model = this.model;
        if (model.get('id') != "") {
            // test if this is a student task
            if (model.get('createdBy').id == this.personLite.get('id')) {
                message = "WARNING: You are about to delete a task created by this student. Would you like to continue?";
            }
            
            Ext.Msg.confirm({
                title: 'Delete Task?',
                msg: message,
                buttons: Ext.Msg.YESNO,
                fn: this.deleteTask,
                scope: this
            });
        }
        else {
            Ext.Msg.alert('SSP Error', 'Unable to delete task.');
        }
    },
    
    deleteTask: function(btnId){
        var store = this.store;
        var id = this.model.get('id');
        if (btnId == "yes") {
            this.apiProperties.makeRequest({
                url: this.url + "/" + id,
                method: 'DELETE',
                successFunc: function(response, responseText){
                    store.remove(store.getById(id));
                }
            });
        }
    },
    
    loadEditor: function(){
        var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
    },
    
    onAddTaskClick: function(button){
        this.appEventsController.getApplication().fireEvent('addTask');
    },
    
    onFilterTasksBySelfChange: function(comp, newComp, oldComp, eOpts){
        this.filterAuthenticated = !this.filterAuthenticated;
        this.filterTasks();
    },
    
    onFilterTasksByIncompleteCheck: function(comp, newComp, oldComp, eOpts){
    
        if (newComp == true) {
            this.filteredTaskStatus = "Incomplete";
            
        }
        else {
            this.filteredTaskStatus = null;
        }
        this.filterTasks();
    },
    
    filterTasks: function(){
        var me = this;
        var filtersArr = [];
        var filterStatusFunc = null;
        var authenticatedId = me.authenticatedPerson.get('id');
        var filterAuthenticatedFunc = new Ext.util.Filter({
            filterFn: function(item){
                return (item.get('createdBy').id == authenticatedId);
            },
            scope: me
        });
        
        switch (me.filteredTaskStatus) {
            case 'Incomplete':
                filterStatusFunc = function(item){
                    return (item.get('completed') == false);
                };
                break;
        }
        
        if (filterStatusFunc != null) 
            filtersArr.push(filterStatusFunc);
        
        if (me.filterAuthenticated == true) {
            filtersArr.push(filterAuthenticatedFunc);
        }
        
        // reset filter
        me.store.clearFilter();
        
        // apply new filters
        me.store.filter(filtersArr);
    },
  
    onSelectAllTaskButtonClick: function(button){
		 var me = this;
		if (button.up('panel').getSelectionModel().getSelection().length > 0) {
			button.up('panel').getSelectionModel().deselectAll();
			button.setText('Check All');
		}
		else {
		
		button.up('panel').getSelectionModel().selectAll();
		button.setText('Clear All');
		}
    }
    
    
    
});
