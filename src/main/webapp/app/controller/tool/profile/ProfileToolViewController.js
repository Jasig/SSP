Ext.define('Ssp.controller.tool.profile.ProfileToolViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	appEventsController: 'appEventsController'
    },
    
    control: {
    	'viewHistoryButton': {
			click: 'onViewHistoryClick'
		},
		
		'studentTransitionButton': {
			click: 'onStudentTransitionClick'
		}
    },
	init: function() {
		return this.callParent(arguments);
    },
    
    onViewHistoryClick: function(button){
   	 this.appEventsController.getApplication().fireEvent("viewHistory");
    },

    onStudentTransitionClick: function(button){
      	 Ext.Msg.alert('Attention','This feature is not yet active.');
    },
});