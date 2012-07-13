Ext.define('Ssp.controller.tool.journal.JournalToolViewController', {
    extend: 'Deft.mvc.ViewController',	
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
        confidentialityLevelsStore: 'confidentialityLevelsStore',
    	formUtils: 'formRendererUtils',
        journalEntriesStore: 'journalEntriesStore',
        journalSourcesStore: 'journalSourcesStore',
    	journalTracksStore: 'journalTracksStore',
    	model: 'currentJournalEntry',
    	person: 'currentPerson'
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
		}
	},
    init: function() {
		var me = this;
		var personId = me.person.get('id');
		var successFunc = function(response,view){
	    	var r = Ext.decode(response.responseText);
	    	
	    	if (r.rows.length > 0)
	    	{
	    		me.journalEntriesStore.loadData(r.rows);
	    	}

	    	// hide the loader
	    	me.getView().setLoading( false );	    	
		};

		// clear any existing journal entries
		me.journalEntriesStore.removeAll();		

    	// ensure loading of all confidentiality levels in the database
    	me.confidentialityLevelsStore.load({
    		params:{limit:50}
    	});
    	
		me.journalSourcesStore.load();
		me.journalTracksStore.load();
		
		me.personJournalUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personJournalEntry') );
		me.personJournalUrl = me.personJournalUrl.replace('{id}',personId);		

		// display loader
		me.getView().setLoading( true );		
		
		me.apiProperties.makeRequest({
			url: me.personJournalUrl,
			method: 'GET',
			successFunc: successFunc
		});
    	
		return me.callParent(arguments);
    },
 
    onViewReady: function(comp, obj){
    	this.appEventsController.assignEvent({eventName: 'editJournalEntry', callBackFunc: this.editJournalEntry, scope: this});
    	this.appEventsController.assignEvent({eventName: 'deleteJournalEntry', callBackFunc: this.deleteConfirmation, scope: this});
    },    
 
    destroy: function() {
    	var me=this;
    	
    	me.appEventsController.removeEvent({eventName: 'editJournalEntry', callBackFunc: me.editJournalEntry, scope: me});
    	me.appEventsController.removeEvent({eventName: 'deleteJournalEntry', callBackFunc: me.deleteConfirmation, scope: me});

        return me.callParent( arguments );
    },    
    
    onAddClick: function(button){
    	var je = new Ssp.model.tool.journal.JournalEntry();
    	this.model.data = je.data;
    	this.loadEditor();
    },
    
    editJournalEntry: function(){
    	this.loadEditor();
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
      		   url: this.personJournalUrl+"/"+id,
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