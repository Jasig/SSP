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
    control: {
    	view: {
    		itemexpand: 'onItemExpand'
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
    }
});