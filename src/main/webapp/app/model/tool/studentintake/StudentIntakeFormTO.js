Ext.define('Ssp.model.tool.studentintake.StudentIntakeFormTO', {
    extend: 'Ext.data.Model',
    fields: ['studentId',
             'referenceData',
             {name: 'student', 
		      convert: function(value, record) {
		            var student  = new Ssp.model.StudentTO();
		            
		            student.populateFromGenericObject( value );
		
		            return student;
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
    autoLoad: true,
   
    proxy: {
		type: 'ajax',
		api: {
			read: 'data/tools/StudentIntakeFormTO.json'
		},
		reader: {
			type: 'json',
			root: 'StudentIntakeForm',
			successProperty: 'success'
		}
	}
    
});