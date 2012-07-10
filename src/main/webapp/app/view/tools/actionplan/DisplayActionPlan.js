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
						    		  items: [{xtype: 'tasks', itemId:'activeTasksGrid'}]
						    		},{ 
						    		  title: 'Complete',
						    		  autoScroll: true,
						    		  action: 'complete',
						    		  items: [{xtype: 'tasks', itemId:'completeTasksGrid'}]
						    		},{ 
						    		  title: 'All',
						    		  autoScroll: true,
						    		  action: 'all',
						    		  items: [{xtype: 'tasks', itemId:'allTasksGrid'}]
						    		}],
						    	    
						    	    dockedItems: [{
								        dock: 'top',
								        xtype: 'toolbar',
								        items: [{
								            tooltip: 'Add a Task',
								            text: 'Add',
								            xtype: 'button',
								            itemId: 'addTaskButton'
								        }]
						    	    }]
						})
						,{xtype: 'displayactionplangoals', itemId: 'goalsPanel', flex: 1}
						,{xtype: 'displaystrengths', itemId: 'strengthsPanel'}
				    ],
				    
				    dockedItems: [{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{
					            tooltip: 'Email Action Plan',
					            text: '',
					            width: 30,
					            height: 30,
					            cls: 'emailIcon',
					            xtype: 'button',
					            itemId: 'emailTasksButton'
					        },{
					            tooltip: 'Print Action Plan',
					            text: '',
					            width: 30,
					            height: 30,
					            cls: 'printIcon',
					            xtype: 'button',
					            itemId: 'printTasksButton'
					        },{ 
					        	xtype: 'tbspacer',
					        	flex: 1
					        },{
					            xtype: 'checkbox',
					            boxLabel: 'Display only tasks that I created',
					            itemId: 'filterTasksBySelfCheck'
					        }]
				    }]
				});
	
		return this.callParent(arguments);
	}
		
});