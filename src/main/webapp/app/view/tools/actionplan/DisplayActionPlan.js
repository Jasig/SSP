/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
Ext.define('Ssp.view.tools.actionplan.DisplayActionPlan', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.displayactionplan',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.DisplayActionPlanViewController',
    inject: {
        authenticatedPerson: 'authenticatedPerson'
    },
    width: '100%',
    height: '100%',
    padding: 0,
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            layout: {
                type: 'fit',
            
            },
            title: 'Action Plan',
            autoScroll: true,
            padding: 0,
			
            items: [{
                xtype: 'tabpanel',
                activeTab: 0,
				minTabWidth: 120,
			
				style: 'ul.x-tab-strip-top{\n   padding-top: 1px;\n background: repeat-x bottom;\n  border-bottom: 1px solid;\n background-color: white;\n}',
                   
                items: [{
                    xtype: 'panel',
                    title: 'Tasks',
                    width: '100%',
                    height: '100%',
                    autoScroll: true,
                    items: [{
                        xtype: 'tabpanel',
                        activeTab: 0,
                        hidden: !me.authenticatedPerson.hasAccess('ACTION_PLAN_TASKS_PANEL'),
						padding: '2 0 0 2',
                        //title: 'Tasks',
                        itemId: 'taskStatusTabs',
                        items: [{
                            title: 'Active',
                            autoScroll: true,
                            action: 'active',
                            items: [{
                                xtype: 'tasks',
                                itemId: 'activeTasksGrid'
                            }]
                        }, {
                            title: 'Complete',
                            autoScroll: true,
                            action: 'complete',
                            items: [{
                                xtype: 'tasks',
                                itemId: 'completeTasksGrid'
                            }]
                        }, {
                            title: 'All',
                            autoScroll: true,
                            action: 'all',
                            items: [{
                                xtype: 'tasks',
                                itemId: 'allTasksGrid'
                            }]
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
                    }]
                }, {
                    xtype: 'displayactionplangoals',
                    itemId: 'goalsPanel',
                    flex: 1,
                    width: '100%',
                    height: '100%',
                    autoScroll: true,
                    hidden: !me.authenticatedPerson.hasAccess('ACTION_PLAN_GOALS_PANEL')
                }, {
                    xtype: 'displaystrengths',
                    itemId: 'strengthsPanel',
                    width: '100%',
                    height: '100%',
                    autoScroll: true,
                    hidden: !me.authenticatedPerson.hasAccess('ACTION_PLAN_STRENGTHS_PANEL')
                }]
            }],
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
                }, {
                    tooltip: 'Print Action Plan',
                    text: '',
                    width: 30,
                    height: 30,
                    hidden: !me.authenticatedPerson.hasAccess('PRINT_ACTION_PLAN_BUTTON'),
                    cls: 'printIcon',
                    xtype: 'button',
                    itemId: 'printTasksButton'
                }, {
                    xtype: 'tbspacer',
                    flex: 1
                }, {
                    xtype: 'checkbox',
                    boxLabel: 'Display only tasks that I created',
                    hidden: !me.authenticatedPerson.hasAccess('FILTER_TASKS_BY_AUTHENTICATED_USER_CHECKBOX'),
                    itemId: 'filterTasksBySelfCheck'
                }]
            }]
            /*items: [
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
             }]*/
        });
        
        return me.callParent(arguments);
    }
    
});
