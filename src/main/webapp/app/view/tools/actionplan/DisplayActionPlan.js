Ext.define('Ssp.view.tools.actionplan.DisplayActionPlan', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.displayactionplan',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.DisplayActionPlanViewController',
    inject: {
    	person: 'currentPerson'
    },
    width: '100%',
	height: '100%',
	padding: 0,
	initComponent: function() {	
		Ext.apply(this, 
				{
		    	    layout: {
		    	        type: 'accordion',
		    	        titleCollapse: true,
		    	        animate: true,
		    	        activeOnTop: true
		    	    },
		            title: 'Action Plan',
		            autoScroll: true,
		            padding: 0,
					items: [
						Ext.createWidget('tabpanel', {
						    width: '100%',
						    height: '100%',
						    activeTab: 0,
						    title: 'Tasks',
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
						,{xtype: 'displayactionplangoals', flex: 1}
						,{xtype: 'displaystrengths'}
				    ],
				    
				    dockedItems: [{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{
				            xtype: 'checkbox',
				            boxLabel: 'Display only tasks that I created',
				            itemId: 'filterTasksBySelfCheck'
				           },{ 
					        	xtype: 'tbspacer',
					        	flex: 1
					        },{
					            tooltip: 'Email Action Plan',
					            text: 'Email',
					            xtype: 'button',
					            itemId: 'emailTasksButton'
					        },{
					            tooltip: 'Print Action Plan',
					            text: 'Print',
					            xtype: 'button',
					            itemId: 'printTasksButton'
					        },{
					            tooltip: 'View Student History',
					            text: 'View History',
					            xtype: 'button',
					            itemId: 'viewHistoryButton'
					        }]
				    }]
				});
	
		return this.callParent(arguments);
	}
		
});