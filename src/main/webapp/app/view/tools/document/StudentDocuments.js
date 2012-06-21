Ext.define('Ssp.view.tools.document.StudentDocuments', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.studentdocuments',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.document.StudentDocumentToolViewController',
    inject: {
    	appEventsController: 'appEventsController',
    	columnRendererUtils: 'columnRendererUtils',
    	model: 'currentDocument',
        store: 'documentsStore'
    },
	title: 'Student Documents',	
	width: '100%',
	height: '100%',   
	autoScroll: true,
    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
			border: 0,
			store: this.store,
			dockedItems: [{
		        dock: 'top',
		        xtype: 'toolbar',
		        items: [{
		        			xtype: 'button', 
		        			itemId: 'addButton', 
		        			text:'Add', 
		        			action: 'add'
		        	   },{
		        			xtype: 'button', 
		        			itemId: 'downloadButton', 
		        			text:'Download', 
		        			action: 'download'
		        	   }]
			}],
			
        	columns: [
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'createdDate',
                    text: 'Date Entered'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'name',
                    text: 'Name'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'note',
                    text: 'Note',
                    flex: 1
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
	    	                panel.appEventsController.getApplication().fireEvent('editDocument');
	    	            },
	    	            scope: this
	    	        },{
	    	            icon: Ssp.util.Constants.GRID_ITEM_DELETE_ICON_PATH,
	    	            tooltip: 'Delete Task',
	    	            handler: function(grid, rowIndex, colIndex) {
	    	            	var rec = grid.getStore().getAt(rowIndex);
	    	            	var panel = grid.up('panel');
	    	                panel.model.data=rec.data;
	    	            	panel.appEventsController.getApplication().fireEvent('deleteDocument');
	    	            },
	    	            scope: this
	    	        }]
                }],
                
            viewConfig: {

            }
        });

        me.callParent(arguments);
    }
});