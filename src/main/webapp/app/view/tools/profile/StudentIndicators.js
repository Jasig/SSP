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
Ext.define('Ssp.view.tools.profile.StudentIndicators', {
    extend: 'Ext.Container',
    alias: 'widget.profilestudentindicators',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    // controller: 'Ssp.controller.tool.profile.ProfileStudentIndicatorsViewController',
    // inject: {
    //     columnRendererUtils: 'columnRendererUtils',
    //     person: 'currentPerson',
    //     textStore: 'sspTextStore'
    // },
    width: '100%',
    height: '100%',
    
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            layout: 'column',
			margin: 0,
			defaults: {
				columnWidth: 0.5,
				layout: {
					type: 'anchor',
					flex: 1
				},
                defaults: {
                    anchor: '100%'
                },
                flex: 1,
                border: 0,
                style: {
                    borderStyle: 'solid'
                }
			},
            items: [{
                xtype: 'container',
                items: [{
                    xtype: 'dashboardindicator',
					itemId: 'gpaIndicator',
                    data: {
						indicatorName: 'GPA'
					}
                }, {
                    xtype: 'dashboardindicator',
					itemId: 'compRateIndicator',
                    data: {
						indicatorName: 'Comp Rate'
					}
                }, {
                    xtype: 'dashboardindicator',
					itemId: 'sapIndicator',
                    data: {
						indicatorName: 'SAP'
					}
                }]
            }, {
                xtype: 'container',
                items: [{
                    xtype: 'dashboardindicator',
					itemId: 'regIndicator',
                    data: {
						indicatorName: 'REG'
					}
                }, {
                    xtype: 'dashboardindicator',
					itemId: 'academicStandingIndicator',
                    data: {
						indicatorName: 'Standing'
					}
                }, {
                    xtype: 'dashboardindicator',
					itemId: 'currentRestrictionsIndicator',
                    data: {
						indicatorName: 'Restrictions'
					}
                }]
            }]
        });
        
        return me.callParent(arguments);
    }
    
});
