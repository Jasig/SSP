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
		currentMapPlan: 'currentMapPlan'
    },
    height: 500,
    width: 550,
    saveAs: null,
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
                 width: '80%',
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
                			    	name: 'objectStatus',
									itemId:'objectStatus',
                			    	labelWidth: 65,
                			    	checked: false,
                			    	labelAlign: 'before',
                			    	inputValue: 'objectStatus'
                			    },
                			    {
                                    xtype: 'tbspacer',
                                    width: 20
                                },
                			    {
                			    	
                			    	boxLabel: 'Important / Caution',
                			    	name: 'isImportant',
                			    	labelWidth: 130,
                			    	boxLabelAlign: 'before',
                			    	inputValue: 'isImportant',
									itemId:'isImportant'
                			    
                			    },
                			    {
                                    xtype: 'tbspacer',
                                    width: 20
                                },
                			    {
                			    	
                			    	boxLabel: 'Required For Financial Aid(SAP)',
                			    	name: 'isFinancialAid',
                			    	labelWidth: 200,
                			    	boxLabelAlign: 'before',
                			    	inputValue: 'isFinancialAid',
									itemId:'isFinancialAid'
                			    },
                			    {
                                    xtype: 'tbspacer',
                                    width: 20
                                },
                			    {
                			    	
                			    	boxLabel: 'F1 visa',
                			    	name: 'isF1Visa',
                			    	labelWidth: 70,
                			    	boxLabelAlign: 'before',
                			    	inputValue: 'isF1Visa',
									itemId:'isF1Visa'
                			    }
                			    ]},
        			    	{
                            xtype: 'container',
                            defaultType: 'textfield',
        			    	border: 1,
                            margin: '0 0 0 2',
                            padding: '0 0 0 5',
							width: '80%',
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
									width: '80%',
            				        allowBlank:false
            				    },{
            				        fieldLabel: 'Contact Name',
            				        name: 'contactName',
            				        itemId: 'contactName',
            				        maxLength: 50,
									width: '80%',
            				        allowBlank:true
            				        
            				    },{
            				        fieldLabel: 'Contact Title',
            				        name: 'contactTitle',
            				        itemId: 'contactTitle',
            				        maxLength: 50,
									width: '80%',
            				        allowBlank:true
            				    },{
	            				    fieldLabel: 'Contact Email',
	            				    name: 'contactEmail',
	            				    itemId: 'contactEmail',
									width: '80%',
	            				   allowBlank:true
	            				 },{
            				        fieldLabel: 'Contact Phone',
            				        name: 'contactPhone',
            				        itemId: 'contactPhone',
									width: '80%',
            				        allowBlank:true
            				    },
            				   {
            				        fieldLabel: 'Academic',
            				        name: 'academicLink',
            				        allowBlank:true,
									width: '80%',
            				        itemId: 'academicLink'
            				    },{
            				        fieldLabel: 'Career Data',
            				        name: 'careerLink',
            				        allowBlank:true,
									width: '80%',
            				        itemId: 'careerLink'
            				    },{
            				        fieldLabel: 'Advisor/Coach Notes',
            				        name: 'contactNotes',
            				        allowBlank:true,
            				        itemId: 'contactNotes',
									width: '80%',
            				        xtype: 'textareafield'
            				    },{
            				        fieldLabel: 'Student Notes',
            				    	name: 'studentNotes',
            			        	allowBlank:true,
            			        	itemId: 'studentNotes',
            			        	xtype: 'textareafield',
									width: '80%'
                			    },{
                			        fieldLabel: 'Academic Goals',
                			        name: 'academicGoals',
                			        allowBlank: true,
                			        itemId: 'academicGoals',
                			        xtype: 'textareafield',
									width: '80%'
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
                            text: 'Save'

                            
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