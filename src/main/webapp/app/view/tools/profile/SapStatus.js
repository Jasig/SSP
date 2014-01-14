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
Ext.define('Ssp.view.tools.profile.SapStatus', {
    extend: 'Ext.window.Window',
    alias: 'widget.sapstatusview',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.profile.SapStatusController',
    inject: {
        columnRendererUtils: 'columnRendererUtils'
    },
    height: 200,
    width: 300,
    style : 'z-index: -1;',  
    resizable: true,
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            layout: {
                align: 'stretch',
                type: 'vbox'
            },
            title: 'Sap Status Code',
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
                itemId: 'sapStatusForm',
                items: [{
                xtype: 'fieldcontainer',
                fieldLabel: '',
                layout: 'vbox',
                align: 'stretch',
                defaultType: 'displayfield',
                fieldDefaults: {
                    msgTarget: 'side'
                },
                items: [ {
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    margin: '0 0 0 2',
                    padding: '0 0 0 5',
                    layout: 'vbox',
                    align: 'stretch',
                    
                    items: [
                    {
                        xtype: 'tbspacer'
                    }, {
                        fieldLabel: 'Code',
                        name: 'code',
                        itemId: 'code',
                        labelWidth: 80,
                        value:'........'
                    }, {
                        fieldLabel: 'Name',
                        name: 'name',
                        itemId: 'name',
                        labelWidth: 80,
                        value:'........'
                    }, {
                        xtype: 'tbspacer',
                        height: '10'
                    },
                    {
                        fieldLabel: 'Description',
                        name: 'description',
                        itemId: 'description',
                        labelWidth: 80,
                        value:'........'
                    }, 
                    {
                        xtype: 'tbspacer',
                        height: '10'
                    }
                    ]
				}]
			}]
            }]
        });
        
        return me.callParent(arguments);
    }
    
});
