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
Ext.define('Ssp.view.tools.actionplan.CustomActionPlan', {
    extend: 'Ext.window.Window',
    alias: 'widget.customactionplan',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.CustomActionPlanViewController',
    inject: {
        columnRendererUtils: 'columnRendererUtils',
        appEventsController: 'appEventsController',
        confidentialityLevelsStore: 'confidentialityLevelsAllUnpagedStore',
        textStore: 'sspTextStore'
    },
    height: 375,
    width: 600,
    itemId: 'customAPWindow',
    resizable: true,
    initComponent: function(){
        var me = this;
        
        Ext.applyIf(me, {
            items: [{
                xtype: 'form',
                height: '100%',
                width: '100%',
                layout: {
                    align: 'stretch',
                    type: 'vbox'
                },
                bodyPadding: 10,
                title: me.textStore.getValueByCode('ssp.label.action-plan.custom-action-plan.title', 'Custom Task'),
                dockedItems: [{
                    xtype: 'toolbar',
                    dock: 'top',
                    items: [{
                        xtype: 'button',
                        text: me.textStore.getValueByCode('ssp.label.save-button', 'Save'),
                        itemId: 'addCustomActionPlanButton'
                    }, {
                        xtype: 'button',
                        text: me.textStore.getValueByCode('ssp.label.cancel-button', 'Cancel'),
                        listeners: {
                            click: function(){
                                me = this;
                                me.close();
                            },
                            scope: me
                        }
                    }]
                }],
                items: [{
                    xtype: 'textfield',
                    width: '99%',
                    fieldLabel: me.textStore.getValueByCode('ssp.label.action-plan.custom-action-plan.name', 'Name'),
                    name: 'name',
					maxLength: 100,
					allowBlank: false
                }, {
                    xtype: 'textareafield',
                    width: '95%',
                    fieldLabel: me.textStore.getValueByCode('ssp.label.action-plan.custom-action-plan.description', 'Description'),
                    maxLength: 2000,
                    allowBlank: false,
                    name: 'description'
                }, {
                    xtype: 'textfield',
                    fieldLabel: me.textStore.getValueByCode('ssp.label.action-plan.custom-action-plan.link', 'Link (No HTML)'),
                    inputAttrTpl: " data-qtip='" + me.textStore.getValueByCode('ssp.tooltip.action-plan.custom-action-plan.link', 'Example:&nbsp;https://www.sample.com  <br /> No HTML markup e.g. &quot;&lt; a href=...&gt;&quot;') + "'",
                    name: 'link',
                    maxLength: 256,
                    allowBlank: true,
                    width: '95%'
                }, {
                    xtype: 'fieldcontainer',
                    fieldLabel: '',
                    width: '50%',
                    layout: {
                        type: 'hbox'
                    },
                    items: [{
                        width: '100%',
                        xtype: 'datefield',
                        fieldLabel: me.textStore.getValueByCode('ssp.label.action-plan.custom-action-plan.due-date', 'Target Date'),
                        altFormats: me.textStore.getValueByCode('ssp.alt-formats.action-plan.custom-action-plan.due-date', 'm/d/Y|m-d-Y'),
                        ltFormats: me.textStore.getValueByCode('ssp.lt-formats.action-plan.custom-action-plan.due-date', 'm/d/Y|m-d-Y'),
    					format: me.textStore.getValueByCode('ssp.format.action-plan.custom-action-plan.due-date', 'm/d/Y'),
                        showToday: false,
    					minValue: Ext.Date.format(new Date(), me.textStore.getValueByCode('ssp.format.action-plan.custom-action-plan.due-date', 'm/d/Y')),
    					minText: me.textStore.getValueByCode('ssp.min-text.action-plan.custom-action-plan.due-date', 'Cannot have a due date before today!'),
                        name: 'dueDate',
                        itemId: 'actionPlanDueDate',
                        allowBlank: false,
                        showToday: false, // else 'today' would be browser-local 'today'
                        listeners: {
                            render: function(field){
                                Ext.create('Ext.tip.ToolTip', {
                                    target: field.getEl(),
                                    html: me.textStore.getValueByCode('ssp.tooltip.action-plan.custom-action-plan.due-date', 'Use this to set the target completion date in the institution\'s time zone.')
                                });
                            }
                        }
                    }]
                }, {
                    xtype: 'combobox',
                    itemId: 'confidentialityLevel',
                    name: 'confidentialityLevelId',
                    fieldLabel: me.textStore.getValueByCode('ssp.label.action-plan.custom-action-plan.confidentiality-level', 'Confidentiality Level'),
                    emptyText: me.textStore.getValueByCode('ssp.empty-text.action-plan.custom-action-plan.confidentiality-level', 'Select One'),
                    store: me.confidentialityLevelsStore,
                    valueField: 'id',
                    displayField: 'name',
                    typeAhead: true,
                    queryMode: 'local',
                    allowBlank: false,
                    forceSelection: true,
                    width: '70%'
                
                }]
            }]
        });
        
        me.callParent(arguments);
    }
    
});
