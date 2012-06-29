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
				    	itemId: 'appointmentDateField',
				        name: 'appointmentDate',
				        allowBlank: false
				    },{
				        xtype: 'timefield',
				        name: 'startTime',
				        itemId: 'startTimeField',
				        fieldLabel: 'Start Time',
				        increment: 30,
				        typeAhead: false,
				        allowBlank: false,
				        anchor: '100%'
				    },{
				        xtype: 'timefield',
				        name: 'endTime',
				        itemId: 'endTimeField',
				        fieldLabel: 'End Time',
				        typeAhead: false,
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