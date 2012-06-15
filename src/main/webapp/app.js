Ext.Loader.setConfig({
	enabled: true,
	paths: {
		'Ssp': '/ssp/app',
		'ContextName': 'ssp'
	}
});

Ext.require([
    'Ssp.view.admin.AdminMain',
    'Ssp.view.admin.AdminTreeMenu',
    'Ssp.view.admin.AdminForms',
    'Ssp.view.Main',
    'Ssp.view.Search',
    'Ssp.view.StudentRecord',
    'Ssp.view.person.CaseloadAssignment',
    'Ssp.view.person.EditPerson',
    'Ssp.view.person.Appointment',
    'Ssp.view.person.SpecialServiceGroups',
    'Ssp.view.person.ReferralSources',
    'Ssp.view.person.ServiceReasons',
    'Ssp.view.person.AnticipatedStartDate',    
    'Ssp.view.component.MappedTextField',
    'Ssp.view.component.MappedTextArea',
    'Ssp.view.component.MappedCheckBox',
    'Ssp.view.component.MappedRadioButton',
    'Ssp.view.ToolsMenu',
    'Ssp.view.Tools',
    'Ssp.view.tools.Profile',
    'Ssp.view.tools.profile.Person',
    'Ssp.view.tools.profile.SpecialServiceGroups',
    'Ssp.view.tools.profile.ReferralSources',
    'Ssp.view.tools.ActionPlan',
    'Ssp.view.tools.actionplan.Tasks',
    'Ssp.view.tools.actionplan.AddTask',
    'Ssp.view.tools.actionplan.AddTaskForm',
    'Ssp.view.tools.actionplan.EditGoalForm',
    'Ssp.view.tools.actionplan.DisplayActionPlan',
    'Ssp.view.tools.actionplan.DisplayActionPlanGoals',
    'Ssp.view.tools.actionplan.DisplayStrengths',
    'Ssp.view.tools.actionplan.TaskTree',
    'Ssp.view.tools.StudentIntake',
    'Ssp.view.tools.studentintake.Challenges',
    'Ssp.view.tools.studentintake.Demographics',
    'Ssp.view.tools.studentintake.EducationGoals',
    'Ssp.view.tools.studentintake.EducationLevels',
    'Ssp.view.tools.studentintake.EducationPlans',
    'Ssp.view.tools.studentintake.Funding',
    'Ssp.view.tools.studentintake.Personal',
    'Ssp.view.tools.Journal',
    'Ssp.view.tools.journal.EditJournal',
    'Ssp.view.tools.journal.TrackTree',
    'Ssp.view.tools.EarlyAlert',
    'Ssp.view.tools.earlyalert.EarlyAlertResponse',
    'Ssp.view.tools.StudentDocuments',
    'Ssp.view.tools.document.EditDocument',
    'Ssp.view.tools.StudentInformationSystem',
    'Ssp.view.tools.sis.Registration',
    'Ssp.view.tools.sis.Assessment',
    'Ssp.view.tools.sis.Transcript',
    'Ssp.view.tools.sis.Act',
    'Ssp.view.tools.DisabilityServices',
    'Ssp.view.tools.disability.General',
    'Ssp.view.tools.disability.AgencyContacts',
    'Ssp.view.tools.disability.DisabilityCodes',
    'Ssp.view.tools.disability.Disposition',
    'Ssp.view.tools.disability.Accommodations',
    'Ssp.view.tools.DisplacedWorker',
    'Ssp.view.tools.StudentSuccess',
    'Ssp.view.admin.AdminForms',
    'Ssp.view.admin.forms.AbstractReferenceAdmin',
    'Ssp.view.admin.forms.ConfidentialityDisclosureAgreementAdmin',
    
    // COUNSELING REFERENCE GUIDE ADMIN VIEWS
    'Ssp.view.admin.forms.crg.ChallengeAdmin',
    'Ssp.view.admin.forms.crg.ChallengeReferralAdmin',
    'Ssp.view.admin.forms.crg.AssociateChallengeCategoriesAdmin',
    'Ssp.view.admin.forms.crg.AssociateChallengeReferralsAdmin',
    'Ssp.view.admin.forms.crg.DisplayChallengesAdmin',
    'Ssp.view.admin.forms.crg.DisplayReferralsAdmin',
    'Ssp.view.admin.forms.crg.EditChallenge',
    'Ssp.view.admin.forms.crg.EditReferral',

    // JOURNAL ADMIN VIEWS
    'Ssp.view.admin.forms.journal.JournalStepAdmin',
    'Ssp.view.admin.forms.journal.JournalStepDetailAdmin',
    'Ssp.view.admin.forms.journal.AssociateTrackStepsAdmin',
    'Ssp.view.admin.forms.journal.AssociateStepDetailsAdmin',
    'Ssp.view.admin.forms.journal.DisplayDetailsAdmin',
    'Ssp.view.admin.forms.journal.DisplayStepsAdmin',
    'Ssp.view.admin.forms.journal.EditStep',
    'Ssp.view.admin.forms.journal.EditStepDetail',
    
    // CAMPUS ADMIN VIEWS
    'Ssp.view.admin.forms.campus.CampusAdmin',
    'Ssp.view.admin.forms.campus.DefineCampus',
    'Ssp.view.admin.forms.campus.EditCampus',
    
    'Ssp.model.util.TreeRequest',
    'Ssp.model.Configuration',
	'Ssp.model.Person',
	'Ssp.model.PersonGoal',
	'Ssp.model.PersonDocument',
	'Ssp.model.tool.studentintake.StudentIntakeForm',
	'Ssp.model.tool.studentintake.PersonDemographics',
	'Ssp.model.tool.studentintake.PersonEducationGoal',
	'Ssp.model.tool.studentintake.PersonEducationPlan',
	'Ssp.model.tool.actionplan.Task',
	'Ssp.model.tool.earlyalert.PersonEarlyAlert',
	'Ssp.model.tool.earlyalert.EarlyAlertResponse',
	'Ssp.model.tool.journal.JournalEntry',
	'Ssp.model.reference.AbstractReference',
    'Ssp.model.reference.Challenge',
    'Ssp.model.reference.ChallengeCategory',
    'Ssp.model.reference.ChallengeReferral',
    'Ssp.model.reference.JournalTrack',
    'Ssp.model.reference.JournalStep',
    'Ssp.model.reference.JournalStepDetail',
	'Ssp.model.reference.ConfidentialityLevel',
	'Ssp.model.reference.ConfidentialityDisclosureAgreement',
	'Ssp.model.ApiUrl',
	'Ssp.mixin.ApiProperties',
	'Ssp.util.FormRendererUtils',
	'Ssp.util.ColumnRendererUtils',
	'Ssp.util.TreeRendererUtils',
	'Ssp.util.Constants',
    'Ssp.store.Tasks',
    'Ssp.store.Goals',
    'Ssp.store.JournalEntries',
    'Ssp.store.EarlyAlerts',
    'Ssp.store.Documents',
	'Ssp.store.reference.AbstractReferences',
	'Ssp.store.admin.AdminTreeMenus',
	'Ssp.store.reference.Campuses',
	'Ssp.store.reference.CampusEarlyAlertRoutings',
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
    'Ssp.store.reference.JournalStepDetails',
    'Ssp.store.reference.JournalSteps',
    'Ssp.store.reference.JournalTracks',
    'Ssp.store.reference.MaritalStatuses',
    'Ssp.store.reference.ProgramStatuses',
    'Ssp.store.reference.ProgramStatusChangeReasons',
    'Ssp.store.reference.ReferralSources',
    'Ssp.store.reference.ServiceReasons',
    'Ssp.store.reference.SpecialServiceGroups',
    'Ssp.store.reference.States', 
    'Ssp.store.Students',
    'Ssp.store.reference.StudentStatuses',
    'Ssp.store.reference.StudentTypes',
    'Ssp.store.Tools',
    'Ssp.store.reference.VeteranStatuses',
    'Ssp.store.reference.YesNo',
    'Ssp.controller.ApplicationEventsController',
    'Ext.tab.*',
	'Ext.ux.CheckColumn',
	'Ext.util.Filter',
	'Ext.data.TreeStore',
	'Ext.dd.DropTarget',
	'Ext.data.Store',
	'Ext.form.field.VTypes',
	'Ext.form.field.Text',
	'Ext.form.field.TextArea',
	'Ext.ux.form.MultiSelect',
	'Ext.ux.form.ItemSelector',
	'Ext.form.FieldSet'
]);

