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
            layout: 'hbox',
            name: 'profileDetails',
            itemId: 'profileDetails',
            items: [{
                xtype: 'container',
                layout: 'anchor',
                width: '100%',
                flex: 4,
                padding: 0,
                margin: '0 20 0 0',
                items: [{
                    xtype: 'fieldset',
                    title: 'Demographic and Academic',
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
                        fieldLabel: me.textStore.getValueByCode('ssp.label.gender'),
                        name: 'gender',
                        itemId: 'gender',
                        labelWidth: 50
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.marital-status'),
                        name: 'maritalStatus',
                        itemId: 'maritalStatus',
                        labelWidth: 84
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.ethnicity'),
                        name: 'ethnicity',
                        itemId: 'ethnicity',
                        labelWidth: 55
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.race'),
                        name: 'race',
                        itemId: 'race',
                        labelWidth: 35
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.residency'),
                        name: 'residencyCounty',
                        itemId: 'residencyCounty',
                        labelWidth: 64
                    }, {
                        fieldLabel: 'F1',
                        name: 'f1Status',
                        itemId: 'f1Status',
                        labelWidth: 24
                    }, {
                        fieldLabel: 'Standing',
                        name: 'academicStanding',
                        itemId: 'academicStanding',
                        labelWidth: 57
                    }, {
                        fieldLabel: 'Restrictions',
                        name: 'currentRestrictions',
                        itemId: 'currentRestrictions',
                        labelWidth: 72
                    }, {
                        fieldLabel: 'Academic Program',
                        name: 'academicPrograms',
                        itemId: 'academicPrograms',
                        labelAlign: 'top'
                    }, {
                        fieldLabel: 'Intended Program',
                        itemId: 'intendedProgramAtAdmit',
                        name: 'intendedProgramAtAdmit',
                        labelAlign: 'top'
                    }, {
                        fieldLabel: 'Start Term',
                        name: 'startYearTerm',
                        itemId: 'startYearTerm',
                        labelWidth: 65
                    }, {
                        fieldLabel: 'Anticipated Start Term',
                        name: 'anticipatedStartYearTerm',
                        itemId: 'anticipatedStartYearTerm',
                        labelWidth: 130
                    }, {
                        fieldLabel: 'Transfer Hours',
                        name: 'transferHrs',
                        itemId: 'transferHrs',
                        labelWidth: 87
                    }]
                }, {
                    xtype: 'fieldset',
                    title: 'MAP',
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
                            tooltip: 'Email MAP',
                            text: '',
                            width: 30,
                            height: 30,
                            cls: 'mapEmailIcon',
                            xtype: 'button',
                            itemId: 'emailPlanButton',
                            hidden: !me.authenticatedPerson.hasAccess('MAP_TOOL_EMAIL_BUTTON')
                        }, {
                            tooltip: 'Print MAP',
                            text: '',
                            width: 30,
                            height: 30,
                            cls: 'mapPrintIcon',
                            xtype: 'button',
                            itemId: 'printPlanButton',
                            hidden: !me.authenticatedPerson.hasAccess('MAP_TOOL_PRINT_BUTTON')
                        }]
                    }, {
                        fieldLabel: 'Plan Program',
                        name: 'planProgram',
                        itemId: 'planProgram',
                        labelWidth: 85
                    }, {
                        fieldLabel: 'Plan Catalog Year',
                        name: 'planCatalogYear',
                        itemId: 'planCatalogYear',
                        labelWidth: 105
                    }, {
                        fieldLabel: 'Plan Status',
                        itemId: 'onPlan',
                        name: 'onPlan',
                        labelWidth: 75
                    }, {
                        fieldLabel: 'Plan Name',
                        name: 'mapName',
                        itemId: 'mapName',
                        labelWidth: 75
                    }, {
                        fieldLabel: 'Plan Owner',
                        name: 'advisor',
                        itemId: 'advisor',
                        labelWidth: 75
                    }, {
                        fieldLabel: 'Plan Updated',
                        name: 'mapLastUpdated',
                        itemId: 'mapLastUpdated',
                        labelWidth: 85
                    }, {
                        fieldLabel: 'Plan Ends',
                        name: 'mapProjected',
                        itemId: 'mapProjected',
                        labelWidth: 65
                    }]
                }]
            }, {
                xtype: 'fieldset',
                title: 'Cumulative and Term Activity',
                flex: 5,
                width: '100%',
            	padding: '6 10 10 10',
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
                            text: 'Completion',
                            dataIndex: 'totalCreditCompletionRate'
                        }, {
                            text: 'Attempted',
                            dataIndex: 'totalCreditHoursAttempted'
                        }, {
                            text: 'Earned',
                            dataIndex: 'totalCreditHoursEarned'
                        }, {
                            text: 'GPA',
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
                    columns: {
                        defaults: {
                            flex: 1,
                            menuDisabled: true,
                            sortable: false,
                            draggable: false
                        },
                        items: [{
                            text: 'Term',
                            dataIndex: 'termCode'
                        }, {
                            text: 'Attempted',
                            dataIndex: 'creditHoursAttempted'
                        }, {
                            text: 'Earned',
                            dataIndex: 'creditHoursEarned'
                        }, {
                            text: 'GPA',
                            dataIndex: 'gradePointAverage'
                        }, {
                            text: 'Paid',
                            dataIndex: 'termCode',
                            renderer: function(val, meta, rec, rowIdx, colIdx, store, view){
                                // check against the financial data by termCode??
                                return '';
                            }
                        }]
                    }
                }]
            }]
        });
        
        return me.callParent(arguments);
    }
});
