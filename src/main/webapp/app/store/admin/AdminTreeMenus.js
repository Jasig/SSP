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
                expanded: false,
                children: [{
                    text: 'Disability Accommodations',
                    title: 'Disability Accommodations',
                    store: 'disabilityAccommodationsAll',
					interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false                  
                    }, 
                    form: 'AbstractReferenceAdmin',
                    leaf: true,
                    columns: [
					{
                        header: 'Active',
                        required: true,
                        dataIndex: 'active',
						defaultValue: true,
                        renderer: me.columnRendererUtils.renderActive,
                        flex: .10,
                        field: {
                            xtype: 'checkbox'
                        }
                    },{
                        header: 'Name',
                        dataIndex: 'name',
						editor:{
							allowBlank: false
						},
                        field: {
                            xtype: 'textfield',
							allowBlank: false
                        },
                        flex: 1
                    }, {
                        header: 'Additional Desc',
                        required: true,
                        dataIndex: 'useDescription',
                        flex: .2,
                        renderer: me.columnRendererUtils.renderFriendlyBoolean,
                        field: {
                            xtype: 'checkbox'
                        }
                    }, {
                        header: 'Desc Label',
                        required: false,
                        dataIndex: 'descriptionFieldLabel',
                        field: {
                            xtype: 'textfield'
                        },
                        flex: .2
                    }, {
                        header: 'Long/Short Desc',
                        required: false,
                        dataIndex: 'descriptionFieldType',
                        field: {
                            xtype: 'textfield'
                        },
                        flex: .2
                    }]
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
                        deleteButtonVisible: false                  
                    },                    
					form: 'AbstractReferenceAdmin',
                    leaf: true,
                    columns: [{
					    header: 'Active',
                        required: true,
                        dataIndex: 'active',
                        defaultValue: true,
                        flex: .2,
                        renderer: me.columnRendererUtils.renderActive,
                        field: {
                            xtype: 'checkbox',
                            fieldStyle: "margin-bottom:12px;"
                        }
					},{					
                        header: 'Name',
                        dataIndex: 'name',
                        required: true,
                        editor:{
							allowBlank: false
						},
                        field: {
                            xtype: 'textfield',
							allowBlank: false
                        },
                        flex: .25
                    }, {
                        header: 'Description',
                        required: false,
                        dataIndex: 'description',
                        field: {
                            xtype: 'textfield'
                        },
                        flex: 1
                    }, {
                        header: 'Require Initial Appointment',
                        required: true,
                        dataIndex: 'requireInitialAppointment',
                        flex: .25,
                        renderer: me.columnRendererUtils.renderFriendlyBoolean,
                        field: {
                            xtype: 'checkbox'
                        }
                    }, {
						header: 'Student Type Code',
						required: true,
						dataIndex: 'code',
						editor:{
							allowBlank: false
						},
                        field: {
                            xtype: 'textfield',
							allowBlank: false
                        },
						flex: .25						
					}]
                }, {
                    text: 'Re-Assignment',
                    title: 'Re-Assignment',
                    store: 'Re-Assignment',
                    form: 'caseloadreassignment',
                    leaf: true
                } ]
            }, {
                text: 'Student Intake',
                title: 'Student Intake',
                form: '',
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
                    form: 'ConfidentialityDisclosureAgreementAdmin',
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
                        deleteButtonVisible: false                  
                    },                    
					form: 'AbstractReferenceAdmin',
                    leaf: true,
                    columns: [{
					    header: 'Active',
                        required: true,
                        dataIndex: 'active',
                        defaultValue: true,
                        flex: .2,
                        renderer: me.columnRendererUtils.renderActive,
                        field: {
                            xtype: 'checkbox',
                            fieldStyle: "margin-bottom:12px;"
                        }
					},{					
                        header: 'Name',
                        dataIndex: 'name',
                        required: true,
                        editor:{
							allowBlank: false
						},
                        field: {
                            xtype: 'textfield',
							allowBlank: false
                        },
                        flex: .25
                    }, {
                        header: 'Description',
                        required: false,
                        dataIndex: 'description',
                        field: {
                            xtype: 'textfield'
                        },
                        flex: 1                 
                    }, {
						header: 'Race Code',
						required: true,
						dataIndex: 'code',
						editor:{
							allowBlank: false
						},
                        field: {
                            xtype: 'textfield',
							allowBlank: false
                        },
						flex: .25						
					}]
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
            }, {
                text: 'Counseling Reference Guide',
                title: 'Counseling Reference Guide',
                form: '',
                expanded: false,
                children: [{
                    text: 'Categories',
                    title: 'Categories',
                    store: 'challengeCategories',
					interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false                  
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
                expanded: false,
                children: [{
                    text: 'Confidentiality Levels',
                    title: 'Confidentiality Levels',
                    store: 'confidentialityLevelsAll',
					interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false                  
                    }, 
                    form: 'AbstractReferenceAdmin',
                    leaf: true,
                    columns: [
					{
                        header: 'Active',
                        required: true,
                        dataIndex: 'active',
						defaultValue: true,
                        renderer: me.columnRendererUtils.renderActive,
                        flex: .10,
                        field: {
                            xtype: 'checkbox'
                        }
                    },
					{
                        header: 'Name',
                        dataIndex: 'name',
                        required: true,
                        flex: .20,
                        editor:{
							allowBlank: false
						},
                        field: {
                            xtype: 'textfield',
							allowBlank: false
                        }
                    }, {
                        header: 'Description',
                        dataIndex: 'description',
                        flex: .30,
                        field: {
                            xtype: 'textfield'
                        }
                    }, {
                        header: 'Acronym',
                        dataIndex: 'acronym',
                        required: true,
                        flex: .10,
                        editor:{
							allowBlank: false
						},
                        field: {
                            xtype: 'textfield',
							allowBlank: false
                        }},
                        {
                            header: 'Data Permission',
                            dataIndex: 'dataPermission',
                            required: true,                        
                            field: {
                                xtype: 'combo',
                                store: me.confidentialityLevelOptionsStore,
                                displayField: 'name',
                                typeAhead: true,
                                mode:'local',
                                queryMode:'local',
                                forceSelection: false,
                                associativeField: 'dataPermission'
                            },
                            flex: .3
                        }]
                }, {
                    text: 'OAuth2 Clients',
                    title: 'OAuth2 Clients',
                    store: 'oauth2Clients',
                    form: 'oauth2clientadmin',
                    leaf: true
                }]
            }, {
                text: 'Early Alert',
                title: 'Early Alert',
                form: '',
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
                        deleteButtonVisible: false                  
                    }, 
                    columns: [
					{
                        header: 'Active',
                        required: true,
                        dataIndex: 'active',
						defaultValue: true,
                        renderer: me.columnRendererUtils.renderActive,
                        flex: .10,
                        field: {
                            xtype: 'checkbox'
                        }
                    },{
                        header: 'Name',
                        dataIndex: 'name',
                        required: true,
                        editor:{
							allowBlank: false
						},
                        field: {
                            xtype: 'textfield',
							allowBlank: false
                        },
                        flex: 50
                    }, {
                        header: 'Description',
                        required: false,
                        dataIndex: 'description',
                        flex: 80,
                        field: {
                            xtype: 'textfield'
                        },
                    }, {
                        header: 'Acronym',
                        required: true,
                        dataIndex: 'acronym',
                        flex: 10,
                        editor:{
							allowBlank: false
						},
                        field: {
                            xtype: 'textfield',
							allowBlank: false
                        }
                     }, {
                         header: 'Referral Email',
                         required: false,
                         dataIndex: 'recipientEmailAddress',
                         renderer: me.columnRendererUtils.renderValidEmail,
                         flex: 25,
                         field: {
                             xtype: 'textfield',
                             vtype: 'email'
                         }
                      }, {
                          header: 'Carbon Copy(s)',
                          required: false,
                          dataIndex: 'carbonCopy',
                          renderer: me.columnRendererUtils.renderValidEmail,
                          flex: 50,
                          field: {
                              xtype: 'textfield',
                              vtype: 'multiemail'
                          }
                    }]
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
                        deleteButtonVisible: false
                    },
                    form: 'AbstractReferenceAdmin',
                    leaf: true,
                    columns: [{
                        header: 'Active',
                        required: true,
                        dataIndex: 'active',
                        defaultValue: true,
                        renderer: me.columnRendererUtils.renderActive,
                        flex: .20,
                        field: {
                            xtype: 'checkbox'
                        }
                    }, {
                        header: 'Name',
                        dataIndex: 'name',
                        required: true,
                        editor:{
							allowBlank: false
						},
                        field: {
                            xtype: 'textfield',
							allowBlank: false
                        },
                        flex: .20
                    }, {
                        header: 'Code',
                        dataIndex: 'code',
                        required: true,
                        editor:{
							allowBlank: false
						},
                        field: {
                            xtype: 'textfield',
							allowBlank: false
                        },
                        flex: .20
                    }, {
                        header: 'Description',
                        dataIndex: 'description',
                        field: {
                            xtype: 'textfield'
                        },
                        flex: 1
                    }]                    
                }, {
                    text: 'Message Templates',
                    title: 'Message Templates',
                    store: 'messageTemplates',
                    form: 'messagetemplatesadmin',
                    leaf: true
                }
                	,
                    {
                    text: 'Text',
                    title: 'Text',
                    store: 'text',
					interfaceOptions: {
                        addButtonVisible: false,
                        deleteButtonVisible: false,
                        hasPagingToolbar: false
                    },
                    form: 'AbstractReferenceAdmin',
                    leaf: true,
                    columns: [
					{
                        header: 'Name',
                        dataIndex: 'name',
                        required: true,
                        rowEditable: false,
                        flex: .10,
                        editor:{
							allowBlank: false
						},
                        field: {
                            xtype: 'textfield',
							allowBlank: false
                        }
                    }, {
                        header: 'Value',
                        dataIndex: 'value',
                        flex: .10,
                        field: {
                            xtype: 'textfield'
                        }
                    },  {
                        header: 'Code',
                        rowEditable: false,
                        dataIndex: 'code',
                        filterable: true,
                        flex: .20,
                        field: {
                            xtype: 'textfield'
                        }
                    }, {
                        header: 'Description',
                        dataIndex: 'description',
                        flex: .20,
                        field: {
                            xtype: 'textfield'
                        }
                    }]
                }	]
            }	, {text: 'Main Tool',
	            title: 'Main Tool',
	            form: '',
	            expanded: false,
	            children: [{
					text: 'SAP Statuses',
	                title: 'SAP Statuses',
	                store: 'sapStatusesAll',
					interfaceOptions: {
	                    addButtonVisible: true,
	                    deleteButtonVisible: false                  
	                },                    
					form: 'AbstractReferenceAdmin',
	                leaf: true,
	                columns: [{
					    header: 'Active',
	                    required: true,
	                    dataIndex: 'active',
	                    defaultValue: true,
	                    flex: .2,
	                    renderer: me.columnRendererUtils.renderActive,
	                    field: {
	                        xtype: 'checkbox',
	                        fieldStyle: "margin-bottom:12px;"
	                    }
					},{					
	                    header: 'Name',
	                    dataIndex: 'name',
	                    required: true,
	                    editor:{
							allowBlank: false
						},
                        field: {
                            xtype: 'textfield',
							allowBlank: false
                        },
	                    flex: .25
	                }, {
	                    header: 'Description',
	                    required: false,
	                    dataIndex: 'description',
	                    field: {
	                        xtype: 'textfield'
	                    },
	                    flex: 1                 
	                }, {
						header: 'SAP Code',
						required: true,
						dataIndex: 'code',
						editor:{
							allowBlank: false
						},
                        field: {
                            xtype: 'textfield',
							allowBlank: false
                        },
						flex: .25						
					}]
	            }, {
					text: 'Financial Aid Files',
	                title: 'Financial Aid Files',
	                store: 'financialAidFilesAll',
					interfaceOptions: {
	                    addButtonVisible: true,
	                    deleteButtonVisible: false                  
	                },                    
					form: 'AbstractReferenceAdmin',
	                leaf: true,
	                columns: [{
					    header: 'Active',
	                    required: true,
	                    dataIndex: 'active',
	                    defaultValue: true,
	                    flex: .2,
	                    renderer: me.columnRendererUtils.renderActive,
	                    field: {
	                        xtype: 'checkbox',
	                        fieldStyle: "margin-bottom:12px;"
	                    }
					},{					
	                    header: 'Name',
	                    dataIndex: 'name',
	                    required: true,
	                    editor:{
							allowBlank: false
						},
                        field: {
                            xtype: 'textfield',
							allowBlank: false
                        },
	                    flex: .25
	                }, {
	                    header: 'Description',
	                    required: false,
	                    dataIndex: 'description',
	                    field: {
	                        xtype: 'textfield'
	                    },
	                    flex: 1                 
	                }, {
						header: 'File Code',
						required: true,
						dataIndex: 'code',
						editor:{
							allowBlank: false
						},
                        field: {
                            xtype: 'textfield',
							allowBlank: false
                        },
						flex: .25						
					}]
	            }]
	        },{
                text: 'MAP',
                title: 'MAP',
                form: '',
                expanded: false,
                children: [{
                    text: 'Electives',
                    title: 'Electives',
                    store: 'electivesAll',
                    sort: {
                    	field: 'sortOrder',
                    	direction: 'ASC' //or 'DESC'
                    },
                    viewConfig: {
                        markDirty: false
                    },
                    interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false,
                        dragAndDropReorder: true,
                        headerInstructions: "Double-click to edit and drag to re-order items.",
                        storeDependencies: [{
                            name: "colors",
                            store: me.colorsAllUnpagedStore,
                            clearFilter: true
                        }]
                    },                    
                    form: 'AbstractReferenceAdmin',
                    leaf: true,
                    columns: [{
                        header: 'Order',
                        dataIndex: 'sortOrder',
                        required: true,
						editor:{
							allowBlank: false
						},
                        field: {
                            xtype: 'textfield',
							 fieldStyle: "margin-bottom:12px;",
							allowBlank: false
                        },
                        flex: .1
                    }, {
                        header: 'Active',
                        required: true,
                        dataIndex: 'active',
                        defaultValue: true,
                        flex: .2,
                        renderer: me.columnRendererUtils.renderActive,
                        field: {
                            xtype: 'checkbox',
                            fieldStyle: "margin-bottom:12px;"
                        }
                    }, {
                        header: 'Elective Name',
                        dataIndex: 'name',
                        required: true,
                        defaultValue: "",
                        	editor:{
								allowBlank: false
							},
	                        field: {
	                            xtype: 'textfield',
								 fieldStyle: "margin-bottom:12px;",
								allowBlank: false
	                        },
                        flex: .2
                    }, {
                        header: 'Elective Code',
                        dataIndex: 'code',
                        defaultValue: "",
                        required: true,
                        	editor:{
								allowBlank: false
							},
	                        field: {
	                            xtype: 'textfield',
								 fieldStyle: "margin-bottom:12px;",
								allowBlank: false
	                        },
                        flex: .2
                    }, {
                        header: 'Description',
                        dataIndex: 'description',
                        defaultValue: "",
                        field: {
                            xtype: 'textfield',
                            fieldStyle: "margin-bottom:12px;"
                        },
                        flex: 1
                    }, {
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
                        flex: .2
                    }]
                }, {
                    text: 'Colors',
                    title: 'Color Management',
                    store: 'colorsAll',
					interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false                    
                    },       
					form: 'AbstractReferenceAdmin',
                    leaf: true,
                    columns: [{
                        header: 'Active',
                        required: true,
                        dataIndex: 'active',
                        defaultValue: true,
                        renderer: me.columnRendererUtils.renderActive,
                        flex: .20,
                        field: {
                            xtype: 'checkbox',
                            fieldStyle: "margin-bottom:12px;"
                        }
                    }, {
                        header: 'Color Name',
                        dataIndex: 'name',
                        required: true,
                        	editor:{
								allowBlank: false
							},
	                        field: {
	                            xtype: 'textfield',
								 fieldStyle: "margin-bottom:12px;",
								allowBlank: false
	                        },
                        flex: .20
                    }, {
                        header: 'Color Code',
                        dataIndex: 'code',
                        required: true,
                        	editor:{
								allowBlank: false
							},
	                        field: {
	                            xtype: 'textfield',
								 fieldStyle: "margin-bottom:12px;",
								allowBlank: false
	                        },
                        flex: .20
                    }, {
                        header: 'Hex Code',
                        dataIndex: 'hexCode',
                        renderer: me.columnRendererUtils.renderHex,
                        required: true,
                        field: {
                            xtype: 'sspcolorpicker'
                        },
                        flex: .20
                    }, {
                        header: 'Description',
                        dataIndex: 'description',
                        field: {
                            xtype: 'textfield',
                            fieldStyle: "margin-bottom:12px;"
                        },
                        flex: 1
                    }]
                }, {
                    text: 'Tags',
                    title: 'Tag Management',
                    store: 'tags',
                    viewConfig: {
                        markDirty: false
                    },
                    interfaceOptions: {
                        addButtonVisible: true,
                        deleteButtonVisible: false
                    },
                    form: 'AbstractReferenceAdmin',
                    leaf: true,
                    columns: [{
                        header: 'Active',
                        required: true,
                        dataIndex: 'active',
                        defaultValue: true,
                        renderer: me.columnRendererUtils.renderActive,
                        flex: .20,
                        field: {
                            xtype: 'checkbox'
                        }
                    }, {
                        header: 'Tag Name',
                        dataIndex: 'name',
                        required: true,
                        	editor:{
								allowBlank: false
							},
	                        field: {
	                            xtype: 'textfield',
								allowBlank: false
	                        },
                        flex: .20
                    }, {
                        header: 'Tag Code',
                        dataIndex: 'code',
                        required: true,
                        	editor:{
								allowBlank: false
							},
	                        field: {
	                            xtype: 'textfield',
								allowBlank: false
	                        },
                        flex: .20
                    }, {
                        header: 'Description',
                        dataIndex: 'description',
                        field: {
                            xtype: 'textfield'
                        },
                        flex: 1
                    }]
                }]
            }]
        };
        
        Ext.apply(me, {
            root: items,
            folderSort: true,
            sorters: [{
                property: 'text',
                direction: 'ASC'
            }]
        });
        return me.callParent(arguments);
    }
});