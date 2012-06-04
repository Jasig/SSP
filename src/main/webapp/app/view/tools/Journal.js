Ext.define('Ssp.view.tools.Journal', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.journal',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.journal.JournalToolViewController',
    inject: {
    	appEventsController: 'appEventsController',
    	columnRendererUtils: 'columnRendererUtils',
    	model: 'currentJournalEntry',
        store: 'journalEntriesStore'
    },
	width: '100%',
	height: '100%',
	initComponent: function() {	
    	var sm = Ext.create('Ext.selection.CheckboxModel');

		Ext.apply(this, 
				{
		            autoScroll: true,
		            title: 'Journal',
		            store: this.store,
	    		      columns: [
	    		                { header: 'Entered By',  
	    		                  dataIndex: 'createdBy',
	    		                  flex: 1,
	    		                  renderer: this.columnRendererUtils.renderCreatedByDateAndName
	    		                },
	      		                { header: 'Journal Source',
	      		                  dataIndex: 'journalSource', 
	      		                  flex: 1,
	      		                  renderer: this.columnRendererUtils.renderJournalSourceName
	    		                },
	      		                { header: 'Confidentiality',
	      		                  dataIndex: 'confidentialityLevel', 
	      		                  flex: 1,
	      		                  renderer: this.columnRendererUtils.renderConfidentialityLevelName
	    		                },{
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
	    		                }],
				    
				    dockedItems: [{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{
				            tooltip: 'Add Journal Note',
				            text: 'Add',
				            xtype: 'button',
				            itemId: 'addButton'
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