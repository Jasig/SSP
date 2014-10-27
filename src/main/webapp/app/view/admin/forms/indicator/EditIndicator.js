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
Ext.define('Ssp.view.admin.forms.indicator.EditIndicator', {
    extend: 'Ext.form.Panel',
    alias: 'widget.editindicator',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.indicator.EditIndicatorViewController',
    title: 'Status Indicator Definition',
    inject: {
         authenticatedPerson: 'authenticatedPerson'
    },
    autoScroll: true,
    collapsible: true,
    scroll: 'vertical',
    layout: 'anchor',
    bodyPadding: 10,
    
    initComponent: function(){
        var me = this;
        
        var types = Ext.create('Ext.data.Store', {
            fields: ['type'],
            data: [{
                'type': 'SCALE'
            }, {
                'type': 'STRING'
            }]
        });
        
        var glyphs = Ext.create('Ext.data.Store', {
            fields: ['value'],
            data: [{
                'value': 'DEFAULT'
            }, {
                'value': 'HIGH'
            }, {
                'value': 'MEDIUM'
            }, {
                'value': 'LOW'
            }]
        });
        
        Ext.applyIf(me, {
            items: [{
                xtype: 'fieldset',
                anchor: '100%',
                layout: 'anchor',
                items: [{
                    xtype: 'container',
                    flex: 1,
                    layout: 'hbox',
                    items: [{
                        xtype: 'container',
                        layout: 'anchor',
                        padding: '10 10 10 0',
                        defaults: {
                            anchor: '90%',
                            labelWidth: 110
                        },
                        defaultType: 'textfield',
                        flex: 1,
                        items: [{
                            xtype: 'displayfield',
                            fieldLabel: 'Indicator Group',
                            name: 'indicatorGroup',
                            itemId: 'indicatorGroup'
                        }, {
                            fieldLabel: 'Indicator Name',
                            name: 'name',
                            itemId: 'indicatorName',
							allowBlank: false
                        }, {
                            fieldLabel: 'Indicator Code',
                            name: 'code',
                            itemId: 'indicatorCode',
							allowBlank: false
                        }, {
							xtype: 'numberfield',
                            fieldLabel: 'Sort Order',
							name: 'sortOrder',
							itemId: 'sortOrder',
                            minValue: 0,
                            step: 10,
							anchor: '40%',
							maxWidth: 160
						}]
                    }, {
                        xtype: 'container',
                        layout: 'anchor',
                        padding: '10 0 10 10',
                        defaults: {
                            anchor: '90%'
                        },
                        defaultType: 'textfield',
                        flex: 1,
                        items: [{
                            fieldLabel: 'Model Code',
                            name: 'modelCode',
                            itemId: 'modelCode',
							allowBlank: false
                        }, {
                            fieldLabel: 'Model Name',
                            name: 'modelName',
                            itemId: 'modelName',
							allowBlank: false
                        }, {
                            xtype: 'combobox',
                            fieldLabel: 'Evaluation Type',
                            name: 'evaluationType',
                            itemId: 'evaluationType',
                            store: types,
                            queryMode: 'local',
                            displayField: 'type',
                            valueField: 'type',
                            editable: false,
                            forceSelection: true,
                            anchor: '40%',
							minWidth: 170,
                            value: 'Scale'
                        }]
                    }]
                }, {
                    xtype: 'textareafield',
                    fieldLabel: 'Indicator Description',
                    anchor: '100%',
                    name: 'description',
                    itemId: 'indicatorDescription',
                    labelAlign: 'top'
                }, {
                    xtype: 'textareafield',
                    fieldLabel: 'Indicator Instructions',
                    anchor: '100%',
                    name: 'instruction',
                    itemId: 'indicatorInstruction',
                    labelAlign: 'top'
                }]
            }, {
                xtype: 'fieldset',
                anchor: '100%',
                layout: 'anchor',
                padding: '10 10 10 10',
                items: [{
                    xtype: 'container',
                    itemId: 'evaluationTypeCard',
                    layout: 'card',
                    anchor: '100%',
                    items: [{
                        xtype: 'fieldcontainer',
                        fieldLabel: 'Scale Evaluation',
                        labelWidth: 110,
                        items: [{
                            xtype: 'panel',
                            width: 280,
                            layout: 'column',
                            items: [{
                                title: 'Evaluation',
                                columnWidth: 0.5,
                                layout: 'vbox',
                                defaults: {
                                    xtype: 'label',
                                    height: 32,
                                    padding: '4 0 1 10'
                                },
                                items: [{
                                    text: 'High'
                                }, {
                                    text: 'Medium'
                                }, {
                                    text: 'Low'
                                }]
                            }, {
                                title: 'From',
                                columnWidth: 0.25,
                                layout: 'vbox',
                                defaults: {
                                    xtype: 'numberfield',
                                    padding: '2 4 3 4',
                                    maxValue: 100,
                                    minValue: 0,
                                    step: 0.01,
                                    width: '100%'
                                },
                                items: [{
                                    name: 'scaleEvaluationHighFrom',
                                    itemId: 'scaleEvaluationHighFrom',
                                    value: 8.0
                                }, {
                                    name: 'scaleEvaluationMediumFrom',
                                    itemId: 'scaleEvaluationMediumFrom',
                                    value: 4.0
                                }, {
                                    name: 'scaleEvaluationLowFrom',
                                    itemId: 'scaleEvaluationLowFrom',
                                    value: 0
                                }]
                            }, {
                                title: 'To',
                                columnWidth: 0.25,
                                layout: 'vbox',
                                defaults: {
                                    xtype: 'numberfield',
                                    padding: '2 4 3 4',
                                    maxValue: 100,
                                    minValue: 0,
                                    width: '100%',
                                    value: 0.01
                                },
                                items: [{
                                    name: 'scaleEvaluationHighTo',
                                    itemId: 'scaleEvaluationHighTo',
                                    value: 10.0
                                }, {
                                    name: 'scaleEvaluationMediumTo',
                                    itemId: 'scaleEvaluationMediumTo',
                                    value: 7.9
                                }, {
                                    name: 'scaleEvaluationLowTo',
                                    itemId: 'scaleEvaluationLowTo',
                                    value: 3.9
                                }]
                            }]
                        }]
                    }, {
                        xtype: 'fieldcontainer',
                        fieldLabel: 'String Evaluation',
                        labelWidth: 110,
                        items: [{
                            xtype: 'panel',
                            width: 480,
                            layout: 'column',
                            items: [{
                                title: 'Evaluation',
                                columnWidth: 0.25,
                                layout: 'vbox',
                                defaults: {
                                    xtype: 'label',
                                    height: 32,
                                    padding: '4 0 1 10'
                                },
                                items: [{
                                    text: 'High'
                                }, {
                                    text: 'Medium'
                                }, {
                                    text: 'Low'
                                }]
                            }, {
                                title: 'Text String',
                                columnWidth: 0.75,
                                layout: 'vbox',
                                defaults: {
                                    xtype: 'textfield',
                                    padding: '4 4 1 4',
                                    width: '100%'
                                },
                                items: [{
                                    name: 'stringEvaluationHigh',
                                    itemId: 'stringEvaluationHigh',
                                    value: 'High'
                                }, {
                                    name: 'stringEvaluationMedium',
                                    itemId: 'stringEvaluationMedium',
                                    value: 'Medium'
                                }, {
                                    name: 'stringEvaluationLow',
                                    itemId: 'stringEvaluationLow',
                                    value: 'Low'
                                }]
                            }]
                        }]
                    }]
                }, {
                    xtype: 'label',
                    labelWidth: 110,
                    text: 'Default Handling'
                }, {
                    xtype: 'combobox',
                    fieldLabel: 'No Data Exists',
                    labelWidth: 110,
                    labelAlign: 'right',
                    name: 'noDataExistsEvaluation',
                    itemId: 'noDataExistsEvaluation',
                    store: glyphs,
                    queryMode: 'local',
                    displayField: 'value',
                    valueField: 'value',
                    editable: false,
                    forceSelection: true,
                    width: 240
                }, {
                    xtype: 'combobox',
                    fieldLabel: 'No Data Match',
                    labelWidth: 110,
                    labelAlign: 'right',
                    name: 'noDataMatchEvaluation',
                    itemId: 'noDataMatchEvaluation',
                    store: glyphs,
                    queryMode: 'local',
                    displayField: 'value',
                    valueField: 'value',
                    editable: false,
                    forceSelection: true,
                    width: 240
                }]
            }],
            dockedItems: [{
                xtype: 'toolbar',
                items: [{
                    text: 'Save',
                    xtype: 'button',
                    action: 'save',
                    itemId: 'saveButton',
                    formBind: true
                }, '-', {
                    text: 'Cancel',
                    xtype: 'button',
                    action: 'cancel',
                    itemId: 'cancelButton'
                }]
            }]
        });
        
        return this.callParent(arguments);
    }
});
