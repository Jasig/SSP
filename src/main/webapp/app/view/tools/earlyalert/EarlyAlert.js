Ext.define('Ssp.view.tools.earlyalert.EarlyAlert', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.earlyalert',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.earlyalert.EarlyAlertToolViewController',
    inject: {
    	columnRendererUtils: 'columnRendererUtils',
        store: 'earlyAlertsStore'
    },
	width: '100%',
	height: '100%',
	
	initComponent: function() {	
    	var sm = Ext.create('Ext.selection.CheckboxModel');
		
		Ext.apply(this, 
				{
		            autoScroll: true,
		            title: 'Early Alerts',

	    		      columns: [{
					    	        xtype:'actioncolumn',
					    	        width:65,
					    	        header: 'Action',
					    	        items: [{
					    	            icon: Ssp.util.Constants.GRID_ITEM_EDIT_ICON_PATH,
					    	            tooltip: 'Edit Task',
					    	            handler: function(grid, rowIndex, colIndex) {
					    	            	var rec = grid.getStore().getAt(rowIndex);
					    	            	var panel = grid.up('panel');
					    	                panel.model.data=rec.data;
					    	                panel.appEventsController.getApplication().fireEvent('editJournalEntry');
					    	            },
					    	            scope: this
					    	        },{
					    	            icon: Ssp.util.Constants.GRID_ITEM_DELETE_ICON_PATH,
					    	            tooltip: 'Delete Task',
					    	            handler: function(grid, rowIndex, colIndex) {
					    	            	var rec = grid.getStore().getAt(rowIndex);
					    	            	var panel = grid.up('panel');
					    	                panel.model.data=rec.data;
					    	            	panel.appEventsController.getApplication().fireEvent('deleteJournalEntry');
					    	            },
					    	            scope: this
					    	        }]
				                },
	    		                { header: 'Name',  
	    		                  dataIndex: 'courseTitle',
	    		                  field: {
	    		                      xtype: 'textfield'
	    		                  },
	    		                  flex: 50 },
	    		                { header: 'Description',
	    		                  dataIndex: 'description',
	    		                  field: {
	    		                      xtype: 'textfield'
	    		                  },
	    		                  flex: 50 }
	    		                  ],
				    
				    dockedItems: [{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{
				            tooltip: 'Respond to the selected Early Alert',
				            text: 'Respond',
				            xtype: 'button',
				            itemId: 'respondButton'
				        }]
				    }]
				});
		
		return this.callParent(arguments);
	}
});