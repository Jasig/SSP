Ext.define('Ssp.controller.tool.journal.DisplayDetailsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	appEventsController: 'appEventsController',
    	model: 'currentJournalEntry',
    	store: 'journalEntryDetailsStore'
    },   
	
    control: {
		view: {
			viewready: 'onViewReady'
		}
	},
	
    init: function() {
    	var me=this;
    	console.log( 'DisplayDetailsViewController->init' );
		me.store.loadData( me.model.getGroupedDetails() );		
		return me.callParent( arguments );
    },
    
    onViewReady: function(){
    	this.appEventsController.assignEvent({eventName: 'refreshJournalEntryDetails', callBackFunc: this.onRefreshJournalEntryDetails, scope: this});
    },
    
    destroy: function(){
    	this.appEventsController.removeEvent({eventName: 'refreshJournalEntryDetails', callBackFunc: this.onRefreshJournalEntryDetails, scope: this});    	
    },
    
    onRefreshJournalEntryDetails: function(){
    	this.init();
    }
});