Ext.define('Ssp.controller.SearchViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
        appEventsController: 'appEventsController',
        person: 'currentPerson',
        programStatusesStore: 'programStatusesStore',
        studentsStore: 'studentsStore',
    },

    control: {
    	view: {
    		selectionchange: 'onSelectionChange',
			viewready: 'onViewReady'
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
		comp.getSelectionModel().select(0);
	}
});