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
Ext.define('Ssp.controller.tool.profile.ProfilePersonDetailsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        apiProperties: 'apiProperties',
        appEventsController: 'appEventsController',
        person: 'currentPerson',
        personLite: 'personLite',
        personService: 'personService',
        transcriptService: 'transcriptService',
        profileReferralSourcesStore: 'profileReferralSourcesStore',
        profileServiceReasonsStore: 'profileServiceReasonsStore',
        profileSpecialServiceGroupsStore: 'profileSpecialServiceGroupsStore',
        sspConfig: 'sspConfig',
		formUtils: 'formRendererUtils'
    },
    
    control: {
        nameField: '#studentName',
        photoUrlField: '#studentPhoto',

        
        studentIdField: '#studentId',
        birthDateField: '#birthDate',
        studentTypeField: '#studentType',
        programStatusField: '#programStatus',
        f1StatusField: '#f1Status',
        residencyCountyField: '#residencyCounty',
        maritalStatusField: '#maritalStatus',
        genderField: '#gender',
        ethnicityField: '#ethnicity',
    	

        gpaField: '#cumGPA',
        
        academicStandingField: '#academicStanding',
        currentRestrictionsField: '#currentRestrictions',
        creditCompletionRateField: '#creditCompletionRate',
        currentYearFinancialAidAwardField: '#currentYearFinancialAidAward',
        academicProgramsField: '#academicPrograms',
        intendedProgramAtAdmitField: '#intendedProgramAtAdmit',
        sapStatusField: '#sapStatus',
        fafsaDateField: '#fafsaDate',
       
        financialAidGpaField: '#financialAidGpa',
        creditHoursEarnedField: '#creditHoursEarned',
        creditHoursAttemptedField: '#creditHoursAttempted',
        creditCompletionRateField: '#creditCompletionRate',
        remainingLoanAmountField: '#remainingLoanAmount',
        financialAidRemainingField: '#financialAidRemaining',
        originalLoanAmountField: '#originalLoanAmount',
    
    },
    init: function(){
        var me = this;
        var id = me.personLite.get('id');
        me.resetForm();
        if (id != "") {
            // display loader
            me.getView().setLoading(true);

            var serviceResponses = {
                failures: {},
                successes: {},
                responseCnt: 0,
                expectedResponseCnt: 2
            }

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
        }
        
        return me.callParent(arguments);
    },

    resetForm: function() {
        var me = this;
        me.getView().getForm().reset();
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

    getPersonSuccess: function(serviceResponses) {
        var me = this;
        var personResponse = serviceResponses.successes.person;
        me.person.populateFromGenericObject(personResponse);

        // load and render person data
        me.profileSpecialServiceGroupsStore.removeAll();
        me.profileReferralSourcesStore.removeAll();
        me.profileServiceReasonsStore.removeAll();

        var nameField = me.getNameField();
        var photoUrlField = me.getPhotoUrlField();
        var birthDateField = me.getBirthDateField();
        var studentTypeField = me.getStudentTypeField();
        var programStatusField = me.getProgramStatusField();


        var fullName = me.person.getFullName();
        var coachName = me.person.getCoachFullName();

        // load general student record
        me.getView().loadRecord(me.person);

        // load additional values
        nameField.setValue(fullName);
        birthDateField.setValue(me.person.getFormattedBirthDate());
        studentTypeField.setValue(me.person.getStudentTypeName());
        photoUrlField.setSrc(me.person.getPhotoUrl());
        programStatusField.setValue(me.person.getProgramStatusName());
       

        var studentRecordComp = Ext.ComponentQuery.query('.studentrecord')[0];
        var studentCoachButton = Ext.ComponentQuery.query('#emailCoachButton')[0];
        studentRecordComp.setTitle('Student: ' + fullName + '          ' + '  -   ID#: ' + me.person.get('schoolId'));
        studentCoachButton.setText('<u>Coach: ' + coachName + '</u>');
		
        me.appEventsController.assignEvent({
            eventName: 'emailCoach',
            callBackFunc: me.onEmailCoach,
            scope: me
        });
    },

    getPersonFailure: function() {
        // nothing to do
    },
    


    getTranscriptSuccess: function(serviceResponses) {
        var me = this;
        var transcriptResponse = serviceResponses.successes.transcript;

        var transcript = new Ssp.model.Transcript(transcriptResponse);
        var gpa = transcript.get('gpa');
        if ( gpa ) {
			var gpaFormatted = Ext.util.Format.number(gpa.gradePointAverage, '0.00');
            me.getGpaField().setValue(gpaFormatted);
            me.getAcademicStandingField().setValue(gpa.academicStanding);
            me.getCreditCompletionRateField().setValue(gpa.creditCompletionRate + '%');
            me.getCurrentRestrictionsField().setValue(gpa.currentRestrictions)

            me.getCreditHoursEarnedField().setValue(gpa.creditHoursEarned)
            me.getCreditHoursAttemptedField().setValue(gpa.creditHoursAttempted)
        }
        var programs = transcript.get('programs');
        if ( programs ) {
            var programNames = [];
            var intendedProgramsAtAdmit = [];
            Ext.Array.each(programs, function(program) {
            	if(program.programName && program.programName.length > 0)
            		programNames.push(program.programName);
            	if(program.intendedProgramAtAdmit && program.intendedProgramAtAdmit.length > 0)
            		intendedProgramsAtAdmit.push(program.intendedProgramAtAdmit);
            });
            me.getAcademicProgramsField().setValue(programNames.join(', '));
            me.getIntendedProgramAtAdmitField().setValue(intendedProgramsAtAdmit.join(', '));
        }
        

        var financialAid = transcript.get('financialAid');
        if ( financialAid ) {
            
        	me.getCurrentYearFinancialAidAwardField().setValue(financialAid.currentYearFinancialAidAward);
        	me.getSapStatusField().setValue(financialAid.sapStatus);
        	
        	me.getSapStatusField().setValue(financialAid.sapStatus);
        	me.getFafsaDateField().setValue(Ext.util.Format.date(new Date(financialAid.fafsaDate),'m/d/Y'));
        	me.getRemainingLoanAmountField().setValue(Ext.util.Format.usMoney(financialAid.remainingLoanAmount));
        	me.getFinancialAidRemainingField().setValue(Ext.util.Format.usMoney(financialAid.financialAidRemaining));
        	me.getOriginalLoanAmountField().setValue(Ext.util.Format.usMoney(financialAid.originalLoanAmount));
        	me.getFinancialAidGpaField().setValue(financialAid.financialAidGpa);
        }
    },

    getTranscriptFailure: function() {
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
        return me.callParent( arguments );
    },

    onEmailCoach: function(){
        var me = this;
        if (me.person.getCoachPrimaryEmailAddress()) {
            window.location = 'mailto:' + me.person.getCoachPrimaryEmailAddress();
        }
    },
	
});
