Ext.define('Ssp.controller.tool.studentintake.StudentIntakeToolViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson',
        appEventsController: 'appEventsController',
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
        personLite: 'personLite',
        statesStore: 'statesStore',
        studentStatusesStore: 'studentStatusesStore',
    	veteranStatusesStore: 'veteranStatusesStore'        
    }, 
    config: {
    	personUrl: null,
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
		var studentIntakeUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('studentIntakeTool') );
		
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
		
		// load the person record
		me.apiProperties.makeRequest({
			url: studentIntakeUrl+'/'+me.personLite.get('id'),
			method: 'GET',
			successFunc: me.loadStudentIntakeResult,
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
    
    initStudentIntakeViews: function(){
    	var me=this;
    	var items = [ Ext.createWidget('tabpanel', {
	        width: '100%',
	        height: '100%',
	        activeTab: 0,
			border: 0,
	        items: [ { title: 'Personal'+Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY,
	        		   autoScroll: true,
	        		   items: [{xtype: 'studentintakepersonal'}]
	        		},{
	            		title: 'Demographics',
	            		autoScroll: true,
	            		items: [{xtype: 'studentintakedemographics'}]
	        		},{
	            		title: 'EduPlan',
	            		autoScroll: true,
	            		items: [{xtype: 'studentintakeeducationplans'}]
	        		},{
	            		title: 'EduLevel',
	            		autoScroll: true,
	            		items: [{xtype: 'studentintakeeducationlevels'}]
	        		},{
	            		title: 'EduGoal',
	            		autoScroll: true,
	            		items: [{xtype: 'studentintakeeducationgoals'}]
	        		},{
	            		title: 'Funding',
	            		autoScroll: true,
	            		items: [{xtype: 'studentintakefunding'}]
	        		},{
	            		title: 'Challenges',
	            		autoScroll: true,
	            		hidden: !me.authenticatedPerson.hasAccess('STUDENT_INTAKE_CHALLENGE_TAB'),
	            		items: [{xtype: 'studentintakechallenges'}]
	        		}]
		    })
	    
		];
    	
    	me.getView().add( items );
    },
    
    loadStudentIntakeResult: function( response, view ){
    	var me=this;
    	var r = Ext.decode(response.responseText);
    	var studentIntakeModel;
		
    	// hide the loader
    	me.getView().setLoading( false );
    	
    	if ( r != null )
    	{  		
        	studentIntakeModel = Ext.ModelManager.getModel('Ssp.model.tool.studentintake.StudentIntakeForm');
    		me.studentIntakeForm = studentIntakeModel.getProxy().getReader().read( r ).records[0];		
    		me.buildStudentIntake( me.studentIntakeForm );    		
    	}else{
    		Ext.Msg.alert('Error','There was an error loading the Student Intake form for this student.');
    	}
	},    
    
	buildStudentIntake: function( formData ){
		var me=this;
		
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
		var studentStatuses =  me.formUtils.alphaSortByField( formData.data.referenceData.studentStatuses, 'name' );
		
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
		me.studentStatusesStore.loadData( studentStatuses );
		me.veteranStatusesStore.loadData( formData.data.referenceData.veteranStatuses ); 
		
		// LOAD RECORDS FOR EACH OF THE FORMS
		
		// format the dates
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
	
	onSaveClick: function( button ) {
		var me=this;
		var handleSuccess = me.saveStudentIntakeSuccess;
		var handleError = me.saveErrorHandler;
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
			
			// account for date offset
			intakeData.person.birthDate = me.formUtils.fixDateOffset( intakeData.person.birthDate );

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
			
			// TODO: handle unused fields from the json
			// since these will throw an error in the
			// current API
			delete intakeData.person.studentIntakeCompleteDate;
			delete intakeData.person.currentProgramStatusName;
			
			// Temporarily Disable Educational Goal Fields
			delete intakeData.personEducationGoal.plannedMajor;
			delete intakeData.personEducationGoal.careerDecided;
			delete intakeData.personEducationGoal.plannedOccupation;
			delete intakeData.personEducationGoal.howSureAboutOccupation;
			delete intakeData.personEducationGoal.confidentInAbilities;
			delete intakeData.personEducationGoal.additionalAcademicProgramInformationNeeded;
						
			// Save the intake
			me.apiProperties.makeRequest({
				url: me.apiProperties.createUrl(me.apiProperties.getItemUrl('studentIntakeTool') +"/"+ this.personLite.get('id')),
				method: 'PUT',
				jsonData: intakeData,
				successFunc: handleSuccess,
				failure: handleError,
				scope: me
			});

		}else{
			me.formUtils.displayErrors( validateResult.fields );
		}
		
	},
	
	saveErrorHandler: function(response) {
		var me=this;
		var r = Ext.decode(response.responseText);
		var msg = 'Status Error: ' + response.status + ' - ' + response.statusText;

		// hide loader
		me.getView().setLoading( false );		
		
		if (r.message != null)
		{
			msg = msg + " " + r.message;
		}
		
		// display error message
		Ext.Msg.alert('SSP Error', msg);								
	},
	
	saveStudentIntakeSuccess: function(response) {
		var me=this;
		var r = Ext.decode(response.responseText);
		
		// hide loader
		me.getView().setLoading( false );
		
		if(r.success == true) {
			me.formUtils.displaySaveSuccessMessage( me.getSaveSuccessMessage() );							
		}								
	},
	
	onCancelClick: function( button ){
		var me=this;
		me.getView().removeAll();
		me.initStudentIntakeViews();
		me.buildStudentIntake( me.studentIntakeForm );	
	}
});