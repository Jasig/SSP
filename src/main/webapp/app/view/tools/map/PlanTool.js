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
		currentMapPlan: 'currentMapPlan'
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
                padding: '0 0 0 15',
                title: '',
                defaultType: 'displayfield',
                layout: 'vbox',
                width: 80,
                cls: 'center-align',
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
                    padding: '0 0 0 0'
                }, {
                    xtype: 'label',
                    text: 'View All'
                
                }]
            
            }, {
                xtype: 'fieldset',
                border: 0,
                padding: '0 0 0 0',
                title: '',
                defaultType: 'displayfield',
                layout: 'vbox',
                width: 95,
                cls: 'center-align',
                
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
                    padding: '0 0 0 0'
                }, {
                    xtype: 'label',
					itemId: 'notesLabel',
					width: 125,
                    text: me.currentMapPlan.get("isTemplate") == true ? 'Template Notes':'Plan Notes'
                
                }]
            
            }, 	{
		                xtype: 'fieldset',
		                border: 0,
		                padding: '0 0 0 0',
		                title: '',
		                defaultType: 'displayfield',
		                layout: 'vbox',
		                width: 100,
		                cls: 'center-align',
		                defaults: {
		                    anchor: '100%'
		                },
		                items: [{
		                    tooltip:  me.currentMapPlan.get("isTemplate") == true ? 'Move Template': 'Move Plan',
		                    width: 30,
		                    height: 30,
		                    cls: 'mapMovePlanIcon',
		                    xtype: 'button',							
		                    itemId: 'movePlanButton',
			                hidden: !me.authenticatedPerson.hasAccess('MAP_TOOL_PRINT_BUTTON'),
		                    align: 'center',
		                    padding: '0 0 0 0'							
		                }, {
		                    xtype: 'label',
							itemId: 'movePlanLabel',								
		                    text: me.currentMapPlan.get("isTemplate") == true ? 'Move Template': 'Move Plan'							
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
                    hidden:	!me.authenticatedPerson.hasAccess('MAP_TOOL_EMAIL_BUTTON') || me.currentMapPlan.get("isTemplate") == true,
                    align: 'center',
                    padding: '0 0 0 0'
                }, {
                    xtype: 'label',
					itemId: 'emailLabel',
                    text: 'Email Plan'
                
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
	                hidden: !me.authenticatedPerson.hasAccess('MAP_TOOL_PRINT_BUTTON') || me.currentMapPlan.get("isTemplate") == true,
                    align: 'center',
                    padding: '0 0 0 0'
                }, {
                    xtype: 'label',
					itemId: 'printLabel',
                    text: 'Print Plan'
                
                }]
            
            },	{
	                xtype: 'fieldset',
	                border: 0,
	                padding: '0 0 0 0',
	                title: '',
	                defaultType: 'displayfield',
	                layout: 'vbox',
					itemId: 'planFAFieldSet',
	                width: 80,
	                cls: 'center-align',
	                hidden:  me.currentMapPlan.get("isTemplate") == true,
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
	                    hidden:  me.currentMapPlan.get("isTemplate") == true,
	                    padding: '0 0 0 0'
	                }, {
	                    xtype: 'label',
	                    hidden:  me.currentMapPlan.get("isTemplate") == true,
	                    text: 'Financial Aid'
	                }]
	            
	            },{
			                xtype: 'fieldset',
			                border: 0,
			                padding: '0 0 0 0',
			                title: '',
			                defaultType: 'displayfield',
			                layout: 'vbox',
			                width: 80,
							itemId: 'planTranscriptFieldSet',
			                cls: 'center-align',
							hidden:  me.currentMapPlan.get("isTemplate") == true,
			                defaults: {
			                    anchor: '100%'
			                },
			                items: [{
			                    tooltip:  'View of Student\'s Transcript',
			                    width: 30,
			                    height: 30,
			                    cls: 'transcriptIcon',
			                    xtype: 'button',
			                    itemId: 'showStudentTranscript',
			                    align: 'center',
			                    hidden:  me.currentMapPlan.get("isTemplate") == true,
			                    padding: '0 0 0 0'
			                    
			                }, {
			                    xtype: 'label',
								itemId: 'showStudentTranscriptLabel',
								width: 125,
								hidden:  me.currentMapPlan.get("isTemplate") == true,
			                    text: 'Transcript'

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
                    fieldLabel: me.currentMapPlan.get("isTemplate") == true ? 'Template Title':'Plan Title',
					xtype:'textareafield',
                    itemId: 'name',
                    name: 'name',
                    labelWidth: 65,
					width:200,
					height:40,
					style: 'border: none;',
					frame: false,
					readOnly: true,
					fieldStyle:"border:none 0px black; margin-top:2px; background-image:none",
					editable:false
                }, 	{
		                xtype: 'fieldset',
		                border: 0,
		                padding: '0 0 0 0',
		                title: '',
						itemId:'onPlanFieldSet',
		                defaultType: 'displayfield',
		                layout: 'hbox',
		                defaults: {
		                    anchor: '100%'
		                },
		                items: [
							{
								 xtype: 'button',
								 width: 20,
				                 height: 20,
								itemId:'onPlanStatusDetails',
				    	         cls: 'helpIconSmall',
				    	         tooltip: 'Student is currently on plan.'
				    	     },{
                    		fieldLabel: 'Student is Currently',
		                    itemId: 'onPlan',
		                    name: 'onPlan',
		                    labelWidth: 115,
							width:200,
							fieldStyle:"text-align:left",
		                    hidden: me.currentMapPlan.get("isTemplate")

		                }]
				}]
            
            }]
        });
        
        return me.callParent(arguments);
    }
    
});