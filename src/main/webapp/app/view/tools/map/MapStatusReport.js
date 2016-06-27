/*
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
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
    	personLite: 'personLite',
    	mapStatusReportCourseDetailsStore: 'mapStatusReportCourseDetailsStore',
    	mapStatusReportTermDetailsStore: 'mapStatusReportTermDetailsStore',
    	mapStatusReportSubstitutionDetailsStore: 'mapStatusReportSubstitutionDetailsStore',
    	textStore: 'sspTextStore'
    },
	height:580,
	width:900,   
    overflowY: 'auto',
    style : 'z-index: -1;',  
    layout: {
		type : 'vbox',
        align: 'stretch'
    },
    initComponent: function() {
    	var me=this;
        Ext.apply(me, {
			title: me.textStore.getValueByCode('ssp.label.map.status-report.title','MAP Status Report:') + ' ' + me.personLite.get('displayFullName'),
            dockedItems: [{
                dock: 'top',
                xtype: 'toolbar',
				height: '25',
                items: [{
                    tooltip: me.textStore.getValueByCode('ssp.tooltip.close-button','Close'),
                    text: me.textStore.getValueByCode('ssp.label.close-button','Close'),
                    height: 22,
                    xtype: 'button',
                    itemId: 'closeButton'
                }]
            }],
			items: [{
				    xtype: 'form',
				    defaultType: 'displayfield',
				    id: 'statusForm',
				    margin: '0 0 2 2',
				    bodyStyle: 'background:none',
				    height: '370',
				    items: [{
				        fieldLabel: me.textStore.getValueByCode('ssp.label.map.status-report.plan-status','Plan Status'),
				        itemId: 'planStatus',
				        name: 'planStatus',
					    align: 'left'
				    
				    }, {
				        fieldLabel: me.textStore.getValueByCode('ssp.label.map.status-report.total-plan-courses','Total Plan Courses In Scope'),
				        itemId: 'totalPlanCourses',
				        name: 'totalPlanCourses'
				    }, {
				        fieldLabel: me.textStore.getValueByCode('ssp.label.map.status-report.plan-ratio-demerits','Plan Anomalies'),
				        itemId: 'planRatioDemerits',
				        name: 'planRatioDemerits'
				    }, {
				        fieldLabel: me.textStore.getValueByCode('ssp.label.map.status-report.plan-ratio','Plan Ratio'),
				        itemId: 'planRatio',
				        name: 'planRatio'
				    },{
				        fieldLabel: me.textStore.getValueByCode('ssp.label.map.status-report.plan-note','Plan Note'),
				        name: 'planNote',
				        itemId: 'planNote'
				    },{
	                    tooltip: me.textStore.getValueByCode('ssp.tooltip.map.status-report.re-calc-button','Re-Calculate Plan Status'),
	                    text: me.textStore.getValueByCode('ssp.label.map.status-report.re-calc-button','Re-Calculate Plan Status'),
	                    xtype: 'button',
	                    itemId: 'calcPlanStatusButton'
	                }]
				}, {
			        	xtype: 'gridpanel',
			        	queryMode:'local',
			        	title: me.textStore.getValueByCode('ssp.label.map.status-report.term-details','Term Details'),
			        	width:800,
			        	height:200,
			        	align: 'center',
			            store: me.mapStatusReportTermDetailsStore,
			            columns: [{
			                xtype: 'gridcolumn',
			                dataIndex: 'termCode',
			                text: me.textStore.getValueByCode('ssp.label.map.status-report.term-code','Plan Term'),
			                flex: 0.10
			            }, {
			                xtype: 'gridcolumn',
			                dataIndex: 'termRatio',
			                text: me.textStore.getValueByCode('ssp.label.map.status-report.term-ratio','Term Ratio'),
			                flex: 0.15
			            }, {
			                xtype: 'gridcolumn',
			                dataIndex: 'anomalyCode',
			                text: me.textStore.getValueByCode('ssp.label.map.status-report.anomaly-code','Anomaly Code'),
			                flex: 0.20
			            }, {
			                xtype: 'gridcolumn',
			                dataIndex: 'anomalyNote',
			                text: me.textStore.getValueByCode('ssp.label.map.status-report.anomaly-note','Note'),
			                flex: 0.20
			            }],
			            viewConfig: {
			                markDirty: false
			            }
			         }, {
				        	xtype: 'gridpanel',
				        	queryMode:'local',
				        	title: me.textStore.getValueByCode('ssp.label.map.status-report.course-details-title','Course Details'),
				        	width:800,
				        	height:200,
				            store: me.mapStatusReportCourseDetailsStore,
				            columns: [{
				                xtype: 'gridcolumn',
				                dataIndex: 'termCode',
				                text: me.textStore.getValueByCode('ssp.label.map.status-report.term-code','Plan Term'),
				                flex: 0.10
				            }, {
				                xtype: 'gridcolumn',
				                dataIndex: 'formattedCourse',
				                text: me.textStore.getValueByCode('ssp.label.map.status-report.formatted-course','Plan Formatted Course'),
				                flex: 0.15
				            }, {
				                xtype: 'gridcolumn',
				                dataIndex: 'courseCode',
				                text: me.textStore.getValueByCode('ssp.label.map.status-report.course-code','Plan Course Code'),
				                flex: 0.20
				            }, {
				                xtype: 'gridcolumn',
				                dataIndex: 'anomalyCode',
				                text: me.textStore.getValueByCode('ssp.label.map.status-report.anomaly-code','Anomaly Code'),
				                flex: 0.20
				            }, {
				                xtype: 'gridcolumn',
				                dataIndex: 'anomalyNote',
				                text: me.textStore.getValueByCode('ssp.label.map.status-report.anomaly-note','Note'),
				                flex: 0.20
				            }],
				            viewConfig: {
				                markDirty: false
				            }
				         }, {
					        	xtype: 'gridpanel',
					        	queryMode:'local',
					        	title: me.textStore.getValueByCode('ssp.label.map.status-report.on-track-details-title','On Track Details'),
					        	width:800,
					        	height:200,
					            store: me.mapStatusReportSubstitutionDetailsStore,
					            columns: [{
					                xtype: 'gridcolumn',
					                dataIndex: 'termCode',
					                text: me.textStore.getValueByCode('ssp.label.map.status-report.term-code','Plan Term'),
					                flex: 0.12
					            }, {
					                xtype: 'gridcolumn',
					                dataIndex: 'formattedCourse',
					                text: me.textStore.getValueByCode('ssp.label.map.status-report.formatted-course','Plan Formatted Course'),
					                flex: 0.22
					            }, {
					                xtype: 'gridcolumn',
					                dataIndex: 'courseCode',
					                text: me.textStore.getValueByCode('ssp.label.map.status-report.course-code','Plan Course Code'),
					                flex: 0.20
					            }, {
					                xtype: 'gridcolumn',
					                dataIndex: 'substitutedTermCode',
					                text: me.textStore.getValueByCode('ssp.label.map.status-report.substituted-term-code','Transcript Term Code'),
					                flex: 0.23
					            }, {
					                xtype: 'gridcolumn',
					                dataIndex: 'substitutedFormattedCourse',
					                text: me.textStore.getValueByCode('ssp.label.map.status-report.substituted-formatted-course','Transcript Formatted Course'),
					                flex: 0.27
					            }, {
					                xtype: 'gridcolumn',
					                dataIndex: 'substitutedCourseCode',
					                text: me.textStore.getValueByCode('ssp.label.map.status-report.substituted-course-code','Transcript Course Code'),
					                flex: 0.27
					            }, {
					                xtype: 'gridcolumn',
					                dataIndex: 'substitutionCode',
					                text: me.textStore.getValueByCode('ssp.label.map.status-report.substitution-code','Substitution Reason'),
					                flex: 0.27
					            }],
					            viewConfig: {
					                markDirty: false
					            }
					         }			         
		]});
        return this.callParent(arguments);
    }
});
