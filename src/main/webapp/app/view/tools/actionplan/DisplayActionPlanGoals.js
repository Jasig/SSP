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
Ext.define('Ssp.view.tools.actionplan.DisplayActionPlanGoals', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.displayactionplangoals',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.DisplayActionPlanGoalsViewController',
    inject: {
        appEventsController: 'appEventsController',
        authenticatedPerson: 'authenticatedPerson',
        columnRendererUtils: 'columnRendererUtils',
        model: 'currentGoal',
        store: 'goalsStore',
        confidentialityLevelsAllUnpagedStore: 'confidentialityLevelsAllUnpagedStore'
    },
    width: '100%',
    height: '100%',
    autoScroll: true,
    layout: 'fit',
    itemId: 'goalsPanel',
    
    initComponent: function(){
        var me = this;
        
        var cellEditor = Ext.create('Ext.grid.plugin.RowEditing', {
            clicksToEdit: 2,
            listeners: {
                cancelEdit: function(rowEditor, item){
                    var columns = rowEditor.grid.columns;
                    var record = rowEditor.context.record;
                    if (record.get('id') == '' || record.get('id') == null || record.get('id') == undefined) {
                        me.store.load();
                    }
                }
            }
        });
        
        Ext.apply(me, {
            plugins: cellEditor,
            selType: 'rowmodel',
            cls: 'configgrid',
            title: 'Goals',
            store: me.store,
            viewConfig: {
                markDirty: false
            },
            columns: [{
                header: 'Name',
                flex: 0.25,
                dataIndex: 'name',
                rowEditable: true,
                field: {
                    xtype: 'textfield',
                    fieldStyle: "margin-bottom:12px;",
					maxLength: 80,
					allowBlank: false
                }
            }, {
                header: 'Description',
                flex: 0.50,
                dataIndex: 'description',
                rowEditable: true,
                field: {
                    xtype: 'textfield',
                    fieldStyle: "margin-bottom:12px;",
					maxLength: 2000,
					allowBlank: false
                }
            }, {
                header: 'Confidentiality',
                dataIndex: 'confidentialityLevel',
                itemId: 'goalsCFCombo',
                renderer: me.columnRendererUtils.renderConfidentialityLevel,
                required: true,
                flex: 0.25,
                field: {
                    xtype: 'combo',
                    store: me.confidentialityLevelsAllUnpagedStore,
                    displayField: 'name',
                    valueField: 'id',
                    forceSelection: true
                }
            
            
            
            }],
            
            dockedItems: [{
                dock: 'top',
                xtype: 'toolbar',
                items: [{
                    tooltip: 'Add a Goal',
                    text: 'Add Goal',
                    hidden: !me.authenticatedPerson.hasAccess('ADD_GOAL_BUTTON'),
                    xtype: 'button',
                    itemId: 'addGoalButton'
                }, {
                    tooltip: 'Delete a Goal',
                    text: 'Delete Goal',
                    hidden: !me.authenticatedPerson.hasAccess('DELETE_GOAL_BUTTON'),
                    xtype: 'button',
                    itemId: 'deleteGoalButton'
                }, {
                    xtype: 'tbspacer',
                    width: '200'
                }, {
                    xtype: 'emailandprintactionplan'
                }]
            }, {
                xtype: 'toolbar',
                dock: 'top',
                items: [{
                    xtype: 'label',
                    text: 'Double-click to edit a goal'
                }]
            }]
        });
        
        return me.callParent(arguments);
    }
});
