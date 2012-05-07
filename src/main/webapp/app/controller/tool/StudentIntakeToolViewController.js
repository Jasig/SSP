Ext.define('Ssp.controller.tool.StudentIntakeToolViewController', {
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
		
		this.formUtils.createRadioButtonGroup('StudentIntakeEducationGoals','StudentIntakeEducationGoalsRadioGroup', educationGoals, personEducationGoalId, 'id', 'educationGoalId');
		this.formUtils.createCheckBoxForm('StudentIntakeEducationLevels', 'Education Levels: Select all that apply', educationLevels, personEducationLevels, 'id', 'educationLevelId');
		this.formUtils.createCheckBoxForm('StudentIntakeFunding', 'Funding Sources', fundingSources, personFundingSources, 'id', 'fundingSourceId');	
		this.formUtils.createCheckBoxForm('StudentIntakeChallenges', 'Challenges', challenges, personChallenges, 'id', 'challengeId');
	},    
    
	save: function( button ) {
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
		var educationLevelObj = null;
		var otherEducationLevelDescription = "";
		var fundingObj = null;
		var otherFundingDescription = "";
		var challengeObj = null;
		var otherChallengeDescription = "";
		
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

			// set selected education levels
			educationLevelObj = educationLevelsForm.getValues();
			otherEducationLevelDescription = this.formUtils.findPropByName(educationLevelObj, 'otherDescription');
			if (otherEducationLevelDescription.length>0)
			{
				delete educationLevelObj['otherDescription'];
			}
			intakeData.personEducationLevels = this.getSelectedEducationLevels(educationLevelObj, personId, otherEducationLevelDescription);;
			
			// set selected funding
			fundingObj = fundingForm.getValues();
			otherFundingDescription = this.formUtils.findPropByName(fundingObj, 'otherDescription');
			if (otherFundingDescription.length>0)
			{
				delete fundingObj['otherDescription'];
			}
			intakeData.personFundingSources = this.getSelectedFunding(fundingObj, personId, otherFundingDescription);
			
			// set selected challenges
			challengeObj = challengesForm.getValues();
			otherChallengeDescription = this.formUtils.findPropByName(challengeObj, 'otherDescription');
			if (otherChallengeDescription.length>0)
			{
				delete challengeObj['otherDescription'];
			}
			intakeData.personChallenges = this.getSelectedChallenges(challengeObj, personId, otherChallengeDescription);

			Ext.Ajax.request({
				url: this.apiProperties.createUrl('tool/studentIntake/' + this.currentPerson.get('id')),
				method: 'PUT',
				headers: { 'Content-Type': 'application/json' },
				jsonData: intakeData,
				success: function(response) {
					var r = Ext.decode(response.responseText);
					if(r.success == true) {
						console.log('student intake saved successfully');							
					}								
				},
				failure: function(response) {
					var msg = 'Status Error: ' + response.status + ' - ' + response.statusText;
					Ext.Msg.alert('SSP Error', msg);								
				}
			}, this);

		}else{
			Ext.Msg.alert('Invalid Data','Please correct the errors in this Student Intake before saving the record.');
		}
		
	},
	
	getSelectedFunding: function(formValues, personId, otherFieldValue){
		var selectedItems = [];
		Ext.iterate(formValues, function(key, value) {
			var obj;
			if (key.toLowerCase()=='other')
			{
				obj = {fundingSourceId: value, personId: personId, description: otherFieldValue};
			}else{
				obj = {fundingSourceId: value, personId: personId};
			}
			selectedItems.push( obj );
		});
		return selectedItems;
	},

	getSelectedChallenges: function(formValues, personId, otherFieldValue){
		var selectedItems = [];
		Ext.iterate(formValues, function(key, value) {
			var obj;
			if (key.toLowerCase()=='other')
			{
				obj = {challengeId: value, personId: personId, description: otherFieldValue};
			}else{
				obj = {challengeId: value, personId: personId};
			}
			selectedItems.push( obj );
		});
		return selectedItems;
	},
	
	getSelectedEducationLevels: function(formValues, personId, otherFieldValue){
		var selectedItems = [];
		Ext.iterate(formValues, function(key, value) {
			var obj;
			if (key.toLowerCase()=='other')
			{
				obj = {educationLevelId: value, personId: personId, description: otherFieldValue};
			}else{
				obj = {educationLevelId: value, personId: personId};
			}
			selectedItems.push( obj );
		});
		return selectedItems;
	},
	
	viewConfidentialityAgreement: function(button){
		Ext.Msg.alert("Alert", "Don't forget to print the confidentiality agreement");
	}
    
});