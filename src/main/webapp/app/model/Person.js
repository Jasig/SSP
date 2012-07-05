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
    		 {name:'permissions', type:'auto', defaultValue: null},
    		 {name:'confidentialityLevels', type:'auto', defaultValue: null}],
    
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

    /**
     * Determines if a user has access to a provided array of permissions.
     * 
     * @arguments
     *  arrPermissions - an array of permissions to test against the granted permissions for this user.
     *  
     *  return true if all of the permissionsToTest exist in the user's record
     */
    hasPermissions: function( arrPermissions ){
   	   return Ext.Array.every(arrPermissions,function(permission){
   		   return this.hasPermission( permission );
   	   },this);
    },
    
    /**
     * Determines if a user has access to the provided permission.
     * @arguments
     *  - permission - a permission
     *  to test against the granted permissions for this user.
     *  
     *  return true if the permission exists in the user's record
     */
    hasPermission: function( permission ){
   	 return Ext.Array.contains( this.get('permissions'), permission );
    }    
});