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
    	personSearchUrl: null
    },
    
    control: {
    	searchText: '#searchText',
    	
    	view: {
    		selectionchange: 'onSelectionChange',
			viewready: 'onViewReady'
    	},
    	
    	'addButton': {
    		click: 'onAddClick'
    	},
    	
    	'editButton': {
    		click: 'onEditClick'
    	},
    	
    	'deleteButton': {
    		click: 'onDeleteClick'
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
		this.appEventsController.assignEvent({eventName: 'deletePerson', callBackFunc: this.onDeletePerson, scope: this});
		this.appEventsController.assignEvent({eventName: 'collapseStudentRecord', callBackFunc: this.onCollapseStudentRecord, scope: this});
	   	this.appEventsController.assignEvent({eventName: 'expandStudentRecord', callBackFunc: this.onExpandStudentRecord, scope: this});
	   	
		comp.getSelectionModel().select(0);
	},

    destroy: function() {
	   	this.appEventsController.removeEvent({eventName: 'addPerson', callBackFunc: this.onAddPerson, scope: this});
		this.appEventsController.removeEvent({eventName: 'editPerson', callBackFunc: this.onEditPerson, scope: this});
		this.appEventsController.removeEvent({eventName: 'deletePerson', callBackFunc: this.onDeletePerson, scope: this});
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
	    grid.getStore().load();		
	},
    
	onAddClick: function( button ){
    	var model = new Ssp.model.Person();
    	this.person.data = model.data;
		this.loadCaseloadAssignment();
	},
	
	onEditClick: function( button ){
	    var records = this.getView().getSelectionModel().getSelection();
		if (records.length>0)
		{
		   this.person.data=records[0].data;
		   this.loadCaseloadAssignment();
		   
		}
	},
	
	onDeleteClick: function( button ){
		Ext.Msg.alert('Attention','This feature is not available yet.');
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
    	var comp = this.formUtils.loadDisplay('studentrecord', 'caseloadassignment', true, {flex:1});    	
    }
});