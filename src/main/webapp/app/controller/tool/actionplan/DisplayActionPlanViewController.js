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
Ext.define('Ssp.controller.tool.actionplan.DisplayActionPlanViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
    	authenticatedPerson: 'authenticatedPerson',
    	formUtils: 'formRendererUtils',
    	goalsStore: 'goalsStore',
    	person: 'currentPerson',
    	store: 'tasksStore'
    },
    
    config: {
    	filteredTaskStatus: null,
    	filterAuthenticated: false,
    	personTaskUrl: '',
    	personTaskGroupUrl: '',
    	personEmailTaskUrl: '',
    	personPrintTaskUrl: ''
    },
    
    control: {
    	'taskStatusTabs': {
    		tabchange: 'onTaskStatusTabChange'
    	},
		
		'emailTasksButton': {
			click: 'onEmailTasksClick'
		},

		'printTasksButton': {
			click: 'onPrintTasksClick'
		},
		
		'filterTasksBySelfCheck': {
			change: 'onFilterTasksBySelfChange'
		},
    	
		addTaskButton: {
			selector: '#addTaskButton',
			listeners: {
				click: 'onAddTaskClick'
			}
		},
		
		goalsPanel: '#goalsPanel',
		activeTasksGrid: '#activeTasksGrid',
		completeTasksGrid: '#completeTasksGrid',
		allTasksGrid: '#allTasksGrid'
	},
	
	init: function() {
		var me = this;
		var personId;
		var child;
		var successFunc = function(response,view){
	    	var r, records;
	    	var groupedTasks=[];
	    	r = Ext.decode(response.responseText);
	    	
	    	// hide the loader
	    	me.getView().setLoading( false );
	    	
	    	if (r != null)
	    	{
	    		Ext.Object.each(r,function(key,value){
		    		var taskGroup = key;
		    		var tasks = value;
		    		Ext.Array.each(tasks,function(task,index){
		    			task.group=taskGroup;
		    			groupedTasks.push(task);
		    		},this);
		    	},this);		    		

	    		me.store.loadData(groupedTasks);
	    		me.filteredTaskStatus="ACTIVE";
	    		me.filterTasks();
	    	}
		};
	
    	me.getAddTaskButton().setDisabled( !me.authenticatedPerson.hasPermission('ROLE_PERSON_TASK_WRITE') );
    	
		// clear any existing tasks
		me.store.removeAll();

		// display loader
		me.getView().setLoading( true );
		
		personId = me.person.get('id');
		me.personTaskUrl = me.apiProperties.getItemUrl('personTask');
		me.personTaskUrl = me.personTaskUrl.replace('{id}',personId);
		me.personTaskGroupUrl = me.apiProperties.getItemUrl('personTaskGroup');
		me.personTaskGroupUrl = me.personTaskGroupUrl.replace('{id}',personId);
		me.personEmailTaskUrl = me.apiProperties.getItemUrl('personEmailTask');
		me.personEmailTaskUrl = me.personEmailTaskUrl.replace('{id}',personId);	
		me.personPrintTaskUrl = me.apiProperties.getItemUrl('personPrintTask');
		me.personPrintTaskUrl = me.personPrintTaskUrl.replace('{id}',personId);
		
		me.apiProperties.makeRequest({
			url: me.apiProperties.createUrl(me.personTaskGroupUrl),
			method: 'GET',
			successFunc: successFunc 
		});
			
    	me.appEventsController.assignEvent({eventName: 'filterTasks', callBackFunc: this.onFilterTasks, scope: this});		
		
		return me.callParent(arguments);
    },
    
    destroy: function(){
    	this.appEventsController.removeEvent({eventName: 'filterTasks', callBackFunc: this.onFilterTasks, scope: this});    	
    },
    
    onFilterTasks: function(){
    	this.filterTasks();
    },
    
    onFilterTasksBySelfChange: function(comp, newComp, oldComp, eOpts){
		this.filterAuthenticated=!this.filterAuthenticated;
		this.filterTasks();
	},
    
    onTaskStatusTabChange: function(panel, newComp, oldComp, eOpts) {
		this.filteredTaskStatus = newComp.action.toUpperCase();
		this.filterTasks();
    },
    
    filterTasks: function(){
    	var me=this;
    	var filtersArr = [];
		var filterStatusFunc = null;
		var authenticatedId = me.authenticatedPerson.get('id');
		var filterAuthenticatedFunc = new Ext.util.Filter({
		    filterFn: function(item) {
				return (item.get('createdBy').id == authenticatedId); 
			},
			scope: me
		});

		switch (me.filteredTaskStatus)
		{
			case 'ACTIVE':
				filterStatusFunc = function(item) { return (item.get('completed') == false); };
				break;
			
			case 'COMPLETE':
				filterStatusFunc = function(item) { return (item.get('completed') == true); };
				break;
		}
		
		if (filterStatusFunc != null)
			filtersArr.push(filterStatusFunc);
		
		if (me.filterAuthenticated == true)
			filtersArr.push(filterAuthenticatedFunc);
		
		// reset filter
		me.store.clearFilter();
		
		// apply new filters
		me.store.filter(filtersArr);
    },

    onEmailTasksClick: function(button) {
    	var me=this;
    	var msg = me.getTaskGoalCountNotificationMessage();
		if (msg.length > 0)
		{
	           Ext.Msg.confirm({
	     		     title:' Would you like to continue emailing?',
	     		     msg: msg,
	     		     buttons: Ext.Msg.YESNO,
	     		     fn: me.emailTasksConfirm,
	     		     scope: me
	     		   });
		}else{
			me.displayEmailAddressWindow();
		}
    },
    
    emailTasksConfirm: function( btnId ){
     	var me=this;
     	if (btnId=="yes")
     	{
         	me.displayEmailAddressWindow();    		
     	}
     }, 
    
    displayEmailAddressWindow: function(){
    	var me=this;
    	Ext.create('Ext.window.Window', {
		    title: 'To whom would you like to send this Action Plan',
		    height: 200,
		    width: 400,
		    layout: 'fit',
		    modal: true,
		    items:[{
		        xtype:'form',
		        layout:'anchor',
		        items :[{
		            xtype: 'label',
		            text: 'Enter recipient email addresses separated by a comma'
		            
		        },{
		            xtype: 'textarea',
		            anchor: '100%',
		            name: 'recipientEmailAddresses'
		        }]
			}],
			dockedItems: [{
		        dock: 'bottom',
		        xtype: 'toolbar',
		        items: [{
		            text: 'Send',
		            xtype: 'button',
		            handler: me.emailTaskList,
		            scope: me
		        },{
		            text: 'Cancel',
		            xtype: 'button',
		            handler: function(button){
		            	button.up('window').close();
		            }
		        }]
    	    }]
		}).show();
    },
    
    emailTaskList: function( button ){
	    var me=this;
    	var valid = false;
	    var jsonData;
	    var emailTestArr;
	    var arrRecipientEmailAddresses = [];
	    var recipientEmailAddresses = button.up('panel').down('form').getValues().recipientEmailAddresses;
	    if (recipientEmailAddresses != null)
	    {
	    	// validate email addresses
		    if ( recipientEmailAddresses.indexOf(",") )
		    {
		    	emailTestArr = recipientEmailAddresses.split(',');
		    	// handle a list of email addresses
		    	Ext.each(emailTestArr,function(emailAddress,index){
		    		valid = this.validateEmailAddress( emailAddress );
		    		arrRecipientEmailAddresses.push( Ext.String.trim(emailAddress) );
		    		if (valid != true)
		    			return;
		    	}, this);
		    }else{
		    	valid = this.validateEmailAddress( recipientEmailAddresses );
		    	arrRecipientEmailAddresses.push( Ext.String.trim( recipientEmailAddresses ) );
		    }
		    
		    // define data to email
			jsonData = {
	    			"taskIds": me.getSelectedTasks(),
	    		    "goalIds": me.getSelectedGoals(),
	    		    "recipientIds": [],
					"recipientEmailAddresses": arrRecipientEmailAddresses
			};

		    if (valid==true)
		    {
		    	// email the task list
	    		url = this.apiProperties.createUrl( this.personEmailTaskUrl );
		    	this.apiProperties.makeRequest({
					url: url,
					method: 'POST',
					jsonData: jsonData,
					successFunc: function(){
						button.up('window').close();
						Ext.Msg.alert('Success','The task list has been sent to the listed recipient(s).');
					}
				});
		    }else{
		    	Ext.Msg.alert('Error','1 or more of the addresses you entered are invalid. Please correct the form and try again.');		    	
		    }	
	    }else{
	    	Ext.Msg.alert('Error','Unable to determine an email address please enter a valid email address.');
	    }
    },
    
    validateEmailAddress: function( value ){
    	var emailExpression = filter = new RegExp('^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$');
    	return emailExpression.test( value );
    },
    
    onPrintTasksClick: function(button) {
    	var me=this;
    	var msg = me.getTaskGoalCountNotificationMessage();
		if (msg.length > 0)
		{
           Ext.Msg.confirm({
     		     title:' Would you like to continue printing??',
     		     msg: msg,
     		     buttons: Ext.Msg.YESNO,
     		     fn: me.printTasksConfirm,
     		     scope: me
     		   });
		}else{
			me.printTasks();
		}
    },
 
    printTasksConfirm: function( btnId ){
     	var me=this;
     	if (btnId=="yes")
     	{
         	me.printTasks();    		
     	}
     },    
    
    printTasks: function() {
    	var me=this;
    	var url, jsonData;	
		var jsonData = {
				"taskIds": me.getSelectedTasks(),
		        "goalIds": me.getSelectedGoals()
		        };
 
    	url = me.apiProperties.createUrl( me.personPrintTaskUrl );

		me.apiProperties.getReporter().postReport({
			url: url,
			params: jsonData
		});
    },
    
    getTaskGoalCountNotificationMessage: function(){
		var me=this;
    	// if no tasks or goals have been added to the student's record
		// then display a notification to first add tasks and goals before
		// printing
		var notificationMsg = "";
		if ( me.store.getCount() < 1 )
		{
			notificationMsg += "This student has " + me.store.getCount() + " assigned tasks.";
		}
		
		if ( me.goalsStore.getCount() < 1 )
		{
			notificationMsg += "This student has " + me.goalsStore.getCount() + " assigned goals.";
		}
		
		return notificationMsg;
    },
		
    getSelectedTasks: function(){
    	var me=this;
    	var activeTasksGrid = me.getActiveTasksGrid();
		var completeTasksGrid = me.getCompleteTasksGrid();
		var allTasksGrid = me.getAllTasksGrid();
		var activeTaskIds = me.getSelectedIdsArray( activeTasksGrid.getView().getSelectionModel().getSelection() );
		var completeTaskIds = me.getSelectedIdsArray( completeTasksGrid.getView().getSelectionModel().getSelection() );
		var allTaskIds = me.getSelectedIdsArray( allTasksGrid.getView().getSelectionModel().getSelection() );
		var taskIds = Ext.Array.merge( activeTaskIds, completeTaskIds, allTaskIds);
		return taskIds;    	
    },
    
    getSelectedGoals: function(){
		var me=this;
    	var goalsPanel = me.getGoalsPanel();
		return me.getSelectedIdsArray( goalsPanel.getView().getSelectionModel().getSelection() );
    },
    
    getSelectedIdsArray: function(arr){
		var selectedIds = [];
		Ext.each(arr, function(item, index) {
			selectedIds.push( item.get('id') );
		});
		
		return selectedIds;
    },
    
    onAddTaskClick: function(button) {
    	this.appEventsController.getApplication().fireEvent('addTask');
    },  
});