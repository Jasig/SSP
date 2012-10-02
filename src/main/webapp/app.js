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
    'Ssp.view.ProgramStatusChangeReasonWindow',
    'Ssp.view.person.CaseloadAssignment',
    'Ssp.view.person.EditPerson',
    'Ssp.view.person.Coach',
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
    'Ssp.view.tools.profile.Profile',
    'Ssp.view.tools.profile.Person',
    'Ssp.view.tools.profile.SpecialServiceGroups',
    'Ssp.view.tools.profile.ReferralSources',
    'Ssp.view.tools.profile.ServicesProvided',
    'Ssp.view.tools.profile.ServiceReasons',
    'Ssp.view.tools.actionplan.ActionPlan',
    'Ssp.view.tools.actionplan.Tasks',
    'Ssp.view.tools.actionplan.AddTask',
    'Ssp.view.tools.actionplan.AddTaskForm',
    'Ssp.view.tools.actionplan.EditGoalForm',
    'Ssp.view.tools.actionplan.DisplayActionPlan',
    'Ssp.view.tools.actionplan.DisplayActionPlanGoals',
    'Ssp.view.tools.actionplan.DisplayStrengths',
    'Ssp.view.tools.actionplan.TaskTree',
    'Ssp.view.tools.studentintake.StudentIntake',
    'Ssp.view.tools.studentintake.Challenges',
    'Ssp.view.tools.studentintake.Demographics',
    'Ssp.view.tools.studentintake.EducationGoals',
    'Ssp.view.tools.studentintake.EducationLevels',
    'Ssp.view.tools.studentintake.EducationPlans',
    'Ssp.view.tools.studentintake.Funding',
    'Ssp.view.tools.studentintake.Personal',
    'Ssp.view.tools.journal.Journal',
    'Ssp.view.tools.journal.EditJournal',
    'Ssp.view.tools.journal.DisplayDetails',
    'Ssp.view.tools.journal.TrackTree',
    'Ssp.view.tools.earlyalert.EarlyAlert',
    'Ssp.view.tools.earlyalert.EarlyAlertResponse',
    'Ssp.view.tools.earlyalert.EarlyAlertReferrals',
    'Ssp.view.tools.earlyalert.EarlyAlertDetails',
    'Ssp.view.tools.earlyalert.EarlyAlertResponseDetails',
    'Ssp.view.tools.document.StudentDocuments',
    'Ssp.view.tools.document.EditDocument',
    'Ssp.view.tools.sis.StudentInformationSystem',
    'Ssp.view.tools.sis.Registration',
    'Ssp.view.tools.sis.Assessment',
    'Ssp.view.tools.sis.Transcript',
    'Ssp.view.tools.disability.DisabilityServices',
    'Ssp.view.tools.disability.General',
    'Ssp.view.tools.disability.AgencyContacts',
    'Ssp.view.tools.disability.DisabilityCodes',
    'Ssp.view.tools.disability.Disposition',
    'Ssp.view.tools.disability.Accommodations',
    'Ssp.view.tools.displacedworker.DisplacedWorker',
    'Ssp.view.tools.studentsuccess.StudentSuccess',
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
    'Ssp.view.admin.forms.campus.CampusEarlyAlertRoutingsAdmin',
    'Ssp.view.admin.forms.campus.EarlyAlertRoutingsAdmin',
    'Ssp.view.admin.forms.campus.EditCampusEarlyAlertRouting',
    
    // ERROR DISPLAYS
    'Ssp.view.ErrorWindow',
    
    // REPORT DISPLAY
    'Ssp.view.Report',
    
    'Ssp.model.SimpleItemDisplay',
    'Ssp.model.ObjectPermission',
    'Ssp.model.AuthenticatedPerson',
    'Ssp.model.Preferences',
    'Ssp.model.FieldError',
    'Ssp.model.util.TreeRequest',
    'Ssp.model.Configuration',
	'Ssp.model.Person',
	'Ssp.model.PersonAppointment',
	'Ssp.model.Appointment',
	'Ssp.model.CaseloadPerson',
	'Ssp.model.SearchPerson',
	'Ssp.model.SearchCriteria',
	'Ssp.model.CaseloadFilterCriteria',
	'Ssp.model.PersonGoal',
	'Ssp.model.PersonDocument',
	'Ssp.model.PersonLite',
	'Ssp.model.PersonSearchLite',
	'Ssp.model.PersonProgramStatus',
	'Ssp.model.tool.studentintake.StudentIntakeForm',
	'Ssp.model.tool.studentintake.PersonDemographics',
	'Ssp.model.tool.studentintake.PersonEducationGoal',
	'Ssp.model.tool.studentintake.PersonEducationPlan',
	'Ssp.model.tool.actionplan.Task',
	'Ssp.model.tool.earlyalert.PersonEarlyAlert',
	'Ssp.model.tool.earlyalert.PersonEarlyAlertTree',
	'Ssp.model.tool.earlyalert.EarlyAlertResponse',
	'Ssp.model.tool.journal.JournalEntry',
	'Ssp.model.tool.journal.JournalEntryDetail',
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
	'Ssp.store.Coaches',
	'Ssp.store.Caseload',
    'Ssp.store.Tasks',
    'Ssp.store.Goals',
    'Ssp.store.JournalEntries',
    'Ssp.store.JournalEntryDetails',
    'Ssp.store.EarlyAlerts',
    'Ssp.store.EarlyAlertCoordinators',
    'Ssp.store.Documents',
	'Ssp.store.reference.AbstractReferences',
	'Ssp.store.admin.AdminTreeMenus',
	'Ssp.store.reference.AnticipatedStartTerms',
	'Ssp.store.reference.AnticipatedStartYears',
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
    'Ssp.store.People',
    'Ssp.store.PeopleSearchLite',
    'Ssp.store.reference.ProgramStatuses',
    'Ssp.store.reference.ProgramStatusChangeReasons',
    'Ssp.store.reference.ReferralSources',
    'Ssp.store.reference.ServiceReasons',
    'Ssp.store.reference.SpecialServiceGroups',
    'Ssp.store.reference.States', 
    'Ssp.store.Students',
    'Ssp.store.Search',
    'Ssp.store.reference.StudentStatuses',
    'Ssp.store.reference.StudentTypes',
    'Ssp.store.Tools',
    'Ssp.store.reference.VeteranStatuses',
    'Ssp.store.reference.YesNo',
    'Ssp.service.AbstractService',
    'Ssp.service.AppointmentService',
    'Ssp.service.AssessmentService',
    'Ssp.service.CaseloadService',
    'Ssp.service.CampusService',
    'Ssp.service.CampusEarlyAlertRoutingService',
    'Ssp.service.ConfidentialityDisclosureAgreementService',
    'Ssp.service.EarlyAlertService',
    'Ssp.service.EarlyAlertResponseService',
    'Ssp.service.EarlyAlertReferralService',
    'Ssp.service.JournalEntryService',
    'Ssp.service.PersonService',
    'Ssp.service.ProgramStatusService',
    'Ssp.service.ReferralSourceService',
    'Ssp.service.SearchService',
    'Ssp.service.SpecialServiceGroupService',
    'Ssp.service.StudentIntakeService',
    'Ssp.service.TranscriptService',
    'Ssp.controller.ApplicationEventsController',
    'Ext.tab.*',
	'Ext.util.Filter',
	'Ext.data.TreeStore',
	'Ext.dd.DropTarget',
	'Ext.data.Store',
	'Ext.form.field.VTypes',
	'Ext.form.field.Text',
	'Ext.form.field.TextArea',
	'Ext.form.FieldSet',
	'Ext.ux.CheckColumn',
	'Ext.ux.form.MultiSelect',
	'Ext.ux.form.ItemSelector',
	'Ext.util.MixedCollection',
	'Ext.util.TaskRunner',
	'Ext.tree.*',
	'Ext.toolbar.Spacer',
	'Ext.form.field.ComboBox',
	'Ext.grid.column.Action',
	'Ext.grid.feature.Grouping'
]);

