/*
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
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
        coachHistoryService: 'coachHistoryService',
        studentActivityService: 'studentActivityService',
        studentActivitiesStore: 'studentActivitiesStore',
        configStore: 'configStore'
    },
    
    control: {
    	coachNameField: '#coachName',
    	coachWorkPhoneField: '#coachWorkPhone',
    	coachDepartmentNameField: '#coachDepartmentName',
    	coachOfficeLocationField: '#coachOfficeLocation',
    	coachPrimaryEmailAddressField: '#coachPrimaryEmailAddress',
    	coachPhotoUrlField: '#coachPhotoUrl',
    	coachLastServiceDateField: '#coachLastServiceDate',
    	coachLastServiceProvidedField: '#coachLastServiceProvided',
    	previousCoachDisplay: '#previousCoachDisplay',
		previousCoachNameField: '#previousCoachName',
        previousCoachChangedByNameField: '#previousCoachChangedByName',
        previousCoachChangeDateField: '#previousCoachChangeDate'
    },

	init: function() {
		var me=this;
		
		var studentIdAlias = me.configStore.getConfigByName('studentIdAlias');
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
		var studentIdAlias = me.configStore.getConfigByName('studentIdAlias');
		
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

		me.coachHistoryService.getCurrentCoachChangeHistory( id, {
			success: me.getCoachHistorySuccess,
			failure: me.getPersonFailure,
			scope: me
		});

		// hide the loader
    	me.getView().setLoading( false ); 
    },

    getCoachHistorySuccess: function(r, scope) {
    	var me = scope;

        var personCoachHistory = new Ext.create('Ssp.model.PersonCoachHistory');
		var previousCoachNameField = me.getPreviousCoachNameField();
		var previousCoachChangedByNameField = me.getPreviousCoachChangedByNameField();
		var previousCoachChangeDateField = me.getPreviousCoachChangeDateField();

		if (r != null && previousCoachNameField && previousCoachChangeDateField && previousCoachChangedByNameField) {
			personCoachHistory.populateFromGenericObject(r);
			me.getPreviousCoachDisplay().show();

			if (personCoachHistory.get('previousCoach') != null &&
					personCoachHistory.get('previousCoach').fullName.indexOf("null null") < 0) {
				previousCoachNameField.setValue((personCoachHistory.get('previousCoach')).fullName);
				previousCoachNameField.show();

                if (personCoachHistory.get('modifiedBy') != null &&
                        personCoachHistory.get('modifiedBy').fullName.indexOf("null null") < 0) {
                    if ((personCoachHistory.get('modifiedBy')).fullName.indexOf("System Administrator") > -1) {
                        previousCoachChangedByNameField.setValue("System Process");
                        previousCoachChangedByNameField.show();

                    } else {
                        previousCoachChangedByNameField.setValue((personCoachHistory.get('modifiedBy')).fullName);
                        previousCoachChangedByNameField.show();
                    }
                }

                if (personCoachHistory.get('modifiedDate') != null && personCoachHistory.get('modifiedDate') != null) {
                    previousCoachChangeDateField.setValue(Ext.util.Format.date(personCoachHistory.get('modifiedDate'),'m/d/Y'));
                    previousCoachChangeDateField.show();
                }

			} else {
				previousCoachNameField.setValue("No Change.");
				previousCoachNameField.show();
			}
		}

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
