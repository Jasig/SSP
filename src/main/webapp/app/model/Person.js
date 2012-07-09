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
             {name: 'coach', type: 'auto'},
    		 {name: 'strengths', type: 'string'},
    		 {name: 'studentType',type:'auto'},
    		 {name: 'abilityToBenefit', type: 'boolean'},
    		 {name: 'anticipatedStartTerm', type: 'string'},
    		 {name: 'anticipatedStartYear', type: 'string'},
    		 {name: 'studentIntakeRequestDate', type: 'date', dateFormat: 'time'},
    		 {name: 'specialServiceGroups', type: 'auto'},
    		 {name: 'referralSources', type: 'auto'},
    		 {name: 'serviceReasons', type: 'auto'},
             {name: 'currentAppointment', type: 'auto'},
    		 {name: 'studentIntakeCompleteDate', type: 'date', dateFormat: 'time'},
    		 {name: 'programStatuses', type: 'auto'}],
    
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
    },
    
    getCoachId: function(){
    	var coach = this.get('coach');
    	return ((coach != null)? coach.id : "");   	
    },

    getCoachName: function(){
    	var coach = this.get('coach');
    	return ((coach != null)? coach.firstName + ' ' + coach.lastName : "");   	
    },     
    
    setCoachId: function( value ){
    	if (value != "")
    	{
        	if ( this.get('coach') != null)
        	{
        		this.get('coach').id = value;
        	}else{
        		this.set('coach',{"id":value});
        	}    		
    	}
    },
    
    getStudentTypeId: function(){
    	var studentType = this.get('studentType');
    	return ((studentType != null)? studentType.id : "");   	
    },
 
    getStudentTypeName: function(){
    	var studentType = this.get('studentType');
    	return ((studentType != null)? studentType.name : "");   	
    },    
    
    setStudentTypeId: function( value ){
    	if (value != "")
    	{
        	if ( this.get('studentType') != null)
        	{
        		this.get('studentType').id = value;
        	}else{
        		this.set('studentType',{"id":value});
        	}    		
    	}
    },
    
    getProgramStatusName: function(){
    	var programStatus = this.get('programStatuses');
    	var programStatusName = "";
    	if (programStatus != null)
    	{
        	if (programStatus.length > 0)
        	{
        		programStatusName = programStatus[0].name;
        	}
    	}
    	return programStatusName;   	
    },  
    
    setAppointment: function( startDate, endDate ){
    	if (startDate != null && endDate != null)
    	{
        	if ( this.get('currentAppointment') != null )
    		{
    		   this.get('currentAppointment').startDate = startDate;
    		   this.get('currentAppointment').endDate = endDate;
    		}else{
    		   model.set('currentAppointment', {
    			                                  "id":"",
    			                                  "startDate":startDate,
    			                                  "endDate":endDate
    			                                 });
    		}    		
    	}
    }
});