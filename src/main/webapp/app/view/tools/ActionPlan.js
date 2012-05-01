Ext.define('Ssp.view.tools.ActionPlan', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.actionplan',
	id: 'ActionPlan',
    title: 'Action Plan',
	width: '100%',
	height: '100%',   

	initComponent: function() {	
		Ext.apply(this, 
				{
		            autoScroll: true,
	
					items: [
						Ext.createWidget('tabpanel', {
						    width: '100%',
						    height: '100%',
						    activeTab: 0,
						    items: [{ 
						    	      title: 'Active',
						    		  autoScroll: true,
						    		  items: [Ext.create('Ssp.view.tools.actionplan.ActiveTasks')]
						    		},{ 
						    		  title: 'Complete',
						    		  autoScroll: true,
						    		  items: [Ext.create('Ssp.view.tools.actionplan.ActiveTasks')]
						    		},{ 
						    		  title: 'All',
						    		  autoScroll: true,
						    		  items: [Ext.create('Ssp.view.tools.actionplan.ActiveTasks')]
						    		}]
						})
				    ],
				    
				    dockedItems: [{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{
				            xtype: 'checkbox',
				            boxLabel: 'Display only items that I created'
				           },{
				            tooltip: 'Print the History for this student',
				            text: 'View History',
				            handler: function(){}
				        }]
				    },{
				        dock: 'bottom',
				        xtype: 'toolbar',
				        items: [{
				            tooltip: 'Add a task',
				            text: 'Add',
				            handler: function(){}
				        },{
				            tooltip: 'Close selected tasks',
				            text: 'Close',
				            handler: function(){}
				        },{
				            tooltip: 'Delete selected tasks',
				            text: 'Delete',
				            handler: function(){}
				        },{
				            tooltip: 'Email selected tasks',
				            text: 'Email',
				            handler: function(){}
				        },{
				            tooltip: 'Print selected tasks',
				            text: 'Print',
				            handler: function(){}
				        }]
				    }]
				});
	
		this.callParent(arguments);
	}
		
});