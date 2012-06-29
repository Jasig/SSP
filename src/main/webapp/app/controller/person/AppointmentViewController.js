Ext.define('Ssp.controller.person.AppointmentViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	appEventsController: 'appEventsController',
    	appointment: 'currentAppointment',
    	formUtils: 'formRendererUtils',
    	person: 'currentPerson',
    	studentTypesStore: 'studentTypesStore'
    },
    control: {
    	appointmentDateField: '#appointmentDateField',
    	startTimeField: '#startTimeField',
    	endTimeField: '#endTimeField'
    },
    
	init: function() {
		var me=this;
		
    	me.appEventsController.assignEvent({eventName: 'studentTypeChange', callBackFunc: this.onStudentTypeChange, scope: this});
    	
		me.getView().loadRecord( me.appointment );

		me.assignAppointmentRequiredFields();
		
		return this.callParent(arguments);
    },
    
    destroy: function(){
    	this.appEventsController.removeEvent({eventName: 'studentTypeChange', callBackFunc: this.onStudentTypeChange, scope: this});    	
    	
    	return this.callParent( arguments );
    },
    
    onStudentTypeChange: function(){
    	this.assignAppointmentRequiredFields();
    },
    
    assignAppointmentRequiredFields: function(){
    	// TODO: Decouple this interaction from
    	// the Coach.js screen
    	var me=this;
    	var studentTypeCombo = Ext.ComponentQuery.query('#studentTypeCombo')[0];
    	var newValue = studentTypeCombo.getValue();
    	var studentType, requireAppointment, appendToLabelValue;
    	var appointmentField = me.getAppointmentDateField();
    	var startTimeField = me.getStartTimeField();
    	var endTimeField = me.getEndTimeField();
    	studentType = me.studentTypesStore.getById(newValue);
    	if (studentType != null)
    	{
    		requireAppointment = studentType.get('requireInitialAppointment');
			appendToLabelValue = "";
			// enable requirement of appointment
    		if (requireAppointment==true)
    		{
    			appendToLabelValue = Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY;
    		}

    		appointmentField.setFieldLabel( appointmentField.fieldLabel.replace(Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY,"","gi")+appendToLabelValue );
    		appointmentField.allowBlank=!requireAppointment;
    		appointmentField.validate();
    		
    		startTimeField.setFieldLabel( startTimeField.fieldLabel.replace(Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY,"","gi")+appendToLabelValue );
    		startTimeField.allowBlank=!requireAppointment;
    		startTimeField.validate();
    		
    		endTimeField.setFieldLabel( endTimeField.fieldLabel.replace(Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY,"","gi")+appendToLabelValue );
    		endTimeField.allowBlank=!requireAppointment;
    		endTimeField.validate();
    	}
    }
});