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
Ext.define('Ssp.view.tools.actionplan.TasksGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.tasksgrid',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.TasksGridViewController',
    inject: {
        appEventsController: 'appEventsController',
        authenticatedPerson: 'authenticatedPerson',
        columnRendererUtils: 'columnRendererUtils',
        model: 'currentTask',
        store: 'addTasksStore',
        confidentialityLevelsAllUnpagedStore: 'confidentialityLevelsAllUnpagedStore'
    },
    width: '100%',
    height: '100%',
    autoScroll: true,
    layout: 'fit',
    margin: '110 0 0 0',
    fullReferralDescription: function(){
        var me = this;
        
        return function(value, metaData, record){
            var fullDesc = record.get('description');
            metaData.tdAttr = 'data-qtip="' + fullDesc + '"';
            var tpl = new Ext.Template('<div class="wrappable-cell">{NAME}</div>');
            
            return tpl.apply({
                NAME: record.get('description')
            });
           
        }
    },
    
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
            cls: 'challengesgrid',
			rowLines: true,
			enableDragDrop: true,
            viewConfig: {
				itemId: 'gridView',
                markDirty: false,
                plugins: {
                    ptype: 'gridviewdragdrop',
	                  dropGroup: 'gridtogrid',
	                  dragGroup: 'gridtogrid',
			          enableDrop: true,
			          enableDrag: true
                }
            },
            columns: [{
                header: 'Task',
                flex: 1,
                dataIndex: 'description',
                rowEditable: false,
                renderer: me.fullReferralDescription()
            }, {
                header: 'Link',
                flex: .45,
                dataIndex: 'link',
                rowEditable: true,
                field: {
                    xtype: 'textfield',
                    fieldStyle: "margin-bottom:12px;"
                }
            }, {
                xtype: 'datecolumn',
                header: 'Due Date',
                dataIndex: 'dueDate',
                width: 85,
                name: 'dueDate',
                itemId: 'actionPlanDueDate',
                renderer: function(value, metaData, record, rowIndex, colIndex, store, view){
                	var dt = record.get('dueDate');
					
					if (Ext.isObject(dt) && dt.apStr) {
						return dt.apStr;
					} else {
						if (Ext.isDate(dt)) {
							return Ext.Date.format(dt,'m/d/y');
						}
						else {
							return dt;
						}
					}
                },
                
                editor: {
                    xtype: 'datefield',
                    allowBlank: false,
                    altFormats: 'm/d/Y|m-d-Y',
                    showToday: false,
                    listeners: {
                        render: function(field){
                            Ext.create('Ext.tip.ToolTip', {
                                target: field.getEl(),
                                html: 'Use this to set the target completion date in the institution\'s time zone.'
                            });
                        }
                    }
                }
            
            }, {
                header: 'Confidentiality',
                dataIndex: 'confidentialityLevel',
                itemId: 'confidentialityLevel',
                name: 'confidentialityLevelId',
                renderer: me.columnRendererUtils.renderConfidentialityLevel,
                required: true,
                flex: .30,
                field: {
                    xtype: 'combo',
                    store: me.confidentialityLevelsAllUnpagedStore,
                    displayField: 'name',
                    valueField: 'id',
                    forceSelection: true,
                    typeAhead: true,
                    queryMode: 'local',
                    allowBlank: false,
                    emptyText: 'Select One'
                
                }
            
			
            
            
            }],
            
            dockedItems: [{
                xtype: 'toolbar',
                dock: 'top',
                items: [{
                    xtype: 'label',
                    html: '<div>For Tasks Added to the Action Plan, Add the Due Date and Confidentiality Level.</div><div>Custom Tasks can be Added to the List Below</div>'
                }]
            }, {
                xtype: 'tbspacer',
                height: 10
            }, {
                dock: 'top',
                xtype: 'toolbar',
                items: [{
                    text: 'Add Custom',
                    xtype: 'button',
                    itemId: 'addCustomButton'
                }, {
                    text: 'Remove',
                    xtype: 'button',
                    itemId: 'removeTaskButton'
                }, {
                    text: 'Remove All',
                    xtype: 'button',
                    itemId: 'removeAllTaskButton'
                }]
            }, {
                xtype: 'tbspacer',
                height: 5
            }]
        });
        
        return me.callParent(arguments);
    }
});
