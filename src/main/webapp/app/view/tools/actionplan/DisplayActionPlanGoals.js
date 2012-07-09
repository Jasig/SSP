Ext.define('Ssp.view.tools.actionplan.DisplayActionPlanGoals', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.displayactionplangoals',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.DisplayActionPlanGoalsViewController',
    inject: {
    	appEventsController: 'appEventsController',
    	authenticatedPerson: 'authenticatedPerson',
    	columnRendererUtils: 'columnRendererUtils',
    	model: 'currentGoal',
        store: 'goalsStore'
    },
    width: '100%',
	height: '100%',   
    layout: 'anchor',
    itemId: 'goalsPanel',
    defaults: {
        anchor: '100%'
    },	
	initComponent: function() {	
		var me=this;
    	var sm = Ext.create('Ext.selection.CheckboxModel');
		
		Ext.apply(me, {

				title: 'Goals',
				store: me.store,
				selModel: sm,
			    columns: [{
	    	        xtype:'actioncolumn',
	    	        width:65,
	    	        header: 'Action',
	    	        items: [{
	    	            icon: Ssp.util.Constants.GRID_ITEM_EDIT_ICON_PATH,
	    	            tooltip: 'Edit Goal',
	    	            handler: function(grid, rowIndex, colIndex) {
	    	            	var rec = grid.getStore().getAt(rowIndex);
	    	                var panel = grid.up('panel');
	    	                panel.model.data=rec.data;
	    	            	panel.appEventsController.getApplication().fireEvent('editGoal');
	    	            },
	    	            getClass: function(value, metadata, record)
                        {
	    	            	// hide if user does not have permission to edit
	    	            	var cls = 'x-hide-display';
	    	            	if ( me.authenticatedPerson.hasPermission('ROLE_PERSON_GOAL_WRITE') )
	    	            	{
	    	            		cls = Ssp.util.Constants.GRID_ITEM_EDIT_ICON_PATH;
	    	            	}
	    	            	
	    	            	return cls;                            
	    	            },
	    	            scope: me
	    	        },{
	    	            icon: Ssp.util.Constants.GRID_ITEM_DELETE_ICON_PATH,
	    	            tooltip: 'Delete Goal',
	    	            handler: function(grid, rowIndex, colIndex) {
	    	            	var rec = grid.getStore().getAt(rowIndex);
	    	            	var panel = grid.up('panel');
	    	                panel.model.data=rec.data;
	    	            	panel.appEventsController.getApplication().fireEvent('deleteGoal');
	    	            },
	    	            getClass: function(value, metadata, record)
                        {
	    	            	// hide if user does not have permission to delete
	    	            	var cls = 'x-hide-display';
	    	            	if ( me.authenticatedPerson.hasPermission('ROLE_PERSON_GOAL_DELETE') )
	    	            	{
	    	            		cls = Ssp.util.Constants.GRID_ITEM_EDIT_ICON_PATH;
	    	            	}
	    	            	
	    	            	return cls;                            
	    	            },
	    	            scope: me
	    	        }]
	    	    },{
	    	        header: 'Name',
	    	        flex: 1,
	    	        dataIndex: 'name',
	    	        renderer: me.columnRendererUtils.renderGoalName
	    	    },{
	    	        header: 'Confidentiality',
	    	        dataIndex: 'confidentialityLevel',
	    	        renderer: me.columnRendererUtils.renderConfidentialityLevelName
	    	    }],
	    	    
	    	    dockedItems: [{
			        dock: 'top',
			        xtype: 'toolbar',
			        items: [{
			            tooltip: 'Add a Goal',
			            text: 'Add',
			            xtype: 'button',
			            itemId: 'addGoalButton'
			        }]
	    	    }]
		});
		
		return me.callParent(arguments);
	}
});