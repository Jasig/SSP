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
        catalogYearsStore: 'catalogYearsStore',
        mapTemplateTagsStore: 'mapTemplateTagsAllStore',
        textStore: 'sspTextStore'
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
            title: me.textStore.getValueByCode('ssp.label.map.save-template.title','Save Template'),
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
                            xtype: 'container',
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
                			    	fieldLabel: me.textStore.getValueByCode('ssp.label.map.save-template.object-status','Active Template'),
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
						        	fieldLabel: me.textStore.getValueByCode('ssp.label.map.save-template.visibility','Visibility'),
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
                                    xtype: 'container',
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
        	                        fieldLabel: me.textStore.getValueByCode('ssp.label.map.save-template.program-code','Program'),
									labelAlign: "top",
        	                        emptyText: me.textStore.getValueByCode('ssp.empty-text.map.save-template.program-code','Specific Program'),
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
        	                        fieldLabel: me.textStore.getValueByCode('ssp.label.map.save-template.division','Division'),
									labelAlign: "top",
        	                        emptyText: me.textStore.getValueByCode('ssp.empty-text.map.save-template.division','Specific Division'),
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
        	                        fieldLabel: me.textStore.getValueByCode('ssp.label.map.save-template.department','Department'),
									labelAlign: "top",
        	                        emptyText: me.textStore.getValueByCode('ssp.empty-text.map.save-template.department','Specific Department'),
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
        	                        fieldLabel: me.textStore.getValueByCode('ssp.label.map.save-template.catalog-year-code','Catalog Year'),
									labelAlign: "top",
        	                        emptyText: me.textStore.getValueByCode('ssp.empty-text.map.save-template.catalog-year-code','Specific Cat Year'),
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
            				        fieldLabel: me.textStore.getValueByCode('ssp.label.map.save-template.template-title','Template Title'),
            				        name: 'name',
									itemId: 'name',
            				        maxLength: 50,
            				        allowBlank:false
            				        
            				    },{
            				        fieldLabel: me.textStore.getValueByCode('ssp.label.map.save-template.contact-name','Contact Name'),
            				        name: 'contactName',
									itemId: 'contactName',
            				        maxLength: 50            				        
            				    },{
            				        fieldLabel: me.textStore.getValueByCode('ssp.label.map.save-template.contact-title','Contact Title'),
            				        name: 'contactTitle',
            				        itemId: 'contactTitle',
            				        maxLength: 50,
            				        allowBlank:true
            				    },{
	            				   fieldLabel: me.textStore.getValueByCode('ssp.label.map.save-template.contact-email','Contact Email'),
	            				   name: 'contactEmail',
		            			   itemId: 'contactEmail',
								   maxLength: 200
	            				},{
            				        fieldLabel: me.textStore.getValueByCode('ssp.label.map.save-template.contact-phone','Contact Phone'),
            				        name: 'contactPhone',
            				        itemId: 'contactPhone',
									maxLength: 200
            				    },
            				   {
            				        fieldLabel: me.textStore.getValueByCode('ssp.label.map.save-template.academic-link','Academic Link'),
            				        name: 'academicLink',
            				        itemId: 'academicLink',
            				        allowBlank:true,
									maxLength: 2000
            				    },{
            				        fieldLabel: me.textStore.getValueByCode('ssp.label.map.save-template.career-link','Career Link'),
            				        name: 'careerLink',
            				        itemId: 'careerLink',
            				        allowBlank:true,
									maxLength: 2000
            				    },{
            				        fieldLabel: me.textStore.getValueByCode('ssp.label.map.save-template.contact-notes','Advisor/Coach Notes'),
            				        name: 'contactNotes',
            				        itemId: 'contactNotes',
            				        allowBlank:true,
            				        xtype: 'textareafield',
									maxLength: 4000
            				    },{
            				        fieldLabel: me.textStore.getValueByCode('ssp.label.map.save-template.student-notes','Student Notes'),
            				    	name: 'studentNotes',
            				    	itemId: 'studentNotes',
            			        	allowBlank:true,
            			        	xtype: 'textareafield',
									maxLength: 4000
                			    },{
                			        fieldLabel: me.textStore.getValueByCode('ssp.label.map.save-template.academic-goals','Academic Goals'),
                			        name: 'academicGoals',
                			        itemId: 'academicGoals',
                			        allowBlank:true,
                			        xtype: 'textareafield',
									maxLength: 2000
                			    },{
									xtype: 'combobox',
									name: 'mapTemplateTagId',
									itemId: 'mapTemplateTagId',
									id: 'mapTemplateTagId',
									store: me.mapTemplateTagsStore,
									fieldLabel: me.textStore.getValueByCode('ssp.label.map-template-tag',"Template Tag"),
									emptyText: me.textStore.getValueByCode('ssp.empty-text.map-template-tag',"Specific Template Tag"),
									valueField: 'id',
									displayField: 'name',
									mode: 'local',
									queryMode: 'local',
									typeAhead: false,
   									editable: false,
									allowBlank: true
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
                            text: me.textStore.getValueByCode('ssp.label.save-button','Save')
                            
                        }, '-', {
                            xtype: 'button',
                            text: me.textStore.getValueByCode('ssp.label.cancel-button','Cancel'),
                            itemId: 'cancelButton'
                        }]
                    
                    }]
            }
            
            ],
            listeners: {
                 afterrender: function(c){
                     c.el.dom.setAttribute('role', 'dialog');
                 }
            }
        });
        
        return me.callParent(arguments);
    }
    
});