Ext.define('Ssp.model.tool.studentintake.StudentIntakeForm', {
	extend: 'Ext.data.Model',
	autoLoad: false,
    fields: ['studentId',
             'referenceData',
             {name: 'student', 
		      convert: function(value, record) {
		            var student  = new Ssp.model.StudentTO();
		            
		            student.populateFromGenericObject( value );
		
		            return student;
		      	}
             },
             {name: 'studentDemographics', 
   		      convert: function(value, record) {
		            var studentDemographics  = new Ssp.model.tool.studentintake.StudentDemographics();
		            
		            studentDemographics.populateFromGenericObject( value );
		
		            return studentDemographics;
		      	}
             },
             {name: 'studentEducationGoal', 
   		      convert: function(value, record) {
   		            var studentEducationGoal  = new Ssp.model.tool.studentintake.StudentEducationGoal();
   		            
   		            studentEducationGoal.populateFromGenericObject( value );
   		
   		            return studentEducationGoal;
   		      	}
             },
             {name: 'studentEducationPlan', 
      		  convert: function(value, record) {
      		            var studentEducationPlan  = new Ssp.model.tool.studentintake.StudentEducationPlan();
      		            
      		            studentEducationPlan.populateFromGenericObject( value );
      		
      		            return studentEducationPlan;
      		    }
             },
             'studentEducationLevels',
             'studentFundingSources',
             'studentChallenges'],
   
    proxy: {
		type: 'ajax',
		api: {
			read: 'data/tools/StudentIntakeForm.json'
		},
		reader: {
			type: 'json',
			root: 'StudentIntakeForm',
			successProperty: 'success'
		}
	}
    
});