Ext.define('Ssp.controller.tool.studentintake.StudentIntakeToolViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties',
        appEventsController: 'appEventsController',
        currentPerson: 'currentPerson',
        challengesStore: 'challengesStore',
    	childCareArrangementsStore: 'childCareArrangementsStore',
    	citizenshipsStore: 'citizenshipsStore',
    	educationGoalsStore: 'educationGoalsStore',
    	educationLevelsStore: 'educationLevelsStore',
    	employmentShiftsStore: 'employmentShiftsStore',
    	ethnicitiesStore: 'ethnicitiesStore',
    	formUtils: 'formRendererUtils',
    	fundingSourcesStore: 'fundingSourcesStore',
    	gendersStore: 'gendersStore',
    	maritalStatusesStore: 'maritalStatusesStore',
        statesStore: 'statesStore',
        studentStatusesStore: 'studentStatusesStore',
    	veteranStatusesStore: 'veteranStatusesStore'        
    }, 

    control: {
		'saveStudentIntakeButton': {
			click: 'save'
		},
		
		'viewConfidentialityAgreementButton': {
			click: 'viewConfidentialityAgreement'
		}	
	},
    
	init: function() {
		// Load the Student Intake
		Form = Ext.ModelManager.getModel('Ssp.model.tool.studentintake.StudentIntakeForm');
		Form.load(this.currentPerson.getId(),{
			success: this.loadStudentIntakeResult,
			scope: this
		});	
				
		return this.callParent(arguments);
    },
    
    loadStudentIntakeResult: function( formData ){
    	// PERSON RECORD
		var person = formData.data.person;
		var personDemographics = formData.data.personDemographics;
		var personEducationPlan = formData.data.personEducationPlan;
		var personEducationGoal = formData.data.personEducationGoal;
		var personEducationLevels = formData.data.personEducationLevels;
		var personFundingSources = formData.data.personFundingSources;
		var personChallenges = formData.data.personChallenges;
		var personEducationGoalId = "";
		
		var educationGoalFormProps;
		var educationGoalsAdditionalFieldsMap;
		var educationLevelFormProps;
		var educationLevelsAdditionalFieldsMap;
		var fundingSourceFormProps;
		var fundingSourcesAdditionalFieldsMap;
		var challengeFormProps;
		var challengesAdditionalFieldsMap;
		var defaultLabelWidth;

		// REFERENCE OBJECTS
		var challenges = this.formUtils.alphaSortByField( formData.data.referenceData.challenges, 'name' );
		var educationGoals = this.formUtils.alphaSortByField( formData.data.referenceData.educationGoals, 'name' );
		var educationLevels = this.formUtils.alphaSortByField( formData.data.referenceData.educationLevels, 'name' );
		var fundingSources = this.formUtils.alphaSortByField( formData.data.referenceData.fundingSources, 'name' );

		this.challengesStore.loadData( challenges );
		this.childCareArrangementsStore.loadData( formData.data.referenceData.childCareArrangements );
		this.citizenshipsStore.loadData( formData.data.referenceData.citizenships );
		this.educationGoalsStore.loadData( educationGoals );
		this.educationLevelsStore.loadData( educationLevels );
		this.employmentShiftsStore.loadData( formData.data.referenceData.employmentShifts );
		this.ethnicitiesStore.loadData( formData.data.referenceData.ethnicities );
		this.fundingSourcesStore.loadData( fundingSources );
		this.gendersStore.loadData( formData.data.referenceData.genders );
		this.maritalStatusesStore.loadData( formData.data.referenceData.maritalStatuses );
		this.statesStore.loadData( formData.data.referenceData.states );
		this.studentStatusesStore.loadData( formData.data.referenceData.studentStatuses );
		this.veteranStatusesStore.loadData( formData.data.referenceData.veteranStatuses ); 
		
		// LOAD RECORDS FOR EACH OF THE FORMS
		Ext.getCmp('StudentIntakePersonal').loadRecord( person );
		
		if ( personDemographics != null && personDemographics != undefined ){
			Ext.getCmp('StudentIntakeDemographics').loadRecord( personDemographics );
		}
		
		if ( personEducationPlan != null && personEducationPlan != undefined )
		{
			Ext.getCmp('StudentIntakeEducationPlans').loadRecord( personEducationPlan );
		}
		
		if ( personEducationGoal != null && personEducationGoal != undefined )
		{
			Ext.getCmp('StudentIntakeEducationGoals').loadRecord( personEducationGoal );
			if (personEducationGoal.get('educationGoalId') != null)
			{
				personEducationGoalId = personEducationGoal.get('educationGoalId')
			}			 
		}

		defaultLabelWidth = 150;

		educationGoalsAdditionalFieldsMap = [{parentId: Ssp.util.Constants.EDUCATIONAL_GOAL_OTHER_ID, 
			  parentName: "other",
			  name: "otherDescription", 
			  label: "Please Explain", 
			  fieldType: "textfield",
			  labelWidth: defaultLabelWidth}];
		
		educationGoalFormProps = {
				mainComponentType: 'radio',
			    formId: 'StudentIntakeEducationGoals',
                itemsArr: educationGoals,
                selectedItemId: personEducationGoalId,
                idFieldName: 'id', 
                selectedIdFieldName: 'educationGoalId',
                additionalFieldsMap: educationGoalsAdditionalFieldsMap,
                radioGroupId: 'StudentIntakeEducationGoalsRadioGroup'};		
		
		this.formUtils.createForm( educationGoalFormProps );	

		educationLevelsAdditionalFieldsMap = [{parentId: Ssp.util.Constants.EDUCATION_LEVEL_NO_DIPLOMA_GED_ID, 
			                                   parentName: "no diploma/no ged", 
			                                   name: "lastYearAttended", 
			                                   label: "Last Year Attended",
			                                   fieldType: "textfield", 
			                                   labelWidth: defaultLabelWidth},
		                                      {parentId: Ssp.util.Constants.EDUCATION_LEVEL_NO_DIPLOMA_GED_ID, 
			                                   parentName: "no diploma/no ged", 
			                                   name: "highestGradeCompleted", 
			                                   label: "Highest Grade Completed", 
			                                   fieldType: "textfield", 
			                                   labelWidth: defaultLabelWidth},
		                                      {parentId: Ssp.util.Constants.EDUCATION_LEVEL_GED_ID, 
			                                   parentName: "ged", 
			                                   name: "graduatedYear", 
			                                   label: "Year of GED", 
			                                   fieldType: "textfield",
			                                   labelWidth: defaultLabelWidth},
		                                      {parentId: Ssp.util.Constants.EDUCATION_LEVEL_HIGH_SCHOOL_GRADUATION_ID, 
			                                   parentName: "high school graduation", 
			                                   name: "graduatedYear", 
			                                   label: "Year Graduated", 
			                                   fieldType: "textfield",
			                                   labelWidth: defaultLabelWidth},
		     		                        {parentId: Ssp.util.Constants.EDUCATION_LEVEL_HIGH_SCHOOL_GRADUATION_ID, 
			                                 parentName: "high school graduation", 
			                                 name: "schoolName", 
			                                 label: "High School Attended", 
			                                 fieldType: "textfield",
			                                 labelWidth: defaultLabelWidth},
		     		                        {parentId: Ssp.util.Constants.EDUCATION_LEVEL_SOME_COLLEGE_CREDITS_ID, 
			                                 parentName: "some college credits", 
			                                 name: "lastYearAttended", 
			                                 label: "Last Year Attended", 
			                                 fieldType: "textfield",
			                                 labelWidth: defaultLabelWidth},
		     		                        {parentId: Ssp.util.EDUCATION_LEVEL_OTHER_ID, 
			                                 parentName: "other", 
			                                 name: "otherDescription", 
			                                 label: "Please Explain", 
			                                 fieldType: "textarea",
			                                 labelWidth: defaultLabelWidth}];		
		
		educationLevelFormProps = {
				mainComponentType: 'checkbox',
			    formId: 'StudentIntakeEducationLevels', 
                fieldSetTitle: 'Education Levels: Select all that apply',
                itemsArr: educationLevels, 
                selectedItemsArr: personEducationLevels, 
                idFieldName: 'id', 
                selectedIdFieldName: 'educationLevelId',
                additionalFieldsMap: educationLevelsAdditionalFieldsMap };
		
		this.formUtils.createForm( educationLevelFormProps );

		fundingSourcesAdditionalFieldsMap = [{parentId: Ssp.util.Constants.FUNDING_SOURCE_OTHER_ID, 
											  parentName: "other",
											  name: "description", 
											  label: "Please Explain", 
											  fieldType: "textarea",
											  labelWidth: defaultLabelWidth}];
		
		fundingSourceFormProps = {
				mainComponentType: 'checkbox',
				formId: 'StudentIntakeFunding', 
                fieldSetTitle: 'Funding Sources',
                itemsArr: fundingSources, 
                selectedItemsArr: personFundingSources, 
                idFieldName: 'id', 
                selectedIdFieldName: 'fundingSourceId',
                additionalFieldsMap: fundingSourcesAdditionalFieldsMap };
		
		this.formUtils.createForm( fundingSourceFormProps );	
		
		challengesAdditionalFieldsMap = [{parentId: Ssp.util.Constants.CHALLENGE_OTHER_ID,
			                              parentName: "other",
			                              name: "description", 
			                              label: "Please Explain", 
			                              fieldType: "textarea",
			                              labelWidth: defaultLabelWidth}];
		
		challengeFormProps = {
				mainComponentType: 'checkbox',
				formId: 'StudentIntakeChallenges', 
                fieldSetTitle: 'Challenges',
                itemsArr: challenges, 
                selectedItemsArr: personChallenges, 
                idFieldName: 'id', 
                selectedIdFieldName: 'challengeId',
                additionalFieldsMap: challengesAdditionalFieldsMap };
		
		this.formUtils.createForm( challengeFormProps );
	},    
    
	save: function( button ) {
		var formUtils = this.formUtils;
		var personalForm = Ext.getCmp('StudentIntakePersonal').getForm();
		var demographicsForm = Ext.getCmp('StudentIntakeDemographics').getForm();
		var educationPlansForm = Ext.getCmp('StudentIntakeEducationPlans').getForm();
		var educationGoalForm = Ext.getCmp('StudentIntakeEducationGoals').getForm();
		var educationLevelsForm = Ext.getCmp('StudentIntakeEducationLevels').getForm();
		var fundingForm = Ext.getCmp('StudentIntakeFunding').getForm();
		var challengesForm = Ext.getCmp('StudentIntakeChallenges').getForm();
		
		var personalFormModel = null;
		var demographicsFormModel = null;
		var educationPlansFormModel = null;
		var educationGoalFormModel = null;
		var educationLevelFormValues = null;
		var fundingFormValues = null;
		var challengesFormValues = null;
		
		var studentIntakeFormModel = null;
		var personId = "";
		var intakeData = {};
		
		// validate and save the model
		if (personalForm.isValid() && demographicsForm.isValid() && educationPlansForm.isValid()  && educationLevelsForm.isValid() && educationGoalForm.isValid() && fundingForm.isValid() && challengesForm.isValid() )
		{
			// retrieve the models
			personalFormModel = personalForm.getRecord();
			demographicsFormModel = demographicsForm.getRecord();
			educationPlansFormModel = educationPlansForm.getRecord();
			educationGoalFormModel = educationGoalForm.getRecord();
			
			// update the model with changes from the forms
			personalForm.updateRecord( personalFormModel );
			demographicsForm.updateRecord( demographicsFormModel );
			educationPlansForm.updateRecord( educationPlansFormModel );
			educationGoalForm.updateRecord( educationGoalFormModel );
			
			// save the full model
			personId = personalFormModel.data.id;
			intakeData = {
				person: personalFormModel.data,
				personDemographics: demographicsFormModel.data,
				personEducationGoal: educationGoalFormModel.data,
				personEducationPlan: educationPlansFormModel.data,
				personEducationLevels: [],
				personFundingSources: [],
				personChallenges: []
			};
			
			intakeData.personDemographics.personId = personId;
			intakeData.personEducationGoal.personId = personId;
			intakeData.personEducationPlan.personId = personId;

			educationLevelFormValues = educationLevelsForm.getValues();
			intakeData.personEducationLevels = formUtils.createTransferObjectFromSelectedValues('educationLevelId', educationLevelFormValues, personId);	
	
			fundingFormValues = fundingForm.getValues();
			intakeData.personFundingSources = formUtils.createTransferObjectFromSelectedValues('fundingSourceId', fundingFormValues, personId);	
			
			challengesFormValues = challengesForm.getValues();
			intakeData.personChallenges = formUtils.createTransferObjectFromSelectedValues('challengeId', challengesFormValues, personId);

			console.log( intakeData );
			
			Ext.Ajax.request({
				url: this.apiProperties.createUrl(this.apiProperties.getItemUrl('studentIntakeTool') + this.currentPerson.get('id')),
				method: 'PUT',
				headers: { 'Content-Type': 'application/json' },
				jsonData: intakeData,
				success: function(response) {
					var r = Ext.decode(response.responseText);
					if(r.success == true) {
						console.log('student intake saved successfully');							
					}								
				},
				failure: this.apiProperties.handleError
			}, this);
		}else{
			Ext.Msg.alert('Invalid Data','Please correct the errors in this Student Intake before saving the record.');
		}
		
	},
	
	viewConfidentialityAgreement: function(button){
		Ext.Msg.alert("Alert", "Don't forget to print the confidentiality agreement");
	}
    
});