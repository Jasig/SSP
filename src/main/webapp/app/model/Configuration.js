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
       	     {name: 'studentIdMinValidationErrorText', 
       	      type: 'string', 
       	      defaultValue: 'The entered value is not long enough.'
       	     },
    	     {name: 'studentIdMaxValidationLength', 
       	      type: 'number', 
       	      defaultValue: 7
       	     },
       	     {name: 'studentIdMaxValidationErrorText', 
      	      type: 'string', 
      	      defaultValue: 'The entered value is too long.'
      	     },
    	     {name: 'studentIdAllowableCharacters', 
          	  type: 'string', 
          	  defaultValue: '0-9'
          	 },
    	     {name: 'studentIdValidationErrorText', 
             	  type: 'string', 
             	  defaultValue: 'Not a valid Student Id'
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
             },
    	     {
              name: 'coachFieldLabel', 
              type: 'string', 
              defaultValue: 'Coach/Advisor'
            }],
             
     	constructor: function(){
     		var me=this;
     		me.callParent(arguments);
     		// apply student id validator for use in 
     		// form fields throughout the application
     		var minStudentIdLen = me.get('studentIdMinValidationLength');
    		var maxStudentIdLen = me.get('studentIdMaxValidationLength');
    		var allowableCharacters = me.get('studentIdAllowableCharacters');
    		// Example RegEx - /(^[1-9]{7,9})/
    		var regExString = '^([' + allowableCharacters + ']';
    		regExString = regExString + '{' + minStudentIdLen + ',';
    		regExString = regExString + maxStudentIdLen + '})';
    		var validStudentId = new RegExp( regExString );
            var studentIdValErrorText = 'You should only use the following character list for input: ' + allowableCharacters;
    		me.set('studentIdValidationErrorText',studentIdValErrorText);
    		me.set('studentIdMinValidationErrorText', 'Value should be at least ' + minStudentIdLen + ' characters & no more than ' + maxStudentIdLen + ' characters'); 	
            me.set('studentIdMaxValidationErrorText', 'Value should be at least ' + minStudentIdLen + ' characters & no more than ' + maxStudentIdLen + ' characters'); 

    		Ext.apply(Ext.form.field.VTypes, {
                //  vtype validation function
                studentIdValidator: function(val, field) {
                    return validStudentId.test(val);
                }
            });
            
    		return me;
    	}
});