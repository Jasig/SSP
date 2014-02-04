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
    	studentTypesStore: 'studentTypesAllUnpagedStore',
		person: 'currentPerson'
    },
    control: {
    	appointmentDateField: '#appointmentDateField',
    	startTimeField: '#startTimeField',
    	endTimeField: '#endTimeField',
    	
    	studentTypeCombo: {
    		selector: '#studentTypeCombo',
    		listeners: {
        		select: 'onStudentTypeComboSelect'
    		}     		
    	}
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
		
		me.studentTypesStore.clearFilter(true);	 
		
		if ( me.person.data.studentType ) {
			me.formUtils.applyAssociativeStoreFilter(me.studentTypesStore, me.person.data.studentType.id);	
		}
				
		me.getView().getForm().reset();
		me.getStudentTypeCombo().setValue( me.person.getStudentTypeId() );
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
	
	onStudentTypeComboSelect: function(comp, records, eOpts){
		var me=this;
		var studentType, requireInitialAppointment;
		if(records.length>0){
			me.appEventsController.getApplication().fireEvent('studentTypeChange');
		}
	},
	
	onStudentTypeComboChange: function(comp, newValue, oldValue, eOpts){
		var me=this;
		var studentType, requireInitialAppointment;
		//Assumes studentTypesStore is loaded in higher controller
		studentType = me.studentTypesStore.getById(newValue); 
		
		if(studentType != null){
			me.appEventsController.getApplication().fireEvent('studentTypeChange');
		}
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
		//Assumes studentTypesStore is loaded in higher controller
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