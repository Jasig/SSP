Ext.define('Ssp.controller.tool.profile.ProfilePersonViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
        person: 'currentPerson',
        personLite: 'personLite',
        personService: 'personService',
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

		// Set defined configured label for the studentId field
		studentIdField.setFieldLabel(studentIdAlias);		
		
		if (id != "")
		{
			// display loader
			me.getView().setLoading( true );
			me.personService.get( me.personLite.get('id'), {
				success: getPersonSuccess,
				failure: getPersonFailure,
				scope: me
			});
		}
		
		return me.callParent(arguments);
    },
    
    getPersonSuccess: function( r, scope ){
    	var me=scope;
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
		var studentIdAlias = me.sspConfig.get('studentIdAlias');
		var fullName; 		
		
		// load the person data
		me.person.populateFromGenericObject(r);
		
    	fullName = me.person.getFullName();
   	
		// load special service groups
    	me.profileSpecialServiceGroupsStore.removeAll();
		if (r.specialServiceGroups != null)
		{
			me.profileSpecialServiceGroupsStore.loadData( me.person.get('specialServiceGroups') );
		}
		
		// load referral sources
		me.profileReferralSourcesStore.removeAll();  
		if (r.referralSources != null)
		{
			me.profileReferralSourcesStore.loadData( me.person.get('referralSources') );
		}

		// load service reasons
		me.profileServiceReasonsStore.removeAll();  
		if (r.serviceReasons != null)
		{
			me.profileServiceReasonsStore.loadData( me.person.get('serviceReasons') );
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
    },
    
    getPersonFailure: function( response, scope){
    	var me=scope;
    	me.getView().setLoading( false );
    }
});