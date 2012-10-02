/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
Ext.define('Ssp.controller.person.AppointmentViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	appEventsController: 'appEventsController',
    	appointment: 'currentAppointment',
    	formUtils: 'formRendererUtils',
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
		
		// require a date beyond today for all new appointments
		if (me.appointment.get('id') == "")
		{
			today = new Date();
			me.getAppointmentDateField().setMinValue( Ext.Date.clearTime( today ) );
		}
				
		me.getView().getForm().reset();
		me.getView().loadRecord( me.appointment );

		me.assignAppointmentRequiredFields();
		
		return me.callParent(arguments);
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