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
        watchListStore: 'watchListStore',
        caseloadService: 'caseloadService',
        exportService: 'exportService',
        watchListService: 'watchListService',
        columnRendererUtils: 'columnRendererUtils',
        formUtils: 'formRendererUtils',
        person: 'currentPerson',
        caseloadActionsStore: 'caseloadActionsStore',
        personLite: 'personLite',
        personService: 'personService',
        personProgramStatusService: 'personProgramStatusService',
        preferences: 'preferences',
        programStatusesStore: 'caseloadFilterProgramStatusesStore',
        programStatusService: 'caseloadFilterProgramStatusService',
        searchCriteria: 'searchCriteria',
        searchService: 'searchService',
        searchStoreOld: 'studentsSearchStore',
        searchStore: 'directoryPersonSearchStore',
		termsStore: 'termsStore',
        configStore: 'configurationOptionsUnpagedStore',
        textStore:'sspTextStore'
        
    },
    
    control: {
    	view: {
    		itemclick: 'onSelectionChange',
			viewready: 'onViewReady',
			render:  function() {
	            alert('render'); //this is work
	        },
	        show:function() {
	            alert('show'); //this is work
	        }
    	},    	
    	caseloadStatusCombo: {
    		selector: '#caseloadStatusCombo',
    		listeners: {
    			select: 'onCaseloadStatusComboSelect'
    		} 
    	},
    	caseloadActionCombo: {
    		selector: '#caseloadActionCombo',
    		listeners: {
    			select: 'onCaseloadActionComboSelect'
    		} 
    	},
    	    	
    	searchGridPager: '#searchGridPager',
    	searchBar: '#searchBar',


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
		},
		'setInactiveButton': {
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
	   	me.SEARCH_GRID_VIEW_TYPE_IS_WATCHLIST = 2;
	   	
		if(me.caseloadActionsStore.getTotalCount() == 0){
			me.caseloadActionsStore.load();
		};
		if(me.termsStore.getTotalCount() == 0){
				me.termsStore.addListener("load", me.onTermsStoreLoad, me);
				me.termsStore.load();
		};
		if(me.textStore.getTotalCount() == 0){
			me.textStore.addListener("load", me.onTextStoreLoad, me, {single: true});
			me.textStore.load();
		}else{
			me.onTextStoreLoad();
		};
		if(me.configStore.getTotalCount() == 0){
			me.configStore.addListener("load", me.onTextStoreLoad, me, {single: true});
			me.configStore.load();
		};
		me.personLite.on('idchanged', me.personChanged, me);
	   	// load program statuses
		me.getProgramStatuses();
		
		switch(tabSelection) {
			case 'myCaseload':
	            me.displayCaseload();
				break;
			case 'watchList':
	            me.displayWatchList();
				break;
			case 'search':
	            me.displaySearch();
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
    	var index = !me.authenticatedPerson.hasAccess('CASELOAD_SEARCH') ? 0 : 2;
    	var birthDateField = Ext.ComponentQuery.query('searchForm')[index].query('datefield[itemId=birthDate]')[0];
    	if ( birthDateField ) {
    	    birthDateField.setFieldLabel( me.textStore.getValueByCode('ssp.label.dob') + ':' );
    	}

    	me.setGridView();
    },
    
	onSelectionChange: function( view, record, item, index, eventObj ) {
		var me=this;
        var skipCallBack = this.appEventsController.getApplication().fireEvent('personNav', record, me);

		if( skipCallBack ) {
			if ( record ) {
				if( record.get("id") ) {
					if( me.personLite.get('id') != record.get("id") ) {
						var person = new Ssp.model.Person();
						// clear the person record
						me.person.data = person.data;
						me.updatePerson(record);
						me.appEventsController.getApplication().fireEvent('loadPerson');
					}
				} else if(me.authenticatedPerson.hasAccess('ADD_STUDENT_BUTTON')){
					var person = new Ssp.model.Person();
					// clear the person record
					me.person.data = person.data;
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
		me.appEventsController.assignEvent({eventName: 'onStudentWatchAction', callBackFunc: me.onViewReady, scope: me});
		
        me.appEventsController.assignEvent({eventName: 'toolsNav', callBackFunc: me.onToolsNav, scope: me});
		me.appEventsController.assignEvent({eventName: 'collapseStudentRecord', callBackFunc: me.onCollapseStudentRecord, scope: me});
	   	me.appEventsController.assignEvent({eventName: 'expandStudentRecord', callBackFunc: me.onExpandStudentRecord, scope: me});
	   	me.appEventsController.assignEvent({eventName: 'setNonParticipatingProgramStatusComplete', callBackFunc: me.onSetNonParticipatingProgramStatusComplete, scope: me});
		me.appEventsController.assignEvent({eventName: 'onPersonSearchSuccess', callBackFunc: me.searchSuccess, scope: me});
		me.appEventsController.assignEvent({eventName: 'onPersonSearchFailure', callBackFunc: me.searchFailure, scope: me});
		me.appEventsController.assignEvent({eventName: 'updateEarlyAlertCounts', callBackFunc: me.onUpdateEarlyAlertCounts, scope: me});
		me.appEventsController.assignEvent({eventName: 'updateSearchStoreRecord', callBackFunc: me.onUpdateSearchStoreRecord, scope: me});
	   	me.initSearchGrid();


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
		person = me.watchListStore.findRecord("id",params.personId, 0, false, false, true);
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
		me.appEventsController.removeEvent({eventName: 'onStudentWatchAction', callBackFunc: me.onViewReady, scope: me});	
    	me.appEventsController.removeEvent({eventName: 'collapseStudentRecord', callBackFunc: me.onCollapseStudentRecord, scope: me});
	   	me.appEventsController.removeEvent({eventName: 'expandStudentRecord', callBackFunc: me.onExpandStudentRecord, scope: me});
	   	me.appEventsController.removeEvent({eventName: 'onPersonSearchSuccess', callBackFunc: me.searchSuccess, scope: me});
		me.appEventsController.removeEvent({eventName: 'onPersonSearchFailure', callBackFunc: me.searchFailure, scope: me});
		me.appEventsController.removeEvent({eventName: 'updateEarlyAlertCounts', callBackFunc: me.onUpdateEarlyAlertCounts, scope: me});
		me.appEventsController.removeEvent({eventName: 'updateSearchStoreRecord', callBackFunc: me.onUpdateSearchStoreRecord, scope: me});
	   	me.appEventsController.removeEvent({eventName: 'setNonParticipatingProgramStatusComplete', callBackFunc: me.onSetNonParticipatingProgramStatusComplete, scope: me});

	   	if ( me.emailStudentPopup ) {
	   	    me.emailStudentPopup.destroy();
	   	}
	   	
		if(me.instantCaseload != null && !me.instantCaseload.isDestroyed)
			me.instantCaseload.close();
		
		return me.callParent( arguments );
    },
    
    initSearchGrid: function(){
	   	var me=this;
	   	
	   	if( me.getIsSearch() )
		{
			me.displaySearch();
		} else
		if( me.getIsWatchList() )
		{
			me.getWatchList(true);
			me.displayWatchList();
		}	   	
	   	else{
			if ( me.authenticatedPerson.hasAccess('CASELOAD_FILTERS') )
			{
				me.preferences.set('SEARCH_GRID_VIEW_TYPE', me.SEARCH_GRID_VIEW_TYPE_IS_CASELOAD);
				// default caseload to Active students if no program status is defined

				me.getCaseload(true);
				me.displayCaseload();
			}else{
				me.search();
				me.displaySearch();
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
		me.showColumn(false,'primaryEmailAddress');
		if(me.getIsCaseload() || me.getIsWatchList()){
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
		me.showColumn(true,'studentType');
		me.showColumn(true,'primaryEmailAddress');
		if(me.getIsCaseload() || me.getIsWatchList()){
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
		if(me.getView().tabContext === 'myCaseload')
			return true;
		return false;
	},
	getIsSearch: function(){
		var me= this;
		if(me.getView().tabContext === 'search')
			return true;
		return false;
	},	
	getIsWatchList: function(){
		var me= this;
		if(me.getView().tabContext === 'watchList')
			return true;
		return false;
	},
	onToolsNav: function() {
		var searchView = Ext.ComponentQuery.query('searchtab')[0];
		searchView.collapse();
	},

	displaySearch: function() {
	    var me = this;
		me.getSearchBar().show();
		
		if ( me.authenticatedPerson.hasAccess('CASELOAD_SEARCH') ) {
		    Ext.ComponentQuery.query('searchForm')[2].show(); //user has two, second is search
        } else {
            Ext.ComponentQuery.query('searchForm')[0].show(); //user only has one (no caseload)
        }

        me.setGridView();
    },

	displayCaseload: function(){
		var me=this;
		
		me.getSearchBar().hide();
		me.setGridView();
	},
	displayWatchList: function(){
		var me=this;
		
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
			}
		else
		if( me.getIsWatchList() )
		{
			store = me.watchListStore;
			
		} else
		{
			store = me.searchStore;
			store.pageSize = store.data.length;
			if (me.searchStore.data.length < 1) {
				me.getCaseloadActionCombo().hide();
			}
			else {
				if (me.authenticatedPerson.hasAnyBulkPermissions()) {
					me.getCaseloadActionCombo().show();
				}
			}
		}
		
		columns = [
	              { sortable: sortableColumns, header: me.textStore.getValueByCode('ssp.label.first-name'), dataIndex: 'firstName', flex: 1 },		        
	              { sortable: sortableColumns, header: me.textStore.getValueByCode('ssp.label.middle-name'), dataIndex: 'middleName', flex: me.getIsExpanded() ? 0.4:0.2},
	              { sortable: sortableColumns, header: me.textStore.getValueByCode('ssp.label.last-name'), dataIndex: 'lastName', flex: 1},
				  { sortable: sortableColumns, header: me.textStore.getValueByCode('ssp.label.dob'), dataIndex: 'birthDate', renderer: Ext.util.Format.dateRenderer('m/d/Y'), flex: 0.5},
	              { sortable: sortableColumns, header: coachIdAlias, dataIndex: 'coach', renderer: me.columnRendererUtils.renderCoachName, flex: 1},
	              { sortable: sortableColumns, header: 'Type', dataIndex: 'studentType', renderer: me.columnRendererUtils.renderStudentType, flex: me.getIsExpanded() ? 0.5:0.2},
				  { sortable: sortableColumns, header: studentIdAlias, dataIndex: 'schoolId', flex: me.getIsExpanded() ? 0.5:1},
				  { sortable: sortableColumns, header: 'Email', dataIndex: 'primaryEmailAddress', flex: 0.8},
	              { sortable: sortableColumns, header: 'Status', dataIndex: 'currentProgramStatusName', flex: 0.2},
	              { sortable: sortableColumns, header: 'Alerts', dataIndex: 'numberOfEarlyAlerts', flex: 0.2}
	              ];

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
		
		if(me.getSearchGridPager)
		{
			me.getSearchGridPager().bindStore(store);
		}
		
		me.refreshPagingToolBar();
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
        	case 'inactive':
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
	   		if (action=='inactive')
	   		{
	   			programStatusId = Ssp.util.Constants.INACTIVE_PROGRAM_STATUS_ID;
				person.set("currentProgramStatusName", "Inactive");
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
	   		if (action=='inactive')
	   			msg = 'Inactive';	   		
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
        me.setGridView();
    	me.getView().setLoading( false );
    	me.personChanged();
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
        	if(me.getView().tabContext === 'watchList')
        	{
        		me.getWatchList();
        	}
        	else
        	{
        		me.getCaseload();
        	}
        }

	},
	
	onCaseloadStatusComboSelect: function( comp, records, eOpts ){
		var me=this;
		if ( records.length > 0)
    	{
			me.caseloadFilterCriteria.set('programStatusId', records[0].get('id') );
			me.caseloadFilterCriteria.set('programStatusName', records[0].get('name') );
     	}
		
        var skipCallBack = this.appEventsController.getApplication().fireEvent('retrieveCaseload',me);  
        if(skipCallBack)
        {
        	if(me.getView().tabContext === 'watchList')
        	{
        		me.getWatchList();
        	}
        	else
        	{
        		me.getCaseload();
        	}
        }
	},
	bulkEmail: function(){
		var me=this;
		var store = null;
		var criteria = {};

		criteria.programStatus = me.translateSelectedStatustoSearchableStatus();

		if ( me.getIsCaseload() )
		{
			store = me.caseloadStore;
			criteria.myCaseload = true;
		}else
		if( me.getIsWatchList() )
		{
			store = me.watchListStore;
			criteria.myWatchList = true;
		} else
		{
			store = me.searchStore;
		}
		if ( me.emailStudentPopup ) {
			me.emailStudentPopup.destroy();
		}
		me.emailStudentPopup = Ext.create('Ssp.view.EmailStudentView',{
			isBulk: true,
			bulkCriteria: criteria
		});
		me.emailStudentPopup.show();
	},
	exportSearch: function(searchType) {
		var me = this;
		window.open(me.exportService.buildExportCaseloadUrl(me.translateSelectedStatustoSearchableStatus(), searchType),
			'_blank');
	},
	onExportConfirm: function(btnId, searchType) {
		var me = this;
		if (btnId=="ok") {
			me.exportSearch(searchType);
		}
	},
	newOnExportConfirm: function (searchType) {
		var me = this;
		return function(btnId) {
			me.onExportConfirm(btnId, searchType);
		}
	},
	onBulkEmailConfirm: function(btnId) {
		var me = this;
		if (btnId=="ok") {
			me.bulkEmail();
		}
	},
	promptWithExportCount: function(count) {
		var me = this;
		var message;
		count = parseInt(count);
		// loadMaskOff() copy/pasted in both prompy*() functions to try to delay that dismissal as long
		// as possible... let all 'background' lookup and computation complete before we re-engage the UI
		me.appEventsController.loadMaskOff();
		var searchRsltType = null;
		if ( me.getIsCaseload() ) {
			searchRsltType = 'caseload';
		}
		if ( me.getIsWatchList() ) {
			searchRsltType = 'watchlist';
		}
		Ext.Msg.confirm({
			title:'Confirm',
			msg: count + " student/s will be exported. Continue?",
			buttons: Ext.Msg.OKCANCEL,
			fn: me.newOnExportConfirm(searchRsltType),
			scope: me
		});
	},
	promptWithEmailCount: function(count) {
		var me = this;
		count = parseInt(count);
		var maxEmail =  parseInt(me.configStore.getConfigByName('mail_bulk_message_limit').trim());
		// loadMaskOff() copy/pasted in both prompy*() functions to try to delay that dismissal as long
		// as possible... let all 'background' lookup and computation complete before we re-engage the UI
		me.appEventsController.loadMaskOff();
		if(maxEmail > 0 && count > maxEmail) {
			Ext.Msg.alert('Too Many Search Results','The number of students in your request ('+count+') exceed the ' +
				'bulk email limit ('+maxEmail+'). <br/><br/>Consider exporting results to a CSV file and using that ' +
				'file as input to a third party bulk email application.');
			return;
		} else if ( count === 0 ) {
			Ext.Msg.alert('Too Few Search Results','Cannot send bulk email to an empty caseload/watchlist/search result.');
			return;
		} else {
			Ext.Msg.confirm({
				title:'Confirm',
				msg: count + " student/s will be emailed. Continue?",
				buttons: Ext.Msg.OKCANCEL,
				fn: me.onBulkEmailConfirm,
				scope: me
			});
		}
	},
	newBulkActionCountResultFailureCallback: function(actionType) {
		var me = this;
		return function(cnt) {
			return me.onBulkActionCountFailure(cnt, actionType);
		}
	},
	newBulkActionCountResultSuccessCallback: function(actionType) {
		var me = this;
		return function(cnt) {
			return me.onBulkActionCountSuccess(cnt, actionType);
		}
	},
	onBulkActionCountFailure: function(cnt, actionType) {
		var me = this;
		me.appEventsController.loadMaskOff();
		Ext.Msg.alert('SSP Error', 'Failed to look up the number of records which would be affected by the ' +
			'requested action. Retry or contact your system administrator');
	},
	onBulkActionCountSuccess: function(cnt, actionType) {
		var me = this;
		if ( actionType === 'EXPORT' ) {
			me.promptWithExportCount(cnt);
		} else if ( actionType === 'EMAIL' ) {
			me.promptWithEmailCount(cnt);
		}
	},
	translateSelectedStatustoSearchableStatus: function() {
		var me = this;
		if ( !(me.getCaseloadStatusCombo().getValue()) ) {
			return Ssp.util.Constants.ACTIVE_PROGRAM_STATUS_ID;
		} else if ( me.getCaseloadStatusCombo().getValue() === 'All' ) {
			return null;
		} else {
			return me.getCaseloadStatusCombo().getValue()
		}
	},
	onCaseloadActionComboSelect: function( comp, records, eOpts ){
		var me=this;
		if(me.getIsSearch()) {
			me.appEventsController.getApplication().fireEvent('onSearchActionComboSelect', records);
			comp.setValue(null);
			return;
		}
		if(records.length > 0) {
			// TODO this really needs to be moved into a modal dialog, else these counts could come back and be
			// completely irrelevant w/r/t whatever the user is looking at currently. The loading mask is not
			// modal, but it's the short-term stopgap measure to try to discourage people from interacting with
			// the UI while we look up the count.
			me.appEventsController.loadMaskOn();
			if(me.getIsCaseload()) {
				me.caseloadService.getCaseloadCount(me.translateSelectedStatustoSearchableStatus(), {
					success: me.newBulkActionCountResultSuccessCallback(records[0].get('id')),
					failure: me.newBulkActionCountResultFailureCallback(records[0].get('id')),
					scope: me
				});
			} else if(me.getIsWatchList()) {
				me.watchListService.getWatchlistCount(me.translateSelectedStatustoSearchableStatus(), {
					success: me.newBulkActionCountResultSuccessCallback(records[0].get('id')),
					failure: me.newBulkActionCountResultFailureCallback(records[0].get('id')),
					scope: me
				});
			}
			comp.setValue(null);
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
			me.getCaseloadStatusCombo().setValue(Ssp.util.Constants.ACTIVE_PROGRAM_STATUS_ID);
    	}
    },	

    getProgramStatusesFailure: function( r, scope){
    	var me=scope;
    },     
	getWatchList: function(){
    	var me=this;
		me.setGridView();
		me.getView().setLoading( true );
		me.watchListService.getWatchList(
			me.translateSelectedStatustoSearchableStatus(),
			me.watchListStore,
			{
				success:me.getCaseloadSuccess,
				failure:me.getCaseloadFailure,
				scope: me
			});
	},
	getCaseload: function(){
    	var me=this;
		me.preferences.set('SEARCH_GRID_VIEW_TYPE',1);
		me.setGridView();
		me.getView().setLoading( true );
		me.caseloadService.getCaseload(
			me.translateSelectedStatustoSearchableStatus(),
			me.caseloadStore,
			{
				success:me.getCaseloadSuccess,
				failure:me.getCaseloadFailure,
				scope: me
			});
	},  
    getCaseloadSuccess: function( r, scope){
    	var me=scope;
    	if(me.getSearchGridPager)
    	{
    		me.getSearchGridPager().onLoad();
    	}
		me.harmonizePersonLite();
		if ( me.getIsCaseload() )
			{
				
				if(me.caseloadStore.data.length < 1)
				{
					me.getCaseloadActionCombo().hide();
				}
				else
				{
					if (me.authenticatedPerson.hasAnyBulkPermissions()) {
						me.getCaseloadActionCombo().show();
						
					}
				}
				
			}
		else
		if( me.getIsWatchList() )
		{
			
			if(me.watchListStore.getTotalCount() < 1)
			{
				
				me.getCaseloadActionCombo().hide();
			}
			else
			{
				if (me.authenticatedPerson.hasAnyBulkPermissions()) {
					me.getCaseloadActionCombo().show();
					
				}
			}
		} 
		me.getView().setLoading( false );
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