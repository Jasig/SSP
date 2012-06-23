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
		var groupedDetails=[];
		var journalEntryDetails = this.model.get('journalEntryDetails');
		Ext.Array.each(journalEntryDetails,function(item,index){
			var stepName=item.journalStep.name;
    		var details = item.journalStepDetail;
    		Ext.Array.each(details,function(detail,index){
    			detail.group=stepName;
    			groupedDetails.push( detail );
    		},this);
    	},this);
		
		this.store.loadData( groupedDetails );
		
		return this.callParent(arguments);
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