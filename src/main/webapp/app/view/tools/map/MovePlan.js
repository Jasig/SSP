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
    extend: 'Ext.form.FieldContainer',
    alias: 'widget.moveplan',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    //controller: 'Ssp.controller.tool.profile.ProfilePersonViewController',
    inject: {
        columnRendererUtils: 'columnRendererUtils'
        //sspConfig: 'sspConfig'
    },
    width: '100%',
    height: '100%',
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            fieldLabel: '',
            layout: 'hbox',
            margin: '0 0 0 0',
			padding: ' 2 0 0 0',
            height: '38',
            defaultType: 'displayfield',
			style: 'background-color: lightgrey;',
            fieldDefaults: {
                msgTarget: 'side'
            },
            
            items: [
			
			{
                
                    tooltip: 'Move Plan Backward',
                    width: 30,
                    height: 30,
                    cls: 'planMoveBackwardIcon',
                    xtype: 'button',
                    itemId: 'movePlanBackwardButton'
                }, {
                    tooltip: 'Move Plan Forward',
                    width: 30,
                    height: 30,
                    cls: 'planMoveForwardIcon',
                    xtype: 'button',
                    itemId: 'movePlanForwardButton'
                }, {
                    xtype: 'tbspacer',
                    flex: 1
                }, {
                    fieldLabel: 'Current Total Plan Cr Hrs',
                    itemId: 'planHrs',
                    name: 'planHrs',
                    id: 'currentTotalPlanCrHrs',
                    labelWidth: 150,
                    width: 180
                
                }, {
                    fieldLabel: 'Dev Cr Hrs',
                    itemId: 'currentPlanTotalDevCrHrs',
                    name: 'currentPlanTotalDevCrHrs',
					id:'currentPlanTotalDevCrHrs',
                    labelWidth: 100,
                    width: 130
                
                }
]
        
        });
        
        return me.callParent(arguments);
    }
    
});
