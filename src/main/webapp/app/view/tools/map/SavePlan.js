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
Ext.define('Ssp.view.tools.map.SavePlan', {
    extend: 'Ext.window.Window',
    alias: 'widget.saveplan',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.map.SavePlanViewController',
    inject: {
        columnRendererUtils: 'columnRendererUtils',
		appEventsController: 'appEventsController',
    },
    height: 200,
    width: 850,
    resizable: true,
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            layout: {
                align: 'stretch',
                type: 'vbox'
            },
            title: 'Save Plan',
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
                itemId: 'faSavePlan',
                items: [
                        {
                            xtype: 'fieldset',
                            defaultType: 'checkbox',
                            border: 0,
                            title: '',
                            layout: 'hbox',
                            align: 'stretch',
                            padding: '0 0 15 0',
        					margin: '0 0 0 5',
                            width: '100%',
                            height: '100%',
                            items: [{
                				    
                			    	fieldLabel: 'Active Plan',
                			    	name: 'activeplan',
                			    	labelWidth: 65,
                			    	checked: true,
                			    	labelAlign: 'before',
                			    	inputValue: 'activeplan'
                			    },
                			    {
                                    xtype: 'tbspacer',
                                    width: 20
                                },
                			    {
                			    	
                			    	boxLabel: 'Important / Caution',
                			    	name: 'importantplan',
                			    	labelWidth: 130,
                			    	boxLabelAlign: 'before',
                			    	inputValue: 'importantplan',
									hidden: true,
									hideable: false
                			    
                			    },
                			    {
                                    xtype: 'tbspacer',
                                    width: 20
                                },
                			    {
                			    	
                			    	boxLabel: 'Required For Financial Aid(SAP)',
                			    	name: 'sapplan',
                			    	labelWidth: 200,
                			    	boxLabelAlign: 'before',
                			    	inputValue: 'sap',
									hidden: true,
									hideable: false
                			    },
                			    {
                                    xtype: 'tbspacer',
                                    width: 20
                                },
                			    {
                			    	
                			    	boxLabel: 'F1 visa',
                			    	name: 'f1',
                			    	labelWidth: 70,
                			    	boxLabelAlign: 'before',
                			    	inputValue: 'f1',
									hidden: true,
									hideable: false
                			    }
                			    ]},
        			    	{
                            xtype: 'container',
                            defaultType: 'textfield',
        			    	border: 1,
                            margin: '0 0 0 2',
                            padding: '0 0 0 5',
                            //flex: 1,
                            layout: {
                                align: 'stretch',
                                type: 'vbox'
                            },
                            items: [
				               {
            				        fieldLabel: 'Plan Title',
            				        name: 'name',
            				        itemId: 'name',
            				        maxLength: 50,
            				        allowBlank:false
            				        
            				    },{
            				        fieldLabel: 'Contact Name',
            				        name: 'contactName',
            				        itemId: 'contactName',
            				        maxLength: 50,
            				        allowBlank:true,
									hidden: true,
									hideable: false
            				        
            				    },{
            				        fieldLabel: 'Contact Title',
            				        name: 'contactTitle',
            				        itemId: 'contactTitle',
            				        maxLength: 50,
            				        allowBlank:true,
									hidden: true,
									hideable: false
            				    },{
            				        fieldLabel: 'Contact Phone',
            				        name: 'contactPhone',
            				        itemId: 'contactPhone',
            				        allowBlank:true,
									hidden: true,
									hideable: false
            				    },
            				   {
            				        fieldLabel: 'Academic',
            				        name: 'academic',
            				        allowBlank:true,
            				        itemId: 'academic',
									hidden: true,
									hideable: false
            				    },{
            				        fieldLabel: 'Career Data',
            				        name: 'careerdata',
            				        allowBlank:true,
            				        itemId: 'careerdata',
									hidden: true,
									hideable: false
            				    },{
            				        fieldLabel: 'Advisor/Coach Notes',
            				        name: 'coachnotes',
            				        allowBlank:true,
            				        itemId: 'coachnotes',
            				        xtype: 'textareafield',
									hidden: true,
									hideable: false
            				    },{
            				        fieldLabel: 'Student Notes',
            				    name: 'studentnotes',
            			        allowBlank:true,
            			        itemId: 'addressLine1',
            			        xtype: 'textareafield',
								hidden: true,
								hideable: false
                			    },{
                			        fieldLabel: 'Academic Goals',
                			        name: 'academicgoals',
                			        allowBlank: true,
                			        itemId: 'academicgoals',
                			        xtype: 'textareafield',
									hidden: true,
									hideable: false
                			    }
            			    ]
                    
                    }
                    ],
                        dockedItems: [{
                        xtype: 'toolbar',
                        dock: 'top',
                        items: [{
                            xtype: 'button',
                            itemId: 'saveButton',
                            text: 'Save',
							
							savePlan: function(){
								me = this;
								me.hide();
								this.appEventsController.getApplication().fireEvent('onSaveMapPlan');
							},
							
                            listeners:{
                            	click: 'savePlan',
								scope: me
                            }
                            
                        }, '-', {
                            xtype: 'button',
                            itemId: 'cancelButton',
                            text: 'Cancel',
							cancel: function(){
								me = this;
								me.hide();
							},
							listeners:{
                            	click: 'cancel',
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