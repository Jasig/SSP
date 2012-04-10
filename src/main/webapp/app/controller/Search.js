Ext.define('Ssp.controller.Search', {
    extend: 'Ext.app.Controller',   
    mixins: [ 'Deft.mixin.Injectable' ],

    inject: {
        person: 'currentPerson'
    },    

    views: [
        'SearchResults'
    ],
 
	init: function() {
 		this.control({
			'searchresults': {
				selectionchange: this.handleSelectionChange,
				viewready: this.handleViewReady,
				scope: this
			}
		});
    },
    
	/* 
	 * Load the student's profile. 
	 */    
	handleSelectionChange: function(selModel,records,eOpts){ 
		var record = records[0];

		// Set the current student
		this.person = record;
		
		this.application.currentStudent = record;
		
		// Load the student's Profile
		this.application.fireEvent('afterLoadStudent', record);
	},
	
	/*
	 * Handle defaults when the Search Result grid appears.
	 */
	handleViewReady: function(view, eobj){
		view.getSelectionModel().select(0);
	}
	
});