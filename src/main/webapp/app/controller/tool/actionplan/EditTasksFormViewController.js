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
Ext.define('Ssp.controller.tool.actionplan.EditTasksFormViewController', {
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
    	model: 'currentTask',
    	url: '',
		containerToLoadInto: 'tools',
    	formToDisplay: 'actionplan'
    },    
    control: {  
    	'editActionPlanButton': {
			click: 'onEditActionPlanButtonClick'
		},
		
		
		NameField: '#name',
    	DescriptionField: '#description',
		actionPlanDueDate: '#actionPlanDueDate',
		confidentialityLevel: '#confidentialityLevel'
	},
 
	init: function(){
		var me=this;
		
		if(this.model.get('type') !== 'CUS')
		{
			
			this.getNameField().disable();
			this.getDescriptionField().disable();
			
		}
		
		me.url = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personTask') );
		me.url = me.url.replace('{id}',me.personLite.get('id'));
		
		me.confidentialityLevelsStore.clearFilter(true);
		//me.confidentialityLevelsStore.load();
		
		
		me.formUtils.applyAssociativeStoreFilter(me.confidentialityLevelsStore, me.model.get('confidentialityLevel').id);
		
		me.initForm();   	
    	
		return me.callParent(arguments);
	},
	
    destroy: function() {
    	var me=this;  	

    	// clear confidentiality level filter
    	me.confidentialityLevelsStore.clearFilter();
    	
        return me.callParent( arguments );
    },	
	
	initForm: function(){
		var form = Ext.ComponentQuery.query('.edittaskform > form')[0];
        
        var editForm = form.getForm();
		editForm.reset();
		
		
		editForm.loadRecord( this.model );
		
		Ext.ComponentQuery.query('#confidentialityLevel')[0].setValue( this.model.get('confidentialityLevel').id );
	},
	
    
    onEditActionPlanButtonClick: function(button){
    	var me=this;
    	var successFunc, failureFunc;
		var form = Ext.ComponentQuery.query('.edittaskform > form')[0].getForm();
    	var model = this.model;
    	var jsonData;
    	var id = model.get('id');
    	if ( form.isValid() )
    	{
    		form.updateRecord();
    		
			me.getView().setLoading( true );
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
			model.data.confidentialityLevel = form.findField('confidentialityLevelId').lastSelection[0].data;
			
			model.data.dueDate = me.formUtils.toJSONStringifiableDate( model.data.dueDate );
			if (id == "") {
				if (origDueDateJSON['formattedStr'] < todayDateJSON['formattedStr']) {
					Ext.Msg.alert('Error', 'The Target Date must be the current or a future date.');
					return;
				}
			}
			successFunc = function(response ,view){
					model.data.dueDate = origDueDate;
					Ext.ComponentQuery.query('.edittaskform')[0].close();
					me.loadDisplay();
					me.getView().setLoading( false );
			};

			failureFunc = function(response) {
				model.data.dueDate = origDueDate;
				me.apiProperties.handleError(response);
				me.getView().setLoading( false );
			}

    		
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
    		
    	}else{
    		Ext.Msg.alert('Error', 'Please correct the errors in your form before continuing.');
    	}
    },
    
    loadDisplay: function(){
    	var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});    	
    }
});