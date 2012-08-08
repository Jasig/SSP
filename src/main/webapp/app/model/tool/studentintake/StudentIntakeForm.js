Ext.define('Ssp.model.tool.studentintake.StudentIntakeForm', {
	extend: 'Ext.data.Model',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties'
    }, 

	fields: [{name: 'person', 
		      convert: function(value, record) {
		            var person  = Ext.create('Ssp.model.Person',{});
		            person.populateFromGenericObject( value );		
		            return person;
		      	}
             },
              {name: 'personDemographics', 
   		      convert: function(value, record) {
		            var personDemographics = Ext.create('Ssp.model.tool.studentintake.PersonDemographics',{});
		            personDemographics.populateFromGenericObject( value );
		            return personDemographics;
		      	}
             },
             {name: 'personEducationGoal', 
   		      convert: function(value, record) {
   		            var personEducationGoal = Ext.create('Ssp.model.tool.studentintake.PersonEducationGoal',{});
   		            personEducationGoal.populateFromGenericObject( value );  		
   		            return personEducationGoal;
   		      	}
             },
             {name: 'personEducationPlan', 
      		  convert: function(value, record) {
      		            var personEducationPlan = Ext.create('Ssp.model.tool.studentintake.PersonEducationPlan',{});
      		            personEducationPlan.populateFromGenericObject( value );
      		            return personEducationPlan;
      		    }
             },
             'personEducationLevels',
             'personFundingSources',
             'personChallenges',
             'referenceData'],

	autoLoad: false,
 	proxy: {
		type: 'rest',
		url: '/ssp/api/1/tool/studentIntake/',
		actionMethods: {
			create: "POST", 
			read: "GET", 
			update: "PUT",
			destroy: "DELETE"
		},
		reader: {
			type: 'json'
		},
	    writer: {
	        type: 'json',
	        successProperty: 'success'
	    }
	}
});