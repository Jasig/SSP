Ext.define('Ssp.controller.tool.StudentIntakeToolViewController', {
    extend: 'Ext.app.Controller',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties',
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
	views: [
        'tools.StudentIntake'
    ],

	init: function() {
        
		this.control({
			'#SaveStudentIntakeButton': {
				click: this.save,
				scope: this
			}
			
		}); 

		this.application.addListener('loadStudentIntake', function(){
			Form = Ext.ModelManager.getModel('Ssp.model.tool.studentintake.StudentIntakeForm');
			Form.load(this.currentPerson.getId(),{
				success: this.loadStudentIntakeResult,
				scope: this
			});
		},this);		
		
		this.callParent(arguments);
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

		// REFERENCE OBJECTS
		var challenges = formData.data.referenceData.challenges;
		var educationGoals = formData.data.referenceData.educationGoals;
		var educationLevels = formData.data.referenceData.educationLevels;
		var fundingSources = formData.data.referenceData.fundingSources;

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
		}

		this.formUtils.createCheckBoxForm('StudentIntakeEducationLevels', educationLevels, personEducationLevels, 'id', 'educationLevelId');
		this.formUtils.createCheckBoxForm('StudentIntakeFunding', fundingSources, personFundingSources, 'id', 'fundingSourceId');	
		this.formUtils.createCheckBoxForm('StudentIntakeChallenges', challenges, personChallenges, 'id', 'challengeId');
	},    
    
	save: function() {
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
		var selectedEducationLevels = null;
		var selectedFunding = null;
		var selectedChallenges = null;
		
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
				personEducationLevels: selectedEducationLevels,
				personFundingSources: selectedFunding,
				personChallenges: selectedChallenges
			};
			
			intakeData.personDemographics.personId = personId;
			intakeData.personEducationGoal.personId = personId;
			intakeData.personEducationPlan.personId = personId;

			var educationLevelObj = educationLevelsForm.getValues();
			var selectedEducationLevels = [];
			for ( prop in educationLevelObj )
			{
				var obj = {educationLevelId: educationLevelObj[prop], personId: personId};
				selectedEducationLevels.push( obj );
			}
			intakeData.personEducationLevels = selectedEducationLevels;
			
			var fundingObj = fundingForm.getValues();
			var selectedFunding = [];
			for ( prop in fundingObj )
			{
				var obj = {fundingSourceId: fundingObj[prop], personId: personId};
				selectedFunding.push( obj );
			}
			intakeData.personFundingSources = selectedFunding;
			
			var challengeObj = challengesForm.getValues();
			var selectedChallenges = [];
			for ( prop in challengeObj )
			{
				var obj = {challengeId: challengeObj[prop], personId: personId};
				selectedChallenges.push( obj );
			}
			intakeData.personChallenges = selectedChallenges;
			
			// console.log(intakeData);
			
			Ext.Ajax.request({
				url: this.apiProperties.createUrl('tool/studentIntake/' + this.currentPerson.get('id')),
				method: 'PUT',
				headers: { 'Content-Type': 'application/json' },
				jsonData: intakeData,
				success: function(response) {
					var r = Ext.decode(response.responseText);
					if(r.success == true) {
						console.log('student intake saved successfully')							
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
		
	}
    
});