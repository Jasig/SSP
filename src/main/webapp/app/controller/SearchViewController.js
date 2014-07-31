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
Ext.define('Ssp.controller.SearchViewController', {
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
        preferences: 'preferences',
        programStatusesStore: 'programStatusesStore',
        programStatusService: 'programStatusService',
        searchCriteria: 'searchCriteria',
        searchService: 'searchService',
        searchStoreOld: 'studentsSearchStore',
        searchStore: 'directoryPersonSearchStore',
		termsStore: 'termsStore',
        configStore: 'configStore',
        textStore:'sspTextStore',
		configurationOptionsUnpagedStore: 'configurationOptionsUnpagedStore'
        
    },
    
    control: {
    	view: {
    		itemclick: 'onSelectionChange',
			viewready: 'onViewReady'
    	},    	
 
    	caseloadStatusCombo: {
    		selector: '#caseloadStatusCombo',
    		listeners: {
    			select: 'onCaseloadStatusComboSelect'
    		} 
    	},

    	'retrieveCaseloadButton': {
    		click: 'onRetrieveCaseloadClick'
    	},    	
    	
    	searchGridPager: '#searchGridPager',
    	searchBar: '#searchBar',
    	caseloadBar: '#caseloadBar',

    	addPersonButton: {
    		selector: '#addPersonButton',
    		listeners: {
    			click: 'onAddPersonClick'
    		}
    	},
    	
    	editPersonButton: {
    		selector: '#editPersonButton',
    		listeners: {
    			click: 'onEditPersonClick'
    		}
    	},
    	
		'setTransitionStatusButton': {
			click: 'onSetProgramStatusClick'
		},
		
		'setNonParticipatingStatusButton': {
			click: 'onSetProgramStatusClick'
		},
		
		'setNoShowStatusButton': {
			click: 'onSetProgramStatusClick'
		},
		
		'setActiveStatusButton': {
			click: 'onSetProgramStatusClick'
		}
    },
    
	init: function() {
		var me=this;
        var tabSelection = me.getView().tabContext;
	   	// ensure the selected person is not loaded twice
		// once on load and once on selection
	   //	me.personLite.set('id','');
		
	   	me.SEARCH_GRID_VIEW_TYPE_IS_SEARCH = 0;
	   	me.SEARCH_GRID_VIEW_TYPE_IS_CASELOAD = 1;
		if(me.termsStore.getTotalCount() == 0){
				me.termsStore.addListener("load", me.onTermsStoreLoad, me);
				me.termsStore.load();
		}
		if(me.textStore.getTotalCount() == 0){
			me.textStore.addListener("load", me.onTextStoreLoad, me, {single: true});
			me.textStore.load();
		}else{
			me.onTextStoreLoad();
		}
		if(me.configStore.getTotalCount() == 0){
			me.configStore.addListener("load", me.onTextStoreLoad, me, {single: true});
			me.configStore.load();
		}
		me.personLite.on('idchanged', me.personChanged, me);

        if ( tabSelection === 'myCaseload' ) {
            me.displayCaseloadBar();
        } else {
            me.displaySearchBar();
        }

		return me.callParent(arguments);
    },
    
    personChanged: function(){
 	   var record = this.getView().getStore().findRecord("id", this.personLite.get("id"));
 	   if(record != null){
 			if(!this.getView().getSelectionModel().isSelected(record ))
 				this.getView().getSelectionModel().select(record, false, true);
 		}else{
 			this.getView().getSelectionModel().deselectAll(true);
 		}
    },
    
    onTextStoreLoad:function(){
    	var me = this;
    	var birthDateField = Ext.ComponentQuery.query('searchForm')[1].query('datefield[itemId=birthDate]')[0];

    	if ( birthDateField ) {
    	    birthDateField.setFieldLabel( me.textStore.getValueByCode('ssp.label.dob') + ':' );
    	}

    	me.onCollapseStudentRecord(true);
    },
    
	onSelectionChange: function( view, record, item, index, eventObj ) {
		var me=this;
        var skipCallBack = this.appEventsController.getApplication().fireEvent('personNav', record, me);

		if( skipCallBack ) {
			var person = new Ssp.model.Person();
			// clear the person record
			me.person.data = person.data;

			if ( record ) {
				if( record.get("id") ) {
					if( me.personLite.get('id') != record.get("id") ) {
						me.updatePerson(record);
						me.appEventsController.getApplication().fireEvent('loadPerson');
					}
				} else if(me.authenticatedPerson.hasAccess('ADD_STUDENT_BUTTON')){
					me.instantCaseloadAssignment(record);
				}
			}
		}
	},

    updatePerson: function( persons ) {
        var me=this;
        
        var person = me.getRecordFromArray(persons);
		if ( person.data.id != null ) {
			me.personLite.set('id', person.data.id);
		} else {
			me.personLite.set('id', person.data.personId);
		}
		me.personLite.set('firstName', person.data.firstName);
		me.personLite.set('middleName', person.data.middleName);
		me.personLite.set('lastName', person.data.lastName);
		me.personLite.set('displayFullName', person.data.firstName + ' ' + person.data.lastName);
	},
	
	getRecordFromArray: function(records){
		var record;
		if(Array.isArray(records)){
			if(records.length == 1)
				record = record[0];
			else
				throw "more than one record found, expect only one" 
		}
		else
			record = records;
		return record
	},
	
	onViewReady: function(comp, eobj){
		var me=this;
		// 'do' events are added to avoid potential multiple listener issues
		me.appEventsController.assignEvent({eventName: 'doAddPerson', callBackFunc: me.onAddPerson, scope: me});
		me.appEventsController.assignEvent({eventName: 'doPersonButtonEdit', callBackFunc: me.onEditPerson, scope: me});
		me.appEventsController.assignEvent({eventName: 'doRetrieveCaseload', callBackFunc: me.getCaseload, scope: me});	
		me.appEventsController.assignEvent({eventName: 'doPersonStatusChange', callBackFunc: me.setProgramStatus, scope: me});	

		
        me.appEventsController.assignEvent({eventName: 'toolsNav', callBackFunc: me.onToolsNav, scope: me});
		me.appEventsController.assignEvent({eventName: 'collapseStudentRecord', callBackFunc: me.onCollapseStudentRecord, scope: me});
	   	me.appEventsController.assignEvent({eventName: 'expandStudentRecord', callBackFunc: me.onExpandStudentRecord, scope: me});
	   	me.appEventsController.assignEvent({eventName: 'setNonParticipatingProgramStatusComplete', callBackFunc: me.onSetNonParticipatingProgramStatusComplete, scope: me});
		me.appEventsController.assignEvent({eventName: 'onPersonSearchSuccess', callBackFunc: me.searchSuccess, scope: me});
		me.appEventsController.assignEvent({eventName: 'onPersonSearchFailure', callBackFunc: me.searchFailure, scope: me});
		me.appEventsController.assignEvent({eventName: 'updateEarlyAlertCounts', callBackFunc: me.onUpdateEarlyAlertCounts, scope: me});
		me.appEventsController.assignEvent({eventName: 'updateSearchStoreRecord', callBackFunc: me.onUpdateSearchStoreRecord, scope: me});
	   	me.initSearchGrid();

	   	// load program statuses
		me.getProgramStatuses();	
		me.configurationOptionsUnpagedStore.load();
		me.harmonizePersonLite();
	},
	
	harmonizePersonLite:function()
	{
		var me = this;
		if(me.personLite.get('id')){
			var foundIndex = me.getView().getStore().findExact("id", me.personLite.get('id'));
				if ( foundIndex >= 0)
			{
				me.getView().getSelectionModel().select(foundIndex);
			}
		}
	},
	
	onUpdateEarlyAlertCounts: function(params){
		var me = this;
		var person = me.caseloadStore.findRecord("id",params.personId, 0, false, false, true);
		if(person != null){
			person.set('numberOfEarlyAlerts', params.openEarlyAlerts);
			person.set('numberEarlyAlertResponsesRequired', params.lateEarlyAlertResponses);
		}
		
		person = me.searchStore.findRecord("id",params.personId, 0, false, false, true);
		if(person != null){
			person.set('numberOfEarlyAlerts', params.openEarlyAlerts);
			person.set('numberEarlyAlertResponsesRequired', params.lateEarlyAlertResponses);
		}
	},

    destroy: function() {
    	var me=this;
    	
		me.termsStore.removeListener("load", me.onTermsStoreLoad, me);
		me.textStore.removeListener("load", me.onTextStoreLoad, me, {single: true});
		me.personLite.un('idchanged', me.personChanged, me);
		me.appEventsController.removeEvent({eventName: 'doPersonButtonEdit', callBackFunc: me.onEditPerson, scope: me});
		me.appEventsController.removeEvent({eventName: 'doAddPerson', callBackFunc: me.onAddPerson, scope: me});
		me.appEventsController.removeEvent({eventName: 'doRetrieveCaseload', callBackFunc: me.getCaseload, scope: me}); 
		me.appEventsController.removeEvent({eventName: 'doPersonStatusChange', callBackFunc: me.setProgramStatus, scope: me});	
        me.appEventsController.removeEvent({eventName: 'toolsNav', callBackFunc: me.onToolsNav, scope: me});
    	me.appEventsController.removeEvent({eventName: 'collapseStudentRecord', callBackFunc: me.onCollapseStudentRecord, scope: me});
	   	me.appEventsController.removeEvent({eventName: 'expandStudentRecord', callBackFunc: me.onExpandStudentRecord, scope: me});
	   	me.appEventsController.removeEvent({eventName: 'onPersonSearchSuccess', callBackFunc: me.searchSuccess, scope: me});
		me.appEventsController.removeEvent({eventName: 'onPersonSearchFailure', callBackFunc: me.searchFailure, scope: me});
		me.appEventsController.removeEvent({eventName: 'updateEarlyAlertCounts', callBackFunc: me.onUpdateEarlyAlertCounts, scope: me});
		me.appEventsController.removeEvent({eventName: 'updateSearchStoreRecord', callBackFunc: me.onUpdateSearchStoreRecord, scope: me});
	   	me.appEventsController.removeEvent({eventName: 'setNonParticipatingProgramStatusComplete', callBackFunc: me.onSetNonParticipatingProgramStatusComplete, scope: me});

		if(me.instantCaseload != null && !me.instantCaseload.isDestroyed)
			me.instantCaseload.close();
		
		return me.callParent( arguments );
    },
    
    initSearchGrid: function(){
	   	var me=this;
	   	
	   	if (!me.getIsCaseload() )
		{
			me.search();
			me.displaySearchBar();
		}else{
			if ( me.authenticatedPerson.hasAccess('CASELOAD_FILTERS') )
			{
				me.preferences.set('SEARCH_GRID_VIEW_TYPE', me.SEARCH_GRID_VIEW_TYPE_IS_CASELOAD);
				// default caseload to Active students if no program status is defined
				if ( me.caseloadFilterCriteria.get('programStatusId') == "")
				{
					me.caseloadFilterCriteria.set('programStatusId', Ssp.util.Constants.ACTIVE_PROGRAM_STATUS_ID );
				}
				me.getCaseload(true);
				me.displayCaseloadBar();
			}else{
				me.search();
				me.displaySearchBar();
			}
		}
    },
    
    selectFirstItem: function(){
    	var me=this;
    	if ( me.getView().getStore().getCount() > 0)
    	{
        	me.getView().getSelectionModel().select(0);
    	}else{
    		// if no record is available, then cast event
    		// to reset the profile tool fields
    		me.personLite.set('id', "");
    		me.appEventsController.getApplication().fireEvent('loadPerson');
    	}
    	me.appEventsController.getApplication().fireEvent('updateStudentRecord');
    	me.refreshPagingToolBar();    	
    },
    onCollapseStudentRecord: function(applyColumns) {
		var me = this;
		me.preferences.set('SEARCH_VIEW_SIZE', "COLLAPSED");
		if(applyColumns === true)
		{
			me.applyColumns();
		}
        me.showColumn(false,'birthDate');
		if(me.getIsCaseload()){
			me.showColumn(false,'coach');
			me.showColumn(false,'currentProgramStatusName');
			me.showColumn(true,'studentType')
		}else{
			me.showColumn(true,'coach');
			me.showColumn(true,'currentProgramStatusName');
			me.showColumn(false,'studentType');
		}
	},
	
	onExpandStudentRecord: function(applyColumns) {
		var me = this;
		me.preferences.set('SEARCH_VIEW_SIZE', "EXPANDED");
		if(applyColumns === true)
		{
			me.applyColumns();
		}
	    me.showColumn(true,'birthDate');
		me.showColumn(true,'studentType')
		if(me.getIsCaseload()){
			me.showColumn(false,'coach');
			me.showColumn(false,'currentProgramStatusName');
		}else{
			me.showColumn(true,'coach');	
			me.showColumn(true,'currentProgramStatusName');
		}
	},  

	setGridView: function( view ){
		var me=this;
		if(me.getIsExpanded()){
			me.onExpandStudentRecord(true);
		}else{
			me.onCollapseStudentRecord(true);
		}
	},
	
	getIsExpanded:function(){
		var me= this;
		if(me.preferences.get('SEARCH_VIEW_SIZE') == "EXPANDED")
			return true;
		return false;
	},
	
	getIsCaseload: function(){
		var me= this;
		if(me.preferences.get('SEARCH_GRID_VIEW_TYPE') == me.SEARCH_GRID_VIEW_TYPE_IS_CASELOAD)
			return true;
		return false;
	},
	
	onToolsNav: function() {
		var searchView = Ext.ComponentQuery.query('searchtab')[0];
		searchView.collapse();
	},

	displaySearchBar: function() {
	    var me = this;
		me.preferences.set('SEARCH_GRID_VIEW_TYPE', me.SEARCH_GRID_VIEW_TYPE_IS_SEARCH);
		me.getCaseloadBar().hide();
		me.getSearchBar().show();

		if ( me.authenticatedPerson.hasAccess('CASELOAD_SEARCH') ) {
		    Ext.ComponentQuery.query('searchForm')[1].show(); //user has two, second is search
        } else {
            Ext.ComponentQuery.query('searchForm')[0].show(); //user only has one (no caseload)
        }

        me.setGridView();
    },

	displayCaseloadBar: function(){
		var me=this;
	    me.preferences.set('SEARCH_GRID_VIEW_TYPE', me.SEARCH_GRID_VIEW_TYPE_IS_CASELOAD);
		me.getCaseloadBar().show();
		me.getSearchBar().hide();
		me.setGridView();
	},
	
	applyColumns: function(){
		var me=this;
		var grid = me.getView();
		var store;
		var sortableColumns = true;
		var studentIdAlias = me.configStore.getConfigByName('studentIdAlias');
		var coachIdAlias = me.configStore.getConfigByName('coachFieldLabel');

		if ( me.getIsCaseload() )
		{
			store = me.caseloadStore;
			
		}else{
			store = me.searchStore;
			store.pageSize = store.data.length;
		}
		
		columns = [
	              { sortable: sortableColumns, header: me.textStore.getValueByCode('ssp.label.first-name'), dataIndex: 'firstName', flex: 1 },		        
	              { sortable: sortableColumns, header: me.textStore.getValueByCode('ssp.label.middle-name'), dataIndex: 'middleName', flex: me.getIsExpanded() ? .4:.2},
	              { sortable: sortableColumns, header: me.textStore.getValueByCode('ssp.label.last-name'), dataIndex: 'lastName', flex: 1},
				  { sortable: sortableColumns, header: me.textStore.getValueByCode('ssp.label.dob'), dataIndex: 'birthDate', renderer: Ext.util.Format.dateRenderer('m/d/Y'), flex: .5},
	              { sortable: sortableColumns, header: coachIdAlias, dataIndex: 'coach', renderer: me.columnRendererUtils.renderCoachName, flex: 1},
	              { sortable: sortableColumns, header: 'Type', dataIndex: 'studentType', renderer: me.columnRendererUtils.renderStudentType, flex: me.getIsExpanded() ? .5:.2},
				  { sortable: sortableColumns, header: studentIdAlias, dataIndex: 'schoolId', flex: me.getIsExpanded() ? .5:1},
	              { sortable: sortableColumns, header: 'Status', dataIndex: 'currentProgramStatusName', flex: .2},   	              
	              { sortable: sortableColumns, header: 'Alerts', dataIndex: 'numberOfEarlyAlerts', flex: .2}
	              ];
		
		if(me.getSearchGridPager)
		{
			me.getSearchGridPager().bindStore(store);
		}
		
		me.refreshPagingToolBar();
		grid.getView().getRowClass = function(row, index)
	    {
			var cls = "";
			var today = Ext.Date.clearTime( new Date() );
			var tomorrow = Ext.Date.clearTime( new Date() );
			tomorrow.setDate( today.getDate() + 1 );
			// set apppointment date color first. early alert will over-ride appointment color.
			if (row.get('currentAppointmentStartTime') != null)
			{
				if ( me.formUtils.dateWithin(today, tomorrow, row.get('currentAppointmentStartTime') ) )
				{
					cls = 'caseload-appointment-indicator'
				}
			}
			
			// early alert color will over-ride the appointment date
			if ( row.get('numberOfEarlyAlerts') != null)
			{
				if (row.get('numberOfEarlyAlerts') > 0)
				{
					cls = 'caseload-early-alert-indicator'
				}				
			}
			
			// early alert color will over-ride the alert
			if ( row.get('numberEarlyAlertResponsesRequired') != null)
			{
				if (row.get('numberEarlyAlertResponsesRequired') > 0)
				{
					cls = 'caseload-early-alert-response-required-indicator'
				}				
			}
			
			if (row.get('id') == "")
			{
				cls = "directory-person-is-external-person-only";
			}

			return cls;
	    };  		
		
		me.formUtils.reconfigureGridPanel(grid, store, columns);
	},

	 showColumn: function( show, dataIndex ) {
		var me=this;
	    var column = me.getColumn(dataIndex);

		if ( column ) {
		   column.setVisible(show);
	    }
	},
	
	getColumn:function(dataIndex){
		var me=this;
		var columns = me.getView().columns;
		for(var i = 0; i < columns.length; i++){
			if(columns[i].dataIndex == dataIndex)
				return columns[i];
		}
		return null;
	},

    onAddPersonClick: function( button ){
    	var me=this;
		
        var skipCallBack = this.appEventsController.getApplication().fireEvent('personButtonAdd',me);  
        if(skipCallBack)
        {
        	me.onAddPerson();
        }
	},
	
	onTermsStoreLoad:function(){
		var me = this;
		me.termsStore.removeListener( "onTermsStoreLoad", me.onTermsStoreLoad, me );
		
	},
	
	onEditPersonClick: function( button ){
    	var me=this;
        var skipCallBack = this.appEventsController.getApplication().fireEvent('personButtonEdit',me);  
        if(skipCallBack)
        {
        	me.onEditPerson();
        }
	},

	onAddPerson: function(){
		var me=this;
		var model = new Ssp.model.Person();
    	me.person.data = model.data;
    	me.personLite.set('id','');
		me.loadCaseloadAssignment();
	},
	
	onEditPerson: function(){
		var me=this;
		var records = this.getView().getSelectionModel().getSelection();
		if (records.length>0)
		{
			me.loadCaseloadAssignment();
		}else{
			Ext.Msg.alert('Error','Please select a student to edit.');
		}
	},

    refreshPagingToolBar: function(){
		if(this.getSearchGridPager() != null)
    		this.getSearchGridPager().onLoad();
    },
    
    loadCaseloadAssignment: function(){
    	var comp = this.formUtils.loadDisplay('mainview', 'caseloadassignment', true, {flex:1});    	
    },
	
	loadStudentToolsView: function(){
    	this.appEventsController.getApplication().fireEvent('displayStudentRecordView');
    },
  
    onSetProgramStatusClick: function( button ){
    	var me=this;
    	var me=this;
        var skipCallBack = this.appEventsController.getApplication().fireEvent('personStatusChange',me,button);  
        if(skipCallBack)
        {
        	var action = button.action;
        	switch ( action )
        	{
        	case 'active':
        		me.setProgramStatus( action );
        		break;
        		
        	case 'no-show':
        		me.setProgramStatus( action );
        		break;
        		
        	case 'transition':
        		/* 
        		 * Temp fix for SSP-434
        		 * 
        		 * Temporarily removing Transition Action from this button.
        		 * TODO: Ensure that this button takes the user to the Journal Tool and initiates a
        		 * Journal Entry.
        		 * // me.appEventsController.getApplication().fireEvent('transitionStudent');
        		 */
        		break;
        		
        	case 'non-participating':
        		me.setProgramStatus(action);
        		break;
        	}
        }
    },
    
    setProgramStatus: function( action ){
	   	var me=this;
	   	var personId = me.personLite.get('id');
	   	var programStatusId = "";
	   	if (personId != "")
	   	{
			var person = me.caseloadStore.findRecord("personId", personId, 0, false, false, true);
			if(!person)
			  	person = me.searchStore.findRecord("id", personId, 0, false, false, true);
			
			if(action == 'non-participating'){
				Ext.create('Ssp.view.ProgramStatusChangeReasonWindow', {
    			    height: 150,
    			    width: 500
    			}).show();
				person.set("currentProgramStatusName", "Non-participating");
				return;
			}
	   		if (action=='active')
	   		{
	   			programStatusId = Ssp.util.Constants.ACTIVE_PROGRAM_STATUS_ID;
				person.set("currentProgramStatusName", "Active");
	   		}
	   		
	   		if (action=='no-show')
	   		{
	   			programStatusId = Ssp.util.Constants.NO_SHOW_PROGRAM_STATUS_ID;
				person.set("currentProgramStatusName", "No-Show");
	   		}
			
	   		personProgramStatus = new Ssp.model.PersonProgramStatus();
	   		personProgramStatus.set('programStatusId', programStatusId );
	   		personProgramStatus.set('effectiveDate', Ext.Date.now() );
	   		me.personProgramStatusService.save( 
	   				personId, 
	   				personProgramStatus.data, 
	   				{
	   			success: me.saveProgramStatusSuccess,
	               failure: me.saveProgramStatusFailure,
	               scope: me 
	           });    		
	   	}else{
	   		var msg = "";
	   		if (action=='no-show')
	   			msg = 'No-Show';
	   		if (action=='non-participating')
	   			msg = 'Non-Participating';
	   		if (action=='active')
	   			msg = 'Active';
	   		
	   		Ext.Msg.alert('SSP Error','Unable to determine student to set to ' + msg + ' status.');
	   	}
    },
    
    saveProgramStatusSuccess: function( r, scope){
    	var me=scope;
    	me.getView().setLoading( false );
		//me.getCaseload();
    	me.initSearchGrid();
    },

    saveProgramStatusFailure: function( r, scope){
    	var me=scope;
    	me.getView().setLoading( false );
    },
    
    onSetNonParticipatingProgramStatusComplete: function(){
    	this.initSearchGrid();
    },
    
    /*********** SEARCH BAR ***************/
    
	onSearchClick: function(button){
		var me=this;
		me.setSearchCriteria();
		if ( me.searchCriteria.get('searchTerm') != "")
		{
			me.search();
		}else{
			me.clearSearch();
		}	
	},
	
	setSearchCriteria: function(){
		var me=this;
		me.searchCriteria.set('searchTerm', searchTerm);
		me.searchCriteria.set('outsideCaseload', outsideCaseload);
	},
	
	clearSearch: function(){
		var me=this;
		me.searchStore.removeAll();
		me.searchStore.totalCount = 0;
		me.getSearchGridPager().onLoad();
	},
	
	onSearchKeyPress: function(comp,e){
		var me=this;
        if(e.getKey()==e.ENTER){  
    		me.setSearchCriteria();
        	if ( me.searchCriteria.get('searchTerm') != "")
    		{
    			me.search();
    		}else{
    			me.clearSearch();
    		}   
        }  
    },
	
	search: function(){
		var me=this;
		me.preferences.set('SEARCH_GRID_VIEW_TYPE',0);
		me.setGridView();
		if ( me.searchCriteria.get('searchTerm') != "")
		{
			me.getView().setLoading( true );
			me.searchService.search( 
					Ext.String.trim(me.searchCriteria.get('searchTerm')),
					me.searchCriteria.get('outsideCaseload'),
					{
					success: me.searchSuccess,
					failure: me.searchFailure,
					scope: me
			});				
		}
	},

    searchSuccess: function(){
    	var me=this;
    	me.personChanged();
    	me.getView().setLoading( false );
		me.getSearchGridPager().onLoad();
    },

    searchFailure: function(){
    	var me=this;
		me.personLite.set('id', "");
    	me.appEventsController.getApplication().fireEvent('loadPerson');
    	me.getView().setLoading( false );
    },
    
    
    /**************** CASELOAD FILTERS *********************/
    
	onRetrieveCaseloadClick: function( button ){
		var me=this;
        var skipCallBack = this.appEventsController.getApplication().fireEvent('retrieveCaseload',me);  

        if(skipCallBack)
        {
        	me.getCaseload();
        }

	},
	
	onCaseloadStatusComboSelect: function( comp, records, eOpts ){
		var me=this;
		if ( records.length > 0)
    	{
			me.caseloadFilterCriteria.set('programStatusId', records[0].get('id') );
     	}
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
    	if ( me.programStatusesStore.getCount() > 0 && me.getCaseloadStatusCombo() != null)
    	{
    		me.getCaseloadStatusCombo().setValue( me.caseloadFilterCriteria.get('programStatusId') );
    	}
    },	

    getProgramStatusesFailure: function( r, scope){
    	var me=scope;
    },     
    
	getCaseload: function(defaultToActive){
    	var me=this;
		me.preferences.set('SEARCH_GRID_VIEW_TYPE',1);
		me.setGridView();
		me.getView().setLoading( true );
		if(me.getCaseloadStatusCombo().getValue() || defaultToActive )
		{
			me.caseloadService.getCaseload( me.caseloadFilterCriteria.get( 'programStatusId' ),
					me.caseloadStore,
					{success:me.getCaseloadSuccess, 
				failure:me.getCaseloadFailure, 
				scope: me});		
		} else
		{
			me.caseloadService.getCaseload(null,
					me.caseloadStore,
					{success:me.getCaseloadSuccess, 
				failure:me.getCaseloadFailure, 
				scope: me});
		}
	},
    
    getCaseloadSuccess: function( r, scope){
    	var me=scope;
    	me.getView().setLoading( false );
    	if(me.getSearchGridPager)
    	{
    		me.getSearchGridPager().onLoad();
    	}
		me.harmonizePersonLite();
    },

    getCaseloadFailure: function( r, scope){
    	var me=scope;
    	me.getView().setLoading( false );
    },
    
    instantCaseloadAssignment: function(record){
    	var me=this;
		if(me.instantCaseload == null || me.instantCaseload.isDestroyed)
       		me.instantCaseload = Ext.create('Ssp.view.person.InstantCaseloadAssignment',{hidden:true, 
       			schoolIdValue:record.get("schoolId"), 
       			coachIdValue:record.get("coachId"),
       			studentTypeNameValue:record.get("studentTypeName")});
		me.instantCaseload.show();
    },
    
    onUpdateSearchStoreRecord:function(params){
    	var me = this;
    	var record = me.searchStore.findRecord("schoolId", params.person.get("schoolId"), 0, false, false, true);
    	record.set("id", params.person.get("id"));
    	record.set("firstName", params.person.get("firstName"));
    	record.set("middleName", params.person.get("middleName"));
    	record.set("lastName", params.person.get("lastName"));
		var coach = params.person.get("coach");
		if(coach){
			record.set("coachLastName",  coach.lastName);
			record.set("coachFirstName",  coach.firstName);
			record.set("coachId",   coach.id);
		}
    	record.set("currentProgramStatusName", "Active");
    	me.updatePerson(record);
    }
});