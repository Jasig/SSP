Ext.define('Ssp.controller.person.EditPersonViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	appEventsController: 'appEventsController',
        person: 'currentPerson',
        sspConfig: 'sspConfig'
    },
    control: {
    	retrieveFromExternalButton: '#retrieveFromExternalButton',
    	
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
    	var disabled = me.sspConfig.get('syncStudentPersonalDataWithExternalSISData');		
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

		me.getView().loadRecord( this.person );
		
		// me.setRetrieveFromExternalButtonDisabled( !studentIdField.isValid() );
		me.getRetrieveFromExternalButton().setVisible( false );
		
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
    }
});