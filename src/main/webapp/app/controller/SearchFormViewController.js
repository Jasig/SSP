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
    	programStatusesStore: 'programStatusesStore',
    },
    control: {
    	view: {
			viewready: 'onViewReady'
    	},  
    'searchStudentButton': {
		click: 'onSearchClick'
		},
   	'resetStudentSearchButton': {
    		click: 'onResetClick'
    	}
    },
    
    
	init: function() {
		var me=this;    	

	   	// ensure the selected person is not loaded twice
		// once on load and once on selection
	   	me.personLite.set('id','');
		
		return me.callParent(arguments);
    },

	onViewReady: function(comp, eobj){
		var me=this;

	   	// load program statuses
		me.getProgramStatuses();	
	},

    destroy: function() {
    	var me=this;
	   	return me.callParent( arguments );
    },
    searchSuccess: function( r, scope){
    	var me=scope;
    	me.getView().setLoading( false );
		me.selectFirstItem();
		me.getSearchGridPager().onLoad();
    },    
	search: function(){
		var me=this;
		console.log(me.getView());
		
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
		me.search();	
	},  	
    searchSuccess: function( r, scope){
    	var me=scope;
    	console.log('yay');
//    	me.getView().setLoading( false );
//		me.selectFirstItem();
//		me.getSearchGridPager().onLoad();
    },

    searchFailure: function( r, scope){
    	var me=scope;
    	console.log('boo');
    	//me.getView().setLoading( false );
    },	
});