var apiUrls = [
  {name: 'campus', url: 'reference/campus'},
  {name: 'campusEarlyAlertRouting', url: 'reference/campus/{id}/earlyAlertRouting'},
  {name: 'category', url: 'reference/category'},
  {name: 'challenge', url: 'reference/challenge'},
  {name: 'challengeReferral', url: 'reference/challengeReferral'},
  {name: 'childCareArrangement', url: 'reference/childCareArrangement'},
  {name: 'citizenship', url: 'reference/citizenship'},
  {name: 'confidentialityDisclosureAgreement', url: 'reference/confidentialityDisclosureAgreement'},
  {name: 'printConfidentialityDisclosureAgreement', url: '/forms/ConfidentialityAgreement.jsp'},
  {name: 'confidentialityLevel', url: 'reference/confidentialityLevel'},
  {name: 'configuration', url: 'reference/configuration'},
  {name: 'earlyAlertOutcome', url: 'reference/earlyAlertOutcome'},
  {name: 'earlyAlertOutreach', url: 'reference/earlyAlertOutreach'},
  {name: 'earlyAlertReason', url: 'reference/earlyAlertReason'},
  {name: 'earlyAlertReferral', url: 'reference/earlyAlertReferral'},
  {name: 'earlyAlertSuggestion', url: 'reference/earlyAlertSuggestion'},
  {name: 'educationGoal', url: 'reference/educationGoal'},
  {name: 'educationLevel', url: 'reference/educationLevel'},
  {name: 'ethnicity', url: 'reference/ethnicity'},
  {name: 'fundingSource', url: 'reference/fundingSource'},
  {name: 'journalSource', url: 'reference/journalSource'},
  {name: 'journalStep', url: 'reference/journalStep'},
  {name: 'journalTrack', url: 'reference/journalTrack'},
  {name: 'journalStepDetail', url: 'reference/journalStepDetail'},
  {name: 'maritalStatus', url: 'reference/maritalStatus'},
  {name: 'studentStatus', url: 'reference/studentStatus'},
  {name: 'veteranStatus', url: 'reference/veteranStatus'},
  {name: 'person', url: 'person'},
  {name: 'personAppointment', url: 'person/{id}/appointment'},
  {name: 'personAssessment', url: 'person/{id}/test'},
  {name: 'personCaseload', url: 'person/caseload'},
  {name: 'personMasterCaseload', url: 'person/{id}/caseload'},
  {name: 'personChallenge', url: 'person/{id}/challenge'},
  {name: 'personCoach', url: 'person/coach'},
  {name: 'personDocument', url: 'person/{id}/document'},
  {name: 'personEarlyAlert', url: 'person/{personId}/earlyAlert'},
  {name: 'personEarlyAlertResponse', url: 'person/{personId}/earlyAlert/{earlyAlertId}/response'},
  {name: 'personGoal', url: 'person/{id}/goal'},
  {name: 'personJournalEntry', url: 'person/{id}/journalEntry'},
  {name: 'personTask', url: 'person/{id}/task'},
  {name: 'personTaskGroup', url: 'person/{id}/task/group'},
  {name: 'personTranscript', url: 'person/{id}/transcript'},
  {name: 'personEmailTask', url: 'person/{id}/task/email'},
  {name: 'personViewHistory', url: 'report/{id}/History'},
  {name: 'personPrintTask', url: 'person/{id}/task/print'},
  {name: 'personSearch', url: 'person/search'},
  {name: 'personProgramStatus', url: 'person/{id}/programStatus'},
  {name: 'programStatus', url: 'reference/programStatus'},
  {name: 'programStatusChangeReason', url: 'reference/programStatusChangeReason'},
  {name: 'referralSource', url: 'reference/referralSource'},
  {name: 'serviceReasons', url: 'reference/serviceReason'},
  {name: 'session', url: 'session'},
  {name: 'specialServiceGroup', url: 'reference/specialServiceGroup'},
  {name: 'studentIntakeTool', url: 'tool/studentIntake'},
  {name: 'studentType', url: 'reference/studentType'}
];

