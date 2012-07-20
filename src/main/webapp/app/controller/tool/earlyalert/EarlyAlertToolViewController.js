Ext.define('Ssp.controller.tool.earlyalert.EarlyAlertToolViewController', {
    extend: 'Deft.mvc.ViewController',	
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	appEventsController: 'appEventsController',
    	confidentialityLevelsStore: 'confidentialityLevelsStore',
    	earlyAlertOutcomesStore: 'earlyAlertOutcomesStore',
    	earlyAlertsStore: 'earlyAlertsStore',
    	earlyAlertService: 'earlyAlertService',
    	earlyAlertResponseService: 'earlyAlertResponseService',
    	earlyAlert: 'currentEarlyAlert',
    	formUtils: 'formRendererUtils',
        outcomesStore: 'earlyAlertOutcomesStore',
        outreachesStore: 'earlyAlertOutreachesStore',
    	personLite: 'personLite',
        referralsStore: 'earlyAlertReferralsStore',
    	treeStore: 'earlyAlertsTreeStore'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'earlyalertresponse'
    },
    control: {
    	view: {
    		viewready: 'onViewReady',
    		itemexpand: 'onItemExpand'
    	},
    	
    	'respondButton': {
			click: 'onRespondClick'
		}
	},
	
	init: function(){
		return this.callParent(arguments);		
	},

    onViewReady: function(comp, obj){
		var me=this;		
		me.earlyAlertOutcomesStore.load();
		me.confidentialityLevelsStore.load();
    	me.outcomesStore.load();
    	me.outreachesStore.load(); 	
    	me.referralsStore.load({
    		callback: function(r,options,success) {
    	         if(success == true) {
     	                 me.getEarlyAlerts(); 
    	          }
    	          else {
    	              Ext.Msg.alert("Ssp Error","Failed to load referrals. See your system administrator for assitance.");
    	          }
    	     }
    	});
    },	

    destroy: function() {
         return this.callParent( arguments );
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

    onItemExpand: function(nodeInt, obj){
    	console.log( 'EarlyAlertTreeViewController->onItemExpand');
    	var me=this;
    	var node = nodeInt;
    	var nodeType = node.get('nodeType');
    	var id = node.get('id' );
    	var personId = me.personLite.get('id');
    	if (node != null)
    	{
    		// use root here to prevent the expand from firing
    		// when items are added to the root element in the tree
        	if (nodeType == 'early alert' && id != "root" && id != "")
        	{
        		me.earlyAlertService.getAllEarlyAlertResponses(personId, id);
        	}    		
    	}
    },
    
	onRespondClick: function( button ){
		var me=this;
		var record = me.getView().getSelectionModel().getSelection()[0];
		if (record != null)
		{
			if (record.get('nodeType')=='early alert')
	    	{
	        	for (prop in me.earlyAlert.data)
	        	{
	        		me.earlyAlert.data[prop] = record.data[prop];
	        	}
	        	
	        	me.loadEditor();
	    	}else{
	    		Ext.Msg.alert('Notification','Please select an Early Alert to send a response.');
	    	}	
    	}else{
    		Ext.Msg.alert('Notification','Please select an Early Alert to send a response.');
    	}
	},
	
	loadEditor: function(button){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
    }
});