Ext.require('Ext.tab.*');
Ext.define('Ssp.controller.Tool', {
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
		
		this.application.addListener('afterLoadStudent', function(){
			// Load the profile for the selected record.
			Ext.getStore('Tools').loadRawData( this.currentPerson.get('tools') );
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
						console.log( formData );
						var formUtils = Ext.create('Ssp.util.FormRendererUtils');
						var person = formData.data.person;
						var personDemographics = formData.data.personDemographics;
						var personEducationPlan = formData.data.personEducationPlan;
						var personEducationGoal = formData.data.personEducationGoal;
						var personEducationLevels = formData.data.personEducationLevels;
						var personFundingSources = formData.data.personFundingSources;
						var personChallenges = formData.data.personChallenges;
							// [{ id: "07b5c3ac-3bdf-4d12-b65d-94cb55167998" },
							// { id: "eba26bb7-c36b-41d3-857e-00673b231a5d" }]; 
						
						var challenges = formData.data.referenceData.challenges;
						var childCareArrangements = formData.data.referenceData.childCareArrangements;
						var citizenships = formData.data.referenceData.citizenships;
						var educationGoals = formData.data.referenceData.educationGoals;
						var educationLevels = formData.data.referenceData.educationLevels;
						var ethnicities = formData.data.referenceData.ethnicities;
						var fundingSources = formData.data.referenceData.fundingSources;
						var maritalStatuses = formData.data.referenceData.maritalStatuses;
						var studentStatuses = formData.data.referenceData.studentStatuses;
						var veteranStatuses = formData.data.referenceData.veteranStatuses;

						Ext.getStore('reference.Challenges').loadData( challenges );
						Ext.getStore('reference.ChildCareArrangements').load( childCareArrangements );
						Ext.getStore('reference.Citizenships').load( citizenships );
						Ext.getStore('reference.EducationalGoals').load( educationGoals );
						Ext.getStore('reference.EducationLevels').load( educationLevels );
						Ext.getStore('reference.EmploymentShifts').load();
						Ext.getStore('reference.Ethnicities').load( ethnicities );
						Ext.getStore('reference.FundingSources').load( fundingSources );
						Ext.getStore('reference.Genders').load();
						Ext.getStore('reference.MaritalStatuses').load( maritalStatuses );
						Ext.getStore('reference.States').load();
						Ext.getStore('reference.StudentStatuses').load( studentStatuses );
						Ext.getStore('reference.VeteranStatuses').load( veteranStatuses ); 
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