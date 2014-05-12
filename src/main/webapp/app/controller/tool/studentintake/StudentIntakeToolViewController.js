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
Ext.define('Ssp.controller.tool.studentintake.StudentIntakeToolViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson',
        appEventsController: 'appEventsController',
        challengesStore: 'challengesStore',
        completedItemStore: 'completedItemStore',
    	childCareArrangementsStore: 'childCareArrangementsStore',
    	citizenshipsStore: 'citizenshipsStore',
    	educationGoalsStore: 'educationGoalsStore',
    	educationLevelsStore: 'educationLevelsStore',
    	employmentShiftsStore: 'employmentShiftsStore',
    	ethnicitiesStore: 'ethnicitiesStore',
		racesStore: 'racesAllUnpagedStore',
    	formUtils: 'formRendererUtils',
    	fundingSourcesStore: 'fundingSourcesStore',
    	gendersStore: 'gendersStore',
    	maritalStatusesStore: 'maritalStatusesStore',
    	militaryAffiliationsStore: 'militaryAffiliationsStore',
        personLite: 'personLite',
        person: 'currentPerson',
        statesStore: 'statesStore',
        service: 'studentIntakeService',
        studentStatusesStore: 'studentStatusesStore',
        studentIntake: 'currentStudentIntake',
    	veteranStatusesStore: 'veteranStatusesStore',
    	registrationLoadsStore: 'registrationLoadsStore',
    	courseworkHoursStore:'courseworkHoursStore',
    	textStore:'sspTextStore'
    }, 
    config: {
    	studentIntakeForm: null
    },
    control: {
		'saveButton': {
			click: 'onSaveClick'
		},
		
    	'cancelButton': {
    		click: 'onCancelClick'
    	},
    	
    	saveSuccessMessage: '#saveSuccessMessage'
	},
    
	init: function() {
		var me=this;	
		
		me.textStore.load();
		// Load the views dynamically
		// otherwise duplicate id's will be registered
		// on cancel
		me.initStudentIntakeViews();
	
		// This enables mapped text fields and mapped text areas
		// to be shown or hidden upon selection from a parent object
		// such as a dynamic check box.
		me.appEventsController.getApplication().addListener('dynamicCompChange', function( comp ){
			var tfArr = Ext.ComponentQuery.query('.mappedtextfield');
			var taArr = Ext.ComponentQuery.query('.mappedtextarea');
			
			// show or hide mapped text fields
			Ext.each(tfArr,function(item, index){
				if (comp.id==item.parentId)
				{
					if(comp.checked)
					{
						item.show();
						Ext.apply(item,{allowBlank:false});
					}else{
						item.hide();
						Ext.apply(item,{allowBlank:true});
					}
				}	
			},this);
			
			// show or hide mapped text area components
			Ext.each(taArr,function(item, index){
				if (comp.id==item.parentId)
				{
					if(comp.checked)
					{
						item.show();
					}else{
						item.hide();
					}
				}	
			},this);
		},me);
		
		// display loader
		me.getView().setLoading( true );
		
		me.service.get(me.personLite.get('id'),{
			success: me.getStudentIntakeSuccess,
			failure: me.getStudentIntakeFailure,
			scope: me
		});
		return me.callParent(arguments);
    },

    destroy: function() {
    	//this.appEventsController.removeEvent({eventName: 'dynamicCompChange', callBackFunc: this.onDynamicCompChange, scope: this});

        return this.callParent( arguments );
    },     
    
    initStudentIntakeViews: function(){
    	var me=this;
    	var items = [ Ext.createWidget('tabpanel', {
	        width: '100%',
	        height: '100%',
	        activeTab: 0,
			border: 0,
	        items: [ { title: me.textStore.getValueByCode('intake.tab1.label')+Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY,
	        		   autoScroll: true,
	        		   items: [{xtype: 'studentintakepersonal'}]
	        		},{
	            		title: me.textStore.getValueByCode('intake.tab2.label'),
	            		autoScroll: true,
	            		items: [{xtype: 'studentintakedemographics'}]
	        		},{
	            		title: me.textStore.getValueByCode('intake.tab3.label'),
	            		autoScroll: true,
	            		items: [{xtype: 'studentintakeeducationplans'}]
	        		},{
	            		title: me.textStore.getValueByCode('intake.tab4.label'),
	            		autoScroll: true,
	            		items: [{xtype: 'studentintakeeducationlevels'}]
	        		},{
	            		title: me.textStore.getValueByCode('intake.tab5.label'),
	            		autoScroll: true,
	            		items: [{xtype: 'studentintakeeducationgoals'}]
	        		},{
	            		title: me.textStore.getValueByCode('intake.tab6.label'),
	            		autoScroll: true,
	            		items: [{xtype: 'studentintakefunding'}]
	        		},{
	            		title: me.textStore.getValueByCode('intake.tab7.label'),
	            		autoScroll: true,
	            		hidden: !me.authenticatedPerson.hasAccess('STUDENT_INTAKE_CHALLENGE_TAB'),
	            		items: [{xtype: 'studentintakechallenges'}]
	        		},{
	            		title: me.textStore.getValueByCode('intake.tab8.label'),
	            		autoScroll: true,
	            		items: [{xtype: 'studentintakechecklist'}]
	        		}]
		    })
	    
		];
    	
    	me.getView().add( items );
    },
    
    getStudentIntakeSuccess: function( r, scope ){
    	var me=scope;
    	var studentIntakeClass;
		
    	// hide the loader
    	me.getView().setLoading( false );
    	
    	if ( r != null )
    	{  		
        	studentIntakeClass = Ext.ModelManager.getModel('Ssp.model.tool.studentintake.StudentIntakeForm');
    		me.studentIntake.data = studentIntakeClass.getProxy().getReader().read( r ).records[0].data;
    		me.buildStudentIntake( me.studentIntake );    		
    	}else{
    		Ext.Msg.alert('Error','There was an error loading the Student Intake form for this student.');
    	}
	},
	
	getStudentIntakeFailure: function( response, scope){
		var me=scope;
		me.getView().setLoading( false );
	},
    
	buildStudentIntake: function( formData ){
		var me=this; // formData

    	// PERSON RECORD
		var person = formData.data.person;
		var personDemographics = formData.data.personDemographics;
		var personEducationPlan = formData.data.personEducationPlan;
		var personEducationGoal = formData.data.personEducationGoal;
		var personEducationLevels = formData.data.personEducationLevels;
		var personFundingSources = formData.data.personFundingSources;
		var personChallenges = formData.data.personChallenges;
		var personChecklist = formData.data.personChecklist;
		var personEducationGoalId = "";
		
		var studentIntakeEducationPlansForm = Ext.getCmp('StudentIntakeEducationPlans');
		var studentIntakeDemographicsForm = Ext.getCmp('StudentIntakeDemographics');
		var studentIntakeEducationGoalsForm = Ext.getCmp('StudentIntakeEducationGoals');
		
		var educationGoalFormProps;
		var educationGoalsAdditionalFieldsMap;
		var educationLevelFormProps;
		var educationLevelsAdditionalFieldsMap;
		var fundingSourceFormProps;
		var fundingSourcesAdditionalFieldsMap;
		var challengeFormProps;
		var challengesAdditionalFieldsMap;
		var checklistFormProps;
		var defaultLabelWidth;
		// REFERENCE OBJECTS
		var checklist = me.formUtils.alphaSortByField( formData.data.referenceData.checklist, 'name' );
		var challenges = me.formUtils.alphaSortByField( formData.data.referenceData.challenges, 'name' );
		var educationGoals = me.formUtils.alphaSortByField( formData.data.referenceData.educationGoals, 'name' );
		var educationLevels = me.formUtils.alphaSortByField( formData.data.referenceData.educationLevels, 'name' );
		var fundingSources = me.formUtils.alphaSortByField( formData.data.referenceData.fundingSources, 'name' );
		var studentStatuses =  me.formUtils.alphaSortByField( formData.data.referenceData.studentStatuses, 'name' );
		var courseworkHoursStore = me.formUtils.valueSortByField( formData.data.referenceData.courseworkHours, 'name' );
		var registrationLoadsStore =  me.formUtils.valueSortByField(formData.data.referenceData.registrationLoads , 'rangeStart' );
		var militaryAffiliations = me.formUtils.alphaSortByField( formData.data.referenceData.militaryAffiliations, 'name' );
		
		me.challengesStore.loadData( challenges );
		me.completedItemStore.loadData( checklist );
		me.childCareArrangementsStore.loadData( formData.data.referenceData.childCareArrangements );
		me.citizenshipsStore.loadData( formData.data.referenceData.citizenships );
		me.educationGoalsStore.loadData( educationGoals );
		me.educationLevelsStore.loadData( educationLevels );
		me.employmentShiftsStore.loadData( formData.data.referenceData.employmentShifts );
		me.ethnicitiesStore.loadData( formData.data.referenceData.ethnicities );
		me.racesStore.loadData( formData.data.referenceData.races );
		me.fundingSourcesStore.loadData( fundingSources );
		
		me.gendersStore.loadData( formData.data.referenceData.genders );
		me.maritalStatusesStore.loadData( formData.data.referenceData.maritalStatuses );
		me.militaryAffiliationsStore.loadData( militaryAffiliations );
		
		me.statesStore.loadData( formData.data.referenceData.states );
		me.studentStatusesStore.loadData( studentStatuses );
		me.veteranStatusesStore.loadData( formData.data.referenceData.veteranStatuses );
		me.registrationLoadsStore.loadData(registrationLoadsStore);
		me.courseworkHoursStore.loadData(courseworkHoursStore);
		
		// LOAD RECORDS FOR EACH OF THE FORMS
		
		//Trims blank spaces from zip code fields which causes user experience issues
		person.data.zipCode = Ext.String.trim(person.data.zipCode);
		person.data.alternateAddressZipCode = Ext.String.trim(person.data.alternateAddressZipCode);
		
		//handles issue of intake completed date not showing after save event and subsequent client-side model reloads
		var studentIntakeCompleteDate = person.data.studentIntakeCompleteDate;
		var formattedStudentIntakeCompleteDate = person.data.formattedStudentIntakeCompleteDate;
		
		if ( studentIntakeCompleteDate && (!formattedStudentIntakeCompleteDate) ) {
			person.data.formattedStudentIntakeCompleteDate = Ext.util.Format.date(studentIntakeCompleteDate,'m/d/Y');		
		}		
		
		// format the dates
		Ext.getCmp('StudentIntakePersonal').loadRecord( person );
		
		if ( personDemographics != null && personDemographics != undefined ){
			studentIntakeDemographicsForm.loadRecord( personDemographics );
		}

		if ( personEducationPlan != null && personEducationPlan != undefined )
		{
			studentIntakeEducationPlansForm.loadRecord( personEducationPlan );
		}
		
		if ( personEducationGoal != null && personEducationGoal != undefined )
		{
			studentIntakeEducationGoalsForm.loadRecord( personEducationGoal );
			if (personEducationGoal.get('educationGoalId') != null)
			{
				personEducationGoalId = personEducationGoal.get('educationGoalId');
			}			 
		}

		defaultLabelWidth = 150;

		educationGoalsAdditionalFieldsMap = [
		     {
		      parentId: Ssp.util.Constants.EDUCATION_GOAL_BACHELORS_DEGREE_ID, 
			  parentName: "bachelor",
			  name: "description", 
			  label:  me.textStore.getValueByCode('intake.tab5.label.bachelor-major'),
			  fieldType: "mappedtextfield",
			  labelWidth: 200
			 },
		     {
			      parentId: Ssp.util.Constants.EDUCATION_GOAL_MILITARY_ID, 
				  parentName: "military",
				  name: "description", 
				  label: me.textStore.getValueByCode('intake.tab5.label.military-goal'),
				  fieldType: "mappedtextfield",
				  labelWidth: 200
			 },
			 {
		      parentId: Ssp.util.Constants.EDUCATION_GOAL_OTHER_ID, 
			  parentName: "other",
			  name: "description", 
			  label: me.textStore.getValueByCode('intake.tab5.label.other-goal'),
			  fieldType: "mappedtextfield",
			  labelWidth: 200
			 }
		];
		
		educationGoalFormProps = {
				mainComponentType: 'radio',
			    formId: 'StudentIntakeEducationGoals',
                itemsArr: educationGoals,
                selectedItemId: personEducationGoalId,
                idFieldName: 'id',
                selectedItemsArr: [ personEducationGoal.data ],
                selectedIdFieldName: 'educationGoalId',
                additionalFieldsMap: educationGoalsAdditionalFieldsMap,
                radioGroupId: 'StudentIntakeEducationGoalsRadioGroup',
                radioGroupFieldSetId: 'StudentIntakeEducationGoalsFieldSet'};		
		
		me.formUtils.createForm( educationGoalFormProps );	

		educationLevelsAdditionalFieldsMap = [{parentId: Ssp.util.Constants.EDUCATION_LEVEL_NO_DIPLOMA_GED_ID, 
			                                   parentName: "no diploma/no ged", 
			                                   name: "lastYearAttended", 
			                                   label: me.textStore.getValueByCode('intake.tab4.label.last-year-attended'),
			                                   fieldType: "mappedtextfield", 
			                                   labelWidth: defaultLabelWidth,
			                                   enforceMaxLength: true,
			                                   maxLength: 20,
			                                   validationErrorMessage: "This field requires a valid year."},
		                                      {parentId: Ssp.util.Constants.EDUCATION_LEVEL_NO_DIPLOMA_GED_ID, 
			                                   parentName: "no diploma/no ged", 
			                                   name: "highestGradeCompleted", 
			                                   label: me.textStore.getValueByCode('intake.tab4.label.highest-grade'),
			                                   enforceMaxLength: true,
			                                   maxLength: 10,
			                                   fieldType: "mappedtextfield", 
			                                   labelWidth: defaultLabelWidth},
		                                      {parentId: Ssp.util.Constants.EDUCATION_LEVEL_GED_ID, 
			                                   parentName: "ged", 
			                                   name: "graduatedYear", 
			                                   label: me.textStore.getValueByCode('intake.tab4.label.year-of-ged'),
			                                   enforceMaxLength: true,
			                                   maxLength: 20,			                                   
			                                   fieldType: "mappedtextfield",
			                                   labelWidth: defaultLabelWidth,
			                                   validationErrorMessage: "This field requires a valid year."},
		                                      {parentId: Ssp.util.Constants.EDUCATION_LEVEL_HIGH_SCHOOL_GRADUATION_ID, 
			                                   parentName: "high school graduation", 
			                                   name: "graduatedYear", 
			                                   label: me.textStore.getValueByCode('intake.tab4.label.year-graduated'), 
			                                   fieldType: "mappedtextfield",
			                                   labelWidth: defaultLabelWidth,
			                                   enforceMaxLength: true,
			                                   maxLength: 20,			                                   
			                                   validationErrorMessage: "This field requires a valid year."},
		     		                        {parentId: Ssp.util.Constants.EDUCATION_LEVEL_HIGH_SCHOOL_GRADUATION_ID, 
			                                 parentName: "high school graduation", 
			                                 name: "schoolName", 
			                                 label: me.textStore.getValueByCode('intake.tab4.label.highschool-attended'), 
			                                 fieldType: "mappedtextfield",
			                                 maxLength: 255,
			                                 labelWidth: defaultLabelWidth},
		     		                        {parentId: Ssp.util.Constants.EDUCATION_LEVEL_SOME_COLLEGE_CREDITS_ID, 
			                                 parentName: "some college credits", 
			                                 name: "lastYearAttended", 
			                                 label: me.textStore.getValueByCode('intake.tab4.label.last-year-attended'), 
			                                 fieldType: "mappedtextfield",
			                                 enforceMaxLength: true,
			                                 maxLength: 20,			                                 
			                                 labelWidth: defaultLabelWidth,
			                                 validationErrorMessage: "This field requires a valid year."},
		     		                        {parentId: Ssp.util.Constants.EDUCATION_LEVEL_OTHER_ID, 
			                                 parentName: "other", 
			                                 name: "description", 
			                                 label: me.textStore.getValueByCode('intake.tab4.label.explain-credits'), 
			                                 fieldType: "mappedtextarea",
			                                 labelWidth: defaultLabelWidth}];		
		
		educationLevelFormProps = {
				mainComponentType: 'checkbox',
			    formId: 'StudentIntakeEducationLevels', 
                fieldSetTitle: me.textStore.getValueByCode('intake.tab4.label.edu-level'),
                itemsArr: educationLevels, 
                selectedItemsArr: personEducationLevels, 
                idFieldName: 'id', 
                selectedIdFieldName: 'educationLevelId',
                additionalFieldsMap: educationLevelsAdditionalFieldsMap };
		
		me.formUtils.createForm( educationLevelFormProps );
		
		fundingSourcesAdditionalFieldsMap = [{parentId: Ssp.util.Constants.FUNDING_SOURCE_OTHER_ID, 
											  parentName: "other",
											  name: "description", 
											  label: me.textStore.getValueByCode('intake.tab6.label.other-funding'), 
											  fieldType: "mappedtextarea",
											  labelWidth: defaultLabelWidth}];
		
		fundingSourceFormProps = {
				mainComponentType: 'checkbox',
				formId: 'StudentIntakeFunding', 
                fieldSetTitle: me.textStore.getValueByCode('intake.tab6.label.funding-question'),
                itemsArr: fundingSources, 
                selectedItemsArr: personFundingSources, 
                idFieldName: 'id', 
                selectAllButton: true,
                selectedIdFieldName: 'fundingSourceId',
                additionalFieldsMap: fundingSourcesAdditionalFieldsMap };
		
		me.formUtils.createForm( fundingSourceFormProps );	
		
		challengesAdditionalFieldsMap = [{parentId: Ssp.util.Constants.CHALLENGE_OTHER_ID,
			                              parentName: "other",
			                              name: "description", 
			                              label: me.textStore.getValueByCode('intake.tab6.label.other-challenges'), 
			                              fieldType: "mappedtextarea",
			                              labelWidth: defaultLabelWidth}];
		
		challengeFormProps = {
				mainComponentType: 'checkbox',
				formId: 'StudentIntakeChallenges', 
                fieldSetTitle: me.textStore.getValueByCode('intake.tab7.label.challenges-question'),
                itemsArr: challenges, 
                selectedItemsArr: personChallenges, 
                idFieldName: 'id', 
                selectedIdFieldName: 'challengeId',
                additionalFieldsMap: challengesAdditionalFieldsMap };
		
		me.formUtils.createForm( challengeFormProps );
		
		checklistFormProps = {
				mainComponentType: 'checkbox',
				formId: 'StudentIntakeChecklist', 
                fieldSetTitle: me.textStore.getValueByCode('intake.tab8.label.checklist-question'),
                itemsArr: checklist, 
                selectedItemsArr: personChecklist, 
                idFieldName: 'id', 
                selectedIdFieldName: 'completedItemId'};
		
		me.formUtils.createForm( checklistFormProps );
	},
	
	onSaveClick: function( button ) {
		var me=this;
		var formUtils = me.formUtils;
		var personalForm = Ext.getCmp('StudentIntakePersonal').getForm();
		var demographicsForm = Ext.getCmp('StudentIntakeDemographics').getForm();
		var educationPlansForm = Ext.getCmp('StudentIntakeEducationPlans').getForm();
		var educationGoalForm = Ext.getCmp('StudentIntakeEducationGoals').getForm();
		var educationLevelsForm = Ext.getCmp('StudentIntakeEducationLevels').getForm();
		var fundingForm = Ext.getCmp('StudentIntakeFunding').getForm();
		var challengesForm = Ext.getCmp('StudentIntakeChallenges').getForm();
		var checklistForm = Ext.getCmp('StudentIntakeChecklist').getForm();

		var educationGoalId = "";
		var educationGoalDescription = "";
		var educationGoalFormValues = null;
		var educationLevelFormValues = null;
		var fundingFormValues = null;
		var challengesFormValues = null;
		var checklistFormValues = null;

		
		var studentIntakeFormModel = null;
		var personId = "";
		var intakeData = {};
		
		var formsToValidate = [personalForm,
		             demographicsForm,
		             educationPlansForm,
		             educationLevelsForm,
		             educationGoalForm,
		             fundingForm,
		             challengesForm,
		             checklistForm];

		// validate and save the model
		var validateResult = me.formUtils.validateForms( formsToValidate );
		if ( validateResult.valid )
		{
			// update the model with changes from the forms
			personalForm.updateRecord( me.studentIntake.get('person') );
			demographicsForm.updateRecord( me.studentIntake.get('personDemographics') );
			educationPlansForm.updateRecord( me.studentIntake.get('personEducationPlan') );
			educationGoalForm.updateRecord( me.studentIntake.get('personEducationGoal') );
			
			educationGoalId = me.studentIntake.get('personEducationGoal').data.educationGoalId;
			
			// save the full model
			personId = me.studentIntake.get('person').data.id;
			intakeData = {
				person: me.studentIntake.get('person').data,
				personDemographics: me.studentIntake.get('personDemographics').data,
				personEducationGoal: me.studentIntake.get('personEducationGoal').data,
				personEducationPlan: me.studentIntake.get('personEducationPlan').data,
				personEducationLevels: [],
				personFundingSources: [],
				personChallenges: [],
				personChecklist: []
				
			};
						
			// date saved as null is ok if using External Data Sync Routine
			// and the date will not validate because the date field is disabled under
			// this mode. See SSPConfig and studentintake.PersonalViewController for additional detail.
			var origBirthDate = intakeData.person.birthDate;
			if (intakeData.person.birthDate != null)
			{
				intakeData.person.birthDate = me.formUtils.toJSONStringifiableDate(intakeData.person.birthDate);
			}
			
			//save intake completion date so when model strips it below, it can be reloaded
			var completedDateSave = me.studentIntake.get('person').data.studentIntakeCompleteDate;
			
			// cleans properties that will be unable to be saved if not null
			// arrays set to strings should be null rather than string in saved
			// json
			intakeData.person = me.person.setPropsNullForSave( intakeData.person );			
			
			intakeData.personDemographics.personId = personId;
			intakeData.personEducationGoal.personId = personId;
			intakeData.personEducationPlan.personId = personId;

			educationGoalFormValues = educationGoalForm.getValues();
			educationGoalDescription = formUtils.getMappedFieldValueFromFormValuesByIdKey( educationGoalFormValues, educationGoalId );
			intakeData.personEducationGoal.description = educationGoalDescription;
			
			educationLevelFormValues = educationLevelsForm.getValues();
			intakeData.personEducationLevels = formUtils.createTransferObjectsFromSelectedValues('educationLevelId', educationLevelFormValues, personId);	
	
			fundingFormValues = fundingForm.getValues();
			intakeData.personFundingSources = formUtils.createTransferObjectsFromSelectedValues('fundingSourceId', fundingFormValues, personId);	
			
			challengesFormValues = challengesForm.getValues();
			intakeData.personChallenges = formUtils.createTransferObjectsFromSelectedValues('challengeId', challengesFormValues, personId);	
			
			checklistFormValues = checklistForm.getValues();
			intakeData.personChecklist = formUtils.createTransferObjectsFromSelectedValues('completedItemId', checklistFormValues, personId);			


			// display loader
			me.getView().setLoading( true );
			
			me.service.save(me.personLite.get('id'), intakeData, {
				success: function(r, scope) {
					var newSaveFlag = false;
					intakeData.person.birthDate = origBirthDate;
					
					//this handles case for displaying completed date on new save and also for also for restoring it into the model after all saves
					if ( !completedDateSave ) {
						completedDateSave = new Date();						
						newSaveFlag = true;
					} 					
					intakeData.person.studentIntakeCompleteDate = completedDateSave;
					
					me.saveStudentIntakeSuccess(r,scope,newSaveFlag);
				},
				failure: function(r, scope) {
					intakeData.person.birthDate = origBirthDate;
					intakeData.person.studentIntakeCompleteDate = completedDateSave;
					me.saveStudentIntakeFailure(r,scope);
				},
				scope: me
			});

		}else{
			me.formUtils.displayErrors( validateResult.fields );
		}
	},
	
	saveStudentIntakeSuccess: function(r, scope, newSaveFlag) {
		var me=scope;

		me.getView().setLoading( false );
		
		if( r.success ) {
			me.formUtils.displaySaveSuccessMessage( me.getSaveSuccessMessage() );
	        me.appEventsController.getApplication().fireEvent('loadIntake');  
		}								
	},
	
	saveStudentIntakeFailure: function(response, scope) {
		var me=scope;
		me.getView().setLoading( false );							
	},	
	
	onCancelClick: function( button ){
		var me=this;
		me.getView().removeAll();
		me.initStudentIntakeViews();
		me.buildStudentIntake( me.studentIntake );	
	}
});