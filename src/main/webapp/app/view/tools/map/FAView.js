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
Ext.define('Ssp.view.tools.map.FAView', {
    extend: 'Ext.window.Window',
    alias: 'widget.faview',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.map.FAViewController',
    inject: {
        columnRendererUtils: 'columnRendererUtils'
        //sspConfig: 'sspConfig'
    },
    height: 500,
    width: 500,
    resizable: true,
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            layout: {
                align: 'stretch',
                type: 'vbox'
            },
            title: 'Financial Aid',
            items: [{
                xtype: 'form',
                flex: 1,
                border: 0,
                frame: false,
                layout: {
                    align: 'stretch',
                    type: 'vbox'
                },
                 width: '100%',
                height: '100%',
                bodyPadding: 0,
                autoScroll: true,
                itemId: 'faForm',
                items: [{
                xtype: 'fieldcontainer',
                fieldLabel: '',
                layout: 'vbox',
                align: 'stretch',
                defaultType: 'displayfield',
                fieldDefaults: {
                    msgTarget: 'side'
                },
                items: [ {
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    margin: '0 0 0 2',
                    padding: '0 0 0 5',
                    layout: 'vbox',
                    align: 'stretch',
                    
                    items: [
                    {
                        fieldLabel: 'GPA',
                        name: 'cumGPA',
                        itemId: 'cumGPA',
                        labelWidth: 30
                    
                    },  
                    {
                        xtype: 'tbspacer',
                        height: '10'
                    }, {
                        fieldLabel: 'Standing',
                        name: 'academicStanding',
                        itemId: 'academicStanding',
                        labelWidth: 60
                    }, {
                        fieldLabel: 'Restrictions',
                        name: 'currentRestrictions',
                        itemId: 'currentRestrictions',
                        labelWidth: 80
                    }, {
                        xtype: 'tbspacer',
                        height: '10'
                    },
                    {
                        fieldLabel: 'FA GPA',
                        name: 'financialAidGpa',
                        itemId: 'financialAidGpa',
                        labelWidth: 50
                    }, 
                    {
                        xtype: 'tbspacer',
                        height: '10'
                    },
                    {
                        fieldLabel: 'Hrs Earned',
                        name: 'creditHoursEarned',
                        itemId: 'creditHoursEarned',
						labelWidth: 80
                    }, {
                        fieldLabel: 'Hrs Attempted',
                        name: 'creditHoursAttempted',
                        itemId: 'creditHoursAttempted',
						labelWidth: 100
                    }, {
                        fieldLabel: '<a href="">Comp Rate</a>',
                        name: 'creditCompletionRate',
                        itemId: 'creditCompletionRate',
						labelWidth: 80
                    },
                    {
                        xtype: 'tbspacer',
                        height: '10'
                    },
                    
                    ]
                
                },
                {
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    layout: 'hbox',
                    align: 'stretch',
                    padding: 0,
					margin: '0 0 0 5',
                    
                    items: [{
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    layout: 'vbox',
                    align: 'stretch',
                    padding: 0,
                    margin: '0 0 0 5',
                    items: [ 
                     {
                        fieldLabel: 'Reg',
                        name: 'registeredTerms',
                        itemId: 'registeredTerms',
                        labelWidth: 30
                    }, {
                        fieldLabel: 'Payment',
                        name: 'paymentStatus',
                        itemId: 'paymentStatus',
                        labelWidth: 80
                    },, {
                        fieldLabel: 'Balance',
                        name: 'remainingLoanAmount',
                        itemId: 'remainingLoanAmount',
                        labelWidth: 80
                    }       
                            ]
                },
                {
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    layout: 'vbox',
                    align: 'stretch',
                    padding: 0,
                    margin: '0 0 0 5',
                    items: [        
                           
                    
                    {
                        fieldLabel: 'SAP',
                        name: 'sapStatus',
                        itemId: 'sapStatus',
                        labelWidth: 30
                    }, {
                        fieldLabel: 'F1',
                        name: 'f1Status',
                        itemId: 'f1Status',
                        labelWidth: 30
                    }]
                }
                ]}
                
                ,
                {
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    layout: 'vbox',
                    align: 'stretch',
                    padding: '0 0 0 5',
					margin: '0 0 0 5',
                   
                    items: [{
                        fieldLabel: 'FASFA',
                        name: 'fafsaDate',
                        itemId: 'fafsaDate',
						labelWidth: 60
                    
                    }, {
                        fieldLabel: 'FA Award',
                        name: 'currentYearFinancialAidAward',
                        itemId: 'currentYearFinancialAidAward',
						labelWidth: 80
                    
                    }, {
                        fieldLabel: 'FA Amount Remaining',
                        name: 'financialAidRemaining',
                        itemId: 'financialAidRemaining',
						labelWidth: 80
                    
                    }, {
                        fieldLabel: 'Loan Amount',
                        name: 'originalLoanAmount',
                        itemId: 'originalLoanAmount',
						labelWidth: 80
                    
                    }]
                }
                ]
            }]
            }]
        });
        
        return me.callParent(arguments);
    }
    
});
