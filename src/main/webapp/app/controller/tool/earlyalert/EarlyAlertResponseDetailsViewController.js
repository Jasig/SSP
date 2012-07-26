Ext.define('Ssp.controller.tool.earlyalert.EarlyAlertResponseDetailsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	outcomesStore: 'earlyAlertOutcomesStore',
    	outreachesStore: 'earlyAlertOutreachesStore',
    	referralsStore: 'earlyAlertReferralsStore',
    	formUtils: 'formRendererUtils',
        model: 'currentEarlyAlertResponse',
        selectedOutreachesStore: 'earlyAlertResponseDetailsOutreachesStore',
        selectedReferralsStore: 'earlyAlertResponseDetailsReferralsStore'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'earlyalert'
    },
    control: {
    	'finishButton': {
    		click: 'onFinishButtonClick'
    	},
    	
    	outcomeField: '#outcomeField',
    	createdByField: '#createdByField'
    },
	init: function() {
		var me=this;
		var me=this;
		var selectedOutreaches=[];
		var selectedReferrals=[];
		var outcome = me.outcomesStore.getById( me.model.get('earlyAlertOutcomeId') );
		
		// reset and populate general fields comments, etc.
		me.getView().getForm().reset();
		me.getView().loadRecord( me.model );

		me.getCreatedByField().setValue( me.model.getCreatedByPersonName() );
		
		// display outcome
		me.getOutcomeField().setValue( outcome.get('name') );

		// Outreaches
		selectedOutreaches = me.formUtils.getSimpleItemsForDisplay( me.outreachesStore, me.model.get('earlyAlertOutreachIds'), 'Outreaches' );
		me.selectedOutreachesStore.removeAll();
		me.selectedOutreachesStore.loadData( selectedOutreaches );

		// Referrals
		selectedReferrals = me.formUtils.getSimpleItemsForDisplay( me.referralsStore, me.model.get('earlyAlertReferralIds'), 'Referrals' );
		me.selectedReferralsStore.removeAll();
		me.selectedReferralsStore.loadData( selectedReferrals );		

		return this.callParent(arguments);
    },
    
    onFinishButtonClick: function( button ){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});    	
    }
});