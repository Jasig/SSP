Ext.define('Ssp.view.person.Appointment', {
	extend: 'Ext.form.Panel',
	alias: 'widget.personappointment',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.AppointmentViewController',
    inject: {
    	person: 'currentPerson'
    },
	initComponent: function() {	
		var me=this;
		Ext.apply(this, 
				{
			    fieldDefaults: {
			        msgTarget: 'side',
			        labelAlign: 'right',
			        labelWidth: 200
			    },	
			    border: 0,
			    padding: 0,
				items: [{
			            xtype: 'fieldset',
			            border: 0,
			            title: '',
			            defaultType: 'textfield',
			            defaults: {
			                anchor: '100%'
			            },
			       items: [{
				    	xtype: 'datefield',
				    	fieldLabel: 'Appointment Date',
				    	itemId: 'appointmentDate',
				        name: 'appointmentDate',
				        allowBlank: false
				    },{
				        xtype: 'timefield',
				        name: 'startTime',
				        fieldLabel: 'Start Time',
				        minValue: '8:00 AM',
				        maxValue: '7:00 PM',
				        increment: 30,
				        allowBlank: false,
				        anchor: '100%'
				    },{
				        xtype: 'timefield',
				        name: 'endTime',
				        fieldLabel: 'End Time',
				        minValue: '8:00 AM',
				        maxValue: '7:00 PM',
				        allowBlank: false,
				        increment: 30,
				        anchor: '100%'
				    },{
				        xtype: 'checkboxgroup',
				        fieldLabel: 'Send Student Intake Request',
				        columns: 1,
				        items: [
				            {boxLabel: '', name: 'sendStudentIntakeRequest'},
				        ]
				    },{
				    	xtype: 'displayfield',
				        fieldLabel: 'Last Student Intake Request Date',
				        name: 'lastStudentIntakeRequestDate',
				        value: ((me.person.getFormattedStudentIntakeRequestDate().length > 0) ? me.person.getFormattedStudentIntakeRequestDate() : 'No requests have been sent')
				    }]
			    }]
			});
		
		return me.callParent(arguments);
	}
});