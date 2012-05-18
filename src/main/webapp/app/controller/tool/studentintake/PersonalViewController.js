Ext.define('Ssp.controller.tool.studentintake.PersonalViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	citizenshipsStore: 'citizenshipsStore',
    	sspConfig: 'sspConfig'
    },
    
	init: function() {
    	var disabled = this.sspConfig.get('syncStudentPersonalDataWithExternalSISData');
		var studentIdAlias = this.sspConfig.get('studentIdAlias');
		var minStudentIdLen = this.sspConfig.get('studentIdMinValidationLength');
		var maxStudentIdLen = this.sspConfig.get('studentIdMaxValidationLength');
		var studentIdAllowableCharacters = this.sspConfig.get('studentIdAllowableCharacters');
		// Example RegEx - /(^[1-9]{7,9})/
		var regExString = '^([' + studentIdAllowableCharacters + ']';
		regExString = regExString + '{' + minStudentIdLen + ',';
		regExString = regExString + maxStudentIdLen + '})';
		var validStudentId = new RegExp( regExString );
        var studentIdValErrorText = 'Not a valid ' + studentIdAlias + '.';
        studentIdValErrorText += 'The value should be at least ' + minStudentIdLen + ' characters & no more than ' + maxStudentIdLen + ' characters. '; 
		studentIdValErrorText += 'You should only use the following character list for input: ' + studentIdAllowableCharacters;
        Ext.apply(Ext.form.field.VTypes, {
            //  vtype validation function
            studentIdValidator: function(val, field) {
                return validStudentId.test(val);
            }
        });
		
		firstName = Ext.ComponentQuery.query('#firstName')[0].setDisabled(disabled);
		middleInitial = Ext.ComponentQuery.query('#middleInitial')[0].setDisabled(disabled);
		lastName = Ext.ComponentQuery.query('#lastName')[0].setDisabled(disabled);
		studentId = Ext.ComponentQuery.query('#studentId')[0];
		studentId.setDisabled(disabled);
		studentId.setFieldLabel(studentIdAlias);
		Ext.apply(studentId, {
			                  minLength: minStudentIdLen,
			                  minLengthText: '',
			                  maxLength: maxStudentIdLen,
			                  maxLengthText: '',
			                  vtype: 'studentIdValidator',
			                  vtypeText: studentIdValErrorText
                             });
		birthDate = Ext.ComponentQuery.query('#birthDate')[0].setDisabled(disabled);
		homePhone = Ext.ComponentQuery.query('#homePhone')[0].setDisabled(disabled);
		workPhone = Ext.ComponentQuery.query('#workPhone')[0].setDisabled(disabled);
		address = Ext.ComponentQuery.query('#address')[0].setDisabled(disabled);
		city = Ext.ComponentQuery.query('#city')[0].setDisabled(disabled);
		state = Ext.ComponentQuery.query('#state')[0].setDisabled(disabled);
		zipCode = Ext.ComponentQuery.query('#zipCode')[0].setDisabled(disabled);
		primaryEmailAddress = Ext.ComponentQuery.query('#primaryEmailAddress')[0].setDisabled(disabled);

		return this.callParent(arguments);
    }
});