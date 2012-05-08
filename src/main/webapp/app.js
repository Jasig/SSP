Ext.Loader.setConfig({
	enabled: true,
	paths: {
		'Ssp': './app'
	}
});

Ext.require([
	'Ssp.model.Person',
	'Ssp.model.tool.studentintake.StudentIntakeForm',
	'Ssp.model.tool.studentintake.PersonDemographics',
	'Ssp.model.tool.studentintake.PersonEducationGoal',
	'Ssp.model.tool.studentintake.PersonEducationPlan',
	'Ssp.model.tool.actionplan.Task',
	'Ssp.model.reference.AbstractReference',
	'Ssp.model.reference.Challenge',
	'Ssp.model.reference.ConfidentialityLevel',
	'Ssp.model.reference.ConfidentialityDisclosureAgreement',
	'Ssp.mixin.ApiProperties',
	'Ssp.util.FormRendererUtils',
	'Ssp.util.ColumnRendererUtils',
    'Ssp.store.Tasks',
	'Ssp.store.reference.AbstractReferences',
	'Ssp.store.admin.AdminTreeMenus',
	'Ssp.store.reference.Campuses',
	'Ssp.store.reference.Challenges',
	'Ssp.store.reference.ChallengeCategories',
	'Ssp.store.reference.ChallengeReferrals',
    'Ssp.store.reference.ChildCareArrangements',
    'Ssp.store.reference.Citizenships',
	'Ssp.store.reference.ConfidentialityLevels',
    'Ssp.store.reference.EmploymentShifts',
    'Ssp.store.reference.Ethnicities',
    'Ssp.store.reference.FundingSources',
    'Ssp.store.reference.Genders',
    'Ssp.store.reference.MaritalStatuses',
    'Ssp.store.reference.States', 
    'Ssp.store.Students',
    'Ssp.store.reference.StudentStatuses',
    'Ssp.store.Tools',
    'Ssp.store.reference.VeteranStatuses',
    'Ssp.store.reference.YesNo',
	'Ext.tab.*',
	'Ext.ux.CheckColumn',
	'Ssp.controller.ApplicationEventsController'
]);

Ext.onReady(function(){
	Deft.Injector.configure({
	    currentPerson: {
	        fn: function(){
	            return new Ssp.model.Person({id:"0"});
	        },
	        singleton: true
	    },
	    apiProperties: {
	        fn: function(){
	            return new Ssp.mixin.ApiProperties({});
	        },
	        singleton: true
	    },
	    formRendererUtils:{
	        fn: function(){
	            return new Ssp.util.FormRendererUtils({});
	    	},
	        singleton: true
	    },
	    columnRendererUtils:{
	        fn: function(){
	            return new Ssp.util.ColumnRendererUtils({});
	    	},
	        singleton: true
	    },
        appEventsController:{
	        fn: function(){
	            return new Ssp.controller.ApplicationEventsController({});
	    	},
	        singleton: true
        },
	    tasksStore: 'Ssp.store.Tasks',
		abstractReferencesStore: 'Ssp.store.reference.AbstractReferences',
	    adminTreeMenusStore: 'Ssp.store.admin.AdminTreeMenus',
		campusesStore: 'Ssp.store.reference.Campuses',
	    challengesStore: 'Ssp.store.reference.Challenges',
		challengeCategoriesStore: 'Ssp.store.reference.ChallengeCategories',
		challengeReferralsStore: 'Ssp.store.reference.ChallengeReferrals',
	    childCareArrangementsStore: 'Ssp.store.reference.ChildCareArrangements',
	    citizenshipsStore: 'Ssp.store.reference.Citizenships',
    	confidentialityDisclosureAgreementsStore: 'Ssp.store.reference.ConfidentialityDisclosureAgreements',		
	    confidentialityLevelsStore: 'Ssp.store.reference.ConfidentialityLevels',
	    educationGoalsStore: 'Ssp.store.reference.EducationGoals',
    	educationLevelsStore: 'Ssp.store.reference.EducationLevels',
    	employmentShiftsStore: 'Ssp.store.reference.EmploymentShifts',
    	ethnicitiesStore: 'Ssp.store.reference.Ethnicities',
    	fundingSourcesStore: 'Ssp.store.reference.FundingSources',
    	gendersStore: 'Ssp.store.reference.Genders',
    	maritalStatusesStore: 'Ssp.store.reference.MaritalStatuses',
	    statesStore: 'Ssp.store.reference.States',
	    studentsStore: 'Ssp.store.Students',
	    studentStatusesStore: 'Ssp.store.reference.StudentStatuses',
	    toolsStore: 'Ssp.store.Tools',
    	veteranStatusesStore: 'Ssp.store.reference.VeteranStatuses',
        yesNoStore: 'Ssp.store.reference.YesNo' 
	});

	Ext.application({
	    name: 'Ssp',
	    appFolder: 'app',
		autoCreateViewport: true,
	    launch: function( app ) {
	    	//console.log('Application->Launch');
	    	//console.log(Deft);
	    	Deft.Injector.providers.appEventsController.value.config.app=this;
	    	Deft.Injector.providers.appEventsController.value.app=this;
	    	// Display the application
	        //this.getController('MainViewController').displayApplication();
	   }
	});	
});