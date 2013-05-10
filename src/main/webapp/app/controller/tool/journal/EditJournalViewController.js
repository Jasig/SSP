/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
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
    	personLite: 'personLite',
    	util: 'util'
    },
    config: {
    	containerToLoadInto: 'tools',
    	mainFormToDisplay: 'journal',
    	sessionDetailsEditorDisplay: 'journaltracktree',
    	inited: false
    },

    control: {
    	entryDateField: {
			selector: '#entryDateField',
			listeners: {
				select: 'onEntryDateSelect'
			}
		},
    	
    	removeJournalTrackButton: {
    		selector: '#removeJournalTrackButton',
    		listeners: {
    			click: 'onRemoveJournalTrackButtonClick'
    		}
    	},
    	
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
			me.getEntryDateField().setLoading(true);
			me.util.getCurrentServerDate({
				success: function(date) {
					me.getEntryDateField().setValue(date);
					me.model.set('entryDate', me.getEntryDateField().getValue());
					me.getEntryDateField().setLoading(false);
				},
				failure: function() {
					// probably not what you want, but a reasonable default
					// given that most users will be in the server's timezone
					me.getEntryDateField().setValue( new Date() );
					me.model.set('entryDate', me.getEntryDateField().getValue());
					me.getEntryDateField().setLoading(false);
				},
				scope: me
			});

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
				var origEntryDate = record.data.entryDate;
				record.data.entryDate = me.formUtils.toJSONStringifiableDate( record.data.entryDate );

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
    				success: function(r, scope) {
						record.data.entryDate = origEntryDate;
						scope.saveSuccess(r, scope);
					},
					failure: function(r, scope) {
						record.data.entryDate = origEntryDate;
						me.saveFailure(r, scope);
					},
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

	onEntryDateSelect: function(comp, newValue, eOpts) {
		var me = this;
		me.model.set('entryDate', newValue);
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
		var me=this;
		if (records.length > 0)
    	{
    		me.model.set('journalTrack',{"id": records[0].get('id')});
    		
    		// the inited property prevents the
    		// Journal Entry Details from clearing
    		// when the ViewController loads, so the details only 
    		// clear when a new journal track is selected
    		// because the init for the view sets the combo
    		if (me.inited==true)
    		{
    	   		me.model.removeAllJournalEntryDetails();
    			me.appEventsController.getApplication().fireEvent('refreshJournalEntryDetails');    			
    		}
     	}else{
     		me.removeJournalTrackAndSessionDetails();
     	}
	},
	
	onJournalTrackComboBlur: function( comp, event, eOpts){
		var me=this;
    	if (comp.getValue() == "")
    	{
     		me.removeJournalTrackAndSessionDetails();
     	}		
	},
	
	removeJournalTrackAndSessionDetails: function(){
 		var me=this;
		me.model.set("journalTrack","");
 		me.model.removeAllJournalEntryDetails();
 		me.appEventsController.getApplication().fireEvent('refreshJournalEntryDetails');
	},
	
	onRemoveJournalTrackButtonClick: function( button ){
		var me=this;
		var combo = me.getJournalTrackCombo();
		combo.clearValue();
		combo.fireEvent('select',{
			combo: combo,
			records: [],
			eOpts: {}
		});
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