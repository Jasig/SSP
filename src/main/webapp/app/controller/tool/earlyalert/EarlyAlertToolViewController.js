Ext.define('Ssp.controller.tool.earlyalert.EarlyAlertToolViewController', {
    extend: 'Deft.mvc.ViewController',	
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	appEventsController: 'appEventsController',
    	formUtils: 'formRendererUtils',
        outcomesStore: 'earlyAlertOutcomesStore',
        outreachesStore: 'earlyAlertOutreachesStore',
        referralsStore: 'earlyAlertReferralsStore',
        store: 'confidentialityLevelsStore'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'earlyalertresponse'
    },
    control: {
    	'respondButton': {
			click: 'onRespondClick'
		}
	},
	
    constructor: function() {
    	this.store.load();
    	this.outcomesStore.load();
    	this.outreachesStore.load();
    	this.referralsStore.load();
		return this.callParent(arguments);
    },
    
    onRespondClick: function(button){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
    }
});