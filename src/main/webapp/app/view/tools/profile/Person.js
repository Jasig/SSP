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
    extend: 'Ext.form.Panel',
    alias: 'widget.profileperson',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.profile.ProfilePersonViewController',
    inject: {
        columnRendererUtils: 'columnRendererUtils',
        sspConfig: 'sspConfig'
    },
    width: '100%',
    height: '100%',
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            border: 0,
            bodyPadding: 0,
            layout: 'anchor',
            defaults: {
                anchor: '100%'
            },
            items: [{
                xtype: 'fieldcontainer',
                fieldLabel: '',
                layout: 'hbox',
                margin: '5 0 0 0',
                defaultType: 'displayfield',
                fieldDefaults: {
                    msgTarget: 'side'
                    //labelAlign: 'right',
                    //labelWidth: 80
                },
                items: [{
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    defaults: {
                        anchor: '100%'
                    },
                    flex: .40,
                    items: [{
                        fieldLabel: '',
                        name: 'name',
                        itemId: 'studentName'
                    }, {
                        fieldLabel: 'ID',
                        itemId: 'studentId',
                        name: 'schoolId',
                        labelWidth: 70
                    }, {
                        fieldLabel: 'DOB',
                        name: 'birthDate',
                        itemId: 'birthDate',
                        labelWidth: 30
                    }, {
                        fieldLabel: 'Phone',
                        name: 'homePhone',
                        labelWidth: 40
                    }, {
                        fieldLabel: '',
                        name: 'primaryEmailAddress'
                    }, {
                        fieldLabel: 'Student Type',
                        name: 'studentType',
                        itemId: 'studentType',
                        labelWidth: 80
                    }, {
                        fieldLabel: 'SSP Status',
                        name: 'programStatus',
                        itemId: 'programStatus',
                        labelWidth: 70
                    }, {
                        fieldLabel: 'Academic Program',
                        name: 'academicPrograms',
                        itemId: 'academicPrograms',
                        labelWidth: 120
                    }                   
                    ]
                
                }, {
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    defaults: {
                        anchor: '100%'
                    },
                    flex: .30,
                    items: [{
                        fieldLabel: 'GPA',
                        name: 'cumGPA',
                        itemId: 'cumGPA'
                    }, {
                        fieldLabel: 'Hrs Earned',
                        name: 'hrsEarned',
                        itemId: 'hrsEarned'
                    }, {
                        fieldLabel: 'Hrs Attempted',
                        name: 'hrsAttempted',
                        itemId: 'hrsAttempted'
                    }, {
                        fieldLabel: 'Reg',
                        name: 'registeredTerms'
                    }, {
                        fieldLabel: 'Payment',
                        name: 'paymentStatus'
                    }, {
                        fieldLabel: 'Early Alerts (Open/Total)',
                        itemId: 'earlyAlert',
                        name: 'earlyAlert'
                    }]
                
                }, {
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    defaults: {
                        anchor: '100%'
                    },
                    padding: 0,
                    flex: .30,
                    items: [{
                        xtype: 'profileservicereasons'
                    }, 
					{
                            xtype: 'tbspacer',
                            height: '20'
                        },
					{
                        xtype: 'profilespecialservicegroups'
                    }]
                }]
            }]
        });
        
        return me.callParent(arguments);
    }
    
});
