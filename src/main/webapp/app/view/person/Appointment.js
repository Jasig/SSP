Ext.define('Ssp.view.person.Appointment', {
	extend: 'Ext.form.Panel',
	alias: 'widget.personappointment',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.AppointmentViewController',
	initComponent: function() {	
		var me=this;
		Ext.apply(me, 
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
			            padding: 0,
			            title: '',
			            defaultType: 'textfield',
			            defaults: {
			            	anchor: '50%'
			            },
			       items: [{
				    	xtype: 'datefield',
				    	fieldLabel: 'Appointment Date',
				    	itemId: 'appointmentDateField',
				    	altFormats: 'm/d/Y|m-d-Y',
				    	invalidText: '{0} is not a valid date - it must be in the format: 06/21/2012 or 06-21-2012',
				    	name: 'appointmentDate',
				        allowBlank: false
				    },{
				        xtype: 'timefield',
				        name: 'startTime',
				        itemId: 'startTimeField',
				        fieldLabel: 'Start Time',
				        increment: 30,
				        typeAhead: false,
				        allowBlank: false
				    },{
				        xtype: 'timefield',
				        name: 'endTime',
				        itemId: 'endTimeField',
				        fieldLabel: 'End Time',
				        typeAhead: false,
				        allowBlank: false,
				        increment: 30
				    }]
			    }]
			});
		
		return me.callParent(arguments);
	}
});