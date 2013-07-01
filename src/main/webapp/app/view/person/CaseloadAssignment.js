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
Ext.define('Ssp.view.person.CaseloadAssignment', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.caseloadassignment',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.CaseloadAssignmentViewController',
    inject: {
        model: 'currentPerson',
        sspConfig: 'sspConfig'
    },
    width: '100%',
    height: '100%',
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            title: "Caseload Assignment",
            autoScroll: true,
            defaults: {
                bodyStyle: 'padding:5px'
            },
            layout: {
                type: 'accordion',
                align: 'stretch',
                titleCollapse: true,
                animate: true,
                activeOnTop: true
            },
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
            
            }, {
                xtype: 'toolbar',
                
                items: [{
                
                    xtype: 'label',
                    text: 'Fill out the following forms with assigned coach details and appointment information'
                }]
            }, {
                dock: 'bottom',
                xtype: 'toolbar',
                items: [                /*,{
                 xtype: 'checkbox',
                 boxLabel: 'Send Student Intake Request',
                 name: 'sendStudentIntakeRequest'
                 },{
                 xtype: 'tbspacer',
                 width: 25
                 },{
                 xtype: 'displayfield',
                 fieldLabel: 'Last Request Date',
                 name: 'lastStudentIntakeRequestDate',
                 value: ((me.model.getFormattedStudentIntakeRequestDate().length > 0) ? me.person.getFormattedStudentIntakeRequestDate() : 'No requests have been sent')
                 }*/
                 {
                    xtype: 'tbspacer',
                    flex: 1
                }, {
                    xtype: 'button',
                    itemId: 'printButton',
                    tooltip: 'Print Appointment Form',
                    hidden: true,
                    width: 30,
                    height: 30,
                    cls: 'printIcon'
                }, {
                    xtype: 'button',
                    itemId: 'emailButton',
                    hidden: true,
                    tooltip: 'Email Appointment Form',
                    width: 30,
                    height: 30,
                    cls: 'emailIcon'
                }]
            }]
        });
        
        return me.callParent(arguments);
    }
    
});