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
             {name: 'nonLocalAddress', type:'boolean', useNull: true},
    		 {name: 'addressLine1', type: 'string'},
             {name: 'addressLine2', type: 'string'},
    		 {name: 'city', type: 'string'},
             {name: 'state', type: 'string'},
    		 {name: 'zipCode', type: 'string'},
    		 {name: 'alternateAddressInUse', type:'boolean', useNull: true},
    		 {name: 'alternateAddressLine1', type: 'string'},
             {name: 'alternateAddressLine2', type: 'string'},
    		 {name: 'alternateAddressCity', type: 'string'},
             {name: 'alternateAddressState', type: 'string'},
    		 {name: 'alternateAddressZipCode', type: 'string'},
    		 {name: 'alternateAddressCountry', type: 'string'},
             {name: 'primaryEmailAddress', type: 'string'},
    		 {name: 'secondaryEmailAddress', type: 'string'},
             {name: 'birthDate', type: 'date', dateFormat: 'time'},
    		 {name: 'username', type: 'string'},
    		 {name: 'enabled', type: 'boolean', defaultValue: true},
             {name: 'coach', type: 'auto'},
    		 {name: 'strengths', type: 'string'},
    		 {name: 'studentType',type:'auto'},
    		 {name: 'abilityToBenefit', type: 'boolean'},
    		 {name: 'anticipatedStartTerm', type: 'string'},
    		 {name: 'anticipatedStartYear', type: 'string'},
    		 {name: 'actualStartTerm', type: 'string'},
    		 {name: 'actualStartYear', type: 'string'},
    		 {name: 'studentIntakeRequestDate', type: 'date', dateFormat: 'time'},
    		 {name: 'specialServiceGroups', type: 'auto'},
    		 {name: 'referralSources', type: 'auto'},
    		 {name: 'serviceReasons', type: 'auto'},
    		 {name: 'studentIntakeCompleteDate', type: 'date', dateFormat: 'time'},
    		 {name: 'currentProgramStatusName', type: 'auto'},
    		 {name: 'registeredTerms', type: 'string'},
    		 {name: 'paymentStatus', type: 'string'},
             {name: 'activeAlertsCount', type: 'int'},
             {name: 'closedAlertsCount', type: 'int'}],
    		 		 
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
    
    getPhotoUrl: function(){
    	return  this.get('photoUrl')   	
    },    
    
    setPhotoUrl: function( value ){
    	var me=this;
    	if (value != "")
    	{
        	if ( me.get('photoUrl') != null)
        	{
        		me.set('photoUrl',value);
        	}    		
    	}
    },
    
    getProgramStatusName: function(){
    	return this.get('currentProgramStatusName')? this.get('currentProgramStatusName') : "";   	
    },

    getEarlyAlertRatio: function() {
        return this.get('activeAlertsCount') + '/' + (this.get('activeAlertsCount') + this.get('closedAlertsCount'));
    },
 
    buildAddress: function(){
    	var me=this;
    	var address = "";
    	address += ((me.get('addressLine1') != null)? me.get('addressLine1') + '<br/>' : "");
    	address += ((me.get('city') != null)? me.get('city') + ', ': "");
    	address += ((me.get('state') != null)? me.get('state') + '<br/>': "");
    	address += ((me.get('zipCode') != null)? me.get('zipCode') : "");	
    	// ensure a full address was defined 
    	if (address.length < 30)
    	{
    		address = "";
    	}    	
    	return address;   	
    },
    
    buildAlternateAddress: function(){
    	var me=this;
    	var alternateAddress = "";
    	alternateAddress += ((me.get('alternateAddressLine1') != null)? me.get('alternateAddressLine1') + '<br/>' : "");
    	alternateAddress += ((me.get('alternateAddressCity') != null)? me.get('alternateAddressCity') : "");
    	alternateAddress += ((me.get('alternateAddressState') != null)? ', ' + me.get('alternateAddressState') + '<br/>': "");
    	alternateAddress += ((me.get('alternateAddressZipCode') != null)? me.get('alternateAddressZipCode') : "<br />");	
    	alternateAddress += ((me.get('alternateAddressCountry') != null)? ', ' + me.get('alternateAddressCountry') : "");	
    	// ensure a full address was defined
    	if (alternateAddress.length < 30)
    	{
    		alternateAddress = "";
    	}
    	return alternateAddress;   	
    },
    
    /*
     * cleans properties that will be unable to be saved if not null
     */ 
    setPropsNullForSave: function( jsonData ){
		delete jsonData.studentIntakeCompleteDate;
		delete jsonData.currentProgramStatusName;

		if ( jsonData.studentType == "")
		{
			jsonData.studentType = null;
		}
		
		if ( jsonData.coach == "")
		{
			jsonData.coach = null;
		}
		
		if( jsonData.serviceReasons == "" )
		{
			jsonData.serviceReasons=null;
		}
		
		if( jsonData.specialServiceGroups == "" )
		{
			jsonData.specialServiceGroups=null;
		}

		if( jsonData.referralSources == "" )
		{
			jsonData.referralSources=null;
		}

		return jsonData;
    },
    
    populateFromExternalData: function( jsonData ){
    	var me=this;
    	me.set('photoUrl',jsonData.photoUrl);
    	me.set('schoolId',jsonData.schoolId);
    	me.set('firstName',jsonData.firstName);
    	me.set('middleName',jsonData.middleName);
    	me.set('lastName', jsonData.lastName);	
    	me.set('anticipatedStartTerm',jsonData.anticipatedStartTerm);
    	me.set('anticipatedStartYear',jsonData.anticipatedStartYear);
    	me.set('homePhone', jsonData.homePhone);
    	me.set('cellPhone', jsonData.cellPhone);
    	me.set('workPhone', jsonData.workPhone);
    	me.set('addressLine1', jsonData.addressLine1);
    	me.set('addressLine2', jsonData.addressLine2);
    	me.set('city', jsonData.city);
    	me.set('state', jsonData.state);
    	me.set('zipCode', jsonData.zipCode);
    	me.set('primaryEmailAddress', jsonData.primaryEmailAddress);
    	me.set('secondaryEmailAddress', jsonData.secondaryEmailAddress);
    	me.set('birthDate', jsonData.birthDate);
    	me.set('username', jsonData.username);
    	me.set('photoUrl', jsonData.photoUrl);
    }
});