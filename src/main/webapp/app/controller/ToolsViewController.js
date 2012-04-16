Ext.define('Ssp.controller.ToolsViewController', {
	extend: 'Ssp.controller.AbstractViewController',

    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        currentPerson: 'currentPerson'
    },
	
	views: [
        'ToolsMenu','Tools'
    ],
    
    stores: ['reference.FundingSources','reference.Challenges'],

	init: function() {
        
		this.control({
			'toolsmenu': {
				itemclick: this.itemClick,
				scope: this
			}
			
		});
		
		this.application.addListener('afterLoadPerson', function(){
			// Load the profile for the selected record.
			Ext.getStore('Tools').loadRawData(
				[{
			        name: "Profile",
			        toolType: "Profile"
			    },{
			        name: "Student Intake",
			        toolType: "StudentIntake"
			    }]		
			);// this.currentPerson.get('tools') );
			Ext.ComponentQuery.query('toolsmenu')[0].getSelectionModel().select(0);
			this.loadTool('Profile');
		},this);
		
		this.callParent(arguments);
    },
     
    /*
     * Handle Tool Menu Item Click.
     */
	itemClick: function(grid,record,item,index){ 
		this.loadTool( record.get('toolType') );		
	},
	
 	/*
	 * Loads a tool.
	 */	
	loadTool: function( toolType ) {
		var toolsView = Ext.ComponentQuery.query('tools')[0];
		var comp = null;
		var tabs;
		var form = "";
		var person = this.currentPerson;
		var personId = person.get('id');
		
		// Kill existing tools, so no dupe ids are registered
		if (toolsView != null)
		{
			Ssp.util.FormRendererUtils.cleanAll(toolsView);
		}
		
		// create the tool by type
		comp =  Ext.create('Ssp.view.tools.'+toolType);
		
		switch(toolType)
		{
			case 'Profile':
				comp.loadRecord( person );
				break;
			
			case 'StudentIntake':
				Form = Ext.ModelManager.getModel('Ssp.model.tool.studentintake.StudentIntakeForm');
				Form.load(personId,{
					success: function( formData ) {
						var formUtils = Ext.create('Ssp.util.FormRendererUtils');
						
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
						var childCareArrangements = formData.data.referenceData.childCareArrangements;
						var citizenships = formData.data.referenceData.citizenships;
						var educationGoals = formData.data.referenceData.educationGoals;
						var educationLevels = formData.data.referenceData.educationLevels;
						var employmentShifts = formData.data.referenceData.employmentShifts;
						var ethnicities = formData.data.referenceData.ethnicitys;
						var fundingSources = formData.data.referenceData.fundingSources;
						var genders = formData.data.referenceData.genders;
						var maritalStatuses = formData.data.referenceData.maritalStatuss;
						var states = formData.data.referenceData.states;
						var studentStatuses = formData.data.referenceData.studentStatuss;
						var veteranStatuses = formData.data.referenceData.veteranStatuss;

						Ext.getStore('reference.Challenges').loadData( challenges );
						Ext.getStore('reference.ChildCareArrangements').loadData( childCareArrangements );
						Ext.getStore('reference.Citizenships').loadData( citizenships );
						Ext.getStore('reference.EducationalGoals').loadData( educationGoals );
						Ext.getStore('reference.EducationLevels').loadData( educationLevels );
						Ext.getStore('reference.EmploymentShifts').loadData( employmentShifts );
						Ext.getStore('reference.Ethnicities').loadData( ethnicities );
						Ext.getStore('reference.FundingSources').loadData( fundingSources );
						Ext.getStore('reference.Genders').loadData( genders );
						Ext.getStore('reference.MaritalStatuses').loadData( maritalStatuses );
						Ext.getStore('reference.States').loadData( states );
						Ext.getStore('reference.StudentStatuses').loadData( studentStatuses );
						Ext.getStore('reference.VeteranStatuses').loadData( veteranStatuses ); 
						Ext.getStore('reference.YesNo').load();						
						
						// Load records for each of the forms
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

						formUtils.createCheckBoxForm('StudentIntakeEducationLevels', educationLevels, personEducationLevels, 'id');
						formUtils.createCheckBoxForm('StudentIntakeFunding', fundingSources, personFundingSources, 'id');	
						formUtils.createCheckBoxForm('StudentIntakeChallenges', challenges, personChallenges, 'id');
					}
				});
				break;
		}
		
		toolsView.add( comp );
	}
    
});