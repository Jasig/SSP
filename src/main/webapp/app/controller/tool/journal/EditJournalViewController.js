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
			Ext.Msg.alert('Error','Please correct the errors in your Journal Entry.');
		}

	},
	
	onCancelClick: function(button){
		this.displayMain();
	},
	
	displayMain: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});