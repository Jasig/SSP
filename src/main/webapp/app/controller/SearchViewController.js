Ext.define('Ssp.controller.SearchViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
        appEventsController: 'appEventsController',
        formUtils: 'formRendererUtils',
        person: 'currentPerson',
        programStatusesStore: 'programStatusesStore',
        studentsStore: 'studentsStore',
    },

    control: {
    	view: {
    		selectionchange: 'onSelectionChange',
			viewready: 'onViewReady'
    	},
    	
    	'addButton': {
    		click: 'onAddClick'
    	}
    },
    
	init: function() {
		var tempProgramStatuses = [{
			"id":"1",
			"name":"Active"
		},
		{
			"id":"2",
			"name":"Inactive"			
		},{
			"id":"3",
		    "name":"Transitioned"
		}];
		// load students
    	this.studentsStore.load();
    	// load program status 
    	this.programStatusesStore.loadData( tempProgramStatuses );

 		return this.callParent(arguments);
    },
    
	onSelectionChange: function(selModel,records,eOpts){ 
		// select the person
		this.person.data = records[0].data;
		this.appEventsController.getApplication().fireEvent('loadPerson');
	},

	onViewReady: function(comp, eobj){
	   	this.appEventsController.assignEvent({eventName: 'editPerson', callBackFunc: this.onEditPerson, scope: this});
		
		comp.getSelectionModel().select(0);
	},

    destroy: function() {
	   	this.appEventsController.removeEvent({eventName: 'editPerson', callBackFunc: this.onEditPerson, scope: this});

        return this.callParent( arguments );
    },	
	
	onAddClick: function(button){
		console.log('SearchViewController->onAddClick');
    	var model = new Ssp.model.Person();
    	this.person.data = model.data;
		this.loadCaseloadAssignment();
	},
	
	onEditPerson: function(){
		console.log('SearchViewController->onEditPerson');
		this.loadCaseloadAssignment();
	},
	
    loadCaseloadAssignment: function(){
    	var comp = this.formUtils.loadDisplay('studentrecord', 'caseloadassignment', true, {flex:1});    	
    }
});