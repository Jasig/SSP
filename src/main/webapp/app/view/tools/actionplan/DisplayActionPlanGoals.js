Ext.define('Ssp.view.tools.actionplan.DisplayActionPlanGoals', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.displayactionplangoals',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.DisplayActionPlanGoalsViewController',
    inject: {
    	appEventsController: 'appEventsController',
    	columnRendererUtils: 'columnRendererUtils',
    	model: 'currentGoal',
        store: 'goalsStore'
    },
    width: '100%',
	height: '100%',   
    layout: 'anchor',
    defaults: {
        anchor: '100%'
    },	
	initComponent: function() {	
		
    	var sm = Ext.create('Ext.selection.CheckboxModel');
		
		Ext.apply(this, {

				title: 'Goals',
				store: this.store,
				selModel: sm,
				
			    columns: [{
	    	        header: 'Name',
	    	        flex: 1,
	    	        dataIndex: 'name',
	    	        renderer: this.columnRendererUtils.renderGoalName
	    	    },{
	    	        header: 'Confidentiality',
	    	        dataIndex: 'confidentialityLevel',
	    	        renderer: this.columnRendererUtils.renderConfidentialityLevelName
	    	    },{
	    	        xtype:'actioncolumn',
	    	        width:65,
	    	        header: 'Action',
	    	        items: [{
	    	            icon: 'images/edit-icon.jpg',
	    	            tooltip: 'Edit Goal',
	    	            handler: function(grid, rowIndex, colIndex) {
	    	            	var rec = grid.getStore().getAt(rowIndex);
	    	                var panel = grid.up('panel');
	    	                panel.model.data=rec.data;
	    	            	panel.appEventsController.getApplication().fireEvent('editGoal');
	    	            },
	    	            scope: this
	    	        },{
	    	            icon: 'images/delete-icon.png',
	    	            tooltip: 'Delete Goal',
	    	            handler: function(grid, rowIndex, colIndex) {
	    	            	var rec = grid.getStore().getAt(rowIndex);
	    	            	var panel = grid.up('panel');
	    	                panel.model.data=rec.data;
	    	            	panel.appEventsController.getApplication().fireEvent('deleteGoal');
	    	            },
	    	            scope: this
	    	        }]
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
		
		return this.callParent(arguments);
	}
});