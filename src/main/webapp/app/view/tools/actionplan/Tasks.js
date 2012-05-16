Ext.define('Ssp.view.tools.actionplan.Tasks', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.tasks',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.TasksViewController',
    inject: {
    	appEventsController: 'appEventsController',
    	columnRendererUtils: 'columnRendererUtils',
        store: 'tasksStore',
    },
	width: '100%',
    height: '100%',
    initComponent: function(){
    	
    	var sm = Ext.create('Ext.selection.CheckboxModel');
    	
    	Ext.apply(this,
    			{
    	    		store: this.store,    		
    	    		selModel: sm,
    	    		features: [{
		    	        id: 'group',
		    	        ftype: 'groupingsummary',
		    	        groupHeaderTpl: '{name}',
		    	        hideGroupedHeader: true,
		    	        enableGroupingMenu: false
		    	    }],
		
		    	    columns: [{
		    	        text: 'Task',
		    	        flex: 1,
		    	        tdCls: 'task',
		    	        sortable: true,
		    	        dataIndex: 'name',
		    	        renderer: this.columnRendererUtils.renderTaskName
		    	    },{
		    	        header: 'Due Date',
		    	        width: 100,
		    	        dataIndex: 'dueDate',
		    	        renderer: this.columnRendererUtils.renderTaskDueDate
		    	    },{
		    	        xtype:'actioncolumn',
		    	        width:100,
		    	        items: [{
		    	            icon: 'images/edit-icon.jpg',
		    	            tooltip: 'Edit',
		    	            handler: function(grid, rowIndex, colIndex) {
		    	            	var rec = grid.getStore().getAt(rowIndex);
		    	                grid.up('panel').appEventsController.getApplication().fireEvent('editTask', { record: rec });
		    	            },
		    	            scope: this
		    	        },{
		    	            icon: 'images/close-icon.jpg',
		    	            tooltip: 'Close',
		    	            handler: function(grid, rowIndex, colIndex) {
		    	            	var rec = grid.getStore().getAt(rowIndex);
		    	                grid.up('panel').appEventsController.getApplication().fireEvent('closeTask', { record: rec });
		    	            },
		    	            scope: this
		    	        },{
		    	            icon: 'images/delete-icon.png',
		    	            tooltip: 'Delete',
		    	            handler: function(grid, rowIndex, colIndex) {
		    	            	var rec = grid.getStore().getAt(rowIndex);
		    	                grid.up('panel').appEventsController.getApplication().fireEvent('deleteTask', { record: rec });
		    	            },
		    	            scope: this
		    	        }]
		    	    }],
		    	    
		    	    dockedItems: [{
				        dock: 'bottom',
				        xtype: 'toolbar',
				        items: [{
				            tooltip: 'Add a task',
				            text: 'Add',
				            xtype: 'button',
				            itemId: 'addTaskButton'
				        }]
		    	    }]
    	

    			});
    	
    	return this.callParent(arguments);
    }
});