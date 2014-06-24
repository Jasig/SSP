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
    'Ssp.view.field.ObjectStatusCheckbox',
    'Ssp.view.admin.forms.ColorPicker',
    'Ssp.view.admin.AdminMain',
    'Ssp.view.admin.AdminTreeMenu',
    'Ssp.view.admin.AdminForms',
    'Ssp.view.Main',
    'Ssp.view.Search',
    'Ssp.view.SearchForm',
    'Ssp.view.SearchTab',
    'Ssp.view.StudentRecord',
    'Ssp.view.EmailStudentForm',
    'Ssp.view.EmailStudentView',
    'Ssp.view.ProgramStatusChangeReasonWindow',
	'Ssp.view.person.Student',
	'Ssp.view.person.InstantStudent',
    'Ssp.view.person.CaseloadAssignment',
    'Ssp.view.person.InstantCaseloadAssignment',
    'Ssp.view.person.EditPerson',
    'Ssp.view.person.Coach',
    'Ssp.view.person.Appointment',
	'Ssp.view.person.StudentIntakeRequest',
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
	'Ssp.view.tools.profile.Coach',
	'Ssp.view.tools.profile.Contact',
	'Ssp.view.tools.profile.Placement',
    'Ssp.view.tools.profile.Transcript',
	'Ssp.view.tools.profile.RecentSSPActivity',
	'Ssp.view.tools.profile.Schedule',
    'Ssp.view.tools.profile.CurrentSchedule',
    'Ssp.view.tools.profile.DroppedCourses',
	'Ssp.view.tools.profile.Details',
	'Ssp.view.tools.profile.SapStatus',
	'Ssp.view.tools.profile.Dashboard',
	'Ssp.view.tools.profile.AcademicProgram',
	'Ssp.view.tools.profile.RecentTermActivity',
	'Ssp.view.tools.profile.FinancialAidFiles',
	'Ssp.view.tools.profile.FinancialAidFileViewer',
	'Ssp.view.tools.profile.FinancialAidAwards',
	'Ssp.view.tools.profile.FinancialAidAwardViewer',
    'Ssp.view.tools.actionplan.ActionPlan',
    'Ssp.view.tools.actionplan.Tasks',
    'Ssp.view.tools.actionplan.AddTask',
    'Ssp.view.tools.actionplan.AddTaskForm',
    'Ssp.view.tools.actionplan.EditGoalForm',
	'Ssp.view.tools.actionplan.EmailActionPlan',
	'Ssp.view.tools.actionplan.EmailAndPrintActionPlan',
    'Ssp.view.tools.actionplan.DisplayActionPlan',
    'Ssp.view.tools.actionplan.DisplayActionPlanGoals',
    'Ssp.view.tools.actionplan.DisplayStrengths',
    'Ssp.view.tools.actionplan.TaskTree',
	'Ssp.view.tools.actionplan.AddAPTask',
	'Ssp.view.tools.actionplan.ChallengesGrid',
    'Ssp.view.tools.actionplan.CustomActionPlan',
    'Ssp.view.tools.actionplan.EditTaskForm',
    'Ssp.view.tools.actionplan.SearchChallengeView',
	'Ssp.view.tools.actionplan.TasksGrid',
	'Ssp.view.tools.actionplan.AddTaskView',
    'Ssp.view.tools.studentintake.StudentIntake',
    'Ssp.view.tools.studentintake.Checklist',
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
	'Ssp.view.tools.journal.JournalList',
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

    //'Ssp.view.tools.sis.Transcript',
    'Ssp.view.tools.accommodation.Accommodation',
    'Ssp.view.tools.accommodation.General',
    'Ssp.view.tools.accommodation.AgencyContacts',
    'Ssp.view.tools.accommodation.DisabilityTypes',
    'Ssp.view.tools.accommodation.Disposition',
    'Ssp.view.tools.accommodation.Accommodations',

    'Ssp.view.tools.displacedworker.DisplacedWorker',
    'Ssp.view.tools.studentsuccess.StudentSuccess',
    'Ssp.view.admin.AdminForms',
    'Ssp.view.admin.forms.AbstractReferenceAdmin',
    'Ssp.view.admin.forms.ConfidentialityDisclosureAgreementAdmin',
	
	//MAP Views
    'Ssp.view.tools.map.MAP',
	'Ssp.view.tools.map.CoursesView',
    'Ssp.view.tools.map.SemesterPanel',
    'Ssp.view.tools.map.PersistentFieldSet',
    'Ssp.view.tools.map.SemesterPanelContainer',
    'Ssp.view.tools.map.StudentTranscriptViewer',
    'Ssp.view.tools.map.MapStatusReport',
    'Ssp.view.tools.map.FAView',
	'Ssp.view.tools.map.MAPView',
    'Ssp.view.tools.map.MovePlan',
	'Ssp.view.tools.map.MovePlanDialog',
    'Ssp.view.tools.map.PlanTool',
    'Ssp.view.tools.map.LoadPlans',
	'Ssp.view.tools.map.PlanNotes',
	'Ssp.view.tools.map.LoadTemplates',
	'Ssp.view.tools.map.SaveTemplate',
	'Ssp.view.tools.map.StudentTranscriptViewer',
	'Ssp.view.tools.map.SavePlan',
    'Ssp.view.tools.map.CourseNotes',
    'Ssp.view.tools.map.TermNotes',
    'Ssp.view.tools.map.EmailPlan',
    'Ssp.view.tools.map.PrintPlan',
    'Ssp.view.tools.map.CourseDetails',
	'Ssp.view.tools.map.CoursesGridPanel',
	
	//PERSON NOTES TOOL
	'Ssp.view.tools.notes.Notes',
	
	'Ssp.view.tools.documents.Documents',
	'Ssp.view.tools.documents.UploadDocuments',
    
    // COUNSELING REFERENCE GUIDE ADMIN VIEWS
    'Ssp.view.admin.forms.crg.ChallengeAdmin',
    'Ssp.view.admin.forms.crg.ChallengeReferralAdmin',
    'Ssp.view.admin.forms.crg.AssociateChallengeCategoriesAdmin',
    'Ssp.view.admin.forms.crg.AssociateChallengeReferralsAdmin',
    'Ssp.view.admin.forms.crg.DisplayChallengesAdmin',
    'Ssp.view.admin.forms.crg.DisplayReferralsAdmin',
    'Ssp.view.admin.forms.crg.EditChallenge',
    'Ssp.view.admin.forms.crg.EditReferral',

	
    //CASELOAD REASSIGNMENT ADMIN
    'Ssp.view.admin.forms.caseload.CaseloadReassignment',
    'Ssp.view.admin.forms.caseload.CaseloadReassignmentSource',
    'Ssp.view.admin.forms.caseload.CaseloadReassignmentTarget',
    
    //CASELOAD REASSIGNMENT TOOL
    'Ssp.view.tools.caseload.CaseloadReassignment',
    'Ssp.view.tools.caseload.CaseloadReassignmentSource',
    'Ssp.view.tools.caseload.CaseloadReassignmentTarget',    

    //MyGPS ADMIN TOOLS
    'Ssp.view.admin.forms.shg.SelfHelpGuideAdmin',
    'Ssp.view.admin.forms.shg.SelfHelpGuidesDisplayAdmin',
    'Ssp.view.admin.forms.shg.EditSelfHelpGuide',
    'Ssp.view.admin.forms.shg.EditSelfHelpGuideChallenges',
    'Ssp.view.admin.forms.shg.EditSelfHelpGuideDetails',
    'Ssp.view.admin.forms.shg.EditSelfHelpGuideAvailableChallengesAdmin',
    'Ssp.view.admin.forms.shg.EditSelfHelpGuideEditChallenges',    
    
  
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
	
	//CONFIG ADMIN VIEWS
	'Ssp.view.admin.forms.config.ConfigurationOptionsAdmin',
    'Ssp.view.admin.forms.config.ConfigurationOptionsDisplayAdmin',
	'Ssp.view.admin.forms.config.MessageTemplatesAdmin',
    'Ssp.view.admin.forms.config.MessageTemplatesDisplayAdmin',
	'Ssp.view.admin.forms.config.MessageTemplateDetails',

    //OAUTH2 ADMIN VIEWS
    'Ssp.view.admin.forms.apikey.oauth2.OAuth2ClientAdmin',
    'Ssp.view.admin.forms.apikey.oauth2.EditOAuth2Client',
    
    //LTIConsumer ADMIN VIEWS
    'Ssp.view.admin.forms.apikey.lticonsumer.LTIConsumerAdmin',
    'Ssp.view.admin.forms.apikey.lticonsumer.EditLTIConsumer',
    
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
	'Ssp.model.EmailStudentRequest',
	'Ssp.model.SearchPerson',
	'Ssp.model.SearchCriteria',
	'Ssp.model.CaseloadFilterCriteria',
	'Ssp.model.PersonGoal',
	'Ssp.model.PersonStrength',
	'Ssp.model.SearchChallengeReferral',
	'Ssp.model.PersonLite',	
	'Ssp.model.ContactPerson',	
	'Ssp.model.Placement',
	'Ssp.model.PersonProgramStatus',
	'Ssp.model.CourseTranscript',
	'Ssp.model.TermTranscript',
	'Ssp.model.StudentActivity',
	'Ssp.model.Transcript',
	'Ssp.model.FilterDiscreteValues',
	'Ssp.model.MessageTemplates',
	'Ssp.model.OAuth2Client',
	'Ssp.model.LTIConsumer',
	'Ssp.model.Permission',
	'Ssp.model.PersonRegistrationStatusByTerm',
	'Ssp.model.tool.studentintake.StudentIntakeForm',
	'Ssp.model.tool.studentintake.PersonDemographics',
	'Ssp.model.tool.studentintake.PersonEducationGoal',
	'Ssp.model.tool.studentintake.PersonEducationPlan',
	'Ssp.model.tool.accommodation.AccommodationForm',
	'Ssp.model.tool.accommodation.PersonDisability',
	'Ssp.model.tool.accommodation.PersonDisabilityAgency',
	'Ssp.model.tool.accommodation.PersonDisabilityType',
	'Ssp.model.tool.accommodation.PersonDisabilityAccommodation',
	'Ssp.model.tool.actionplan.Task',
	'Ssp.model.tool.earlyalert.PersonEarlyAlert',
	'Ssp.model.tool.earlyalert.PersonEarlyAlertTree',
	'Ssp.model.tool.earlyalert.EarlyAlertResponse',
	'Ssp.model.tool.earlyalert.EarlyAlertResponseGrid',
	'Ssp.model.tool.journal.JournalEntry',
	'Ssp.model.tool.journal.JournalEntryDetail',
	'Ssp.model.tool.documents.StudentDocument',
	'Ssp.model.tool.map.SemesterCourse',
	'Ssp.model.tool.map.Plan',
	'Ssp.model.tool.map.MapStatus',
	'Ssp.model.tool.map.MapStatusCourseDetail',
	'Ssp.model.tool.map.MapStatusSubstitutionDetail',
	'Ssp.model.tool.map.MapStatusTermDetail',
	'Ssp.model.tool.map.TermNote',
	'Ssp.model.tool.map.PlanCourse',
	'Ssp.model.tool.map.PlanOutputData',
	'Ssp.model.tool.shg.SelfHelpGuides',
	'Ssp.model.tool.shg.SelfHelpGuideQuestions',
	'Ssp.model.tool.caseload.CaseloadReassignmentRequest',
	'Ssp.model.reference.AbstractReference',
	'Ssp.model.reference.AdminSelectedIndex',
    'Ssp.model.reference.Challenge',
    'Ssp.model.reference.ChallengeCategory',
    'Ssp.model.reference.ChallengeReferral',
    'Ssp.model.reference.EnrollmentStatus',
    'Ssp.model.reference.CompletedItem',
    'Ssp.model.reference.Text',
    'Ssp.model.reference.JournalTrack',
    'Ssp.model.reference.JournalStep',
    'Ssp.model.reference.JournalStepDetail',
	'Ssp.model.reference.ConfidentialityLevel',
	'Ssp.model.reference.ConfidentialityLevelOption',
	'Ssp.model.reference.ConfidentialityDisclosureAgreement',
	'Ssp.model.reference.EarlyAlertReferral',
	'Ssp.model.external.Course',
	'Ssp.model.external.CourseRequisite',
	'Ssp.model.external.PersonNote',
	'Ssp.model.external.FinancialAidFileStatus',
	'Ssp.model.external.FinancialAidAward',
	'Ssp.model.ApiUrl',
	'Ssp.mixin.ApiProperties',
	'Ssp.mixin.controller.ItemSelectorInitializer',
	'Ssp.util.tools.map.MapEventUtils',
    'Ssp.util.ResponseDispatcher',
	'Ssp.util.FormRendererUtils',
	'Ssp.util.ColumnRendererUtils',
	'Ssp.util.TreeRendererUtils',
	'Ssp.util.StoreUtils',
	'Ssp.util.Constants',
	'Ssp.util.Util',
	'Ssp.store.Coaches',
	'Ssp.store.Caseload',
    'Ssp.store.Tasks',
    'Ssp.store.StudentActivities',
    'Ssp.store.Goals',
	'Ssp.store.Strengths',
	'Ssp.store.AddTasks',
    'Ssp.store.JournalEntries',
    'Ssp.store.JournalEntriesUnpaged',
    'Ssp.store.JournalEntryDetails',
    'Ssp.store.EarlyAlerts',
    'Ssp.store.StudentDocuments',
	'Ssp.store.reference.AbstractReferences',
	'Ssp.store.admin.AdminTreeMenus',
	'Ssp.store.reference.AnticipatedStartTerms',
	'Ssp.store.reference.Campuses',
	'Ssp.store.reference.CampusEarlyAlertRoutings',
	'Ssp.store.reference.CampusServices',
	'Ssp.store.reference.Challenges',
	'Ssp.store.reference.ChallengeCategories',
	'Ssp.store.reference.ChallengeReferrals',
    'Ssp.store.reference.ChildCareArrangements',
    'Ssp.store.reference.Citizenships',
    'Ssp.store.reference.Colors',
	'Ssp.store.reference.ConfidentialityLevels',
	'Ssp.store.reference.ConfidentialityLevelOptions',
	'Ssp.store.reference.ConfigurationOptions',
	'Ssp.store.reference.DisabilityAccommodations',
	'Ssp.store.reference.DisabilityAgencies',
	'Ssp.store.reference.DisabilityStatuses',
	'Ssp.store.reference.DisabilityTypes',
	'Ssp.store.reference.EarlyAlertOutcomes',
	'Ssp.store.reference.EarlyAlertOutreaches',
	'Ssp.store.reference.EarlyAlertReasons',
	'Ssp.store.reference.EarlyAlertReferrals',
	'Ssp.store.reference.EarlyAlertSuggestions',
	'Ssp.store.reference.Electives',
    'Ssp.store.reference.EmploymentShifts',
    'Ssp.store.reference.Ethnicities',
	'Ssp.store.reference.Races',
	'Ssp.store.reference.SapStatuses',
	'Ssp.store.reference.FinancialAidFiles',
    'Ssp.store.reference.FundingSources',
    'Ssp.store.reference.Genders',
    'Ssp.store.reference.JournalSources',
    'Ssp.store.reference.JournalStepDetails',
    'Ssp.store.reference.JournalSteps',
    'Ssp.store.reference.JournalTracks',
    'Ssp.store.reference.Lassis',
    'Ssp.store.reference.MaritalStatuses',
    'Ssp.store.reference.MilitaryAffiliations',
    'Ssp.store.reference.CourseworkHours',
    'Ssp.store.reference.EnrollmentStatuses',
    'Ssp.store.reference.CompletedItem',
    'Ssp.store.reference.Texts',
    'Ssp.store.reference.RegistrationLoads',
    'Ssp.store.reference.CourseworkHours',
	'Ssp.store.reference.MessageTemplates',
    'Ssp.store.LTIConsumers',
    'Ssp.store.OAuth2Clients',
    'Ssp.store.Permissions',
    'Ssp.store.People',
    'Ssp.store.Placement',   
    'Ssp.store.reference.RegistrationLoadRanges', 
    'Ssp.store.reference.WeeklyCourseWorkHourRanges', 
    'Ssp.store.reference.PersonalityTypes',
    'Ssp.store.reference.ProgramStatuses',
    'Ssp.store.reference.ProgramStatusChangeReasons',
    'Ssp.store.reference.ReferralSources',
    'Ssp.store.reference.ServiceReasons',
    'Ssp.store.reference.SelfHelpGuides',
    'Ssp.store.reference.SelfHelpGuideQuestions',
    'Ssp.store.reference.SpecialServiceGroups',
    'Ssp.store.reference.States', 
    'Ssp.store.external.Terms',
	'Ssp.store.external.TermsFaceted',
	'Ssp.store.external.Programs',
	'Ssp.store.external.ProgramsFaceted',
	'Ssp.store.external.Departments',
	'Ssp.store.external.Divisions',
	'Ssp.store.external.CatalogYears',
    'Ssp.store.external.Courses',
    'Ssp.store.external.CourseRequisites',
    'Ssp.store.RegistrationStatusByTerm',    
    'Ssp.store.external.PersonNotes',
    'Ssp.store.Students',
	'Ssp.store.SemesterCourses',
    'Ssp.store.Search',
    'Ssp.store.reference.StudentStatuses',
    'Ssp.store.reference.StudentTypes',
	'Ssp.store.reference.Tags',
	'Ssp.store.reference.FacetedTags',
    'Ssp.store.Tools',
    'Ssp.store.reference.VeteranStatuses',
    'Ssp.store.PlanStatus',
    'Ssp.store.MAPStatus',
    'Ssp.store.PersonTableTypes',
    'Ssp.store.MapStatusReports',
    'Ssp.store.MapStatusReportCourseDetails',
    'Ssp.store.MapStatusReportSubstitutionDetails',
    'Ssp.store.MapStatusReportTermDetails',
    'Ssp.store.CurrentlyRegistered',
    'Ssp.store.FinancialAidSAPStatus',
    'Ssp.service.AbstractService',
    'Ssp.service.AppointmentService',
    'Ssp.service.AssessmentService',
    'Ssp.service.CaseloadService',
    'Ssp.service.CampusService',
    'Ssp.service.CampusEarlyAlertRoutingService',
    'Ssp.service.ConfidentialityDisclosureAgreementService',
    'Ssp.service.AccommodationService',
    'Ssp.service.EarlyAlertService',
    'Ssp.service.EarlyAlertResponseService',
    'Ssp.service.EarlyAlertReferralService',
    'Ssp.service.JournalEntryService',
    'Ssp.service.PersonService',
    'Ssp.service.PlacementService',
	'Ssp.service.PersonNoteService',
    'Ssp.service.ProgramStatusService',
    'Ssp.service.ReferralSourceService',
    'Ssp.service.SearchService',
	'Ssp.service.SearchChallengeReferralService',
    'Ssp.service.SpecialServiceGroupService',
	'Ssp.service.ServiceReasonsService',
    'Ssp.service.StudentIntakeService',
    'Ssp.service.TranscriptService',
	'Ssp.service.MapPlanService',
	'Ssp.service.CourseService', 
    'Ssp.controller.ApplicationEventsController',
    'Ext.tab.*',
	'Ext.util.Filter',
	'Ext.data.TreeStore',
	'Ext.dd.*',
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
  {name: 'campusService', url: 'reference/campusService'},
  {name: 'category', url: 'reference/category'},
  {name: 'challenge', url: 'reference/challenge'},
  {name: 'challengeReferral', url: 'reference/challengeReferral'},
  {name: 'challengeReferralSearch', url: 'reference/challengeReferral/search'},
  {name: 'childCareArrangement', url: 'reference/childCareArrangement'},
  {name: 'citizenship', url: 'reference/citizenship'},
  {name: 'color', url: 'reference/color'},
  {name: 'confidentialityDisclosureAgreement', url: 'reference/confidentialityDisclosureAgreement'},
  {name: 'printConfidentialityDisclosureAgreement', url: '/forms/ConfidentialityAgreement.jsp'},
  {name: 'confidentialityLevel', url: 'reference/confidentialityLevel'},
  {name: 'config', url: 'reference/config'},
  {name: 'configuration', url: 'reference/configuration'},
  {name: 'disabilityAccommodation', url: 'reference/disabilityAccommodation'},
  {name: 'accommodationTool', url: 'tool/accommodation'},
  {name: 'disabilityAgency', url: 'reference/disabilityAgency'},
  {name: 'disabilityStatus', url: 'reference/disabilityStatus'},
  {name: 'disabilityType', url: 'reference/disabilityType'},
  {name: 'earlyAlertOutcome', url: 'reference/earlyAlertOutcome'},
  {name: 'earlyAlertOutreach', url: 'reference/earlyAlertOutreach'},
  {name: 'earlyAlertReason', url: 'reference/earlyAlertReason'},
  {name: 'earlyAlertReferral', url: 'reference/earlyAlertReferral'},
  {name: 'earlyAlertSuggestion', url: 'reference/earlyAlertSuggestion'},
  {name: 'educationGoal', url: 'reference/educationGoal'},
  {name: 'educationLevel', url: 'reference/educationLevel'},
  {name: 'elective', url: 'reference/elective'},
  {name: 'ethnicity', url: 'reference/ethnicity'},
  {name: 'race', url: 'reference/race'},
  {name: 'sapstatus', url: 'reference/sapstatus'},
  {name: 'financialAidFile', url: 'reference/financialAidFile'},
  {name: 'fundingSource', url: 'reference/fundingSource'},
  {name: 'journalSource', url: 'reference/journalSource'},
  {name: 'journalStep', url: 'reference/journalStep'},
  {name: 'journalTrack', url: 'reference/journalTrack'},
  {name: 'journalStepDetail', url: 'reference/journalStepDetail'},
  {name: 'lassi', url: 'reference/lassi'},
  {name: 'maritalStatus', url: 'reference/maritalStatus'},
  {name: 'militaryAffiliation', url: 'reference/militaryAffiliation'},
  {name: 'enrollmentStatus', url: 'reference/enrollmentStatus'},
  {name: 'completedItem', url: 'reference/completedItem'},
  {name: 'blurb', url: 'blurb'},
  {name: 'registrationLoad', url: 'reference/registrationLoad'},
  {name: 'courseworkHours', url: 'reference/courseworkHours'},
  {name: 'messageTemplate', url: 'reference/messageTemplate'},
  {name: 'studentStatus', url: 'reference/studentStatus'},
  {name: 'veteranStatus', url: 'reference/veteranStatus'},
  {name: 'oauth2Client', url: 'oauth2/client'},
  {name: 'ltiConsumer', url: 'lti/tc'},
  {name: 'permission', url: 'reference/permission'},
  {name: 'person', url: 'person'},
  {name: 'personAppointment', url: 'person/{id}/appointment'},
  {name: 'personAssessment', url: 'person/{id}/test'},
  {name: 'personCaseload', url: 'person/caseload'},
  {name: 'personCaseloadId', url: 'person/{id}/caseload'},
  {name: 'personMasterCaseload', url: 'person/{id}/caseload'},
  {name: 'personChallenge', url: 'person/{id}/challenge'},
  {name: 'personCoach', url: 'person/coach'},
  {name: 'personCoachCurrent', url: 'person/currentCoachesLite'},
  {name: 'personDocument', url: 'person/{id}/document'},
  {name: 'personRegistrationStatusByTerm', url: 'person/{personId}/registrationStatusByTerm'},
  {name: 'personEarlyAlert', url: 'person/{personId}/earlyAlert'},
  {name: 'personEarlyAlertResponse', url: 'person/{personId}/earlyAlert/{earlyAlertId}/response'},
  {name: 'personGoal', url: 'person/{id}/goal'},
  {name: 'personStrength', url: 'person/{id}/strength'},
  {name: 'personJournalEntry', url: 'person/{id}/journalEntry'},
  {name: 'personTask', url: 'person/{id}/task'},
  {name: 'personTaskGroup', url: 'person/{id}/task/group'},
  {name: 'studentActivities', url: 'person/{id}/studentactivity'},
  {name: 'personalityType', url: 'reference/personalityType'},
  {name: 'personTranscript', url: 'person/{id}/transcript'},
  {name: 'personNote', url: 'person/{id}/note'},
  {name: 'personEmailTask', url: 'person/{id}/task/email'},
  {name: 'personViewHistory', url: 'person/{id}/history/print'},
  {name: 'personPrintTask', url: 'person/{id}/task/print'},
  {name: 'studentSearch', url: 'person/students/search'},
  {name: 'directoryPersonSearch', url: 'person/directoryperson/search'},
  {name: 'personSearch', url: 'person/search'},
  {name: 'personMapPlan', url: 'person/{id}/map/plan'},
  {name: 'templatePlan', url: 'reference/map/template'},
  {name: 'placement', url: 'person/{id}/test'},
  {name: 'studentDocument', url: 'person/{id}/studentdocument'},
  {name: 'mapStatusReport', url: 'person/{id}/map/statusReport'},
  {name: 'registrationLoadRanges', url: 'reference/config/?name=registration_load_ranges'},
  {name: 'selfHelpGuides', url: 'selfHelpGuides/search'},
  {name: 'selfHelpGuideQuestions', url: 'selfHelpGuides/selfHelpGuideQuestions'},
  {name: 'personProgramStatus', url: 'person/{id}/programStatus'},
  {name: 'programStatus', url: 'reference/programStatus'},
  {name: 'programStatusChangeReason', url: 'reference/programStatusChangeReason'},
  {name: 'referralSource', url: 'reference/referralSource'},
  {name: 'serviceReason', url: 'reference/serviceReason'},
  {name: 'session', url: 'session'},
  {name: 'server', url: 'server'},
  {name: 'serverDateTime', url: 'server/datetime'},
  {name: 'specialServiceGroup', url: 'reference/specialServiceGroup'},
  {name: 'studentIntakeTool', url: 'tool/studentIntake'},
  {name: 'studentType', url: 'reference/studentType'},
  {name: 'terms', url: 'reference/term'},
  {name: 'course', url: 'reference/course'},
  {name: 'courseRequisites', url: 'reference/courserequisites/{id}'},
  {name: 'program', url: 'reference/program/all'},
  {name: 'programfaceted', url: 'reference/program/facet'},
  {name: 'department', url: 'reference/department/all'},//TODO Change to facets.
  {name: 'division', url: 'reference/division/all'},
  {name: 'catalogYear', url: 'reference/catalogYear/all'},
  {name: 'tag', url: 'reference/tag'},
  {name: 'facetedtag', url: 'reference/tag/facet'},
  {name: 'futureTerms', url: 'reference/term/future'},
  {name: 'termsfaceted', url: 'reference/term/facet'},
  {name: 'weeklyCourseWorkHourRanges', url: 'reference/config/?name=weekly_course_work_hour_ranges'}
];

