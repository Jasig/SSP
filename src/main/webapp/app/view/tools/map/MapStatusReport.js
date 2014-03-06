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
Ext.define('Ssp.view.tools.map.MapStatusReport', {
    extend: 'Ext.window.Window',
    alias: 'widget.mapstatusreport',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.map.MapStatusReportController',
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
		columnRendererUtils : 'columnRendererUtils',
    	currentMapPlan: 'currentMapPlan',
    	personLite: 'personLite',
    	mapStatusReportStore: 'mapStatusReportStore',
    	mapStatusReportCourseDetailsStore: 'mapStatusReportCourseDetailsStore',
    	mapStatusReportTermDetailsStore: 'mapStatusReportTermDetailsStore',
    	mapStatusReportSubstitutionDetailsStore: 'mapStatusReportSubstitutionDetailsStore',
		person: 'currentPerson'
    },
	height:800,
	width:900,   
    overflowY: 'auto',
    style : 'z-index: -1;',  
    layout: {
		type : 'vbox',
        align: 'stretch'
            },
    initComponent: function() {
    	var me=this;
        Ext.apply(me,
		{
			title: 'Map Status Report: '+me.personLite.get('displayFullName'),
            dockedItems: [{
                dock: 'top',
                xtype: 'toolbar',
				height: '25',
                items: [{
                    tooltip: 'Close',
                    text: 'Close',
                    height: 22,
                    xtype: 'button',
                    itemId: 'closeButton'
                }
                ]
            }],
			items: [
				{
				    xtype: 'form',
				    defaultType: 'displayfield',
				    id: 'statusForm',
				    margin: '0 0 2 2',
				    bodyStyle: 'background:none',
				    height: '370',
				    items: [{
				        fieldLabel: 'Plan Status',
				        itemId: 'planStatus',
				        name: 'planStatus',
					    align: 'left'
				    
				    }, {
				        fieldLabel: 'Total Plan Courses',
				        itemId: 'totalPlanCourses',
				        name: 'totalPlanCourses'
				    }, {
				        fieldLabel: 'Plan Demerits',
				        itemId: 'planRatioDemerits',
				        name: 'planRatioDemerits'
				    }, {
				        fieldLabel: 'Plan Ratio',
				        itemId: 'planRatio',
				        name: 'planRatio'
				    },{
				        fieldLabel: 'Plan Note',
				        name: 'planNote',
				        itemId: 'planNote',
				    },{
	                    tooltip: 'Re-Calculate Plan Status',
	                    text: 'Re-Calculate Plan Status',
	                    xtype: 'button',
	                    itemId: 'calcPlanStatusButton'
	                }]
				},
			         {
			        	xtype: 'gridpanel',
			        	queryMode:'local',
			        	title: 'Term Details',
			        	width:800,
			        	height:200,
			        	align: 'center',
			            store: me.mapStatusReportTermDetailsStore,
			            columns: [{
			                xtype: 'gridcolumn',
			                dataIndex: 'termCode',
			                text: 'Term',
			                flex: .10
			            }, {
			                xtype: 'gridcolumn',
			                dataIndex: 'termRatio',
			                text: 'Term Ratio',
			                flex: .15
			            }, {
			                xtype: 'gridcolumn',
			                dataIndex: 'anomalyCode',
			                text: 'Anomaly Code',
			                flex: .20
			            }, {
			                xtype: 'gridcolumn',
			                dataIndex: 'anomalyNote',
			                text: 'Note',
			                flex: .20
			            }],
			            viewConfig: {
			                markDirty: false
			            }
			         },
			         { 
			         },
			         {
				        	xtype: 'gridpanel',
				        	queryMode:'local',
				        	title: 'Course Details',
				        	width:800,
				        	height:200,
				            store: me.mapStatusReportCourseDetailsStore,
				            columns: [{
				                xtype: 'gridcolumn',
				                dataIndex: 'termCode',
				                text: 'Term',
				                flex: .10
				            }, {
				                xtype: 'gridcolumn',
				                dataIndex: 'formattedCourse',
				                text: 'Formatted Course',
				                flex: .15
				            }, {
				                xtype: 'gridcolumn',
				                dataIndex: 'courseCode',
				                text: 'Course Code',
				                flex: .20
				            }, {
				                xtype: 'gridcolumn',
				                dataIndex: 'anomalyCode',
				                text: 'Anomaly Code',
				                flex: .20
				            }, {
				                xtype: 'gridcolumn',
				                dataIndex: 'anomalyNote',
				                text: 'Note',
				                flex: .20
				            }],
				            viewConfig: {
				                markDirty: false
				            }
				         }	,
				         { 
				         },
				         {
					        	xtype: 'gridpanel',
					        	queryMode:'local',
					        	title: 'Substitution Details',
					        	width:800,
					        	height:200,
					            store: me.mapStatusReportSubstitutionDetailsStore,
					            columns: [{
					                xtype: 'gridcolumn',
					                dataIndex: 'termCode',
					                text: 'Term',
					                flex: .17
					            }, {
					                xtype: 'gridcolumn',
					                dataIndex: 'formattedCourse',
					                text: 'Formatted Course',
					                flex: .17
					            }, {
					                xtype: 'gridcolumn',
					                dataIndex: 'courseCode',
					                text: 'Course Code',
					                flex: .15
					            }, {
					                xtype: 'gridcolumn',
					                dataIndex: 'substitutedTermCode',
					                text: 'Substituted Term Code',
					                flex: .27
					            }, {
					                xtype: 'gridcolumn',
					                dataIndex: 'substitutedFormattedCourse',
					                text: 'Substituted Formatted Course',
					                flex: .27
					            }, {
					                xtype: 'gridcolumn',
					                dataIndex: 'substitutedCourseCode',
					                text: 'Substituted Course Course',
					                flex: .27
					            }, {
					                xtype: 'gridcolumn',
					                dataIndex: 'substitutionCode',
					                text: 'Substitution Reason',
					                flex: .27
					            }],
					            viewConfig: {
					                markDirty: false
					            }
					         }			         
		]});

        return this.callParent(arguments);
    }
});
