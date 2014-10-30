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
Ext.define('Ssp.view.admin.forms.successindicator.EditSuccessIndicator', {
    extend: 'Ext.form.Panel',
    alias: 'widget.editindicator',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.successindicator.EditSuccessIndicatorViewController',
    title: 'Success Indicator Definition',
    inject: {
         authenticatedPerson: 'authenticatedPerson'
    },
    autoScroll: true,
    collapsible: true,
    scroll: 'vertical',
    layout: 'anchor',

    withOverrides: function(base, overrides) {
        Ext.apply(base, overrides);
        return base;
    },

    newScaleEvalInput: function(name, itemId) {
        return {
            xtype: 'numberfield',
            padding: '12 0 0 0',
            step: 0.01,
            width: 65,
            decimalPrecision: 2,
            name: name,
            itemId: itemId
        }
    },

    newStringEvalInput: function(name, itemId, overrides) {
        var me = this;
        var base = {
            xtype: 'textfield',
            padding: '12 0 0 0',
            name: name,
            itemId: itemId,
            maxLength: 1024
        };
        return overrides ? me.withOverrides(base, overrides) : base;
    },

    newEvalInputHeader: function(title, overrides) {
        var me = this;
        var base = {
            xtype: 'header',
            baseCls: 'x-panel-header', // Makes this look like a panel title cell
            width: 75, // Can't figure out how to get the table to just size itself to content,
                       // let alone grow/shrink with its container
            title: title,
            titleAlign: 'center'
        };
        return overrides ? me.withOverrides(base, overrides) : base;
    },
    
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
                            allowBlank: false,
                            minLength: 1,
                            maxLength: 80
                        }, {
                            fieldLabel: 'Indicator Code',
                            name: 'code',
                            itemId: 'indicatorCode',
                            allowBlank: false,
                            minLength: 1,
                            maxLength: 50
                        }, {
                            xtype: 'numberfield',
                            fieldLabel: 'Sort Order',
                            name: 'sortOrder',
                            itemId: 'sortOrder',
                            minValue: 0,
                            allowBlank: false,
                            step: 10,
                            maxWidth: 200
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
                            fieldLabel: 'Model Name',
                            name: 'modelName',
                            itemId: 'modelName',
                            allowBlank: false,
                            minLength: 1,
                            maxLength: 100
                        }, {
                            fieldLabel: 'Model Code',
                            name: 'modelCode',
                            itemId: 'modelCode',
                            allowBlank: false,
                            minLength: 1,
                            maxLength: 50
                        }, {
                            xtype: 'oscheckbox',
                            fieldLabel: 'Active',
                            checked: true,
                            name: 'objectStatus'
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
                    labelAlign: 'top',
                    maxLength: 1024
                }]
            }, {
                xtype: 'fieldset',
                anchor: '100%',
                layout: 'hbox',
                items: [{
                    xtype: 'container',
                    layout: 'anchor',
                    anchor: '100%',
                    items: [{
                        xtype: 'combobox',
                        fieldLabel: 'Rule Type',
                        name: 'evaluationType',
                        itemId: 'evaluationType',
                        store: types,
                        queryMode: 'local',
                        displayField: 'type',
                        valueField: 'type',
                        editable: false,
                        forceSelection: true,
                        allowBlank: false,
                        width: 200,
                        labelWidth: 75,
                        labelPad: 0
                    }, {
                        xtype: 'container',
                        itemId: 'evaluationTypeCard',
                        layout: 'card',
                        anchor: '100%',
                        items: [{
                            xtype: 'fieldcontainer',
                            fieldLabel: '',
                            labelWidth: 0,
                            labelPad: 0,
                            items: [{
                                xtype: 'container',
                                layout: {
                                    type: 'table',
                                    columns: 3
                                },
                                defaults: {
                                    xtype: 'label',
                                    width: 75, // Can't figure out how to get the table to just size itself to content,
                                                // let alone grow/shrink with its container
                                    tdAttrs: {
                                        align: 'center'
                                    }
                                },
                                items: [
                                    me.newEvalInputHeader('Evaluation'),
                                    me.newEvalInputHeader('From'),
                                    me.newEvalInputHeader('To'),
                                    { text: 'HIGH' },
                                    me.newScaleEvalInput('scaleEvaluationHighFrom', 'scaleEvaluationHighFrom'),
                                    me.newScaleEvalInput('scaleEvaluationHighTo', 'scaleEvaluationHighTo'),
                                    { text: 'MEDIUM' },
                                    me.newScaleEvalInput('scaleEvaluationMediumFrom', 'scaleEvaluationMediumFrom'),
                                    me.newScaleEvalInput('scaleEvaluationMediumTo', 'scaleEvaluationMediumTo'),
                                    { text: 'LOW' },
                                    me.newScaleEvalInput('scaleEvaluationLowFrom', 'scaleEvaluationLowFrom'),
                                    me.newScaleEvalInput('scaleEvaluationLowTo', 'scaleEvaluationLowTo')
                                ]
                            }]
                        }, {
                            xtype: 'fieldcontainer',
                            fieldLabel: '',
                            labelWidth: 0,
                            labelPad: 0,
                            items: [{
                                xtype: 'container',
                                layout: {
                                    type: 'table',
                                    columns: 2
                                },
                                defaults: {
                                    xtype: 'label',
                                    tdAttrs: {
                                        align: 'center'
                                    }
                                },
                                items: [
                                    me.newEvalInputHeader('Evaluation'),
                                    me.newEvalInputHeader('Text String', { width: 300 }),
                                    { text: 'HIGH' },
                                    me.newStringEvalInput('stringEvaluationHigh', 'stringEvaluationHigh', { width: 300 }),
                                    { text: 'MEDIUM' },
                                    me.newStringEvalInput('stringEvaluationMedium', 'stringEvaluationMedium', { width: 300 }),
                                    { text: 'LOW' },
                                    me.newStringEvalInput('stringEvaluationLow', 'stringEvaluationLow', { width: 300 })
                                ]
                            }]
                        }]
                    }]

                }, {
                    xtype: 'tbspacer',
                    width: 35
                }, {
                    xtype: 'container',
                    layout: 'anchor',
                    anchor: '100%',
                    items: [{
                        xtype: 'combobox',
                        fieldLabel: 'If Missing',
                        labelWidth: 100,
                        labelPad: 0,
                        name: 'noDataExistsEvaluation',
                        itemId: 'noDataExistsEvaluation',
                        store: glyphs,
                        queryMode: 'local',
                        displayField: 'value',
                        valueField: 'value',
                        editable: false,
                        forceSelection: true,
                        allowBlank: false,
                        width: 200
                    }, {
                        xtype: 'combobox',
                        fieldLabel: 'If Unexpected',
                        labelWidth: 100,
                        labelPad: 0,
                        name: 'noDataMatchesEvaluation',
                        itemId: 'noDataMatchesEvaluation',
                        store: glyphs,
                        queryMode: 'local',
                        displayField: 'value',
                        valueField: 'value',
                        editable: false,
                        forceSelection: true,
                        allowBlank: false,
                        width: 200
                    }]
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
