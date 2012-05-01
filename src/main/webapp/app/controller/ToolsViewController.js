Ext.define('Ssp.controller.ToolsViewController', {
	extend: 'Ext.app.Controller',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        currentPerson: 'currentPerson',
        formUtils: 'formRendererUtils'
    },
	views: [
        'ToolsMenu','Tools'
    ],

	init: function() {
        
		this.control({
			'toolsmenu': {
				itemclick: this.itemClick,
				scope: this
			}
			
		});
		
		this.application.addListener('loadPerson', function(){
			// this.currentPerson.get('tools') );
			Ext.ComponentQuery.query('toolsmenu')[0].getSelectionModel().select(0);
			this.loadTool('Profile');
		},this);
		
		this.callParent(arguments);
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
		if (toolsView != null)
		{
			this.formUtils.cleanAll(toolsView);
		}
		
		// create the tool by type
		comp =  Ext.create('Ssp.view.tools.'+toolType);
		
		switch(toolType)
		{
			case 'Profile':
				comp.loadRecord( person );
				break;
			
			case 'StudentIntake':
				this.application.fireEvent('loadStudentIntake');
				break;
		}
		
		toolsView.add( comp );
	}
    
});