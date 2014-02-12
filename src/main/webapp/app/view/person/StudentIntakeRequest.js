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
Ext.define('Ssp.view.person.StudentIntakeRequest', {
    extend: 'Ext.form.Panel',
    alias: 'widget.studentIntakeRequest',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.StudentIntakeRequestViewController',
    
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            fieldDefaults: {
                msgTarget: 'side',
                
                labelWidth: 260
            },
            border: 0,
            padding: 0,
            items: [{
                xtype: 'fieldset',
                border: 0,
                padding: 0,
                title: '',
                defaultType: 'textfield',
                defaults: {
                    anchor: '100%'
                },
                items: [{
                    xtype: 'checkboxfield',
                    boxLabel: 'Send Student Email Intake Request',
                    name: 'studentIntakeRequested',
                    itemId: 'studentIntakeRequestedField',
                    inputValue: true,
					boxLabelAlign: 'after'
					
                }, {
                    //xtype: 'textfield',
                    fieldLabel: 'Also Send Student Intake Request To Email',
                    name: 'intakeEmail',
                    itemId: 'intakeEmailField',
                    hidden: true,
                    maxLength: 100,
                    vtype: 'email',
					labelAlign: 'top',
					width: 250
                }]
            }]
        });
        
        return me.callParent(arguments);
    }
});
