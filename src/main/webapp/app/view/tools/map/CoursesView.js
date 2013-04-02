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
Ext.define('Ssp.view.tools.map.CoursesView', {
    extend: 'Ext.form.Panel',
    alias: 'widget.coursesview',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    //controller: 'Ssp.controller.tool.profile.ProfileToolViewController',
    
    width: '100%',
    height: '100%',
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            layout: {
                type: 'fit'
            },
            padding: 0,
            preventHeader: true,
            items: [{
                xtype: 'fieldcontainer',
                fieldLabel: '',
                layout: 'vbox',
                margin: '0 0 0 0',
                padding: '0 0 0 0',
                width: '100%',
                height: '100%',
                items: [
                {
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    margin: '0 0 0 0',
                    padding: '5 0 5 5',
                    layout: 'hbox',
                    defaults: {
                        anchor: '100%'
                    },
                    items: [{
                        xtype: 'combobox',
                        name: 'coursesCombo',
                        fieldLabel: '',
                        emptyText: 'Select From - All Courses',
                        valueField: 'courseType',
                        displayField: 'courseType',
                        mode: 'local',
                        queryMode: 'local',
                        allowBlank: true,
                        itemId: 'coursesTypeCombo',
                        width: 285
                    }]
                
                    },
                    {
                        xtype: 'container',
                        autoEl: 'hr'
                    },
                    {
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    margin: '0 0 0 0',
                    padding: '5 0 5 5',
                    layout: 'hbox',
                    defaults: {
                        anchor: '100%'
                    },
                    items: [{
                        xtype: 'combobox',
                        name: 'programCombo',
                        fieldLabel: '',
                        emptyText: 'Filter by Program',
                        valueField: 'code',
                        displayField: 'longName',
                        mode: 'local',
                        queryMode: 'local',
                        allowBlank: true,
                        itemId: 'programCombo',
                        width: 260
                    }, {
                        tooltip: 'Reset to All Programs',
                        text: '',
                        width: 30,
                        height: 25,
                        cls: 'mapClearSearchIcon',
                        xtype: 'button',
                        itemId: 'cancelProgramSearchButton'
                    }]
                
                }, {
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    margin: '0 0 0 0',
					padding: '0 0 5 5',
                    layout: 'hbox',
                    defaults: {
                        anchor: '100%'
                    },
                    
                    items: [{
                        xtype: 'combobox',
                        name: 'transferCombo',
                        fieldLabel: '',
                        emptyText: 'Filter by Transfer',
                        valueField: 'code',
                        displayField: 'transfer',
                        mode: 'local',
                        typeAhead: true,
                        queryMode: 'local',
                        allowBlank: true,
                        itemId: 'transferCombo',
                        width: 260
                    }, {
                        tooltip: 'Reset to All Transfer Types',
                        text: '',
                        width: 30,
                        height: 25,
                        cls: 'mapClearSearchIcon',
                        xtype: 'button',
                        itemId: 'cancelTransferSearchButton'
                    }]
                
                }, {
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'textfield',
                    margin: '0 0 0 0',
					padding: '0 0 5 5',
                    layout: 'hbox',
                    defaults: {
                        anchor: '100%'
                    },
                    
                    items: [
					{
						 xtype: 'tbspacer',
                       width: 75
					},
					
					{
                       fieldLabel: 'Filter By',
                        name: 'filterBy',
                        itemId: 'filterBy',
                        maxLength: 50,
                        allowBlank:true,
						labelWidth:50
                    }, {
                        tooltip: 'Clear Filter',
                        text: '',
                        width: 30,
                        height: 25,
                        cls: 'mapClearSearchIcon',
                        xtype: 'button',
                        itemId: 'cancelFilterButton'
                    }]
                
                },
                {
                    xtype : 'container',
                    flex: 1,
                    width: '100%',
                    height: '100%',
                    layout: 'card',
                    //autoScroll: true,
                    items : [
                        {xtype:'coursesgrid', itemId:'allcoursesgrid', flex:1},
                        {xtype:'coursesgrid', itemId:'electivesgrid', flex:1},
                        {xtype:'coursesgrid', itemId:'defined1grid', flex:1},
                        {xtype:'coursesgrid', itemId:'defined2grid', flex:1},
                        {xtype:'coursesgrid', itemId:'defined3grid', flex:1},
                        {xtype:'coursesgrid', itemId:'holdcoursesgrid', flex:1}
                        
                    ]
                }
            
                ]
            }
            
            
            ]
        
        });
        
        return me.callParent(arguments);
    }
    
});
