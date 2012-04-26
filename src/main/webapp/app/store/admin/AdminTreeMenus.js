Ext.define('Ssp.store.admin.AdminTreeMenus',{
	extend: 'Ext.data.TreeStore',  
    storeId: 'adminTreeMenuStore',
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
							        form: 'EducationalGoals',
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
						text: 'Counseling Reference Guide',
						title: 'Counseling Reference Guide',
						form: '',
						expanded: true,
						children: [
								{
									text: 'Challenges',
									title: 'Challenges',
									form: 'Challenges',
									leaf: true
								}]
					}
                   
        ]

    },

	folderSort: true,
	sorters: [{
	    property: 'text',
	    direction: 'ASC'
	}]

});