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
Ext.define('Ssp.view.tools.map.EmailPlan', {
    extend: 'Ext.window.Window',
    alias: 'widget.emailplan',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.map.EmailPlanController',
    inject: {
        columnRendererUtils: 'columnRendererUtils'
        //sspConfig: 'sspConfig'
    },
    height: 500,
    width: 700,
    resizable: true,
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            layout: {
                align: 'stretch',
                type: 'vbox'
            },
            title: 'Email Plan',
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
                bodyPadding: 5,
                autoScroll: true,
                itemId: 'faEmailPlan',
                items: [
                        {
                        	xtype: 'fieldset',
                            border: 0,
                            title: '',
                            defaultType: 'radio',
                            margin: '0 0 0 2',
                            padding: '0 0 0 5',
                            layout: 'vbox',
                            align: 'stretch' ,
                            items: [ {
                                checked: true,
                                boxLabel: 'Email MAP with Options',
                                name: 'optionsEmail',
                                inputValue: 'optionsEmail',
                                itemId: 'optionsEmailFormat'
                            },
                            {
                                boxLabel: 'Email MAP in Matrix Format',
                                name: 'optionsEmail',
                                inputValue: 'emailmatrixFormat',
                                itemId: 'emailmatrixFormat'
                            }]
                            },
                            {
                            	xtype: 'fieldset',
                                border: 0,
                                title: '',
                                defaultType: 'radio',
                                margin: '0 0 0 2',
                                padding: '0 0 0 5',
                                layout: 'vbox',
                                align: 'stretch' ,
                                itemId: 'optionsEmailView',
                                items: [{
                                    checked: true,
                                    boxLabel: 'With Course Description',
                                    name: 'courseDescriptionEmail',
                                    inputValue: 'courseDescEmail'
                                },
                                {
                                    boxLabel: 'Without Course Description',
                                    name: 'courseDescriptionEmail',
                                    inputValue: 'withoutcourseDescEmail'
                                },
                                {
                                    checked: true,
                                    boxLabel: 'With Header/Footer',
                                    name: 'headerEmail',
                                    inputValue: 'headerEmail'
                                },
                                {
                                    boxLabel: 'Without Header/Footer',
                                    name: 'headerEmail',
                                    inputValue: 'footerEmail'
                                },
                                {
                				    
                                    name: 'totalTimeExpected',
                                    inputValue: 'totalTimeExpected',
                                    xtype:'checkbox',
                                    //padding: '0 0 0 105',
                                    labelSeparator: '',
                                    hideLabel: true,
                                    boxLabel: 'Total Time Expected Outside Class'
                                    //fieldLabel: 'Mark As Important' 
                                    },
                                    {
                    				    
                                    name: 'finAidInfo',
                                    inputValue: 'finAidInfo',
                                    xtype:'checkbox',
                                    //padding: '0 0 0 105',
                                    labelSeparator: '',
                                    hideLabel: true,
                                    boxLabel: 'Display FinAid Information'
                                    }
                                ]
                                },
                                {
                                	xtype: 'container',
                                    defaultType: 'textfield',
                                    margin: '0 0 0 2',
                                    padding: '0 0 0 5',
                                    //flex: 1,
                                    layout: {
                                        align: 'stretch',
                                        type: 'vbox'
                                    },
                                    items: [
                                       {
                                    	   xtype: 'container',
                                           defaultType: 'textfield',
                                           margin: '0 0 0 0',
                                           padding: '0 0 0 0',
                                           width: '100%',
                                           layout: {
                                               align: 'stretch',
                                               type: 'hbox'
                                           },
                                       items: [
                                                   {
                                                    name: 'checkEmailTo',
                                                    inputValue: 'checkEmailTo',
                                                    xtype:'checkbox',
                                                    padding: '0 5 0 0',
                                                    labelSeparator: '',
                                                    hideLabel: true
                                                    } , 
                                                    {
                                                    	xtype: 'textfield',
                                                    	name: 'emailTo',
                                                    	fieldLabel: 'To',
                                                    	labelWidth: 30
                                                    }    
                                                     ]
                                                  },
                                                  {
                                               	   xtype: 'container',
                                                  title: '',
                                                  margin: '0 0 0 0',
                                                  padding: '5 0 5 0',
                                                  layout: 'hbox',
                                                  align: 'stretch' ,
                                                  items: [
                                                           {
                                                           name: 'checkEmailCC',
                                                           inputValue: 'checkEmailCC',
                                                           xtype:'checkbox',
                                                           padding: '0 5 0 0',
                                                           labelSeparator: '',
                                                           hideLabel: true
                                                           }  , 
                                                           {
                                                           	xtype: 'textfield',
                                                           	name: 'emailCC1',
                                                           	fieldLabel: 'cc',
                                                           	labelWidth: 30
                                                           }  
                                                            ]
                                               },
                                    {
                                    	xtype: 'textfield',
                                    	name: 'emailCC2',
                                    	fieldLabel: 'cc',
                                    	labelWidth: 48
                                    },
                                    {
                                    	xtype: 'textareafield',
                                    	name: 'EmailNotes',
                                    	fieldLabel: 'Notes',
                                    	labelWidth: 48
                                    }
                                    ]
                                    }
                         
                                ],
                                    dockedItems: [{
                                    xtype: 'toolbar',
                                    dock: 'top',
                                    items: [{
                                        xtype: 'button',
                                        itemId: 'sendEmailButton',
                                        text: 'Send Email'
                                        
                                    }, '-', {
                                        xtype: 'button',
                                        itemId: 'cancelButton',
                                        text: 'Cancel'
                                    }]
                                
                                }]
                        }
                        
                        ]
            
        });
        
        return me.callParent(arguments);
    }
    
});
