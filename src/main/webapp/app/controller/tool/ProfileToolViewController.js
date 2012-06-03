Ext.define('Ssp.controller.tool.ProfileToolViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        person: 'currentPerson',
        sspConfig: 'sspConfig'
    },
    control: {
    	nameField: '#studentName',
    	studentIdField: '#studentId',
    	birthDateField: '#birthDate'
    },
	init: function() {
		var studentIdAlias = this.sspConfig.get('studentIdAlias');

		// load general student record
		this.getView().loadRecord( this.person );
		
		// load additional values
		this.getStudentIdField().setFieldLabel( studentIdAlias );
		this.getNameField().setValue( this.person.getFullName() );
		this.getBirthDateField().setValue( this.person.getFormattedBirthDate() );
		
		return this.callParent(arguments);
    }
});