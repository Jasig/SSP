Ext.define('Ssp.store.admin.AdminTreeMenus',{
	extend: 'Ext.data.TreeStore',
    root: {
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
									store: 'ChildCareArrangements',
							        form: 'AbstractReferenceAdmin',
									leaf: true
								},
								{
									text: 'Citizenships',
									title: 'Citizenships',
									store: 'Citizenships',
							        form: 'AbstractReferenceAdmin',
									leaf: true
							    }
							    ,{
							    	text: 'Education Goals',
							    	title: 'Education Goals',
							    	store: 'EducationGoals',
							        form: 'AbstractReferenceAdmin',
									leaf: true
							    }
							    ,{
							    	text: 'Education Levels',
							    	title: 'Education Levels',
							    	store: 'EducationLevels',
							        form: 'AbstractReferenceAdmin',
									leaf: true
							    }
							    ,{
							    	text: 'Ethnicities',
							    	title: 'Ethnicities',
							    	store: 'Ethnicities',
							        form: 'AbstractReferenceAdmin',
									leaf: true
							    }
							    ,{
							    	text: 'Funding Sources',
							    	title: 'Funding Sources',
							    	store: 'FundingSources',
							        form: 'AbstractReferenceAdmin',
									leaf: true
							    }
							    ,{
							    	text: 'Marital Statuses',
							    	title: 'Marital Statuses',
							    	store: 'MaritalStatuses',
							        form: 'AbstractReferenceAdmin',
									leaf: true
							    }
							    ,{
							    	text: 'Student Statuses',
							    	title: 'Student Statuses',
							    	store: 'StudentStatuses',
							        form: 'AbstractReferenceAdmin',
									leaf: true
							    }
							    ,{
							    	text: 'Veteran Statuses',
							    	title: 'Veteran Statuses',
							    	store: 'VeteranStatuses',
							        form: 'AbstractReferenceAdmin',
									leaf: true
							    }
						]
					},{
						text: 'Action Plan',
						title: 'Action Plan',
						form: '',
						expanded: false,
						children: [{
									text: 'Categories',
									title: 'Categories',
									store: 'Categories',
									form: 'AbstractReferenceAdmin',
									leaf: true
								},
								{
									text: 'Challenges',
									title: 'Challenges',
									store: 'Challenges',
									form: 'ChallengeAdmin',
									leaf: true
								},{
									text: 'Referrals',
									title: 'Referrals',
									store: 'Referrals',
									form: 'AbstractReferenceAdmin',
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
									store: 'ConfidentialityLevels',
									form: 'ConfidentialityLevelAdmin',
									leaf: true }]
					},{
						text: 'Campus',
						title: 'Campus',
						form: '',
						expanded: false,
						children: [{text: 'Campuses',
									title: 'Campuses',
									store: 'Campuses',
									form: 'AbstractReferenceAdmin',
									leaf: true }]
					}
                   
        ]

    },

	folderSort: true,
	sorters: [{
	    property: 'text',
	    direction: 'ASC'
	}]

});