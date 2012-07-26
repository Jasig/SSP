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
    	statusField: '#statusField'
    },
	init: function() {
		var me=this;
		var selectedSuggestions=[];
		var campus = me.campusesStore.getById( me.model.get('campusId') );
		var reason = me.reasonsStore.getById( me.model.get('earlyAlertReasonId') );
		me.getView().getForm().reset();
		me.getView().loadRecord( me.model );
		me.getStatusField().setValue( ((me.model.get('closedDate'))? 'Closed' : 'Open') );
		me.getCampusField().setValue( ((campus)? campus.get('name') : "No Campus Defined") );
		me.getEarlyAlertReasonField().setValue( ((reason)? reason.get('name') : "No Reason Defined") );
		Ext.Array.each( me.model.get('earlyAlertSuggestionIds'), function(id,index){
			var suggestion = {name: me.suggestionsStore.getById(id).get('name')};
			selectedSuggestions.push( suggestion );
		});
		me.selectedSuggestionsStore.removeAll();
		if (selectedSuggestions.length==0)
			selectedSuggestions.push({name:'No Suggestions'});
		me.selectedSuggestionsStore.loadData(selectedSuggestions);
		return this.callParent(arguments);
    },
    
    onFinishButtonClick: function( button ){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});    	
    }
});