/*
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
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
        store: 'tasksStore'
    },
    layout: 'fit',
	width: '100%',
    height: '100%',
	itemId: 'tasksPanel',
	dueDateMsg: 'Task due dates are always interpreted in the institution\'s time zone.',
	dueDateRenderer: function() {
		var me = this;
		return function(value,metaData,record) {
			metaData.tdAttr = 'data-qtip="' + me.dueDateMsg + '"';
			return me.columnRendererUtils.renderTaskDueDate(value,metaData,record);
		}
	},
	fullDescription: function() {
		var me = this;
		
		return function(value,metaData,record) {
			var fullDesc = record.get('description');
			metaData.tdAttr = 'data-qtip="' + fullDesc + '"';
			return me.columnRendererUtils.renderTaskName(value,metaData,record);
		}
	},
    initComponent: function(){
    	var me=this;
    	var sm = Ext.create('Ext.selection.CheckboxModel');
    	Ext.apply(me,
    			{
    		        scroll: 'vertical',
    	    		store: me.store,    		
    	    		selModel: sm,
					title: 'Tasks',
					cls: 'tasksgrid',
		    	    columns: [{
		    	        xtype:'actioncolumn',
		    	        width:68,
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
		    	            	if ( me.authenticatedPerson.hasAccess('EDIT_TASK_BUTTON') && record.get('completedDate') == null)
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
		    	            	if ( me.authenticatedPerson.hasAccess('CLOSE_TASK_BUTTON') && record.get('completedDate') == null)
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
		    	            	if ( me.authenticatedPerson.hasAccess('DELETE_TASK_BUTTON') && record.get('completedDate') == null)
		    	            	{
		    	            		cls = Ssp.util.Constants.GRID_ITEM_CLOSE_ICON_PATH;
		    	            	}
		    	            	
		    	            	return cls;
                            },
		    	            scope: me
		    	        }]
		    	    },{
		    	        text: 'Challenge',
		    	        flex: 0.20,
		    	        sortable: true,
		    	        dataIndex: 'group',
						renderer: function(value, metaData, record, rowIndex, colIndex, store, view){
	                        var tpl = new Ext.Template('<div class="wrappable-cell">{NAME}</div>');
	                        
	                        return tpl.apply({
	                            NAME: record.get('group')
	                        });
	                    }
		    	    },
					{
		    	        text: 'Description',
		    	        flex: 1,
		    	        sortable: true,
		    	        dataIndex: 'name',
		    	        renderer: me.fullDescription()
		    	    },{
		    	        header: 'Due Date',
		    	        width: 80,
		    	        dataIndex: 'dueDate',
		    	        renderer: me.dueDateRenderer(),
						listeners: {
							render: function(field){
								Ext.create('Ext.tip.ToolTip',{
									target: field.getEl(),
									html: me.dueDateMsg
								});
							}
						}
		    	    },
					{
		    	        text: 'Status',
		    	        width: 80,
		    	        sortable: true,
		    	        dataIndex: 'completedDate',
		    	        renderer: me.columnRendererUtils.renderTaskStatus
		    	    },
					{
		    	        text: 'Completed Date',
		    	        width: 90,
		    	        sortable: true,
		    	        dataIndex: 'completedDate',
		    	        renderer: me.columnRendererUtils.renderCompletedDate
		    	    },
					{
		    	        text: 'Author',
		    	        flex: 0.20,
		    	        sortable: true,
		    	        dataIndex: 'name',
		    	        renderer: me.columnRendererUtils.renderTaskAuthor
		    	    },
					{
		    	        text: 'Confidentiality',
		    	        flex: 0.20,
		    	        sortable: true,
		    	        dataIndex: 'name',
		    	        renderer: me.columnRendererUtils.renderTaskCFLevel
		    	    }
					
					],
					dockedItems: [{
                            dock: 'top',
                            xtype: 'toolbar',
                            items: [{
                                text: 'Check All',
                                xtype: 'button',
                                itemId: 'selectAllTaskButton'
                            }, {
                                tooltip: 'Add a Task',
                                text: 'Add Task',
                                hidden: !me.authenticatedPerson.hasAccess('ADD_TASK_BUTTON'),
                                xtype: 'button',
                                itemId: 'addTaskButton'
                            }, {
                                xtype: 'emailandprintactionplan'
                            }, {
                                xtype: 'tbspacer',
                                flex: 0.50
                            }, 
							{
								xtype: 'label',
								text: 'Show Only:',
								padding: '5 10 5 5'
							},{
                                xtype: 'checkbox',
                                boxLabel: 'Tasks I created',
                                hidden: !me.authenticatedPerson.hasAccess('FILTER_TASKS_BY_AUTHENTICATED_USER_CHECKBOX'),
                                itemId: 'filterTasksBySelfCheck'
                            },
							{
                                xtype: 'tbspacer',
                                width: 10
                            }, 
							{
                                xtype: 'checkbox',
                                boxLabel: 'Incomplete Tasks',
                                itemId: 'filterTasksByIncompleteCheck'
                            },
							{
                                xtype: 'tbspacer',
                                flex: 0.05
                            }
							]
                        }, {
                            xtype: 'toolbar',
                            dock: 'top',
                            items: [{
                                xtype: 'label',
                                text: 'By default, all items are included in the Print and Email functions. Select items to Print or Email individual items'
                            }]
                        }]
    	

    			});
    	
    	return me.callParent(arguments);
    }
});