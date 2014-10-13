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
Ext.define('Ssp.view.EmailStudentForm', {
    extend: 'Ext.form.Panel',
    alias: 'widget.emailstudentform',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    inject: {
        confidentialityLevelsStore: 'confidentialityLevelsAllUnpagedStore',
        person: 'currentPerson',
        textStore: 'sspTextStore'
    },
    controller: 'Ssp.controller.EmailStudentViewController',
    config: {
        isBulk: false,
        bulkCriteria: null
    },
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            layout: {
                anchor: '100%'
            },
            bodyStyle: 'background:none',
            bodyPadding: 8,
            items: [{
                xtype: 'label',
				anchor: '100%',
                text: 'Fill-in the address(es), subject and message information below to send an email to the selected student. Optionally, a Journal Entry can be created to record the email content. Only the subject and message entered will be sent to the student. To email Action Plans and MAP plans, use those tools to email the content.'
            }, {
                xtype: 'tbspacer',
                height: 10
            }, {
                xtype: 'fieldcontainer',
                fieldLabel: '',
                height: 30,
				anchor: '100%',
                layout: {
                    type: 'hbox'
                },
                items: [{
                    xtype: 'checkbox',
                    name: 'createJournalEntry',
                    fieldLabel: '',
                    itemId: 'createJournalEntry',
                    labelSeparator: '',
                    hideLabel: true,
                    boxLabel: 'Record this Email as a Journal Entry',
                    fieldLabel: 'text'
                }, {
                    xtype: 'tbspacer',
                    width: 45
                }, {
                    xtype: 'combobox',
                    itemId: 'confidentialityLevel',
                    name: 'confidentialityLevelId',
                    fieldLabel: 'Confidentiality Level',
                    labelWidth: 125,
                    emptyText: 'Select One',
                    store: me.confidentialityLevelsStore,
                    valueField: 'id',
                    displayField: 'name',
                    mode: 'local',
                    typeAhead: true,
                    queryMode: 'local',
                    allowBlank: true,
					flex: 1,
                    forceSelection: true,
                    hidden: true,
                    disabled: true
                }]
            }, {
                xtype: 'fieldcontainer',
                fieldLabel: '',
				anchor: '100%',
                height: 30,
                layout: {
                    type: 'hbox'
                },
                hidden: !(me.getIsBulk()) && !me.person.get('primaryEmailAddress'),
                items: [{
                    xtype: 'checkbox',
                    name: 'sendToPrimaryEmail',
                    fieldLabel: '',
                    itemId: 'sendToPrimaryEmail',
                    labelSeparator: '',
                    hideLabel: true,
                    disabled: !(me.getIsBulk()) && !me.person.get('primaryEmailAddress'),
                    boxLabel: 'Send To ' + me.textStore.getValueByCode('ssp.label.school-email') + ' Address',
                    fieldLabel: 'text'
                }, {
                    xtype: 'tbspacer',
                    width: 45
                }, {
                    xtype: 'displayfield',
                    fieldLabel: '',
                    name: 'primaryEmail',
                    itemId: 'primaryEmail',
                    labelSeparator: '',
                    fieldStyle: 'color:blue',
                    hidden: me.getIsBulk() || !me.person.get('primaryEmailAddress')
                }]
            }, {
                xtype: 'fieldcontainer',
                fieldLabel: '',
                height: 30,
				anchor: '100%',
                layout: {
                    type: 'hbox'
                },
                hidden: !(me.getIsBulk()) && !me.person.get('secondaryEmailAddress'),
                items: [{
                    xtype: 'checkbox',
                    name: 'sendToSecondaryEmail',
                    fieldLabel: '',
                    itemId: 'sendToSecondaryEmail',
                    labelSeparator: '',
                    hideLabel: true,
                    disabled: !(me.getIsBulk()) && !me.person.get('secondaryEmailAddress'),
                    boxLabel: 'Send To ' + me.textStore.getValueByCode('ssp.label.alternate-email') + ' Address',
                    fieldLabel: 'text'
                }, {
                    xtype: 'tbspacer',
                    width: 45
                }, {
                    xtype: 'displayfield',
                    fieldLabel: '',
                    name: 'secondaryEmail',
                    itemId: 'secondaryEmail',
                    labelSeparator: '',
                    fieldStyle: 'color:blue',
                    hidden: me.getIsBulk() || !me.person.get('secondaryEmailAddress')
                }]
            }, {
                xtype: 'fieldcontainer',
                fieldLabel: '',
                height: 30,
				anchor: '100%',
                layout: {
                    type: 'hbox'
                },
                items: [{
                    xtype: 'displayfield',
                    value: 'CC This email to additional recipients (comma separated)',
                    fieldStyle: 'color:black'
                }, {
                    xtype: 'tbspacer',
                    width: 50
                }, {
                    xtype: 'textfield',
                    fieldLabel: '',
                    name: 'additionalEmail',
                    itemId: 'additionalEmail',
                    flex: 1,
                    labelSeparator: '',
                    maxLength: 400 // somewhat smaller than underlying db field b/c spaces
                                   // will be inserted before storage, plus secondaryEmailAddress is potentially
                                   // added to this list
                }]
            }, {
                xtype: 'textfield',
                fieldLabel: 'Subject',
                name: 'emailSubject',
                itemId: 'emailSubject',
                allowBlank: false,
                anchor: '100%',
                labelWidth: 60
            }, {
                xtype: 'ssphtmleditor',
                name: 'emailBody',
                allowBlank: false,
                fieldLabel: 'Body',
                itemId: 'emailBody',
                width: '100%',
                labelWidth: 60,
                height: 200
            }],
            dockedItems: [{
                xtype: 'toolbar',
                dock: 'top',
                items: [{
                    xtype: 'button',
                    itemId: 'saveButton',
                    text: 'Save'
                
                }, '-', {
                    xtype: 'button',
                    itemId: 'cancelButton',
                    text: 'Cancel'
                }]
            
            }]
        
        
        });
        
        return me.callParent(arguments);
    }
});
