Ext.define('Ssp.model.Person', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name: 'photoUrl', type: 'string'},
             {name: 'schoolId', type: 'string'},
    		 {name: 'firstName', type: 'string'},
             {name: 'middleName', type: 'string'},
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
    		 {name: 'studentIntakeCompleteDate', type: 'date', dateFormat: 'time'},
    		 {name: 'currentProgramStatusName', type: 'auto'},
    		 {name: 'registeredForCurrentTerm', type: 'string'}],
    		 		 
    getFullName: function(){ 
    	var firstName = this.get('firstName') || "";
    	var middleName = this.get('middleName') || "";
    	var lastName = this.get('lastName') || "";
    	return firstName + " " + middleName + " " + lastName;
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

    setCoachId: function( value ){
    	if (value != "")
    	{
        	if ( this.get('coach') != null)
        	{
        		this.set('coach',{"id":value});
        	}    		
    	}
    },    
    
    getCoachFullName: function(){
    	var coach = this.get('coach');
    	return ((coach != null)? coach.firstName + ' ' + coach.lastName : "");   	
    },     

    getCoachWorkPhone: function(){
    	var coach = this.get('coach');
    	return ((coach != null)? coach.workPhone : "");   	
    },    

    getCoachPrimaryEmailAddress: function(){
    	var coach = this.get('coach');
    	return ((coach != null)? coach.primaryEmailAddress : "");   	
    },    

    getCoachOfficeLocation: function(){
    	var coach = this.get('coach');
    	return ((coach != null)? coach.officeLocation : "");   	
    },    
    
    getCoachDepartmentName: function(){
    	var coach = this.get('coach');
    	return ((coach != null)? coach.departmentName : "");   	
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
    	var me=this;
    	if (value != "")
    	{
        	if ( me.get('studentType') != null)
        	{
        		me.set('studentType',{"id":value});
        	}    		
    	}
    },
    
    getProgramStatusName: function(){
    	return this.get('currentProgramStatusName')? this.get('currentProgramStatusName') : "";   	
    },
    
    /*
     * cleans properties that will be unable to be saved if not null
     */ 
    setPropsNullForSave: function( jsonData ){
		delete jsonData.studentIntakeCompleteDate;
		delete jsonData.currentProgramStatusName;
		if( jsonData.serviceReasons == "" )
		{
			jsonData.serviceReasons=null;
		}
		
		return jsonData;
    }
});