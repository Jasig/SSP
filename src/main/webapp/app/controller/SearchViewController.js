Ext.define('Ssp.controller.SearchViewController', {
    extend: 'Ext.app.Controller', 

    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        currentPerson: 'currentPerson',
        studentsStore: 'studentsStore'
    },
    
    views: [
        'Search'
    ],
 
	init: function() {
    	// load students
    	this.studentsStore.load();
		
		this.control({
			'search': {
				selectionchange: this.handleSelectionChange,
				viewready: this.handleViewReady,
				scope: this
			}
		});
 		
 		this.callParent(arguments);
    },
       
	handleSelectionChange: function(selModel,records,eOpts){ 
		// select the person
		this.currentPerson.data = records[0].data;
		this.application.fireEvent('loadPerson');
	},

	handleViewReady: function(view, eobj){
		view.getSelectionModel().select(0);
	}

});