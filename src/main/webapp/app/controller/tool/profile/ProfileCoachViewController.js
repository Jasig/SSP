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
Ext.define('Ssp.controller.tool.profile.ProfileCoachViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
        person: 'currentPerson',
        personLite: 'personLite',
        personService: 'personService',
        studentActivityService: 'studentActivityService',
        studentActivitiesStore: 'studentActivitiesStore',
        sspConfig: 'sspConfig'
    },
    
    control: {
    	
    	coachNameField: '#coachName',
    	coachWorkPhoneField: '#coachWorkPhone',
    	coachDepartmentNameField: '#coachDepartmentName',
    	coachOfficeLocationField: '#coachOfficeLocation',
    	coachPrimaryEmailAddressField: '#coachPrimaryEmailAddress',
    	coachPhotoUrlField: '#coachPhotoUrl',
    	coachLastServiceDateField: '#coachLastServiceDate',
    	coachLastServiceProvidedField: '#coachLastServiceProvided'
     },
	init: function() {
		var me=this;
		
		var studentIdAlias = me.sspConfig.get('studentIdAlias');
		var id =  me.personLite.get('id');
		me.getView().getForm().reset();
		
		if (id != "")
		{
			// display loader
			me.getView().setLoading( true );
			me.personService.get( id, {
				success: me.getPersonSuccess,
				failure: me.getPersonFailure,
				scope: me
			});
			me.studentActivitiesStore.removeAll();
			me.studentActivityService.getAll( id, {
				success: me.getStudentActivitySuccess,
				failure: me.getStudentActivitySuccess,
				scope: me
			});
		}
		
		return me.callParent(arguments);
    },
    
    getPersonSuccess: function( r, scope ){
    	var me=scope;

    	var coachNameField = me.getCoachNameField();
		var coachWorkPhoneField = me.getCoachWorkPhoneField();
		var coachDepartmentNameField = me.getCoachDepartmentNameField();
		var coachOfficeLocationField = me.getCoachOfficeLocationField();
		var coachPrimaryEmailAddressField = me.getCoachPrimaryEmailAddressField();
		var coachPhotoUrlField = me.getCoachPhotoUrlField();
		
		var id= me.personLite.get('id');
		var studentIdAlias = me.sspConfig.get('studentIdAlias');
		
		// load the person data
		me.person.populateFromGenericObject(r);		
		
		// load general student record
		me.getView().loadRecord( me.person );
		
		// load additional values
		
		coachNameField.setValue( me.person.getCoachFullName() );
		coachWorkPhoneField.setValue( me.person.getCoachWorkPhone() );
		coachDepartmentNameField.setValue( me.person.getCoachDepartmentName() );
		coachOfficeLocationField.setValue( me.person.getCoachOfficeLocation() );
		coachPrimaryEmailAddressField.setValue( me.person.getCoachPrimaryEmailAddress() );
		coachPhotoUrlField.setSrc( me.person.getCoachPhotoUrl() );
		// hide the loader
    	me.getView().setLoading( false ); 
    },
    
    getPersonFailure: function( response, scope){
    	var me=scope;
    	me.getView().setLoading( false );
    },
    
    getStudentActivitySuccess: function( r, scope ){
    	var me=scope;
    	var serviceDate = me.getCoachLastServiceDateField();
		var lastService = me.getCoachLastServiceProvidedField();
		
		
		me.studentActivitiesStore.loadData(r);
		var activity = me.studentActivitiesStore.getCoachLastActivity(me.person.getCoachId());
		if(activity){
			serviceDate.setValue(activity.get('activityDateFormatted'));
			lastService.setValue(activity.get('activity'));
		}else{
			serviceDate.setValue("");
			lastService.setValue("No Record Found");
		}
        me.getView().setLoading( false );
    },
    
    getStudentActivityFailure: function( response, scope ){
    	var me=scope;
    	me.getView().setLoading( false );  	
    }
});