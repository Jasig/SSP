Ext.Loader.setConfig({
	enabled: true,
	paths: {
		'Ssp': './app'
	}
});

Ext.require([
	'Ssp.model.Person',
	'Ssp.model.tool.studentintake.StudentIntakeForm',
	'Ssp.model.tool.studentintake.PersonDemographics',
	'Ssp.model.tool.studentintake.PersonEducationGoal',
	'Ssp.model.tool.studentintake.PersonEducationPlan',
	'Ssp.model.tool.actionplan.Task',
	'Ssp.model.reference.AbstractReference',
	'Ssp.mixin.ApiProperties',
	'Ssp.util.FormRendererUtils',
	'Ssp.util.ColumnRendererUtils',
	'Ext.tab.*'
]);

Ext.onReady(function(){
	Deft.Injector.configure({
	    currentPerson: {
	        fn: function(){
	            return new Ssp.model.Person({id:"0"});
	        },
	        singleton: true
	    },
	    apiProperties: {
	        fn: function(){
	            return new Ssp.mixin.ApiProperties({});
	        },
	        singleton: true
	    },
	    formRendererUtils:{
	        fn: function(){
	            return new Ssp.util.FormRendererUtils({});
	    	},
	        singleton: true
	    }
	});
	
	Ext.application({
	    name: 'Ssp',
	    appFolder: 'app',
				
		stores: [ 'Students',
		          'Tasks',
				  'ApplicationForms', 
				  'Tools',
				  'admin.AdminTreeMenus',
				  'reference.AbstractReferences',
				  'reference.Campuses',
				  'reference.Categories',
				  'reference.Challenges',
				  'reference.ChildCareArrangements',
				  'reference.Citizenships',
				  'reference.ConfidentialityLevels',
				  'reference.EducationGoals',
				  'reference.EducationLevels',
				  'reference.EmploymentShifts',
				  'reference.Ethnicities',
				  'reference.FundingSources',
				  'reference.Genders',
				  'reference.MaritalStatuses',
				  'reference.Referrals',
				  'reference.States', 
				  'reference.StudentStatuses',
				  'reference.VeteranStatuses',
				  'reference.YesNo'], 
		
		controllers: [
	        	'AdminViewController',
	        	'MainViewController',
	        	'SearchViewController',
	        	'tool.StudentIntakeToolViewController',
	        	'ToolsViewController'     	
	    ],
	          		
	    launch: function( app ) {
			// Load the application shell
	        Ext.create('Ext.container.Viewport', {
	            layout: 'fit',
	            id: 'sspView',
	            alias: 'widget.sspview',
	            items: [ Ext.create('Ssp.view.Main') ]
	        });
	        
	 		// Display the application
	        this.getController('MainViewController').displayApplication();
	   }
	});	
});