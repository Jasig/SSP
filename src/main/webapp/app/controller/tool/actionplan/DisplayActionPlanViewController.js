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
Ext.define('Ssp.controller.tool.actionplan.DisplayActionPlanViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        apiProperties: 'apiProperties',
        appEventsController: 'appEventsController',
        authenticatedPerson: 'authenticatedPerson',
        formUtils: 'formRendererUtils',
        goalsStore: 'goalsStore',
        strengthsStore: 'strengthsStore',
        personLite: 'personLite',
        store: 'tasksStore'
    },
    
    config: {
        personTaskUrl: '',
        personTaskGroupUrl: '',
        personPrintTaskUrl: ''
    },
    
    control: {
        goalsPanel: '#goalsPanel',
        tasksPanel: '#tasksPanel'
    },
    
    init: function(){
        var me = this;
        var personId;
        var child;
        var successFunc = function(response, view){
            var r, records;
            var groupedTasks = [];
            r = Ext.decode(response.responseText);
            
            // hide the loader
            me.getView().setLoading(false);
            
            if (r != null) {
                Ext.Object.each(r, function(key, value){
                    var taskGroup = key;
                    var tasks = value;
                    Ext.Array.each(tasks, function(task, index){
                        task.group = taskGroup;
                        groupedTasks.push(task);
                    }, this);
                }, this);
                
                me.store.sort([{
                    property: 'completedDate',
                    direction: 'ASC'
                }, {
                    property: 'dueDate',
                    direction: 'DESC'
                }]);
                
            }
            me.store.loadData(groupedTasks);
        };
        
        
        
        // clear any existing tasks
        me.store.removeAll();
        
        
        // display loader
        me.getView().setLoading(true);
        
        personId = me.personLite.get('id');
        me.personTaskUrl = me.apiProperties.getItemUrl('personTask');
        me.personTaskUrl = me.personTaskUrl.replace('{id}', personId);
        me.personTaskGroupUrl = me.apiProperties.getItemUrl('personTaskGroup');
        me.personTaskGroupUrl = me.personTaskGroupUrl.replace('{id}', personId);
        me.personPrintTaskUrl = me.apiProperties.getItemUrl('personPrintTask');
        me.personPrintTaskUrl = me.personPrintTaskUrl.replace('{id}', personId);
        
        me.apiProperties.makeRequest({
            url: me.apiProperties.createUrl(me.personTaskGroupUrl),
            method: 'GET',
            successFunc: successFunc
        });
        
        
        me.appEventsController.assignEvent({
            eventName: 'emailActionPlan',
            callBackFunc: me.onEmailActionPlan,
            scope: me
        });
        me.appEventsController.assignEvent({
            eventName: 'printActionPlan',
            callBackFunc: me.onPrintActionPlan,
            scope: me
        });
        return me.callParent(arguments);
    },
    
    destroy: function(){
        var me = this;
        me.appEventsController.removeEvent({
            eventName: 'emailActionPlan',
            callBackFunc: me.onEmailActionPlan,
            scope: me
        });
        me.appEventsController.removeEvent({
            eventName: 'printActionPlan',
            callBackFunc: me.onPrintActionPlan,
            scope: me
        });

        if ( me.emailActionPlanPopUp ) {
            me.emailActionPlanPopUp.destroy();
        }

        return me.callParent(arguments);
    },
    
    onEmailActionPlan: function(button){
        var me = this;
        var msg = me.getTaskGoalCountNotificationMessage();
        if (msg.length > 0) {
            Ext.Msg.confirm({
                title: ' Would you like to continue emailing?',
                msg: msg,
                buttons: Ext.Msg.YESNO,
                fn: me.emailTasksConfirm,
                scope: me
            });
        }
        else {
            me.goToEmailActionPlan();
        }
    },
    
    emailTasksConfirm: function(btnId){
        var me = this;
        if (btnId == "yes") {
            me.goToEmailActionPlan();
        }
    },
    
    goToEmailActionPlan: function(){
        var me = this;
        if (me.emailActionPlanPopUp == null || me.emailActionPlanPopUp.isDestroyed) 
            me.emailActionPlanPopUp = Ext.create('Ssp.view.tools.actionplan.EmailActionPlan', {
                hidden: true
            });
        me.emailActionPlanPopUp.show();
        
    },
    
    
    
    onPrintActionPlan: function(button){
        var me = this;
        var msg = me.getTaskGoalCountNotificationMessage();
        if (msg.length > 0) {
            Ext.Msg.confirm({
                title: ' Would you like to continue printing??',
                msg: msg,
                buttons: Ext.Msg.YESNO,
                fn: me.printTasksConfirm,
                scope: me
            });
        }
        else {
            me.printTasks();
        }
    },
    
    printTasksConfirm: function(btnId){
        var me = this;
        if (btnId == "yes") {
            me.printTasks();
        }
    },
    
    printTasks: function(){
        var me = this;
        var url, jsonData;
        var allGoals = [];
        me.goalsStore.each(function(rec){
        
            allGoals.push(rec.get('id'));
        });
        
        
        var allStrenghts = [];
        me.strengthsStore.each(function(rec){
        
            allStrenghts.push(rec.get('id'));
        });
        
        var allTasks = [];
        
        if (me.getSelectedTasks().length == 0) {
            me.store.each(function(rec){
                allTasks.push(rec.get('id'));
            });
        }
        else {
            var allTasks = me.getSelectedTasks();
        }
        var jsonData = {
            "taskIds": allTasks,
            "goalIds": allGoals,
            "strengthIds": allStrenghts
        };
        
        url = me.apiProperties.createUrl(me.personPrintTaskUrl);
        
        me.apiProperties.getReporter().postReport({
            url: url,
            params: jsonData
        });
    },
    
    getTaskGoalCountNotificationMessage: function(){
        var me = this;
        // if no tasks or goals have been added to the student's record
        // then display a notification to first add tasks and goals before
        // printing
        var notificationMsg = "";
        if (me.store.getCount() < 1) {
            notificationMsg += "This student has " + me.store.getCount() + " assigned tasks.";
        }
        
        if (me.goalsStore.getCount() < 1) {
            notificationMsg += "This student has " + me.goalsStore.getCount() + " assigned goals.";
        }
        
        return notificationMsg;
    },
    
    getSelectedTasks: function(){
        var me = this;
        var allTasksGrid = me.getTasksPanel();
        var taskIds = [];
        var allTaskIds = me.getSelectedIdsArray(allTasksGrid.getView().getSelectionModel().getSelection());
        var taskIds = Ext.Array.merge(allTaskIds);
        return taskIds;
    },
    
    
    
    getSelectedIdsArray: function(arr){
        var selectedIds = [];
        Ext.each(arr, function(item, index){
            selectedIds.push(item.get('id'));
        });
        
        return selectedIds;
    }
});
