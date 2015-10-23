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
Ext.define('Ssp.view.CustomizableExportView', {
    extend: 'Ext.window.Window',
    alias: 'widget.customizableexportview',
    mixins: ['Deft.mixin.Injectable','Deft.mixin.Controllable'],
    controller: 'Ssp.controller.CustomizableExportController',
    inject: {
    	textStore: 'sspTextStore'
    },
    config: {
        bulkCriteria: null
    },
    height: 300,
    width: 700,
    resizable: true,
    initComponent: function() {
        var me = this;
            Ext.apply(me, {
            layout: {
                align: 'stretch',
                type: 'vbox'
            },
            title: this.textStore.getValueByCode('ssp.label.custom-export.title','Custom Export to CSV'),
            defaults: {
                border: 'false',
                fieldLabel: ''
            },
            bodyStyle: 'background:none',
            bodyPadding: 8,
            items: [{
                xtype: 'label',
                text: this.textStore.getValueByCode('ssp.label.custom-export.select-items-below',
                            'Select items below to be included in the export file along with the mandatory student name/id fields and hit Export when done.')
            }, {
                xtype: 'checkboxgroup',    //TODO refactor possibly dynamic push checkboxes and labels
				columns: 3,
                allowBlank: false,
                itemId: 'exportColumnOptions',
                name: 'exportColumnOptions',
                defaults: {
                    labelWidth: 180,
                    margin: '10 5 10 5'
                },
                items: [{
                    xtype: 'checkbox',   //TODO Slave these to existing blurbs or create new?
                    name: 'address',
                    itemId: 'address',
                    boxLabel: this.textStore.getValueByCode('ssp.label.custom-export.address','Address')
                }, {
                    xtype: 'checkbox',
                    itemId: 'alternate',
                    name: 'alternateContact',
                    boxLabel: this.textStore.getValueByCode('ssp.label.custom-export.alt-contact-information','Alternate Contact Information')
                }, {
                    xtype: 'checkbox',
                    name: 'sspStatus',
                    itemId: 'sspstatus',
                    boxLabel: this.textStore.getValueByCode('ssp.label.custom-export.ssp-status','SSP Status')
                }, {
                    xtype: 'checkbox',
                    itemId: 'demographics',
                    name: 'demographics',
                    hidden: true,
                    boxLabel: this.textStore.getValueByCode('ssp.label.custom-export.demographics','Demographics')
                }, {
                    xtype: 'checkbox',
                    itemId: 'degree',
                    name: 'academicProgram',
                    boxLabel: this.textStore.getValueByCode('ssp.label.custom-export.academic-program','Academic Program')
                }, {
                    xtype: 'checkbox',
                    itemId: 'gpa',
                    name: 'academicGpa',
                    boxLabel: this.textStore.getValueByCode('ssp.label.custom-export.academic-gpa','Academic GPA')
                }, {
                    xtype: 'checkbox',
                    name: 'academicStanding',
                    itemId: 'academicStanding',
                    boxLabel: this.textStore.getValueByCode('ssp.label.custom-export.academic-standing','Academic Standing')
                }, {
                    xtype: 'checkbox',
                    itemId: 'department',
                    name: 'department',
                    boxLabel: this.textStore.getValueByCode('ssp.label.custom-export.department','Department')
                }, {
                    xtype: 'checkbox',
                    itemId: 'serviceReasons',
                    name: 'serviceReasons',
                    boxLabel: this.textStore.getValueByCode('ssp.label.custom-export.service-reasons','Service Reasons')
                }, {
                    xtype: 'checkbox',
                    itemId: 'referralSources',
                    name: 'referralSources',
                    boxLabel: this.textStore.getValueByCode('ssp.label.custom-export.referral-sources','Referral Sources')
                }, {
                    xtype: 'checkbox',
                    itemId: 'specialServiceGroups',
                    name: 'specialServiceGroups',
                    boxLabel: this.textStore.getValueByCode('ssp.label.custom-export.special-service-groups','Special Service Groups')
                }, {
                    xtype: 'checkbox',
                    name: 'sapStatus',
                    itemId: 'sapStatus',
                    boxLabel: this.textStore.getValueByCode('ssp.label.custom-export.sap-status','SAP Status')
                }, {
                    xtype: 'checkbox',
                    name: 'startTermYear',
                    itemId: 'startTermYear',
                    boxLabel: this.textStore.getValueByCode('ssp.label.custom-export.start-term-year','Start Term/Year')
                }, {
                    xtype: 'checkbox',
                    itemId: 'faCompletionRate',
                    name: 'faCompletionRate',
                    boxLabel: this.textStore.getValueByCode('ssp.label.custom-export.fa-completion-rate','FA Completion Rate'),
                    hidden: true
                }, {
                    xtype: 'checkbox',
                    itemId: 'mapInfo',
                    name: 'mapInfo',
                    boxLabel: this.textStore.getValueByCode('ssp.label.custom-export.map-information','MAP Information')
                }, {
                     xtype: 'checkbox',
                     name: 'extra',
                     itemId: 'extra',
                     boxLabel: this.textStore.getValueByCode('ssp.label.custom-export.miscellaneous','Misc. Student Info from Search')
                }]
            }, {
                xtype: 'label',
                text: this.textStore.getValueByCode('ssp.label.custom-export.please-include-only',
                            'Please include only those fields which are necessary as the report will run slower with more students and/or options selected.')
            }],
            dockedItems: [{
                xtype: 'toolbar',
                dock: 'top',
                items: [{
                    xtype: 'button',
                    itemId: 'exportButton',
                    text: this.textStore.getValueByCode('ssp.label.export-button','Export')

                }, '-', {
                    xtype: 'button',
                    itemId: 'cancelButton',
                    text: this.textStore.getValueByCode('ssp.label.cancel-button','Cancel')
                }]

            }]
        });
        return me.callParent(arguments);
    }
});