var apiUrls = [
  {name: 'campus', url: 'reference/campus/'},
  {name: 'campusEarlyAlertRouting', url: 'reference/campus/{id}/earlyAlertRouting/'},
  {name: 'category', url: 'reference/category/'},
  {name: 'challenge', url: 'reference/challenge/'},
  {name: 'challengeReferral', url: 'reference/challengeReferral/'},
  {name: 'childCareArrangement', url: 'reference/childCareArrangement/'},
  {name: 'citizenship', url: 'reference/citizenship/'},
  {name: 'confidentialityDisclosureAgreement', url: 'reference/confidentialityDisclosureAgreement/'},
  {name: 'confidentialityLevel', url: 'reference/confidentialityLevel/'},
  {name: 'configuration', url: 'reference/configuration/'},
  {name: 'earlyAlertOutcome', url: 'reference/earlyAlertOutcome/'},
  {name: 'earlyAlertOutreach', url: 'reference/earlyAlertOutreach/'},
  {name: 'earlyAlertReason', url: 'reference/earlyAlertReason/'},
  {name: 'earlyAlertReferral', url: 'reference/earlyAlertReferral/'},
  {name: 'earlyAlertSuggestion', url: 'reference/earlyAlertSuggestion/'},
  {name: 'educationGoal', url: 'reference/educationGoal/'},
  {name: 'educationLevel', url: 'reference/educationLevel/'},
  {name: 'ethnicity', url: 'reference/ethnicity/'},
  {name: 'fundingSource', url: 'reference/fundingSource/'},
  {name: 'journalSource', url: 'reference/journalSource/'},
  {name: 'journalStep', url: 'reference/journalStep/'},
  {name: 'journalTrack', url: 'reference/journalTrack/'},
  {name: 'journalStepDetail', url: 'reference/journalStepDetail/'},
  {name: 'maritalStatus', url: 'reference/maritalStatus/'},
  {name: 'studentStatus', url: 'reference/studentStatus/'},
  {name: 'veteranStatus', url: 'reference/veteranStatus/'},
  {name: 'person', url: 'person/'},
  {name: 'personChallenge', url: 'person/{id}/challenge/'},
  {name: 'personDocument', url: 'person/{id}/document/'},
  {name: 'personEarlyAlert', url: 'person/{id}/earlyAlert/'},
  {name: 'personEarlyAlertResponse', url: 'person/{id}/earlyAlert/{id}/earlyAlertResponse/'},
  {name: 'personGoal', url: 'person/{id}/goal/'},
  {name: 'personJournalEntry', url: 'person/{id}/journalEntry/'},
  {name: 'personTask', url: 'person/{id}/task/'},
  {name: 'personTaskGroup', url: 'person/{id}/task/group/'},
  {name: 'personEmailTask', url: 'person/{id}/task/email/'},
  {name: 'personViewHistory', url: 'person/{id}/history/print/'},
  {name: 'personPrintTask', url: 'person/{id}/task/print/'},
  {name: 'personSearch', url: 'person/search/'},
  {name: 'programStatus', url: 'reference/programStatus/'},
  {name: 'programStatusChangeReason', url: 'reference/programStatusChangeReason/'},
  {name: 'referralSource', url: 'reference/referralSource/'},
  {name: 'serviceReasons', url: 'reference/serviceReason/'},
  {name: 'specialServiceGroup', url: 'reference/specialServiceGroup/'},
  {name: 'studentIntakeTool', url: 'tool/studentIntake/'},
  {name: 'studentType', url: 'reference/studentType/'}
];

