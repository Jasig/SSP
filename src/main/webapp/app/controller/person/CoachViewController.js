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
Ext.define('Ssp.controller.person.CoachViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	appEventsController: 'appEventsController',
		apiProperties: 'apiProperties',
    	coachesStore: 'coachesStore',
    	person: 'currentPerson', 	
    	sspConfig: 'sspConfig',
        studentTypesStore: 'studentTypesStore'
    },
    config: {
    	inited: false
    },
    control: {
    	departmentField: '#departmentField',
    	phoneField: '#phoneField',
    	officeField: '#officeField',
    	emailAddressField: '#emailAddressField',

    	coachCombo: {
    		selector: '#coachCombo',
    		listeners: {
        		change: 'onCoachComboChange',
        		select: 'onCoachComboSelect'
    		} 
    	},
    	
    	studentTypeCombo: {
    		selector: '#studentTypeCombo',
    		listeners: {
        		select: 'onStudentTypeComboSelect'
    		}     		
    	}
    },
    
	init: function() {
		var me=this;

		if ( me.person.get('id') != "")
		{			
			me.getCoachCombo().setDisabled( me.sspConfig.get('coachSetFromExternalData') );
			var url = me.apiProperties.createUrl('reference/config?name=studentTypeSetFromExternalData');
			
			Ext.Ajax.request({
				url: url,	
				method: 'GET',
				headers: { 'Content-Type': 'application/json' },					
				success: function(response, view) {
						var r = Ext.decode(response.responseText);					
						if (r.value.trim().toLowerCase() === 'true'){					
							me.getStudentTypeCombo().setDisabled(true); 							
						} else {
							me.getStudentTypeCombo().setDisabled(false);
						}
				},
				failure: function () {
					 me.getStudentTypeCombo().setDisabled(false); 
					this.apiProperties.handleError
				}
			}, this);				
		}
		
		me.studentTypesStore.load();
		me.coachesStore.load(function(records, operation, success) {
	          if(!success)
	          {
	        	  Ext.Msg.alert('Error','Unable to load Coaches. Please see your system administrator for assistance.');
	          }
		 });
		
		me.initForm();
		
		return this.callParent(arguments);
    },

	initForm: function(){
		var me=this;
		me.getView().getForm().reset();
		me.getCoachCombo().setValue( me.person.getCoachId() );
		me.getStudentTypeCombo().setValue( me.person.getStudentTypeId() );
		me.inited=true;
	},    
    
	onCoachComboSelect: function(comp, records, eOpts){
		var me=this;
		var coach;
		if(records.length>0){
			coach=records[0];
			me.displayCoachDepartment( coach );
		}
	},
	
	onCoachComboChange: function(comp, newValue, oldValue, eOpts){
		var me=this;
		var coach = me.coachesStore.getById(newValue);
		if(coach != null){
			me.displayCoachDepartment( coach );
		}
	},
	
	displayCoachDepartment: function( coach ){
		var me=this;
		me.getDepartmentField().setValue( coach.get('departmentName') );
		me.getPhoneField().setValue( coach.get('workPhone') );
		me.getEmailAddressField().setValue( coach.get('primaryEmailAddress') );
		me.getOfficeField().setValue( coach.get('officeLocation') );
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
		studentType = me.studentTypesStore.getById(newValue);
		if(studentType != null){
			me.appEventsController.getApplication().fireEvent('studentTypeChange');
		}
	}
});