Ext.define('Ssp.view.tools.actionplan.Tasks', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.tasks',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.TasksViewController',
    inject: {
    	appEventsController: 'appEventsController',
    	authenticatedPerson: 'authenticatedPerson',
    	columnRendererUtils: 'columnRendererUtils',
    	model: 'currentTask',
        store: 'tasksStore',
    },
    layout: 'auto',
	width: '100%',
    height: '100%',
    initComponent: function(){
    	var me=this;
    	var sm = Ext.create('Ext.selection.CheckboxModel');
    	
    	Ext.apply(me,
    			{
    		        scroll: 'vertical',
    	    		store: me.store,    		
    	    		selModel: sm,
    	    		features: [{
		    	        id: 'group',
		    	        ftype: 'grouping',
		    	        groupHeaderTpl: '{name}',
		    	        hideGroupedHeader: false,
		    	        enableGroupingMenu: false
		    	    }],
		
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
		    	            	panel.appEventsController.getApplication().fireEvent('editTask');
		    	            },
		    	            getClass: function(value, metadata, record)
                            {
		    	            	// completed items cannot be edited 
		    	            	// hide if completed or if user does not have permission to edit
		    	            	var cls = 'x-hide-display';
		    	            	if ( me.authenticatedPerson.hasPermission('ROLE_PERSON_TASK_WRITE') && record.get('completedDate') == null)
		    	            	{
		    	            		cls = Ssp.util.Constants.GRID_ITEM_CLOSE_ICON_PATH;
		    	            	}
		    	            	
		    	            	return cls;                            
		    	            },
		    	            scope: me
		    	        },{
		    	            icon: Ssp.util.Constants.GRID_ITEM_CLOSE_ICON_PATH,
		    	            tooltip: 'Close Task',
		    	            handler: function(grid, rowIndex, colIndex) {
		    	            	var rec = grid.getStore().getAt(rowIndex);
		    	            	var panel = grid.up('panel');
		    	                panel.model.data=rec.data;
		    	            	panel.appEventsController.getApplication().fireEvent('closeTask');
		    	            },
		    	            getClass: function(value, metadata, record)
                            {
		    	            	// completed items cannot be closed 
		    	            	// hide if completed or if user does not have permission to edit
		    	            	var cls = 'x-hide-display';
		    	            	if ( me.authenticatedPerson.hasPermission('ROLE_PERSON_TASK_WRITE') && record.get('completedDate') == null)
		    	            	{
		    	            		cls = Ssp.util.Constants.GRID_ITEM_CLOSE_ICON_PATH;
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
		    	            	panel.appEventsController.getApplication().fireEvent('deleteTask');
		    	            },
		    	            getClass: function(value, metadata, record)
                            {
		    	            	// completed items cannot be deleted 
		    	            	// hide if completed or if user does not have permission to delete
		    	            	var cls = 'x-hide-display';
		    	            	if ( me.authenticatedPerson.hasPermission('ROLE_PERSON_TASK_DELETE') && record.get('completedDate') == null)
		    	            	{
		    	            		cls = Ssp.util.Constants.GRID_ITEM_CLOSE_ICON_PATH;
		    	            	}
		    	            	
		    	            	return cls;
                            },
		    	            scope: me
		    	        }]
		    	    },{
		    	        text: 'Description',
		    	        flex: 1,
		    	        tdCls: 'task',
		    	        sortable: true,
		    	        dataIndex: 'name',
		    	        renderer: me.columnRendererUtils.renderTaskName
		    	    },{
		    	        header: 'Due Date',
		    	        width: 150,
		    	        dataIndex: 'dueDate',
		    	        renderer: me.columnRendererUtils.renderTaskDueDate
		    	    }]
    	

    			});
    	
    	return me.callParent(arguments);
    }
});