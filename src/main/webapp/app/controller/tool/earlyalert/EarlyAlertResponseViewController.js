Ext.define('Ssp.controller.tool.earlyalert.EarlyAlertResponseViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	earlyAlert: 'currentEarlyAlert',
    	earlyAlertResponseService: 'earlyAlertResponseService',
    	formUtils: 'formRendererUtils',
    	model: 'currentEarlyAlertResponse',
    	personLite: 'personLite'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'earlyalert'
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
		var personId = me.personLite.get('id');
		var earlyAlertId = me.earlyAlert.get('id');
		var referralsItemSelector = Ext.ComponentQuery.query('#earlyAlertReferralsItemSelector')[0];	
		var selectedReferrals = [];			
		
		me.getView().getForm().updateRecord();
		record = me.model;
		
		// populate referrals
		selectedReferrals = referralsItemSelector.getValue();
		if (selectedReferrals.length > 0)
		{			
		   record.set('earlyAlertReferralIds', selectedReferrals);
		}else{
		   record.data.referrals=null;
		}		
		
		// set the early alert id for the response
		record.set( 'earlyAlertId', earlyAlertId ); 
		
		// jsonData for the response
		jsonData = record.data;
		
		console.log(jsonData);
		
		me.earlyAlertResponseService.save(personId, jsonData, {
			success: me.saveEarlyAlertResponseSuccess,
			failure: me.saveEarlyAlertResponseFailure,
			scope: me
		})
	},
	
	saveEarlyAlertResponseSuccess: function( r, scope ) {
		var me=scope;
		me.displayMain();
	},

	saveEarlyAlertResponseFailure: function( r, scope ) {
		var me=scope;
	},
	
	onCancelClick: function(button){
		this.displayMain();
	},
	
	displayMain: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});