Ext.onReady(function(){	

    // load the authenticated user
	Ext.Ajax.request({
		url: Ssp.mixin.ApiProperties.getBaseApiUrl() + 'session/getAuthenticatedPerson',
		method: 'GET',
		headers: { 'Accept' : 'application/json','Content-Type': 'application/json' },
		success: function(response){
			var r = Ext.decode(response.responseText);
			var user={};
			
			if (r != null)
			{			
				// authenticated user
			    user=r;

				// configure the application
				Deft.Injector.configure({
					adminSelectedIndex: {
						fn: function(){
				            return new Ssp.model.reference.AdminSelectedIndex({});
				        },
				        singleton: true
					},
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
					contactPerson: {
				    	fn: function(){
				    		return new Ssp.model.ContactPerson({id:""});
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
				    itemSelectorInitializer: {
				        fn: function(){
				            return new Ssp.mixin.controller.ItemSelectorInitializer({});
				        },
                        // Not a singleton b/c this is really intended to work
                        // more like a mixin on a view component, so needs to
                        // allowed to maintain state and smaller scopes than
                        // 'application'.
				        singleton: false
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
				    mapEventUtils:{
				        fn: function(){
				            return new Ssp.util.tools.map.MapEventUtils({});
				    	},
				        singleton: true
				    },				    
				    treeRendererUtils:{
				        fn: function(){
				            return new Ssp.util.TreeRendererUtils({});
				    	},
				        singleton: true
				    },
					util:{
						fn: function(){
							return new Ssp.util.Util({});
						},
						singleton: true
					},
					storeUtils:{
						fn: function(){
							return new Ssp.util.StoreUtils({});
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
			        currentCourse:{
				        fn: function(){
				            return new Ssp.model.external.Course({id:""});
				    	},
				        singleton: true
			        },			        
			        currentJournalStep:{
				        fn: function(){
				            return new Ssp.model.reference.JournalStep({id:""});
				    	},
				        singleton: true
			        },
			        currentStudentDocument: {
				        fn: function(){
				            return new Ssp.model.tool.documents.StudentDocument({id:""});
				    	},
				        singleton: true			        },
			        currentJournalStepDetail:{
				        fn: function(){
				            return new Ssp.model.reference.JournalStepDetail({id:""});
				    	},
				        singleton: true
			        },
					currentMapPlan: {
				        fn: function(){
				            return new Ssp.model.tool.map.Plan({id:""});
				        },
				        singleton: true
				    },
					currentSemesterStores: {
				        fn: function(){
				            return {};
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
					currentStrength:{
				        fn: function(){
				            return new Ssp.model.PersonStrength({id:""});
				    	},
				        singleton: true
			        },
			        currentStudentIntake: {
			        	fn: function(){
			        		return new Ssp.model.tool.studentintake.StudentIntakeForm();
			        	},
				        singleton: true
			        },
			        currentAccommodation: {
			        	fn: function(){
			        		return new Ssp.model.tool.accommodation.AccommodationForm();
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
					currentEarlyAlertResponsesGridStore: {
                        fn: function() {
                            return Ext.create('Ext.data.Store',{
                                model: 'Ssp.model.tool.earlyalert.EarlyAlertResponseGrid',
                                storeId: 'currentEarlyAlertResponsesGridStore'
                            }); 
                        },
                        singleton: true
                    },
			        currentDocument:{
				        fn: function(){
				            return new Ssp.model.tool.documents.StudentDocument({id:""});
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
			        currentSelfHelpGuide:{
				        fn: function(){
				            return new Ssp.model.tool.shg.SelfHelpGuides({id:""});
				    	},
				        singleton: true
			        },		
			        currentSelfHelpGuideQuestions:{
				        fn: function(){
				            return new Ssp.model.tool.shg.SelfHelpGuideQuestions({id:""});
				    	},
				        singleton: true
			        },
					courseTranscriptsStore: {
						fn: function(){
							return Ext.create('Ext.data.Store',{
								model: 'Ssp.model.CourseTranscript'
							});
						},
						singleton: true
					},
					mapStatusReportStore: {
						fn: function(){
							return Ext.create('Ssp.store.MapStatusReports');
						},
						singleton: true
					},	
					mapStatusReportCourseDetailsStore: {
						fn: function(){
							return Ext.create('Ssp.store.MapStatusReportCourseDetails');
						},
						singleton: true
					},	
					mapStatusReportTermDetailsStore: {
						fn: function(){
							return Ext.create('Ssp.store.MapStatusReportTermDetails');
						},
						singleton: true
					},	
					personRegistrationStatusByTermStore: {
						fn: function(){
							return Ext.create('Ssp.store.RegistrationStatusByTerm');
						},
						singleton: true
					},
					mapStatusReportSubstitutionDetailsStore: {
						fn: function(){
							return Ext.create('Ssp.store.MapStatusReportSubstitutionDetails');
						},
						singleton: true
					},					
					currentScheduleStore: {
						fn: function(){
							return Ext.create('Ext.data.Store',{
								model: 'Ssp.model.CourseTranscript'
							});
						},
						singleton: true
					},
					currentDroppedScheduleStore:{
						fn: function(){
							return Ext.create('Ext.data.Store',{
								model: 'Ssp.model.CourseTranscript'
							});
						},
						singleton: true
					},
					
					termTranscriptsStore: {
						fn: function(){
							return Ext.create('Ext.data.Store',{
								model: 'Ssp.model.TermTranscript'
							});
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
				    earlyAlertDetailsReasonsStore: {
				    	fn: function(){
				    		return Ext.create('Ext.data.Store', {
							     model: 'Ssp.model.SimpleItemDisplay',
							     storeId: 'earlyAlertDetailsReasonsStore',
							     sorters: [{property: 'name'}]
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
				            	model: 'Ssp.model.reference.SpecialServiceGroup',
								filterOnLoad: true,
								filters: [{property:"objectStatus", value:'ACTIVE'}],
								sorters:  [{
							         property: 'name',
							         direction: 'ASC'
							     }]
				            });
				    	},
				        singleton: true
			        },
			        profileReferralSourcesStore:{
				        fn: function(){
				            return Ext.create('Ext.data.Store',{
				            	model: 'Ssp.model.reference.ReferralSource',
								filterOnLoad: true,
								filters: [{property:"objectStatus", value:'ACTIVE'}],
								sorters:  [{
							         property: 'name',
							         direction: 'ASC'
							     }]
				            });
				    	},
				        singleton: true
			        },
			        profileServiceReasonsStore:{
				        fn: function(){
				            return Ext.create('Ext.data.Store',{
				            	model: 'Ssp.model.reference.ServiceReason',
								filterOnLoad: true,
								filters: [{property:"objectStatus", value:'ACTIVE'}],
								sorters:  [{
							         property: 'name',
							         direction: 'ASC'
							     }]
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
					currentMessageTemplate:{
				        fn: function(){
				            return new Ssp.model.MessageTemplates({id:""});
				    	},
				        singleton: true
			        },
					addTasksStore:{
				        fn: function(){
				            return new Ssp.store.AddTasks();
				    	},
				        singleton: true
			        },
			        // STORES
					abstractReferencesStore: 'Ssp.store.reference.AbstractReferences',
				    adminTreeMenusStore: 'Ssp.store.admin.AdminTreeMenus',
				    anticipatedStartTermsStore: 'Ssp.store.reference.AnticipatedStartTerms',
					campusesStore: 'Ssp.store.reference.Campuses',
					campusesAllStore: {
				    	fn: function(){
				    		return Ext.create('Ssp.store.reference.Campuses', {
							     storeId: 'campusesAllStore',		
							     extraParams: {status: "ALL"}
							 });
				    	},
				    	singleton: true
				    },
					campusesAllUnpagedStore: {
				    	fn: function(){
				    		return Ext.create('Ssp.store.reference.Campuses', {
							     storeId: 'campusesAllUnpagedStore',		
							     extraParams: {status: "ALL", limit: "-1"}
							 });
				    	},
				    	singleton: true
				    },
					campusEarlyAlertRoutingsStore: 'Ssp.store.reference.CampusEarlyAlertRoutings',
					campusServicesStore: 'Ssp.store.reference.CampusServices',
					caseloadStore: {
				    	fn: function(){
				    		return Ext.create('Ssp.store.Caseload', {
							     storeId: 'caseloadStoreMain',		
							     params : {
										page : 0,
										start : 0,
										limit : 100
									}	
							 });
				    	},
				    	singleton: true
				    },
					reassignCaseloadStagingStore: 'Ssp.store.Caseload',
					reassignCaseloadStore: 'Ssp.store.Caseload',
					contactPersonStore: 'Ssp.store.ContactPerson',
					challengesStore: 'Ssp.store.reference.Challenges',
					challengesAllStore: {
				    	fn: function(){
				    		return Ext.create('Ssp.store.reference.Challenges', {
							     extraParams: {status: "ALL"}
							 });
				    	},
				    	singleton: true
				    },
					challengesAllUnpagedStore: {
				    	fn: function(){
				    		return Ext.create('Ssp.store.reference.Challenges', {
							     extraParams: {limit: "-1"}
							 });
				    	},
				    	singleton: true
				    },
					challengeCategoriesStore: 'Ssp.store.reference.ChallengeCategories',
					challengeCategoriesAllStore: {
				    	fn: function(){
				    		return Ext.create('Ssp.store.reference.ChallengeCategories', {
							     extraParams: {status: "ALL"}
							 });
				    	},
				    	singleton: true
				    },
					challengeReferralsStore: 'Ssp.store.reference.ChallengeReferrals',
					challengeReferralsAllStore: {
				    	fn: function(){
				    		return Ext.create('Ssp.store.reference.ChallengeReferrals', {
							     extraParams: {status: "ALL"}
							 });
				    	},
				    	singleton: true
				    },
				    challengeReferralsAllUnpagedStore: {
				    	fn: function(){
				    		return Ext.create('Ssp.store.reference.ChallengeReferrals', {
							     extraParams: {status: "ALL", limit:"-1"}
							 });
				    	},
				    	singleton: true
				    },
				    childCareArrangementsStore: 'Ssp.store.reference.ChildCareArrangements',
					childCareArrangementsAllStore: {
				    	fn: function(){
				    		return Ext.create('Ssp.store.reference.ChildCareArrangements', {
							     storeId: 'childCareArrangementsAllStore',		
							     extraParams: {status: "ALL"}
							 });
				    	},
				    	singleton: true
				    },
				    citizenshipsStore: 'Ssp.store.reference.Citizenships',
					citizenshipsAllStore: {
				    	fn: function(){
				    		return Ext.create('Ssp.store.reference.Citizenships', {
							     storeId: 'citizenshipsAllStore',		
							     extraParams: {status: "ALL"}
							 });
				    	},
				    	singleton: true
				    },
			    	coachesStore: 'Ssp.store.Coaches',
			    	allCoachesStore: 'Ssp.store.CoachesAll',
			    	allCoachesCurrentStore: 'Ssp.store.CoachesAllCurrent',
				    confidentialityDisclosureAgreementsStore: 'Ssp.store.reference.ConfidentialityDisclosureAgreements',
					configurationOptionsStore: 'Ssp.store.reference.ConfigurationOptions',
					configurationOptionsUnpagedStore: 
					{
				    	fn: function(){
				    		return Ext.create('Ssp.store.reference.ConfigurationOptionsUnpaged', {
							     storeId: 'configurationOptionsUnpagedStore',		
							     extraParams: {limit: "-1"}
							 });
				    	},
				    	singleton: true
				    },
					configStore: 
					{
				    	fn: function(){
				    		return Ext.create('Ssp.store.reference.ConfigurationOptionsUnpaged', {
							     storeId: 'configurationOptionsUnpagedStore',		
							     extraParams: {limit: "-1"}
							 });
				    	},
				    	singleton: true
				    },				    
				    colorsStore: {
				    	fn: function(){
				    		return Ext.create('Ssp.store.reference.Colors', {
							     storeId: 'colorsStore'						    
							 });
				    	},
				    	singleton: true
				    }, 
				    colorsUnpagedStore: {
				    	fn: function(){
				    		return Ext.create('Ssp.store.reference.Colors', {
							     storeId: 'colorsUnpagedStore',		
							     extraParams: {limit: "-1"}
							 });
				    	},
				    	singleton: true
				    },
				    colorsAllStore: {
				    	fn: function(){
				    		return Ext.create('Ssp.store.reference.Colors', {
							     storeId: 'colorsAllStore',		
							     extraParams: {status: "ALL"}
							 });
				    	},
				    	singleton: true
				    },
				    colorsAllUnpagedStore: {
				    	fn: function(){
				    		return Ext.create('Ssp.store.reference.Colors', {
							     storeId: 'colorsAllUnpagedStore',		
							     extraParams: {status: "ALL", limit: "-1"}
							 });
				    	},
				    	singleton: true
				    },
				    confidentialityLevelOptionsStore: {
				    	fn: function(){
				    		return Ext.create('Ssp.store.reference.ConfidentialityLevelOptions', {
							     storeId: 'confidentialityLevelOptionsStore'		
							 });
				    	},
				    	singleton: true
				    },				    
				    confidentialityLevelsStore: 'Ssp.store.reference.ConfidentialityLevels',
					confidentialityLevelsAllStore: {
						fn: function(){
							return Ext.create('Ssp.store.reference.ConfidentialityLevels', {
								storeId: 'confidentialityLevelsAllStore',
								extraParams: {status: "ALL"}
							});
						},
						singleton: true
					},
					confidentialityLevelsAllUnpagedStore: {
						fn: function(){
							return Ext.create('Ssp.store.reference.ConfidentialityLevels', {
								storeId: 'confidentialityLevelsAllUnpagedStore',
								extraParams: {status: "ALL", limit: "-1"}
							});
						},
						singleton: true
					},
					
				    //disabilityAccommodationsStore: 'Ssp.store.reference.DisabilityAccommodations',
					disabilityAccommodationsStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.DisabilityAccommodations', {});
					    },
					    singleton: true
					},
					disabilityAccommodationsAllStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.DisabilityAccommodations', {
							     extraParams: {status: "ALL"}
							 });
					    },
					    singleton: true
					},
				    disabilityAgenciesStore: 'Ssp.store.reference.DisabilityAgencies',
					disabilityAgenciesAllStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.DisabilityAgencies', {
							     extraParams: {status: "ALL"}
							 });
					    },
					    singleton: true
					},
				    disabilityStatusesStore: 'Ssp.store.reference.DisabilityStatuses',
					disabilityStatusesAllStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.DisabilityStatuses', {
							     extraParams: {status: "ALL"}
							 });
					    },
					    singleton: true
					},
				    disabilityTypesStore: 'Ssp.store.reference.DisabilityTypes',
					disabilityTypesAllStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.DisabilityTypes', {
							     extraParams: {status: "ALL"}
							 });
					    },
					    singleton: true
					},
				    earlyAlertOutcomesStore: 'Ssp.store.reference.EarlyAlertOutcomes',
					earlyAlertOutcomesAllStore: {
						fn: function(){
							return Ext.create('Ssp.store.reference.EarlyAlertOutcomes', {
								storeId: 'earlyAlertOutcomesAllStore',
								extraParams: {status: "ALL"}
							});
						},
						singleton: true
					},
				    earlyAlertOutcomesAllUnpagedStore: {
						fn: function(){
							return Ext.create('Ssp.store.reference.EarlyAlertOutcomes', {
								storeId: 'earlyAlertOutcomesAllUnpagedStore',
								extraParams: {status: "ALL", limit: "-1"}
							});
						},
						singleton: true
					},
				    earlyAlertOutreachesStore: 'Ssp.store.reference.EarlyAlertOutreaches',
					earlyAlertOutreachesAllStore: {
						fn: function(){
							return Ext.create('Ssp.store.reference.EarlyAlertOutreaches', {
								storeId: 'earlyAlertOutreachesAllStore',
								extraParams: {status: "ALL"}
							});
						},
						singleton: true
					},
					earlyAlertOutreachesAllUnpagedStore: {
						fn: function(){
							return Ext.create('Ssp.store.reference.EarlyAlertOutreaches', {
								storeId: 'earlyAlertOutreachesAllUnpagedStore',
								extraParams: {status: "ALL", limit: "-1"}
							});
						},
						singleton: true
					},
					earlyAlertReasonsStore: 'Ssp.store.reference.EarlyAlertReasons',
					earlyAlertReasonsAllStore: {
						fn: function(){
							return Ext.create('Ssp.store.reference.EarlyAlertReasons', {
								storeId: 'earlyAlertReasonsAllStore',
								extraParams: {status: "ALL"}
							});
						},
						singleton: true
					},
					earlyAlertReasonsAllUnpagedStore: {
						fn: function(){
							return Ext.create('Ssp.store.reference.EarlyAlertReasons', {
								storeId: 'earlyAlertReasonsAllUnpagedStore',
								extraParams: {status: "ALL", limit: "-1"}
							});
						},
						singleton: true
					},
					earlyAlertReferralsStore: 'Ssp.store.reference.EarlyAlertReferrals',
					earlyAlertReferralsAllStore: {
						fn: function(){
							return Ext.create('Ssp.store.reference.EarlyAlertReferrals', {
								storeId: 'earlyAlertReferralsAllStore',
								extraParams: {status: "ALL"}
							});
						},
						singleton: true
					},
					earlyAlertReferralsAllUnpagedStore: {
						fn: function(){
							return Ext.create('Ssp.store.reference.EarlyAlertReferrals', {
								storeId: 'earlyAlertReferralsAllUnpagedStore',
								extraParams: {status: "ALL", limit: "-1"}
							});
						},
						singleton: true
					},
					earlyAlertReferralsBindStore: 'Ssp.store.reference.EarlyAlertReferralsBind',
					earlyAlertsStore: 'Ssp.store.EarlyAlerts',
					earlyAlertSuggestionsStore: 'Ssp.store.reference.EarlyAlertSuggestions',
					earlyAlertSuggestionsAllStore: {
						fn: function(){
							return Ext.create('Ssp.store.reference.EarlyAlertSuggestions', {
								storeId: 'earlyAlertSuggestionsAllStore',
								extraParams: {status: "ALL"}
							});
						},
						singleton: true
					},
					earlyAlertSuggestionsAllUnpagedStore: {
						fn: function(){
							return Ext.create('Ssp.store.reference.EarlyAlertSuggestions', {
								storeId: 'earlyAlertSuggestionsAllUnpagedStore',
								extraParams: {status: "ALL", limit: "-1"}
							});
						},
						singleton: true
					},
				    educationGoalsStore: 'Ssp.store.reference.EducationGoals',
					educationGoalsAllStore: {
						fn: function(){
							return Ext.create('Ssp.store.reference.EducationGoals', {
								storeId: 'educationGoalsAllStore',
								extraParams: {status: "ALL"}
							});
						},
						singleton: true
					},
			    	educationLevelsStore: 'Ssp.store.reference.EducationLevels',
					educationLevelsAllStore: {
						fn: function(){
							return Ext.create('Ssp.store.reference.EducationLevels', {
								storeId: 'educationLevelsAllStore',
								extraParams: {status: "ALL"}
							});
						},
						singleton: true
					},
			    	electivesStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.Electives', {
							     storeId: 'electivesStore',
							     extraParams: {sort: "sortOrder", sortDirection:'ASC' }
							 });
					    },
					    singleton: true
					},
					electivesUnpagedStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.Electives', {
							     storeId: 'electivesUnpagedStore',
							     extraParams: {limit: "-1"}
							 });
					    },
					    singleton: true
					},
					electivesAllStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.Electives', {
							     storeId: 'electivsAllStore',		
							     extraParams: {status: "ALL"}
							 });
					    },
					    singleton: true
					},
					electivesAllUnpagedStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.Electives', {
							     storeId: 'electivsAllUnpagedStore',		
							     extraParams: {status: "ALL", limit: "-1"}
							 });
					    },
					    singleton: true
					},
					
					planTemplatesSummaryStore: {
						fn: function(){
							return Ext.create('Ssp.store.PlanTemplatesSummary', {
							     storeId: 'planTemplatesSummaryStore',		
							     extraParams: {sort: "name", status: "ALL", limit: "-1"}
							});
						},
						singleton: true
					}, 
			    	employmentShiftsStore: 'Ssp.store.reference.EmploymentShifts',
			    	ethnicitiesStore: 'Ssp.store.reference.Ethnicities',
					ethnicitiesAllStore: {
						fn: function(){
							return Ext.create('Ssp.store.reference.Ethnicities', {
								storeId: 'ethnicitiesAllStore',
								extraParams: {status: "ALL"}
							});
						},
						singleton: true
					},
					racesAllStore: {
						fn: function(){
							return Ext.create('Ssp.store.reference.Races', {
								extraParams: {status: "ALL"}
							});
						},
						singleton: true
					},
					racesAllUnpagedStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.Races', {
							     extraParams: {status: "ALL", limit: "-1"}
							 });
					    },
					    singleton: true
					},	
					sapStatusesAllStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.SapStatuses', {
							     extraParams: {status: "ALL"}
							 });
					    },
					    singleton: true
					},
					sapStatusesAllUnpagedStore: {
						fn: function(){
							return Ext.create('Ssp.store.reference.SapStatuses', {
								extraParams: {status: "ALL", limit: "-1"}
							});
						},
						singleton: true
					},
					sapStatusesActiveUnpagedStore: {
						fn: function(){
							return Ext.create('Ssp.store.reference.SapStatuses', {
								extraParams: {status: "ACTIVE", limit: "-1"}
							});
						},
						singleton: true
					},
					financialAidFilesAllStore: {
						fn: function(){
							return Ext.create('Ssp.store.reference.FinancialAidFiles', {
								extraParams: {status: "ALL"}
							});
						},
						singleton: true
					},
					financialAidFilesAllUnpagedStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.FinancialAidFiles', {
							     extraParams: {status: "ALL", limit: "-1"}
							 });
					    },
					    singleton: true
					},
			    	fundingSourcesStore: 'Ssp.store.reference.FundingSources',
					fundingSourcesAllStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.FundingSources', {
							     extraParams: {status: "ALL"}
							 });
					    },
					    singleton: true
					},	
			    	gendersStore: 'Ssp.store.reference.Genders',
				    goalsStore: 'Ssp.store.Goals',
					strengthsStore: 'Ssp.store.Strengths',
					// addTasksStore: 'Ssp.store.AddTasks',
			    	journalEntriesStore: 'Ssp.store.JournalEntries',
			    	journalEntriesUnpagedStore: 'Ssp.store.JournalEntriesUnpaged',
			    	journalEntryDetailsStore: 'Ssp.store.JournalEntryDetails',
			    	journalSourcesStore: 'Ssp.store.reference.JournalSources',
			    	journalSourcesAllStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.JournalSources', {
							     extraParams: {status: "ALL"}
							 });
					    },
					    singleton: true
					},	
					journalSourcesAllUnpagedStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.JournalSources', {
							     extraParams: {status: "ALL", limit:"-1"}
							 });
					    },
					    singleton: true
					},
					journalSourcesUnpagedStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.JournalSources', {
							     extraParams: {limit:"-1"}
							 });
					    },
					    singleton: true
					},
			        journalStepsStore: 'Ssp.store.reference.JournalSteps',
					journalStepsAllStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.JournalSteps', {
							     extraParams: {status: "ALL"}
							 });
					    },
					    singleton: true
					},	
					journalStepsAllUnpagedStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.JournalSteps', {
							     extraParams: {status: "ALL", limit: "-1"}
							 });
					    },
					    singleton: true
					},	
			        journalDetailsStore: 'Ssp.store.reference.JournalStepDetails',
					journalDetailsAllStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.JournalStepDetails', {
							     extraParams: {status: "ALL"}
							 });
					    },
					    singleton: true
					},	
					journalDetailsAllUnpagedStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.JournalStepDetails', {
							     extraParams: {status: "ALL", limit:"-1"}
							 });
					    },
					    singleton: true
					},	
			        journalTracksStore: 'Ssp.store.reference.JournalTracks',
			        journalTracksUnpagedStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.JournalTracks', {
							     extraParams: {limit:"-1"}
							 });
					    },
					    singleton: true
					},
					journalTracksAllStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.JournalTracks', {
							     extraParams: {status: "ALL"}
							 });
					    },
					    singleton: true
					},
					journalTracksAllUnpagedStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.JournalTracks', {
							     extraParams: {status: "ALL", limit:"-1"}
							 });
					    },
					    singleton: true
					},
			        lassisStore: 'Ssp.store.reference.Lassis',
			        maritalStatusesStore: 'Ssp.store.reference.MaritalStatuses',
					maritalStatusesAllStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.MaritalStatuses', {
							     extraParams: {status: "ALL"}
							 });
					    },
					    singleton: true
					},	
			    	militaryAffiliationsStore: 'Ssp.store.reference.MilitaryAffiliations',
					militaryAffiliationsAllStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.MilitaryAffiliations', {
							     extraParams: {status: "ALL"}
							 });
					    },
					    singleton: true
					},	
			    	registrationLoadsStore: 'Ssp.store.reference.RegistrationLoads',
					registrationLoadsAllStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.RegistrationLoads', {
							     extraParams: {status: "ALL"}
							 });
					    },
					    singleton: true
					},	
			    	courseworkHoursStore: 'Ssp.store.reference.CourseworkHours',
					courseworkHoursAllStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.CourseworkHours', {
							     extraParams: {status: "ALL"}
							 });
					    },
					    singleton: true
					},	
			    	enrollmentStatusesStore: 'Ssp.store.reference.EnrollmentStatuses',
			    	completedItemStore: 
			    	{
						fn: function(){
							return Ext.create('Ssp.store.reference.CompletedItem', {
								storeId: 'completedItemStore',
								extraParams: {status: "ALL", limit: -1, start: null}
							});
						},
						singleton: true
					},
			    	textStore: 
			    	{
						fn: function(){
							return Ext.create('Ssp.store.reference.Texts', {
								storeId: 'textStore',
								extraParams: {status: "ALL", limit: -1, start: null}
							});
						},
						singleton: true
					},
			    	sspTextStore: 
			    	{
						fn: function(){
							return Ext.create('Ssp.store.reference.Texts', {
								storeId: 'sspTextStore',
								extraParams: {status: "ALL", limit: -1, start: null}
							});
						},
						singleton: true
					},
					messageTemplatesStore: 'Ssp.store.reference.MessageTemplates',
			    	personalityTypesStore: 'Ssp.store.reference.PersonalityTypes',
			    	placementStore: 'Ssp.store.Placement',
			    	planStore: 'Ssp.store.Plan',			    	
					programStatusesStore: 'Ssp.store.reference.ProgramStatuses',
			    	programStatusChangeReasonsStore: 'Ssp.store.reference.ProgramStatusChangeReasons',
					programStatusChangeReasonsAllStore: {
						fn: function(){
							return Ext.create('Ssp.store.reference.ProgramStatusChangeReasons', {
								storeId: 'programStatusChangeReasonsAllStore',
								extraParams: {status: "ALL"}
							});
						},
						singleton: true
					},
				    referralSourcesStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.ReferralSources', { });
					    },
					    singleton: true
					},
					referralSourcesAllStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.ReferralSources', {
					    		extraParams: {status: "ALL"}
					    	});
					    },
					    singleton: true
					},
					referralSourcesAllUnpagedStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.ReferralSources', {
					    		extraParams: {status: "ALL", limit: "-1"}
					    	});
					    },
					    singleton: true
					},
				    searchStore: 'Ssp.store.Search',
				    studentsSearchStore: {
				    	fn: function(){
				    		return Ext.create('Ssp.store.StudentsSearch', {
							     storeId: 'studenSearchStoreMain',		
							     extraParams: {limit: "50"}
							 });
				    	},
				    	singleton: true
				    },
				    directoryPersonSearchStore: {
				    	fn: function(){
				    		return Ext.create('Ssp.store.DirectoryPersonSearch', {
							     storeId: 'directoryPersonSearchStore',		
							     extraParams: {limit: "50"}
							 });
				    	},
				    	singleton: true
				    },
					searchChallengeReferralStore: 'Ssp.store.SearchChallengeReferral',
				    selfHelpGuidesStore: 'Ssp.store.reference.SelfHelpGuides',
				    selfHelpGuidesAllUnpagedStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.SelfHelpGuides', {
					    		extraParams: {status: "ALL", limit: "-1"}
					    	});
					    },
					    singleton: true
					},
					selfHelpGuidesAllStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.SelfHelpGuides', {
					    		extraParams: {status: "ALL"}
					    	});
					    },
					    singleton: true
					},
				    selfHelpGuideQuestionsStore: 'Ssp.store.reference.SelfHelpGuideQuestions',
				    serviceReasonsStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.ServiceReasons', {
					    	});
					    },
					    singleton: true
					},
					serviceReasonsAllStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.ServiceReasons', {
					    		extraParams: {status: "ALL"}
					    	});
					    },
					    singleton: true
					},
				    serviceReasonsAllUnpagedStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.ServiceReasons', {
					    		extraParams: {status: "ALL", limit: "-1"}
					    	});
					    },
					    singleton: true
					},
				    specialServiceGroupsStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.SpecialServiceGroups', { });
					    },
					    singleton: true
					},
					specialServiceGroupsAllStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.SpecialServiceGroups', {
								extraParams: {status: "ALL"}
								 });
					    },
					    singleton: true
					},
				    specialServiceGroupsAllUnpagedStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.SpecialServiceGroups', {
							     extraParams: {status: "ALL", limit: "-1"}
							 });
					    },
					    singleton: true
					},
				    specialServiceGroupsActiveUnpagedStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.SpecialServiceGroups', {
							     extraParams: {status: "ACTIVE", limit: "-1"}
							 });
					    },
					    singleton: true
					},					
				    statesStore: 'Ssp.store.reference.States',
				    studentDocumentsStore: 'Ssp.store.StudentDocuments',
				    studentsStore: 'Ssp.store.Students',
				    studentStatusesStore: 'Ssp.store.reference.StudentStatuses',
					studentStatusesAllStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.StudentStatuses', {
							     extraParams: {status: "ALL"}
							 });
					    },
					    singleton: true
					},
				    studentTypesStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.StudentTypes', {});
					    },
					    singleton: true
					},
					studentTypesAllUnpagedStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.StudentTypes', {
							     extraParams: {status: "ALL", limit: "-1"}
							 });
					    },
					    singleton: true
					},
					studentTypesAllStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.StudentTypes', {
							     extraParams: {status: "ALL"}
							 });
					    },
					    singleton: true
					},
				    registrationLoadRangesStore: 'Ssp.store.reference.RegistrationLoadRanges', 
				    weeklyCourseWorkHourRangesStore: 'Ssp.store.reference.WeeklyCourseWorkHourRanges',
				    termsStore:'Ssp.store.external.Terms',
					termsFacetedStore:'Ssp.store.external.TermsFaceted',
				    programsStore:'Ssp.store.external.Programs',
					programsFacetedStore:'Ssp.store.external.ProgramsFaceted',
				    divisionsStore:'Ssp.store.external.Divisions',
				    catalogYearsStore:'Ssp.store.external.CatalogYears',
				    personNotesStore:'Ssp.store.external.PersonNotes',
				    departmentsStore:'Ssp.store.external.Departments',
					coursesStore:'Ssp.store.external.Courses',
					tagsStore: 'Ssp.store.reference.Tags',
					facetedTagsStore: 'Ssp.store.reference.FacetedTags',
				    tasksStore: 'Ssp.store.Tasks',
				    studentActivitiesStore: 'Ssp.store.StudentActivities',
				    toolsStore: 'Ssp.store.Tools',
			    	veteranStatusesStore: 'Ssp.store.reference.VeteranStatuses',
					veteranStatusesAllStore: {
			    		fn: function(){
					    	return Ext.create('Ssp.store.reference.VeteranStatuses', {
							     extraParams: {status: "ALL"}
							 });
					    },
					    singleton: true
					},
			        planStatusStore: 'Ssp.store.PlanStatus',
			        personTableTypesStore: 'Ssp.store.PersonTableTypes',
			        financialAidSAPStatus: 'Ssp.store.FinancialAidSAPStatus',
			        mapStatusStore: 'Ssp.store.MAPStatus',
			        earlyAlertResponseLateStore: 'Ssp.store.EarlyAlertResponseLate',
			        currentlyRegisteredStore: 'Ssp.store.CurrentlyRegistered',
			        oauth2ClientsStore: 'Ssp.store.OAuth2Clients',
			        ltiConsumersStore: 'Ssp.store.LTIConsumers',
			        ltiSspUserFieldNamesStore: 'Ssp.store.LtiSspUserFieldNames',
			        permissionsStore: 'Ssp.store.Permissions',

			        
			        	
			        // SERVICES
			        appointmentService: 'Ssp.service.AppointmentService',
			        assessmentService: 'Ssp.service.AssessmentService',
			        campusService: 'Ssp.service.CampusService',
			        campusEarlyAlertRoutingService: 'Ssp.service.CampusEarlyAlertRoutingService',
			        caseloadService: 'Ssp.service.CaseloadService',
			        confidentialityDisclosureAgreementService: 'Ssp.service.ConfidentialityDisclosureAgreementService',
			        accommodationService: 'Ssp.service.AccommodationService',
			        earlyAlertService: 'Ssp.service.EarlyAlertService',
			        earlyAlertReferralService: 'Ssp.service.EarlyAlertReferralService',
			        earlyAlertResponseService: 'Ssp.service.EarlyAlertResponseService',
			        journalEntryService: 'Ssp.service.JournalEntryService',
			        personService: 'Ssp.service.PersonService',
			        placementService: 'Ssp.service.PlacementService',
					personNoteService: 'Ssp.service.PersonNoteService',
			        personProgramStatusService: 'Ssp.service.PersonProgramStatusService',
			        programStatusService: 'Ssp.service.ProgramStatusService',
			        referralSourceService: 'Ssp.service.ReferralSourceService',
			        searchService: 'Ssp.service.SearchService',
					searchChallengeReferralService: 'Ssp.service.SearchChallengeReferralService',
			        specialServiceGroupService: 'Ssp.service.SpecialServiceGroupService',
					serviceReasonsService: 'Ssp.service.ServiceReasonsService',
			        studentIntakeService: 'Ssp.service.StudentIntakeService',
			        transcriptService: 'Ssp.service.TranscriptService',
			        mapPlanService: 'Ssp.service.MapPlanService',
			        studentActivityService: 'Ssp.service.StudentActivityService',
			        courseService: 'Ssp.service.CourseService'
				});


				// Do not use 'autoCreateViewport: true' here. It will trigger
				// initialization of the Deft IoC container before the
				// Application exists, so some managed components may be only
				// partially initialized. This is particularly problematic for
				// AppEventsController which needs a reference to the
				// Application. If it does not have that reference, the first
				// components to load (those associated with Viewport) cannot
				// register Application-scoped events during their
				// initialization. The resulting deferred event listener
				// binding has been the direct cause of subtle bugs.
				Ext.application({
				    name: 'Ssp',
				    appFolder: Ext.Loader.getPath('Ssp'),
				    launch: function( app ) {
				    	var me=this;
				    	Deft.Injector.resolve("appEventsController").setApp(me);
				    	
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
				    		simpleSortMode: true,
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
				    		if (fl){
				    			this.labelStyle=Ssp.util.Constants.SSP_LABEL_STYLE;
				    		}
				    		if (ab===false && fl){
				    			this.fieldLabel += Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY;
				    		}
				    	});		

				    	/*
				    	 * Provide global asterisks next to required field containers
				    	 */
				    	Ext.Function.interceptAfter(Ext.form.FieldContainer.prototype,'initComponent', function(){
				    		var fl=this.fieldLabel, ab=this.allowBlank;
				    		if (fl){
				    			this.labelStyle=Ssp.util.Constants.SSP_LABEL_STYLE;
				    		}
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

				    	//http://stackoverflow.com/questions/9704913/confirm-password-validator-extjs-4
				    	Ext.apply(Ext.form.field.VTypes,{
				    	    passwordConfirm : function(val, field) {
				    	        if (field.initialPassField) {
				    	            var pwd = Ext.getCmp(field.initialPassField);
				    	            return (val === pwd.getValue());
				    	        }
				    	        return true;
				    	    },
				    	    passwordConfirmText : 'Passwords/secrets do not match'
				    	});
				
						Ext.apply(Ext.form.field.VTypes,{
				    	    multiemail: function(val, field) {
								Ext.form.field.VTypes.multiemailInvalidPositions = [];
								if(val && val.length > 0){
									var emailAddresses = val.split(',');
									var valid = true;
									var i = 0;
									Ext.Array.each( emailAddresses, function(emailAddress, index){
										// if it is a string and it contains something other than whitespace
										if(emailAddress && !(/^\s*$/).test(emailAddress)){
											if(!Ext.form.field.VTypes.email(emailAddress.trim())){
												valid = false;
												Ext.form.field.VTypes.multiemailInvalidPositions[i] = index;
											}
										}
										i++;
									});
									return valid;
								}
								return true;
							},
							multiemailInvalidPositions: [],
							multiemailText: function(){return 'Email(s) at position(s) ' + Ext.form.field.VTypes.multiemailInvalidPositions.join(",") + "are invalid"},
							multiemailMask: /[a-z0-9_.-@,\s]/i
						
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

				    	// Since we're not using 'autoCreateViewport: true',
				    	// we need to create the default view ourselves.
				    	// (Frankly not sure exactly what the relationship is
				    	// between this and the xtype-based lookup of the same
				    	// component type immediately above. But you'll get
				    	// a blank screen without this explicit create.
				    	Ext.create( "Ssp.view.Viewport");
				    	
				   }
				});
				
			}else{
				Ext.Msg.alert('Error','Unable to determine authenticated user. Please see your system administrator for assistance.');
			}
		}
	}, this);		
	
});
