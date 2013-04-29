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
Ext.define('Ssp.view.tools.profile.Dashboard', {
    extend: 'Ext.form.Panel',
    alias: 'widget.profiledashboard',
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
                margin: '0 0 0 0',
                padding: '0 0 0 0',
                defaultType: 'displayfield',
                flex: '.90',
                fieldDefaults: {
                    msgTarget: 'side'
                },
                items: [{
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    margin: '0 0 0 0',
                    defaults: {
                        anchor: '100%'
                    },
                    flex: .45,
                    items: [                    /*{
                     fieldLabel: '<a href="">Watch</a>',
                     name: 'watchStudent',
                     itemId: 'watchStudent',
                     padding: '0 0 0 0',
                     labelWidth: 20,
                     margin: '0 0 1 5'
                     
                     },*/
                    {
                        xtype: 'profileperson',
                    
                    }, {
                        xtype: 'profileacademicprogram',
                    
                    }]
                
                }, {
                    xtype: 'fieldset',
                    border: 1,
                    title: '',
                    defaultType: 'displayfield',
                    margin: '0 0 0 2',
                    defaults: {
                        anchor: '100%'
                    },
                    flex: .25,
                    height: '370',
                    
                    items: [{
                        fieldLabel: 'GPA',
                        name: 'cumGPA',
                        itemId: 'cumGPA'
                    }, {
                        fieldLabel: 'Comp Rate',
                        name: 'creditCompletionRate',
                        itemId: 'creditCompletionRate'
                    }, {
                        fieldLabel: 'Standing',
                        name: 'academicStanding',
                        itemId: 'academicStanding'
                    }, {
                        fieldLabel: 'Restrictions',
                        name: 'currentRestrictions',
                        itemId: 'currentRestrictions'
                    }, {
                        xtype: 'tbspacer',
                        height: '10'
                    }, {
                        fieldLabel: 'Reg',
                        name: 'registeredTerms',
                        itemId: 'registeredTerms',
                        labelWidth: 30
                    }, {
                        fieldLabel: 'Payment',
                        name: 'paymentStatus',
                        itemId: 'paymentStatus',
                        labelWidth: 50
                    }, {
                        fieldLabel: 'FA Award',
                        name: 'currentYearFinancialAidAward',
                        itemId: 'currentYearFinancialAidAward'
                    
                    }, {
                        fieldLabel: 'SAP',
                        name: 'sapStatus',
                        itemId: 'sapStatus'
                    }, {
                        fieldLabel: 'F1',
                        name: 'f1Status',
                        itemId: 'f1Status',
                        labelWidth: 15
                    }, {
                        xtype: 'tbspacer',
                        height: '10'
                    }, {
                        fieldLabel: 'Early Alerts',
                        itemId: 'earlyAlert',
                        name: 'earlyAlert'
                    
                    }, {
                        fieldLabel: 'Action Plan',
                        itemId: 'actionPlan',
                        name: 'actionPlan'
                    }]
                
                }, {
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    defaults: {
                        anchor: '100%'
                    },
                    padding: '0 0 0 5',
                    margin: '0 0 0 0',
                    flex: .30,
                    items: [{
                        xtype: 'profileservicereasons'
                    }, {
                        xtype: 'tbspacer',
                        height: '20'
                    }, {
                        xtype: 'profilespecialservicegroups'
                    }]
                }]
            }, {
                xtype: 'recentsspactivity',
                flex: '.10'
            }]
        });
        
        return me.callParent(arguments);
    }
    
});
