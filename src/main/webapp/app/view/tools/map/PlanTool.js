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
    inject: {
        columnRendererUtils: 'columnRendererUtils',
        authenticatedPerson: 'authenticatedPerson',
		currentMapPlan: 'currentMapPlan'
    },
    width: '100%',
    height: '150',
    margin: '10 0 10 0',
    padding: '0 0 0 0', 
    border: 0,
    msgTarget: 'side',
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
			layout: {
                align: 'stretch',
                type: 'hbox'
            },        	
        	items: [   
			{
			    xtype: 'tbspacer',
			    flex: .02
			},         	        
           {
                    tooltip: 'View all',
                    height: 30,
                    flex:.10,
                    cls: 'overviewIcon',
                    xtype: 'button',
                    itemId: 'planOverviewButton',
                    align: 'center',
                    hideMode: 'offsets',
                    padding: '0 0 0 0'
            },
           {
                    tooltip: 'Plan Notes',
                    height: 30,
                    flex:.10,
                    cls: 'mapNotesIcon',
                    xtype: 'button',
                    itemId: 'planNotesButton',
                    hideMode: 'offsets',
                    align: 'center',
                    padding: '0 0 0 0'
                },
             	{
		                    tooltip:  me.currentMapPlan.get("isTemplate") == true ? 'Move Template': 'Move Plan',
		                    height: 30,
		                    flex:.10,
		                    cls: 'mapMovePlanIcon',
		                    xtype: 'button',							
		                    itemId: 'movePlanButton',
			                hidden: !me.authenticatedPerson.hasAccess('MAP_TOOL_PRINT_BUTTON'),
		                    align: 'center',
                    		hideMode: 'offsets',
		                    padding: '0 0 0 0'							
		           }, 
           {
                    tooltip: 'Email Plan',
                    height: 30,
                    flex:.10,
                    cls: 'planEmailIcon',
                    xtype: 'button',
                    itemId: 'emailPlanButton',
                    hidden:	!me.authenticatedPerson.hasAccess('MAP_TOOL_EMAIL_BUTTON') || me.currentMapPlan.get("isTemplate") == true,
                    align: 'center',
                    hideMode: 'offsets',
                    padding: '0 0 0 0'
                },
                {
                    tooltip: 'Print Plan',
                    height: 30,
                    flex:.10,
                    cls: 'mapPrintIcon',
                    xtype: 'button',
                    itemId: 'printPlanButton',
                    hideMode: 'offsets',
	                hidden: !me.authenticatedPerson.hasAccess('MAP_TOOL_PRINT_BUTTON') || me.currentMapPlan.get("isTemplate") == true,
                    align: 'center',
                    padding: '0 0 0 0'
                }, 
            	{
	                    tooltip: 'Financial Aid',
	                    height: 30,
	                    flex:.10,
	                    cls: 'mapFAIcon',
	                    xtype: 'button',
	                    itemId: 'planFAButton',
	                    align: 'center',
                  	    hideMode: 'offsets',
	                    hidden:  me.currentMapPlan.get("isTemplate") == true,
	                    padding: '0 0 0 0'
	                },
			        {
			                    tooltip:  'View of Student\'s Course History',
			                    height: 30,
			                    flex:.10,
			                    cls: 'transcriptIcon',
			                    xtype: 'button',
			                    itemId: 'showStudentTranscript',
			                    align: 'center',
                  	   		    hideMode: 'offsets',
			                    hidden:  me.currentMapPlan.get("isTemplate") == true,
			                    padding: '0 0 0 0'
			          },
			           {
			                    tooltip:  'View of Student\'s Map Status Detail',
			                    height: 30,
			                    flex:.10,
			                    cls: 'studentHistoryIcon',
			                    xtype: 'button',
			                    itemId: 'showMapStatus',
			                    align: 'center',
                  			    hideMode: 'offsets',
			                    hidden:  me.currentMapPlan.get("isTemplate") == true,
			                    padding: '0 0 0 0'
			                    
			                }, 
						{
						    xtype: 'tbspacer',
						    flex: .3
						}, 			          
			          ]

        });
        
        return me.callParent(arguments);
    }
    
});