Ext.define('Ssp.controller.tool.profile.ProfilePersonViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
        person: 'currentPerson',
        profileSpecialServiceGroupsStore: 'profileSpecialServiceGroupsStore',
        profileReferralSourcesStore: 'profileReferralSourcesStore',
        sspConfig: 'sspConfig'
    },
    
    control: {
    	nameField: '#studentName',
    	studentIdField: '#studentId',
    	birthDateField: '#birthDate'
    },
	init: function() {
		var me=this;
		me.getView().getForm().reset();
		var studentRecordComp = Ext.ComponentQuery.query('.studentrecord')[0];
		var studentIdField = me.getStudentIdField();
		var nameField = me.getNameField();
		var birthDateField = me.getBirthDateField();
		var id=me.person.get('id');
		var personUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('person') );
		var studentIdAlias = me.sspConfig.get('studentIdAlias');			
		var successFunc = function(response,view){
	    	var r = Ext.decode(response.responseText);
    		var fullName = me.person.getFullName() ;
	    	me.person.populateFromGenericObject(r);
    		
    		// load special service groups
    		if (r.specialServiceGroups != null)
    		{
    			me.profileSpecialServiceGroupsStore.loadData(r.specialServiceGroups);
    		}
    		
    		// load referral sources
    		if (r.referralSources != null)
    		{
    			me.profileReferralSourcesStore.loadData(r.referralSources);
    		}
    		
    		// load general student record
    		me.getView().loadRecord( me.person );
    		
    		// load additional values
    		studentIdField.setFieldLabel(studentIdAlias+'<span style="color: rgb(255, 0, 0); padding-left: 2px;">*</span>');
    		nameField.setValue( fullName );
    		birthDateField.setValue( me.person.getFormattedBirthDate() );
    		studentRecordComp.setTitle('Student Record - ' + fullName)
		};
		
		// load the person record
		me.apiProperties.makeRequest({
			url: personUrl+id,
			method: 'GET',
			successFunc: successFunc 
		});
		
		return this.callParent(arguments);
    }
});