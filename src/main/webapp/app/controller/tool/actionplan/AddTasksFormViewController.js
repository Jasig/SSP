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
Ext.define('Ssp.controller.tool.actionplan.AddTasksFormViewController', {
    extend: 'Deft.mvc.ViewController',	
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
    	authenticatedPerson: 'authenticatedPerson',
    	confidentialityLevelsStore: 'confidentialityLevelsAllUnpagedStore',
    	formUtils: 'formRendererUtils',
    	model: 'currentTask',
    	personLite: 'personLite'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'actionplan',
    	model: 'currentTask',
    	url: ''
    },    
    control: {  
    	'addButton': {
			click: 'onAddClick'
		},
		
		'closeButton': {
			click: 'onCloseClick'
		},
		
		actionPlanDueDate: '#actionPlanDueDate',
		confidentialityLevel: '#confidentialityLevel'
	},
 
	init: function(){
		var me=this;
		
		// apply confidentiality level filter
		//me.authenticatedPerson.applyConfidentialityLevelsFilter( me.confidentialityLevelsStore );
		
		me.url = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personTask') );
		me.url = me.url.replace('{id}',me.personLite.get('id'));
		
		me.initForm();
		
    	me.appEventsController.assignEvent({eventName: 'loadTask', callBackFunc: me.initFormAfterReferal, scope: me});    	
    	
		return me.callParent(arguments);
	},
	
    destroy: function() {
    	var me=this;  	

    	// clear confidentiality level filter
    	me.confidentialityLevelsStore.clearFilter();
    	
    	me.appEventsController.removeEvent({eventName: 'loadTask', callBackFunc: me.initFormAfterReferal, scope: me});
    	
        return me.callParent( arguments );
    },	
	
	initForm: function(){
		this.getView().getForm().reset();
		this.getView().getForm().loadRecord( this.model );
		Ext.ComponentQuery.query('#confidentialityLevel')[0].setValue( this.model.get('confidentialityLevel').id );
		this.formUtils.applyAssociativeStoreFilter(this.confidentialityLevelsStore,this.model.get('confidentialityLevel').id);
		
		this.authenticatedPerson.applyConfidentialityLevelsFilter(this.confidentialityLevelsStore);
	},
	
	initFormAfterReferal: function(){
		if(this.getActionPlanDueDate().getValue())
		{
			
			this.model.set('dueDate', this.getActionPlanDueDate().getValue());
		}
		this.getView().getForm().loadRecord( this.model );
	},
    
    onAddClick: function(button){
    	var me=this;
    	var successFunc, failureFunc;
    	var form = this.getView().getForm();
    	var model = this.model;
    	var jsonData;
    	var id = model.get('id');
    	if ( form.isValid() )
    	{
    		form.updateRecord();

			// Can't use model.set('dueDate') to set our date string here b/c
			// the types don't match. Doing so will cause that field to become
			// undefined. So we just set the underlying field to our string. But
			// then we also try to be good citizens and revert back to the
			// original value after the call completes. Reverting on success
			// really shouldn't matter b/c the UI should reload. And failure
			// handling in general is a bit squishy. So there's no real
			// guarantee the form is actually still usable following an AJAX
			// failure. But this at least gives us a fighting chance. Other
			// option would be taking a deep copy of the model's "raw" fields to
			// manipulate before sending over the wire as JSON. We're not doing
			// that anywhere else in the app though.
			var origDueDate = model.data.dueDate;
			var todayDateJSON = me.formUtils.toJSONStringifiableDate( new Date() );
			var origDueDateJSON = me.formUtils.toJSONStringifiableDate( model.data.dueDate );
			
			model.data.dueDate = me.formUtils.toJSONStringifiableDate( model.data.dueDate );
			if (id == "") {
				if (origDueDateJSON['formattedStr'] < todayDateJSON['formattedStr']) {
					Ext.Msg.alert('Error', 'The Target Date must be the current or a future date.');
					return;
				}
			}
			successFunc = function(response ,view){
					model.data.dueDate = origDueDate;
		    	   Ext.Msg.confirm({
		    		     title:'Success',
		    		     msg: 'The task was saved successfully. Would you like to create another task?',
		    		     buttons: Ext.Msg.YESNO,
		    		     fn: me.createTaskConfirmResult,
		    		     scope: me
		    		});
			};

			failureFunc = function(response) {
				model.data.dueDate = origDueDate;
				me.apiProperties.handleError(response);
			}

    		if (id == "")
    		{
        		model.set('type','SSP');
        		model.set('personId', this.personLite.get('id') );
        		model.set('confidentialityLevel',{id: form.getValues().confidentialityLevelId});
    			// add the task
    			this.apiProperties.makeRequest({
	    			url: me.url,
	    			method: 'POST',
	    			jsonData: model.data,
	    			successFunc: successFunc,
	    			failureFunc: failureFunc
	    		});
    		}else{
    			
    			// This removes the group property from
    			// a TaskGroup item before it is saved
    			// as a Task. Task grouping is handled in the Tasks display.
        		if (model.data.group != null)
        			delete model.data.group;
        		        		
        		// edit the task
	    		this.apiProperties.makeRequest({
	    			url: me.url+"/"+id,
	    			method: 'PUT',
	    			jsonData: model.data,
	    			successFunc: successFunc,
	    			failureFunc: failureFunc
	    		});    			
    		}
    	}else{
    		Ext.Msg.alert('Error', 'Please correct the errors in your form before continuing.');
    	}
    },

    createTaskConfirmResult: function( btnId ){
    	if (btnId=="yes")
    	{
    		var task = new Ssp.model.tool.actionplan.Task();
    		this.model.data = task.data;
    		this.initForm();
    	}else{
    		this.loadDisplay();
    	}
    },    
    
    onCloseClick: function(button){
    	this.loadDisplay();
    },
    
    loadDisplay: function(){
    	var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});    	
    }
});