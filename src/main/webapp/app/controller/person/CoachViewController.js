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
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        appEventsController: 'appEventsController',
        apiProperties: 'apiProperties',
        coachesStore: 'coachesStore',
        person: 'currentPerson',
        configStore: 'configStore',
        studentTypesStore: 'studentTypesAllUnpagedStore',
        formRendererUtils: 'formRendererUtils',
        personService: 'personService'
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
    	view: {
    		afterlayout: {
    			fn: 'onAfterLayout',
    			single: true
    		}
    	}
    },
    
    init: function(){
        var me = this;
        me.missingCoach = null;
         return this.callParent(arguments);
    },
    
    onAfterLayout: function(){
		var me = this;
    	 me.coachesStore.load(function(records, operation, success){
             if (!success) {
                 Ext.Msg.alert('Error', 'Unable to load Coaches. Please see your system administrator for assistance.');
             }
             else {
                 // Make best effort at trying to find the
                 // associated coach and inject it into the view. Currently
                 // associated coach might not be in the store already, e.g. if he/she
                 // were "demoted" in the directory system. So, we query to get all
                 // coaches.
                 var currentCoachId = me.getView().instantCaseloadAssignment ? me.getView().coachIdValue : me.person.getCoachId();
                 
                 //Don't look up or put in combo if ID is not valid
                 if (currentCoachId) {
                     if (!(me.coachesStore.getById(currentCoachId))) {
                         me.lookupMissingCoach(currentCoachId, me.afterMissingCoachLookup, me);
                     }
                     else {
                         me.getCoachCombo().setValue(currentCoachId);
                     }
                 }
             }
         });
         
         var getExtDataCoachValue = 'true';
 		var getExtUnsetDataCoachValue = 'true';
 		
         
         me.configStore.each(function(rec){
         
             var s = rec.get('value');
             if (rec.get('name') == 'coachSetFromExternalData') {
 				
                 if (s.toUpperCase().indexOf("TRUE") == -1) 
                     getExtDataCoachValue = 'false'
                 
             }
             if (rec.get('name') == 'coachUnsetFromExternalData') {
 				
                 if (s.toUpperCase().indexOf("TRUE") == -1) 
                     getExtUnsetDataCoachValue = 'false'
             }
            
             
         });
       
 		
 		
         if (getExtDataCoachValue == 'false' && getExtUnsetDataCoachValue == 'false') {
             me.getCoachCombo().setFieldLabel(me.configStore.getConfigByName('coachFieldLabel') +  Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY);
         }
         
         if(me.getView().instantCaseloadAssignment){
         	me.hideForInstantCaseload();
         }
 		
         me.initForm();
    },
    
    hideForInstantCaseload: function(){
		var me = this;
    	me.getDepartmentField().hide();
        me.getPhoneField().hide();
        me.getEmailAddressField().hide();
        me.getOfficeField().hide();
    },
    
    initForm: function(){
        var me = this;
        me.getView().getForm().reset();
        
        me.inited = true;
    },
    
    onCoachComboSelect: function(comp, records, eOpts){
        var me = this;
        var coach;
        if (records.length > 0) {
            coach = records[0];
            me.displayCoachDepartment(coach);
        }
    },
    
    onCoachComboChange: function(comp, newValue, oldValue, eOpts){
        var me = this;
        var coach = me.coachesStore.getById(newValue);
        if (coach != null) {
            me.displayCoachDepartment(coach);
        }
    },
    
    displayCoachDepartment: function(coach){
        var me = this;
        me.getDepartmentField().setValue(coach.get('departmentName'));
        me.getPhoneField().setValue(coach.get('workPhone'));
        me.getEmailAddressField().setValue(coach.get('primaryEmailAddress'));
        me.getOfficeField().setValue(coach.get('officeLocation'));
    },
    
    
    lookupMissingCoach: function(coachId, after, afterScope){
        var me = this;
        me.missingCoach = Ext.create('Ssp.model.Coach', {
            id: coachId,
            firstName: "UNKNOWN",
            lastName: "PERSON"
        });
        me.personService.getLite(coachId, {
            success: function(person){
                if (person) {
                    me.missingCoach = Ext.create('Ssp.model.Coach', {
                        id: person.id,
                        firstName: person.firstName,
                        lastName: person.lastName
                    });
                }
                after.apply(afterScope);
            },
            failure: function(){
                //likely app can just proceed without current coach in store				
                after.apply(afterScope);
            }
        });
    },
    
    afterMissingCoachLookup: function(){
        var me = this;
        me.coachesStore.add(me.missingCoach);
        me.coachesStore.sort('lastName', 'ASC');
        me.getCoachCombo().setValue(me.missingCoach);
    },
    
    maybeClearMissingCoach: function(){
        var me = this;
        if (me.missingCoach) {
            me.coachesStore.remove(me.missingCoach);
            me.coachesStore.sort('lastName', 'ASC');
            me.missingCoach = null;
        }
    }
});
