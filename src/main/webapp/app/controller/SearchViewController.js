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
        preferences: 'preferences',
        programStatusesStore: 'programStatusesStore',
        programStatusService: 'programStatusService',
        searchService: 'searchService',
        searchStore: 'searchStore',
        sspConfig: 'sspConfig'
    },

    config: {
    	personUrl: null,
    },
    
    control: {
    	searchText: '#searchText',
    	searchCaseloadCheck: '#searchCaseloadCheck',
    	caseloadStatusCombo: {
    		selector: '#caseloadStatusCombo',
    		listeners: {
    			change: 'onCaseloadStatusComboSelect'
    		}
    	},
    	
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
    	}
    },
    
	init: function() {
		var me=this;
		
		me.applyColumns();
		
		me.personUrl =  me.apiProperties.createUrl( me.apiProperties.getItemUrl('person') );
				
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
			me.appEventsController.getApplication().fireEvent('loadPerson');			
		}
	},

	onViewReady: function(comp, eobj){
		var me=this;
		me.appEventsController.assignEvent({eventName: 'collapseStudentRecord', callBackFunc: me.onCollapseStudentRecord, scope: me});
	   	me.appEventsController.assignEvent({eventName: 'expandStudentRecord', callBackFunc: me.onExpandStudentRecord, scope: me});
	
	   	// ensure the selected person is not loaded twice
	   	me.personLite.set('id','');
	   	
	    me.programStatusesStore.removeAll();
		me.initializeCaseload();	
	},

    destroy: function() {
    	var me=this;
    	me.appEventsController.removeEvent({eventName: 'collapseStudentRecord', callBackFunc: me.onCollapseStudentRecord, scope: me});
	   	me.appEventsController.removeEvent({eventName: 'expandStudentRecord', callBackFunc: me.onExpandStudentRecord, scope: me});

        return me.callParent( arguments );
    },	

    initializeCaseload: function(){
    	var me=this;
    	me.getProgramStatuses();
    },

	onCaseloadStatusComboSelect: function(comp, value, eOpts){
    	var me=this;
		if (value.length > 0)
    	{
			me.getCaseload();
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
    		programStatus = me.programStatusesStore.findRecord("name", "active");
    		activeProgramStatusId = programStatus.get('id');
    		me.getCaseloadStatusCombo().select( activeProgramStatusId );
    	}
    },	

    getProgramStatusesFailure: function( r, scope){
    	var me=scope;
    },     
    
	getCaseload: function(){
    	var me=this;
		var pId = "";
		if ( me.getCaseloadStatusCombo().value.length > 0)
    	{
			pId = me.getCaseloadStatusCombo().value;
     	}
		me.caseloadService.getCaseload( pId, 
    		{success:me.getCaseloadSuccess, 
			 failure:me.getCaseloadFailure, 
			 scope: me});		
	},
    
    getCaseloadSuccess: function( r, scope){
    	var me=scope;
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
    },    
    
    onCollapseStudentRecord: function(){
    	console.log('SearchViewController->onCollapseStudentRecord');
	},
	
	onExpandStudentRecord: function(){
		console.log('SearchViewController->onExpandStudentRecord');
	},  

	onDisplayPhotoClick: function( button ){
		this.preferences.set('SEARCH_GRID_VIEW_TYPE',0);
		this.applyColumns();
	},
	
	onDisplayListClick: function( button ){
		this.preferences.set('SEARCH_GRID_VIEW_TYPE',1);
		this.applyColumns();
	},
	
	applyColumns: function(){
		var me=this;
		var grid = me.getView();
		var store = me.caseloadStore;
		var studentIdAlias = me.sspConfig.get('studentIdAlias');
		if ( me.preferences.get('SEARCH_GRID_VIEW_TYPE')==1 )
		{
			store = me.caseloadStore;
			columns = [
    	              { header: 'First', dataIndex: 'firstName', flex: 1 },		        
    	              { header: 'MI', dataIndex: 'middleInitial', flex: .2},
    	              { header: 'Last', dataIndex: 'lastName', flex: 1},
    	              { header: 'Type', dataIndex: 'studentType', renderer: me.columnRendererUtils.renderStudentType, flex: .2},
    	              { header: studentIdAlias, dataIndex: 'schoolId', flex: 1},
    	              { header: 'Alerts', dataIndex: 'alerts', flex: .2}
    	              ];			
		}else{
			store = me.searchStore;
			columns = [
    	              /* { header: "Photo", dataIndex: 'photoUrl', renderer: this.columnRendererUtils.renderPhotoIcon, flex: 50 }, */		        
    	              { text: 'Name', dataIndex: 'lastName', renderer: me.columnRendererUtils.renderStudentDetails, flex: 50}
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
		var store = me.searchStore;
     	var id = me.personLite.get('id');
     	if (btnId=="yes")
     	{
         	me.apiProperties.makeRequest({
      		   url: me.personUrl+"/"+id,
      		   method: 'DELETE',
      		   successFunc: function(response,responseText){
      			   store.remove( store.getById( id ) );
      		   }
      	    });    		
     	}	
	},

	onRetrieveCaseloadClick: function( button ){
		var me=this;
		me.preferences.set('SEARCH_GRID_VIEW_TYPE',1);
		me.applyColumns();
		me.getCaseload();
	},
	
	onSearchClick: function(button){
		var me=this;
		var outsideCaseload = !me.getSearchCaseloadCheck().getValue();		
		me.preferences.set('SEARCH_GRID_VIEW_TYPE',0);
		me.applyColumns();		
		if (me.getSearchText().value != undefined && me.getSearchText().value != "")
		{
			me.searchService.search(me.getSearchText().value, outsideCaseload);		
		}else{
			me.searchStore.removeAll();
		}	
	},
	
    loadCaseloadAssignment: function(){
    	var comp = this.formUtils.loadDisplay('mainview', 'caseloadassignment', true, {flex:1});    	
    }
});