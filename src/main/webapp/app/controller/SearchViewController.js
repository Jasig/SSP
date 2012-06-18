Ext.define('Ssp.controller.SearchViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
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
		var me=this;

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

		/*
		var successFunc = function(response,view){
	    	var r = Ext.decode(response.responseText);
	    	if (r.rows.length > 0)
	    	{
	    		me.studentsStore.loadData(r.rows);
	    	}
		};

		me.personSearchUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personSearch') );
		
		me.apiProperties.makeRequest({
			url: me.personSearchUrl,
			method: 'GET',
			successFunc: successFunc
		});
		*/
		
		// load students
    	me.studentsStore.load();
    	
    	// load program status 
    	me.programStatusesStore.loadData( tempProgramStatuses );

 		return me.callParent(arguments);
    },
    
	onSelectionChange: function(selModel,records,eOpts){ 
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