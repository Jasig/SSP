Ext.define('Ssp.controller.tool.earlyalert.EarlyAlertResponseViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	earlyAlert: 'currentEarlyAlert',
    	earlyAlertResponseService: 'earlyAlertResponseService',
    	earlyAlertService: 'earlyAlertService',
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
		me.getView().getForm().reset();
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
		
		me.getView().setLoading(true);
		me.earlyAlertResponseService.save(personId, earlyAlertId, jsonData, {
			success: me.saveEarlyAlertResponseSuccess,
			failure: me.saveEarlyAlertResponseFailure,
			scope: me
		})
	},
	
	saveEarlyAlertResponseSuccess: function( r, scope ) {
		var me=scope;
		me.getView().setLoading(false);
        Ext.Msg.confirm({
		     title: 'Your response was saved.',
		     msg: 'Would you like to close the Early Alert Notice',
		     buttons: Ext.Msg.YESNO,
		     fn: me.closeEarlyAlertConfirm,
		     scope: me
	    });
	},

	saveEarlyAlertResponseFailure: function( response, scope ) {
		var me=scope;
		me.getView().setLoading(false);
	},
	
	closeEarlyAlertConfirm: function( btnId ){
     	var me=this;
     	var jsonData;
     	var personId = me.personLite.get('id');
     	if (btnId=="yes")
     	{
     		if (me.earlyAlert.get('closedById') != "")
     		{
         		me.earlyAlert.set( 'closedDate', new Date() );
         		me.earlyAlert.set( 'closedById', personId );    			
     		}
     		jsonData = me.earlyAlert.data;
     		delete jsonData.earlyAlertReasonId;
         	me.earlyAlertService.save( personId, jsonData,{
         		success: me.closeEarlyAlertSuccess,
         		failure: me.closeEarlyAlertFailure,
         		scope: me
         	});    		
     	}else{
     		me.displayMain();
     	}
     }, 

 	closeEarlyAlertSuccess: function( r, scope ) {
		var me=scope;
		me.getView().setLoading(false);
		me.displayMain();
	},

	closeEarlyAlertFailure: function( response, scope ) {
		var me=scope;
		me.getView().setLoading(false);
	},
	
	onCancelClick: function(button){
		this.displayMain();
	},
	
	displayMain: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});