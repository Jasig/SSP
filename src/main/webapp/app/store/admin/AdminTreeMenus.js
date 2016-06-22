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
Ext.define('Ssp.store.admin.AdminTreeMenus', {
    extend: 'Ext.data.TreeStore',
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        columnRendererUtils: 'columnRendererUtils',
        colorsStore: 'colorsStore',
        colorsUnpagedStore: 'colorsUnpagedStore',
        colorsAllStore: 'colorsAllStore',
        colorsAllUnpagedStore: 'colorsAllUnpagedStore',
        confidentialityLevelOptionsStore: 'confidentialityLevelOptionsStore',
       	authenticatedPerson: 'authenticatedPerson',
        textStore: 'sspTextStore'
    },
    autoLoad: false,
    constructor: function(){
        var me = this;
        var items = {
            text: 'Administrative Tools',
            title: 'Administrative Tools',
            form: '',
            expanded: true,
            children: [
            {
                text: 'Accommodation',
                title: 'Accommodation',
                form: '',
                securityRole: 'REFERENCE_ACCOMMODATION_WRITE',
                expanded: false,
                children: [{
                    text: 'Disability Accommodations',
                    title: 'Disability Accommodations',
                    store: 'disabilityAccommodationsAll',
                    // Any config you want AbstractReferenceAdmin to consume effectively *must* be nested inside
                    // interface options. Some other fields will just happen to make it through, most notably
                    // 'columns', but only use that if you truly want to completely replace all the column defaults.
                    // 'columnDefaults' is generally a better choice for more targeted column re-config.
                    interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false,
                        columnOverrides: [
                            {
                                dataIndex: 'objectStatus',
                                flex: 0.10
                            },
                            {
                                dataIndex: 'name',
                                flex: 0.5
                            },
                            {
                                dataIndex: 'description',
                                flex: 0.5
                            },
                            {
                                header: 'Add. Desc',
                                required: true,
                                dataIndex: 'useDescription',
                                flex: 0.2,
                                renderer: me.columnRendererUtils.renderFriendlyBoolean,
                                field: {
                                    xtype: 'checkbox'
                                },
                                sortOrder: 40
                            },
                            {
                                header: 'Description Label',
                                required: false,
                                dataIndex: 'descriptionFieldLabel',
                                field: {
                                    xtype: 'textfield',
                                    maxLength: 80
                                },
                                flex: 0.5,
                                sortOrder: 50
                            },
                            {
                                header: 'Description Label Type (Long/Short)',
                                required: false,
                                dataIndex: 'descriptionFieldType',
                                field: {
                                    xtype: 'textfield',
                                    maxLength: 80
                                },
                                flex: 0.5,
                                sortOrder: 60
                            }
                        ]
                    },
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Disability Agencies',
                    title: 'Disability Agencies',
                    store: 'disabilityAgenciesAll',
					interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false                  
                    }, 
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Disability Statuses',
                    title: 'Disability Statuses',
                    store: 'disabilityStatusesAll',
					interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false                  
                    }, 
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Disability Types',
                    title: 'Disability Types',
                    store: 'disabilityTypesAll',
					interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false                  
                    }, 
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }]
            }, {
                text: 'Caseload Assignment',
                title: 'Caseload Assignment',
                form: '',
                securityRole: 'REFERENCE_CASELOAD_ASSIGNMENT_WRITE',
                expanded: false,
                children: [{
                    text: 'Program Status Change Reasons',
                    title: 'Program Status Change Reasons',
                    store: 'programStatusChangeReasonsAll',
					interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false                  
                    }, 
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                },{
                    text: 'Referral Sources',
                    title: 'Referral Sources',
                    store: 'referralSourcesAll',
					interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false                  
                    }, 
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Special Service Groups',
                    title: 'Special Service Groups',
                    store: 'specialServiceGroupsAll',
					interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false                  
                    }, 
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Service Reasons',
                    title: 'Service Reasons',
                    store: 'serviceReasonsAll',
					interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false                  
                    }, 
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Student Types',
                    title: 'Student Types',
                    store: 'studentTypes',
                    interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false,
                        columnOverrides: [
                            {
                                dataIndex: 'objectStatus',
                                flex: 0.10
                            },
                            {
                                dataIndex: 'name',
                                flex: 0.5
                            },
                            {
                                dataIndex: 'description',
                                flex: 0.5
                            },
                            {
                                header: 'Req. Appt.',
                                required: true,
                                dataIndex: 'requireInitialAppointment',
                                flex: 0.15,
                                renderer: me.columnRendererUtils.renderFriendlyBoolean,
                                field: {
                                    xtype: 'checkbox'
                                },
                                sortOrder: 40
                            },
                            {
                                header: 'Student Type Code',
                                required: true,
                                dataIndex: 'code',
                                editor:{
                                    allowBlank: false
                                },
                                field: {
                                    xtype: 'textfield',
                                    allowBlank: false,
                                    maxLength: 10
                                },
                                flex: 0.25,
                                sortOrder: 50
                            }
                        ]
                    },
					form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Re-Assignment',
                    title: 'Re-Assignment',
                    store: 'Re-Assignment',
                    form: 'caseloadreassignment',
                    leaf: true
                }, {
                     text: 'Bulk Add/Caseload Re-Assignment',
                     title: 'Bulk Add/Caseload Re-Assignment',
                     form: 'bulkAddCaseloadReassignment',
                     leaf: true
                 } ]
            }, {
                text: 'Student Intake',
                title: 'Student Intake',
                form: '',
                securityRole: 'REFERENCE_STUDENT_INTAKE_WRITE',
                expanded: false,
                children: [{
                    text: 'Child Care Arrangements',
                    title: 'Child Care Arrangements',
                    store: 'childCareArrangementsAll',
					interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false                  
                    }, 
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'CompletedItem',
                    title: 'CompletedItem',
                    store: 'completedItem',
                    form: 'AbstractReferenceAdmin',
					interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false                  
                    }, 
                    leaf: true
                },{
                    text: 'Citizenships',
                    title: 'Citizenships',
                    store: 'citizenshipsAll',
                    form: 'AbstractReferenceAdmin',
					interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false                  
                    }, 
                    leaf: true
                }, {
                    text: 'Confidentiality Disclosure Agreement',
                    title: 'Confidentiality Disclosure Agreement',
                    store: '',
                    form: 'CDAAdmin',
                    leaf: true
                }, {
                    text: 'Education Goals',
                    title: 'Education Goals',
                    store: 'educationGoalsAll',
					interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false                  
                    }, 
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Education Levels',
                    title: 'Education Levels',
                    store: 'educationLevelsAll',
					interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false                  
                    }, 
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Ethnicities',
                    title: 'Ethnicities',
                    store: 'ethnicitiesAll',
					interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false                  
                    }, 
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
					text: 'Races',
                    title: 'Races',
                    store: 'racesAll',
					interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false,
                        columnOverrides: [{
                            header: 'Race Code',
                            required: true,
                            dataIndex: 'code',
                            editor:{
                                allowBlank: false
                            },
                            field: {
                                xtype: 'textfield',
                                allowBlank: false,
                                maxLength: 10
                            },
                            flex: 30,
                            sortOrder: 40
                        }],
                        columnFieldOverrides: {
                            description: {
                                maxLength: 150
                            }
                        }
                    },
					form: 'AbstractReferenceAdmin',
                    leaf: true
                },
                {
                    text: 'Coursework Hours',
                    title: 'Coursework Hours',
                    store: 'courseworkHoursAll',
					interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false                  
                    }, 
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Funding Sources',
                    title: 'Funding Sources',
                    store: 'fundingSourcesAll',
					interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false                  
                    }, 
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Marital Statuses',
                    title: 'Marital Statuses',
                    store: 'maritalStatusesAll',
					interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false                  
                    }, 
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Military Affiliations',
                    title: 'Military Affiliations',
                    store: 'militaryAffiliationsAll',
					interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false                  
                    }, 
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                },{
                    text: 'Registration Loads',
                    title: 'Registration Loads',
                    store: 'registrationLoadsAll',
					interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false                  
                    }, 
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Student Statuses',
                    title: 'Student Statuses',
                    store: 'studentStatusesAll',
					interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false                  
                    }, 
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Veteran Statuses',
                    title: 'Veteran Statuses',
                    store: 'veteranStatusesAll',
					interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false                  
                    }, 
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }]
            }, {    inject: {
                columnRendererUtils: 'columnRendererUtils',
                colorsStore: 'colorsStore',
                colorsUnpagedStore: 'colorsUnpagedStore',
                colorsAllStore: 'colorsAllStore',
                colorsAllUnpagedStore: 'colorsAllUnpagedStore',
                confidentialityLevelOptionsStore: 'confidentialityLevelOptionsStore'
            },
                text: 'Counseling Reference Guide',
                title: 'Counseling Reference Guide',
                form: '',
                securityRole: 'REFERENCE_COUNSELING_REF_GUIDE_WRITE',
                expanded: false,
                children: [{
                    text: 'Categories',
                    title: 'Categories',
                    store: 'challengeCategoriesAll',
                    interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false,
                        // Very targeted override of complex column config fields. If we did this with
                        // columnOverrides (as seen for several admin types above), the 'field' config on the overridden
                        // config would be completely replaced by whatever we defined here and we'd end up forced to
                        // duplicate all its default config. So columnFieldOverrides is a special workaround
                        // specifically for that complex 'field', ahem, field so its defaults can be retained when
                        // appropriate.
                        columnFieldOverrides: {
                            name: {
                                maxLength: 50
                            },
                            description: {
                                maxLength: 150
                            }
                        }
                    },
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Challenges',
                    title: 'Challenges',
                    store: '',
                    form: 'ChallengeAdmin',
                    leaf: true
                }, {
                    text: 'Referrals',
                    title: 'Referrals',
                    store: '',
                    form: 'ChallengeReferralAdmin',
                    leaf: true
                }]
            }, {
                text: 'MyGPS',
                title: 'MyGPS',
                form: '',
                securityRole: 'REFERENCE_MYGPS_WRITE',
                expanded: false,
                children: [{
                    text: 'Self Help Guides',
                    title: 'Self Help Guides',
                    store: 'selfHelpGuides',
                    form: 'selfhelpguideadmin',
                    leaf: true
                }]
            }, {
                text: 'Security',
                title: 'Security',
                form: '',
                securityRole: 'REFERENCE_SECURITY_WRITE',
                expanded: false,
                children: [{
                    text: 'Confidentiality Levels',
                    title: 'Confidentiality Levels',
                    store: 'confidentialityLevelsAll',
                    interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false,
                        columnOverrides: [
                            {
                                dataIndex: 'objectStatus',
                                flex: 0.10
                            },
                            {
                                dataIndex: 'name',
                                flex: 0.2
                            },
                            {
                                dataIndex: 'description',
                                flex: 0.3
                            },
                            {
                                header: 'Acronym',
                                dataIndex: 'acronym',
                                required: true,
                                flex: 0.10,
                                editor:{
                                    allowBlank: false
                                },
                                field: {
                                    xtype: 'textfield',
                                    allowBlank: false,
                                    maxLength: 10,
                                    fieldStyle: "margin-bottom:12px;" // workaround for presence of combobox field
                                },
                                sortOrder: 40
                            },
                            {
                                header: 'Data Permission',
                                dataIndex: 'permission',
                                required: true,
                                field: {
                                    xtype: 'combo',
                                    store: me.confidentialityLevelOptionsStore,
                                    displayField: 'name',
                                    valueField:'name',
                                    typeAhead: true,
                                    forceSelection: false,
                                    associativeField: 'permission'
                                },
                                flex: 0.3,
                                sortOrder: 50
                            }
                        ],
                        columnFieldOverrides: {
                            objectStatus: {
                                fieldStyle: "margin-bottom:12px;" // workaround for presence of combobox field
                            },
                            name: {
                                fieldStyle: "margin-bottom:12px;" // workaround for presence of combobox field
                            },
                            description: {
                                fieldStyle: "margin-bottom:12px;" // workaround for presence of combobox field
                            }
                        }
                    }, 
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'OAuth2 Clients',
                    title: 'OAuth2 Clients',
                    store: 'oauth2Clients',
                    form: 'oauth2clientadmin',
                    leaf: true
                },
                {
                    text: 'LTI Consumers',
                    title: 'LTI Consumers',
                    store: 'ltiConsumers',
                    form: 'lticonsumeradmin',
                    leaf: true
                }]
            }, {
                text: 'Early Alert',
                title: 'Early Alert',
                form: '',
                securityRole: 'REFERENCE_EARLY_ALERT_WRITE',
                expanded: false,
                children: [{
                    text: 'Campuses',
                    title: 'Campuses',
                    store: '',
                    form: 'CampusAdmin',
                    leaf: true
                }, {
                    text: 'Outcomes',
                    title: 'Outcomes',
                    store: 'earlyAlertOutcomesAll',
					interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false                  
                    }, 
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Outreaches',
                    title: 'Outreaches',
                    store: 'earlyAlertOutreachesAll',
					interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false                  
                    }, 
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Reasons',
                    title: 'Reasons',
                    store: 'earlyAlertReasonsAll',
					interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false                  
                    }, 
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Referrals',
                    title: 'Referrals',
                    store: 'earlyAlertReferralsAll',
                    form: 'AbstractReferenceAdmin',
                    leaf: true,
					interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false,
                        columnOverrides: [
                            {
                                dataIndex: 'objectStatus',
                                flex: 0.10
                            },
                            {
                                dataIndex: 'name',
                                flex: 50
                            },
                            {
                                dataIndex: 'description',
                                flex: 50
                            },
                            {
                                header: 'Acronym',
                                required: true,
                                dataIndex: 'acronym',
                                flex: 20,
                                editor:{
                                    allowBlank: false
                                },
                                field: {
                                    xtype: 'textfield',
                                    allowBlank: false,
                                    maxLength: 80
                                },
                                sortOrder: 40
                            },
                            {
                                header: 'Referral Email',
                                required: false,
                                dataIndex: 'recipientEmailAddress',
                                renderer: me.columnRendererUtils.renderValidEmail,
                                flex: 25,
                                field: {
                                    xtype: 'textfield',
                                    vtype: 'email',
                                    maxLength: 100
                                },
                                sortOrder: 50
                            },
                            {
                                header: 'Carbon Copy(s)',
                                required: false,
                                dataIndex: 'carbonCopy',
                                renderer: me.columnRendererUtils.renderValidEmail,
                                flex: 50,
                                field: {
                                    xtype: 'textfield',
                                    vtype: 'multiemail',
                                    maxLength: 512
                                },
                                sortOrder: 60
                            }
                        ]
                    }
                }, {
                    text: 'Suggestions',
                    title: 'Suggestions',
                    store: 'earlyAlertSuggestionsAll',
					interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false                  
                    }, 
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }]
            }, {
                text: 'Journal',
                title: 'Journal',
                form: '',
                securityRole: 'REFERENCE_JOURNAL_WRITE',
                expanded: false,
                children: [{
                    text: 'Sources',
                    title: 'Sources',
                    store: 'journalSourcesAll',
					interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false                  
                    }, 
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Details',
                    title: 'Details',
                    store: '',
                    form: 'JournalStepDetailAdmin',
                    leaf: true
                }, {
                    text: 'Steps',
                    title: 'Steps',
                    store: '',
                    form: 'JournalStepAdmin',
                    leaf: true
                }, {
                    text: 'Tracks',
                    title: 'Tracks',
                    store: 'journalTracksAll',
					interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false                  
                    }, 
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }]
            }, {
                text: 'System Configuration',
                title: 'System Configuration',
                form: '',
                securityRole: 'REFERENCE_SYSTEM_CONFIG_WRITE',
                expanded: false,
                children: [{
                    text: 'Configuration Options',
                    title: 'Configuration Options',
                    store: 'configurationOptions',
                    form: 'configurationoptionsadmin',
                    leaf: true
                },{
                    text: 'Enrollment Statuses',
                    title: 'Enrollment Statuses',
                    store: 'enrollmentStatuses',
                    viewConfig: {
                        markDirty: false
                    },
                    interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false,
                        columnOverrides: [
                            // leave objectStatus default alone
                            {
                                dataIndex: 'name',
                                flex: 0.7
                            },
                            {
                                header: 'Code',
                                dataIndex: 'code',
                                required: true,
                                editor:{
                                    allowBlank: false
                                },
                                field: {
                                    xtype: 'textfield',
                                    allowBlank: false,
                                    maxLength: 50
                                },
                                flex: 0.20,
                                sortOrder: 25
                            },
                            {
                                dataIndex: 'description',
                                flex: 1
                            }
                        ]
                    },
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Message Templates',
                    title: 'Message Templates',
                    store: 'messageTemplates',
                    form: 'messagetemplatesadmin',
                    leaf: true
                }, {
                    text: 'Message Queue',
                    title: 'Message Queue',
                    store: 'messages',
                    form: 'messagequeueadmin',
                    leaf: true
                }, {
                    text: 'Text',
                    title: 'Text',
                    store: 'text',
                    interfaceOptions: {
                        addButtonVisible: false,
                        deleteButtonVisible: false,
                        hasPagingToolbar: false,
                        columnOverrides: [
                            {
                                dataIndex: 'objectStatus',
                                rowEditable: false,
                                flex: 0.05
                            },
                            {
                                dataIndex: 'name',
                                rowEditable: false,
                                flex: 0.25
                            },
                            {
                                header: 'Value',
                                dataIndex: 'value',
                                flex: 0.3,
                                field: {
                                    xtype: 'textfield',
                                    maxLength: 256
                                },
                                sortOrder: 22
                            },
                            {
                                header: 'Code',
                                rowEditable: false,
                                dataIndex: 'code',
                                filterable: true,
                                flex: 0.25,
                                field: {
                                    xtype: 'textfield'
                                },
                                sortOrder: 26
                            },
                            {
                                dataIndex: 'description',
                                flex: 0.20
                            }
                        ]
                    },
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                    }]
                },{
                    text: 'Main Tool',
                    title: 'Main Tool',
                    form: '',
                    securityRole: 'REFERENCE_MAIN_TOOL_WRITE',
                    expanded: false,
                    children: [{
                        text: 'SAP Statuses',
                        title: 'SAP Statuses',
                        store: 'sapStatusesAll',
                        interfaceOptions: {
                            addButtonVisible: true,
                            deleteButtonVisible: false,
                            columnOverrides: [
                            {
                                header: 'SAP Code',
                                required: true,
                                dataIndex: 'code',
                                editor:{
                                    allowBlank: false
                                },
                                field: {
                                    xtype: 'textfield',
                                    allowBlank: false,
                                    maxLength: 10
                                },
                                flex: 25,
                                sortOrder: 40
                            }
                        ],
                        columnFieldOverrides: {
                            description: {
                                maxLength: 150
                            }
                        }
	                },
					form: 'AbstractReferenceAdmin',
	                leaf: true
	            }, {
					text: 'Financial Aid Files',
	                title: 'Financial Aid Files',
	                store: 'financialAidFilesAll',
                    interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false,
                        columnOverrides: [
                            {
                                header: 'File Code',
                                required: true,
                                dataIndex: 'code',
                                editor:{
                                    allowBlank: false
                                },
                                field: {
                                    xtype: 'textfield',
                                    allowBlank: false,
                                    maxLength: 10
                                },
                                flex: 25,
                                sortOrder: 40
                            }
                        ],
                        columnFieldOverrides: {
                            description: {
                                maxLength: 150
                            }
                        }
	                },
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Career Decision Statuses',
                    title: 'Career Decision Statuses',
                    store: 'careerDecisionStatusesAll',
                    interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false,
                        columnOverrides: [{
                            header: 'Career Status Code',
                            required: true,
                            dataIndex: 'code',
                            editor:{
                                allowBlank: false
                            },
                            field: {
                                xtype: 'textfield',
                                allowBlank: false,
                                maxLength: 2
                            },
                            flex: 25,
                            sortOrder: 40
                        }],
                        columnFieldOverrides: {
                            description: {
                                maxLength: 150
                            }
                        }
	                },
					form: 'AbstractReferenceAdmin',
	                leaf: true
                }, {
                    text: 'Success Indicators',
                    title: 'Success Indicators',
                    form: 'successindicatoradmin',
                    leaf: true
                }]
	        },{
                text: 'MAP',
                title: 'MAP',
                form: '',
                securityRole: 'REFERENCE_MAP_WRITE',
                expanded: false,
                children: [{
                    text: 'Elective Tags',
                    title: 'Elective Tags',
                    store: 'electivesAll',
                    sort: {
                    	field: 'sortOrder'
                    },
                    viewConfig: {
                        markDirty: false
                    },
                    interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false,
                        headerInstructions: "Double-click to edit and drag to re-order items.",
                        storeDependencies: [{
                            name: "colors",
                            store: me.colorsAllUnpagedStore,
                            clearFilter: true
                        }],
                        columnOverrides: [
                            // careful with flex values here. Some mixes can result in the combo box expander
                            // rendering off-screen.
                            {
                                header: 'Order',
                                dataIndex: 'sortOrder',
                                required: true,
                                editor:{
                                    allowBlank: false
                                },
                                field: {
                                    xtype: 'textfield',
                                    fieldStyle: "margin-bottom:12px;", // workaround for presence of combobox field
                                    allowBlank: false
                                },
                                flex: 0.1,
                                sortOrder: 5
                            },
                            {
                                dataIndex: 'objectStatus',
                                flex: 0.1
                            },
                            {
                                dataIndex: 'name',
                                flex: 0.2
                            },
                            {
                                header: 'Elective Code',
                                dataIndex: 'code',
                                defaultValue: "",
                                required: true,
                                editor:{
                                    allowBlank: false
                                },
                                field: {
                                    xtype: 'textfield',
                                    fieldStyle: "margin-bottom:12px;", // workaround for presence of combobox field
                                    allowBlank: false,
                                    maxLength: 10
                                },
                                flex: 0.2,
                                sortOrder: 25
                            },
                            {
                                dataIndex: 'description',
                                flex: 0.5
                            },
                            {
                                header: 'Color',
                                dataIndex: 'color',
                                renderer: me.columnRendererUtils.renderElectiveColor,
                                required: true,
                                field: {
                                    xtype: 'combo',
                                    store: me.colorsAllUnpagedStore,
                                    displayField: 'name',
                                    valueField: 'id',
                                    forceSelection: true,
                                    associativeField: 'color'
                                },
                                flex: 0.2,
                                sortOrder: 40
                            }
                        ],
                        columnFieldOverrides: {
                            objectStatus: {
                                fieldStyle: "margin-bottom:12px;" // workaround for presence of combobox field
                            },
                            name: {
                                maxLength: 50,
                                fieldStyle: "margin-bottom:12px;" // workaround for presence of combobox field
                            },
                            description: {
                                maxLength: 150,
                                fieldStyle: "margin-bottom:12px;" // workaround for presence of combobox field
                            }
                        }
                    },
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Colors',
                    title: 'Color Management',
                    store: 'colorsAll',
                    interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false,
                        columnOverrides: [
                            {
                                dataIndex: 'objectStatus',
                                flex: 0.1
                            },
                            {
                                dataIndex: 'name',
                                flex: 0.2
                            },
                            {
                                header: 'Color Code',
                                dataIndex: 'code',
                                required: true,
                                editor:{
                                    allowBlank: false
                                },
                                field: {
                                    xtype: 'textfield',
                                    fieldStyle: "margin-bottom:12px;", // workaround for presence of combobox field
                                    allowBlank: false,
                                    maxLength: 10
                                },
                                flex: 0.20,
                                sortOrder: 22
                            },
                            {
                                header: 'Hex Code',
                                dataIndex: 'hexCode',
                                renderer: me.columnRendererUtils.renderHex,
                                required: true,
                                field: {
                                    xtype: 'sspcolorpicker'
                                },
                                flex: 0.20,
                                sortOrder: 26
                            },
                            {
                                dataIndex: 'description',
                                flex: 0.6
                            }
                        ],
                        columnFieldOverrides: {
                            objectStatus: {
                                fieldStyle: "margin-bottom:12px;" // workaround for presence of combobox field
                            },
                            name: {
                                maxLength: 50,
                                fieldStyle: "margin-bottom:12px;" // workaround for presence of combobox field
                            },
                            description: {
                                maxLength: 150,
                                fieldStyle: "margin-bottom:12px;" // workaround for presence of combobox field
                            }
                        }
                    },
					form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Course Tags',
                    title: 'Course Tag Management',
                    store: 'tags',
                    viewConfig: {
                        markDirty: false
                    },
                    interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false,
                        columnOverrides: [
                            {
                                dataIndex: 'name',
                                flex: 50
                            },
                            {
                                header: 'Tag Code',
                                dataIndex: 'code',
                                required: true,
                                editor: {
                                    allowBlank: false
                                },
                                field: {
                                    xtype: 'textfield',
                                    allowBlank: false,
                                    maxLength: 10
                                },
                                flex: 20,
                                sortOrder: 25
                            }
                        ],
                        columnFieldOverrides: {
                            description: {
                                maxLength: 150
                            }
                        }
                    },
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                // }, {
                //     text: 'Template',
                //     title: 'Template Tool',
                //     leaf: true
                }, {
                    text: (me.textStore.getValueByCode('ssp.label.map-template-tag') ? me.textStore.getValueByCode('ssp.label.map-template-tag') : "Template Tag"),
                    title: (me.textStore.getValueByCode('ssp.label.map-template-tag') ? me.textStore.getValueByCode('ssp.label.map-template-tag') : "Template Tag") + ' Management',
                    store: 'mapTemplateTagsAll',
                    interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false,
                    },
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                },{
                    text: 'Template Management',
                    title: 'Template Manager',
                    store: 'planTemplatesSummary',
                    form: 'maptemplatesadmin',
                    leaf: true,
                    addButtonVisible: false
                }]
            }]
        };
        
        Ext.apply(me, {
            root:  me.applySecurity(items),
            folderSort: true,
            sorters: [{
                property: 'text',
                direction: 'ASC'
            }]
        });
        return me.callParent(arguments);
    },

    applySecurity: function( items ){
        var me=this;

        if (me.authenticatedPerson.hasAccess('REFERENCE_WRITE')) {
            return items;
        } else {
            Ext.Array.each(items, function(item, index) {
                var sspSecureChildren = [];
                Ext.Array.each(item.children, function(child, index){
                    if (me.authenticatedPerson.hasAccess( child.securityRole ) ) {
                        sspSecureChildren.push( child );
                    }
                })
                item.children = sspSecureChildren;
            });
            return items;
        }
    }
});
