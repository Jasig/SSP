Ext.define('Ssp.controller.tool.journal.EditJournalViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	authenticatedPerson: 'authenticatedPerson',
        appEventsController: 'appEventsController',
        confidentialityLevelsStore: 'confidentialityLevelsStore',
    	formUtils: 'formRendererUtils',
    	model: 'currentJournalEntry',
    	person: 'currentPerson'
    },
    config: {
    	containerToLoadInto: 'tools',
    	mainFormToDisplay: 'journal',
    	sessionDetailsEditorDisplay: 'journaltracktree',
    	url: '',
    	inited: false
    },

    control: {
    	'journalTrackCombo': {
    		select: 'onJournalTrackComboSelect'
    	},
    	
    	'confidentialityLevelCombo': {
    		select: 'onConfidentialityLevelComboSelect'
    	},
    	
    	'journalSourceCombo': {
    		select: 'onJournalSourceComboSelect'
    	},
    	
    	'commentText': {
    		change: 'onCommentChange'
    	},
    	
    	'saveButton': {
			click: 'onSaveClick'
		},
		
		'cancelButton': {
			click: 'onCancelClick'
		},
		
		'addSessionDetailsButton': {
			click: 'onAddSessionDetailsClick'
		}
    },
    
	init: function() {
		var me=this;

		// apply confidentiality level filter
		me.authenticatedPerson.applyConfidentialityLevelsFilter( me.confidentialityLevelsStore );		
		
		me.url = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personJournalEntry') );
		me.url = me.url.replace('{id}',me.person.get('id'));
		
		me.initForm();
		
		return me.callParent(arguments);
    },   
    
	initForm: function(){
		var id = this.model.get("id");
		this.getView().getForm().reset();
		this.getView().getForm().loadRecord( this.model );
		Ext.ComponentQuery.query('#confidentialityLevelCombo')[0].setValue( this.model.getConfidentialityLevelId() );
		Ext.ComponentQuery.query('#journalSourceCombo')[0].setValue( this.model.get('journalSource').id );
		Ext.ComponentQuery.query('#journalTrackCombo')[0].setValue( this.model.get('journalTrack').id );			

		this.inited=true;
	},    
	
    destroy: function() {
    	var me=this;  	

    	// clear confidentiality level filter
    	me.confidentialityLevelsStore.clearFilter();
    	
        return me.callParent( arguments );
    },	
	
	onSaveClick: function(button) {
		console.log( 'EditJournalViewController->onSaveClick' );
		var me = this;
		var record, id, jsonData, url;
		var form = this.getView().getForm();
		var values = form.getValues();
		var handleSuccess = me.saveSuccess;
		var error = false;
		url = this.url;
		record = this.model;
		id = record.get('id');
		
		// ensure all required fields are supplied
		if ( !form.isValid() )
		{	
			error = true;
			Ext.Msg.alert('Error','Please correct the errors in your Journal Entry.');
		}
		
		// ensure a comment or journal track are supplied
		if ( record.get('comment') == "" && (record.data.journalTrack.id == null || record.data.journalTrack.id == "") )
		{
			error = true;
			Ext.Msg.alert('Error','You are required to supply a Comment or Journal Track Details for a Journal Entry.');			
		}
		
		if (error == false)
		{
    		// if a journal track is selected then validate that the details are set
    		if ( (record.data.journalTrack.id != null && record.data.journalTrack.id != "") && record.data.journalEntryDetails.length == 0)
    		{
    			Ext.Msg.alert('Error','You have a Journal Track set in your entry. Please select the associated details for this Journal Entry.');  			
    		}else{
    			
    			jsonData = record.data;
    			    			
    			// null out journalTrack.id prop to prevent failure
    			// from an empty string on null field
    			if ( jsonData.journalTrack == "" )
    			{
    				jsonData.journalTrack = null;
    				jsonData.journalEntryDetails = null;
    			}
    			
    			// clean the group property from the journal
    			// entry details. It was only used for display
    			// of the details.
    			if ( jsonData.journalEntryDetails != null )
    			{
    				jsonData.journalEntryDetails = record.clearGroupedDetails( jsonData.journalEntryDetails );
    			}
    			
    			console.log(jsonData.entryDate);
    			
    			// Fix entry date to represent appropriate date and time
    			jsonData.entryDate = me.formUtils.fixDateOffsetWithTime( jsonData.entryDate );
    			
    			if (id == "")
    			{	
    				// adding
    				this.apiProperties.makeRequest({
    					url: url,
    					method: 'POST',
    					jsonData: jsonData,
    					successFunc: handleSuccess,
    					scope: me
    				});
    			}else{
    				// editing
    				this.apiProperties.makeRequest({
    					url: url+"/"+id,
    					method: 'PUT',
    					jsonData: jsonData,
    					successFunc: handleSuccess,
    					scope: me
    				});
    			}
    		}			
		}
	},
	
	saveSuccess: function(response, view) {
		this.displayMain();
	},
	
	onCancelClick: function(button){
		this.displayMain();
	},

	onConfidentialityLevelComboSelect: function(comp, records, eOpts){
    	if (records.length > 0)
    	{
    		this.model.set('confidentialityLevel',{id: records[0].get('id')});
     	}
	},	
	
	onJournalSourceComboSelect: function(comp, records, eOpts){
    	if (records.length > 0)
    	{
    		this.model.set('journalSource',{id: records[0].get('id')});
     	}
	},	
	
	onJournalTrackComboSelect: function(comp, records, eOpts){
    	if (records.length > 0)
    	{
    		this.model.set('journalTrack',{"id": records[0].get('id')});
    		
    		// the inited property prevents the
    		// Journal Entry Details from clearing
    		// when the ViewController loads, so the details only 
    		// clear when a new journal track is selected
    		// because the init for the view sets the combo
    		if (this.inited==true)
    		{
    	   		this.model.removeAllJournalEntryDetails();
    			this.appEventsController.getApplication().fireEvent('refreshJournalEntryDetails');    			
    		}
     	}
	},
	
	onCommentChange: function(comp, newValue, oldValue, eOpts){
		this.model.set('comment',newValue);
	},
	
	onAddSessionDetailsClick: function( button ){
		if( this.model.get('journalTrack') != null && this.model.get('journalTrack') != "")
		{
			this.displaySessionDetails();
		}else{
			Ext.Msg.alert('Error', 'A Journal Track is required before selecting details.')
		}
	},
	
	displayMain: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getMainFormToDisplay(), true, {});
	},
	
	displaySessionDetails: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getSessionDetailsEditorDisplay(), true, {});
	}
});