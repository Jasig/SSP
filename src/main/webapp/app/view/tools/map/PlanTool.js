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
Ext.define('Ssp.view.tools.map.PlanTool', {
    extend: 'Ext.form.Panel',
    alias: 'widget.plantool',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.map.MapPlanToolViewController',
    inject: {
        columnRendererUtils: 'columnRendererUtils',
        authenticatedPerson: 'authenticatedPerson',
    },
    width: '100%',
    height: '100%',
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            fieldLabel: '',
            layout: 'hbox',
            margin: '0 0 0 0',
            padding: ' 0 0 0 0',
            height: '40',
            defaultType: 'displayfield',
            fieldDefaults: {
                msgTarget: 'side'
            },
            
            items: [{
                xtype: 'tbspacer',
                flex: .02
            }, 
            {
                xtype: 'fieldset',
                border: 0,
                padding: '0 0 0 0',
                title: '',
                defaultType: 'displayfield',
                layout: 'vbox',
                width: 80,
                cls: 'center-align',
				hidden: true,
				hideable: false,
                defaults: {
                    anchor: '100%'
                },
                
                items: [{
                    tooltip: 'View all',
                    width: 30,
                    height: 30,
                    cls: 'overviewIcon',
                    xtype: 'button',
                    itemId: 'planOverviewButton',
                    align: 'center',
                    padding: '0 0 0 0',
                	hidden: true,
					hideable: false
                }, {
                    xtype: 'label',
                    text: 'View All',
                	hidden: true,
					hideable: false
                
                }]
            
            }, 
             {
                xtype: 'fieldset',
                border: 0,
                padding: '0 0 0 0',
                title: '',
                defaultType: 'displayfield',
                layout: 'vbox',
                width: 80,
                cls: 'center-align',
				hidden: true,
				hideable: false,
                defaults: {
                    anchor: '100%'
                },
                
                items: [{
                    tooltip: 'Financial Aid',
                    width: 30,
                    height: 30,
                    cls: 'mapFAIcon',
                    xtype: 'button',
                    itemId: 'planFAButton',
                    align: 'center',
                    padding: '0 0 0 0',
					hidden: true,
					hideable: false
                }, {
                    xtype: 'label',
                    text: 'Financial Aid',
                	hidden: true,
					hideable: false
                }]
            
            }, 
            {
                xtype: 'fieldset',
                border: 0,
                padding: '0 0 0 0',
                title: '',
                defaultType: 'displayfield',
                layout: 'vbox',
                width: 80,
                cls: 'center-align',
				hidden: true,
				hideable: false,
                
                defaults: {
                    anchor: '100%'
                },
                
                items: [{
                    tooltip: 'Plan Notes',
                    width: 30,
                    height: 30,
                    cls: 'mapNotesIcon',
                    xtype: 'button',
                    itemId: 'planNotesButton',
                    align: 'center',
                    padding: '0 0 0 0',
					hidden: true,
					hideable: false
                }, {
                    xtype: 'label',
                    text: 'Plan Notes',
                	hidden: true,
					hideable: false
                
                }]
            
            }, 
            {
				hidden: true,
				hideable: false,
                xtype: 'tbspacer',
                flex: .10
            },
            {
                xtype: 'fieldset',
                border: 0,
                padding: '0 0 0 0',
                title: '',
                defaultType: 'displayfield',
                layout: 'vbox',
                width: 80,
                cls: 'center-align',
				hidden: true,
				hideable: false,
                defaults: {
                    anchor: '100%'
                },
                
                items: [{
                    tooltip: 'Email Plan',
                    width: 30,
                    height: 30,
                    cls: 'planEmailIcon',
                    xtype: 'button',
                    itemId: 'emailPlanButton',
                    align: 'center',
                    padding: '0 0 0 0',
					hidden: true,
					hideable: false
                }, {
                    xtype: 'label',
                    text: 'Email Plan',
                	hidden: true,
					hideable: false
                
                }]
            
            }, {
                xtype: 'fieldset',
                border: 0,
                padding: '0 0 0 0',
                title: '',
                defaultType: 'displayfield',
                layout: 'vbox',
                width: 80,
                cls: 'center-align',
                defaults: {
                    anchor: '100%'
                },
                
                items: [{
                    tooltip: 'Print Plan',
                    width: 30,
                    height: 30,
                    cls: 'mapPrintIcon',
                    xtype: 'button',
                    itemId: 'printPlanButton',
	                hidden: !me.authenticatedPerson.hasAccess('MAP_TOOL_PRINT_BUTTON'),
                    align: 'center',
                    padding: '0 0 0 0'
                }, {
                    xtype: 'label',
                    text: 'Print Plan'
                
                }]
            
            },{
                xtype: 'tbspacer',
                flex: 1
            }, {
                xtype: 'fieldset',
                border: 0,
                padding: '0 0 0 0',
                title: '',
                defaultType: 'displayfield',
                layout: 'vbox',
                defaults: {
                    anchor: '100%'
                },
                items: [{
                    fieldLabel: 'Plan Title',
					xtype:'textareafield',
                    itemId: 'name',
                    name: 'name',
                    labelWidth: 55,
					width:200,
					height:40,
					style: 'border: none;',
					frame: false,
					readOnly: true,
					fieldStyle:"border:none 0px black; margin-top:2px; background-image:none",
					editable:false,
                }, {
                    fieldLabel: 'Student is Currently',
                    itemId: 'onPlan',
                    name: 'onPlan',
                    labelWidth: 150,
					hidden: true,
					hideable: false
                }]
            
            }, {}]
        
        });
        
        return me.callParent(arguments);
    }
    
});
