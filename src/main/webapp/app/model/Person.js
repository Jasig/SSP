Ext.define('Ssp.model.Person', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name: 'photoUrl', type: 'string'},
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
    		 {name: 'strengths', type: 'string'},
    		 {name: 'studentTypeId',type:'string'},
    		 {name: 'abilityToBenefit', type: 'boolean'},
    		 {name: 'anticipatedStartTerm', type: 'string'},
    		 {name: 'anticipatedStartYear', type: 'string'},
    		 {name: 'studentIntakeRequestDate', type: 'time'},
    		 {name: 'specialServiceGroups', type: 'auto'},
    		 {name: 'referralSources', type: 'auto'},
    		 {name: 'serviceReasons', type: 'auto'},
             {name: 'currentAppointment', type: 'auto'}],
    
             /*defaultValue:{"id" : "",
             "startDate" : 1331269200000,
             "endDate" : 1331269200000} */
    		 
    		 //'programStatus',
    		 //'registrationStatus',
    		 //'paymentStatus',
    		 //'cumGPA',
    		 //'academicPrograms'
    		 		 
    getFullName: function(){ 
    	var firstName = this.get('firstName') || "";
    	var middleInitial = this.get('middleInitial') || "";
    	var lastName = this.get('lastName') || "";
    	return firstName + " " + middleInitial + " " + lastName;
    },
    
    getFormattedBirthDate: function(){
    	return Ext.util.Format.date( this.get('birthDate'),'m/d/Y');
    },
    
    getFormattedStudentIntakeRequestDate: function(){
    	return Ext.util.Format.date( this.get('studentIntakeRequestDate'),'m/d/Y');   	
    }
});