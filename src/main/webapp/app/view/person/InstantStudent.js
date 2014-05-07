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
Ext.define('Ssp.view.person.InstantStudent', {
    extend: 'Ext.form.Panel',
    alias: 'widget.instantstudent',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.StudentViewController',
    inject: {
        textStore: 'sspTextStore'
    },
    width: '100%',
    height: '100%',
   
    bodyPadding: 5,
    
    initComponent: function(){
        var me = this;
        
        Ext.applyIf(me, {
            border: 0,
            bodyPadding: 0,
            layout: {
                align: 'stretch',
                type: 'vbox' 
            },
            fieldDefaults: {
                msgTarget: 'side',
                labelAlign: 'right',
                labelWidth: 100
            },
			items: [
                {
                    xtype: 'fieldcontainer',
                    flex: 0.35,
					padding: '0 10 0 0',
					margin: '0 0 0 0',
                    fieldLabel: '',
					layout: {
						type: 'vbox'
					},
					items: [
                        {
                            xtype: 'editperson',
                            instantCaseloadAssignment:me.instantCaseloadAssignment,
                            schoolIdValue: me.schoolIdValue
                        }
                    ]
                },
                {
                    xtype: 'fieldcontainer',
                    fieldLabel: '',
					padding: ' 0 0 0 0',
					margin: '0 0 0 0',
					flex: 0.30,
					layout: {
						type: 'vbox'
					},
					items: [
                        {
                    xtype: 'personappointment',
                    instantCaseloadAssignment:me.instantCaseloadAssignment,
                    hidden: false,
                },
                {
                    xtype: 'personcoach',
                    instantCaseloadAssignment:me.instantCaseloadAssignment,
                    schoolIdValue: me.schoolIdValue,
                    coachIdValue: me.coachIdValue
                },
                {
                    xtype: 'fieldcontainer',
                    flex: 0.35,
                    fieldLabel: '',
					padding: '0 10 0 0',
					margin: '0 0 0 0',
					layout: {
						type: 'vbox'
					},
					items: [
                        
						{
                    xtype: 'fieldset',
					padding: '0 10 0 0',
                    title: 'Student Intake',
                    hidden:true,
					border: 0,
                    layout: {
						flex: .90
                    },
                    items: [{
                        xtype: 'studentIntakeRequest',
                    },{
                            fieldLabel: 'Student Intake Request Last Sent ',
                            name: 'studentIntakeRequestDateInCaseload',
                            itemId: 'studentIntakeRequestDateInCaseload',
                            xtype: 'displayfield',
                            labelWidth: 195,
                            renderer: Ext.util.Format.dateRenderer('m/d/Y')
                        }, {
                            fieldLabel: 'Student Intake Request Completed',
                            name: 'studentIntakeCompleteDateInCaseload',
                            itemId: 'studentIntakeCompleteDateInCaseload',
                            xtype: 'displayfield',
                            labelWidth: 195,
                            value: 'test',
                            renderer: Ext.util.Format.dateRenderer('m/d/Y')
                        
                        }]
            }
						
                    ]
                },
                 {
                    xtype: 'personanticipatedstartdate',
                    hidden: true,
                    instantCaseloadAssignment:me.instantCaseloadAssignment,
                    schoolIdValue: me.schoolIdValue
                }
                    ]
                }
            ]
			
            
        });
        
        me.callParent(arguments);
    }
    
});
