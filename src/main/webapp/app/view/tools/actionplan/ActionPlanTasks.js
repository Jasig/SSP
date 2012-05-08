Ext.define('Ssp.view.tools.actionplan.ActionPlanTasks', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.actionplantasks',
	id: 'ActionPlanTasks',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.ActionPlanTasksViewController',
    width: '100%',
	height: '100%',   
	
	initComponent: function() {	
		Ext.apply(this, 
				{
		            autoScroll: true,
		            title: 'Action Plan Tasks',
					items: [
						Ext.createWidget('tabpanel', {
						    width: '100%',
						    height: '100%',
						    activeTab: 0,
						    itemId: 'taskStatusTabs',
						    items: [{ 
						    	      title: 'Active',
						    		  autoScroll: true,
						    		  action: 'active',
						    		  items: [{xtype: 'tasks'}]
						    		},{ 
						    		  title: 'Complete',
						    		  autoScroll: true,
						    		  action: 'complete',
						    		  items: [{xtype: 'tasks'}]
						    		},{ 
						    		  title: 'All',
						    		  autoScroll: true,
						    		  action: 'all',
						    		  items: [{xtype: 'tasks'}]
						    		}]
						})
				    ],
				    
				    dockedItems: [{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{
				            xtype: 'checkbox',
				            boxLabel: 'Display only items that I created',
				            itemId: 'filterTasksBySelfCheck'
				           },{ 
				        	   xtype: 'tbspacer',
				        	   flex: 1
				           },{
				            tooltip: 'Print the History for this student',
				            text: 'View History',
				            xtype: 'button',
				            itemId: 'viewHistoryButton'
				        }]
				    },{
				        dock: 'bottom',
				        xtype: 'toolbar',
				        items: [{
				            tooltip: 'Add a task',
				            text: 'Add',
				            xtype: 'button',
				            itemId: 'addTasksButton'
				        },{
				            tooltip: 'Close selected tasks',
				            text: 'Close',
				            xtype: 'button',
				            itemId: 'closeTasksButton'
				        },{
				            tooltip: 'Delete selected tasks',
				            text: 'Delete',
				            xtype: 'button',
				            itemId: 'deleteTasksButton'
				        },{
				            tooltip: 'Email selected tasks',
				            text: 'Email',
				            xtype: 'button',
				            itemId: 'emailTasksButton'
				        },{
				            tooltip: 'Print selected tasks',
				            text: 'Print',
				            xtype: 'button',
				            itemId: 'printTasksButton'
				        }]
				    }]
				});
	
		return this.callParent(arguments);
	}
		
});