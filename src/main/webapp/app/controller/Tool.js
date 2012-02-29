Ext.require('Ext.tab.*');
Ext.define('Ssp.controller.Tool', {
    extend: 'Ext.app.Controller',
    
	views: [
        'ToolsMenu'
    ],
    
    stores: ['reference.FundingSources','reference.Challenges'],

	init: function() {
        console.log('Initialized Tools Controller!');
        
		this.control({
			'ToolsMenu': {
				itemclick: this.itemClick,
				scope: this
			}
			
		});  
    },
 
 	/*
	 * Load the selected tool.
	 * var studentRecord = Ext.getCmp('SearchResults');
		var columnRendererUtils = Ext.create('Ssp.util.FormRendererUtils');
		var profileForm = columnRendererUtils.getProfileFormItems();
		var studentRecordView = Ext.getCmp('StudentRecord');

	 */    
	itemClick: function(grid,record,item,index){ 
		this.loadTool( record.get('toolType') );		
	},
	
	loadTool: function( toolType ) {
		var toolsView = Ext.getCmp('Tools');
		var comp = Ext.create('Ssp.view.tools.'+toolType);
		var tabs;
		
		switch(toolType)
		{
			case 'Profile':
				comp.loadRecord( this.application.currentStudent );
				break;
			
			case 'StudentIntake':
				Ext.getCmp('StudentIntakePersonal').loadRecord( this.application.currentStudent );
				Ext.getCmp('StudentIntakeDemographics').loadRecord( this.application.currentStudent );
				Ext.getCmp('StudentIntakeEducationPlans').loadRecord( this.application.currentStudent );

				// Education Levels Check Boxes
				var form = Ext.getCmp('StudentIntakeEducationLevels');
				var store = Ext.getStore('reference.EducationLevels');
				for (var i=0; i<store.data.items.length; i++)
				{
					var cb = {xtype:'checkbox'};
					var item = store.data.items[i];
					cb.boxLabel = item.get('name');
					cb.inputValue = item.get('id');
					form.insert(form.items.length,cb);
				}
				form.doLayout();
				form.loadRecord( this.application.currentStudent );
				
				// Funding Check Boxes
				var form = Ext.getCmp('StudentIntakeFunding');
				var store = Ext.getStore('reference.FundingSources');
				for (var i=0; i<store.data.items.length; i++)
				{
					var cb = {xtype:'checkbox'};
					var item = store.data.items[i];
					cb.boxLabel = item.get('name');
					cb.inputValue = item.get('id');
					form.insert(form.items.length,cb);
				}
				form.doLayout();
				form.loadRecord( this.application.currentStudent );
				
				// Challenges
				form = Ext.getCmp('StudentIntakeChallenges');
				store = Ext.getStore('reference.Challenges');
				for (i=0; i<store.data.items.length; i++)
				{
					var cb = {xtype:'checkbox'};
					var item = store.data.items[i];
					cb.boxLabel = item.get('name');
					cb.inputValue = item.get('id');
					form.insert(form.items.length,cb);
				}
				form.doLayout();
				form.loadRecord( this.application.currentStudent );
				break;
		}
		
		toolsView.removeAll();			
		toolsView.add( comp );
		//toolsView.doLayout();	
	}
    
});