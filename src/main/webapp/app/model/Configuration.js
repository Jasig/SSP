Ext.define('Ssp.model.Configuration', {
    extend: 'Ext.data.Model',
    fields: [{name: 'syncStudentPersonalDataWithExternalSISData', 
    	      type: 'boolean', 
    	      defaultValue: false
    	     },
             {
    	      name: 'studentIdAlias', 
    	      type: 'string', 
    	      defaultValue: 'Tartan ID'
    	     },
    	     {name: 'studentIdMinValidationLength', 
    	      type: 'number', 
    	      defaultValue: 7
    	     },
    	     {name: 'studentIdMaxValidationLength', 
       	      type: 'number', 
       	      defaultValue: 7
       	     },
    	     {name: 'studentIdAllowableCharacters', 
          	  type: 'string', 
          	  defaultValue: '0-9'
          	 },
    	     {
              name: 'displayStudentIntakeDemographicsEmploymentShift', 
              type: 'boolean', 
              defaultValue: '1'
             },
    	     {
              name: 'educationPlanParentsDegreeLabel', 
              type: 'string', 
              defaultValue: 'Have your parents obtained a college degree?'
             },
    	     {
              name: 'educationPlanSpecialNeedsLabel', 
              type: 'string', 
              defaultValue: 'Special needs or require special accomodation?'
             }]
});