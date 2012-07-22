Ext.define('Ssp.controller.SearchViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
        appEventsController: 'appEventsController',
        caseloadStore: 'caseloadStore',
        caseloadService: 'caseloadService',
        columnRendererUtils: 'columnRendererUtils',
        formUtils: 'formRendererUtils',
        person: 'currentPerson',
        personLite: 'personLite',
        personService: 'personService',
        preferences: 'preferences',
        programStatusesStore: 'programStatusesStore',
        programStatusService: 'programStatusService',
        searchService: 'searchService',
        searchStore: 'searchStore',
        sspConfig: 'sspConfig'
    },
    
    control: {
    	searchGridPager: '#searchGridPager',
    	searchText: '#searchText',
    	searchCaseloadCheck: '#searchCaseloadCheck',
    	caseloadStatusCombo: '#caseloadStatusCombo',
    	
    	view: {
    		selectionchange: 'onSelectionChange',
			viewready: 'onViewReady'
    	},
    	
    	'searchButton': {
    		click: 'onSearchClick'
    	},

    	'retrieveCaseloadButton': {
    		click: 'onRetrieveCaseloadClick'
    	},    	
    	
    	'displayPhotoButton': {
    		click: 'onDisplayPhotoClick'
    	},
    	
    	'displayListButton': {
    		click: 'onDisplayListClick'
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
    	
		'transitionStudentButton': {
			click: 'onTransitionStudentClick'
		},
		
		'setNonParticipatingButton': {
			click: 'onSetNonParticipatingClick'
		},
		
		'setNoShowButton': {
			click: 'onSetNoShowClick'
		}
    },
    
	init: function() {
		var me=this;    	
 		return me.callParent(arguments);
    },
    
	onSelectionChange: function(selModel,records,eOpts){ 
		var me=this;
		if (records.length > 0)
		{
			if (records[0].data.id != null)
			{
				me.personLite.set('id', records[0].data.id);
			}else{
				me.personLite.set('id', records[0].data.personId);
			}
			me.personLite.set('firstName', records[0].data.firstName);
			me.personLite.set('middleInitial', records[0].data.middleInitial);
			me.personLite.set('lastName', records[0].data.lastName);
			me.personLite.set('displayFullName', records[0].data.firstName + ' ' + records[0].data.lastName);
			me.appEventsController.getApplication().fireEvent('loadPerson');			
		}
	},

	onViewReady: function(comp, eobj){
		var me=this;
		me.appEventsController.assignEvent({eventName: 'collapseStudentRecord', callBackFunc: me.onCollapseStudentRecord, scope: me});
	   	me.appEventsController.assignEvent({eventName: 'expandStudentRecord', callBackFunc: me.onExpandStudentRecord, scope: me});
	
	   	// ensure the selected person is not loaded twice
	   	me.personLite.set('id','');

		if ( me.preferences.get('SEARCH_GRID_VIEW_TYPE')==0 )
		{
			me.setGridView('search');
		}else{
			me.setGridView('caseload');
		}
		
    	me.getProgramStatuses();
	},

    destroy: function() {
    	var me=this;
    	me.appEventsController.removeEvent({eventName: 'collapseStudentRecord', callBackFunc: me.onCollapseStudentRecord, scope: me});
	   	me.appEventsController.removeEvent({eventName: 'expandStudentRecord', callBackFunc: me.onExpandStudentRecord, scope: me});

        return me.callParent( arguments );
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
    		programStatus = me.programStatusesStore.findRecord("name", "active");
    		activeProgramStatusId = programStatus.get('id');
    		me.getCaseloadStatusCombo().setValue( activeProgramStatusId );
    		me.getCaseload();
    	}
    },	

    getProgramStatusesFailure: function( r, scope){
    	var me=scope;
    },     
    
	getCaseload: function(){
    	var me=this;
		var pId = "";
		if ( me.getCaseloadStatusCombo().getValue().length > 0)
    	{
			pId = me.getCaseloadStatusCombo().getValue();
     	}
		me.getView().setLoading( true );
		me.caseloadService.getCaseload( pId, 
    		{success:me.getCaseloadSuccess, 
			 failure:me.getCaseloadFailure, 
			 scope: me});		
	},
    
    getCaseloadSuccess: function( r, scope){
    	var me=scope;
    	me.getView().setLoading( false );
    	if ( me.caseloadStore.getCount() > 0)
    	{
    		me.getView().getSelectionModel().select(0);
    	}else{
    		// if no record is available, then cast event
    		// to reset the profile tool fields
    		me.personLite.set('id', "");
    		me.appEventsController.getApplication().fireEvent('loadPerson');
    	}
    },

    getCaseloadFailure: function( r, scope){
    	var me=scope;
    	me.getView().setLoading( false );
    },    
    
    onCollapseStudentRecord: function(){
    	console.log('SearchViewController->onCollapseStudentRecord');
	},
	
	onExpandStudentRecord: function(){
		console.log('SearchViewController->onExpandStudentRecord');
	},  

	setGridView: function( view ){
		var me=this;
		if (view=='search')
		{
			me.preferences.set('SEARCH_GRID_VIEW_TYPE',0);
		}else{
			me.preferences.set('SEARCH_GRID_VIEW_TYPE',1);
		}
		me.applyColumns();
	},
	
	onDisplayPhotoClick: function( button ){
		var me=this;
		me.setGridView('search');
	},
	
	onDisplayListClick: function( button ){
		var me=this;
		me.setGridView('caseload');
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
    	              { sortable: sortableColumns, header: 'MI', dataIndex: 'middleInitial', flex: .2},
    	              { sortable: sortableColumns, header: 'Last', dataIndex: 'lastName', flex: 1},
    	              { sortable: sortableColumns, header: 'Type', dataIndex: 'studentType', renderer: me.columnRendererUtils.renderStudentType, flex: .2},
    	              { sortable: sortableColumns, header: studentIdAlias, dataIndex: 'schoolId', flex: 1},
    	              { sortable: sortableColumns, header: 'Alerts', dataIndex: 'numberOfEarlyAlerts', flex: .2}
    	              ];			
		}else{
			store = me.searchStore;
			columns = [
    	              /* { header: "Photo", dataIndex: 'photoUrl', renderer: this.columnRendererUtils.renderPhotoIcon, flex: 50 }, */		        
    	              { sortable: sortableColumns, header: 'Student', dataIndex: 'lastName', renderer: me.columnRendererUtils.renderSearchStudentName, flex: .25 },
    	              { sortable: sortableColumns, header: 'Coach', dataIndex: 'coach', renderer: me.columnRendererUtils.renderCoachName, flex: .25 },
    	              { sortable: sortableColumns, header: studentIdAlias, dataIndex: 'schoolId', flex: .25},
    	              { sortable: sortableColumns, header: 'Status', dataIndex: 'currentProgramStatusName', flex: .25}
    	              ];		
		}
		
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

	onRetrieveCaseloadClick: function( button ){
		var me=this;
		me.setGridView('caseload');
		me.getCaseload();
	},
	
	onSearchClick: function(button){
		var me=this;
		var outsideCaseload = !me.getSearchCaseloadCheck().getValue();
		me.setGridView('search');
		if ( me.getSearchText().value != "")
		{
			me.getView().setLoading( true );
			me.searchService.search( 
					me.getSearchText().value, 
					outsideCaseload,
					{
					success: me.searchSuccess,
					failure: me.searchFailure,
					scope: me
			});	
		}else{
			me.searchStore.removeAll();
		}	
	},

    searchSuccess: function( r, scope){
    	var me=scope;
    	me.getView().setLoading( false );
    	if ( r.rows.length > 0)
    	{
    		me.getView().getSelectionModel().select(0);
    	}else{
    		Ext.Msg.alert('Attention','No students match your search. Try a different search value.');
    	}
    },

    searchFailure: function( r, scope){
    	var me=scope;
    	me.getView().setLoading( false );
    }, 	
	
    loadCaseloadAssignment: function(){
    	var comp = this.formUtils.loadDisplay('mainview', 'caseloadassignment', true, {flex:1});    	
    },
    
    onTransitionStudentClick: function(button){
     	 this.appEventsController.getApplication().fireEvent('transitionStudent');
    },
   
    onSetNonParticipatingClick: function(button){
		Ext.create('Ssp.view.ProgramStatusChangeReasonWindow', {
		    height: 150,
		    width: 500
		}).show();
    },
  
    onSetNoShowClick: function(button){
	   	var me=this;
	   	var personId = me.personLite.get('id');
	   	if (personId != "")
	   	{
	   		// TODO: Look-up No-Show Program Status Id
	   		personProgramStatus = new Ssp.model.PersonProgramStatus();
	   		personProgramStatus.set('programStatusId','b2d12640-5056-a51a-80cc-91264965731a');
	   		personProgramStatus.set('effectiveDate', new Date());
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
    }
});