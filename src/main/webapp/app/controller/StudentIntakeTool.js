Ext.define('Ssp.controller.StudentIntakeTool', {
    extend: 'Ssp.controller.AbstractController',
    
	views: [
        'tools.StudentIntake'
    ],

	init: function() {
        console.log('Initialized Student Intake Tool Controller!');
        
		this.control({
			'#SaveStudentIntakeButton': {
				click: this.saveStudentIntakeButtonClick,
				scope: this
			}
			
		}); 
				
		this.callParent(arguments);
    },
 
 	/*
 	 * Handle click for save student intake button.
	 */    
	saveStudentIntakeButtonClick: function(){ 
		this.save();		
	},
	
	save: function() {
		
		var formUtils = Ext.create('Ssp.util.FormRendererUtils');
		
		var personalForm = Ext.getCmp('StudentIntakePersonal').getForm();
		var demographicsForm = Ext.getCmp('StudentIntakeDemographics').getForm();
		var educationPlansForm = Ext.getCmp('StudentIntakeEducationPlans').getForm();
		var educationGoalForm = Ext.getCmp('StudentIntakeEducationGoal').getForm();
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
			studentIntakeFormModel = new Ssp.model.tool.studentintake.StudentIntakeForm();
			studentIntakeFormModel.set('person', personalFormModel.data );
			studentIntakeFormModel.set('personDemographics', demographicsFormModel.data);
			studentIntakeFormModel.set('personEducationGoal', educationGoalFormModel.data);
			studentIntakeFormModel.set('personEducationPlan', educationPlansFormModel.data);

			selectedEducationLevels = formUtils.getSelectedValuesAsTransferObject( educationLevelsForm.getValues(), 'Ssp.model.reference.EducationLevelTO' );
			studentIntakeFormModel.set('personEducationLevels', selectedEducationLevels );			
			
			selectedFunding = formUtils.getSelectedValuesAsTransferObject( fundingForm.getValues(), 'Ssp.model.reference.FundingSourceTO' );
			studentIntakeFormModel.set('personFundingSources', selectedFunding );

			selectedChallenges = formUtils.getSelectedValuesAsTransferObject( challengesForm.getValues(), 'Ssp.model.reference.ChallengeTO' );
			studentIntakeFormModel.set('personChallenges', selectedChallenges );

			// Save the Student Intake Model
			console.log( studentIntakeFormModel );
			
		}else{
			Ext.Msg.alert('Invalid Data','Please correct the errors in this Student Intake before saving the record.');
		}
		
	}
    
});