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
				    },{
			            xtype: 'checkboxfield',
			            fieldLabel: 'Send Student Intake Request',
				        name: 'studentIntakeRequested',
			            itemId: 'studentIntakeRequestedField',
			            inputValue: true
				    },{
			            xtype: 'textfield',
			            fieldLabel: 'Also Send Student Intake Request To Email',
				        name: 'intakeEmail',
			            itemId: 'intakeEmailField',
			            hidden: true
				    }
				    
				    ]
			    }]
			});
		
		return me.callParent(arguments);
	}
});