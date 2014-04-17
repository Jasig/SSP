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
Ext.define('Ssp.view.tools.profile.Person', {
    extend: 'Ext.form.FieldContainer',
    alias: 'widget.profileperson',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    //controller: 'Ssp.controller.tool.profile.ProfilePersonViewController',
    inject: {
        columnRendererUtils: 'columnRendererUtils',
        sspConfig: 'sspConfig',
        textStore:'sspTextStore'
    },
    width: '100%',
    height: '100%',
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            fieldLabel: '',
            layout: 'hbox',
            margin: '0 0 0 0',
			height: '150',
            defaultType: 'displayfield',
            fieldDefaults: {
                msgTarget: 'side'
            },
            
            items: [{
                xtype: 'image',
                fieldLabel: '',
                src: Ssp.util.Constants.DEFAULT_NO_STUDENT_PHOTO_URL,
                itemId: 'studentPhoto',
                width:150
            }, {
                xtype: 'fieldset',
                border: 0,
                padding: '0 0 0 5',
                title: '',
                defaultType: 'displayfield',
                defaults: {
                    anchor: '100%'
                },
                //flex: .40,
                items: [{
                    fieldLabel: 'Name',
                    name: 'studentName',
                    itemId: 'studentName'
                
                }, {
                    fieldLabel: 'ID',
                    itemId: 'studentId',
                    name: 'schoolId'
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.dob'),
                    name: 'birthDate',
                    itemId: 'birthDate'
                }, {
                    fieldLabel: me.textStore.getValueByCode('ssp.label.home-phone'),
                    name: 'homePhone',
                    labelWidth: 38
                }, {
                    name: 'primaryEmailAddressLabel',
					itemId: 'primaryEmailAddressLabel'
                }, {
                    fieldLabel: '',
                    hideLabel: true,
                    name: 'primaryEmailAddressField',
					itemId: 'primaryEmailAddressField'
                }, {
                    fieldLabel: 'Student Type',
                    name: 'studentType',
                    itemId: 'studentType'
                }, {
                    fieldLabel: 'SSP Status',
                    name: 'programStatus',
                    itemId: 'programStatus'
                },
				{
					fieldLabel: 'Status Reason',
                    name: 'programStatusReason',
                    itemId: 'programStatusReason',
					hidden: true
					
					
				}]
            
            }]
        
        });
        
        return me.callParent(arguments);
    }
    
});
