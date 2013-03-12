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
    alias: 'widget.profilecoach',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.profile.ProfileCoachViewController',
    inject: {
        columnRendererUtils: 'columnRendererUtils',
        sspConfig: 'sspConfig'
    },
    width: '100%',
    height: '100%',
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            border: 1,
            bodyPadding: 0,
            layout: 'anchor',
            
            items: [{
                xtype: 'fieldcontainer',
                fieldLabel: '',
                layout: 'hbox',
                margin: '5 5 5 5',
                defaultType: 'displayfield',
				anchor: '100% , 60%',
                fieldDefaults: {
                    msgTarget: 'side'
                },
                items: [{
                    xtype: 'label',
                    html: '<img src=""  height="150" width="150" />',
                    text: '',
                    itemId: 'coachImage'
                }, {
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    
                    padding: '0 5 15 5',
                    
                    flex: .30,
                    items: [{
                    
                        fieldLabel: 'Assigned Coach',
                        name: 'coachName',
                        itemId: 'coachName',
                        labelAlign: 'top',
                        labelPad: 0,
						flex: 1
                    }, {
                        xtype: 'tbspacer',
                        height: '10'
                    }, {
                        fieldLabel: 'Phone',
                        name: 'coachWorkPhone',
                        itemId: 'coachWorkPhone',
                        labelWidth: 80,
						flex: 1
                    
                    }, {
                        fieldLabel: '',
                        name: 'coachPrimaryEmailAddress',
                        itemId: 'coachPrimaryEmailAddress',
						flex: 1
                    }, {
                        xtype: 'tbspacer',
                        height: '10'
                    }, {
                        fieldLabel: '',
                        name: 'coachDepartmentName',
                        itemId: 'coachDepartmentName'
                    
                    }, {
                        fieldLabel: '',
                        name: 'coachOfficeLocation',
                        itemId: 'coachOfficeLocation',
						flex: 1
                    
                    }, {
                        fieldLabel: 'Coach Type',
                        name: 'coachType',
                        itemId: 'coachType',
                    flex: 1
                    
                    }]
                
                }, {
                    xtype: 'fieldset',
                    border: 1,
                    title: 'Most recent activity of this coach with the record',
                    cls: 'makeTitleBold',
                    
                    defaultType: 'displayfield',
                    defaults: {
                        anchor: '100%'
                    },
                    flex: .50,
                    padding: '0 5 5 5',
                    items: [{
                        fieldLabel: 'Date',
                        name: 'coachLastServiceDate',
                        itemId: 'coachLastServiceDate',
                        labelWidth: 80,
                        labelSeperator: false
                    
                    }, {
                        xtype: 'tbspacer',
                        height: '10'
                    }, {
                        fieldLabel: 'Last Service Provided',
                        name: 'coachLastServiceProvided',
                        itemId: 'coachLastServiceProvided',
                        labelAlign: 'top',
                        labelPad: 0,
                        labelWidth: 150
                    
                    }]
                
                }]
            
            }, {
                xtype: 'recentsspactivity',
				anchor: '100% , 40%'
            
            }]
        
        });
        
        return me.callParent(arguments);
    }
    
});
