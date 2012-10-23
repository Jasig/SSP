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
Ext.define('Ssp.controller.tool.actionplan.DisplayActionPlanGoalsViewController', {
    extend: 'Deft.mvc.ViewController',	
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
    	authenticatedPerson: 'authenticatedPerson',
    	formUtils: 'formRendererUtils',
    	model: 'currentGoal',
    	person: 'currentPerson',
    	preferences: 'preferences',
    	store: 'goalsStore'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'editgoalform',
    	url: ''
    },
    control: {
		view: {
			viewready: 'onViewReady'
		},
    	
    	addGoalButton:{
    		selector: '#addGoalButton',
    		listeners: {
    			click: 'onAddGoalClick'
    		}
    	}
    },
    
    constructor: function() {
    	// reconfigure the url for the current person
    	this.url = this.apiProperties.createUrl(this.apiProperties.getItemUrl('personGoal'));
    	this.url = this.url.replace('{id}',this.person.get('id'));
    	
    	// apply the person url to the store proxy
    	Ext.apply(this.store.getProxy(), { url: this.url });

    	// load records
    	this.store.load();

		return this.callParent(arguments);
    },

    onViewReady: function(comp, obj){
    	var me=this;
    	me.getAddGoalButton().setDisabled( !me.authenticatedPerson.hasPermission('ROLE_PERSON_GOAL_WRITE') );
    	
    	me.appEventsController.assignEvent({eventName: 'editGoal', callBackFunc: this.editGoal, scope: this});
    	me.appEventsController.assignEvent({eventName: 'deleteGoal', callBackFunc: this.deleteConfirmation, scope: this});
    
    	// display the goals pane if a goal was added to the student's record
    	if ( me.preferences.ACTION_PLAN_ACTIVE_VIEW == 1 )
    	{
    		// reset to the tasks view
    		me.preferences.ACTION_PLAN_ACTIVE_VIEW=0;
    		me.getView().expand();
    	}
    },
    
    destroy: function() {
    	this.appEventsController.removeEvent({eventName: 'editGoal', callBackFunc: this.editGoal, scope: this});
    	this.appEventsController.removeEvent({eventName: 'deleteGoal', callBackFunc: this.deleteConfirmation, scope: this});

        return this.callParent( arguments );
    },
    
    onAddGoalClick: function( button ){
		var goal = new Ssp.model.PersonGoal();
		this.model.data = goal.data;
		this.loadEditor();
    },
 
    editGoal: function(){
  	   this.loadEditor();
    },

    deleteConfirmation: function() {
       if (this.model.get('id') != "") 
       {
    	   Ext.Msg.confirm({
    		     title:'Delete Goal?',
    		     msg: 'You are about to delete the goal: "'+ this.model.get('name') + '". Would you like to continue?',
    		     buttons: Ext.Msg.YESNO,
    		     fn: this.deleteGoal,
    		     scope: this
    		});
       }else{
    	   Ext.Msg.alert('SSP Error', 'Unable to delete goal.'); 
       }
    },
    
    deleteGoal: function( btnId ){
    	var store = this.store;
    	var id = this.model.get('id');
    	if (btnId=="yes")
    	{
        	this.apiProperties.makeRequest({
     		   url: this.url+"/"+id,
     		   method: 'DELETE',
     		   successFunc: function(response,responseText){
     			  store.remove( store.getById( id ) );
     		   }
     	    });    		
    	}
    },
    
    loadEditor: function(){
    	var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});    	
    }
});