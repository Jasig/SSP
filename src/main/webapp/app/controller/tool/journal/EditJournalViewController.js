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
    	formToDisplay: 'journal',
    	url: '',
    	inited: false
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
		this.url = this.apiProperties.createUrl( this.apiProperties.getItemUrl('personJournalEntry') );
		this.url = this.url.replace('{id}',this.person.get('id'));
		
		this.initForm();
		
		return this.callParent(arguments);
    },
 
	initForm: function(){
		var id = this.model.get("id");
		this.getView().getForm().reset();
		this.getView().getForm().loadRecord( this.model );
		if (id != null && id != "")
		{
			Ext.ComponentQuery.query('#confidentialityLevelCombo')[0].setValue( this.model.get('confidentialityLevel').id );
			Ext.ComponentQuery.query('#journalSourceCombo')[0].setValue( this.model.get('journalSource').id );
			Ext.ComponentQuery.query('#journalTrackCombo')[0].setValue( this.model.get('journalTrack').id );			
		}
		this.inited=true;
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
    		record.set('confidentialityLevel',{id: form.getValues().confidentialityLevelId});
    		record.set('journalSource',{id: form.getValues().journalSourceId});
    		record.set('journalTrack',{id: form.getValues().journalTrackId});
    		
    		// if a journal track is selected then validate that the details are set
    		if (record.get('journalTrack').id != "" && record.get('journalEntryDetails').length > 0)
    		{
        		jsonData = record.data;
    			
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
    			Ext.Msg.alert('Error','You have a Journal Track set in your entry. Please check the associated details for this Journal Entry.');    			
    		}
		}else{
			Ext.Msg.alert('Error','Please correct the errors in your Journal Entry.');
		}

	},
	
	onCancelClick: function(button){
		this.displayMain();
	},
	
	onJournalTrackComboChange: function(comp, newValue, oldValue, eOpts){
    	if (newValue.length > 2)
    	{
    		this.model.set('journalTrack',{id: newValue});
    		
    		// the inited property prevents the
    		// tree from being populated twice
    		// once when the viewcontroller loads
    		// and another time when the journal track combo
    		// is first populated
    		if (this.inited==true)
    		{
    	   		this.appEventsController.getApplication().fireEvent('setJournalTrack');    			
    		}
     	}
	},
	
	displayMain: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});