Ext.Loader.setConfig({
	enabled: true,
	paths: {
		'Ssp': './app'
	}
});

Ext.require([
    'Ssp.view.admin.AdminMain',
    'Ssp.view.admin.AdminTreeMenu',
    'Ssp.view.admin.AdminForms',
    'Ssp.view.Main',
    'Ssp.view.Search',
    'Ssp.view.StudentRecord',
    'Ssp.view.ToolsMenu',
    'Ssp.view.Tools',
    'Ssp.view.tools.Profile',
    'Ssp.view.tools.ActionPlan',
    'Ssp.view.tools.actionplan.Tasks',
    'Ssp.view.tools.actionplan.AddTask',
    'Ssp.view.tools.actionplan.AddTaskForm',
    'Ssp.view.tools.actionplan.ActionPlanTasks',
    'Ssp.view.tools.StudentIntake',
    'Ssp.view.tools.studentintake.Challenges',
    'Ssp.view.tools.studentintake.Demographics',
    'Ssp.view.tools.studentintake.EducationGoals',
    'Ssp.view.tools.studentintake.EducationLevels',
    'Ssp.view.tools.studentintake.EducationPlans',
    'Ssp.view.tools.studentintake.Funding',
    'Ssp.view.tools.studentintake.Personal',
    'Ssp.view.tools.Journal',
    'Ssp.view.tools.EarlyAlert',
    'Ssp.view.admin.AdminForms',
    'Ssp.view.admin.forms.AbstractReferenceAdmin',
    
    // COUNSELING REFERENCE GUIDE VIEWS
    'Ssp.view.admin.forms.ChallengeAdmin',
    'Ssp.view.admin.forms.ChallengeReferralAdmin',
    'Ssp.view.admin.forms.ConfidentialityDisclosureAgreementAdmin',
    'Ssp.view.admin.forms.ConfidentialityLevelAdmin',
    'Ssp.view.admin.forms.EarlyAlertReferralAdmin',
    'Ssp.view.admin.forms.crg.DisplayChallengesAdmin',
    'Ssp.view.admin.forms.crg.EditChallenge',
    'Ssp.view.admin.forms.crg.DisplayChallengeCategoriesAdmin',
    'Ssp.view.admin.forms.crg.EditChallengeCategory',
    'Ssp.view.admin.forms.crg.DisplayChallengeReferralsAdmin',
    'Ssp.view.admin.forms.crg.DisplayReferralsAdmin',
    'Ssp.view.admin.forms.crg.EditReferral',

	'Ssp.model.Person',
	'Ssp.model.tool.studentintake.StudentIntakeForm',
	'Ssp.model.tool.studentintake.PersonDemographics',
	'Ssp.model.tool.studentintake.PersonEducationGoal',
	'Ssp.model.tool.studentintake.PersonEducationPlan',
	'Ssp.model.tool.actionplan.Task',
	'Ssp.model.reference.AbstractReference',
    'Ssp.model.reference.Challenge',
    'Ssp.model.reference.ChallengeCategory',
    'Ssp.model.reference.ChallengeReferral',
	'Ssp.model.reference.ConfidentialityLevel',
	'Ssp.model.reference.ConfidentialityDisclosureAgreement',
	'Ssp.mixin.ApiProperties',
	'Ssp.util.FormRendererUtils',
	'Ssp.util.ColumnRendererUtils',
	'Ssp.util.TreeRendererUtils',
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
	'Ssp.store.reference.EarlyAlertOutcomes',
	'Ssp.store.reference.EarlyAlertOutreaches',
	'Ssp.store.reference.EarlyAlertReasons',
	'Ssp.store.reference.EarlyAlertReferrals',
	'Ssp.store.reference.EarlyAlertSuggestions',
    'Ssp.store.reference.EmploymentShifts',
    'Ssp.store.reference.Ethnicities',
    'Ssp.store.reference.FundingSources',
    'Ssp.store.reference.Genders',
    'Ssp.store.reference.JournalSources',
    'Ssp.store.reference.JournalSteps',
    'Ssp.store.reference.JournalTracks',
    'Ssp.store.reference.MaritalStatuses',
    'Ssp.store.reference.States', 
    'Ssp.store.Students',
    'Ssp.store.reference.StudentStatuses',
    'Ssp.store.Tools',
    'Ssp.store.reference.VeteranStatuses',
    'Ssp.store.reference.YesNo',
    'Ssp.controller.ApplicationEventsController',
	'Ext.tab.*',
	'Ext.ux.CheckColumn',
	'Ext.util.Filter',
	'Ext.data.TreeStore',
	'Ext.dd.DropTarget'
]);

Ext.onReady(function(){
	Deft.Injector.configure({
	    currentPerson: {
	        fn: function(){
	            return new Ssp.model.Person({id:"0"});
	        },
	        singleton: true
	    },
	    authenticatedPerson: {
	        fn: function(){
	            return new Ssp.model.Person({id:"91f46e39-cea8-422b-b215-00f6bcf5d280"});
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
	    treeRendererUtils:{
	        fn: function(){
	            return new Ssp.util.TreeRendererUtils({});
	    	},
	        singleton: true
	    },
        appEventsController:{
	        fn: function(){
	            return new Ssp.controller.ApplicationEventsController({});
	    	},
	        singleton: true
        },
        currentChallenge:{
	        fn: function(){
	            return new Ssp.model.reference.Challenge({id:""});
	    	},
	        singleton: true
        },
        currentChallengeCategory: {
	        fn: function(){
	            return new Ssp.model.reference.ChallengeCategory({id:""});
	    	},
	        singleton: true
        },
        currentChallengeReferral:{
	        fn: function(){
	            return new Ssp.model.reference.ChallengeReferral({id:""});
	    	},
	        singleton: true
        },
        treeStore:{
	        fn: function(){
	            return Ext.create('Ext.data.TreeStore',{
	            	root: {
	    	          text: 'root',
	    	          expanded: true,
	    	          children: []
	    	        }
	            });
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
		earlyAlertOutcomesStore: 'Ssp.store.reference.EarlyAlertOutcomes',
		earlyAlertOutreachesStore: 'Ssp.store.reference.EarlyAlertOutreaches',
		earlyAlertReasonsStore: 'Ssp.store.reference.EarlyAlertReasons',
		earlyAlertReferralsStore: 'Ssp.store.reference.EarlyAlertReferrals',
		earlyAlertSuggestionsStore: 'Ssp.store.reference.EarlyAlertSuggestions',	    
	    educationGoalsStore: 'Ssp.store.reference.EducationGoals',
    	educationLevelsStore: 'Ssp.store.reference.EducationLevels',
    	employmentShiftsStore: 'Ssp.store.reference.EmploymentShifts',
    	ethnicitiesStore: 'Ssp.store.reference.Ethnicities',
    	fundingSourcesStore: 'Ssp.store.reference.FundingSources',
    	gendersStore: 'Ssp.store.reference.Genders',
        journalSourcesStore: 'Ssp.store.reference.JournalSources',
        journalStepsStore: 'Ssp.store.reference.JournalSteps',
        journalTracksStore: 'Ssp.store.reference.JournalTracks',
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