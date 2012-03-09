Ext.define('Ssp.model.tool.studentintake.StudentIntakeForm', {
	extend: 'Ext.data.Model',
	autoLoad: false,
    fields: ['personId',
             'referenceData',
             {name: 'person', 
		      convert: function(value, record) {
		            var person  = new Ssp.model.StudentTO();
		            
		            person.populateFromGenericObject( value );
		
		            return person;
		      	}
             },
             {name: 'personDemographics', 
   		      convert: function(value, record) {
		            var personDemographics  = new Ssp.model.tool.studentintake.StudentDemographics();
		            
		            personDemographics.populateFromGenericObject( value );
		
		            return personDemographics;
		      	}
             },
             {name: 'personEducationGoal', 
   		      convert: function(value, record) {
   		            var personEducationGoal  = new Ssp.model.tool.studentintake.StudentEducationGoal();
   		            
   		            personEducationGoal.populateFromGenericObject( value );
   		
   		            return personEducationGoal;
   		      	}
             },
             {name: 'personEducationPlan', 
      		  convert: function(value, record) {
      		            var personEducationPlan  = new Ssp.model.tool.studentintake.StudentEducationPlan();
      		            
      		            personEducationPlan.populateFromGenericObject( value );
      		
      		            return personEducationPlan;
      		    }
             },
             'personEducationLevels',
             'personFundingSources',
             'personChallenges'],
   
    proxy: {
		type: 'ajax',
		api: {
			read: 'data/tools/StudentIntakeForm.json'
		},
		reader: {
			type: 'json'
		}
	}
    
});