Ext.define('Ssp.controller.tool.profile.ProfilePersonViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
        person: 'currentPerson',
        personLite: 'personLite',
        profileReferralSourcesStore: 'profileReferralSourcesStore',
        profileServiceReasonsStore: 'profileServiceReasonsStore',
        profileSpecialServiceGroupsStore: 'profileSpecialServiceGroupsStore',
        sspConfig: 'sspConfig'
    },
    
    control: {
    	nameField: '#studentName',
    	coachNameField: '#coachName',
    	coachWorkPhoneField: '#coachWorkPhone',
    	coachDepartmentNameField: '#coachDepartmentName',
    	coachOfficeLocationField: '#coachOfficeLocation',
    	coachPrimaryEmailAddressField: '#coachPrimaryEmailAddress',
    	studentIdField: '#studentId',
    	birthDateField: '#birthDate',
    	studentTypeField: '#studentType',
    	programStatusField: '#programStatus'
    },
	init: function() {
		var me=this;
		me.getView().getForm().reset();
		var studentRecordComp = Ext.ComponentQuery.query('.studentrecord')[0];
		var studentIdField = me.getStudentIdField();
		var nameField = me.getNameField();
		var coachNameField = me.getCoachNameField();
		var coachWorkPhoneField = me.getCoachWorkPhoneField();
		var coachDepartmentNameField = me.getCoachDepartmentNameField();
		var coachOfficeLocationField = me.getCoachOfficeLocationField();
		var coachPrimaryEmailAddressField = me.getCoachPrimaryEmailAddressField();
		var birthDateField = me.getBirthDateField();
		var studentTypeField = me.getStudentTypeField();
		var programStatusField = me.getProgramStatusField();
		var id= me.personLite.get('id');
		var personUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('person') );
		var studentIdAlias = me.sspConfig.get('studentIdAlias');			
		var successFunc = function(response,view){
	    	var r = Ext.decode(response.responseText);
    		var fullName; 		
    		
    		// load the person data
    		me.person.populateFromGenericObject(r);
    		
	    	fullName = me.person.getFullName();
	    	
	    	// console.log( me.person.get('programStatuses') );
	    	
    		// load special service groups
    		if (r.specialServiceGroups != null)
    		{
    			me.profileSpecialServiceGroupsStore.loadData( me.person.get('specialServiceGroups') );
    		}else{
    			me.profileSpecialServiceGroupsStore.removeAll();
    		}
    		
    		// load referral sources
    		if (r.referralSources != null)
    		{
    			me.profileReferralSourcesStore.loadData( me.person.get('referralSources') );
    		}else{
    			me.profileReferralSourcesStore.removeAll();    			
    		}

    		// load service reasons
    		if (r.serviceReasons != null)
    		{
    			me.profileServiceReasonsStore.loadData( me.person.get('serviceReasons') );
    		}else{
    			me.profileServiceReasonsStore.removeAll();    			
    		}    		
    		
    		// load general student record
    		me.getView().loadRecord( me.person );
    		
    		// load additional values
    		nameField.setValue( fullName );
    		coachNameField.setValue( me.person.getCoachFullName() );
    		coachWorkPhoneField.setValue( me.person.getCoachWorkPhone() );
    		coachDepartmentNameField.setValue( me.person.getCoachDepartmentName() );
    		coachOfficeLocationField.setValue( me.person.getCoachOfficeLocation() );
    		coachPrimaryEmailAddressField.setValue( me.person.getCoachPrimaryEmailAddress() );
    		birthDateField.setValue( me.person.getFormattedBirthDate() );
    		studentTypeField.setValue( me.person.getStudentTypeName() );
    		programStatusField.setValue( me.person.getProgramStatusName() );
    		studentRecordComp.setTitle('Student Record - ' + fullName);
    		
	    	// hide the loader
	    	me.getView().setLoading( false ); 
		};

		// Set defined configured label for the studentId field
		studentIdField.setFieldLabel(studentIdAlias);		
		
		if (id != "")
		{
			// display loader
			me.getView().setLoading( true );
			
			// load the person record
			me.apiProperties.makeRequest({
				url: personUrl+'/'+id,
				method: 'GET',
				successFunc: successFunc 
			});
		}
		
		return me.callParent(arguments);
    }
});