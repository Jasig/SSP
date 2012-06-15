Ext.define('Ssp.controller.tool.ProfileToolViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	appEventsController: 'appEventsController'
    },
    
    control: {
    	'viewHistoryButton': {
			click: 'onViewHistoryClick'
		}
    },
	init: function() {
		return this.callParent(arguments);
    },
    
    onViewHistoryClick: function(button){
   	 this.appEventsController.getApplication().fireEvent("viewHistory");
    },
});