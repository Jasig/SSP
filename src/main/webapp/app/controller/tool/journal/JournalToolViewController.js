Ext.define('Ssp.controller.tool.journal.JournalToolViewController', {
    extend: 'Deft.mvc.ViewController',	
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
    	formUtils: 'formRendererUtils',
    	person: 'currentPerson',
    	model: 'currentJournalEntry',
        journalEntriesStore: 'journalEntriesStore',
        journalSourcesStore: 'journalSourcesStore',
    	journalTracksStore: 'journalTracksStore',
        confidentialityLevelsStore: 'confidentialityLevelsStore'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'editjournal',
    	personJournalUrl: ''
    },
    control: {
    	view: {
    		viewready: 'onViewReady'
    	},
    	
    	'addButton': {
			click: 'onAddClick'
		},
		
		'viewHistoryButton': {
			click: 'onViewHistoryClick'
		}
	},
    init: function() {
		var me = this;
		var personId = this.person.get('id');
		var successFunc = function(response,view){
	    	var r = Ext.decode(response.responseText);
	    	if (r.rows.length > 0)
	    	{
	    		me.journalEntriesStore.loadData(r.rows);
	    	}
		};

    	this.confidentialityLevelsStore.load();
		this.journalSourcesStore.load();
		this.journalTracksStore.load();
		
		this.personJournalUrl = this.apiProperties.createUrl( this.apiProperties.getItemUrl('personJournalEntry') );
		this.personJournalUrl = this.personJournalUrl.replace('{id}',personId);		

		this.apiProperties.makeRequest({
			url: this.personJournalUrl,
			method: 'GET',
			successFunc: successFunc
		});
    	
    	var json = {"success":true,"results":0,"rows":[]};
    	var rows = [{
    		"id" : "240e97c0-7fe5-11e1-b0c4-0800200c9a66",
    		"comment":"testing",
    		"confidentialityLevel":{"id":"afe3e3e6-87fa-11e1-91b2-0026b9e7ff4c","name":"EVERYONE"},
    		"createdBy":{"id":"58ba5ee3-734e-4ae9-b9c5-943774b4de41","firstName":"System","lastName":"Administrator"},
    		"modifiedBy":{"id":"58ba5ee3-734e-4ae9-b9c5-943774b4de41","firstName":"System","lastName":"Administrator"},
    		"createdDate":1331269200000,
    		"entryDate":1331269200000,
    		"journalSource":{"id": "b2d07973-5056-a51a-8073-1d3641ce507f", "name":"appointment" },
    		"journalTrack":{"id":"b2d07a7d-5056-a51a-80a8-96ae5188a188","name":"ILP"},
    		"journalEntryDetails":
    		[{"journalStep" :
          	 {"id" : "0a080114-3799-156f-8137-99220ac10000",
				 "name" : "Two"},
	           "journalStepDetails" :
	          [{"id" : "0a080114-3799-156f-8137-9926abc30003",
	            "name" : "Action Plan Developed"}]
			 }]
    	}];
    	json.rows = rows;

		this.journalEntriesStore.loadData(json.rows);
		
		return this.callParent(arguments);
    },
 
    onViewReady: function(comp, obj){
    	this.appEventsController.assignEvent({eventName: 'editJournalEntry', callBackFunc: this.editJournalEntry, scope: this});
    	this.appEventsController.assignEvent({eventName: 'deleteJournalEntry', callBackFunc: this.deleteConfirmation, scope: this});
    },    
 
    destroy: function() {
    	this.appEventsController.removeEvent({eventName: 'editJournalEntry', callBackFunc: this.editJournalEntry, scope: this});
    	this.appEventsController.removeEvent({eventName: 'deleteJournalEntry', callBackFunc: this.deleteConfirmation, scope: this});

        return this.callParent( arguments );
    },    
    
    onAddClick: function(button){
    	var je = new Ssp.model.tool.journal.JournalEntry();
    	this.model.data = je.data;
    	this.loadEditor();
    },
    
    editJournalEntry: function(){
    	this.loadEditor();
    },

    onViewHistoryClick: function(button){
	 this.appEventsController.getApplication().fireEvent("viewHistory");
    },
 
    deleteConfirmation: function() {
        var message = 'You are about to delete a Journal Entry. Would you like to continue?';
    	var model = this.model;
        if (model.get('id') != "") 
        {
           Ext.Msg.confirm({
   		     title:'Delete Journal Entry?',
   		     msg: message,
   		     buttons: Ext.Msg.YESNO,
   		     fn: this.deleteJournalEntry,
   		     scope: this
   		   });
        }else{
     	   Ext.Msg.alert('SSP Error', 'Unable to delete Journal Entry.'); 
        }
     },
     
     deleteJournalEntry: function( btnId ){
     	var store = this.journalEntriesStore;
     	var id = this.model.get('id');
     	if (btnId=="yes")
     	{
     		this.apiProperties.makeRequest({
      		   url: this.personJournalUrl+id,
      		   method: 'DELETE',
      		   successFunc: function(response,responseText){
      			   store.remove( store.getById( id ) );
      		   }
      	    });   		
     	}
     },    
    
    loadEditor: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});    	
    }
});