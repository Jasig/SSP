/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
Ext.define('Ssp.model.Configuration', {
    extend: 'Ext.data.Model',
	fields: [
	         /*
		      * Set this option to true to lock editing on fields that relate to the external_data
		      * source. This will restrict you from editing any data that relates to external after
		      * you have first added a record to the system. All editing will be disabled for personal
		      * data, including changing a studentId/schoolId after the record has been added to the
		      * system.  
		      */
             {name: 'syncStudentPersonalDataWithExternalData', 
    	      type: 'boolean', 
    	      defaultValue: false
    	     },
    	     /*
    	      * Set this option to true to display the retrieveFromExternalDataButton on the Caseload
    	      * Assignment Screen when adding a new record. This will allow you to populate a student's record
    	      * from the external_data source while adding a new record to the system.
    	      */
    	     {
    	    	name: 'allowExternalRetrievalOfStudentDataFromCaseloadAssignment',
    	    	type: 'boolean',
    	    	defaultValue: true
    	     },
    	     /*
    	      * Set this option to true to lock editing of Coach Assignments for records in the system.
    	      * When this option is set to true, all Coach Assignments will be populated from an external system
    	      * through the external_data tables/views of this application.
    	      */
    	     {name: 'coachSetFromExternalData', 
    	      type: 'boolean', 
    	      defaultValue: false
    	     },
    	     /*
    	      * Set this option to true to lock editing of Student Type Assignments for records in the system.
    	      * When this option is set to true, all Student Type Assignments will be populated from an external system
    	      * through the external_data tables/views of this application.
    	      */
    	     {name: 'studentTypeSetFromExternalData', 
       	      type: 'boolean', 
       	      defaultValue: false
       	     },
    	     /*
    	      * Set this option to the label you would like to see for the studentId values in the system.
    	      * For instance: Use this to label your studentId for your institution's naming convention.
    	      */
             {
    	      name: 'studentIdAlias', 
    	      type: 'string', 
    	      defaultValue: 'Tartan ID'
    	     },
    	     /*
    	      * Minimum data length for a studentId/schoolId in the application.
    	      */
    	     {name: 'studentIdMinValidationLength', 
    	      type: 'number', 
    	      defaultValue: 3
    	     },
    	     /*
    	      * Error message for a studentId/schoolId that exceeds the specified minimum validation length.
    	      */
       	     {name: 'studentIdMinValidationErrorText', 
       	      type: 'string', 
       	      defaultValue: 'The entered value is not long enough.'
       	     },
    	     /*
    	      * Maximum data length for a studentId/schoolId in the application.
    	      */
    	     {name: 'studentIdMaxValidationLength', 
       	      type: 'number', 
       	      defaultValue: 8
       	     },
    	     /*
    	      * Error message for a studentId/schoolId that exceeds the specified maximum validation length.
    	      */
       	     {name: 'studentIdMaxValidationErrorText', 
      	      type: 'string', 
      	      defaultValue: 'The entered value is too long.'
      	     },
    	     /*
    	      * Values to validate for the allowable characters in a studentId/schoolId. This value will
    	      * be converted and applied as a regular expression, so all regular expressions values should be
    	      * available to this value.
    	      * 
    	      * For example: 
    	      * 
    	      * Only digits use "0-9".  
    	      * Only alphabetical characters use: "a-zA-Z".
    	      * Alphabetical characters and digits use: "a-zA-Z0-9"
    	      */
    	     {name: 'studentIdAllowableCharacters', 
          	  type: 'string', 
          	  defaultValue: 'a-zA-Z0-9'
          	 },
    	     /*
    	      * Error message for a studentId/schoolId validation error.
    	      */
    	     {name: 'studentIdValidationErrorText', 
             	  type: 'string', 
             	  defaultValue: 'Not a valid Student Id'
             },
    	     /*
    	      * Set to 1 to display the Employment Shift option on the Student Intake Tool.
    	      */
    	     {
              name: 'displayStudentIntakeDemographicsEmploymentShift', 
              type: 'boolean', 
              defaultValue: true
             },
    	     /*
    	      * Label to use for the Parent's Degree question on the Education Plan tab of the Student Intake Tool.
    	      */
    	     {
              name: 'educationPlanParentsDegreeLabel', 
              type: 'string', 
              defaultValue: 'Have your parents obtained a college degree?'
             },
    	     /*
    	      * Label to use for the Special Needs question on the Education Plan tab of the Student Intake Tool.
    	      */
    	     {
              name: 'educationPlanSpecialNeedsLabel', 
              type: 'string', 
              defaultValue: 'Special needs or require special accomodation?'
             },
    	     /*
    	      * Label to use for the Coach field displays across the application.
    	      */
    	     {
              name: 'coachFieldLabel', 
              type: 'string', 
              defaultValue: 'Coach'
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