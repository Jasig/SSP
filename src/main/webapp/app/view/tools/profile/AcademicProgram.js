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
Ext.define('Ssp.view.tools.profile.AcademicProgram', {
    extend: 'Ext.form.Panel',
    alias: 'widget.profileacademicprogram',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    inject: {
        columnRendererUtils: 'columnRendererUtils',
        sspConfig: 'sspConfig'
    },
    controller: 'Ssp.controller.tool.profile.AcademicProgramViewController',
    width: '100%',
    height: '100%',
	autoScroll: true,
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            border: 1,
            bodyPadding: 0,
            margin: '0 0 7 0',
            layout: 'anchor',
            defaults: {
                anchor: '100%'
            },
            items: [{
                xtype: 'fieldcontainer',
                fieldLabel: '',
                //layout: 'hbox',
                margin: '0 0 0 2',
                defaultType: 'displayfield',
                fieldDefaults: {
                    msgTarget: 'side'
                },
                
                items: [{
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    layout: 'hbox',
					height: 35,
					margin: '0 0 0 0',
                    padding: '0 0 0 0',
                    defaults: {
                        anchor: '100%'
                    },
                    //flex: .40,
                    items: [
					{
                        xtype: 'tbspacer',
                       flex: .50
                    },
					/*{
                        tooltip: 'Email MAP',
                        text: '',
                        width: 30,
                        height: 30,
                        cls: 'mapEmailIcon',
                        xtype: 'button',
                        itemId: 'emailMAPButton'
                    }, */
					{
                        tooltip: 'Print MAP',
                        text: '',
                        width: 30,
                        height: 30,
                        cls: 'mapPrintIcon',
                        xtype: 'button',
                        itemId: 'printPlanButton'
                    },
					{
                        xtype: 'tbspacer',
                       flex: .05
                    },]
                }, {
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    defaults: {
                        anchor: '100%'
                    },
					margin: '0 0 0 0',
					padding: '0 0 5 0',
                    //flex: .40,
                    items: [{
                        fieldLabel: 'Academic Program',
                        name: 'academicPrograms',
                        itemId: 'academicPrograms',
                        //labelWidth: 120
                    }, {
                        fieldLabel: 'MAP',
                        itemId: 'onPlan',
                        name: 'onPlan',
                        labelWidth: 26
                    }, {
                        fieldLabel: 'Name',
                        name: 'mapName',
                        itemId: 'mapName',
                        labelWidth: 35
                    }, {
                        fieldLabel: 'Advisor',
                        name: 'advisor',
                        itemId: 'advisor',
                        labelWidth: 42
                    }, {
                        fieldLabel: 'Last Updated',
                        name: 'mapLastUpdated',
                        itemId: 'mapLastUpdated',
						labelWidth: 60
                    }, {
                        fieldLabel: 'MAP Projected Grad',
                        name: 'mapProjected',
                        itemId: 'mapProjected',
                        labelWidth: 110
                    }]
                
                }]
            }]
        });
        
        return me.callParent(arguments);
    }
    
});
