Ext.define('Ssp.view.tools.EarlyAlert', {
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

	    		      columns: [
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
	    		                  ,{
		    			    	        xtype:'actioncolumn',
		    			    	        width:65,
		    			    	        header: 'Action',
		    			    	        items: [{
		    			    	            icon: 'images/edit-icon.jpg',
		    			    	            tooltip: 'Edit Task',
		    			    	            handler: function(grid, rowIndex, colIndex) {
		    			    	            	var rec = grid.getStore().getAt(rowIndex);
		    			    	            	var panel = grid.up('panel');
		    			    	                panel.model.data=rec.data;
		    			    	                panel.appEventsController.getApplication().fireEvent('editJournalEntry');
		    			    	            },
		    			    	            scope: this
		    			    	        },{
		    			    	            icon: 'images/delete-icon.png',
		    			    	            tooltip: 'Delete Task',
		    			    	            handler: function(grid, rowIndex, colIndex) {
		    			    	            	var rec = grid.getStore().getAt(rowIndex);
		    			    	            	var panel = grid.up('panel');
		    			    	                panel.model.data=rec.data;
		    			    	            	panel.appEventsController.getApplication().fireEvent('deleteJournalEntry');
		    			    	            },
		    			    	            scope: this
		    			    	        }]
		    		                }],
				    
				    dockedItems: [{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{
				            tooltip: 'Respond to the selected Early Alert',
				            text: 'Respond',
				            xtype: 'button',
				            itemId: 'respondButton'
				        },{ 
				        	xtype: 'tbspacer',
				        	flex: 1
				        },{
				            tooltip: 'Print the History for this student',
				            text: 'View History',
				            xtype: 'button',
				            itemId: 'viewHistoryButton'
				        }]
				    }]
				});
		
		return this.callParent(arguments);
	}
});