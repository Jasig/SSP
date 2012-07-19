Ext.define('Ssp.controller.tool.earlyalert.EarlyAlertTreeViewController', {
    extend: 'Deft.mvc.ViewController',	
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	appEventsController: 'appEventsController',
    	earlyAlertService: 'earlyAlertService',
    	earlyAlertResponseService: 'earlyAlertResponseService',
    	earlyAlert: 'currentEarlyAlert',
    	formUtils: 'formRendererUtils',
    	personLite: 'personLite',
    	treeStore: 'earlyAlertsTreeStore'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'earlyalertresponse'
    },
    control: {
    	view: {
    		itemexpand: 'onItemExpand'
    	},
    	
    	'respondButton': {
			click: 'onRespondClick'
		}
    },
	init: function(){
		return this.callParent(arguments);		
	},
	
    onItemExpand: function(nodeInt, obj){
    	console.log( 'EarlyAlertTreeViewController->onItemExpand');
    	var me=this;
    	var node = nodeInt;
    	var nodeType = node.get('nodeType');
    	var id = node.get('id' );
    	var personId = me.personLite.get('id');
    	if (nodeType == 'early alert' && id != "")
    	{
    		me.earlyAlertService.getAllEarlyAlertResponses(personId, id);
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
	
	loadEditor: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});