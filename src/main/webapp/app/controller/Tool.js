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
												
						Ext.getStore('reference.States').loadData( formData.data.referenceData.states );
						Ext.getStore('reference.Challenges').loadData( formData.data.referenceData.challenges );
						//var challengesStore = Ext.getStore('reference.Challenges');
						//challengesStore.load();
						Ext.getStore('reference.ChildCareArrangements').loadData( formData.data.referenceData.childCareArrangements );
						Ext.getStore('reference.Citizenships').loadData( formData.data.referenceData.citizenships );
						Ext.getStore('reference.EducationalGoals').loadData( formData.data.referenceData.educationalGoals );
						Ext.getStore('reference.EducationLevels').loadData( formData.data.referenceData.educationLevels );
						Ext.getStore('reference.EmploymentShifts').loadData( formData.data.referenceData.employmentShifts );
						Ext.getStore('reference.Ethnicities').loadData( formData.data.referenceData.ethnicities );
						//var ethnicitiesStore = Ext.getStore('reference.Ethnicities');
						//ethnicitiesStore.load();
						Ext.getStore('reference.FundingSources').loadData( formData.data.referenceData.fundingSources );
						Ext.getStore('reference.Genders').loadData( formData.data.referenceData.genders );
						Ext.getStore('reference.MaritalStatuses').loadData( formData.data.referenceData.maritalStatuses );
						Ext.getStore('reference.StudentStatuses').loadData( formData.data.referenceData.studentStatuses );
						Ext.getStore('reference.VeteranStatuses').loadData( formData.data.referenceData.veteranStatuses ); 
						Ext.getStore('reference.YesNo').loadData( formData.data.referenceData.yesNo );						
						
						
						// Load records for each of the forms
						Ext.getCmp('StudentIntakePersonal').loadRecord( formData.data.person );
						Ext.getCmp('StudentIntakeDemographics').loadRecord( formData.data.personDemographics );
						Ext.getCmp('StudentIntakeEducationPlans').loadRecord( formData.data.personEducationPlan );
						Ext.getCmp('StudentIntakeEducationGoal').loadRecord( formData.data.personEducationGoal );
						
						formUtils = Ext.create('Ssp.util.FormRendererUtils');
						
						// console.log( ethnicitiesStore );
						// console.log( challengesStore );
						
						// Education Levels
						formUtils.createCheckBoxForm('StudentIntakeEducationLevels', formData.data.referenceData.educationLevels, formData.data.personEducationLevels, 'id');
						
						// Funding Sources
						formUtils.createCheckBoxForm('StudentIntakeFunding', formData.data.referenceData.fundingSources, formData.data.personFundingSources, 'id');						
						
						// Challenges
						//formUtils.createCheckBoxFormFromStore('StudentIntakeChallenges', challengesStore.data.items, formData.data.personChallenges, 'id');
						formUtils.createCheckBoxForm('StudentIntakeChallenges', formData.data.referenceData.challenges, formData.data.personChallenges, 'id'); // formData.data.referenceData.challenges
					}
				});
				break;
		}

		this.formRendererUtils.cleanItems(toolsView);
		toolsView.add( comp );	
	}
    
});