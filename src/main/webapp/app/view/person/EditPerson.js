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
Ext.define('Ssp.view.person.EditPerson', {
    extend: 'Ext.form.Panel',
    alias: 'widget.editperson',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.EditPersonViewController',
    inject: {
        textStore: 'sspTextStore',
        sspConfig: 'sspConfig'
    },
	padding: '0 0 0 0',
	margin: '0 0 0 0',
	width: '100%',
	layout: {
		//align:'stretch',
		type:'vbox'
	},
    initComponent: function(){
        var me = this;
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
					layout: {
						type: 'hbox'
					},
					items: [{
						xtype: 'textfield',
						name: 'schoolId',
						fieldLabel: '<span class="syncedField">(sync\'d)</span>  ' + me.sspConfig.get('studentIdAlias'),
						minLength: 7,
						maxLength: 7,
						itemId: 'studentId',
						allowBlank: false,
						width: 250,
						labelAlign: 'top',
						padding: '0 5 0 0'
					},
					{
						xtype: 'button',
						tooltip: 'Load record from external system (Possible loss of local changes if record is found)',
						text: 'Find',
						itemId: 'retrieveFromExternalButton',
						margins: '22 0 0 10',
						width: 50
					}]
				}, {
                    fieldLabel: '<span class="syncedField">(sync\'d)</span>  ' + 'Username',
                    name: 'username',
                    minLength: 4,
                    maxLength: 25,
                    itemId: 'username',
                    allowBlank: false,
                    width: 250
                }, {
                    fieldLabel: '<span class="syncedField">(sync\'d)</span>  ' + me.textStore.getValueByCode('ssp.label.first-name'),
                    name: 'firstName',
                    itemId: 'firstName',
                    id: 'editPersonFirstName',
                    maxLength: 50,
                    allowBlank: false,
                    width: 250
                }, {
                    fieldLabel: '<span class="syncedField">(sync\'d)</span>  ' + me.textStore.getValueByCode('ssp.label.middle-name'),
                    name: 'middleName',
                    itemId: 'middleName',
                    id: 'editPersonMiddleName',
                    maxLength: 50,
                    allowBlank: true,
                    width: 250
                }, {
                    fieldLabel: '<span class="syncedField">(sync\'d)</span>  ' + me.textStore.getValueByCode('ssp.label.last-name'),
                    name: 'lastName',
                    itemId: 'lastName',
                    id: 'editPersonLastName',
                    maxLength: 50,
                    allowBlank: false,
                    width: 250
                }, {
                    fieldLabel: '<span class="syncedField">(sync\'d)</span>  ' + me.textStore.getValueByCode('ssp.label.home-phone'),
                    name: 'homePhone',
                    emptyText: 'xxx-xxx-xxxx',
                    maxLength: 25,
                    allowBlank: true,
                    itemId: 'homePhone',
                    width: 250
                }, {
                    fieldLabel: '<span class="syncedField">(sync\'d)</span>  ' + me.textStore.getValueByCode('ssp.label.work-phone'),
                    name: 'workPhone',
                    emptyText: 'xxx-xxx-xxxx',
                    maxLength: 25,
                    allowBlank: true,
                    itemId: 'workPhone',
                    width: 250
                }, {
                    fieldLabel: '<span class="syncedField">(sync\'d)</span>  ' + me.textStore.getValueByCode('ssp.label.school-email'),
                    name: 'primaryEmailAddress',
                    vtype: 'email',
                    maxLength: 100,
                    allowBlank: true,
                    itemId: 'primaryEmailAddress',
                    width: 250
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.alternate-email'),
                    name: 'secondaryEmailAddress',
                    vtype: 'email',
                    maxLength: 100,
                    allowBlank: true,
                    itemId: 'secondaryEmailAddress',
                    width: 250
                }]
            }]
        });
        
        return this.callParent(arguments);
    }
});
