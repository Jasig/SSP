Ext.define('Ssp.controller.Search', {
    extend: 'Ext.app.Controller',
    
	models: ['StudentTO'],

    stores: ['Students'],
    
	views: [
        'SearchResults'
    ],
 
		
	init: function() {
        console.log('Initialized Search Controller!');
    		
		this.control({
			'searchresults': {
				selectionchange: this.loadStudent,
				viewready: this.setSearchResultsDefaults,
				scope: this
			}
		});
		
    },
    
	/*
	 * Load the student's profile.
	 */    
	loadStudent: function(selModel,records,eOpts){ 
		var record = records[0];

		// Set the current student
		this.application.currentStudent = record;
		
		// Load the student's Profile
		this.application.fireEvent('afterLoadStudent', record);
	},
	
	/*
	 * Handle defaults when the Search Results grid
	 * appears.
	 */
	setSearchResultsDefaults: function(view, eobj){
		view.getSelectionModel().select(0);
	}
	
});