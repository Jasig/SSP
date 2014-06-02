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
Ext.define('Ssp.controller.tool.studentintake.DemographicsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	citizenshipsStore: 'citizenshipsStore',
    	model: 'currentStudentIntake',
        configStore: 'configStore'
    },
    config: {
    	displayEmploymentShift: 1
    },
    control: {
		primaryCaregiverCheckOn: '#primaryCaregiverCheckOn',
		primaryCaregiverCheckOff: '#primaryCaregiverCheckOff',		
		
		'childcareNeeded': {
			change: 'onChildcareNeededChange'
		},
		
		childCareNeededCheckOn: '#childCareNeededCheckOn',
		childCareNeededCheckOff: '#childCareNeededCheckOff',
		
		'isEmployed': {
			change: 'onIsEmployedChange'
		},
		
		employedCheckOn: '#employedCheckOn',
		employedCheckOff: '#employedCheckOff',		

		'childcareArrangement': {
			hide: 'onFieldHidden'
		},
		
		'placeOfEmployment': {
			hide: 'onFieldHidden'
		},
		
		'shift': {
			hide: 'onFieldHidden'
		},
		
		'wage': {
			hide: 'onFieldHidden'
		},
		
		'totalHoursWorkedPerWeek': {
			hide: 'onFieldHidden'
		}
	},

	init: function() {
		var me=this;
		//added below 5 lines to take care of disabling entry if syncStudentPersonalDataWithExternalData is true
		var disabled = me.configStore.getConfigByName('syncStudentPersonalDataWithExternalData');
		var studentIntakeDemographicsForm = Ext.getCmp('StudentIntakeDemographics');
		studentIntakeDemographicsForm.getForm().findField("gender").setDisabled(disabled);
		studentIntakeDemographicsForm.getForm().findField("maritalStatusId").setDisabled(disabled);
		studentIntakeDemographicsForm.getForm().findField("ethnicityId").setDisabled(disabled);
		studentIntakeDemographicsForm.getForm().findField("raceId").setDisabled(disabled);
		
		var personDemographics = me.model.get('personDemographics');
		var citizenship = Ext.ComponentQuery.query('#citizenship')[0];
		var childcareNeeded = Ext.ComponentQuery.query('#childcareNeeded')[0];
		var isEmployed = Ext.ComponentQuery.query('#isEmployed')[0];
		var primaryCaregiver = me.model.get('personDemographics').get('primaryCaregiver');
		var childCareNeeded = me.model.get('personDemographics').get('childCareNeeded');
		var employed = me.model.get('personDemographics').get('employed');
		
		// Assign radio button values
		// Temp solution to assign a value to 
		// the No radio button 
		if ( personDemographics != null && personDemographics != undefined )
		{
			me.getPrimaryCaregiverCheckOn().setValue(primaryCaregiver);
			me.getPrimaryCaregiverCheckOff().setValue(!primaryCaregiver);

			me.getChildCareNeededCheckOn().setValue( childCareNeeded );
			me.getChildCareNeededCheckOff().setValue( !childCareNeeded );				

			me.getEmployedCheckOn().setValue( employed );
			me.getEmployedCheckOff().setValue( !employed );
		}
		
		
        me.showHideChildcareArrangement( childcareNeeded.getValue() );
        me.showHideEmploymentFields( isEmployed.getValue() );
        
		return me.callParent(arguments);
    },
    

    onChildcareNeededChange: function(radiogroup, newValue, oldValue, eOpts) {
    	this.showHideChildcareArrangement( newValue );
    },
    
    showHideChildcareArrangement: function( value ){
    	var field = Ext.ComponentQuery.query('#childcareArrangement')[0];
    	if(value.childCareNeeded=="true")
    	{
    		field.show();
    	}else{
    		field.hide();
    	}
    },
 
    onIsEmployedChange: function(radiogroup, newValue, oldValue, eOpts) {
    	this.showHideEmploymentFields( newValue );
    },    
    
    showHideEmploymentFields: function( value ){
    	var placeOfEmployment = Ext.ComponentQuery.query('#placeOfEmployment')[0];
    	var shift = Ext.ComponentQuery.query('#shift')[0];
    	var wage = Ext.ComponentQuery.query('#wage')[0];
    	var totalHoursWorkedPerWeek = Ext.ComponentQuery.query('#totalHoursWorkedPerWeek')[0];
    	if(value.employed=="true")
    	{
    		placeOfEmployment.show();
    		shift.show();
    		wage.show();
    		totalHoursWorkedPerWeek.show();
    	}else{
    		placeOfEmployment.hide();
    		shift.hide();
    		wage.hide();
    		totalHoursWorkedPerWeek.hide();
    	}
    },    

    onFieldHidden: function( comp, eOpts){
    	comp.setValue("");
    }
    
});