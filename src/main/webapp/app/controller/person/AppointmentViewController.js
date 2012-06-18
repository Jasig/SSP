Ext.define('Ssp.controller.person.AppointmentViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        studentTypesStore: 'studentTypesStore'
    },
    control: {
    	departmentField: '#departmentField',
    	phoneField: '#phoneField',
    	officeField: '#officeField',
    	emailAddressField: '#emailAddressField',
    	
    	'coachCombo': {
    		select: 'onCoachComboSelect'
    	},    	
    },
	init: function() {
		this.studentTypesStore.load();

		return this.callParent(arguments);
    },
    
	onCoachComboSelect: function(comp, records, eOpts){
		var coach;
		if(records.length>0){
			coach=records[0];
			this.getDepartmentField().setValue( coach.get('department') );
			this.getPhoneField().setValue( coach.get('phone') );
			this.getEmailAddressField().setValue( coach.get('emailAddress') );
			this.getOfficeField().setValue( coach.get('office') );
		}
	},
});