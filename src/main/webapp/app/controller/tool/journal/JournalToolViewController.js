/*
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
Ext.define('Ssp.controller.tool.journal.JournalToolViewController', {
    extend: 'Deft.mvc.ViewController',	
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
        confidentialityLevelsStore: 'confidentialityLevelsAllUnpagedStore',
    	formUtils: 'formRendererUtils',
    	service: 'journalEntryService',
        journalEntriesStore: 'journalEntriesUnpagedStore',
        journalSourcesStore: 'journalSourcesAllUnpagedStore',
    	journalTracksStore: 'journalTracksAllUnpagedStore',
    	model: 'currentJournalEntry',
    	personLite: 'personLite',
    	textStore: 'sspTextStore'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'journal',
    	personJournalUrl: ''
    },
    control: {
    	view: {
    		viewready: 'onViewReady',
			select: 'onJournalClick'
    	},
    	
    	'addButton': {
			click: 'onAddClick'
		},
		
		'deleteButton': {
			click: 'onDeleteClick'
		},
		
		'saveButton': {
			click: 'onSaveClick'
		},
		
		'cancelButton': {
			click: 'onCancelClick'
		},
		
		'journalHistoryButton': {
			click: 'onViewJournalHistoryButtonClick'
		}
	},
	
    init: function() {
		var me = this;
		me.getView().setLoading( true );
		
		// clear any existing journal entries
		me.journalEntriesStore.removeAll();
		
		me.service.getAll( me.personLite.get('id'), {
			success: me.getAllInitialJournalEntriesSuccess,
			failure: me.getAllJournalEntriesFailure,
			scope: me
		});
		
		me.confidentialityLevelsStore.load();
		me.formUtils.applyActiveOnlyFilter(me.confidentialityLevelsStore);
		
		me.journalSourcesStore.load();

		me.journalTracksStore.load();
		
		return me.callParent(arguments);
    },
	
	getJournalEntries: function(){
		var me = this;
		
		me.getView().setLoading( true );
		
		// clear any existing journal entries
		me.journalEntriesStore.removeAll();
		
		me.service.getAll( me.personLite.get('id'), {
			success: me.getAllInitialJournalEntriesSuccess,
			failure: me.getAllJournalEntriesFailure,
			scope: me
		});
		
	},
	onViewJournalHistoryButtonClick: function(button){
        var me=this;
        var personId = me.personLite.get('id');
        var personJournalViewHistoryUrl = (me.apiProperties.getAPIContext() + me.apiProperties.getItemUrl('personViewJournalHistory')).replace('{id}',personId);
        me.apiProperties.getReporter().loadBlankReport(personJournalViewHistoryUrl);
    },
 
    getAllJournalEntriesSuccess: function( r, scope ) {
		var me=scope;
		me.getView().setLoading( false );
    	if ( r.rows.length > 0 ) {
    		me.journalEntriesStore.sort([
		    {
		        property : 'modifiedDate',
		        direction: 'DESC'
		    }]);
			me.journalEntriesStore.loadData(r.rows);
			me.model.data = me.journalEntriesStore.getAt(0).data;
			me.getView().getSelectionModel().select(0);			
    	}
		else{
            // if no record is available 
            var je = new Ssp.model.tool.journal.JournalEntry();
    		me.model.data = je.data;
			me.callDetails();
        }
		
	},
	
	getAllInitialJournalEntriesSuccess: function( r, scope ) {
		var me=scope;
		me.getView().setLoading( false );
    	if ( r.rows.length > 0 ) {
    		me.journalEntriesStore.sort([
		    {
		        property : 'modifiedDate',
		        direction: 'DESC'
		    }]);
			me.journalEntriesStore.loadData(r.rows);
			var je = new Ssp.model.tool.journal.JournalEntry();
    		me.model.data = je.data;
			me.callDetails();	
    	}
		else{
            // if no record is available 
            var je = new Ssp.model.tool.journal.JournalEntry();
    		me.model.data = je.data;
			me.callDetails();
        }
		
	},

	getAllJournalEntriesFailure: function( response, scope ) {
		var me=scope;
		me.getView().setLoading( false );
	},    
    
    onViewReady: function(comp, obj) {    	
    	this.appEventsController.assignEvent({eventName: 'deleteJournalEntry', callBackFunc: this.deleteConfirmation, scope: this});
		this.appEventsController.assignEvent({eventName: 'getJournals', callBackFunc: this.getJournalEntries, scope: this});
    },    
 
    destroy: function() {
    	var me=this;
    	    	
    	me.appEventsController.removeEvent({eventName: 'deleteJournalEntry', callBackFunc: me.deleteConfirmation, scope: me});
		
		me.appEventsController.removeEvent({eventName: 'getJournals', callBackFunc: me.getJournalEntries, scope: me});


        return me.callParent( arguments );
    },    
    
    onAddClick: function( button ) {
		var me=this;
    	var je = new Ssp.model.tool.journal.JournalEntry();
    	this.model.data = je.data;
		me.callDetails();
    },
    	
	onDeleteClick: function( button ) {
	    var me=this;
    	var grid, record;
		grid = button.up('grid');
		record = grid.getView().getSelectionModel().getSelection()[0];
        if ( record ) {	
			this.model.data=record.data;
        	this.appEventsController.getApplication().fireEvent('deleteJournalEntry');
        } else {
			Ext.Msg.alert(
				me.textStore.getValueByCode('ssp.message.journal.error-title','SSP Error'),
				me.textStore.getValueByCode('ssp.message.journal.select-journal-delete','Please select a journal to delete.')
				);
        }
    },
	
	onSaveClick: function(button) {
		var me=this;		
		me.appEventsController.getApplication().fireEvent('saveJournal');
	},
	
	onJournalClick:function() {
		var me=this;
		var record = me.getView().getSelectionModel().getSelection()[0];
		
		if ( record ) {	
			me.model.data=record.data;
			me.callDetails();
        }		
	},
	
	onCancelClick: function( button ) {
		var me = this;		
		me.appEventsController.getApplication().fireEvent('resetJournal');
	},
	
	callDetails: function() {
		Ext.ComponentQuery.query('#editjournalGrid')[0].getController().initForm();
		Ext.ComponentQuery.query('#editjournalGrid treepanel')[0].getController().init();
	},
 
    deleteConfirmation: function() {
        var me=this;
    	var defaultMsg = 'You are about to delete a Journal Entry. Would you like to continue?';
    	var model = me.model;
        if ( model.get('id') ) 
        {
           Ext.Msg.confirm({
   		     title: me.textStore.getValueByCode('ssp.message.journal.confirm-delete-title','Delete Journal Entry?'),
   		     msg: me.textStore.getValueByCode('ssp.message.journal.confirm-delete-body',defaultMsg),
   		     buttons: Ext.Msg.YESNO,
   		     fn: me.deleteJournalEntry,
   		     scope: me
   		   });
        } else {
			Ext.Msg.alert(
				me.textStore.getValueByCode('ssp.message.journal.error-title','SSP Error'),
				me.textStore.getValueByCode('ssp.message.journal.unable-to-delete','Unable to delete Journal Entry.')
				);
        }
     },
     
     deleteJournalEntry: function( btnId ) {
     	var me=this;
    	var store = me.journalEntriesStore;
     	var id = me.model.get('id');
     	if ( btnId == "yes" ) {
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
		
		if (me.journalEntriesStore.data.length) {
			me.model.data = me.journalEntriesStore.getAt(0).data;			
			me.getView().getSelectionModel().select(0);
		}
 	},

 	destroyJournalEntryFailure: function( response, scope ) {
 		var me=scope;
 		me.getView().setLoading( false );
 	},      
     
    loadEditor: function() {
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});    	
    },
	loadEditorSelf: function() {
		var comp = this.formUtils.loadDisplay(this.getFormToDisplay(), this.getFormToDisplay(), true, {});    	
    }
});
