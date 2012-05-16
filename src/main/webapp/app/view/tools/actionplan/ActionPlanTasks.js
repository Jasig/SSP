Ext.define('Ssp.view.tools.actionplan.ActionPlanTasks', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.actionplantasks',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.ActionPlanTasksViewController',
    width: '100%',
	height: '100%',   
	
	initComponent: function() {	
		Ext.apply(this, 
				{
		            autoScroll: true,
		            title: 'Action Plan',
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
						,{xtype: 'actionplangoals'}
				    ],
				    
				    dockedItems: [{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{
				            xtype: 'checkbox',
				            boxLabel: 'Display only items that I created',
				            itemId: 'filterTasksBySelfCheck'
				           }]
				    },{
				        dock: 'bottom',
				        xtype: 'toolbar',
				        items: [{
				            tooltip: 'Email selected tasks',
				            text: 'Email',
				            xtype: 'button',
				            itemId: 'emailTasksButton'
				        },{
				            tooltip: 'Print selected tasks',
				            text: 'Print',
				            xtype: 'button',
				            itemId: 'printTasksButton'
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