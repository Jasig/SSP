/*
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
Ext.define('Ssp.controller.tool.profile.ProfilePersonViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        appEventsController: 'appEventsController',
        person: 'currentPerson',
        personLite: 'personLite',
        personService: 'personService',
        transcriptService: 'transcriptService',
        profileReferralSourcesStore: 'profileReferralSourcesStore',
        profileServiceReasonsStore: 'profileServiceReasonsStore',
		profileSpecialServiceGroupsStore: 'profileSpecialServiceGroupsStore',
		formUtils: 'formRendererUtils',
    //	textStore:'sspTextStore',
		mapPlanService: 'mapPlanService',
		currentMapPlan: 'currentMapPlan',
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

		me.appEventsController.assignEvent({eventName: 'afterPersonProgramStatusChange', callBackFunc: me.onAfterPersonProgramStatusChange, scope: me});

        return me.callParent(arguments);
    },
    
    onAfterLayout:function(){
		var me = this;
        var id = me.personLite.get('id');

        if (id != "") {
            // display loader
            me.getView().setLoading(true);

            me.mapPlanService.getCurrent(id, {
                success: me.getMapPlanServiceSuccess,
                failure: me.getMapPlanServiceFailure,
                scope: me
            });

            if (me.person.get('id') !== me.personLite.get('id')) {
                me.personService.get(id, {
                    success: me.getPersonSuccess,
                    failure: me.getPersonFailure,
                    scope: me
                });
            } else {
                me.getPersonSuccess(null, null);
            }

            me.transcriptService.getSummary(id, {
                success: me.getTranscriptSuccess,
                failure: me.getTranscriptFailure,
                scope: me
            });
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

	handleNull: function(value, defaultValue){
		if(defaultValue == null || defaultValue == undefined)
			defaultValue = "";
		if(value == null || value == undefined || value == 'null')
			return defaultValue;
		return value;
	},
    
    getMapPlanServiceSuccess: function(mapTOResponse, scope){
        var me = scope;
        var mapNameField = me.getMapNameField();

        if (mapTOResponse && mapTOResponse.responseText && Ext.String.trim(mapTOResponse.toString()).length) {
            me.currentMapPlan.loadFromServer(Ext.decode(mapTOResponse.responseText));
			
			// mapNameField.setFieldLabel('');
        	// mapNameField.setValue('<span style="color:#15428B">Plan Name:  </span>' + me.currentMapPlan.get("name"));
        	mapNameField.setValue(me.currentMapPlan.get("name"));
			
            me.personService.getLite(me.currentMapPlan.get('ownerId'), {
                success: me.getMapPersonSuccess,
                failure: me.getMapPersonFailure,
                scope: me
            });
        }
    },
    
    getMapPlanServiceFailure: function(){
        // nothing to do
        var me = this;
        me.afterLoad();
    },
    
    getMapPersonSuccess: function(personLiteTOResponse, scope){
        var me = scope;
		var advisorField = me.getAdvisorField();

        if (!personLiteTOResponse || !advisorField) {
            me.getView().setLoading(false);  //is an invalid state, but removes an infinite loading spinner
            return;
        } else {
            // advisorField.setFieldLabel('');
        	// advisorField.setValue('<span style="color:#15428B">Plan Name:  </span>' + person.fullName());
        	advisorField.setValue(personLiteTOResponse.fullName);
        }
    },
    
    getMapPersonFailure: function(){
    	// nothing to do
    	var me = this;
    	me.afterLoad();
    },

    getPersonSuccess: function(personTOResponse, scope) {
        var me = scope;

        if ( personTOResponse || (personTOResponse == null && scope == null) ) {
            if (personTOResponse !== null && personTOResponse.id !== '') {
                me.person.populateFromGenericObject(personTOResponse);
            } else if (scope == null) {
                me = this;
            } else {
                this.getPersonFailure();
            }

            var photoUrlField = me.getPhotoUrlField();
            var primaryEmailAddressField = me.getPrimaryEmailAddressField();
            var birthDateField = me.getBirthDateField();
            var studentTypeField = me.getStudentTypeField();
            var programStatusField = me.getProgramStatusField();
            var programStatusReasonField = me.getProgramStatusReasonField();

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

            if (me.person.getProgramStatusChangeReasonName()) {
                me.updateProgramStatusReasonField(me.person.getProgramStatusChangeReasonName());
            }

            // load referral sources
            if (me.person.get('referralSources') != null) {
                me.profileReferralSourcesStore.loadData(me.person.get('referralSources'));
            }

            // load service reasonssd
            if (me.person.get('serviceReasons') != null) {
                me.profileServiceReasonsStore.loadData(me.person.get('serviceReasons'));
            }

            // load special service groups
            if (me.person.get('specialServiceGroups') != null) {
                me.profileSpecialServiceGroupsStore.loadData(me.person.get('specialServiceGroups'));
            }
            me.afterLoad();
        } else {
            this.getPersonFailure();
        }
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
        var me = this;
        me.afterLoad();
    },

    getTranscriptSuccess: function(transcriptTOResponse, scope) {
        var me = scope;
        var transcript = new Ssp.model.Transcript(transcriptTOResponse);
        var programs = transcript.get('programs');
        
		if (programs) {
            var programNames = [];
            // var intendedProgramsAtAdmit = [];
            Ext.Array.each(programs, function(program){
                if (program.programName && program.programName.length > 0) {
                     programNames.push(program.programName);
                }
                // if (program.intendedProgramAtAdmit && program.intendedProgramAtAdmit.length > 0) {
                //     intendedProgramsAtAdmit.push(program.intendedProgramAtAdmit);
                // }
            });
            // me.getAcademicProgramsField().setFieldLabel('');
            // me.getAcademicProgramsField().setValue('<span style="color:#15428B">Academic Program:  </span>' + programNames.join(', '));
            me.getAcademicProgramsField().setValue(programNames.join(', '));
        }
    },
    
    

    getTranscriptFailure: function() {
        // nothing to do
        var me = this;
        me.afterLoad();
    },
	

    afterLoad: function() {
        var me = this;
        me.getView().setLoading(false);
    },

	destroy: function() {
        var me=this;
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
