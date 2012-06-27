Ext.define('Ssp.controller.SearchViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
        appEventsController: 'appEventsController',
        columnRendererUtils: 'columnRendererUtils',
        formUtils: 'formRendererUtils',
        person: 'currentPerson',
        programStatusesStore: 'programStatusesStore',
        studentsStore: 'studentsStore',
    },

    config: {
    	personUrl: null,
    	personSearchUrl: null
    },
    
    control: {
    	searchText: '#searchText',
    	
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
    	}	
    },
    
	init: function() {
		var me=this;
		me.personUrl =  me.apiProperties.createUrl( me.apiProperties.getItemUrl('person') );		
		me.personSearchUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personSearch') );		
		
		// load students
    	me.studentsStore.load();

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
		this.appEventsController.assignEvent({eventName: 'addPerson', callBackFunc: this.onAddPerson, scope: this});
		this.appEventsController.assignEvent({eventName: 'editPerson', callBackFunc: this.onEditPerson, scope: this});
		this.appEventsController.assignEvent({eventName: 'deletePerson', callBackFunc: this.deleteConfirmation, scope: this});

		this.appEventsController.assignEvent({eventName: 'collapseStudentRecord', callBackFunc: this.onCollapseStudentRecord, scope: this});
	   	this.appEventsController.assignEvent({eventName: 'expandStudentRecord', callBackFunc: this.onExpandStudentRecord, scope: this});
	   	
		comp.getSelectionModel().select(0);
	},

    destroy: function() {
		this.appEventsController.removeEvent({eventName: 'addPerson', callBackFunc: this.onAddPerson, scope: this});
		this.appEventsController.removeEvent({eventName: 'editPerson', callBackFunc: this.onEditPerson, scope: this});
		this.appEventsController.removeEvent({eventName: 'deletePerson', callBackFunc: this.deleteConfirmation, scope: this});

    	this.appEventsController.removeEvent({eventName: 'collapseStudentRecord', callBackFunc: this.onCollapseStudentRecord, scope: this});
	   	this.appEventsController.removeEvent({eventName: 'expandStudentRecord', callBackFunc: this.onExpandStudentRecord, scope: this});

        return this.callParent( arguments );
    },	

    onCollapseStudentRecord: function(){
    	console.log('SearchViewController->onCollapseStudentRecord');
	},
	
	onExpandStudentRecord: function(){
		console.log('SearchViewController->onExpandStudentRecord');
	},  

	onDisplayPhotoClick: function( button ){
		this.applyColumns('search');
	},
	
	onDisplayListClick: function( button ){
		this.applyColumns('caseload');
	},
	
	applyColumns: function( display ){
		var grid = this.getView();
		var store = this.studentsStore;
		if (display=='caseload'){
			columns = [
    	              { header: 'First', dataIndex: 'firstName', flex: 1 },		        
    	              { header: 'MI', dataIndex: 'middleInitial', flex: .2},
    	              { header: 'Last', dataIndex: 'lastName', flex: 1},
    	              { header: 'Type', dataIndex: 'studentType', flex: 1},
    	              { header: 'Tartan Id', dataIndex: 'schoolId', flex: 1},
    	              { header: 'Alerts', dataIndex: 'alerts', flex: 1}
    	              ];			
		}else{
			columns = [
    	              { header: "Photo", dataIndex: 'photoUrl', renderer: this.columnRendererUtils.renderPhotoIcon, flex: 50 },		        
    	              { text: 'Name', dataIndex: 'lastName', renderer: this.columnRendererUtils.renderStudentDetails, flex: 50},
    	              ];		
		}
		
		this.formUtils.reconfigureGridPanel(grid, store, columns);		
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
      		   url: me.personUrl+id,
      		   method: 'DELETE',
      		   successFunc: function(response,responseText){
      			   store.remove( store.getById( id ) );
      		   }
      	    });    		
     	}	
	},
	
	onSearchClick: function(button){
		var me=this;
		var successFunc = function(response,view){
	    	var r = Ext.decode(response.responseText);
	    	if (r.rows.length > 0)
	    	{
	    		me.studentsStore.loadData(r.rows);
	    	}
		};
		
		if (me.getSearchText().value != undefined && me.getSearchText().value != "")
		{
			me.apiProperties.makeRequest({
				url: me.personSearchUrl,
				json: { outsideCaseload : true, searchTerm : me.getSearchText().value},
				method: 'GET',
				successFunc: successFunc
			});			
		}else{
			Ext.Msg.alert('Attention','Please provide a value to search such as Student Id,  ( First Name, Last Name ) or any portion of the name.');
		}	
	},
	
    loadCaseloadAssignment: function(){
    	var comp = this.formUtils.loadDisplay('mainview', 'caseloadassignment', true, {flex:1});    	
    }
});