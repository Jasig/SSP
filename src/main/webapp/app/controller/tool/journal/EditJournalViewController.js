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
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson',
        appEventsController: 'appEventsController',
        confidentialityLevelsStore: 'confidentialityLevelsAllUnpagedStore',
        formUtils: 'formRendererUtils',
        journalEntryService: 'journalEntryService',
        journalTracksStore: 'journalTracksAllUnpagedStore',
		journalSourcesStore: 'journalSourcesAllUnpagedStore',
        model: 'currentJournalEntry',
        personLite: 'personLite',
        util: 'util'
    },
    config: {
        containerToLoadInto: 'editjournal',
        containerToLoadIntoTools: 'tools',
        mainFormToDisplay: 'journal',
        sessionDetailsEditorDisplay: 'journaltracktree',
        journalList: 'journallist',
        inited: false
    },
    
    control: {
        entryDateField: {
            selector: '#entryDateField'           
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
            selector: '#confidentialityLevelCombo'          
        },
        
        journalSourceCombo: {
            selector: '#journalSourceCombo'           
        },
             
        commentText: '#commentTxt',
        
        journalTrackTree: '#journalTrackTree'
    },
    
    init: function() {
        var me = this;
        
        me.appEventsController.assignEvent({
            eventName: 'saveJournal',
            callBackFunc: me.onSaveJournal,
            scope: me
        });
		
		me.appEventsController.assignEvent({
			eventName: 'resetJournal',
			callBackFunc: me.onResetJournal,
			scope: me
		});

        return me.callParent(arguments);
    },

    // Currently being called explicitly from the top-level tool controller
    // (JournalToolViewController) when a JournalEntry is selected
    initForm: function() {
        var me = this;
		me.getView().setLoading(false);
        var id = me.model.get("id");
        var journalTrackId = "";
      
		me.journalTracksStore.clearFilter(true);
		me.journalSourcesStore.clearFilter(true);
        
        if ( me.model.get('journalTrack') != null ) {
            journalTrackId = me.model.get('journalTrack').id;
        }
        else {
            me.removeJournalTrackAndSessionDetails();
        }
        
        me.getView().getForm().reset();
        me.getView().getForm().loadRecord(me.model);

        me.confidentialityLevelsStore.clearFilter(true);
		me.formUtils.applyAssociativeStoreFilter(me.confidentialityLevelsStore);
        var confLevelId = me.model.getConfidentialityLevelId();
        me.getConfidentialityLevelCombo().setValue(confLevelId);

        var journalSourceId = me.model.get('journalSource').id;
        me.formUtils.applyAssociativeStoreFilter(me.journalSourcesStore,journalSourceId);
        me.getJournalSourceCombo().setValue(journalSourceId);

        me.formUtils.applyAssociativeStoreFilter(me.journalTracksStore,journalTrackId);
        me.getJournalTrackCombo().setValue(journalTrackId);

        if ( me.model.get('entryDate') == null ) {
            me.getEntryDateField().setLoading(true);
            me.util.getCurrentServerDate({
                success: function(date){
                    me.getEntryDateField().setValue(date);
                    me.model.set('entryDate', me.getEntryDateField().getValue());
                    me.getEntryDateField().setLoading(false);
                },
                failure: function(){
                    // probably not what you want, but a reasonable default
                    // given that most users will be in the server's timezone
                    me.getEntryDateField().setValue(new Date());
                    me.model.set('entryDate', me.getEntryDateField().getValue());
                    me.getEntryDateField().setLoading(false);
                },
                scope: me
            });            
        }
        me.inited = true;
    },
    
    destroy: function() {
        var me = this;		
        me.journalTracksStore.clearFilter(true);
        me.journalSourcesStore.clearFilter(true);
        me.confidentialityLevelsStore.clearFilter(true);

        me.appEventsController.removeEvent({
            eventName: 'saveJournal',
            callBackFunc: me.onSaveJournal,
            scope: me
        });

        me.appEventsController.removeEvent({
            eventName: 'resetJournal',
            callBackFunc: me.onResetJournal,
            scope: me
        });
        
        return me.callParent(arguments);
    },
    
    onSaveJournal: function(response) {
        var me = this;        
        me.save();
    },
	
	onResetJournal: function() {
		var me = this;	
		me.removeJournalTrackAndSessionDetails();
		me.initForm();
	},
    
    save: function() {
        var me = this;
        var record = me.model;
		var id = record.get('id');		
        var form = me.getView().getForm();  
		var error = false; 
		var jsonData;		
        
		//get data from form
		var trackValue = form.findField('journalTrackId').getValue(); 
		var confidentialityValue = form.findField('confidentialityLevelId').getValue();
		var sourceValue = form.findField('journalSourceId').getValue();
		var comment = Ext.ComponentQuery.query('#commentTxt')[0].getValue();
        var jTT = Ext.ComponentQuery.query('#journalTrackTree')[0];	
		               
         // ensure all required fields are supplied
        if ( (!form.isValid()) || (!confidentialityValue) || (!sourceValue) ) {
            error = true;
            Ext.Msg.alert('Error', 'Please complete the required items in your Journal Entry.');
        }
        
        // ensure a comment or journal track are supplied
        if ( (!comment) && (!trackValue) ) {
            error = true;
            Ext.Msg.alert('Error', 'You are required to supply a Comment or Journal Track Details for a Journal Entry.');
        }
        
        if ( error == false ) {				
			//store confidentiality and source
			record.set('confidentialityLevel', {id:confidentialityValue});
			record.set('journalSource', {id:sourceValue});	
			
			//store comment in model	
			record.set('comment', comment);			
			
			if ( trackValue ) {
				//store journal track tree		
				jTT.getController().save();		
			}
					
            // if a journal track is selected then validate that the details are set         
            if ( trackValue && (record.data.journalEntryDetails.length == 0) ) {
                Ext.Msg.alert('SSP Error', 'You have a Journal Track set in your entry. Please select the associated details for this Journal Entry.');
            }
            else {
				//store journal track
				record.set('journalTrack', {id:trackValue});
							
                // fix date from GMT to UTC
                var origEntryDate = record.data.entryDate;
                record.data.entryDate = me.formUtils.toJSONStringifiableDate(record.data.entryDate);                
                             
                jsonData = record.data;                
                
                // null out journalTrack.id prop to prevent failure
                // from an empty string on null field
                if (jsonData.journalTrack == "") {
                    jsonData.journalTrack = null;
                    jsonData.journalEntryDetails = null;
                }
                
                // clean the group property from the journal
                // entry details. It was only used for display
                // of the details.
                if (jsonData.journalEntryDetails != null) {
                    jsonData.journalEntryDetails = record.clearGroupedDetails(jsonData.journalEntryDetails);
                }
                
                me.getView().setLoading(true);
                
                me.journalEntryService.save(me.personLite.get('id'), jsonData, {
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
    
    saveSuccess: function(r, scope) {
        var me = scope;
		me.appEventsController.getApplication().fireEvent('getJournals');
    },
    
    saveFailure: function(response, scope) {
        var me = scope;
        me.getView().setLoading(false);
		me.initForm();
    },
       
    onJournalTrackComboSelect: function(comp, records, eOpts) {
        var me = this;
		var journalTrack = me.getView().getForm().findField('journalTrackId').getValue();
    
		if ( records.length > 0 ) {               
            // the inited property prevents the
            // Journal Entry Details from clearing
            // when the ViewController loads, so the details only 
            // clear when a new journal track is selected
            // because the init for the view sets the combo
            if ( me.inited == true ) {               
				me.model.getGroupedDetails();   			
            }
            if ( journalTrack != null && journalTrack != "" ) {                           
				me.getJournalTrackTree().getController().loadSteps(journalTrack);                
            }
        }
        else {			
            me.removeJournalTrackAndSessionDetails();           
        }
    },
    
    onJournalTrackComboBlur: function(comp, event, eOpts) {
        var me = this;	
        if ( comp.getValue() == "" ) {
            me.removeJournalTrackAndSessionDetails();
		}
    },
    
    removeJournalTrackAndSessionDetails: function() {
        var me = this;					
		me.getView().getForm().findField('journalTrackId').clearValue();
		me.getJournalTrackTree().getController().loadSteps();  		
    },
    
    onRemoveJournalTrackButtonClick: function( button ) {
        var me = this; 		
		me.getView().getForm().findField('journalTrackId').clearValue();	
		me.getJournalTrackTree().getController().clearTrack();		
    },
        
    displayMain: function() {
        var comp = this.formUtils.loadDisplay(this.getContainerToLoadIntoTools(), this.getMainFormToDisplay(), true, {});
    },
    
    displaySessionDetails: function() {
        var comp = this.formUtils.loadDisplay(this.getContainerToLoadIntoTools(), this.getSessionDetailsEditorDisplay(), true, {});        
    }
});
