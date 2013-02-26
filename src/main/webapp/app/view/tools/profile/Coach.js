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
Ext.define('Ssp.view.tools.profile.Coach', {
    extend: 'Ext.form.Panel',
    alias : 'widget.profilecoach',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.profile.ProfileCoachViewController',
    inject: {
        columnRendererUtils: 'columnRendererUtils',
        sspConfig: 'sspConfig'
    },
    width: '100%',
    height: '100%',
    initComponent: function() { 
        var me=this;
        Ext.apply(me, 
                {
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
                        margin: '0 5 0 0',
                        defaultType: 'displayfield',
                        fieldDefaults: {
                            msgTarget: 'side',
                            labelAlign: 'right',
                            labelWidth: 100
                        },
                        items: [{
                            xtype: 'fieldset',
                            border: 0,
                            title: '',
                            defaultType: 'displayfield',
                            defaults: {
                                anchor: '100%'
                            },
                            flex: .55,
                            items:[{
                        
                                    fieldLabel: me.sspConfig.get('coachFieldLabel'),
                                    name: 'coachName',
                                    itemId: 'coachName',
                                    labelWidth: 80
                                },{
                                    fieldLabel: 'Phone',
                                    name: 'coachWorkPhone',
                                    itemId: 'coachWorkPhone',
                                    labelWidth: 80
                                },{
                                    fieldLabel: 'Email',
                                    name: 'coachPrimaryEmailAddress',
                                    itemId: 'coachPrimaryEmailAddress',
                                    labelWidth: 80
                                },{
                                    fieldLabel: 'Department',
                                    name: 'coachDepartmentName',
                                    itemId: 'coachDepartmentName',
                                    labelWidth: 80
                                },{
                                    fieldLabel: 'Office',
                                    name: 'coachOfficeLocation',
                                    itemId: 'coachOfficeLocation',
                                    labelWidth: 80
                                }]
                            
                        }]
                               
                      }]
                    
                });
        
         return me.callParent(arguments);
    }
    
});