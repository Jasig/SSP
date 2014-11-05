/*
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
Ext.define('Ssp.view.tools.map.SaveTemplate', {
    extend: 'Ext.window.Window',
    alias: 'widget.savetemplate',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.map.SaveTemplateViewController',
    inject: {
        columnRendererUtils: 'columnRendererUtils',
        programsStore: 'programsStore',
        departmentsStore: 'departmentsStore',
        authenticatedPerson: 'authenticatedPerson',
        divisionsStore: 'divisionsStore',
        catalogYearsStore: 'catalogYearsStore'
    },
    height: 580,
    width: 850,
    resizable: true,
    modal: true,
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            layout: {
                align: 'stretch',
                type: 'vbox'
            },
            title: 'Save Template',
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
                items: [
                        {
                            xtype: 'fieldset',
                            border: 0,
                            title: '',
                            layout: 'hbox',
                            align: 'stretch',
                            padding: '0 0 15 0',
        					margin: '0 0 0 5',
                            width: '100%',
                            height: 100,
                            items: [{
                				    xtype:'checkbox',
                			    	fieldLabel: 'Active Template',
                			    	name: 'objectStatus',
									itemId: 'objectStatus',
                			    	labelWidth: 95,
                			    	checked: true
                			    },
                			    {
                                    xtype: 'tbspacer',
                                    width: 20
                                },{
						        	xtype: 'combobox',
						        	name: 'visibility',
						        	fieldLabel: 'Visibility',
									labelWidth:60,
									store: Ext.create('Ext.data.Store', {
									    	fields: ['value', 'name'],
									    		data : [
										        	{"value":"ANONYMOUS","name":"ANONYMOUS"},
										        	{"value":"AUTHENTICATED","name":"AUTHENTICATED"},
										        	{"value":"PRIVATE","name":"PRIVATE"}
										        	]
											}),
						        		valueField: 'value',
						        		displayField: 'name',
										value: "PRIVATE",
						        		mode: 'local',
						        		queryMode: 'local',
						        		allowBlank: false,
						        		itemId: 'visibility',
						        		width: 290
						    		}
                			    ]},
                			{
                                    xtype: 'fieldset',
                                    border: 0,
                                    title: '',
                                    defaultType: 'displayfield',
                                    layout: 'hbox',
                                    align: 'stretch',
                                    padding: '0 0 15 0',
                					margin: '0 0 0 5',
                                    
                                    items: [{
        		                        xtype: 'combobox',
        	                        name: 'programCode',
									store: me.programsStore,
        	                        fieldLabel: 'Program',
									labelAlign: "top",
        	                        emptyText: 'Specific Program',
        	                        valueField: 'code',
        	                        displayField: 'name',
        	                        mode: 'local',
        	                        typeAhead: true,
        	                        queryMode: 'local',
        	                        allowBlank: true,
        	                        width: 190
        	                    },
        	                    {
                                    xtype: 'tbspacer',
                                    width: 10
                                },
        	                    {
        	                        xtype: 'combobox',
        	                        name: 'divisionCode',
									store: me.divisionsStore,
        	                        fieldLabel: 'Division',
									labelAlign: "top",
        	                        emptyText: 'Specific Division',
        	                        valueField: 'code',
        	                        displayField: 'name',
        	                        mode: 'local',
        	                        typeAhead: true,
        	                        allowBlank: true,
        	                        width: 190
        	                    },
        	                    {
                                    xtype: 'tbspacer',
                                    width: 10
                                },
        	                    {
        	                        xtype: 'combobox',
        	                        name: 'departmentCode',
									store: me.departmentsStore,
        	                        fieldLabel: 'Department',
									labelAlign: "top",
        	                        emptyText: 'Specific Department',
        	                        valueField: 'code',
        	                        displayField: 'name',
        	                        mode: 'local',
        	                        typeAhead: true,
        	                        allowBlank: true,
        	                        width: 190
        	                    },
        	                    {
                                    xtype: 'tbspacer',
                                    width: 10
                                },
        	                    {
        	                        xtype: 'combobox',
        	                        name: 'catalogYearCode',
									store: me.catalogYearsStore,
        	                        fieldLabel: 'Catalog Year',
									labelAlign: "top",
        	                        emptyText: 'Specific Cat Year',
        	                        valueField: 'code',
        	                        displayField: 'name',
        	                        mode: 'local',
        	                        typeAhead: true,
        	                        allowBlank: true,
        	                        width: 190
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
            				        fieldLabel: 'Template Title',
            				        name: 'name',
									itemId: 'name',
            				        maxLength: 50,
            				        allowBlank:false
            				        
            				    },{
            				        fieldLabel: 'Contact Name',
            				        name: 'contactName',
									itemId: 'contactName',
            				        maxLength: 50            				        
            				    },{
            				        fieldLabel: 'Contact Title',
            				        name: 'contactTitle',
            				        itemId: 'contactTitle',
            				        maxLength: 50,
            				        allowBlank:true
            				    },{
	            				   fieldLabel: 'Contact Email',
	            				   name: 'contactEmail',
		            			   itemId: 'contactEmail',
								   maxLength: 200
	            				},{
            				        fieldLabel: 'Contact Phone',
            				        name: 'contactPhone',
            				        itemId: 'contactPhone',
									maxLength: 200
            				    },
            				   {
            				        fieldLabel: 'Academic Link',
            				        name: 'academicLink',
            				        itemId: 'academicLink',
            				        allowBlank:true,
									maxLength: 2000
            				    },{
            				        fieldLabel: 'Career Link',
            				        name: 'careerLink',
            				        itemId: 'careerLink',
            				        allowBlank:true,
									maxLength: 2000
            				    },{
            				        fieldLabel: 'Advisor/Coach Notes',
            				        name: 'contactNotes',
            				        itemId: 'contactNotes',
            				        allowBlank:true,
            				        xtype: 'textareafield',
									maxLength: 2000
            				    },{
            				        fieldLabel: 'Student Notes',
            				    	name: 'studentNotes',
            				    	itemId: 'studentNotes',
            			        	allowBlank:true,
            			        	xtype: 'textareafield',
									maxLength: 2000
                			    },{
                			        fieldLabel: 'Academic Goals',
                			        name: 'academicGoals',
                			        itemId: 'academicGoals',
                			        allowBlank:true,
                			        xtype: 'textareafield',
									maxLength: 2000
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
                            text: 'Cancel',
                            itemId: 'cancelButton'
                        }]
                    
                    }]
            }
            
            ]
            
        });
        
        return me.callParent(arguments);
    }
    
});