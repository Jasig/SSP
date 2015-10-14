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
Ext.define('Ssp.view.tools.profile.Details', {
    extend: 'Ext.form.Panel',
    alias: 'widget.profiledetails',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.profile.ProfilePersonDetailsViewController',
    inject: {
		authenticatedPerson: 'authenticatedPerson',
		termTranscriptsStore: 'termTranscriptsStore',
		textStore:'sspTextStore'
    },
    width: '100%',
    height: '100%',
    initComponent: function(){
        var me = this;
		
		var termsTotalStore = Ext.create('Ext.data.Store', {
	    	fields: [{name: 'totalCreditCompletionRate', type: 'string'},
	                 {name: 'totalCreditHoursAttempted', type: 'string'},
	                 {name: 'totalCreditHoursEarned', type: 'string'},
	                 {name: 'totalGradePointAverage', type: 'string'}]
		});
        
        Ext.apply(me, {
            border: 0,
            bodyPadding: 10,
            layout: 'column',
            minHeight: 610,
            name: 'profileDetails',
            itemId: 'profileDetails',
            items: [{
                xtype: 'container',
                layout: 'anchor',
                flex: 4,
                padding: 0,
                margin: '0 20 0 0',
                columnWidth: 0.6,
                items: [{
                    xtype: 'fieldset',
                    title: me.textStore.getValueByCode('ssp.label.main.details.demographic-and-academic', 'Demographic and Academic'),
                    anchor: '100%',
                    flex: 1,
                    layout: 'anchor',
            		padding: '4 10 10 10',
                    defaults: {
                        anchor: '100%',
                        height: 18,
                        margin: 0
                    },
                    defaultType: 'displayfield',
                    items: [{
                        fieldLabel: me.textStore.getValueByCode('ssp.label.gender', 'Gender'),
                        name: 'gender',
                        itemId: 'gender',
                        labelWidth: 50
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.marital-status', 'Marital Status'),
                        name: 'maritalStatus',
                        itemId: 'maritalStatus',
                        labelWidth: 84
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.ethnicity', 'Ethnicity'),
                        name: 'ethnicity',
                        itemId: 'ethnicity',
                        labelWidth: 55
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.race', 'Race'),
                        name: 'race',
                        itemId: 'race',
                        labelWidth: 35
                    }, {
                        fieldLabel:  me.textStore.getValueByCode('ssp.label.residency', 'Residency'),
                        name: 'residencyCounty',
                        itemId: 'residencyCounty',
                        labelWidth: 64
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.f1-status', 'F1'),
                        name: 'f1Status',
                        itemId: 'f1Status',
                        labelWidth: 24
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.local-gpa', 'Local GPA'),
                        name: 'localGPA',
                        itemId: 'localGPA',
                        labelWidth: 84,
                        hidden: true
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.program-gpa', 'Program GPA'),
                        name: 'programGPA',
                        itemId: 'programGPA',
                        labelWidth: 84,
                        hidden: true
                    }, {
                         fieldLabel: me.textStore.getValueByCode('ssp.label.career-decision-status', 'Career Decision'),
                         name: 'careerStatus',
                         itemId: 'careerStatus',
                         labelWidth: 84,
                         hidden: true
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.academic-standing', 'Standing'),
                        name: 'academicStanding',
                        itemId: 'academicStanding',
                        labelWidth: 57
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.current-restrictions', 'Restrictions'),
                        name: 'currentRestrictions',
                        itemId: 'currentRestrictions',
                        labelWidth: 72
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.main.details.academic-program', 'Academic Program'),
                        name: 'academicPrograms',
                        itemId: 'academicPrograms',
                        labelAlign: 'top'
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.main.details.intended-program-at-admit', 'Intended Program'),
                        itemId: 'intendedProgramAtAdmit',
                        name: 'intendedProgramAtAdmit',
                        labelAlign: 'top'
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.home-campus', 'Home Campus'),
                        name: 'homeCampus',
                        itemId: 'homeCampus',
                        labelWidth: 85
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.main.details.actual-start-term', 'Start Term'),
                        name: 'actualStartTerm',
                        itemId: 'actualStartTerm',
                        labelWidth: 65
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.main.details.anticipated-start-term', 'Anticipated Start Term'),
                        name: 'anticipatedStartTerm',
                        itemId: 'anticipatedStartTerm',
                        labelWidth: 130
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.main.details.transfer-hours', 'Transfer Hours'),
                        name: 'transferHrs',
                        itemId: 'transferHrs',
                        labelWidth: 87
                    }]
                }, {
                    xtype: 'fieldset',
                    title: me.textStore.getValueByCode('ssp.label.main.details.map', 'MAP'),
                    anchor: '100%',
            		padding: '0 10 10 10',
                    flex: 1,
                    layout: 'anchor',
                    defaults: {
                        anchor: '100%',
                        height: 18,
                        margin: 0
                    },
                    defaultType: 'displayfield',
                    items: [{
                        xtype: 'container',
                        width: '100%',
                        layout: 'hbox',
                        margin: 0,
                        padding: 0,
                        height: 32,
                        items: [{
                            xtype: 'tbspacer',
                            flex: 1
                        }, {
                            tooltip: me.textStore.getValueByCode('ssp.tooltip.email-map', 'Email MAP'),
                            text: '',
                            width: 30,
                            height: 30,
                            cls: 'mapEmailIcon',
                            xtype: 'button',
                            itemId: 'emailPlanButton',
                            hidden: !me.authenticatedPerson.hasAccess('MAP_TOOL_EMAIL_BUTTON')
                        }, {
                            tooltip: me.textStore.getValueByCode('ssp.tooltip.print-map', 'Print MAP'),
                            text: '',
                            width: 30,
                            height: 30,
                            cls: 'mapPrintIcon',
                            xtype: 'button',
                            itemId: 'printPlanButton',
                            hidden: !me.authenticatedPerson.hasAccess('MAP_TOOL_PRINT_BUTTON')
                        }]
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.main.details.plan-program', 'Plan Program'),
                        name: 'planProgram',
                        itemId: 'planProgram',
                        labelWidth: 85
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.main.details.plan-catalog-year', 'Plan Catalog Year'),
                        name: 'planCatalogYear',
                        itemId: 'planCatalogYear',
                        labelWidth: 105
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.main.details.plan-status', 'Plan Status'),
                        itemId: 'onPlan',
                        name: 'onPlan',
                        labelWidth: 75
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.main.details.map-plan-name', 'Plan Name'),
                        name: 'mapName',
                        itemId: 'mapName',
                        labelWidth: 75
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.main.details.advisor', 'Plan Owner'),
                        name: 'advisor',
                        itemId: 'advisor',
                        labelWidth: 75
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.main.details.map-last-updated', 'Plan Updated'),
                        name: 'mapLastUpdated',
                        itemId: 'mapLastUpdated',
                        labelWidth: 85
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.main.details.map-projected', 'Plan Ends'),
                        name: 'mapProjected',
                        itemId: 'mapProjected',
                        labelWidth: 65
                    }, {
						xtype: 'tbspacer',
						height: 10
					}]
                }]
            }, {
                xtype: 'fieldset',
                title: me.textStore.getValueByCode('ssp.label.main.details.cumulative-and-term-activity', 'Cumulative and Term Activity'),
                flex: 5,
            	padding: '6 10 10 10',
                columnWidth: 0.4,
                items: [{
                    xtype: 'grid',
					itemId: 'cumTermGrid',
                    minHeight: 1,
                    store: termsTotalStore,
                    columns: {
                        defaults: {
                            flex: 1,
                            menuDisabled: true,
                            sortable: false,
                            draggable: false
                        
                        },
                        items: [{
                            text: me.textStore.getValueByCode('ssp.label.main.details.total-credit-completion-rate', 'Completion'),
                            dataIndex: 'totalCreditCompletionRate'
                        }, {
                            text: me.textStore.getValueByCode('ssp.label.main.details.total-credit-hours-attempted', 'Attempted'),
                            dataIndex: 'totalCreditHoursAttempted'
                        }, {
                            text: me.textStore.getValueByCode('ssp.label.main.details.total-credit-hours-earned', 'Earned'),
                            dataIndex: 'totalCreditHoursEarned'
                        }, {
                            text: me.textStore.getValueByCode('ssp.label.main.details.total-gpa', 'GPA'),
                            dataIndex: 'totalGradePointAverage'
                        }]
                    }
                }, {
                    xtype: 'tbspacer',
                    height: 20
                }, {
                    xtype: 'grid',
                    flex: 1,
                    minHeight: 1,
					height: '100%',
                    store: me.termTranscriptsStore,
					viewConfig: {
						markDirty: false
					},
                    columns: {
                        defaults: {
                            flex: 1,
                            menuDisabled: true,
                            sortable: false,
                            draggable: false
                        },
                        items: [{
                            text: me.textStore.getValueByCode('ssp.label.main.details.term-code', 'Term'),
                            dataIndex: 'termCode'
                        }, {
                            text: me.textStore.getValueByCode('ssp.label.main.details.credit-hours-attempted', 'Attempted'),
                            dataIndex: 'creditHoursAttempted',
                            xtype: 'gridcolumn',
                            renderer: Ssp.util.Util.fixedWidthFloatRenderer
                        }, {
                            text: me.textStore.getValueByCode('ssp.label.main.details.credit-hours-earned', 'Earned'),
                            dataIndex: 'creditHoursEarned',
                            xtype: 'gridcolumn',
                            renderer: Ssp.util.Util.fixedWidthFloatRenderer
                        }, {
                            text: me.textStore.getValueByCode('ssp.label.main.details.gpa', 'GPA'),
                            dataIndex: 'gradePointAverage',
                            xtype: 'gridcolumn',
                            renderer: Ssp.util.Util.fixedWidthFloatRenderer
                        }, {
                            text: me.textStore.getValueByCode('ssp.label.main.details.tuition-paid', 'Paid'),
                            dataIndex: 'tuitionPaid'
                        }]
                    }
                }]
            }]
        });
        
        return me.callParent(arguments);
    }
});
