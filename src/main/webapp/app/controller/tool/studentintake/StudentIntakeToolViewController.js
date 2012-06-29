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
		var me=this;	
		
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
		
		// Load the Student Intake
		Form = Ext.ModelManager.getModel('Ssp.model.tool.studentintake.StudentIntakeForm');
		Form.load(me.currentPerson.getId(),{
			success: me.loadStudentIntakeResult,
			scope: me
		});
		
		// display loader
		me.getView().setLoading( true );
		
		return me.callParent(arguments);
    },

    destroy: function() {
    	//this.appEventsController.removeEvent({eventName: 'dynamicCompChange', callBackFunc: this.onDynamicCompChange, scope: this});

        return this.callParent( arguments );
    },     
    
    loadStudentIntakeResult: function( formData ){
    	var me=this;
    	
    	// hide the loader
    	me.getView().setLoading( false );
    	
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
		var challenges = me.formUtils.alphaSortByField( formData.data.referenceData.challenges, 'name' );
		var educationGoals = me.formUtils.alphaSortByField( formData.data.referenceData.educationGoals, 'name' );
		var educationLevels = me.formUtils.alphaSortByField( formData.data.referenceData.educationLevels, 'name' );
		var fundingSources = me.formUtils.alphaSortByField( formData.data.referenceData.fundingSources, 'name' );

		me.challengesStore.loadData( challenges );
		me.childCareArrangementsStore.loadData( formData.data.referenceData.childCareArrangements );
		me.citizenshipsStore.loadData( formData.data.referenceData.citizenships );
		me.educationGoalsStore.loadData( educationGoals );
		me.educationLevelsStore.loadData( educationLevels );
		me.employmentShiftsStore.loadData( formData.data.referenceData.employmentShifts );
		me.ethnicitiesStore.loadData( formData.data.referenceData.ethnicities );
		me.fundingSourcesStore.loadData( fundingSources );
		me.gendersStore.loadData( formData.data.referenceData.genders );
		me.maritalStatusesStore.loadData( formData.data.referenceData.maritalStatuses );
		me.statesStore.loadData( formData.data.referenceData.states );
		me.studentStatusesStore.loadData( formData.data.referenceData.studentStatuses );
		me.veteranStatusesStore.loadData( formData.data.referenceData.veteranStatuses ); 
		
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

		educationGoalsAdditionalFieldsMap = [
		     {
		      parentId: Ssp.util.Constants.EDUCATION_GOAL_BACHELORS_DEGREE_ID, 
			  parentName: "bachelor",
			  name: "description", 
			  label: "Describe bachelor's goal", 
			  fieldType: "mappedtextfield",
			  labelWidth: 200
			 },
		     {
			      parentId: Ssp.util.Constants.EDUCATION_GOAL_MILITARY_ID, 
				  parentName: "military",
				  name: "description", 
				  label: "Describe military goal", 
				  fieldType: "mappedtextfield",
				  labelWidth: 200
			 },
			 {
		      parentId: Ssp.util.Constants.EDUCATION_GOAL_OTHER_ID, 
			  parentName: "other",
			  name: "description", 
			  label: "Decribe your other goal", 
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
			                                   label: "Last Year Attended",
			                                   fieldType: "mappedtextfield", 
			                                   labelWidth: defaultLabelWidth,
			                                   validationExpression: '^(19|20)\\d{2}$',
			                                   validationErrorMessage: "This field requires a valid year."},
		                                      {parentId: Ssp.util.Constants.EDUCATION_LEVEL_NO_DIPLOMA_GED_ID, 
			                                   parentName: "no diploma/no ged", 
			                                   name: "highestGradeCompleted", 
			                                   label: "Highest Grade Completed", 
			                                   fieldType: "mappedtextfield", 
			                                   labelWidth: defaultLabelWidth},
		                                      {parentId: Ssp.util.Constants.EDUCATION_LEVEL_GED_ID, 
			                                   parentName: "ged", 
			                                   name: "graduatedYear", 
			                                   label: "Year of GED", 
			                                   fieldType: "mappedtextfield",
			                                   labelWidth: defaultLabelWidth,
			                                   validationExpression: '^(19|20)\\d{2}$',
			                                   validationErrorMessage: "This field requires a valid year."},
		                                      {parentId: Ssp.util.Constants.EDUCATION_LEVEL_HIGH_SCHOOL_GRADUATION_ID, 
			                                   parentName: "high school graduation", 
			                                   name: "graduatedYear", 
			                                   label: "Year Graduated", 
			                                   fieldType: "mappedtextfield",
			                                   labelWidth: defaultLabelWidth,
			                                   validationExpression: '^(19|20)\\d{2}$',
			                                   validationErrorMessage: "This field requires a valid year."},
		     		                        {parentId: Ssp.util.Constants.EDUCATION_LEVEL_HIGH_SCHOOL_GRADUATION_ID, 
			                                 parentName: "high school graduation", 
			                                 name: "schoolName", 
			                                 label: "High School Attended", 
			                                 fieldType: "mappedtextfield",
			                                 labelWidth: defaultLabelWidth},
		     		                        {parentId: Ssp.util.Constants.EDUCATION_LEVEL_SOME_COLLEGE_CREDITS_ID, 
			                                 parentName: "some college credits", 
			                                 name: "lastYearAttended", 
			                                 label: "Last Year Attended", 
			                                 fieldType: "mappedtextfield",
			                                 labelWidth: defaultLabelWidth,
			                                 validationExpression: '^(19|20)\\d{2}$',
			                                 validationErrorMessage: "This field requires a valid year."},
		     		                        {parentId: Ssp.util.Constants.EDUCATION_LEVEL_OTHER_ID, 
			                                 parentName: "other", 
			                                 name: "description", 
			                                 label: "Please Explain", 
			                                 fieldType: "mappedtextarea",
			                                 labelWidth: defaultLabelWidth}];		
		
		educationLevelFormProps = {
				mainComponentType: 'checkbox',
			    formId: 'StudentIntakeEducationLevels', 
                fieldSetTitle: 'Education level completed: Select all that apply',
                itemsArr: educationLevels, 
                selectedItemsArr: personEducationLevels, 
                idFieldName: 'id', 
                selectedIdFieldName: 'educationLevelId',
                additionalFieldsMap: educationLevelsAdditionalFieldsMap };
		
		me.formUtils.createForm( educationLevelFormProps );

		fundingSourcesAdditionalFieldsMap = [{parentId: Ssp.util.Constants.FUNDING_SOURCE_OTHER_ID, 
											  parentName: "other",
											  name: "description", 
											  label: "Please Explain", 
											  fieldType: "mappedtextarea",
											  labelWidth: defaultLabelWidth}];
		
		fundingSourceFormProps = {
				mainComponentType: 'checkbox',
				formId: 'StudentIntakeFunding', 
                fieldSetTitle: 'How will you pay for college?',
                itemsArr: fundingSources, 
                selectedItemsArr: personFundingSources, 
                idFieldName: 'id', 
                selectedIdFieldName: 'fundingSourceId',
                additionalFieldsMap: fundingSourcesAdditionalFieldsMap };
		
		me.formUtils.createForm( fundingSourceFormProps );	
		
		challengesAdditionalFieldsMap = [{parentId: Ssp.util.Constants.CHALLENGE_OTHER_ID,
			                              parentName: "other",
			                              name: "description", 
			                              label: "Please Explain", 
			                              fieldType: "mappedtextarea",
			                              labelWidth: defaultLabelWidth}];
		
		challengeFormProps = {
				mainComponentType: 'checkbox',
				formId: 'StudentIntakeChallenges', 
                fieldSetTitle: 'Select all challenges that may be barriers to your academic success:',
                itemsArr: challenges, 
                selectedItemsArr: personChallenges, 
                idFieldName: 'id', 
                selectedIdFieldName: 'challengeId',
                additionalFieldsMap: challengesAdditionalFieldsMap };
		
		me.formUtils.createForm( challengeFormProps );
	},    
    
	save: function( button ) {
		var me=this;
		var formUtils = me.formUtils;
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
		var educationGoalDescription = "";
		var educationGoalFormValues = null;
		var educationLevelFormValues = null;
		var fundingFormValues = null;
		var challengesFormValues = null;
		
		var studentIntakeFormModel = null;
		var personId = "";
		var intakeData = {};
		
		var formsToValidate = [personalForm,
		             demographicsForm,
		             educationPlansForm,
		             educationLevelsForm,
		             educationGoalForm,
		             fundingForm,
		             challengesForm];
		
		// validate and save the model
		var validateResult = me.formUtils.validateForms( formsToValidate );
		if ( validateResult.valid )
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

			educationGoalFormValues = educationGoalForm.getValues();
			educationGoalDescription = formUtils.getMappedFieldValueFromFormValuesByIdKey( educationGoalFormValues, educationGoalFormModel.data.educationGoalId );
			intakeData.personEducationGoal.description = educationGoalDescription;
			
			educationLevelFormValues = educationLevelsForm.getValues();
			intakeData.personEducationLevels = formUtils.createTransferObjectsFromSelectedValues('educationLevelId', educationLevelFormValues, personId);	
	
			fundingFormValues = fundingForm.getValues();
			intakeData.personFundingSources = formUtils.createTransferObjectsFromSelectedValues('fundingSourceId', fundingFormValues, personId);	
			
			challengesFormValues = challengesForm.getValues();
			intakeData.personChallenges = formUtils.createTransferObjectsFromSelectedValues('challengeId', challengesFormValues, personId);		

			// ensure array id fields are sent as null rather
			// than an empty string
			
			if (intakeData.person.specialServiceGroups=="")
			{
				intakeData.person.specialServiceGroups=null;
			}

			if (intakeData.person.referralSources=="")
			{
				intakeData.person.referralSources=null;
			}
			
			if (intakeData.person.serviceReasons=="")
			{
				intakeData.person.serviceReasons=null;
			}

			if (intakeData.personEducationPlan.studentStatusId=="")
			{
				intakeData.personEducationPlan.studentStatusId=null;
			}

			if (intakeData.personEducationGoal.educationGoalId=="")
			{
				intakeData.personEducationGoal.educationGoalId=null;
			}			
			
			// display loader
			me.getView().setLoading( true );
			
			// Save the intake
			Ext.Ajax.request({
				url: me.apiProperties.createUrl(me.apiProperties.getItemUrl('studentIntakeTool') + this.currentPerson.get('id')),
				method: 'PUT',
				headers: { 'Content-Type': 'application/json' },
				jsonData: intakeData,
				success: function(response) {
					var r = Ext.decode(response.responseText);
					
					// hide loader
					me.getView().setLoading( false );
					
					if(r.success == true) {
						console.log('student intake saved successfully');							
					}								
				},
				failure: me.apiProperties.handleError
			}, me);

		}else{
			me.formUtils.displayErrors( validateResult.fields );
		}
		
	},
	
	viewConfidentialityAgreement: function(button){
		Ext.Msg.alert("Attention", "Print the confidentiality Agreement after the report is available.");
	}
    
});