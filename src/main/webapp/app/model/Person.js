Ext.define('Ssp.model.Person', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name: 'photoUrl', type: 'string', defaultValue: 'images/student-pic-small.png'},
             {name: 'schoolId', type: 'string'},
    		 {name: 'firstName', type: 'string'},
             {name: 'middleInitial', type: 'string'},
    		 {name: 'lastName', type: 'string'},
             {name: 'homePhone', type: 'string'},
    		 {name: 'cellPhone', type: 'string'},
             {name: 'workPhone', type: 'string'},
    		 {name: 'addressLine1', type: 'string'},
             {name: 'addressLine2', type: 'string'},
    		 {name: 'city', type: 'string'},
             {name: 'state', type: 'string'},
    		 {name: 'zipCode', type: 'string'},
             {name: 'primaryEmailAddress', type: 'string'},
    		 {name: 'secondaryEmailAddress', type: 'string'},
             {name: 'birthDate', type: 'date', dateFormat: 'time'},
    		 {name: 'username', type: 'string'},
             {name: 'userId', type: 'string'},
    		 {name: 'enabled', type: 'boolean'},
             {name: 'coachId', type: 'string'},
    		 {name: 'strengths', type: 'string', defaultValue: 'default strengths'}
    		 //'studentType',
    		 //'programStatus',
    		 //'registrationStatus',
    		 //'paymentStatus',
    		 //'cumGPA',
    		 //'academicPrograms'
    		 ],
    		 
    getFullName: function(){ 
    	var firstName = this.get('firstName') || "";
    	var middleInitial = this.get('middleInitial') || "";
    	var lastName = this.get('lastName') || "";
    	return firstName + " " + middleInitial + " " + lastName;
    },
    
    getFormattedBirthDate: function(){
    	return Ext.util.Format.date( this.get('birthDate'),'m/d/Y');
    }
});