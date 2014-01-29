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
Ext.define('Ssp.view.tools.map.LoadTemplates', {
    extend: 'Ext.window.Window',
    alias: 'widget.loadtemplates',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
	controller: 'Ssp.controller.tool.map.LoadTemplateViewController',
    inject: {
        columnRendererUtils: 'columnRendererUtils',
        programsStore: 'programsFacetedStore',
        departmentsStore: 'departmentsStore',
        divisionsStore: 'divisionsStore',
        store: 'planTemplatesSummaryStore'
    },
    height: 500,
    width: 900,
    resizable: true,
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            layout: {
                align: 'stretch',
                type: 'hbox'
            },
            title: 'Load Template',
			dockedItems: [{
                xtype: 'toolbar',
                dock: 'top',
                items: [{
                    xtype: 'button',
                    itemId: 'openButton',
                    text: 'Open'
                    
                }, '-', {
                    xtype: 'button',
                    itemId: 'cancelButton',
                    text: 'Cancel'
                }]
            }],
           items: [
				
				{
                xtype: 'form',
                flex: 1,
                border: 0,
                frame: false,
				title: "Filters",
				titleAlign:"center",
                layout: {
                    align: 'stretch',
                    type: 'hbox'
                },
                width: 330,
                height: '80%',
                bodyPadding: 10,
                autoScroll: true,
                items: [
                {
                    xtype: 'fieldcontainer',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    layout: 'hbox',
                    align: 'stretch',
                    padding: 0,
                    margin: '0 0 0 0',
                    
                    items: [
                      
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
						    xtype: 'fieldset',
						    border: 0,
						    title: '',
						    margin: '0 0 0 0',
						    padding: '0 0 0 5',
						    layout: 'hbox',
						    defaults: {
						        anchor: '100%'
						    },
						    items: [{
						        xtype: 'combobox',
						        name: 'program',
						        store: me.programsStore,
						        fieldLabel: 'Program',
								labelWidth:80,
						        emptyText: 'Filter by Program',
						        valueField: 'code',
						        displayField: 'name',
						        mode: 'local',
						        queryMode: 'local',
						        allowBlank: true,
						        itemId: 'program',
						        width: 260
						    	}, {
							        tooltip: 'Reset to All Programs',
							        text: '',
							        width: 23,
							        height: 25,
							        name: 'programCancel',
							        cls: 'mapClearSearchIcon',
							        xtype: 'button',
							        itemId: 'programCancel'
							    }]
							},
							{
							    xtype: 'fieldset',
							    border: 0,
							    title: '',
							    margin: '0 0 0 0',
							    padding: '0 0 0 5',
							    layout: 'hbox',
							    defaults: {
							        anchor: '100%'
							    	},
							    items: [{
							        xtype: 'combobox',
							        name: 'department',
							        store: me.departmentsStore,
							        fieldLabel: 'Department',
									labelWidth:80,
							        emptyText: 'Filter by Department',
							        valueField: 'code',
							        displayField: 'name',
							        mode: 'local',
							        queryMode: 'local',
							        allowBlank: true,
							        itemId: 'department',
							        width: 260
							    	}, {
							        tooltip: 'Reset to All Departments',
							        text: '',
							        width: 23,
							        height: 25,
							        name: 'departmentCancel',
							        cls: 'mapClearSearchIcon',
							        xtype: 'button',
							        itemId: 'departmentCancel'
							    	}]
							},{
							    xtype: 'fieldset',
							    border: 0,
							    title: '',
							    margin: '0 0 0 0',
							    padding: '0 0 0 5',
							    layout: 'hbox',
							    defaults: {
							        anchor: '100%'
							    	},
							    items: [{
							        xtype: 'combobox',
							        name: 'division',
							        store: me.divisionsStore,
							        fieldLabel: 'Division',
									labelWidth:80,
							        emptyText: 'Filter by Division',
							        valueField: 'code',
							        displayField: 'name',
							        mode: 'local',
							        queryMode: 'local',
							        allowBlank: true,
							        itemId: 'division',
							        width: 260
							    	}, {
							        tooltip: 'Reset to All Divisions',
							        text: '',
							        width: 23,
							        height: 25,
							        name: 'divisionCancel',
							        cls: 'mapClearSearchIcon',
							        xtype: 'button',
							        itemId: 'divisionCancel'
							    	}]
							},{
						    xtype: 'fieldset',
						    border: 0,
						    title: '',
						    margin: '0 0 0 0',
						    padding: '0 0 0 5',
						    layout: 'hbox',
						    defaults: {
						        anchor: '100%'
						    	},
						    items: [{
						        	xtype: 'combobox',
						        	name: 'isPrivateFilter',
						        	fieldLabel: 'Type',
									labelWidth:80,
									store: Ext.create('Ext.data.Store', {
									    fields: ['value', 'name'],
									    	data : [
										        	{"value":"ALL","name":"ALL"},
										        	{"value":"ANONYMOUS","name":"ANONYMOUS"},
										        	{"value":"AUTHENTICATED","name":"AUTHENTICATED"},
										        	{"value":"PRIVATE","name":"PRIVATE"}
										    	]
									}),
								
						        		emptyText: 'Filter by Template Type',
						        		valueField: 'value',
						        		displayField: 'name',
										value: "ALL",
						        		mode: 'local',
						        		queryMode: 'local',
						        		allowBlank: false,
						        		itemId: 'typeFilter',
						        		width: 290
						    		}]
								},{
					    	xtype: 'fieldset',
					    	border: 0,
					    	title: '',
					    	margin: '0 0 0 0',
					    	padding: '0 0 0 5',
					    	layout: 'hbox',
					    	defaults: {
					        		anchor: '100%'
					    			},
					    	items: [{
				        		xtype: 'combobox',
				        		name: 'objectStatusFilter',
				        		fieldLabel: 'Status',
								labelWidth:80,
				        		emptyText: 'Filter by Status',
								store: Ext.create('Ext.data.Store', {
								    fields: ['value', 'name'],
								    	data : [
									        	{"value":"ALL","name":"ALL"},
									        	{"value":"ACTIVE","name":"ACTIVE"},
									        	{"value":"INACTIVE","name":"INACTIVE"}
									    	]
								}),
				        		valueField: 'name',
				        		displayField: 'name',
								defaultValue: "ALL",
								value:'ALL',
				        		mode: 'local',
				        		queryMode: 'local',
				        		allowBlank: false,
				        		itemId: 'objectStatusFilter',
				        		width: 290
				    			}]
							},
							{
                            xtype: 'fieldset',
					    	border: 0,
					    	title: '',
					    	margin: '0 0 0 0',
					    	padding: '0 0 0 5',
					    	layout: 'hbox',
					    	defaults: {
					        		anchor: '100%'
					    			},
                            items: [
                            	{
								xtype: 'textfield',
                                fieldLabel: 'Title',
                                name: 'templateNameFilter',
                                itemId: 'templateNameFilter',
                                maxLength: 50,
                                allowBlank:true,
                                labelWidth:80,
								width: 290,
								enableKeyEvents:true,
								listeners:{
									keyup: function(textField, e, eOpts) {
										var me = this;
				                        var searchString = textField.getValue().trim();
				                        var templatesGrid = me.findParentByType('loadtemplates').query('#allPlansTemplateGridPanel')[0];
										templatesGrid.getStore().filterBy(getFilterRecord(['name'], searchString)); 
				                    }
								}
                            }]}
                            
            
            ]}]
         }]
		},	{
         xtype: 'gridpanel',
         title: '',
         id: 'allPlansTemplateGridPanel',
         width: 555,
         height: '100%',
         border: true,
         autoScroll: true,
         columnLines: true,
         store: me.store,
         columns: [
         {
             text: 'Type',
             width: 45,
             dataIndex: 'visibility',
             sortable: true,
				renderer:me.columnRendererUtils.renderTemplateVisibility
         },	{
                 text: 'Status',
                 width: 55,
                 dataIndex: 'objectStatus',
                 sortable: true
          },{
             text: 'Template Title',
             width: 200,
             dataIndex: 'name',
             sortable: true
         }, {
             text: 'Date/ Time',
             width: 125,
             dataIndex: 'modifiedDate',
             sortable: true,
             renderer: Ext.util.Format.dateRenderer('Y-m-d g:i A')
             
         }, {
             text: 'Advisor',
             width: 120,
             sortable: true,
             dataIndex: 'ownerName'
             
         }
         ]}]
        });
        
        return me.callParent(arguments);
    }   
});