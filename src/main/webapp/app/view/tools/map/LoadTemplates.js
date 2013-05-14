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
        programsStore: 'programsStore',
        departmentsStore: 'departmentsStore',
        divisionsStore: 'divisionsStore',
        store: 'planTemplatesSummaryStore',
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
            title: 'Load Template',
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
                bodyPadding: 10,
                autoScroll: true,
                itemId: 'faForm',
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
                        xtype : 'label',
                        text: 'Filter By:'
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
                                xtype: 'combobox',
                                itemId: 'program',
                                store: me.programsStore,
                                fieldLabel: '',
                                emptyText: 'Specific Program',
                                valueField: 'code',
                                displayField: 'name',
                                mode: 'local',
                                typeAhead: true,
                                allowBlank: true,
                                width: 250
                            },
                            {
                                    xtype: 'tbspacer',
                                    width: 30
                            },
                            {
                                xtype: 'combobox',
                                itemId: 'department',
                                store: me.departmentsStore,
                                fieldLabel: '',
                                emptyText: 'Specific Division',
                                valueField: 'code',
                                displayField: 'name',
                                mode: 'local',
                                typeAhead: true,
                                allowBlank: true,
                                width: 250
                            },
                            {
                                xtype: 'combobox',
                                itemId: 'division',
                                store: me.divisionsStore,
                                fieldLabel: '',
                                emptyText: 'Specific Division',
                                valueField: 'code',
                                displayField: 'name',
                                mode: 'local',
                                typeAhead: true,
                                allowBlank: true,
                                width: 250
                            }    
                       ]
                },
                {
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'radio',
                    layout: 'vbox',
                    align: 'stretch',
                    padding: '0 0 0 20',
                    margin: '0 0 0 0',
                    items: [        
                     
                            {
                                 inputValue: 'All',
                                    boxLabel: 'All',
                                    labelWidth: 50
                                },
                                {
                                    inputValue: 'Private',
                                    boxLabel: 'Private',
                                    labelWidth: 50
                                },
                                {
                                    inputValue: 'Public',
                                    boxLabel: 'Public',
                                    labelWidth: 50
                                    }
                            ]
                          }
                        
                        ]
                    },
                    {
                            xtype: 'container',
                            defaultType: 'textfield',
                            padding: '0 0 0 0',
                            layout: {
                                align: 'stretch',
                                type: 'vbox'
                            },
                            items: [
                            {
                                fieldLabel: 'Template Name',
                                name: 'templateName',
                                itemId: 'templateName',
                                maxLength: 50,
                                allowBlank:true,
                                labelWidth:100
                            }]},
                            {
                            xtype: 'gridpanel',
                            title: '',
                            id: 'allPlansTemplateGridPanel',
                            width: '100%',
                            height: '100%',
                            border: true,
                            autoScroll: true,
                            columnLines: true,
                            store: me.store,
                            columns: [
                            {
                                text: 'Type',
                                width: '75',
                                dataIndex: 'type',
                                sortable: true
                            },{
                                text: 'Template Title',
                                width: '400',
                                dataIndex: 'name',
                                sortable: true
                            }, {
                                text: 'Date/ Time',
                                width: '125',
                                dataIndex: 'modifiedDate',
                                sortable: true,
                                renderer: Ext.util.Format.dateRenderer('Y-m-d g:i A')
                                
                            }, {
                                text: 'Advisor',
                                width: '100',
                                sortable: true,
                                dataIndex: 'ownerName'
                                
                            },
							{
                                text: 'dpartment',
                                width: '100',
                                sortable: true,
                                dataIndex: 'departmentCode',
								hidden: true,
								hideable:false
                                
                            },
							{
                                text: 'progrram',
                                width: '100',
                                sortable: true,
                                dataIndex: 'programCode',
								hidden: true,
								hideable:false
                                
                            },
							{
                                text: 'division',
                                width: '100',
                                sortable: true,
                                dataIndex: 'divisionCode',
								hidden: true,
								hideable:false
                                
                            }
                            ]}
            
            ],
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
            
            }]
            
            }]
        });
        
        return me.callParent(arguments);
    }
                            
    
});