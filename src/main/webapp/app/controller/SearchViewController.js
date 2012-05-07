Ext.define('Ssp.controller.SearchViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
        currentPerson: 'currentPerson',
        studentsStore: 'studentsStore',
        appEventsController: 'appEventsController'
    },

    control: {
    	view: {
    		selectionchange: 'onSelectionChange',
			viewready: 'onViewReady'
    	}
    },
    
	init: function() {
		// load students
    	this.studentsStore.load();

 		return this.callParent(arguments);
    },
    
	onSelectionChange: function(selModel,records,eOpts){ 
		// select the person
		this.currentPerson.data = records[0].data;
		this.appEventsController.getApplication().fireEvent('loadPerson');
	},

	onViewReady: function(comp, eobj){
		comp.getSelectionModel().select(0);
	}
});