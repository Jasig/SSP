Ext.define('Ssp.controller.tool.journal.JournalToolViewController', {
    extend: 'Deft.mvc.ViewController',	
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
        confidentialityLevelsStore: 'confidentialityLevelsStore',
    	formUtils: 'formRendererUtils',
    	service: 'journalEntryService',
        journalEntriesStore: 'journalEntriesStore',
        journalSourcesStore: 'journalSourcesStore',
    	journalTracksStore: 'journalTracksStore',
    	model: 'currentJournalEntry',
    	personLite: 'personLite'
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
		
		me.getView().setLoading( true );
		
		// clear any existing journal entries
		me.journalEntriesStore.removeAll();
		
		me.service.getAll( me.personLite.get('id'), {
			success: me.getAllJournalEntriesSuccess,
			failure: me.getAllJournalEntriesFailure,
			scope: me
		});

    	// ensure loading of all confidentiality levels in the database
    	me.confidentialityLevelsStore.load({
    		params:{limit:50}
    	});
    	
		me.journalSourcesStore.load();
		me.journalTracksStore.load();
		
		return me.callParent(arguments);
    },
 
    getAllJournalEntriesSuccess: function( r, scope ) {
		var me=scope;
		me.getView().setLoading( false );
    	if (r.rows.length > 0)
    	{
    		me.journalEntriesStore.loadData(r.rows);
    	}
	},

	getAllJournalEntriesFailure: function( response, scope ) {
		var me=scope;
		me.getView().setLoading( false );
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
        var me=this;
    	var message = 'You are about to delete a Journal Entry. Would you like to continue?';
    	var model = me.model;
        if (model.get('id') != "") 
        {
           Ext.Msg.confirm({
   		     title:'Delete Journal Entry?',
   		     msg: message,
   		     buttons: Ext.Msg.YESNO,
   		     fn: me.deleteJournalEntry,
   		     scope: me
   		   });
        }else{
     	   Ext.Msg.alert('SSP Error', 'Unable to delete Journal Entry.'); 
        }
     },
     
     deleteJournalEntry: function( btnId ){
     	var me=this;
    	var store = me.journalEntriesStore;
     	var id = me.model.get('id');
     	if (btnId=="yes")
     	{
     		me.getView().setLoading( true );
     		me.service.destroy( me.personLite.get('id'), id, {
     			success: me.destroyJournalEntrySuccess,
     			failure: me.destroyJournalEntryFailure,
     			scope: me
     		});  		
     	}
     },    

     destroyJournalEntrySuccess: function( r, id, scope ) {
 		var me=scope;
 		var store = me.journalEntriesStore;
 		me.getView().setLoading( false );
 		store.remove( store.getById( id ) );
 	},

 	destroyJournalEntryFailure: function( response, scope ) {
 		var me=scope;
 		me.getView().setLoading( false );
 	},      
     
    loadEditor: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});    	
    }
});