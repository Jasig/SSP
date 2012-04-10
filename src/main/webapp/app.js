Deft.Injector.configure({
    currentPerson: {
    	fn: function(){
    		return new Ssp.model.StudentTO({id:"0"}, {})
    	},
    	singleton: true
    }
});

Ext.application({
	/*  To-DO determine required objects in the Requires config. These reference items
	 * cause an issue with loading the interface in Firefox and not in Chrome. 
			   ,
			   'Ssp.view.admin.forms.Challenges',
			   'Ssp.view.admin.forms.Ethnicity',
			   'Ext.data.UuidGenerator',
			   'Ext.container.Viewport',
			   ,
               'Ssp.util.FormRendererUtils'
			   ,
	*/		
	
	requires: ['Ssp.model.StudentTO',
			   'Ssp.model.tool.studentintake.StudentIntakeForm',
			   'Ssp.model.tool.studentintake.StudentDemographics',
			   'Ssp.model.tool.studentintake.StudentEducationGoal',
			   'Ssp.model.tool.studentintake.StudentEducationPlan',
			   'Ssp.model.reference.AbstractReferenceTO',
			   'Ssp.view.admin.forms.AbstractReferenceAdmin'],
	
			   
    name: 'Ssp',
    appFolder: 'app',
			
	stores: [ 'Students', 
			  'ApplicationForms', 
			  'Tools',
			  'admin.AdminTreeMenus',
			  'reference.AbstractReferences',
			  'reference.Challenges',
			  'reference.ChildCareArrangements',
			  'reference.Citizenships',
			  'reference.EducationalGoals',
			  'reference.EducationLevels',
			  'reference.EmploymentShifts',
			  'reference.Ethnicities',
			  'reference.FundingSources',
			  'reference.Genders',
			  'reference.MaritalStatuses',
			  'reference.States', 
			  'reference.StudentStatuses',
			  'reference.VeteranStatuses',
			  'reference.YesNo'], 
	
	controllers: [
        	'AbstractViewController',
        	'Admin',
        	'Main',
        	'Search',
        	'StudentIntakeTool',
        	'Tool'     	
    ],
          		
    launch: function( app ) {
    	
		// Load the application shell
        Ext.create('Ext.container.Viewport', {
            layout: 'fit',
            id: 'sspView',
            alias: 'widget.sspview',
            items: []
        });   
    	
 		// Display the application
        this.getController('Main').displayApplication();
   }
});