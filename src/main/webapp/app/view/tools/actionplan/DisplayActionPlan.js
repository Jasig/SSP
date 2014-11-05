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
                type: 'fit'
            },
            title: 'Action Plan',
            autoScroll: true,
            padding: 0,
            
            items: [{
                xtype: 'tabpanel',
                activeTab: 0,
                minTabWidth: 120,
                
                style: 'ul.x-tab-strip-top{\n   padding-top: 1px;\n background: repeat-x bottom;\n  border-bottom: 1px solid;\n background-color: white;\n}',
                
                items: [
				{
                    xtype: 'tasks',
                    itemId: 'tasksPanel',
                    flex: 1,
                    width: '100%',
                    height: '100%',
                    autoScroll: true,
                    hidden: !me.authenticatedPerson.hasAccess('ACTION_PLAN_TASKS_PANEL')
                },
				{
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
            }]
        
        });
        
        return me.callParent(arguments);
    }
    
});
