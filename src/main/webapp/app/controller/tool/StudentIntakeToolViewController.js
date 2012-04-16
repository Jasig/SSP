Ext.define('Ssp.controller.tool.StudentIntakeToolViewController', {
    extend: 'Ssp.controller.AbstractViewController',
    
	views: [
        'tools.StudentIntake'
    ],

	init: function() {
        
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
			selectedEducationLevels = formUtils.getSelectedValuesAsTransferObject( educationLevelsForm.getValues(), 'Ssp.model.reference.EducationLevel' );
			selectedFunding = formUtils.getSelectedValuesAsTransferObject( fundingForm.getValues(), 'Ssp.model.reference.FundingSource' );
			selectedChallenges = formUtils.getSelectedValuesAsTransferObject( challengesForm.getValues(), 'Ssp.model.reference.Challenge' );

			/*
			studentIntakeFormModel = Ext.create('Ssp.model.tool.studentintake.StudentIntakeForm',{});
			studentIntakeFormModel.set('person', personalFormModel.data );
			studentIntakeFormModel.set('personDemographics', demographicsFormModel.data);
			studentIntakeFormModel.set('personEducationGoal', educationGoalFormModel.data);
			studentIntakeFormModel.set('personEducationPlan', educationPlansFormModel.data);
			studentIntakeFormModel.set('personEducationLevels', selectedEducationLevels );			
			studentIntakeFormModel.set('personFundingSources', selectedFunding );
			studentIntakeFormModel.set('personChallenges', selectedChallenges );
			*/
			// console.log( studentIntakeFormModel );
			// studentIntakeFormModel.save();

			Ext.Ajax.request({
				url: '/ssp/api/tool/studentIntake/' + personalFormModel.data.id,
				method: 'PUT',
				headers: { 'Content-Type': 'application/json' },
				jsonData: {
					person: personalFormModel.data,
					personDemographics: demographicsFormModel.data,
					personEducationGoal: educationGoalFormModel.data,
					personEducationPlan: educationPlansFormModel.data,
					personEducationLevels: selectedEducationLevels,
					personFundingSources: selectedFunding,
					personChallenges: selectedChallenges
				},
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
			});
			
		}else{
			Ext.Msg.alert('Invalid Data','Please correct the errors in this Student Intake before saving the record.');
		}
		
	} 
    
});