Ext.require('Ext.tab.*');
Ext.define('Ssp.controller.Tool', {
    extend: 'Ssp.controller.AbstractController',
    
	views: [
        'ToolsMenu'
    ],
    
    stores: ['reference.FundingSources','reference.Challenges'],

	init: function() {
        console.log('Initialized Tools Controller!');
        
		this.control({
			'ToolsMenu': {
				itemclick: this.itemClick,
				scope: this
			}
			
		}); 
		
		this.superclass.init.call(this, arguments);
    },
 
 	/*
	 * Load the selected tool.
	 * var studentRecord = Ext.getCmp('SearchResults');
		var columnRendererUtils = Ext.create('Ssp.util.FormRendererUtils');
		var profileForm = columnRendererUtils.getProfileFormItems();
		var studentRecordView = Ext.getCmp('StudentRecord');

	 */    
	itemClick: function(grid,record,item,index){ 
		this.loadTool( record.get('toolType') );		
	},
	
	loadTool: function( toolType ) {
		var toolsView = Ext.getCmp('Tools');
		var comp = Ext.create('Ssp.view.tools.'+toolType);
		var tabs;
		var Form = "";
		var currentStudent = this.application.currentStudent;
		var currentStudentId = currentStudent.get('id');
		
		switch(toolType)
		{
			case 'Profile':
				comp.loadRecord( currentStudent );
				break;
			
			case 'StudentIntake':
				Form = Ext.ModelManager.getModel('Ssp.model.tool.studentintake.StudentIntakeForm');
				Form.load(currentStudentId,{
					success: function( formData ) {
						// console.log( formData.data.student );
						var formUtils = Ext.create('Ssp.util.FormRendererUtils');
						var person = formData.data.person;
						//var personDemographics = formData.data.personDemographics;
						//var personEducationPlan = formData.data.personEducationPlan;
						//var personEducationGoal = formData.data.personEducationGoal;
						//var personEducationLevels = formData.data.personEducationLevels;
						//var personFundingSources = formData.data.personFundingSources;
						// formData.data.personChallenges
						var personChallenges = [
						               			{ id: "f53920f4-59f6-454f-a3db-d57b721fed5c" },
						               			{ id: "eba26bb7-c36b-41d3-857e-00673b231a5d" }
						               		]; 
						
						var challenges = formData.data.referenceData.challenges;
						Ext.getStore('reference.Challenges').loadData( challenges );
						//var childCareArrangements = formData.data.referenceData.childCareArrangements;
						//var citizenships = formData.data.referenceData.citizenships;
						//var educationGoals = formData.data.referenceData.educationGoals;
						//var educationLevels = formData.data.referenceData.educationLevels;
						//var ethnicities = formData.data.referenceData.ethnicities;
						//var fundingSources = formData.data.referenceData.fundingSources;
						//var maritalStatuses = formData.data.referenceData.maritalStatuses;
						//var studentStatuses = formData.data.referenceData.studentStatuses;
						//var veteranStatuses = formData.data.referenceData.veteranStatuses;

						Ext.getStore('reference.States').load();
						Ext.getStore('reference.ChildCareArrangements').load();
						Ext.getStore('reference.Citizenships').load();
						Ext.getStore('reference.EducationalGoals').load();
						Ext.getStore('reference.EducationLevels').load();
						Ext.getStore('reference.EmploymentShifts').load();
						Ext.getStore('reference.Ethnicities').load();
						Ext.getStore('reference.FundingSources').load();
						Ext.getStore('reference.Genders').load();
						Ext.getStore('reference.MaritalStatuses').load();
						Ext.getStore('reference.StudentStatuses').load();
						Ext.getStore('reference.VeteranStatuses').load(); 
						Ext.getStore('reference.YesNo').load();						
						
						// Load records for each of the forms
						Ext.getCmp('StudentIntakePersonal').loadRecord( person );
						//Ext.getCmp('StudentIntakeDemographics').loadRecord( personDemographics );
						//Ext.getCmp('StudentIntakeEducationPlans').loadRecord( personEducationPlan );
						//Ext.getCmp('StudentIntakeEducationGoal').loadRecord( personEducationGoal );
						//formUtils.createCheckBoxForm('StudentIntakeEducationLevels', educationLevels, personEducationLevels, 'id');
						//formUtils.createCheckBoxForm('StudentIntakeFunding', fundingSources, personFundingSources, 'id');	
						formUtils.createCheckBoxForm('StudentIntakeChallenges', challenges, personChallenges, 'id');
					}
				});
				break;
		}

		this.formRendererUtils.cleanItems(toolsView);
		toolsView.add( comp );	
	}
    
});