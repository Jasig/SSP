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
Ext.define('Ssp.view.person.EditPerson', {
    extend: 'Ext.form.Panel',
    alias: 'widget.editperson',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.EditPersonViewController',
    inject: {
        textStore: 'sspTextStore',
        configStore: 'configStore',
		appEventsController: 'appEventsController',
        campusesStore: 'campusesAllUnpagedStore'
    },
	padding: '0 0 0 10',
	margin: '0 0 0 0',
	width: '100%',
	layout: {
		//align:'stretch',
		type:'vbox'
	},
    initComponent: function(){
        var me = this;
        var defaultSyncdLabel = '<span class="syncedField">(sync\'d)</span>  ';
        Ext.apply(me, {
            border: 0,
			padding: 0,
            fieldDefaults: {
                msgTarget: 'side',
                labelAlign: 'top',
                labelWidth: 150
            },
            items: [{
                xtype: 'fieldset',
                border: 0,
                title: '',
                defaultType: 'textfield',
                padding: '0 0 0 0',
                items: [{
					xtype: 'fieldcontainer',
					border: 0,
					padding: '0 0 0 0',
					defaultType: 'textfield',
					layout: {
						type: 'hbox'
					},
					items: [{
						name: 'schoolId',
						fieldLabel: me.textStore.getValueByCode('ssp.label.syncd',defaultSyncdLabel) + me.textStore.getValueByCode('ssp.label.student-id', 'Student Id'),
						minLength: 7,
						maxLength: 7,
						itemId: 'studentId',
						allowBlank: false,
						width: 250,						
						labelAlign: 'top',
						padding: '0 5 0 0',
                        enableKeyEvents: true,
                        listeners: {
                            afterrender: function(field){
                                field.focus(false, 5);
								
                            },
                            specialkey: {
                                scope: me,
                                fn: function(field, el){
                                    if (el.getKey() == Ext.EventObject.ENTER) {
                                        me.appEventsController.getApplication().fireEvent("onRetrieveFromExternal");
                                    }
                                }
                            }
                        }
					},
					{
						xtype: 'button',
						tooltip: me.textStore.getValueByCode('ssp.tooltip.edit-student.find-button','Load record from external system (Possible loss of local changes if record is found)'),
						text: me.textStore.getValueByCode('ssp.label.find-button','Find'),
						itemId: 'retrieveFromExternalButton',
						margins: '22 0 0 10',
						width: 50
					}]
				}, {
                    fieldLabel:  me.textStore.getValueByCode('ssp.label.syncd',defaultSyncdLabel) + me.textStore.getValueByCode('ssp.label.username','Username'),
                    name: 'username',
                    minLength: 1,
                    maxLength: 50,
                    itemId: 'username',
                    allowBlank: false,
                    width: 250
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.syncd',defaultSyncdLabel) + me.textStore.getValueByCode('ssp.label.first-name','First Name'),
                    name: 'firstName',
                    itemId: 'firstName',
                    id: 'editPersonFirstName',
                    maxLength: 50,
                    allowBlank: false,
                    width: 250
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.syncd',defaultSyncdLabel) + me.textStore.getValueByCode('ssp.label.middle-name','Middle Name'),
                    name: 'middleName',
                    itemId: 'middleName',
                    id: 'editPersonMiddleName',
                    maxLength: 50,
                    allowBlank: true,
                    width: 250
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.syncd',defaultSyncdLabel) + me.textStore.getValueByCode('ssp.label.last-name','Last Name'),
                    name: 'lastName',
                    itemId: 'lastName',
                    id: 'editPersonLastName',
                    maxLength: 50,
                    allowBlank: false,
                    width: 250
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.syncd',defaultSyncdLabel) + me.textStore.getValueByCode('ssp.label.home-phone','Home Phone'),
                    name: 'homePhone',
                    emptyText: me.textStore.getValueByCode('ssp.empty-text.phone','xxx-xxx-xxxx'),
                    maxLength: 25,
                    allowBlank: true,
                    itemId: 'homePhone',
                    width: 250
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.syncd',defaultSyncdLabel) + me.textStore.getValueByCode('ssp.label.work-phone','Work Phone'),
                    name: 'workPhone',
                    emptyText: me.textStore.getValueByCode('ssp.empty-text.phone','xxx-xxx-xxxx'),
                    maxLength: 25,
                    allowBlank: true,
                    itemId: 'workPhone',
                    width: 250
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.syncd',defaultSyncdLabel) + me.textStore.getValueByCode('ssp.label.school-email','School Email'),
                    name: 'primaryEmailAddress',
                    vtype: 'email',
                    maxLength: 100,
                    allowBlank: true,
                    itemId: 'primaryEmailAddress',
                    width: 250
                }, {
                    xtype: 'combobox',
                    name: 'campusId',
                    itemId: 'campusCombo',
                    id: 'campusCombo',
                    fieldLabel: me.textStore.getValueByCode('ssp.label.syncd',defaultSyncdLabel) + me.textStore.getValueByCode('ssp.label.home-campus', 'Home Campus'),
                    emptyText: me.textStore.getValueByCode('ssp.empty-text.edit-student.home-campus','Select One'),
                    store: me.campusesStore,
                    valueField: 'id',
                    displayField: 'name',
                    mode: 'local',
                    typeAhead: false,
                    editable: false,
                    queryMode: 'local',
                    allowBlank: true,
					width: 250
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.alternate-email','Alternate Email'),
                    name: 'secondaryEmailAddress',
                    vtype: 'email',
                    maxLength: 100,
                    allowBlank: true,
                    itemId: 'secondaryEmailAddress',
                    width: 250
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.alternate-phone','Alternate Phone'),
                    name: 'alternatePhone',
                    emptyText: me.textStore.getValueByCode('ssp.empty-text.phone','xxx-xxx-xxxx'),
                    maxLength: 25,
                    allowBlank: true,
                    itemId: 'alternatePhone',
                    width: 250
                }]
            }]
        });
        
        return this.callParent(arguments);
    }
});
