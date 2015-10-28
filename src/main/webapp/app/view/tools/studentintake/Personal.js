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
Ext.define('Ssp.view.tools.studentintake.Personal', {
    extend: 'Ext.form.Panel',
    alias: 'widget.studentintakepersonal',
    id: 'StudentIntakePersonal',
    mixins: ['Deft.mixin.Injectable',
        'Deft.mixin.Controllable'
    ],
    controller: 'Ssp.controller.tool.studentintake.PersonalViewController',
    inject: {
        columnRendererUtils: 'columnRendererUtils',
        statesStore: 'statesStore',
        textStore: 'sspTextStore',
        configStore: 'configStore'
    },
    width: '100%',
    height: '100%',
    minHeight: 1000,
    minWidth: 600,
    style: 'padding: 0px 5px 5px 10px',
    initComponent: function() {
        var me = this;
        var defaultSyncdLabel = '<span class="syncedField">(sync\'d)</span>  ';
        Ext.apply(me, {
            autoScroll: true,
            border: 0,
            bodyPadding: 5,
            layout: 'anchor',
            defaults: {
                anchor: '100%'
            },
            fieldDefaults: {
                msgTarget: 'side',
                labelAlign: 'right',
                labelWidth: 200
            },
            items: [{
                xtype: 'fieldset',
                border: 0,
                title: '',
                defaultType: 'textfield',
                defaults: {
                    anchor: '95%'
                },
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: me.textStore.getValueByCode('ssp.label.student-intake.cda-acceptance-date','CDA Acceptance Date'),
                    name: 'formattedCDACompleteDate',
                    itemId: 'formattedCDACompleteDate',
                    renderer: Ext.util.Format.dateRenderer('m/d/Y'),
                    listeners: {
                        render: function(field) {
                            Ext.create('Ext.tip.ToolTip', {
                                target: field.getEl(),
                                html: me.textStore.getValueByCode('ssp.tooltip.student-intake.cda-acceptance-date','This is the date on which the student accepted the Confidentiality Disclosure Agreement form.')
                            });
                        }
                    }
                }, {
                    xtype: 'displayfield',
                    fieldLabel: me.textStore.getValueByCode('ssp.label.student-intake.intake-acceptance-date','Intake Completion Date'),
                    name: 'formattedStudentIntakeCompleteDate',
                    renderer: Ext.util.Format.dateRenderer('m/d/Y'),
                    listeners: {
                        render: function(field) {
                            Ext.create('Ext.tip.ToolTip', {
                                target: field.getEl(),
                                html: me.textStore.getValueByCode('ssp.tooltip.student-intake.intake-acceptance-date','This is the date on which intake data for this student was most recently received. It is shown in institution-local time. E.g. for a May 9, 11pm submission on the US west coast to an east coast school, this would display the "next" day, i.e. May 10.')
                            });
                        }
                    }
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.syncd',defaultSyncdLabel) + me.textStore.getValueByCode('ssp.label.first-name', 'First Name'),
                    name: 'firstName',
                    itemId: 'firstName',
                    maxLength: 50,
                    allowBlank: false
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.syncd',defaultSyncdLabel) + me.textStore.getValueByCode('ssp.label.middle-name', 'Middle Name'),
                    name: 'middleName',
                    itemId: 'middleName',
                    maxLength: 50,
                    allowBlank: true
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.syncd',defaultSyncdLabel) + me.textStore.getValueByCode('ssp.label.last-name', 'Last Name'),
                    name: 'lastName',
                    itemId: 'lastName',
                    maxLength: 50,
                    allowBlank: false
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.syncd',defaultSyncdLabel) + me.textStore.getValueByCode('ssp.label.student-id', 'Student Id'),
                    name: 'schoolId',
                    minLength: 0,
                    maxLength: 7,
                    itemId: 'studentId',
                    allowBlank: false
                }, {
                    xtype: 'datefield',
                    fieldLabel: me.textStore.getValueByCode('ssp.label.syncd',defaultSyncdLabel) + me.textStore.getValueByCode('ssp.label.dob', 'DOB'),
                    itemId: 'birthDate',
                    altFormats: 'm/d/Y|m-d-Y',
                    invalidText: me.textStore.getValueByCode('ssp.invalid-text.dob','{0} is not a valid date - it must be in the format: 06/02/2012 or 06-02-2012'),
                    name: 'birthDate',
                    allowBlank: false
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.syncd',defaultSyncdLabel) + me.textStore.getValueByCode('ssp.label.home-phone', 'Home Phone'),
                    name: 'homePhone',
                    emptyText: me.textStore.getValueByCode('ssp.empty-text.home-phone','xxx-xxx-xxxx'),
                    maxLength: 25,
                    allowBlank: true,
                    itemId: 'homePhone'
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.syncd',defaultSyncdLabel) + me.textStore.getValueByCode('ssp.label.work-phone', 'Work Phone'),
                    name: 'workPhone',
                    emptyText: me.textStore.getValueByCode('ssp.empty-text.work-phone','xxx-xxx-xxxx'),
                    maxLength: 25,
                    allowBlank: true,
                    itemId: 'workPhone'
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.syncd',defaultSyncdLabel) + me.textStore.getValueByCode('ssp.label.cell-phone', 'Cell Phone'),
                    name: 'cellPhone',
                    emptyText: me.textStore.getValueByCode('ssp.empty-text.cell-phone','xxx-xxx-xxxx'),
                    maxLength: 25,
                    allowBlank: true,
                    itemId: 'cellPhone'
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.syncd',defaultSyncdLabel) + me.textStore.getValueByCode('ssp.label.school-email', 'School Email'),
                    name: 'primaryEmailAddress',
                    vtype: 'email',
                    maxLength: 100,
                    allowBlank: true,
                    itemId: 'primaryEmailAddress'
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.alternate-email', 'Alternate Email'),
                    name: 'secondaryEmailAddress',
                    vtype: 'email',
                    maxLength: 100,
                    allowBlank: true,
                    itemId: 'secondaryEmailAddress'
                }, {
                    xtype: 'displayfield',
                    fieldLabel: me.textStore.getValueByCode('ssp.label.student-intake.current-address','CURRENT ADDRESS')
                }, {
                    xtype: 'displayfield',
                    fieldLabel: me.textStore.getValueByCode('ssp.label.syncd',defaultSyncdLabel) + me.textStore.getValueByCode('ssp.label.non-local', 'Non Local'),
                    name: 'nonLocalAddress',
                    renderer: me.columnRendererUtils.renderFriendlyBoolean
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.syncd',defaultSyncdLabel) + me.textStore.getValueByCode('ssp.label.address-1', 'Address Line 1'),
                    name: 'addressLine1',
                    maxLength: 50,
                    allowBlank: true,
                    itemId: 'addressLine1'
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.syncd',defaultSyncdLabel) + me.textStore.getValueByCode('ssp.label.address-2', 'Address Line 2'),
                    name: 'addressLine2',
                    maxLength: 50,
                    allowBlank: true,
                    itemId: 'addressLine2'
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.syncd',defaultSyncdLabel) + me.textStore.getValueByCode('ssp.label.city', 'City'),
                    name: 'city',
                    maxLength: 50,
                    allowBlank: true,
                    itemId: 'city'
                }, {
                    xtype: 'combobox',
                    name: 'state',
                    fieldLabel: me.textStore.getValueByCode('ssp.label.syncd',defaultSyncdLabel) + me.textStore.getValueByCode('ssp.label.state', 'State'),
                    emptyText: me.textStore.getValueByCode('ssp.empty-text.state','Select a State'),
                    store: me.statesStore,
                    valueField: 'code',
                    displayField: 'title',
                    mode: 'local',
                    typeAhead: true,
                    queryMode: 'local',
                    allowBlank: true,
                    forceSelection: true,
                    itemId: 'state',
                    listeners: {
                        'select': function() {
                            me.statesStore.clearFilter();
                        }
                    }
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.syncd',defaultSyncdLabel) + me.textStore.getValueByCode('ssp.label.zip', 'Zip Code'),
                    name: 'zipCode',
                    maxLength: 10,
                    allowBlank: true,
                    itemId: 'zipCode'
                }, {
                    xtype: 'displayfield',
                    fieldLabel: me.textStore.getValueByCode('ssp.label.student-intake.alternate-phone','ALTERNATE PHONE')
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.alternate-phone', 'Alternate Phone'),
                    name: 'alternatePhone',
                    emptyText: me.textStore.getValueByCode('ssp.empty-text.alternate-phone','xxx-xxx-xxxx'),
                    maxLength: 25,
                    allowBlank: true,
                    itemId: 'alternatePhone'
                }, {
                    xtype: 'displayfield',
                    fieldLabel: me.textStore.getValueByCode('ssp.label.student-intake.alternate-address','ALTERNATE ADDRESS')
                }, {
                    xtype: 'checkbox',
                    fieldLabel: me.textStore.getValueByCode('ssp.label.alt-in-use', 'In Use'),
                    name: 'alternateAddressInUse'
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.address-1', 'Address Line 1'),
                    name: 'alternateAddressLine1',
                    maxLength: 50,
                    allowBlank: true,
                    itemId: 'alternateAddressLine1'
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.address-2', 'Address Line 2'),
                    name: 'alternateAddressLine2',
                    maxLength: 50,
                    allowBlank: true,
                    itemId: 'alternateAddressLine2'
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.city', 'City'),
                    name: 'alternateAddressCity',
                    maxLength: 50,
                    allowBlank: true,
                    itemId: 'alternateAddressCity'
                }, {
                    xtype: 'combobox',
                    name: 'alternateAddressState',
                    fieldLabel: me.textStore.getValueByCode('ssp.label.state', 'State'),
                    emptyText: me.textStore.getValueByCode('ssp.empty-text.state','Select a State'),
                    store: me.statesStore,
                    valueField: 'code',
                    displayField: 'title',
                    mode: 'local',
                    typeAhead: true,
                    queryMode: 'local',
                    allowBlank: true,
                    forceSelection: true,
                    itemId: 'alternateAddressState',
                    listeners: {
                        'select': function() {
                            me.statesStore.clearFilter();
                        }
                    }
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.zip', 'Zip Code'),
                    name: 'alternateAddressZipCode',
                    maxLength: 10,
                    allowBlank: true,
                    itemId: 'alternateAddressZipCode'
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.country', 'Country'),
                    name: 'alternateAddressCountry',
                    allowBlank: true,
                    itemId: 'alternateAddressCountry',
                    maxLength: 50
                }]
            }]
        });

        return me.callParent(arguments);
    }
});