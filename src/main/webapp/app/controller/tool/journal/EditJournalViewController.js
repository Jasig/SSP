Ext.define('Ssp.controller.tool.journal.EditJournalViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	authenticatedPerson: 'authenticatedPerson',
        appEventsController: 'appEventsController',
        confidentialityLevelsStore: 'confidentialityLevelsStore',
    	formUtils: 'formRendererUtils',
    	journalEntryService: 'journalEntryService',
    	model: 'currentJournalEntry',
    	personLite: 'personLite'
    },
    config: {
    	containerToLoadInto: 'tools',
    	mainFormToDisplay: 'journal',
    	sessionDetailsEditorDisplay: 'journaltracktree',
    	inited: false
    },

    control: {
    	entryDateField: '#entryDateField',
    	
    	journalTrackCombo: {
    		selector: '#journalTrackCombo',
    		listeners: {
    			select: 'onJournalTrackComboSelect',
        		blur: 'onJournalTrackComboBlur'
    		} 
    	},

    	confidentialityLevelCombo: {
    		selector: '#confidentialityLevelCombo',
    		listeners: {
    			select: 'onConfidentialityLevelComboSelect'
    		} 
    	},    	

    	journalSourceCombo: {
    		selector: '#journalSourceCombo',
    		listeners: {
    			select: 'onJournalSourceComboSelect'
    		} 
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
		me.initForm();	
		return me.callParent(arguments);
    },   
    
	initForm: function(){
		var me=this;
		var id = this.model.get("id");
		var journalTrackId = "";
		if ( me.model.get('journalTrack') != null )
		{
			journalTrackId = me.model.get('journalTrack').id;
		}
		me.getView().getForm().reset();
		me.getView().getForm().loadRecord( this.model );
		me.getConfidentialityLevelCombo().setValue( me.model.getConfidentialityLevelId() );
		me.getJournalSourceCombo().setValue( me.model.get('journalSource').id );
		me.getJournalTrackCombo().setValue( journalTrackId );			
		if ( me.model.get('entryDate') == null)
		{
			me.getEntryDateField().setValue( new Date() );
		}
		
		me.inited=true;
	},    
	
    destroy: function() {
    	var me=this;  	

    	// clear confidentiality level filter
    	me.confidentialityLevelsStore.clearFilter();
    	
        return me.callParent( arguments );
    },	
	
	onSaveClick: function(button) {
		var me = this;
		var record, id, jsonData, url;
		var form = this.getView().getForm();
		var values = form.getValues();
		//var handleSuccess = me.saveSuccess;
		var error = false;
		var journalTrackId="";		
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
    		if ( record.data.journalTrack != null)
    		{
    			journalTrackId = record.data.journalTrack.id;
    		}
			if ( (journalTrackId != null && journalTrackId != "") && record.data.journalEntryDetails.length == 0)
    		{
    			Ext.Msg.alert('SSP Error','You have a Journal Track set in your entry. Please select the associated details for this Journal Entry.');  			
    		}else{

    			// fix date from GMT to UTC
        		record.set('entryDate', me.formUtils.fixDateOffsetWithTime( record.data.entryDate ) );

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
    			    			
    			me.getView().setLoading( true );
    			
    			me.journalEntryService.save( me.personLite.get('id'), jsonData, {
    				success: me.saveSuccess,
    				failure: me.saveFailure,
    				scope: me
    			});
    		}			
		}
	},
	
	saveSuccess: function( r, scope ) {
		var me=scope;
		me.getView().setLoading( false );
		me.displayMain();
	},

	saveFailure: function( response, scope ) {
		var me=scope;
		me.getView().setLoading( false );
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
	
	onJournalTrackComboBlur: function( comp, event, eOpts){
		var me=this;
    	if (comp.getValue() == "")
    	{
     		me.model.set("journalTrack","");
     		me.model.removeAllJournalEntryDetails();
     		me.appEventsController.getApplication().fireEvent('refreshJournalEntryDetails');    			
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