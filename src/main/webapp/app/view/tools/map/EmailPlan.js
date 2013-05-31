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
        columnRendererUtils: 'columnRendererUtils',
        appEventsController: 'appEventsController'
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
                items: [{
                	xtype: 'container',
                    defaultType: 'textfield',
                    margin: '0 0 0 2',
                    padding: '0 0 0 5',
                    //flex: 1,
                    layout: {
                        align: 'stretch',
                        type: 'vbox'
                    },
                    items: [{
                    	   		xtype: 'container',
                    	   		defaultType: 'textfield',
                    	   		margin: '0 0 0 0',
                    	   		padding: '0 0 0 0',
                    	   		width: '100%',
                    	   		layout: {
                               		align: 'stretch',
                               		type: 'hbox'
                           			},
                           		items: [{
                           				xtype: 'textfield',
                                    	name: 'emailTo',
                                    	vtype: 'multiemail',
                                    	fieldLabel: 'To',
                                    	labelWidth: 30,
										width:400
                                    	}]
                              },
                              {
                               	  xtype: 'container',
                                  title: '',
                                  margin: '0 0 0 0',
                                  padding: '5 0 5 0',
                                  layout: 'hbox',
                                  align: 'stretch' ,
                                  items: [{
                                           	xtype: 'textfield',
                                           	name: 'emailCC',
                                           	fieldLabel: 'cc',
                                           	vtype: 'multiemail',
                                           	labelWidth: 30,
											width:400
                                           }]
                               },
                               {
                            	   xtype: 'textareafield',
                            	   name: 'notes',
                            	   fieldLabel: 'notes',
                            	   labelWidth: 48,
								   inputWidth:400,
                            	   rows: 5,
                            	   grow: true
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
							
                            items: [ {
                                	checked: false,
                                	boxLabel: 'Email MAP with Options',
                                	name: 'outputFormat',
                                	inputValue: 'fullFormat',
                                	itemId: 'fullFormat'
                            	},
                            	{
									checked: true,
                                	boxLabel: 'Email MAP in Matrix Format',
                                	name: 'outputFormat',
                                	inputValue: 'matrixFormat',
                                	itemId: 'matrixFormat'
                            	}
							]},
                            {
                            	xtype: 'fieldset',
                                border: 0,
                                title: '',
                                defaultType: 'checkbox',
                                margin: '0 0 0 2',
                                padding: '0 0 0 5',
                                layout: 'vbox',
                                align: 'stretch' ,
                                itemId: 'optionsEmailView',
                                items: [{
                                    checked: true,
                                    boxLabel: 'With Course Description',
                                    name: 'includeCourseDescription'
                                },
                                {
                                    checked: true,
                                    boxLabel: 'With Header/Footer',
                                    name: 'includeHeaderFooter'
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
                                    boxLabel: 'Display Financial Aid Information'
                                 }]
                                }],
                                    dockedItems: [{
                                    xtype: 'toolbar',
                                    dock: 'top',
                                    items: [{
                                        xtype: 'button',
                                        itemId: 'sendEmailButton',
                                        text: 'Send Email',
                                        listeners:{
                                        	click: function(){
                                        		me = this;
												var form = me.query('form')[0];
												var record = new Ssp.model.tool.map.PlanOutputData();
												form.getForm().updateRecord(record);
                                        		me.appEventsController.getApplication().fireEvent(me.emailEvent, record);
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
                        }]
            
        });
        return me.callParent(arguments);
    }
    
});
