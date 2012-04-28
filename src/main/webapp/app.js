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
	'Ssp.model.reference.AbstractReference',
	'Ssp.view.admin.forms.AbstractReferenceAdmin',
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
});

Ext.application({
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
        	'AdminViewController',
        	'MainViewController',
        	'SearchResultsViewController',
        	'tool.StudentIntakeToolViewController',
        	'ToolsViewController'     	
    ],
          		
    launch: function( app ) {

    	//Ext.getStore('Students').load();
    	
		// Load the application shell
        Ext.create('Ext.container.Viewport', {
            layout: 'fit',
            id: 'sspView',
            alias: 'widget.sspview',
            items: []
        });
        
 		// Display the application
        this.getController('MainViewController').displayApplication();
   }
});