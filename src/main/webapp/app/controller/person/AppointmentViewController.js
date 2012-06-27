Ext.define('Ssp.controller.person.AppointmentViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	appointment: 'currentAppointment',
    	person: 'currentPerson'
    },
    control: {

    },
	init: function() {
		var me=this;

		me.getView().loadRecord( me.appointment );
		
		return this.callParent(arguments);
    }
});