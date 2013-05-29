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
Ext.define('Ssp.view.tools.map.LoadPlans', {
    extend: 'Ext.window.Window',
    alias: 'widget.loadplans',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.map.LoadPlanViewController',
    inject: {
        columnRendererUtils: 'columnRendererUtils',
        sspConfig: 'sspConfig'
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
            title: 'Load Plan',
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
                itemId: 'planForm',
                items: [{
                xtype: 'fieldcontainer',
                fieldLabel: '',
                layout: 'vbox',
                align: 'stretch',
                padding: '2 2 2 2',
                defaultType: 'displayfield',
                fieldDefaults: {
                    msgTarget: 'side'
                },
                items: [ 
                       {
                        xtype: 'label',
                        padding: '2 0 0 5',
                        text: 'Select plan then click open to load a plan.',
                        style: 'font-weight: bold',
                        } ,
                        {
                            xtype: 'fieldset',
                            border: 0,
                            title: '',
                            margin: '0 0 0 2',
                            padding: '0 0 0 5',
                            layout: 'hbox',
                            align: 'stretch',
                            items: [
                                {
                                    xtype: 'label',
                                    padding: '2 15 0 0',
                                    text: 'Blue background: Active Plan',
									style: 'background-color:#C5D7F1;'
                                },
                                {
                                    xtype: 'label',
                                    padding: '2 0 0 5',
                                    text: 'White background: Saved Plan'
                                },
                                ]
                        },
                        {
                            xtype: 'fieldset',
                            border: 0,
                            title: '',
                            margin: '0 0 0 2',
                            padding: '0 0 0 5',
                            layout: 'vbox',
                            align: 'stretch',
                            items: [
                            {
                            xtype: 'gridpanel',
                            title: 'Load Plan',
                            width: '100%',
                            height: '100%',
                            border: true,
                            autoScroll: true,
                            columnLines: true,
                            columns: [{
                                text: 'Important',
                                dataIndex: 'name',
                                sortable: true,
								width: 70,
								tooltip:'Yes indicates plan as important.',
								renderer: me.columnRendererUtils.renderImportant
                            },
							{
                                text: 'Plan Title',
                                dataIndex: 'name',
                                sortable: true,
								width: 350,
								renderer: me.columnRendererUtils.renderBackgroundColorActive
                            }, {
                                text: 'Date/ Time',
                                dataIndex: 'modifiedDate',
                                sortable: true,
								width: 100,
                                renderer: me.columnRendererUtils.renderDateBackgroundColorActive
                                
                            }, {
                                text: 'Advisor',
                                sortable: true,
								width: 160,
                                dataIndex: 'ownerName',
								renderer: me.columnRendererUtils.renderBackgroundColorActive
                            }, 
                            ]
                        }
                        ]}
                    ]
            }],
               
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