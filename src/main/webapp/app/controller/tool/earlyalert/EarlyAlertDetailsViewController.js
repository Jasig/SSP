Ext.define('Ssp.controller.tool.earlyalert.EarlyAlertDetailsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	campusesStore: 'campusesStore',
    	formUtils: 'formRendererUtils',
        model: 'currentEarlyAlert',
       	reasonsStore: 'earlyAlertReasonsStore',
        suggestionsStore: 'earlyAlertSuggestionsStore',
        selectedSuggestionsStore: 'earlyAlertDetailsSuggestionsStore'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'earlyalert'
    },
    control: {
    	'finishButton': {
    		click: 'onFinishButtonClick'
    	},
    	
    	earlyAlertSuggestionsList: '#earlyAlertSuggestionsList',
    	campusField: '#campusField',
    	earlyAlertReasonField: '#earlyAlertReasonField',
    	statusField: '#statusField',
    	createdByField: '#createdByField',
    	closedByField: '#closedByField'
    },
	init: function() {
		var me=this;
		var selectedSuggestions=[];
		var campus = me.campusesStore.getById( me.model.get('campusId') );
		var reasonId = ((me.model.get('earlyAlertReasonIds') != null)?me.model.get('earlyAlertReasonIds')[0].id : me.model.get('earlyAlertReasonId') );
		var reason = me.reasonsStore.getById( reasonId );
		
		// Reset and populate general fields comments, etc.
		me.getView().getForm().reset();
		me.getView().loadRecord( me.model );
		
		me.getCreatedByField().setValue( me.model.getCreatedByPersonName() );
		
		// Early Alert Status: 'Open', 'Closed'
		me.getStatusField().setValue( ((me.model.get('closedDate'))? 'Closed' : 'Open') );
		
		// Campus
		me.getCampusField().setValue( ((campus)? campus.get('name') : "No Campus Defined") );
		
		// Reason
		me.getEarlyAlertReasonField().setValue( ((reason)? reason.get('name') : "No Reason Defined") );
		
		// Suggestions
		selectedSuggestions = me.formUtils.getSimpleItemsForDisplay( me.suggestionsStore, me.model.get('earlyAlertSuggestionIds'), 'Suggestions' );
		me.selectedSuggestionsStore.removeAll();
		me.selectedSuggestionsStore.loadData( selectedSuggestions );
		return this.callParent(arguments);
    },
    
    onFinishButtonClick: function( button ){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});    	
    }
});