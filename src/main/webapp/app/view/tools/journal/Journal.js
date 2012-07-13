Ext.define('Ssp.view.tools.journal.Journal', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.journal',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.journal.JournalToolViewController',
    inject: {
    	appEventsController: 'appEventsController',
    	authenticatedPerson: 'authenticatedPerson',
    	columnRendererUtils: 'columnRendererUtils',
    	model: 'currentJournalEntry',
        store: 'journalEntriesStore'
    },
	width: '100%',
	height: '100%',
	initComponent: function() {	
		var me=this;
    	var sm = Ext.create('Ext.selection.CheckboxModel');
		Ext.apply(me, 
				{
		            autoScroll: true,
		            title: 'Journal',
		            store: me.store,
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
					    	            getClass: function(value, metadata, record)
			                            {
					    	            	// hide user does not have permission to edit
					    	            	var cls = 'x-hide-display';
					    	            	if ( me.authenticatedPerson.hasAccess('EDIT_JOURNAL_ENTRY_BUTTON') )
					    	            	{
					    	            		cls = Ssp.util.Constants.GRID_ITEM_EDIT_ICON_PATH;
					    	            	}
					    	            	
					    	            	return cls;                            
					    	            },
					    	            scope: me
					    	        },{
					    	            icon: Ssp.util.Constants.GRID_ITEM_DELETE_ICON_PATH,
					    	            tooltip: 'Delete Task',
					    	            handler: function(grid, rowIndex, colIndex) {
					    	            	var rec = grid.getStore().getAt(rowIndex);
					    	            	var panel = grid.up('panel');
					    	                panel.model.data=rec.data;
					    	            	panel.appEventsController.getApplication().fireEvent('deleteJournalEntry');
					    	            },
					    	            getClass: function(value, metadata, record)
			                            {
					    	            	// hide user does not have permission to delete
					    	            	var cls = 'x-hide-display';
					    	            	if ( me.authenticatedPerson.hasAccess('DELETE_JOURNAL_ENTRY_BUTTON') )
					    	            	{
					    	            		cls = Ssp.util.Constants.GRID_ITEM_DELETE_ICON_PATH;
					    	            	}
					    	            	
					    	            	return cls;                            
					    	            },
					    	            scope: me
					    	        }]
				                },
	    		                { header: 'Date',  
		    		                  dataIndex: 'entryDate',
		    		                  flex: 1,
		    		                  renderer: Ext.util.Format.dateRenderer('m/d/Y')
	    		                },
	    		                { header: 'Entered By',  
	    		                  dataIndex: 'createdBy',
	    		                  flex: 1,
	    		                  renderer: me.columnRendererUtils.renderCreatedBy
	    		                },
	      		                { header: 'Source',
	      		                  dataIndex: 'journalSource', 
	      		                  flex: 1,
	      		                  renderer: me.columnRendererUtils.renderJournalSourceName
	    		                },
	      		                { header: 'Confidentiality',
	      		                  dataIndex: 'confidentialityLevel', 
	      		                  flex: 1,
	      		                  renderer: me.columnRendererUtils.renderConfidentialityLevelName
	    		                }],
				    
				    dockedItems: [{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{
				            tooltip: 'Add Journal Note',
				            text: 'Add',
				            xtype: 'button',
				            hidden: !me.authenticatedPerson.hasAccess('ADD_JOURNAL_ENTRY_BUTTON'),
				            itemId: 'addButton'
				        }]
				    }]
				});
		
		return me.callParent(arguments);
	}
});