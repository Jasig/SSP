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
Ext.define('Ssp.controller.tool.actionplan.EditGoalFormViewController', {
    extend: 'Deft.mvc.ViewController',	
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	authenticatedPerson: 'authenticatedPerson',
    	confidentialityLevelsStore: 'confidentialityLevelsAllUnpagedStore',
    	formUtils: 'formRendererUtils',
    	model: 'currentGoal',
    	personLite: 'personLite',
    	preferences: 'preferences'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'actionplan',
    	url: ''
    },    
    control: {
    	combo: '#confidentialityLevel',
    	
    	'saveButton': {
			click: 'onSaveClick'
		},
		
		'cancelButton': {
			click: 'onCancelClick'
		}
	},
  
	init: function() {
		var me=this;
		
		// apply confidentiality level filter
		//me.authenticatedPerson.applyConfidentialityLevelsFilter( me.confidentialityLevelsStore );
		
		me.getView().getForm().loadRecord( me.model );
		
		me.formUtils.applyAssociativeStoreFilter(me.confidentialityLevelsStore,me.model.get('confidentialityLevel').id);
		
		me.getCombo().setValue( this.model.get('confidentialityLevel').id );
		
		
		me.authenticatedPerson.applyConfidentialityLevelsFilter(me.confidentialityLevelsStore);
		
		return me.callParent(arguments);
    },	
	
	constructor: function(){
		this.url = this.apiProperties.getItemUrl('personGoal');
		this.url = this.url.replace('{id}',this.personLite.get('id'));
    	this.url = this.apiProperties.createUrl( this.url );
	
		return this.callParent(arguments);
	},
    
    onSaveClick: function(button){
    	var me=this;
    	var model = this.model;
    	var form, url, goalId, successFunc;
    	form = this.getView().getForm();
    	id = model.get('id');
    	if ( form.isValid() )
    	{
    		var values = form.getValues();
    		model.set('name',values.name);
    		model.set('description',values.description);
    		model.set('confidentialityLevel',{id: values.confidentialityLevelId});
    		
    		successFunc = function(response ,view){
    			me.preferences.ACTION_PLAN_ACTIVE_VIEW=1;
    			me.loadDisplay();
			};
			
    		if (id == "")
    		{
    			// add
    			this.apiProperties.makeRequest({
	    			url: this.url,
	    			method: 'POST',
	    			jsonData: model.data,
	    			successFunc: successFunc
	    		});
    		}else{
    			// edit
	    		this.apiProperties.makeRequest({
	    			url: this.url+"/"+id,
	    			method: 'PUT',
	    			jsonData: model.data,
	    			successFunc: successFunc
	    		});    			
    		}
    	}else{
    		Ext.Msg.alert('Error', 'Please correct the errors in your form before continuing.');
    	}
    },
    
    onCancelClick: function(button){
    	this.loadDisplay();
    },
    
    loadDisplay: function(){
    	var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
    }
});