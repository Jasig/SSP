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
Ext.define('Ssp.controller.SearchFormViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
        appEventsController: 'appEventsController',
        authenticatedPerson: 'authenticatedPerson',
        caseloadFilterCriteria: 'caseloadFilterCriteria',
        caseloadStore: 'caseloadStore',
        caseloadService: 'caseloadService',
        columnRendererUtils: 'columnRendererUtils',
        formUtils: 'formRendererUtils',
        person: 'currentPerson',
        personLite: 'personLite',
        personService: 'personService',
        personProgramStatusService: 'personProgramStatusService',
        programStatusesStore: 'programStatusesStore',
        programStatusService: 'programStatusService',
        searchCriteria: 'searchCriteria',
        searchService: 'searchService',
    	programStatusesStore: 'programStatusesStore'
    },
    control: {
    	view: {
			afterlayout: {
				fn: 'onAfterLayout',
				single: true
			}
    	},
    'searchStudentButton': {
		click: 'onSearchClick'
		},
   	'resetStudentSearchButton': {
    		click: 'onResetClick'
    	},
	'hoursEarnedMin':{
			specialkey: "specialKeyPressed",
			blur: "hoursEarnedMinChanged",
	 },
	'hoursEarnedMax':{
			specialkey: "specialKeyPressed",
			blur: 'hoursEarnedMaxChanged',
	 },
	 'gpaMin':{
			specialkey: "specialKeyPressed",
			blur: 'gpaMinChanged',
	 },
	 'gpaMax':{
			specialkey: "specialKeyPressed",
			blur: 'gpaMaxChanged',
	 }
    },
    
	init: function() {
		var me=this;    	

	   	// ensure the selected person is not loaded twice
		// once on load and once on selection
	   	me.personLite.set('id','');
		return me.callParent(arguments);
    },

	onAfterLayout: function(comp, eobj){
		var me=this;
		me.appEventsController.assignEvent({eventName: 'onStudentSearchRequested', callBackFunc: me.onSearchClick, scope: me});
	   	// load program statuses
		me.getProgramStatuses();	
	},

    destroy: function() {
    	var me=this;
    	me.appEventsController.removeEvent({eventName: 'onStudentSearchRequested', callBackFunc: me.onSearchClick, scope: me});
	   	return me.callParent( arguments );
    },
    searchSuccess: function( r, scope){
    	var me=scope;
    	me.getView().setLoading( false );
		me.getView().collapse();
		me.appEventsController.getApplication().fireEvent("onPersonSearchSuccess");
    },    
	search: function(){
		var me=this;		
		
		me.searchService.search2( 
				me.getView().query('textfield[name=studentId]')[0].value,
				me.getView().query('combobox[name=programStatus]')[0].value,
				me.getView().query('combobox[name=coachId]')[0].value,				
				me.getView().query('combobox[name=declaredMajor]')[0].value,
				me.getView().query('numberfield[name=hoursEarnedMin]')[0].value,
				me.getView().query('numberfield[name=hoursEarnedMax]')[0].value,			
				me.getView().query('numberfield[name=gpaMin]')[0].value,
				me.getView().query('numberfield[name=gpaMax]')[0].value,				
				me.getView().query('combobox[name=currentlyRegistered]')[0].value,
				me.getView().query('combobox[name=financialAidStatus]')[0].value,
				me.getView().query('combobox[name=mapStatus]')[0].value,
				me.getView().query('combobox[name=planStatus]')[0].value,
				me.getView().query('checkbox[name=myCaseload]')[0].value,
				me.getView().query('checkbox[name=myPlans]')[0].value,
				{
				success: me.searchSuccess,
				failure: me.searchFailure,
				scope: me
		});	
		
	}, 
	clear: function() {
		var me=this;
		me.getView().getForm().reset();
	},
	getProgramStatuses: function(){
		var me=this;
		me.programStatusService.getAll({
			success:me.getProgramStatusesSuccess, 
			failure:me.getProgramStatusesFailure, 
			scope: me
	    });
	},

	getProgramStatusesSuccess: function( r, scope){
    	var me=scope;
    	var activeProgramStatusId = "";
    	var programStatus;
    	if ( me.programStatusesStore.getCount() > 0)
    	{
    	}
    },	
    getProgramStatusesFailure: function( r, scope){
    	var me=scope;
    },
    onResetClick: function(button){
		var me=this;
		me.clear();	
	},  
	onSearchClick: function(button){
		var me=this;
		if(!me.getView().getForm().isDirty())
		{
	     	Ext.Msg.alert('SSP Error', 'Please enter some filter values.'); 
	     	return;
		}
		var message = "";
		var valuesInvalid = false;
		if(me.getGpaMin().getValue() > me.getGpaMax().getValue()){
			valuesInvalid = true;
			message += "GPA Min is greater than GPA Maximum. ";
		}
		
		if(me.getGpaMin().getValue() == null && me.getGpaMax().getValue() != null){
			me.getGpaMin().setValue(0);
		}
		
		if(me.getHoursEarnedMin().getValue() > me.getHoursEarnedMax().getValue()){
				valuesInvalid = true;
				message += "Hours Earned Min is greater than Hours Earned Maximum. ";
		}

		if(me.getGpaMin().getValue() == null && me.getGpaMax().getValue() != null){
				me.getGpaMin().setValue(0);
		}
		
		if(valuesInvalid == true){
	     	Ext.Msg.alert('SSP Error', message + "Search will return no values."); 
	     	return;
		}
		
		me.search();	
	},  
	
	specialKeyPressed: function(field, el){
		var me=this;
		if(el.getKey() == Ext.EventObject.ENTER)
			me.onSearchClick();
	},
	
	hoursEarnedMinChanged: function(){
		var me=this;
		
		if(me.getHoursEarnedMax().getValue() == null){
				me.getHoursEarnedMax().setValue(me.getHoursEarnedMin().getValue());
				return;
		}
		if(me.getHoursEarnedMin().getValue() > me.getHoursEarnedMax().getValue())
			me.getHoursEarnedMax().setValue(me.getHoursEarnedMin().getValue())
	},
	
	hoursEarnedMaxChanged: function(){
		var me=this;
		if(me.getHoursEarnedMax().getValue() == null){
			me.getHoursEarnedMin().setValue(null);
			return;
		}
		if(!me.getHoursEarnedMin().getValue()){
			me.getHoursEarnedMin().setValue(0);
			return;
		}
		if(me.getHoursEarnedMin().getValue() > me.getHoursEarnedMax().getValue())
			me.getHoursEarnedMin().setValue(me.getHoursEarnedMax().getValue())
	},
	
	gpaMinChanged: function(){
		var me=this;
		if(me.getGpaMin().getValue() === null){
			me.getGpaMax().setValue(null);
			return;
		}	
		if(me.getGpaMax().getValue() == null){
			me.getGpaMax().setValue(me.getGpaMin().getValue());
			return;
		}
		if(me.getGpaMin().getValue() > me.getGpaMax().getValue())
			me.getGpaMax().setValue(me.getGpaMin().getValue())
	},
	
	gpaMaxChanged: function(){
		var me=this;

		if(!me.getGpaMin().getValue()){
			me.getGpaMin().setValue(0);
			return;
		}
			
		if(me.getGpaMin().getValue() > me.getGpaMax().getValue())
			me.getGpaMin().setValue(me.getGpaMax().getValue())
	},

    searchFailure: function( r, scope){
    	var me=scope;
		me.appEventsController.getApplication().fireEvent("onPersonSearchFailure");
    },	
});