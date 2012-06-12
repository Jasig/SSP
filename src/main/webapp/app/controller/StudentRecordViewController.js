Ext.define('Ssp.controller.StudentRecordViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
        appEventsController: 'appEventsController',
        person: 'currentPerson'
    },

    control: {
    	view: {
			afterrender: 'onViewReady'
    	}
    },
    
	init: function() {
 		return this.callParent(arguments);
    },

	onViewReady: function(comp, eobj){
		console.log(this.getView());
		this.appEventsController.assignEvent({eventName: 'loadPerson', callBackFunc: this.onLoadPerson, scope: this});
	},

    destroy: function() {
	   	this.appEventsController.removeEvent({eventName: 'loadPerson', callBackFunc: this.onLoadPerson, scope: this});

        return this.callParent( arguments );
    },
    
	onLoadPerson: function(){
		this.getView().setTitle(this.person.getFullName());
	}
});