Ext.define('Ssp.controller.tool.earlyalert.EarlyAlertResponseViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	person: 'currentPerson',
    	earlyAlert: 'currentEarlyAlert',
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
		var me=this;
		
		me.getView().getForm().loadRecord(me.model);
		me.showHideOtherOutcomeDescription();
		
		me.url = me.apiProperties.getItemUrl('personEarlyAlertResponse');
		me.url = me.url.replace('{personId}',me.person.get('id'));
		me.url = me.url.replace('{earlyAlertId}',me.earlyAlert.get('id'));

		return me.callParent(arguments);
    },
    
    onOutcomeComboSelect: function(comp, records, eOpts){
    	this.showHideOtherOutcomeDescription();
    },
    
    showHideOtherOutcomeDescription: function(){
    	var me=this;
    	if (me.getOutcomeCombo().getValue()==Ssp.util.Constants.OTHER_EARLY_ALERT_OUTCOME_ID)
    	{
    		me.getOtherOutcomeDescriptionText().show();
    	}else{
    		me.getOtherOutcomeDescriptionText().hide();
    	}
    },
    
	onSaveClick: function(button) {
		var me = this;
		var record, id, jsonData, url;
		// referrals view
		var referralsItemSelector = Ext.ComponentQuery.query('#earlyAlertReferralsItemSelector')[0];	
		var selectedReferrals = [];			
		var successFunc = function(response, view) {
			me.displayMain();
		}
		
		url = me.url;
		me.getView().getForm().updateRecord();
		record = me.model;
		id = record.get('id');	
		
		// referrals
		selectedReferrals = referralsItemSelector.getValue();
		if (selectedReferrals.length > 0)
		{			
		   record.set('earlyAlertReferralIds', selectedReferrals);
		}else{
		   record.data.referrals=null;
		}		
		
		record.set( 'earlyAlertId', me.earlyAlert.get('id') ); 
		
		jsonData = record.data;
		
		console.log(jsonData);
		
		/*
		if (id.length > 0)
		{
			// editing
			this.apiProperties.makeRequest({
				url: url+"/"+id,
				method: 'PUT',
				jsonData: jsonData,
				successFunc: successFunc,
				scope: me 
			});
			
		}else{
		
			// adding
			this.apiProperties.makeRequest({
				url: url,
				method: 'POST',
				jsonData: jsonData,
				successFunc: successFunc,
				scope: me 
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