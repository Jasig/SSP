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
        columnRendererUtils: 'columnRendererUtils'
    },
    autoLoad: false,
    constructor: function(){
        var me = this;
        var items = {
            text: 'Administrative Tools',
            title: 'Administrative Tools',
            form: '',
            expanded: true,
            children: [{
                text: 'Student Success',
                title: 'Student Success',
                form: '',
                expanded: false,
                children: [{
                    text: 'Campus Services',
                    title: 'Campus Services',
                    store: 'campusServices',
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'LASSI Skill Components',
                    title: 'LASSI Skill Components',
                    store: 'lassis',
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Personality Types',
                    title: 'Personality Types',
                    store: 'personalityTypes',
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }]
            }, {
                text: 'Accommodation',
                title: 'Accommodation',
                form: '',
                expanded: false,
                children: [{
                    text: 'Disability Accommodations',
                    title: 'Disability Accommodations',
                    store: 'disabilityAccommodations',
                    form: 'AbstractReferenceAdmin',
                    leaf: true,
                    columns: [{
                        header: 'Name',
                        dataIndex: 'name',
                        required: true,
                        field: {
                            xtype: 'textfield'
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
                    store: 'disabilityAgencies',
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Disability Statuses',
                    title: 'Disability Statuses',
                    store: 'disabilityStatuses',
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Disability Types',
                    title: 'Disability Types',
                    store: 'disabilityTypes',
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }]
            }, {
                text: 'Caseload',
                title: 'Caseload',
                form: '',
                expanded: false,
                children: [{
                    text: 'Program Status Change Reasons',
                    title: 'Program Status Change Reasons',
                    store: 'programStatusChangeReasons',
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }]
            }, {
                text: 'Caseload Assignment',
                title: 'Caseload Assignment',
                form: '',
                expanded: false,
                children: [{
                    text: 'Referral Sources',
                    title: 'Referral Sources',
                    store: 'referralSources',
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Special Service Groups',
                    title: 'Special Service Groups',
                    store: 'specialServiceGroups',
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Service Reasons',
                    title: 'Service Reasons',
                    store: 'serviceReasons',
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Student Types',
                    title: 'Student Types',
                    store: 'studentTypes',
                    form: 'AbstractReferenceAdmin',
                    leaf: true,
                    columns: [{
                        header: 'Name',
                        dataIndex: 'name',
                        required: true,
                        field: {
                            xtype: 'textfield'
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
                    }]
                }, {
                    text: 'Re-Assignment',
                    title: 'Re-Assignment',
                    store: 'Re-Assignment',
                    form: 'caseloadreassignment',
                    leaf: true
                }, ]
            }, {
                text: 'Student Intake',
                title: 'Student Intake',
                form: '',
                expanded: false,
                children: [{
                    text: 'Child Care Arrangements',
                    title: 'Child Care Arrangements',
                    store: 'childCareArrangements',
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Citizenships',
                    title: 'Citizenships',
                    store: 'citizenships',
                    form: 'AbstractReferenceAdmin',
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
                    store: 'educationGoals',
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Education Levels',
                    title: 'Education Levels',
                    store: 'educationLevels',
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Ethnicities',
                    title: 'Ethnicities',
                    store: 'ethnicities',
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Funding Sources',
                    title: 'Funding Sources',
                    store: 'fundingSources',
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Marital Statuses',
                    title: 'Marital Statuses',
                    store: 'maritalStatuses',
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Military Affiliations',
                    title: 'Military Affiliations',
                    store: 'militaryAffiliations',
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Student Statuses',
                    title: 'Student Statuses',
                    store: 'studentStatuses',
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Veteran Statuses',
                    title: 'Veteran Statuses',
                    store: 'veteranStatuses',
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
                    store: 'confidentialityLevels',
                    form: 'AbstractReferenceAdmin',
                    leaf: true,
                    columns: [{
                        header: 'Name',
                        dataIndex: 'name',
                        required: true,
                        field: {
                            xtype: 'textfield'
                        },
                        flex: 50
                    }, {
                        header: 'Description',
                        dataIndex: 'description',
                        flex: 50,
                        field: {
                            xtype: 'textfield'
                        },
                        flex: 50
                    }, {
                        header: 'Acronym',
                        dataIndex: 'acronym',
                        required: true,
                        flex: 50,
                        field: {
                            xtype: 'textfield'
                        }
                    }]
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
                    store: 'earlyAlertOutcomes',
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Outreaches',
                    title: 'Outreaches',
                    store: 'earlyAlertOutreaches',
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Reasons',
                    title: 'Reasons',
                    store: 'earlyAlertReasons',
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }, {
                    text: 'Referrals',
                    title: 'Referrals',
                    store: 'earlyAlertReferrals',
                    form: 'AbstractReferenceAdmin',
                    leaf: true,
                    columns: [{
                        header: 'Name',
                        dataIndex: 'name',
                        required: true,
                        field: {
                            xtype: 'textfield'
                        },
                        flex: 50
                    }, {
                        header: 'Description',
                        required: false,
                        dataIndex: 'description',
                        flex: 50,
                        field: {
                            xtype: 'textfield'
                        },
                        flex: 50
                    }, {
                        header: 'Acronym',
                        required: true,
                        dataIndex: 'acronym',
                        flex: 50,
                        field: {
                            xtype: 'textfield'
                        }
                    }]
                }, {
                    text: 'Suggestions',
                    title: 'Suggestions',
                    store: 'earlyAlertSuggestions',
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
                    store: 'journalSources',
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
                    store: 'journalTracks',
                    form: 'AbstractReferenceAdmin',
                    leaf: true
                }]
            }, {
                text: 'MAP',
                title: 'MAP',
                form: '',
                expanded: false,
                children: [{
				    text: 'Colors',
				    title: 'Color Management',
				    store: 'colors',
					//storeLoadOptions: { params: { status: 'ALL' } },
				    form: 'AbstractReferenceAdmin',
				    leaf: true,
				    columns: [
						{
				        header: 'Active',
				        required: true,
				        dataIndex: 'active',
				        renderer: me.columnRendererUtils.renderActive,
				        flex: .20,
				        field: {
				            xtype: 'checkbox',
				            
				        	}
				    	}, {
				        header: 'Color Name',
				        dataIndex: 'name',
				        required: true,
				        field: {
				            xtype: 'textfield'
				        },
				        flex: .20
				    }, {
				        header: 'Color Code',
				        dataIndex: 'code',
				        required: true,
				        field: {
				            xtype: 'textfield'
				        },
				        flex: .20
				    }, {
				        header: 'Hex Code',
				        dataIndex: 'hexCode',
				        renderer: me.columnRendererUtils.renderHex,
				        required: true,				        
				        editor: Ext.create('Ext.form.TriggerField', {
				            onTriggerClick: function(e) {
				            	var triggerField = this;	
				            	console.log(me);
				            	console.log(triggerField);
				            	var colorPicker = Ext.create('Ext.picker.Color', {
				                    style: {
				                        backgroundColor: "#fff"
				                    } ,	
				                    listeners: {
				                        scope:me,
				                        select: function(picker, selColor) {	                        
				                        	triggerField.setValue(selColor);
				                        	window.close();
				                        }
				                    },
				            	});
				                var window = Ext.create('Ext.window.Window', {
				                        title: 'Select Color',
				                        resizable: false,
				                        draggable: false,
				                        closeAction: 'hide',
				                        width: 150,
				                        height: 135,
				                        border: false,
				                        hidden: true,
				                        layout:'fit',
				                        floating: true,
				                        items: [colorPicker]
				                    });
				                window.show(); 
				            }
				        }),
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
                    text: 'Tags',
                    title: 'Tag Management',
                    store: 'tags',
					storeLoadOptions: { params: { status: 'ALL' } },
                    form: 'AbstractReferenceAdminWithoutDelete',
                    leaf: true,
                    columns: [
						{
                        header: 'Active',
                        required: true,
                        dataIndex: 'active',
                        renderer: me.columnRendererUtils.renderActive,
                        flex: .20,
                        field: {
                            xtype: 'checkbox',
                            
                        	}
                    	}, {
                        header: 'Tag Name',
                        dataIndex: 'name',
                        required: true,
                        field: {
                            xtype: 'textfield'
                        },
                        flex: .20
                    }, {
                        header: 'Tag Code',
                        dataIndex: 'code',
                        required: true,
                        field: {
                            xtype: 'textfield'
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