Ext.onReady(function(){
	Deft.Injector.configure({
		sspParentDivId: {
	        value: sspParentDivId
	    },
	    renderSSPFullScreen: {
	        value: renderSSPFullScreen
	    },
		apiUrlStore: {
			fn: function(){
				var urlStore = Ext.create('Ext.data.Store', {
				     model: 'Ssp.model.ApiUrl',
				     storeId: 'apiUrlStore'
				 });
				
				urlStore.loadData( apiUrls );
				
				return urlStore;
			},
			singleton: true
		},
		sspConfig: {
	        fn: function(){
	            return new Ssp.model.Configuration({});
	        },
	        singleton: true
	    },
		currentPerson: {
	        fn: function(){
	            return new Ssp.model.Person({id:"0"});
	        },
	        singleton: true
	    },
	    authenticatedPerson: {
	        fn: function(){
	            return new Ssp.model.Person({id:"1010e4a0-1001-0110-1011-4ffc02fe81ff"});
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
        currentChallengeReferral:{
	        fn: function(){
	            return new Ssp.model.reference.ChallengeReferral({id:""});
	    	},
	        singleton: true
        },
        currentJournalStep:{
	        fn: function(){
	            return new Ssp.model.reference.JournalStep({id:""});
	    	},
	        singleton: true
        },
        currentJournalStepDetail:{
	        fn: function(){
	            return new Ssp.model.reference.JournalStepDetail({id:""});
	    	},
	        singleton: true
        },
        currentTask:{
	        fn: function(){
	            return new Ssp.model.tool.actionplan.Task({id:""});
	    	},
	        singleton: true
        },
        currentGoal:{
	        fn: function(){
	            return new Ssp.model.PersonGoal({id:""});
	    	},
	        singleton: true
        },
        currentJournalEntry:{
	        fn: function(){
	            return new Ssp.model.tool.journal.JournalEntry({id:""});
	    	},
	        singleton: true
        },
        currentEarlyAlert:{
	        fn: function(){
	            return new Ssp.model.tool.earlyalert.PersonEarlyAlert({id:"",courseTitle:'DEV-065-TC - Developmental Reading'});
	    	},
	        singleton: true
        },
        currentEarlyAlertResponse:{
	        fn: function(){
	            return new Ssp.model.tool.earlyalert.EarlyAlertResponse({id:""});
	    	},
	        singleton: true
        },
        currentDocument:{
	        fn: function(){
	            return new Ssp.model.PersonDocument({id:""});
	    	},
	        singleton: true
        },
        currentCampus:{
	        fn: function(){
	            return new Ssp.model.reference.Campus({id:""});
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
        profileSpecialServiceGroupsStore:{
	        fn: function(){
	            return Ext.create('Ext.data.Store',{
	            	model: 'Ssp.model.reference.SpecialServiceGroup'
	            });
	    	},
	        singleton: true
        },
        profileReferralSourcesStore:{
	        fn: function(){
	            return Ext.create('Ext.data.Store',{
	            	model: 'Ssp.model.reference.ReferralSource'
	            });
	    	},
	        singleton: true
        },
		abstractReferencesStore: 'Ssp.store.reference.AbstractReferences',
	    adminTreeMenusStore: 'Ssp.store.admin.AdminTreeMenus',
		campusesStore: 'Ssp.store.reference.Campuses',
		campusEarlyAlertRoutingsStore: 'Ssp.store.reference.CampusEarlyAlertRoutings',
	    challengesStore: 'Ssp.store.reference.Challenges',
		challengeCategoriesStore: 'Ssp.store.reference.ChallengeCategories',
		challengeReferralsStore: 'Ssp.store.reference.ChallengeReferrals',
	    childCareArrangementsStore: 'Ssp.store.reference.ChildCareArrangements',
	    citizenshipsStore: 'Ssp.store.reference.Citizenships',
    	confidentialityDisclosureAgreementsStore: 'Ssp.store.reference.ConfidentialityDisclosureAgreements',		
	    confidentialityLevelsStore: 'Ssp.store.reference.ConfidentialityLevels',
	    documentsStore: 'Ssp.store.Documents',
	    earlyAlertOutcomesStore: 'Ssp.store.reference.EarlyAlertOutcomes',
		earlyAlertOutreachesStore: 'Ssp.store.reference.EarlyAlertOutreaches',
		earlyAlertReasonsStore: 'Ssp.store.reference.EarlyAlertReasons',
		earlyAlertReferralsStore: 'Ssp.store.reference.EarlyAlertReferrals',
	    earlyAlertsStore: 'Ssp.store.EarlyAlerts',
		earlyAlertSuggestionsStore: 'Ssp.store.reference.EarlyAlertSuggestions',	    
	    educationGoalsStore: 'Ssp.store.reference.EducationGoals',
    	educationLevelsStore: 'Ssp.store.reference.EducationLevels',
    	employmentShiftsStore: 'Ssp.store.reference.EmploymentShifts',
    	ethnicitiesStore: 'Ssp.store.reference.Ethnicities',
    	fundingSourcesStore: 'Ssp.store.reference.FundingSources',
    	gendersStore: 'Ssp.store.reference.Genders',
	    goalsStore: 'Ssp.store.Goals',
    	journalEntriesStore: 'Ssp.store.JournalEntries',
    	journalSourcesStore: 'Ssp.store.reference.JournalSources',
        journalStepsStore: 'Ssp.store.reference.JournalSteps',
        journalDetailsStore: 'Ssp.store.reference.JournalStepDetails',
        journalTracksStore: 'Ssp.store.reference.JournalTracks',
    	maritalStatusesStore: 'Ssp.store.reference.MaritalStatuses',
    	programStatusesStore: 'Ssp.store.reference.ProgramStatuses',
    	programStatusChangeReasonsStore: 'Ssp.store.reference.ProgramStatusChangeReasons',
	    referralSourcesStore: 'Ssp.store.reference.ReferralSources',
	    serviceReasonsStore: 'Ssp.store.reference.ServiceReasons',
	    specialServiceGroupsStore: 'Ssp.store.reference.SpecialServiceGroups',
    	statesStore: 'Ssp.store.reference.States',
	    studentsStore: 'Ssp.store.Students',
	    studentStatusesStore: 'Ssp.store.reference.StudentStatuses',
	    studentTypesStore: 'Ssp.store.reference.StudentTypes',
	    tasksStore: 'Ssp.store.Tasks',
	    toolsStore: 'Ssp.store.Tools',
    	veteranStatusesStore: 'Ssp.store.reference.VeteranStatuses',
        yesNoStore: 'Ssp.store.reference.YesNo'
	});

	Ext.application({
	    name: 'Ssp',
	    appFolder: Ext.Loader.getPath('Ssp'),
		autoCreateViewport: true,
	    launch: function( app ) {
	    	console.log('launch application');
	    	Deft.Injector.providers.appEventsController.value.config.app=this;
	    	Deft.Injector.providers.appEventsController.value.app=this;
	    	
	    	// Global error handling for Ajax calls 
	    	Ext.override(Ext.data.proxy.Server, {
	            constructor: function(config)
	            {
	                this.callOverridden([config]);
	                this.addListener("exception",  function (proxy, response, operation) {
	                    if (response.responseText != null)
	                    {
	                        Ext.Msg.alert('Error', response.responseText);
	                    }
	                });
	            }
	        });
	    	
	    	/*
	    	 * Provide global asterisks next to required fields
	    	 */
	    	Ext.Function.interceptAfter(Ext.form.Field.prototype,'initComponent', function(){
	    		var fl=this.fieldLabel, ab=this.allowBlank;
	    		if (ab===false && fl){
	    			this.fieldLabel += '<span style="color: rgb(255, 0, 0); padding-left: 2px;">*</span>';
	    		}
	    	});	    	
	    	
	    	Ext.apply(this,{
	    		items: [{xtype:'sspview'}]
	    	});
	    	// Display the application
	        //this.getController('MainViewController').displayApplication();
	   }
	});	
});