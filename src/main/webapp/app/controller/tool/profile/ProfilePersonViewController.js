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
Ext.define('Ssp.controller.tool.profile.ProfilePersonViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        apiProperties: 'apiProperties',
        appEventsController: 'appEventsController',
        person: 'currentPerson',
        personLite: 'personLite',
        personService: 'personService',
        transcriptService: 'transcriptService',
		personProgramStatusService: 'personProgramStatusService',
        profileReferralSourcesStore: 'profileReferralSourcesStore',
        profileServiceReasonsStore: 'profileServiceReasonsStore',
		profileSpecialServiceGroupsStore: 'profileSpecialServiceGroupsStore',
		programStatusChangeReasonsStore: 'programStatusChangeReasonsAllUnpagedStore',
        configStore: 'configStore',
		formUtils: 'formRendererUtils',
    	textStore:'sspTextStore',
        sapStatusesStore: 'sapStatusesAllUnpagedStore',
        financialAidFilesStore: 'financialAidFilesAllUnpagedStore',
        personRegistrationStatusByTermStore: 'personRegistrationStatusByTermStore',
		mapPlanService: 'mapPlanService',
		currentMapPlan: 'currentMapPlan',
		termsStore: 'termsStore'
    },
    
    control: {
        photoUrlField: '#studentPhoto',
        
        primaryEmailAddressField: '#primaryEmailAddressField',
        birthDateField: '#birthDate',
        studentTypeField: '#studentType',
        programStatusField: '#programStatus',
        programStatusReasonField: '#programStatusReason',
        academicProgramsField: '#academicPrograms',
        mapNameField: '#mapName',
        advisorField: '#advisor',
        
		'serviceReasonEdit': {
            click: 'onServiceReasonEditButtonClick'
        },
        
        'serviceGroupEdit': {
            click: 'onServiceGroupEditButtonClick'
        },
        
        'referralSourceEdit': {
            click: 'onReferralSourceEditButtonClick'
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
        
        me.resetForm();

        if(me.sapStatusesStore.getTotalCount() <= 0){
			me.sapStatusesStore.load();
        }
        if(me.financialAidFilesStore.getTotalCount() <= 0){
			me.financialAidFilesStore.load();
        }
        
        if (!me.programStatusChangeReasonsStore.getTotalCount()) {
			me.programStatusChangeReasonsStore.load();
		}

		me.appEventsController.assignEvent({eventName: 'afterPersonProgramStatusChange', callBackFunc: me.onAfterPersonProgramStatusChange, scope: me});

        return me.callParent(arguments);
    },
    
    onAfterLayout:function(){
		var me = this;
        var id = me.personLite.get('id');
        
        if (id != "") {
            // display loader
            me.getView().setLoading(true);
            
            if (me.termsStore.getTotalCount() <= 0) {
                me.termsStore.addListener('load', me.termsLoaded, me);
                me.termsStore.load();
            }
            else {
                me.fireOnTermsLoad();
            }
        }
    },
    
    resetForm: function() {
        var me = this;
        me.getView().getForm().reset();
        // load and render person data
       	me.profileReferralSourcesStore.removeAll();
       	me.profileServiceReasonsStore.removeAll();
       	me.profileSpecialServiceGroupsStore.removeAll();
    },

    newServiceSuccessHandler: function(name, callback, serviceResponses) {
        var me = this;
        return me.newServiceHandler(name, callback, serviceResponses, function(name, serviceResponses, response) {
            serviceResponses.successes[name] = response;
        });
    },

    newServiceFailureHandler: function(name, callback, serviceResponses) {
        var me = this;
        return me.newServiceHandler(name, callback, serviceResponses, function(name, serviceResponses, response) {
            serviceResponses.failures[name] = response;
        });
    },

    newServiceHandler: function(name, callback, serviceResponses, serviceResponsesCallback) {
        return function(r, scope) {
            var me = scope;
            serviceResponses.responseCnt++;
            if ( serviceResponsesCallback ) {
                serviceResponsesCallback.apply(me, [name, serviceResponses, r]);
			}
            if ( callback ) {
                callback.apply(me, [ serviceResponses ]);
            }
            me.afterServiceHandler(serviceResponses);
        };
    },
    
    termsLoaded: function(){
        var me = this;
        
        me.termsStore.removeListener('load', me.termsLoaded, me);
        me.fireOnTermsLoad();
    },
    
    fireOnTermsLoad: function(){
        var me = this;
        var id = me.personLite.get('id');
        
        if (id != "") {
            var serviceResponses = {
                failures: {},
                successes: {},
                responseCnt: 0,
                expectedResponseCnt: 4
            }
            
            me.mapPlanService.getCurrent(id, {
                success: me.newServiceSuccessHandler('map', me.getMapPlanServiceSuccess, serviceResponses),
                failure: me.newServiceFailureHandler('map', me.getMapPlanServiceFailure, serviceResponses),
                scope: me
            });
            me.personService.get(id, {
                success: me.newServiceSuccessHandler('person', me.getPersonSuccess, serviceResponses),
                failure: me.newServiceFailureHandler('person', me.getPersonFailure, serviceResponses),
                scope: me
            });
            me.transcriptService.getSummary(id, {
                success: me.newServiceSuccessHandler('transcript', me.getTranscriptSuccess, serviceResponses),
                failure: me.newServiceFailureHandler('transcript', me.getTranscriptFailure, serviceResponses),
                scope: me
            });
            me.personProgramStatusService.getCurrentProgramStatus(id, {
                success: me.newServiceSuccessHandler('programstatus', me.getCurrentProgramStatusSuccess, serviceResponses),
                failure: me.newServiceFailureHandler('programstatus', me.getCurrentProgramStatusFailure, serviceResponses),
                scope: me
            });
        }
    },
    
	handleNull: function(value, defaultValue){
		if(defaultValue == null || defaultValue == undefined)
			defaultValue = "";
		if(value == null || value == undefined || value == 'null')
			return defaultValue;
		return value;
	},
    
    getMapPlanServiceSuccess: function(serviceResponses){
        var me = this;
        var mapResponse = serviceResponses.successes.map;
        var mapNameField = me.getMapNameField();
		
        if (mapResponse && mapResponse.responseText && Ext.String.trim(mapResponse.toString()).length) {
            me.currentMapPlan.loadFromServer(Ext.decode(mapResponse.responseText));
			
			// mapNameField.setFieldLabel('');
        	// mapNameField.setValue('<span style="color:#15428B">Plan Name:  </span>' + me.currentMapPlan.get("name"));
        	mapNameField.setValue(me.currentMapPlan.get("name"));
			
            var serviceResponses = {
                failures: {},
                successes: {},
                responseCnt: 0,
                expectedResponseCnt: 1
            };
            me.personService.get(me.currentMapPlan.get('ownerId'), {
                success: me.newServiceSuccessHandler('person', me.getMapPersonSuccess, serviceResponses),
                failure: me.newServiceFailureHandler('person', me.getMapPersonFailure, serviceResponses),
                scope: me
            });
        }
    },
    
    getMapPlanServiceFailure: function(){
        // nothing to do
    },
    
    getMapPersonSuccess: function(serviceResponses){
        var me = this;
        var person = serviceResponses.successes.person;
		var advisorField = me.getAdvisorField();
		
        if (!person) {
            return;
        }
        else {
            var personResponse = serviceResponses.successes.person;
            var advisor = new Ssp.model.Person();
            advisor.populateFromGenericObject(personResponse);
            // advisorField.setFieldLabel('');
        	// advisorField.setValue('<span style="color:#15428B">Plan Name:  </span>' + advisor.getFullName());
        	advisorField.setValue(advisor.getFullName());
        }
    },
    
    getMapPersonFailure: function(){
    	// nothing to do
    },

    getPersonSuccess: function(serviceResponses) {
        var me = this;
        var personResponse = serviceResponses.successes.person;
        me.person.populateFromGenericObject(personResponse);
        
        var photoUrlField = me.getPhotoUrlField();
        var primaryEmailAddressField = me.getPrimaryEmailAddressField();
        var birthDateField = me.getBirthDateField();
        var studentTypeField = me.getStudentTypeField();
        var programStatusField = me.getProgramStatusField();
        
        // load general student record
        me.getView().loadRecord(me.person);
        
        photoUrlField.setSrc(me.person.getPhotoUrl());
        
        // primaryEmailAddressField.setFieldLabel('');
        primaryEmailAddressField.setValue('<a href="mailto:' + me.handleNull(me.person.get('primaryEmailAddress')) + '" target="_top">' + me.handleNull(me.person.get('primaryEmailAddress')) + '</a>');
        
        // birthDateField.setFieldLabel('');
        // birthDateField.setValue('<span style="color:#15428B">' + me.textStore.getValueByCode('ssp.label.dob') + ':  </span>' + me.person.getFormattedBirthDate());
        birthDateField.setValue(me.person.getFormattedBirthDate());
        
        // studentTypeField.setFieldLabel('');
        // studentTypeField.setValue('<span style="color:#15428B">Type:  </span>' + me.handleNull(me.person.getStudentTypeName()));
        studentTypeField.setValue(me.handleNull(me.person.getStudentTypeName()));
        
        // programStatusField.setFieldLabel('');
        // programStatusField.setValue('<span style="color:#15428B">Status:  </span>' + me.handleNull(me.person.getProgramStatusName()));
        programStatusField.setValue(me.handleNull(me.person.getProgramStatusName()));
    },

	onAfterPersonProgramStatusChange: function(event) {
		var me = this;
		me.updateProgramStatusField(event.programStatusName);
		me.updateProgramStatusReasonField(event.programStatusChangeReasonName);
		return true;
	},

	updateProgramStatusField: function(programStatusName) {
		var me = this;
		var programStatusField = me.getProgramStatusField();
		programStatusField.setFieldLabel('');
		programStatusField.setValue('<span style="color:#15428B">SSP Status:  </span>' + me.handleNull(programStatusName));
	},

	updateProgramStatusReasonField: function(programStatusChangeReasonName) {
		var me = this;
		var programStatusReasonField = me.getProgramStatusReasonField();
		if (programStatusChangeReasonName) {
			programStatusReasonField.show();
			programStatusReasonField.setFieldLabel('');
			programStatusReasonField.setValue('<span style="color:#15428B">Reason:  </span>' + programStatusChangeReasonName);
		} else {
			programStatusReasonField.hide();
		}
	},

    getPersonFailure: function() {
        // nothing to do
    },

    getTranscriptSuccess: function(serviceResponses) {
        var me = this;
        var transcriptResponse = serviceResponses.successes.transcript;
        var transcript = new Ssp.model.Transcript(transcriptResponse);
        var programs = transcript.get('programs');
        
		if (programs) {
            var programNames = [];
            // var intendedProgramsAtAdmit = [];
            Ext.Array.each(programs, function(program){
                if (program.programName && program.programName.length > 0) 
                    programNames.push(program.programName);
                // if (program.intendedProgramAtAdmit && program.intendedProgramAtAdmit.length > 0) 
                //     intendedProgramsAtAdmit.push(program.intendedProgramAtAdmit);
            });
            // me.getAcademicProgramsField().setFieldLabel('');
            // me.getAcademicProgramsField().setValue('<span style="color:#15428B">Academic Program:  </span>' + programNames.join(', '));
            me.getAcademicProgramsField().setValue(programNames.join(', '));
        }
    },
    
    

    getTranscriptFailure: function() {
        // nothing to do
    },
	
	getCurrentProgramStatusSuccess: function(serviceResponses) {
		var me = this;
        var programStatusReason;
        var studentStatus;
        var programStatusReasonField = me.getProgramStatusReasonField();
        var programStatusResponse = serviceResponses.successes.programstatus;
		
        studentStatus = programStatusResponse['programStatusChangeReasonId'];
        
        if (studentStatus) {
            programStatusReason = me.programStatusChangeReasonsStore.findRecord('id', studentStatus, 0, false, false, true);
            
            if (programStatusReason) {
                programStatusReasonField.show();
                // programStatusReasonField.setFieldLabel('');
                // programStatusReasonField.setValue('<span style="color:#15428B">Reason:  </span>' + programStatusReason.get('name'));
                programStatusReasonField.setValue(programStatusReason.get('name'));
            }
        }
        else {
            programStatusReasonField.hide();
        }
	},
	
	getCurrentProgramStatusFailure: function() {
		
        // nothing to do
    },

    afterServiceHandler: function(serviceResponses) {
        var me = this;
        if ( serviceResponses.responseCnt >= serviceResponses.expectedResponseCnt ) {
            me.getView().setLoading(false);
        }
    },

	destroy: function() {
        var me=this;
	    me.personRegistrationStatusByTermStore.removeListener("load", me.onRegStoreLoaded, me);
		me.appEventsController.removeEvent({eventName: 'afterPersonProgramStatusChange', callBackFunc: me.onAfterPersonProgramStatusChange, scope: me});
		var view = Ext.ComponentQuery.query("#profileDetails");
    	
        return me.callParent( arguments );
    },

	
	onServiceReasonEditButtonClick: function(button){
        var me=this;
        
        var comp = this.formUtils.loadDisplay('mainview', 'caseloadassignment', true, {flex:1}); 
		me.appEventsController.getApplication().fireEvent('goToDifferentTabinCaseload',{panelTitle:'Service Reasons'});
        
    },
    
    onServiceGroupEditButtonClick: function(button){
        var me=this;
        
        var comp = this.formUtils.loadDisplay('mainview', 'caseloadassignment', true, {flex:1}); 
		me.appEventsController.getApplication().fireEvent('goToDifferentTabinCaseload',{panelTitle:'Service Groups'});
        
    },
    
    onReferralSourceEditButtonClick: function(button){
        var me=this;
        
        var comp = this.formUtils.loadDisplay('mainview', 'caseloadassignment', true, {flex:1}); 
		me.appEventsController.getApplication().fireEvent('goToDifferentTabinCaseload',{panelTitle:'Referral Sources'});
        
    }
});
