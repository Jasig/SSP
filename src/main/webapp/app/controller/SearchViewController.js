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
        searchStore: 'searchStore',
        sspConfig: 'sspConfig'
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
    	searchText: {
    		selector: '#searchText',
		    listeners:{   
		        keypress: 'onSearchKeyPress'  
		    } 
    	},
    	
    	searchCaseloadCheck: '#searchCaseloadCheck',
    	searchBar: '#searchBar',
    	caseloadBar: '#caseloadBar',

    	'searchButton': {
    		click: 'onSearchClick'
    	},
    	
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

    	// set the search results to the stored
	   	// search results
		me.getSearchText().setValue( me.searchCriteria.get('searchTerm') );
	   	me.getSearchCaseloadCheck().setValue( !me.searchCriteria.get('outsideCaseload') );
		
		return me.callParent(arguments);
    },
    
	onSelectionChange: function(selModel,records,eOpts){ 
		var me=this;
		var person = new Ssp.model.Person();
		// clear the person record
		me.person.data = person.data;
		if (records.length > 0)
		{
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
			me.appEventsController.getApplication().fireEvent('loadPerson');			
		}
	},

	onViewReady: function(comp, eobj){
		var me=this;
		me.appEventsController.assignEvent({eventName: 'collapseStudentRecord', callBackFunc: me.onCollapseStudentRecord, scope: me});
	   	me.appEventsController.assignEvent({eventName: 'expandStudentRecord', callBackFunc: me.onExpandStudentRecord, scope: me});
	   	me.appEventsController.assignEvent({eventName: 'setNonParticipatingProgramStatusComplete', callBackFunc: me.onSetNonParticipatingProgramStatusComplete, scope: me});
	
	   	me.initSearchGrid();

	   	// load program statuses
		me.getProgramStatuses();	
	},

    destroy: function() {
    	var me=this;
    	me.appEventsController.removeEvent({eventName: 'collapseStudentRecord', callBackFunc: me.onCollapseStudentRecord, scope: me});
	   	me.appEventsController.removeEvent({eventName: 'expandStudentRecord', callBackFunc: me.onExpandStudentRecord, scope: me});
	   	me.appEventsController.removeEvent({eventName: 'retrieveCaseload', callBackFunc: me.onRetrieveCaseload, scope: me});
	   	return me.callParent( arguments );
    },
    
    initSearchGrid: function(){
	   	var me=this;
    	// load search if preference is set
	   	if ( me.preferences.get('SEARCH_GRID_VIEW_TYPE')==0 )
		{
			me.search();
			me.displaySearchBar();
		}else{
			// otherwise load caseload if caseload is
			// available to user. this will ensure
			// caseload will load on first entrance into
			// the program
			if ( me.authenticatedPerson.hasAccess('CASELOAD_FILTERS') )
			{
				me.preferences.set('SEARCH_GRID_VIEW_TYPE',1);
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
    
    onCollapseStudentRecord: function(){
	},
	
	onExpandStudentRecord: function(){
	},  

	setGridView: function( view ){
		var me=this;
		me.applyColumns();
	},
	
	onDisplaySearchBarClick: function( button ){
		this.displaySearchBar();
	},
	
	onDisplayCaseloadBarClick: function( button ){
		this.displayCaseloadBar();
	},
	
	displaySearchBar: function(){
		var me=this;
		me.preferences.set('SEARCH_GRID_VIEW_TYPE',0);
		me.getCaseloadBar().hide();
		me.getSearchBar().show();
		me.setGridView();
	},

	displayCaseloadBar: function(){
		var me=this;
		me.preferences.set('SEARCH_GRID_VIEW_TYPE',1);
		me.getCaseloadBar().show();
		me.getSearchBar().hide();
		me.setGridView();
	},
	
	applyColumns: function(){
		var me=this;
		var grid = me.getView();
		var store;
		var sortableColumns = false;
		var studentIdAlias = me.sspConfig.get('studentIdAlias');
		if ( me.preferences.get('SEARCH_GRID_VIEW_TYPE')==1 )
		{
			store = me.caseloadStore;
			columns = [
    	              { sortable: sortableColumns, header: 'First', dataIndex: 'firstName', flex: 1 },		        
    	              { sortable: sortableColumns, header: 'MI', dataIndex: 'middleName', flex: .2},
    	              { sortable: sortableColumns, header: 'Last', dataIndex: 'lastName', flex: 1},
    	              { sortable: sortableColumns, header: 'Type', dataIndex: 'studentType', renderer: me.columnRendererUtils.renderStudentType, flex: .2},
    	              { sortable: sortableColumns, header: studentIdAlias, dataIndex: 'schoolId', flex: 1},
    	              { sortable: sortableColumns, header: 'Alerts', dataIndex: 'numberOfEarlyAlerts', flex: .2}
    	              ];
		}else{
			store = me.searchStore;
			columns = [
    	              /* { header: "Photo", dataIndex: 'photoUrl', renderer: this.columnRendererUtils.renderPhotoIcon, flex: 50 }, */		        
    	              /*{ sortable: sortableColumns, header: 'Student', dataIndex: 'lastName', renderer: me.columnRendererUtils.renderSearchStudentName, flex: .25 },*/
    	              { sortable: sortableColumns, header: 'First', dataIndex: 'firstName', flex: .2},		        
    	              { sortable: sortableColumns, header: 'MI', dataIndex: 'middleName', flex: .05},
    	              { sortable: sortableColumns, header: 'Last', dataIndex: 'lastName', flex: .2},
    	              { sortable: sortableColumns, header: 'Coach', dataIndex: 'coach', renderer: me.columnRendererUtils.renderCoachName, flex: .25 },
    	              { sortable: sortableColumns, header: studentIdAlias, dataIndex: 'schoolId', flex: .15},
    	              { sortable: sortableColumns, header: 'Status', dataIndex: 'currentProgramStatusName', flex: .15}
    	              ];		
		}

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

    onAddPersonClick: function( button ){
    	var me=this;
    	me.onAddPerson();
	},
	
	onEditPersonClick: function( button ){
    	var me=this;
    	me.onEditPerson();
	},

	onDeletePersonClick: function( button ){
    	var me=this;
    	me.onDeletePerson();
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
  
    onSetProgramStatusClick: function( button ){
    	var me=this;
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
    	     	 me.appEventsController.getApplication().fireEvent('transitionStudent');
    	     	 break;
    	     	 
    		case 'non-participating':
    			Ext.create('Ssp.view.ProgramStatusChangeReasonWindow', {
    			    height: 150,
    			    width: 500
    			}).show();
    			break;
    	}
    },
    
    setProgramStatus: function( action ){
	   	var me=this;
	   	var personId = me.personLite.get('id');
	   	var programStatusId = "";
	   	if (personId != "")
	   	{
	   		if (action=='active')
	   		{
	   			programStatusId = Ssp.util.Constants.ACTIVE_PROGRAM_STATUS_ID;
	   		}
	   		
	   		if (action=='no-show')
	   		{
	   			programStatusId = Ssp.util.Constants.NO_SHOW_PROGRAM_STATUS_ID;
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
	   		Ext.Msg.alert('SSP Error','Unable to determine student to set to No-Show status');
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
			me.searchStore.removeAll();
		}	
	},
	
	setSearchCriteria: function(){
		var me=this;
		var outsideCaseload = !me.getSearchCaseloadCheck().getValue();
		var searchTerm = me.getSearchText().value;
		// store search term
		me.searchCriteria.set('searchTerm', searchTerm);
		me.searchCriteria.set('outsideCaseload', outsideCaseload);
	},
	
	onSearchKeyPress: function(comp,e){
		var me=this;
        if(e.getKey()==e.ENTER){  
    		me.setSearchCriteria();
        	if ( me.searchCriteria.get('searchTerm') != "")
    		{
    			me.search();
    		}else{
    			me.searchStore.removeAll();
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
					me.searchCriteria.get('searchTerm'), 
					me.searchCriteria.get('outsideCaseload'),
					{
					success: me.searchSuccess,
					failure: me.searchFailure,
					scope: me
			});				
		}
	},

    searchSuccess: function( r, scope){
    	var me=scope;
    	me.getView().setLoading( false );
		me.selectFirstItem();
    },

    searchFailure: function( r, scope){
    	var me=scope;
    	me.getView().setLoading( false );
    },
    
    
    /**************** CASELOAD FILTERS *********************/
    
	onRetrieveCaseloadClick: function( button ){
		var me=this;
		me.getCaseload();
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
    	   	/*
    		if ( me.preferences.get('SEARCH_GRID_VIEW_TYPE')==1 )
    		{
    	   		me.getCaseload();
    		}
    		*/
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
    },

    getCaseloadFailure: function( r, scope){
    	var me=scope;
    	me.getView().setLoading( false );
    }
});