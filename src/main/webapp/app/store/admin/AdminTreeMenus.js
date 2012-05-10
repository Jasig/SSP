Ext.define('Ssp.store.admin.AdminTreeMenus',{
	extend: 'Ext.data.TreeStore',
	autoLoad: false,
    constructor: function(){
    	var items= {
    	    	text: 'Administrative Tools',
    	    	title: 'Administrative Tools',
    	    	form: '',
    	        expanded: true,
    	        children: [
    						{
    							text: 'Student Intake',
    							title: 'Student Intake',
    							form: '',
    							expanded: true,
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
    							text: 'Self Help Guide',
    							title: 'Self Help Guide',
    							form: '',
    							expanded: false,
    							children: []
    						},{
    							text: 'Security',
    							title: 'Security',
    							form: '',
    							expanded: false,
    							children: [{text: 'Confidentiality Levels',
    										title: 'Confidentiality Levels',
    										store: 'confidentialityLevels',
    										form: 'ConfidentialityLevelAdmin',
    										leaf: true }]
    						},{
    							text: 'Campus',
    							title: 'Campus',
    							form: '',
    							expanded: false,
    							children: [{text: 'Campuses',
    										title: 'Campuses',
    										store: 'campuses',
    										form: 'AbstractReferenceAdmin',
    										leaf: true }]
    						},{
    							text: 'Early Alert',
    							title: 'Early Alert',
    							form: '',
    							expanded: false,
    							children: [{
									text: 'Early Alert Outcomes',
									title: 'Early Alert Outcomes',
									store: 'earlyAlertOutcomes',
							        form: 'AbstractReferenceAdmin',
									leaf: true
								},{
									text: 'Early Alert Outreaches',
									title: 'Early Alert Outreaches',
									store: 'earlyAlertOutreaches',
							        form: 'AbstractReferenceAdmin',
									leaf: true
								},{
									text: 'Early Alert Reasons',
									title: 'Early Alert Reasons',
									store: 'earlyAlertReasons',
							        form: 'AbstractReferenceAdmin',
									leaf: true
								},{
									text: 'Early Alert Referrals',
									title: 'Early Alert Referrals',
									store: 'earlyAlertReferrals',
							        form: 'EarlyAlertReferralAdmin',
									leaf: true
								},{
									text: 'Early Alert Suggestions',
									title: 'Early Alert Suggestions',
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
									text: 'Journal Sources',
									title: 'Journal Sources',
									store: 'journalSources',
							        form: 'AbstractReferenceAdmin',
									leaf: true
								},{
									text: 'Journal Steps',
									title: 'Journal Steps',
									store: 'journalSteps',
							        form: 'AbstractReferenceAdmin',
									leaf: true
								},{
									text: 'Journal Tracks',
									title: 'Journal Tracks',
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