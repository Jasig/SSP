Ext.define('Ssp.view.tools.actionplan.DisplayActionPlan', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.displayactionplan',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.DisplayActionPlanViewController',
    inject: {
    	authenticatedPerson: 'authenticatedPerson'
    },
    width: '100%',
	height: '100%',
	padding: 0,
	initComponent: function() {	
		var me=this;
		Ext.apply(me, 
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
						    hidden: !me.authenticatedPerson.hasAccess('ACTION_PLAN_TASKS_PANEL'),
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
								            hidden: !me.authenticatedPerson.hasAccess('ADD_TASK_BUTTON'),
								            xtype: 'button',
								            itemId: 'addTaskButton'
								        }]
						    	    }]
						})
						,{
						  xtype: 'displayactionplangoals',
						  itemId: 'goalsPanel',
						  flex: 1,
						  hidden: !me.authenticatedPerson.hasAccess('ACTION_PLAN_GOALS_PANEL')
						 }
						,{
						   xtype: 'displaystrengths', 
						   itemId: 'strengthsPanel',
						   hidden: !me.authenticatedPerson.hasAccess('ACTION_PLAN_STRENGTHS_PANEL')
						 }
				    ],
				    
				    dockedItems: [{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{
					            tooltip: 'Email Action Plan',
					            text: '',
					            width: 30,
					            height: 30,
					            hidden: !me.authenticatedPerson.hasAccess('EMAIL_ACTION_PLAN_BUTTON'),
					            cls: 'emailIcon',
					            xtype: 'button',
					            itemId: 'emailTasksButton'
					        },{
					            tooltip: 'Print Action Plan',
					            text: '',
					            width: 30,
					            height: 30,
					            hidden: !me.authenticatedPerson.hasAccess('PRINT_ACTION_PLAN_BUTTON'),
					            cls: 'printIcon',
					            xtype: 'button',
					            itemId: 'printTasksButton'
					        },{ 
					        	xtype: 'tbspacer',
					        	flex: 1
					        },{
					            xtype: 'checkbox',
					            boxLabel: 'Display only tasks that I created',
					            hidden: !me.authenticatedPerson.hasAccess('FILTER_TASKS_BY_AUTHENTICATED_USER_CHECKBOX'),
					            itemId: 'filterTasksBySelfCheck'
					        }]
				    }]
				});
	
		return me.callParent(arguments);
	}
		
});