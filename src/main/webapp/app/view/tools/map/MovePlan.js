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
Ext.define('Ssp.view.tools.map.MovePlan', {
    extend: 'Ext.form.Panel',
    alias: 'widget.moveplan',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.map.MapPlanToolViewController',
    inject: {
        columnRendererUtils: 'columnRendererUtils',
        termsStore: 'termsStore',
        authenticatedPerson: 'authenticatedPerson',
    	currentMapPlan: 'currentMapPlan'        	
    },
    width: '100%',
    height: '38',
	style: 'background-color: lightgrey;',
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            fieldLabel: '',
            layout: 'hbox',
            margin: '0 0 0 0',
			padding: '0 0 0 0',
            defaultType: 'displayfield',
            bodyStyle: {"background-color":"lightgrey"},  
            fieldDefaults: {
                msgTarget: 'side'
            },
            items: [

                 {
                            fieldLabel: me.currentMapPlan.get("isTemplate") == true ? 'Template Title':'Plan Title',
        					xtype:'displayfield',
                            itemId: 'name',
                            name: 'name',
        					frame: false,
        				    fieldStyle: 'text-align: left',
        					readOnly: true,
        					editable:false
                        }, 	
                        {
                            xtype: 'tbspacer',
                            flex: .5
                        },                        
        				{
        								 xtype: 'button',
        								 width: 20,
        								itemId:'onPlanStatusDetails',
        				    	         cls: 'helpIconSmall',
        				    	         tooltip: 'Student is currently on plan.'
        				    	     },{
                            		fieldLabel: 'Student is Currently',
        		                    itemId: 'onPlan',
        		                    name: 'onPlan',
        							fieldStyle:"text-align:left"
        				   },
                           {
                               xtype: 'tbspacer',
                               flex: .5
                           },         				   
        		{
                    fieldLabel: 'Plan Hrs',
                    itemId: 'currentTotalPlanCrHrs',
                    name: 'currentTotalPlanCrHrs',
                    id: 'currentTotalPlanCrHrs',
				    fieldStyle: 'text-align: left'
                
                },                        {
                    xtype: 'tbspacer',
                    flex: .5
                },  {
                    fieldLabel: 'Dev Hrs',
                    itemId: 'currentPlanTotalDevCrHrs',
                    name: 'currentPlanTotalDevCrHrs',
					id:'currentPlanTotalDevCrHrs',
				    fieldStyle: 'text-align: left'
                
                },	{
		                    xtype: 'tbspacer',
		                    flex: .05
		                }]
        
        });
        
        return me.callParent(arguments);
    }
    
});
