Ext.define('Ssp.controller.Search', {
    extend: 'Ext.app.Controller',
    
	models: ['StudentTO'],

    stores: ['Students','Tools'],
    
	views: [
        'SearchResults','ToolsMenu','Tools'
    ],
 
		
	init: function() {
        console.log('Initialized Search Controller!');
    		
		this.control({
			'SearchResults': {
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
		var toolsStore = Ext.getStore('Tools');
		var toolsMenu = Ext.getCmp('ToolsMenu');
		var toolsController = Ext.getCmp('ToolController');
				
		// Set the current student
		this.application.currentStudent = record;

		// Load the tools for the selected student
		// Assumes that the tools under the students record are in the format
		// of a tools json object
		// toolsStore.load();
		toolsStore.loadRawData( record.get('tools') );
		toolsMenu.getSelectionModel().select(0);
		
		// Load the student's Profile
		this.application.getController('Tool').loadTool('Profile');
	},
	
	/*
	 * Handle defaults when the Search Results grid
	 * appears.
	 */
	setSearchResultsDefaults: function(view, eobj){
		view.getSelectionModel().select(0);
	}
	
});