Ext.onReady(function(){	

    // load the authenticated user
	Ext.Ajax.request({
		url: Ssp.mixin.ApiProperties.getBaseApiUrl() + 'session/getAuthenticatedPerson',
		method: 'GET',
		headers: { 'Content-Type': 'application/json' },
		success: function(response){
			var r = Ext.decode(response.responseText);
			var user={};
			
			if (r != null)
			{			
				// authenticated user
			    user=r;

				// configure the application
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
				            return new Ssp.model.Person({id:""});
				        },
				        singleton: true
				    },
				    personLite: {
				    	fn: function(){
				    		return new Ssp.model.PersonLite({id:""});
				    	},
				    	singleton: true
				    },
				    authenticatedPerson: {
				        fn: function(){
				        	var p = new Ssp.model.AuthenticatedPerson();
				        	p.populateFromGenericObject( user );
				        	p.setObjectPermissions();
				            return p;
				        },
				        singleton: true
				    },
				    preferences: {
				        fn: function(){
				            return new Ssp.model.Preferences();
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
					currentAppointment: {
				        fn: function(){
				            return new Ssp.model.Appointment({id:""});
				        },
				        singleton: true
				    },
					currentPersonAppointment: {
				        fn: function(){
				            return new Ssp.model.PersonAppointment({id:""});
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
			        currentStudentIntake: {
			        	fn: function(){
			        		return new Ssp.model.tool.studentintake.StudentIntakeForm();
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
				            return new Ssp.model.tool.earlyalert.PersonEarlyAlert({id:""});
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
			        currentCampusEarlyAlertRouting:{
				        fn: function(){
				            return new Ssp.model.reference.CampusEarlyAlertRouting({id:""});
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
			        earlyAlertsTreeStore:{
				        fn: function(){
				            return Ext.create('Ext.data.TreeStore',{
				            	model: 'Ssp.model.tool.earlyalert.PersonEarlyAlertTree'
				                ,proxy: {
				                	type: 'ajax',
				                	url: ''
				                }
				            });
				    	},
				        singleton: true
			        },
				    earlyAlertDetailsSuggestionsStore: {
				    	fn: function(){
				    		return Ext.create('Ext.data.Store', {
							     model: 'Ssp.model.SimpleItemDisplay',
							     storeId: 'earlyAlertDetailsSuggestionsStore'
							 });
				    	},
				    	singleton: true
				    },
				    earlyAlertResponseDetailsOutreachesStore: {
				    	fn: function(){
				    		return Ext.create('Ext.data.Store', {
							     model: 'Ssp.model.SimpleItemDisplay',
							     storeId: 'earlyAlertResponseDetailsOutreachesStore'
							 });
				    	},
				    	singleton: true
				    },
				    earlyAlertResponseDetailsReferralsStore: {
				    	fn: function(){
				    		return Ext.create('Ext.data.Store', {
							     model: 'Ssp.model.SimpleItemDisplay',
							     storeId: 'earlyAlertResponseDetailsReferralsStore'
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
			        profileServiceReasonsStore:{
				        fn: function(){
				            return Ext.create('Ext.data.Store',{
				            	model: 'Ssp.model.reference.ServiceReason'
				            });
				    	},
				        singleton: true
			        },
			        errorsStore:{
				        fn: function(){
				            return Ext.create('Ext.data.Store',{
				            	model: 'Ssp.model.FieldError'
				            });
				    	},
				        singleton: true
			        },
				    searchCriteria: {
				    	fn: function(){
				    		return new Ssp.model.SearchCriteria();
				    	},
				    	singleton: true
				    },
				    caseloadFilterCriteria: {
				    	fn: function(){
				    		return new Ssp.model.CaseloadFilterCriteria();
				    	},
				    	singleton: true
				    },
			        // STORES
					abstractReferencesStore: 'Ssp.store.reference.AbstractReferences',
				    adminTreeMenusStore: 'Ssp.store.admin.AdminTreeMenus',
				    anticipatedStartTermsStore: 'Ssp.store.reference.AnticipatedStartTerms',
				    anticipatedStartYearsStore: 'Ssp.store.reference.AnticipatedStartYears',
					campusesStore: 'Ssp.store.reference.Campuses',
					campusEarlyAlertRoutingsStore: 'Ssp.store.reference.CampusEarlyAlertRoutings',
					caseloadStore: 'Ssp.store.Caseload',
					challengesStore: 'Ssp.store.reference.Challenges',
					challengeCategoriesStore: 'Ssp.store.reference.ChallengeCategories',
					challengeReferralsStore: 'Ssp.store.reference.ChallengeReferrals',
				    childCareArrangementsStore: 'Ssp.store.reference.ChildCareArrangements',
				    citizenshipsStore: 'Ssp.store.reference.Citizenships',
			    	coachesStore: 'Ssp.store.Coaches',
				    confidentialityDisclosureAgreementsStore: 'Ssp.store.reference.ConfidentialityDisclosureAgreements',		
				    confidentialityLevelsStore: 'Ssp.store.reference.ConfidentialityLevels',
				    documentsStore: 'Ssp.store.Documents',
				    earlyAlertCoordinatorsStore: 'Ssp.store.EarlyAlertCoordinators',
				    earlyAlertOutcomesStore: 'Ssp.store.reference.EarlyAlertOutcomes',
					earlyAlertOutreachesStore: 'Ssp.store.reference.EarlyAlertOutreaches',
					earlyAlertReasonsStore: 'Ssp.store.reference.EarlyAlertReasons',
					earlyAlertReferralsStore: 'Ssp.store.reference.EarlyAlertReferrals',
					earlyAlertReferralsBindStore: 'Ssp.store.reference.EarlyAlertReferralsBind',
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
			    	journalEntryDetailsStore: 'Ssp.store.JournalEntryDetails',
			    	journalSourcesStore: 'Ssp.store.reference.JournalSources',
			        journalStepsStore: 'Ssp.store.reference.JournalSteps',
			        journalDetailsStore: 'Ssp.store.reference.JournalStepDetails',
			        journalTracksStore: 'Ssp.store.reference.JournalTracks',
			    	maritalStatusesStore: 'Ssp.store.reference.MaritalStatuses',
			        peopleSearchLiteStore: 'Ssp.store.PeopleSearchLite',			    	
			    	peopleStore: 'Ssp.store.People',
			    	programStatusesStore: 'Ssp.store.reference.ProgramStatuses',
			    	programStatusChangeReasonsStore: 'Ssp.store.reference.ProgramStatusChangeReasons',
				    referralSourcesStore: 'Ssp.store.reference.ReferralSources',
				    referralSourcesBindStore: 'Ssp.store.reference.ReferralSourcesBind',
				    searchStore: 'Ssp.store.Search',
				    serviceReasonsStore: 'Ssp.store.reference.ServiceReasons',
				    specialServiceGroupsStore: 'Ssp.store.reference.SpecialServiceGroups',
				    specialServiceGroupsBindStore: 'Ssp.store.reference.SpecialServiceGroupsBind',
				    statesStore: 'Ssp.store.reference.States',
				    studentsStore: 'Ssp.store.Students',
				    studentStatusesStore: 'Ssp.store.reference.StudentStatuses',
				    studentTypesStore: 'Ssp.store.reference.StudentTypes',
				    tasksStore: 'Ssp.store.Tasks',
				    toolsStore: 'Ssp.store.Tools',
			    	veteranStatusesStore: 'Ssp.store.reference.VeteranStatuses',
			        yesNoStore: 'Ssp.store.reference.YesNo',
			        	
			        // SERVICES
			        appointmentService: 'Ssp.service.AppointmentService',
			        assessmentService: 'Ssp.service.AssessmentService',
			        campusService: 'Ssp.service.CampusService',
			        campusEarlyAlertRoutingService: 'Ssp.service.CampusEarlyAlertRoutingService',
			        caseloadService: 'Ssp.service.CaseloadService',
			        confidentialityDisclosureAgreementService: 'Ssp.service.ConfidentialityDisclosureAgreementService',
			        earlyAlertService: 'Ssp.service.EarlyAlertService',
			        earlyAlertReferralService: 'Ssp.service.EarlyAlertReferralService',
			        earlyAlertResponseService: 'Ssp.service.EarlyAlertResponseService',
			        journalEntryService: 'Ssp.service.JournalEntryService',
			        personService: 'Ssp.service.PersonService',
			        personProgramStatusService: 'Ssp.service.PersonProgramStatusService',
			        programStatusService: 'Ssp.service.ProgramStatusService',
			        referralSourceService: 'Ssp.service.ReferralSourceService',
			        searchService: 'Ssp.service.SearchService',
			        specialServiceGroupService: 'Ssp.service.SpecialServiceGroupService',
			        studentIntakeService: 'Ssp.service.StudentIntakeService',
			        transcriptService: 'Ssp.service.TranscriptService'
				});
				
				Ext.application({
				    name: 'Ssp',
				    appFolder: Ext.Loader.getPath('Ssp'),
					autoCreateViewport: true,
				    launch: function( app ) {
				    	var me=this;
				    	Deft.Injector.providers.appEventsController.value.config.app=me;
				    	Deft.Injector.providers.appEventsController.value.app=me;
				    	
				    	// Date patterns for formatting by a description
				    	// rather than a date format
				    	Ext.Date.patterns = {
				    		    ISO8601Long:"Y-m-d H:i:s",
				    		    ISO8601Short:"Y-m-d",
				    		    ShortDate: "n/j/Y",
				    		    LongDate: "l, F d, Y",
				    		    FullDateTime: "l, F d, Y g:i:s A",
				    		    MonthDay: "F d",
				    		    ShortTime: "g:i A",
				    		    LongTime: "g:i:s A",
				    		    SortableDateTime: "Y-m-d\\TH:i:s",
				    		    UniversalSortableDateTime: "Y-m-d H:i:sO",
				    		    YearMonth: "F, Y"
				    	};
				    	
				    	// Global error handling for Ajax calls 
				    	Ext.override(Ext.data.proxy.Server, {
				            constructor: function(config)
				            {
				                this.callOverridden([config]);
				                this.addListener("exception",  function (proxy, response, operation) {
				            		if (response.status==403)
				            		{
				            			Ext.Msg.confirm({
				            	   		     title:'Access Denied Error',
				            	   		     msg: "It looks like you are trying to access restricted information or your login session has expired. Would you like to login to continue working in SSP?",
				            	   		     buttons: Ext.Msg.YESNO,
				            	   		     fn: function( btnId ){
				            	   		    	if (btnId=="yes")
				            	   		    	{
				            	   		    		// force a login
				            	   		    		window.location.reload();
				            	   		    	}else{
				            	   		    		// force a login
				            	   		    		window.location.reload();
				            	   		    	}
				            	   		    },
				            	   		     scope: me
				            	   		});
				            		}
				            		
				            		// Handle call not found result
				            		if (response.status==404)
				            		{
				            			Ext.Msg.alert('SSP Error', '404 Server Error. See logs for additional details');
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
				    			this.fieldLabel += Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY;
				    		}
				    	});		

				    	/*
				    	 * Per Animal, http://www.extjs.com/forum/showthread.php?p=450116#post450116
				    	 * Override to provide a function to determine the invalid
				    	 * fields in a form.
				    	 */
				    	Ext.override(Ext.form.BasicForm, {
				    	    findInvalid: function() {
				    	        var result = [], it = this.getFields().items, l = it.length, i, f;
				    	        for (i = 0; i < l; i++) {
				    	            if(!(f = it[i]).disabled && f.isValid()){
				    	                result.push(f);
				    	            }
				    	        }
				    	        return result;
				    	    }
				    	});	    	

				    	/*
				    	 * Per Animal, http://www.extjs.com/forum/showthread.php?p=450116#post450116
				    	 * Override component so that the first invalid field
				    	 * will be displayed for the user when a form is invalid.
				    	 */
				    	Ext.override(Ext.Component, {
				    	    ensureVisible: function(stopAt) {
				    	    	var p;
				    	        this.ownerCt.bubble(function(c) {
				    	        	if (p = c.ownerCt) {
				    	                if (p instanceof Ext.TabPanel) {
				    	                    p.setActiveTab(c);
				    	                } else if (p.layout.setActiveItem) {
				    	                    p.layout.setActiveItem(c);
				    	                } else if (p.layout.type == 'accordion'){
				    	                	c.expand();
				    	                }
				    	            }
				    	            return (c !== stopAt);
				    	        });
				    	        //this.el.scrollIntoView(this.el.up(':scrollable'));
				    	        return this;
				    	    }
				    	});

				    	/*
				    	 * Per Animal, http://www.extjs.com/forum/showthread.php?p=450116#post450116
				    	 * Enables scrolling to the nearest visible elements
				    	 * in a form for use with the above override for
				    	 * visually indicating when a form validation fails
				    	 * and setting the user to see the first invalid field.    	 
				    	Ext.DomQuery.pseudos.scrollable = function(c, t) {
				    	    var r = [], ri = -1;
				    	    for(var i = 0, ci; ci = c[i]; i++){
				    	        var o = ci.style.overflow;
				    	        if(o=='auto'||o=='scroll') {
				    	            if (ci.scrollHeight < Ext.fly(ci).getHeight(true)) r[++ri] = ci;
				    	        }
				    	    }
				    	    return r;
				    	};
				    	*/    	
				    	
			   	    	// load the main view
			    		Ext.apply(me,{
				    		items: [{xtype:'sspview'}]
				    	});	    	
				    	
				   }
				});
				
			}else{
				Ext.Msg.alert('Error','Unable to determine authenticated user. Please see your system administrator for assistance.');
			}
		}
	}, this);		
	
});