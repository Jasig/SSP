Ext.define('Ssp.controller.tool.earlyalert.EarlyAlertToolViewController', {
    extend: 'Deft.mvc.ViewController',	
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	appEventsController: 'appEventsController',
    	earlyAlertsStore: 'earlyAlertsStore',
    	earlyAlertService: 'earlyAlertService',
    	earlyAlert: 'currentEarlyAlert',
    	formUtils: 'formRendererUtils',
        outcomesStore: 'earlyAlertOutcomesStore',
        outreachesStore: 'earlyAlertOutreachesStore',
    	personLite: 'personLite',
        referralsStore: 'earlyAlertReferralsStore',
        confidentialityLevelsStore: 'confidentialityLevelsStore'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'earlyalertresponse'
    },
    control: {
    	view: {
    		selectionchange: 'onSelectionChange',
    		viewready: 'onViewReady'
    	}
    
        /*,
    	'respondButton': {
			click: 'onRespondClick'
		}
		*/
	},
	
	init: function(){
		return this.callParent(arguments);		
	},

    onViewReady: function(comp, obj){
		var me=this;
		
		me.appEventsController.assignEvent({eventName: 'respondToEarlyAlert', callBackFunc: me.onRespondToEarlyAlert, scope: me});
		
		me.confidentialityLevelsStore.load();
    	me.outcomesStore.load();
    	me.outreachesStore.load();
    	me.referralsStore.load();
    	me.getEarlyAlerts();
    },	

    destroy: function() {
    	var me=this;

		me.appEventsController.removeEvent({eventName: 'respondToEarlyAlert', callBackFunc: me.onRespondToEarlyAlert, scope: me});

        return me.callParent( arguments );
    },    
    
	getEarlyAlerts: function(){
    	var me=this;
		var pId = me.personLite.get('id');
		me.earlyAlertService.getAll( pId, 
    		{success:me.getEarlyAlertsSuccess, 
			 failure:me.getEarlyAlertsFailure, 
			 scope: me});	
	},
    
    getEarlyAlertsSuccess: function( r, scope){
    	var me=scope;
    	if ( me.earlyAlertsStore.getCount() > 0)
    	{
    		me.getView().getSelectionModel().select(0);
    	}else{
    		// if no record is available then set the selected early alert to null
    		me.earlyAlert.data = new Ssp.model.tool.earlyalert.PersonEarlyAlert;
    	}
    },

    getEarlyAlertsFailure: function( r, scope){
    	var me=scope;
    },

	onSelectionChange: function(selModel,records,eOpts){ 
		var me=this;
		if (records.length > 0)
		{
			me.earlyAlert.data = records[0].data;
		}
	},
    
	onRespondToEarlyAlert: function(button){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
    }
});