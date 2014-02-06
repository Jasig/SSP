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
        searchStore: 'studentsSearchStore',
		termsStore: 'termsStore',
        sspConfig: 'sspConfig',
        textStore:'sspTextStore'
        
    },
    
    control: {
    	view: {
    		selectionchange: 'onSelectionChange',
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
//    	searchText: {
//    		selector: '#searchText',
//		    listeners:{   
//		        keypress: 'onSearchKeyPress'  
//		    } 
//    	},
    	
//    	searchCaseloadCheck: '#searchCaseloadCheck',
    	searchBar: '#searchBar',
    	caseloadBar: '#caseloadBar',

//    	'searchButton': {
//    		click: 'onSearchClick'
//    	},
    	
    	'displaySearchBarButton': {
    		click: 'onDisplaySearchBarClick'
    	},
    	
    	'displayCaseloadBarButton': {
    		click: 'onDisplayCaseloadBarClick'
    	},

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
    	
    	deletePersonButton: {
    		selector: '#deletePersonButton',
    		listeners: {
    			click: 'onDeletePersonClick'
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
	   	// ensure the selected person is not loaded twice
		// once on load and once on selection
	   	me.personLite.set('id','');
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

		return me.callParent(arguments);
    },
    
    onTextStoreLoad:function(){
    	var me = this;
    	me.applyColumns();
    	me.onCollapseStudentRecord();
    },
    
	onSelectionChange: function(selModel,records,eOpts){ 
		var me=this;
        var skipCallBack = this.appEventsController.getApplication().fireEvent('personNav', records, me);  

		if(skipCallBack)
		{
			var person = new Ssp.model.Person();
			// clear the person record
			me.person.data = person.data;
			if (records.length > 0)
			{
				me.updatePerson(records);
				me.appEventsController.getApplication().fireEvent('loadPerson');	
			}
		}
	},
    updatePerson: function(records){
    var me=this;
		if (records[0].data.id != null)
		{
			me.personLite.set('id', records[0].data.id);
		}else{
			me.personLite.set('id', records[0].data.personId);
		}
		me.personLite.set('firstName', records[0].data.firstName);
		me.personLite.set('middleName', records[0].data.middleName);
		me.personLite.set('lastName', records[0].data.lastName);
		me.personLite.set('displayFullName', records[0].data.firstName + ' ' + records[0].data.lastName);
	},
	
	onViewReady: function(comp, eobj){
		var me=this;
        me.appEventsController.assignEvent({eventName: 'toolsNav', callBackFunc: me.onToolsNav, scope: me});
		me.appEventsController.assignEvent({eventName: 'collapseStudentRecord', callBackFunc: me.onCollapseStudentRecord, scope: me});
	   	me.appEventsController.assignEvent({eventName: 'expandStudentRecord', callBackFunc: me.onExpandStudentRecord, scope: me});
	   	me.appEventsController.assignEvent({eventName: 'setNonParticipatingProgramStatusComplete', callBackFunc: me.onSetNonParticipatingProgramStatusComplete, scope: me});
		me.appEventsController.assignEvent({eventName: 'onPersonSearchSuccess', callBackFunc: me.searchSuccess, scope: me});
		me.appEventsController.assignEvent({eventName: 'onPersonSearchFailure', callBackFunc: me.searchFailure, scope: me});
	   	me.initSearchGrid();

	   	// load program statuses
		me.getProgramStatuses();	
	},

    destroy: function() {
    	var me=this;
        me.appEventsController.removeEvent({eventName: 'toolsNav', callBackFunc: me.onToolsNav, scope: me});
    	me.appEventsController.removeEvent({eventName: 'collapseStudentRecord', callBackFunc: me.onCollapseStudentRecord, scope: me});
	   	me.appEventsController.removeEvent({eventName: 'expandStudentRecord', callBackFunc: me.onExpandStudentRecord, scope: me});
	   	me.appEventsController.removeEvent({eventName: 'retrieveCaseload', callBackFunc: me.onRetrieveCaseload, scope: me});
	   	me.appEventsController.removeEvent({eventName: 'onPersonSearchSuccess', callBackFunc: me.searchSuccess, scope: me});
		me.appEventsController.removeEvent({eventName: 'onPersonSearchFailure', callBackFunc: me.searchFailure, scope: me});
		me.appEventsController.removeEvent({eventName: 'onPersonSearchFailure', callBackFunc: me.searchFailure, scope: me});
		
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
				me.getCaseload();
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
    	
    	me.refreshPagingToolBar();    	
    },
    
    onCollapseStudentRecord: function() {
		var me = this;
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
		me.preferences.set('SEARCH_VIEW_SIZE', "COLLAPSED");
	},
	
	onExpandStudentRecord: function() {
		var me = this;
	    me.showColumn(true,'birthDate');
		me.showColumn(true,'studentType')
		if(me.getIsCaseload()){
			me.showColumn(false,'coach');
			me.showColumn(false,'currentProgramStatusName');
		}else{
			me.showColumn(true,'coach');	
			me.showColumn(true,'currentProgramStatusName');
		}
		me.preferences.set('SEARCH_VIEW_SIZE', "EXPANDED");
	},  

	setGridView: function( view ){
		var me=this;
		me.applyColumns();
		if(me.getIsExpanded()){
			me.onExpandStudentRecord();
		}else{
			me.onCollapseStudentRecord();
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
		var searchView = Ext.ComponentQuery.query('search')[0];
		searchView.collapse();
	},
	onDisplaySearchBarClick: function( button ){
		this.displaySearchBar();
	},
	
	onDisplayCaseloadBarClick: function( button ){
		this.displayCaseloadBar();
	},
	
	displaySearchBar: function(){
		var me=this;
		me.preferences.set('SEARCH_GRID_VIEW_TYPE', me.SEARCH_GRID_VIEW_TYPE_IS_SEARCH);
		me.getCaseloadBar().hide();
		me.getSearchBar().show();
		Ext.ComponentQuery.query('searchForm')[0].show();
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
		var studentIdAlias = me.sspConfig.get('studentIdAlias');
		if ( me.getIsCaseload() )
		{
			store = me.caseloadStore;
			
		}else{
			store = me.searchStore;
			store.pageSize = store.data.length;
		}
		
		columns = [
	              { sortable: sortableColumns, header: me.textStore.getValueByCode('ssp.label.first-name'), dataIndex: 'firstName', flex: 1 },		        
	              { sortable: sortableColumns, header: me.textStore.getValueByCode('ssp.label.middle-name'), dataIndex: 'middleName', flex: me.getIsExpanded() && me.getIsCaseload() ? .4:.2},
	              { sortable: sortableColumns, header: me.textStore.getValueByCode('ssp.label.last-name'), dataIndex: 'lastName', flex: 1},
				  { sortable: sortableColumns, header: me.textStore.getValueByCode('ssp.label.dob'), dataIndex: 'birthDate', renderer: Ext.util.Format.dateRenderer('m/d/Y'), flex: .5},
	              { sortable: sortableColumns, header: 'Coach', dataIndex: 'coach', renderer: me.columnRendererUtils.renderCoachName, flex: 1},
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

			return cls;
	    };  		
		
		me.formUtils.reconfigureGridPanel(grid, store, columns);
	},

	 showColumn: function( show, dataIndex ) {
		var me=this;
        var column = Ext.ComponentQuery.query('.gridcolumn[dataIndex='+dataIndex+']')[0];
    	if ( column ) {
    	    if ( show ) {
                column.show();
            } else {
                column.hide();
            }
        }
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

	onDeletePersonClick: function( button ){
    	var me=this;
        var skipCallBack = this.appEventsController.getApplication().fireEvent('personButtonDelete',me);  
        if(skipCallBack)
        {
        	me.onDeletePerson();
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

	onDeletePerson: function(){
	    var records = this.getView().getSelectionModel().getSelection();
		if (records.length>0)
		{
			this.deleteConfirmation();
		}else{
			Ext.Msg.alert('Error','Please select a student to delete.');
		}
	},	
	
    deleteConfirmation: function() {
    	var message = 'You are about to delete the student: "'+ this.person.getFullName() + '". Would you like to continue?';
    	var model = this.person;
        if (model.get('id') != "") 
        {  
           Ext.Msg.confirm({
   		     title:'Delete Student?',
   		     msg: message,
   		     buttons: Ext.Msg.YESNO,
   		     fn: this.deletePerson,
   		     scope: this
   		   });
        }else{
     	   Ext.Msg.alert('SSP Error', 'Unable to delete student.'); 
        }
     },	
	
	deletePerson: function( btnId  ){
     	var me=this;
     	var id = me.personLite.get('id');
     	if (btnId=="yes")
     	{
     	   me.getView().setLoading( true );
           me.personService.destroy( id,
        		   {
        	   success: me.deletePersonSuccess,
        	   failure: me.deletePersonFailure,
        	   scope: me
           });	
     	}	
	},
	
	deletePersonSuccess: function( r, scope ){
		var me=scope;
		var store = me.searchStore;
		var id = me.personLite.get('id');
		me.getView().setLoading( false );
	    store.remove( store.getById( id ) );
		me.loadStudentToolsView();
	},
	
	deletePersonFailure: function( r, scope ){
		var me=scope;
		me.getView().setLoading( false );
	},

    refreshPagingToolBar: function(){
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
			var person = me.caseloadStore.findRecord("personId", personId);
			if(!person)
			  	person = me.searchStore.findRecord("id", personId);
			
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
		//var outsideCaseload = !me.getSearchCaseloadCheck().getValue();
		//var searchTerm = me.getSearchText().value;
		// store search term
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
    	me.getView().setLoading( false );
		me.selectFirstItem();
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
    	if ( me.programStatusesStore.getCount() > 0)
    	{
    		me.getCaseloadStatusCombo().setValue( me.caseloadFilterCriteria.get('programStatusId') );
    	}
    },	

    getProgramStatusesFailure: function( r, scope){
    	var me=scope;
    },     
    
	getCaseload: function(){
    	var me=this;
		me.preferences.set('SEARCH_GRID_VIEW_TYPE',1);
		me.setGridView();
		me.getView().setLoading( true );
		me.caseloadService.getCaseload( me.caseloadFilterCriteria.get( 'programStatusId' ), 
    		{success:me.getCaseloadSuccess, 
			 failure:me.getCaseloadFailure, 
			 scope: me});		
	},
    
    getCaseloadSuccess: function( r, scope){
    	var me=scope;
    	me.getView().setLoading( false );
		me.selectFirstItem();
		me.getSearchGridPager().onLoad();
    },

    getCaseloadFailure: function( r, scope){
    	var me=scope;
    	me.getView().setLoading( false );
    }
});