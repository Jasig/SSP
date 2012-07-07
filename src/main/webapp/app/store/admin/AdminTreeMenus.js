Ext.define('Ssp.store.admin.AdminTreeMenus',{
	extend: 'Ext.data.TreeStore',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        columnRendererUtils: 'columnRendererUtils'
    },
	autoLoad: false,
    constructor: function(){
    	var items = {
    	    	text: 'Administrative Tools',
    	    	title: 'Administrative Tools',
    	    	form: '',
    	        expanded: true,
    	        children: [ {
    	        	            text: 'Beta',
    	        	            title: 'Beta',
    	        	            form: '',
    	        	            expanded: false,
    	        	            children: [{
							    	text: 'Campuses',
									title: 'Campuses',
									store: '',
									form: 'CampusAdmin',
									leaf: true
								}]
    	                    },{
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
    	                    },
    						{
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
    								    },{
    										text: 'Special Service Groups',
    										title: 'Special Service Groups',
    										store: 'specialServiceGroups',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    									},
    									{
    										text: 'Service Reasons',
    										title: 'Service Reasons',
    										store: 'serviceReasons',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    								    },{
    										text: 'Student Types',
    										title: 'Student Types',
    										store: 'studentTypes',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true,
    										columns: [
    						    		                { header: 'Name',  
    						    		                  dataIndex: 'name',
    						    		                  required: true,
    						    		                  field: {
    						    		                      xtype: 'textfield'
    						    		                  },
    						    		                  flex: .25 },
    						    		                { header: 'Description',
    						    		                  required: false,
    						    		                  dataIndex: 'description',
    						    		                  field: {
    						    		                      xtype: 'textfield'
    						    		                  },
    						    		                  flex: 1},
    						      		                { header: 'Require Initial Appointment',
    						    		                  required: true,
    						      		                  dataIndex: 'requireInitialAppointment', 
    						      		                  flex: .25,
    						      		                  renderer: this.columnRendererUtils.renderFriendlyBoolean,
    						      		                  field: {
    						      		                      xtype: 'checkbox'
    						      		                  }
    						    		                }
    						    		           ]
    									}
    							]
    						},{
    							text: 'Student Intake',
    							title: 'Student Intake',
    							form: '',
    							expanded: false,
    							children: [
    									{
    										text: 'Child Care Arrangements',
    										title: 'Child Care Arrangements',
    										store: 'childCareArrangements',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    									},
    									{
    										text: 'Citizenships',
    										title: 'Citizenships',
    										store: 'citizenships',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    								    },
    								    {
    								    	text: 'Confidentiality Disclosure Agreement',
    								    	title: 'Confidentiality Disclosure Agreement',
    								    	store: '',
    								        form: 'ConfidentialityDisclosureAgreementAdmin',
    										leaf: true
    								    },
    								    {
    								    	text: 'Education Goals',
    								    	title: 'Education Goals',
    								    	store: 'educationGoals',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    								    }
    								    ,{
    								    	text: 'Education Levels',
    								    	title: 'Education Levels',
    								    	store: 'educationLevels',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    								    }
    								    ,{
    								    	text: 'Ethnicities',
    								    	title: 'Ethnicities',
    								    	store: 'ethnicities',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    								    }
    								    ,{
    								    	text: 'Funding Sources',
    								    	title: 'Funding Sources',
    								    	store: 'fundingSources',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    								    }
    								    ,{
    								    	text: 'Marital Statuses',
    								    	title: 'Marital Statuses',
    								    	store: 'maritalStatuses',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    								    }
    								    ,{
    								    	text: 'Student Statuses',
    								    	title: 'Student Statuses',
    								    	store: 'studentStatuses',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    								    }
    								    ,{
    								    	text: 'Veteran Statuses',
    								    	title: 'Veteran Statuses',
    								    	store: 'veteranStatuses',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    								    }
    							]
    						},{
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
										},{
    										text: 'Challenges',
    										title: 'Challenges',
    										store: '',
    										form: 'ChallengeAdmin',
    										leaf: true
    									},{
    										text: 'Referrals',
    										title: 'Referrals',
    										store: '',
    										form: 'ChallengeReferralAdmin',
    										leaf: true
    									}]
    						},{
    							text: 'Security',
    							title: 'Security',
    							form: '',
    							expanded: false,
    							children: [{text: 'Confidentiality Levels',
    										title: 'Confidentiality Levels',
    										store: 'confidentialityLevels',
    										form: 'AbstractReferenceAdmin',
    										leaf: true,
    										columns: [{ header: 'Name',  
    						    		                  dataIndex: 'name',
    						    		                  required: true,
    						    		                  field: {
    						    		                      xtype: 'textfield'
    						    		                  },
    						    		                  flex: 50 },
    						    		                { header: 'Description',
    						    		                  dataIndex: 'description', 
    						    		                  flex: 50,
    						    		                  field: {
    						    		                      xtype: 'textfield'
    						    		                  },
    						    		                  flex: 50 },
    						      		                { header: 'Acronym',
    						      		                  dataIndex: 'acronym',
    						      		                  required: true,
    						      		                  flex: 50,
    						      		                  field: {
    						      		                      xtype: 'textfield'
    						      		                  }
    						    		                }]
    									}]
    						},{
    							text: 'Early Alert',
    							title: 'Early Alert',
    							form: '',
    							expanded: false,
    							children: [{
									text: 'Outcomes',
									title: 'Outcomes',
									store: 'earlyAlertOutcomes',
							        form: 'AbstractReferenceAdmin',
									leaf: true
								},{
									text: 'Outreaches',
									title: 'Outreaches',
									store: 'earlyAlertOutreaches',
							        form: 'AbstractReferenceAdmin',
									leaf: true
								},{
									text: 'Reasons',
									title: 'Reasons',
									store: 'earlyAlertReasons',
							        form: 'AbstractReferenceAdmin',
									leaf: true
								},{
									text: 'Referrals',
									title: 'Referrals',
									store: 'earlyAlertReferrals',
							        form: 'AbstractReferenceAdmin',
									leaf: true,
									columns: [
					    		                { header: 'Name',  
					    		                  dataIndex: 'name',
					    		                  required: true,
					    		                  field: {
					    		                      xtype: 'textfield'
					    		                  },
					    		                  flex: 50 },
					    		                { header: 'Description',
					    		                  required: false,
					    		                  dataIndex: 'description', 
					    		                  flex: 50,
					    		                  field: {
					    		                      xtype: 'textfield'
					    		                  },
					    		                  flex: 50 },
					      		                { header: 'Acronym',
					    		                  required: true,
					      		                  dataIndex: 'acronym', 
					      		                  flex: 50,
					      		                  field: {
					      		                      xtype: 'textfield'
					      		                  }
					    		                }
					    		           ]
								},{
									text: 'Suggestions',
									title: 'Suggestions',
									store: 'earlyAlertSuggestions',
							        form: 'AbstractReferenceAdmin',
									leaf: true
								}]
    						},{
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
								},{
									text: 'Details',
									title: 'Details',
									store: '',
							        form: 'JournalStepDetailAdmin',
									leaf: true
								},{
									text: 'Steps',
									title: 'Steps',
									store: '',
							        form: 'JournalStepAdmin',
									leaf: true
								},{
									text: 'Tracks',
									title: 'Tracks',
									store: 'journalTracks',
							        form: 'AbstractReferenceAdmin',
									leaf: true
								}]
    						}
    	                   
    	        ]

    	    };
    	
    	Ext.apply(this,{
    		root: items,
    		folderSort: true,
    		sorters: [{
    		    property: 'text',
    		    direction: 'ASC'
    		}]
    	});
		return this.callParent(arguments);
    }


});