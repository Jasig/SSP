Ext.define('Ssp.controller.tool.ActionPlanToolViewController', {
    extend: 'Deft.mvc.ViewController',	
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
        appEventsController: 'appEventsController',
        formRendererUtils: 'formRendererUtils'
    },
    
    control: {
		view: {
			viewready: 'onViewReady'
		}
	},
    
    init: function() {
		return this.callParent(arguments);
    },
    
    onViewReady: function(comp, obj){
		console.log('ActionPlanToolViewController->onViewReady');
    }
});