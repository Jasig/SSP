Ext.define('Ssp.model.StudentTO', {
    extend: 'Ssp.model.AbstractBaseTO',
    fields: ['studentIntakeCreatedDate',
    		 'photoUrl',
    		 'schoolId',
    		 {name: 'name', 
   		      convert: function(value, record) {
   		    	  return record.get('firstName') + " " + record.get('middleInitial') + " " + record.get('lastName');
   		      	}
    		 },
    		 'firstName',
    		 'middleInitial',
    		 'lastName',
    		 'homePhone',
    		 'cellPhone',
    		 'workPhone',
    		 {name: 'address', 
     		      convert: function(value, record) {
     		    	  return record.get('addressLine1');
     		      	}
      		 },
      		 'addressLine1',
      		 'addressLine2',
    		 'city',
    		 'state',
    		 'zipCode',
    		 'primaryEmailAddress',
    		 'secondaryEmailAddress',
    		 'birthDate',
    		 'studentType',
    		 'programStatus',
    		 'registrationStatus',
    		 'paymentStatus',
    		 'cumGPA',
    		 'academicPrograms',
    		 {name: 'tools', 
      		      convert: function(value, record) {
      		    	  var toolJson = [{
					        name: "Profile",
					        toolType: "Profile"
					    },{
					        name: "Student Intake",
					        toolType: "StudentIntake"
					    }
					];
      		    	  return toolJson;
      		      	}
       		 }]
    
});