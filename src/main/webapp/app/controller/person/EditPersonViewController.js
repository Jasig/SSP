Ext.define('Ssp.controller.person.EditPersonViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	appEventsController: 'appEventsController',
        person: 'currentPerson',
        personService: 'personService',
        sspConfig: 'sspConfig'
    },
    control: {
    	retrieveFromExternalButton: {
    		selector: '#retrieveFromExternalButton',
    		listeners: {
                click: 'onRetrieveFromExternalClick'
            }       		
    	},
 		
    	firstNameField: {
    		selector: '#firstName',
    		listeners: {
                change: 'onStudentNameChange'
            }
    	},
    	
    	middleNameField: {
    		selector: '#middleName',
    		listeners: {
                change: 'onStudentNameChange'
            }    		
    	},
    	
    	lastNameField: {
    		selector: '#lastName',
    		listeners: {
                change: 'onStudentNameChange'
            }
    	}, 
    	
    	studentIdField: {
    		selector: '#studentId',
    		listeners: {
                validityChange: 'onStudentIdValidityChange'
            }
    	},
    	
    	homePhoneField: '#homePhone',
    	workPhoneField: '#workPhone',
    	homePhoneField: '#homePhone',
    	primaryEmailAddressField: '#primaryEmailAddress',
    	secondaryEmailAddressField: '#secondaryEmailAddress'
    },  
	init: function() {
		var me=this;
    	var disabled = me.sspConfig.get('syncStudentPersonalDataWithExternalData');		
		// alias the studentId field and provide validation
		var studentIdField = me.getStudentIdField();
		studentIdField.setFieldLabel(me.sspConfig.get('studentIdAlias') + Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY);
		Ext.apply(studentIdField, {
	                  minLength: me.sspConfig.get('studentIdMinValidationLength'),
	                  minLengthText: me.sspConfig.get('studentIdMinValidationErrorText'),
	                  maxLength: me.sspConfig.get('studentIdMaxValidationLength'),
	                  maxLengthText: me.sspConfig.get('studentIdMaxValidationErrorText'),
	                  vtype: 'studentIdValidator',
	                  vtypeText: me.sspConfig.get('studentIdValidationErrorText')
                     });		
		
		// when editing a student, 
		if (me.person.get('id') != "")
		{
			// set the retrieve from SSI button visbility
			me.getRetrieveFromExternalButton().setVisible( false );
		
			// disable fields if the external configuration mode is enabled
			me.getFirstNameField().setDisabled(disabled);
			me.getMiddleNameField().setDisabled(disabled);
			me.getLastNameField().setDisabled(disabled);
			studentIdField.setDisabled(disabled);
			me.getHomePhoneField().setDisabled(disabled);
			me.getWorkPhoneField().setDisabled(disabled);
			me.getPrimaryEmailAddressField().setDisabled(disabled);
			me.getSecondaryEmailAddressField().setDisabled(disabled);
		}

		me.getView().getForm().reset();
		me.getView().loadRecord( me.person );
		
		me.setRetrieveFromExternalButtonDisabled( !studentIdField.isValid() );
		//me.getRetrieveFromExternalButton().setVisible( false );
		
		return me.callParent(arguments);
    },
    
    onStudentNameChange: function( comp, newValue, oldValue, eOpts){
    	var me=this;
    	me.person.set(comp.name,newValue);
    	me.appEventsController.getApplication().fireEvent('studentNameChange');
    },
    
    onStudentIdValidityChange: function(comp, isValid, eOpts){
    	var me=this;
        me.setRetrieveFromExternalButtonDisabled( !isValid );
    },
    
    setRetrieveFromExternalButtonDisabled: function( enabled ){
    	this.getRetrieveFromExternalButton().setDisabled( enabled );
    },
    
    onRetrieveFromExternalClick: function( button ){
    	var me=this;
    	var studentIdField = me.getStudentIdField();
    	var schoolId = studentIdField.value;
    	if ( studentIdField.isValid() )
    	{
    		if (schoolId != "")
    		{
    			me.getView().setLoading( true );
    			me.personService.getBySchoolId( schoolId,{
    				success: me.getBySchoolIdSuccess,
    				failure: me.getBySchoolIdFailure,
    				scope: me
    			});
    		}
    	}else{
    		Ext.Msg.alert('SSP Error','Please correct the errors in your form.');
    	}
    },
    
    getBySchoolIdSuccess: function( r, scope ){
		var me=scope;
		me.getView().setLoading( false );
		if ( r != null)
		{
			me.getView().getForm().reset();
			delete r.id;
			var person = new Ssp.model.Person();
			delete r.createdBy;
			delete r.modifiedBy;
			delete r.username;
			delete r.studentIntakeCompleteDate;
			me.person.data = person.data;
			me.person.populateFromGenericObject( r );	
			me.getView().loadRecord( me.person );
		}else{
			Ext.Msg.alert('SSP Notification','There were no records found with the provided ID. Please try a different value.');
		}
    },    
    
    getBySchoolIdFailure: function( response, scope ){
    	var me=scope;
    	me.getView().setLoading( false );
    }
});