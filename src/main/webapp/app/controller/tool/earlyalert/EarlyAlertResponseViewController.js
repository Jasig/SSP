Ext.define('Ssp.controller.tool.earlyalert.EarlyAlertResponseViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	person: 'currentPerson',
    	model: 'currentEarlyAlertResponse'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'earlyalert',
    	url: ''
    },
    control: {
    	outcomeCombo: {
            selector: '#outcomeCombo',
            listeners: {
                select: 'onOutcomeComboSelect'
            }
        },

    	otherOutcomeDescriptionText: {
            selector: '#otherOutcomeDescriptionText'
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
		
		this.showHideOtherOutcomeDescription();
		
		this.url = this.apiProperties.getItemUrl('personEarlyAlert');
		this.url = this.url.replace('{id}',this.person.get('id'));
		
		return this.callParent(arguments);
    },
    
    onOutcomeComboSelect: function(comp, records, eOpts){
    	this.showHideOtherOutcomeDescription();
    },
    
    showHideOtherOutcomeDescription: function(){
    	if (this.getOutcomeCombo().getValue()==Ssp.util.Constants.OTHER_EARLY_ALERT_OUTCOME_ID)
    	{
    		this.getOtherOutcomeDescriptionText().show();
    	}else{
    		this.getOtherOutcomeDescriptionText().hide();
    	}
    },
    
	onSaveClick: function(button) {
		var me = this;
		var record, id, jsonData, url;
		url = this.url;
		this.getView().getForm().updateRecord();
		record = this.model;
		id = record.get('id');
		jsonData = record.data;
		successFunc = function(response, view) {
			me.displayMain();
		};
		
		console.log(jsonData);
		
		/*
		if (id.length > 0)
		{
			// editing
			this.apiProperties.makeRequest({
				url: url+"/"+id,
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
		*/
	},
	
	onCancelClick: function(button){
		this.displayMain();
	},
	
	displayMain: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});