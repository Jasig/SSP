Ext.define('Ssp.controller.tool.earlyalert.EarlyAlertResponseDetailsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
        model: 'currentEarlyAlertResponse'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'earlyalert'
    },
    control: {
    	'finishButton': {
    		click: 'onFinishButtonClick'
    	}
    },
	init: function() {
		var me=this;
		me.getView().getForm().reset();
		me.getView().loadRecord( me.model );
		return this.callParent(arguments);
    },
    
    onFinishButtonClick: function( button ){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});    	
    }
});