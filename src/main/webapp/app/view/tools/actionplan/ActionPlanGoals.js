Ext.define('Ssp.view.tools.actionplan.ActionPlanGoals', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.actionplangoals',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.ActionPlanGoalsViewController',
    inject: {
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
	    	        dataIndex: 'name'
	    	    },{
	    	        header: 'Description',
	    	        flex: 1,
	    	        dataIndex: 'description'
	    	    },{
	    	        header: 'Confidentiality',
	    	        flex: 1,
	    	        dataIndex: 'confidentialityLevelId'
	    	    }],
	    	    
	    	    dockedItems: [{
			        dock: 'top',
			        xtype: 'toolbar',
			        items: [{
			            tooltip: 'Add a Goal',
			            text: 'Add',
			            xtype: 'button',
			            itemId: 'addGoalButton'
			        },{
			            tooltip: 'Edit a Goal',
			            text: 'Edit',
			            xtype: 'button',
			            itemId: 'editGoalButton'
			        },{
			            tooltip: 'Delete a Goal',
			            text: 'Delete',
			            xtype: 'button',
			            itemId: 'deleteGoalButton'
			        }]
	    	    }]
		});
		
		return this.callParent(arguments);
	}
});