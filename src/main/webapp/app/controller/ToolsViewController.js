Ext.define('Ssp.controller.ToolsViewController', {
	extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
        currentPerson: 'currentPerson',
        formUtils: 'formRendererUtils',
        appEventsController: 'appEventsController'
    },

    control: {
		view: {
			itemclick: 'itemClick',
			viewready: 'onViewReady'
		}
		
	},
	
	init: function() {
		return this.callParent(arguments);
    }, 
    
    onViewReady: function(comp, obj){
 		this.appEventsController.getApplication().addListener('loadPerson', function(){
			// this.currentPerson.get('tools') );
			this.getView().getSelectionModel().select(0);
			this.loadTool('Profile');
		},this);
    },
    
    /*
     * Handle Tool Menu Item Click.
     */
	itemClick: function(grid,record,item,index){ 
		this.loadTool( record.get('toolType') );		
	},
	
 	/*
	 * Loads a tool.
	 */	
	loadTool: function( toolType ) {	
    	var toolsView = Ext.ComponentQuery.query('tools')[0];
		var comp = null;
		var person = this.currentPerson;
		
		// Kill existing tools, so no dupe ids are registered
		if (toolsView.items.length > 0)
		{
			toolsView.removeAll();
		}
		
		// create the tool by type
		comp =  Ext.create('Ssp.view.tools.'+toolType);
		
		// add to the view
		toolsView.add( comp );
	}
    
});