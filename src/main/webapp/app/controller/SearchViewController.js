Ext.define('Ssp.controller.SearchViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
        appEventsController: 'appEventsController',
        columnRendererUtils: 'columnRendererUtils',
        formUtils: 'formRendererUtils',
        person: 'currentPerson',
        preferences: 'preferences',
        programStatusesStore: 'programStatusesStore',
        studentsStore: 'studentsStore',
        sspConfig: 'sspConfig'
    },

    config: {
    	personUrl: null,
    	personSearchUrl: null
    },
    
    control: {
    	searchText: '#searchText',
    	searchCaseloadCheck: '#searchCaseloadCheck',
    	
    	view: {
    		selectionchange: 'onSelectionChange',
			viewready: 'onViewReady'
    	},
    	
    	'searchButton': {
    		click: 'onSearchClick'
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
		me.personSearchUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personSearch') );		
		
		me.initializeStudents();

 		return me.callParent(arguments);
    },
    
	onSelectionChange: function(selModel,records,eOpts){ 
		if (records.length > 0)
		{
			this.person.data = records[0].data;
			this.appEventsController.getApplication().fireEvent('loadPerson');			
		}
	},

	onViewReady: function(comp, eobj){
		this.appEventsController.assignEvent({eventName: 'collapseStudentRecord', callBackFunc: this.onCollapseStudentRecord, scope: this});
	   	this.appEventsController.assignEvent({eventName: 'expandStudentRecord', callBackFunc: this.onExpandStudentRecord, scope: this});
	   	
		comp.getSelectionModel().select(0);
	},

    destroy: function() {
    	this.appEventsController.removeEvent({eventName: 'collapseStudentRecord', callBackFunc: this.onCollapseStudentRecord, scope: this});
	   	this.appEventsController.removeEvent({eventName: 'expandStudentRecord', callBackFunc: this.onExpandStudentRecord, scope: this});

        return this.callParent( arguments );
    },	

    initializeStudents: function(){
    	this.studentsStore.load();
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
		var store = me.studentsStore;
		var studentIdAlias = me.sspConfig.get('studentIdAlias');
		if (me.preferences.get('SEARCH_GRID_VIEW_TYPE')==1){
			columns = [
    	              { header: 'First', dataIndex: 'firstName', flex: 1 },		        
    	              { header: 'MI', dataIndex: 'middleInitial', flex: .2},
    	              { header: 'Last', dataIndex: 'lastName', flex: 1},
    	              { header: 'Type', dataIndex: 'studentType', renderer: me.columnRendererUtils.renderStudentType, flex: .2},
    	              { header: studentIdAlias, dataIndex: 'schoolId', flex: 1},
    	              { header: 'Alerts', dataIndex: 'alerts', flex: .2}
    	              ];			
		}else{
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
    	var model = new Ssp.model.Person();
    	this.person.data = model.data;
		this.loadCaseloadAssignment();
	},
	
	onEditPerson: function(){
	    var records = this.getView().getSelectionModel().getSelection();
		if (records.length>0)
		{
		   this.person.data=records[0].data;
		   this.loadCaseloadAssignment();
		}else{
			Ext.Msg.alert('Error','Please select a student to edit.');
		}
	},

	onDeletePerson: function(){
	    var records = this.getView().getSelectionModel().getSelection();
		if (records.length>0)
		{
			this.person.data=records[0].data;
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
		var store = me.studentsStore;
     	var id = me.person.get('id');
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
	
	onSearchClick: function(button){
		var me=this;
		var outsideCaseload = !me.getSearchCaseloadCheck().getValue();
		var successFunc = function(response,view){
	    	var r = Ext.decode(response.responseText);
	    	if (r.rows.length > 0)
	    	{
	    		me.studentsStore.loadData(r.rows);
	    	}else{
	    		Ext.Msg.alert('Attention','No students match your search. Try a different search value.');
	    	}
		};
		
		if (me.getSearchText().value != undefined && me.getSearchText().value != "")
		{
			
			me.apiProperties.makeRequest({
				url: me.personSearchUrl+'/?outsideCaseload='+outsideCaseload+'&searchTerm='+me.getSearchText().value,
				method: 'GET',
				successFunc: successFunc
			});			
		}else{
			me.initializeStudents();
		}	
	},
	
    loadCaseloadAssignment: function(){
    	var comp = this.formUtils.loadDisplay('mainview', 'caseloadassignment', true, {flex:1});    	
    }
});