Ext.define('Ssp.controller.tool.journal.EditJournalViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	person: 'currentPerson',
    	model: 'currentJournalEntry'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'journal',
    	url: ''
    },
    control: {
    	'journalTrackCombo': {
    		change: 'onJournalTrackComboChange'
    	},
    	
    	'saveButton': {
			click: 'onSaveClick'
		},
		
		'cancelButton': {
			click: 'onCancelClick'
		}   	
    },
    
	init: function() {
		this.getView().getForm().loadRecord(this.model);
		
		this.url = this.apiProperties.createUrl( this.apiProperties.getItemUrl('personJournalEntry') );
		this.url = this.url.replace('{id}',this.person.get('id'));
		
		return this.callParent(arguments);
    },
    
	onSaveClick: function(button) {
		var me = this;
		var record, id, jsonData, url;
		var form = this.getView().getForm();
		var values = form.getValues();
		url = this.url;
		record = this.model;
		id = record.get('id');
		
		successFunc = function(response, view) {
			me.displayMain();
		};
		if (form.isValid())
		{
			form.updateRecord();
    		record.set('personId', this.person.get('id') );    		
    		record.set('confidentialityLevel',{id: form.getValues().confidentialityLevelId});
			
    		jsonData = {"id" : "",
    			 "createdDate" : null,
    			 "createdBy" : null,
    			 "modifiedDate" : null,
    			 "modifiedBy" : null,
    			 "entryDate" : null,
    			 "comment" : "Testing",
    			 "confidentialityLevel" :
    			    {"id" : "afe3e3e6-87fa-11e1-91b2-0026b9e7ff4c",
    			     "name" : "EVERYONE"},
    			 "journalSourceId" : "b2d07973-5056-a51a-8073-1d3641ce507f",
    			 "journalTrackId" : "b2d07a7d-5056-a51a-80a8-96ae5188a188"
    			};  		
    		
    		//jsonData = record.data;
			
			if (id.length > 0)
			{
				// editing
				this.apiProperties.makeRequest({
					url: url+id,
					method: 'PUT',
					jsonData: jsonData,
					successFunc: successFunc 
				});
				
			}else{
				// adding
				this.apiProperties.makeRequest({
					url: url,
					method: 'POST',
					jsonData: jsonData,
					successFunc: successFunc 
				});		
			}
			
		}else{
			Ext.Msg.alert('Error','Please correct the errors in your Journal Entry.');
		}

	},
	
	onCancelClick: function(button){
		this.displayMain();
	},
	
	onJournalTrackComboChange: function(comp, newValue, oldValue, eOpts){
		console.log('EditJournalViewController->onJournalTrackChange');
	},
	
	displayMain: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});