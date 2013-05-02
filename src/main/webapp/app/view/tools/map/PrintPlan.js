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
Ext.define('Ssp.view.tools.map.PrintPlan', {
    extend: 'Ext.window.Window',
    alias: 'widget.printplan',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.map.PrintPlanController',
    inject: {
        columnRendererUtils: 'columnRendererUtils',
        appEventsController: 'appEventsController'
    },
    height: 300,
    width: 400,
    resizable: true,
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            layout: {
                align: 'stretch',
                type: 'vbox'
            },
            title: 'Print Plan',
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
                itemId: 'faPrintPlan',
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
                        boxLabel: 'Print MAP with Options',
                        name: 'outputFormat',
                        inputValue: 'fullFormat',
                        itemId: 'fullFormat'
                    },
                    {
						checked: true,
                        boxLabel: 'Print MAP in Matrix Format',
                        name: 'outputFormat',
                        inputValue: 'matrixFormat',
                        itemId: 'matrixFormat'
                    }]
                    },
                    {xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'checkbox',
                    margin: '0 0 0 2',
                    padding: '10 0 0 5',
                    layout: 'vbox',
                    align: 'stretch'  ,
                    itemId: 'optionsPrintView',
                        items: [{
                            checked: true,
                            boxLabel: 'With Course Description',
                            name: 'includeCourseDescription',
                        },
                        {
                            checked: true,
                            boxLabel: 'With Header/Footer',
                            name: 'includeHeaderFooter',
                        },
                        {
							checked: true,
                            name: 'includeTotalTimeExpected',
                            labelSeparator: '',
                            boxLabel: 'Total Time Expected Outside Class'
                         },
                         {
							checked: true,
                            name: 'includeFinancialAidInformation',
                            labelSeparator: '',
                            boxLabel: 'Display FinAid Information'
                         }
                        ]
                        }
                 
                        ],
                            dockedItems: [{
                            xtype: 'toolbar',
                            dock: 'top',
                            items: [{
                                xtype: 'button',
                                itemId: 'sendPrintButton',
                                text: 'Print',
                                listeners:{
                                	click: function(){
                                		me = this;
										var form = me.query('form')[0];
										var record = new Ssp.model.tool.map.PlanOutputData();
										form.getForm().updateRecord(record);
                                		me.appEventsController.getApplication().fireEvent(me.printEvent, record);
                                		me.close();
                                	},
                                	scope: me
                                }
                            }, '-', {
                                xtype: 'button',
                                itemId: 'cancelButton',
                                text: 'Cancel',
                                listeners: {
    		                    	click:function(){
    		                    		me = this;
    		                    		me.close();
    		                    	},
    		                    	scope: me
    		                    }
                            }]
                        
                        }]
                }
                
                ]
        });
        
        return me.callParent(arguments);
    }
    
});
