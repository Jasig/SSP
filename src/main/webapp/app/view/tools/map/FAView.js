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
    //controller: 'Ssp.controller.tool.profile.ProfilePersonViewController',
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
                        name: 'standing',
                        itemId: 'standing',
                        labelWidth: 60
                    }, {
                        fieldLabel: 'Restrictions',
                        name: 'restrictions',
                        itemId: 'restrictions',
                        labelWidth: 80
                    }, {
                        xtype: 'tbspacer',
                        height: '10'
                    },
                    {
                        fieldLabel: 'FA GPA',
                        name: 'faGPA',
                        itemId: 'faGPA',
                        labelWidth: 50
                    }, 
                    {
                        xtype: 'tbspacer',
                        height: '10'
                    },
                    {
                        fieldLabel: 'Hrs Earned',
                        name: 'hrsEarned',
                        itemId: 'hrsEarned',
						labelWidth: 80
                    }, {
                        fieldLabel: 'Hrs Attempted',
                        name: 'hrsAttempted',
                        itemId: 'hrsAttempted',
						labelWidth: 100
                    }, {
                        fieldLabel: '<a href="">Comp Rate</a>',
                        name: 'compRate',
                        itemId: 'compRate',
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
                        name: 'balance',
                        itemId: 'balance',
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
                        name: 'sap',
                        itemId: 'sap',
                        labelWidth: 30
                    }, {
                        fieldLabel: 'F1',
                        name: 'f1',
                        itemId: 'f1',
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
                        name: 'fasfa',
                        itemId: 'fasfa',
						labelWidth: 60
                    
                    }, {
                        fieldLabel: 'FA Award',
                        name: 'faAward',
                        itemId: 'faAward',
						labelWidth: 80
                    
                    }, {
                        fieldLabel: 'FA Amount',
                        name: 'faAmount',
                        itemId: 'faAmount',
						labelWidth: 80
                    
                    }, {
                        fieldLabel: 'Loan Amount',
                        name: 'loanAmount',
                        itemId: 'loanAmount',
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
