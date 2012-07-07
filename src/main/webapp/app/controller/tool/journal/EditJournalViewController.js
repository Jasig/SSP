Ext.define('Ssp.controller.tool.journal.EditJournalViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
        appEventsController: 'appEventsController',
    	formUtils: 'formRendererUtils',
    	person: 'currentPerson',
    	model: 'currentJournalEntry'
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
		this.url = this.apiProperties.createUrl( this.apiProperties.getItemUrl('personJournalEntry') );
		this.url = this.url.replace('{id}',this.person.get('id'));
		
		this.initForm();
		
		return this.callParent(arguments);
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
    
	onSaveClick: function(button) {
		var me = this;
		var record, id, jsonData, url;
		var form = this.getView().getForm();
		var values = form.getValues();
		var handleSuccess = me.saveSuccess;
		url = this.url;
		record = this.model;
		id = record.get('id');
		
		if (form.isValid())
		{			
    		// if a journal track is selected then validate that the details are set
    		if ( (record.data.journalTrack.id != null && record.data.journalTrack.id != "") && record.data.journalEntryDetails.length == 0)
    		{
    			Ext.Msg.alert('Error','You have a Journal Track set in your entry. Please select the associated details for this Journal Entry.');  			
    		}else{
    			
    			jsonData = record.data;
    			
    			// null out journalTrack.id prop in case
    			if ( record.data.journalTrack == "" )
    			{
    				record.data.journalTrack = null;
    				record.data.journalEntryDetails = null;
    			}
    			
    			console.log( 'EditJournalViewController->onSaveClick' );

    			if (id == "")
    			{	
    				// Prevent tripping null errors
    				// since no entry date will exist
    				delete jsonData.entryDate;
    				
    				// adding
    				this.apiProperties.makeRequest({
    					url: url,
    					method: 'POST',
    					jsonData: jsonData,
    					successFunc: handleSuccess 
    				});
    			}else{
    				// editing
    				this.apiProperties.makeRequest({
    					url: url+id,
    					method: 'PUT',
    					jsonData: jsonData,
    					successFunc: handleSuccess 
    				});
    			}
    		}
		}else{
			Ext.Msg.alert('Error','Please correct the errors in your Journal Entry.');
		}

	},
	
	saveSuccess: function(response, view) {
		me.displayMain();
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