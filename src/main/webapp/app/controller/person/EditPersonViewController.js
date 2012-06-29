Ext.define('Ssp.controller.person.EditPersonViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        person: 'currentPerson',
        sspConfig: 'sspConfig'
    },
    control: {
    	studentIdField: '#studentId'
    },  
	init: function() {
		var me=this;
		
		// alias the studentId field and provide validation
		var studentId = me.getStudentIdField();
		studentId.setFieldLabel(me.sspConfig.get('studentIdAlias') + Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY);
		Ext.apply(studentId, {
	                  minLength: me.sspConfig.get('studentIdMinValidationLength'),
	                  minLengthText: me.sspConfig.get('studentIdMinValidationErrorText'),
	                  maxLength: me.sspConfig.get('studentIdMaxValidationLength'),
	                  maxLengthText: me.sspConfig.get('studentIdMaxValidationErrorText'),
	                  vtype: 'studentIdValidator',
	                  vtypeText: me.sspConfig.get('studentIdValidationErrorText')
                     });		
		me.getView().loadRecord( this.person );
		
		return me.callParent(arguments);
    }
});