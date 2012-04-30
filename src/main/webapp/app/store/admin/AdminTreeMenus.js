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
							        form: 'ChildCareArrangements',
									leaf: true
								},
								{
									text: 'Citizenships',
									title: 'Citizenships',
							        form: 'Citizenships',
									leaf: true
							    }
							    ,{
							    	text: 'Education Goals',
							    	title: 'Education Goals',
							        form: 'EducationGoals',
									leaf: true
							    }
							    ,{
							    	text: 'Education Levels',
							    	title: 'Education Levels',
							        form: 'EducationLevels',
									leaf: true
							    }
							    ,{
							    	text: 'Ethnicities',
							    	title: 'Ethnicities',
							        form: 'Ethnicities',
									leaf: true
							    }
							    ,{
							    	text: 'Funding Sources',
							    	title: 'Funding Sources',
							        form: 'FundingSources',
									leaf: true
							    }
							    ,{
							    	text: 'Marital Statuses',
							    	title: 'Marital Statuses',
							        form: 'MaritalStatuses',
									leaf: true
							    }
							    ,{
							    	text: 'Student Statuses',
							    	title: 'Student Statuses',
							        form: 'StudentStatuses',
									leaf: true
							    }
							    ,{
							    	text: 'Veteran Statuses',
							    	title: 'Veteran Statuses',
							        form: 'VeteranStatuses',
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
									form: 'Categories',
									leaf: true
								},
								{
									text: 'Challenges',
									title: 'Challenges',
									form: 'Challenges',
									leaf: true
								},{
									text: 'Referrals',
									title: 'Referrals',
									form: 'Referrals',
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
									form: 'ConfidentialityLevels',
									leaf: true }]
					},{
						text: 'Campus',
						title: 'Campus',
						form: '',
						expanded: false,
						children: [{text: 'Campuses',
									title: 'Campuses',
									form: 'Campuses',
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