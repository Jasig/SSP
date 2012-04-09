Ext.application({
	/*  To-DO determine required objects in the Requires config. These reference items
	 * cause an issue with loading the interface in Firefox and not in Chrome. 
			   'Ssp.view.admin.forms.AbstractReferenceAdmin',
			   'Ssp.view.admin.forms.Challenges',
			   'Ssp.view.admin.forms.Ethnicity',
			   'Ext.data.UuidGenerator',
			   'Ext.container.Viewport',		
	*/
	requires: ['Ssp.model.StudentTO',
			   'Ssp.model.security.UserTO',
			   'Ssp.model.tool.studentintake.StudentIntakeForm',
			   'Ssp.model.tool.studentintake.StudentDemographics',
			   'Ssp.model.tool.studentintake.StudentEducationGoal',
			   'Ssp.model.tool.studentintake.StudentEducationPlan',
			   'Ssp.model.reference.AbstractReferenceTO'],
	
			   
    name: 'Ssp',
    appFolder: 'app',
			
	stores: [ 'Students', 
			  'ApplicationForms', 
			  'Tools',
			  'security.Roles',
			  'admin.AdminMenus',
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
        	'AbstractController',
        	'Admin',
        	'Main',
        	'Search',
        	'StudentIntakeTool',
        	'Tool'     	
    ],
          		
    launch: function( app ) {
   	
		// Define a global student model
		Ext.apply( this, {currentStudent: new Ssp.model.StudentTO({id:"0"}, {}),
						  currentUser: new Ssp.model.security.UserTO({id:"0"})
		});
		
		
		// Load the initial data for the application
		Ext.getStore('Students').load();
		Ext.getStore('security.Roles').load();
		Ext.getStore('admin.AdminMenus').load();

               					 		
   		// Build the UI
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