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
    	outreachList: {
    		selector: '#outreachList',
            listeners: {
            	validitychange: 'onOutreachListValidityChange'
            }
    	},
    	
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
    
    onOutreachListValidityChange: function( comp, isValid, eOpts ){
    	//comp[isValid ? 'removeCls' : 'addCls']('multiselect-invalid');
    },
    
	onSaveClick: function(button) {
		var me = this;
		var record, id, jsonData, url;
		var personId = me.personLite.get('id');
		var earlyAlertId = me.earlyAlert.get('id');
		var referralsItemSelector = Ext.ComponentQuery.query('#earlyAlertReferralsItemSelector')[0];	
		var selectedReferrals = [];			
		var form = me.getView().getForm();
		var outreaches = me.getOutreachList().getValue();
		var outreachIsValid = false;
		// validate multi-select list
		// accomodate error in extjs where
		// list is not correctly marked invalid
		if ( outreaches.length > 0 )
		{
			if (outreaches[0] != "")
			{
				outreachIsValid=true;
			}
		}
		if ( outreachIsValid == false )
		{
			me.getOutreachList().setValue(["1"]);
			me.getOutreachList().setValue([]);
			me.getOutreachList().addCls('multiselect-invalid');
			me.getOutreachList().markInvalid("At least one Outreach is required.");			
		}
		
		// test for valid form entry
		if ( form.isValid() && outreachIsValid)
		{
			form.updateRecord();
			record = me.model;
			
			// populate referrals
			selectedReferrals = referralsItemSelector.getValue();
			if (selectedReferrals.length > 0)
			{			
			   record.set('earlyAlertReferralIds', selectedReferrals);
			}else{
			   // AAL : 08/01/12 : Commented line below as it was adding a "referrals" property to the API call
					// and this property isn't valid per the api spec.  Added the setting of the earlyAlertReferralIds
					// property to the empty array when none are selected.  By default this value was being set to an
					// empty string which isn't valid per the api spec and was throwing an exception on the server.
			   // record.data.referrals=null;
			   record.set('earlyAlertReferralIds', []);
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
			});				
		}else{
			Ext.Msg.alert('Error','Please correct the indicated errors in this form.');
		}
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
     		if (me.earlyAlert.get('closedById') == "" || me.earlyAlert.get('closedById') == null)
     		{
     			// fix for GMT to UTC
         		me.earlyAlert.set('closedDate', me.formUtils.fixDateOffsetWithTime( new Date() ) );
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