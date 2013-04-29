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
        primaryEmailAddressField: '#primaryEmailAddress',
        
        gpaField: '#cumGPA',
        transferHrsField: '#transferHrs',
        
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
        balanceOwedField: '#balanceOwed',
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
                expectedResponseCnt: 3
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
            me.transcriptService.getFull(id, {
                success: me.newServiceSuccessHandler('transcriptFull', me.getTranscriptFullSuccess, serviceResponses),
                failure: me.newServiceFailureHandler('transcriptFull', me.getTranscriptFullFailure, serviceResponses),
                scope: me
            });
        }
        
        return me.callParent(arguments);
    },
    
    resetForm: function(){
        var me = this;
        me.getView().getForm().reset();
        
        // Set defined configured label for the studentId field
        var studentIdAlias = me.sspConfig.get('studentIdAlias');
        me.getStudentIdField().setFieldLabel(studentIdAlias);
    },
    
    newServiceSuccessHandler: function(name, callback, serviceResponses){
        var me = this;
        return me.newServiceHandler(name, callback, serviceResponses, function(name, serviceResponses, response){
            serviceResponses.successes[name] = response;
        });
    },
    
    newServiceFailureHandler: function(name, callback, serviceResponses){
        var me = this;
        return me.newServiceHandler(name, callback, serviceResponses, function(name, serviceResponses, response){
            serviceResponses.failures[name] = response;
        });
    },
    
    newServiceHandler: function(name, callback, serviceResponses, serviceResponsesCallback){
        return function(r, scope){
            var me = scope;
            serviceResponses.responseCnt++;
            if (serviceResponsesCallback) {
                serviceResponsesCallback.apply(me, [name, serviceResponses, r]);
            }
            if (callback) {
                callback.apply(me, [serviceResponses]);
            }
            me.afterServiceHandler(serviceResponses);
        };
    },
    
    getPersonSuccess: function(serviceResponses){
        var me = this;
        var personResponse = serviceResponses.successes.person;
        me.person.populateFromGenericObject(personResponse);
        
        // load and render person data
        me.profileSpecialServiceGroupsStore.removeAll();
        me.profileReferralSourcesStore.removeAll();
        me.profileServiceReasonsStore.removeAll();
        
        var nameField = me.getNameField();
        var primaryEmailAddressField = me.getPrimaryEmailAddressField();
        var photoUrlField = me.getPhotoUrlField();
        var birthDateField = me.getBirthDateField();
        var studentTypeField = me.getStudentTypeField();
        var programStatusField = me.getProgramStatusField();
        var studentIdField = me.getStudentIdField();
        
        var fullName = me.person.getFullName();
        var coachName = me.person.getCoachFullName();
        
        // load general student record
        me.getView().loadRecord(me.person);
        
        // load additional values
        nameField.setFieldLabel('');
        nameField.setValue('<span style="color:#15428B">Full Name:  </span>' + fullName);
        studentIdField.setFieldLabel('');
        studentIdField.setValue('<span style="color:#15428B">' + me.sspConfig.get('studentIdAlias') + ':  </span>' + me.person.get('schoolId'));
        primaryEmailAddressField.setFieldLabel('');
        primaryEmailAddressField.setValue('<span style="color:#15428B">Email:  </span>' + me.person.get('primaryEmailAddress'));
        birthDateField.setFieldLabel('');
        birthDateField.setValue('<span style="color:#15428B">DOB:  </span>' + me.person.getFormattedBirthDate());
        studentTypeField.setFieldLabel('');
        studentTypeField.setValue('<span style="color:#15428B">Student Type:  </span>' + me.person.getStudentTypeName());
        photoUrlField.setSrc(me.person.getPhotoUrl());
        programStatusField.setFieldLabel('');
        programStatusField.setValue('<span style="color:#15428B">SSP Status:  </span>' + me.person.getProgramStatusName());
        
        
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
    
    getPersonFailure: function(){
        // nothing to do
    },
    
    
    
    getTranscriptSuccess: function(serviceResponses){
        var me = this;
        var transcriptResponse = serviceResponses.successes.transcript;
        
        var transcript = new Ssp.model.Transcript(transcriptResponse);
        var gpa = transcript.get('gpa');
        if (gpa) {
            var gpaFormatted = Ext.util.Format.number(gpa.gradePointAverage, '0.00');
            me.getGpaField().setFieldLabel('');
            me.getGpaField().setValue('<span style="color:#15428B">GPA:  </span>' + gpaFormatted);
            me.getAcademicStandingField().setFieldLabel('');
            me.getAcademicStandingField().setValue('<span style="color:#15428B">Standing:  </span>' + gpa.academicStanding);
            me.getCreditCompletionRateField().setValue(gpa.creditCompletionRate + '%');
            me.getCurrentRestrictionsField().setFieldLabel('');
            me.getCurrentRestrictionsField().setValue('<span style="color:#15428B">Restrictions:  </span>' + gpa.currentRestrictions);
            me.getCreditHoursEarnedField().setFieldLabel('');
            me.getCreditHoursEarnedField().setValue('<span style="color:#15428B">Hrs Earned:  </span>' + gpa.creditHoursEarned);
            me.getCreditHoursAttemptedField().setFieldLabel('');
            me.getCreditHoursAttemptedField().setValue('<span style="color:#15428B">Hrs Attempted:  </span>' + gpa.creditHoursAttempted);
        }
        var programs = transcript.get('programs');
        if (programs) {
            var programNames = [];
            var intendedProgramsAtAdmit = [];
            Ext.Array.each(programs, function(program){
                if (program.programName && program.programName.length > 0) 
                    programNames.push(program.programName);
                if (program.intendedProgramAtAdmit && program.intendedProgramAtAdmit.length > 0) 
                    intendedProgramsAtAdmit.push(program.intendedProgramAtAdmit);
            });
            me.getAcademicProgramsField().setFieldLabel('');
            me.getAcademicProgramsField().setValue('<span style="color:#15428B">Academic Program:  </span>' + programNames.join(', '));
            me.getIntendedProgramAtAdmitField().setValue(intendedProgramsAtAdmit.join(', '));
        }
        
        
        var financialAid = transcript.get('financialAid');
        if (financialAid) {
            me.getCurrentYearFinancialAidAwardField().setFieldLabel('');
        	me.getCurrentYearFinancialAidAwardField().setValue('<span style="color:#15428B">FA Award:  </span>' + financialAid.currentYearFinancialAidAward);
        	me.getSapStatusField().setFieldLabel('');
			me.getSapStatusField().setValue('<span style="color:#15428B">SAP:  </span>' + financialAid.sapStatus);
        	me.getFafsaDateField().setFieldLabel('');
			me.getFafsaDateField().setValue('<span style="color:#15428B">FAFSA:  </span>' + Ext.util.Format.date(new Date(financialAid.fafsaDate),'m/d/Y'));
        	me.getBalanceOwedField().setFieldLabel('');
			me.getBalanceOwedField().setValue('<span style="color:#15428B">Balance:  </span>' + Ext.util.Format.usMoney(financialAid.balanceOwed));
        	me.getFinancialAidRemainingField().setFieldLabel('');
			me.getFinancialAidRemainingField().setValue('<span style="color:#15428B">FA Amount:  </span>' + Ext.util.Format.usMoney(financialAid.financialAidRemaining));
        	me.getOriginalLoanAmountField().setFieldLabel('');
			me.getOriginalLoanAmountField().setValue('<span style="color:#15428B">Loan Amount:  </span>' + Ext.util.Format.usMoney(financialAid.originalLoanAmount));
        	me.getFinancialAidGpaField().setFieldLabel('');
			me.getFinancialAidGpaField().setValue('<span style="color:#15428B">FA GPA:  </span>' + financialAid.financialAidGpa);

        }
    },
    
    getTranscriptFailure: function(){
        // nothing to do
    },
    
    
    getTranscriptFullSuccess: function(serviceResponses){
        var me = this;
        var transcriptFullResponse = serviceResponses.successes.transcriptFull;
        var transferHours = 0;
        var transcript = new Ssp.model.Transcript(transcriptFullResponse);
        var terms = transcript.get('terms');
        if (terms) {
            Ext.Array.each(terms, function(term){
                var courseTranscript = Ext.create('Ssp.model.CourseTranscript', term);
                var creditType = courseTranscript.get('creditType');
                if (creditType == 'transfer') {
                    var credit = courseTranscript.get('creditEarned');
					if (!isNaN(credit)) 
                    	transferHours += credit;
                }
            });
        }
        
        if (transferHours > 0) {
            me.getTransferHrsField().setFieldLabel('');
            me.getTransferHrsField().setValue('<span style="color:#15428B">Transfer Hrs:  </span>' + transferHours);
        }
        
    },
    
    getTranscriptFullFailure: function(){
        // nothing to do
    },
    
    getStudentIntakeSuccess: function(serviceResponses){
        var me = this;
        
        me.getRemainingLoanAmountField().setFieldLabel('');
        me.getRemainingLoanAmountField().setValue('<span style="color:#15428B">Balance:  </span>');
        
    },
    
    afterServiceHandler: function(serviceResponses){
        var me = this;
        if (serviceResponses.responseCnt >= serviceResponses.expectedResponseCnt) {
            me.getView().setLoading(false);
        }
    },
    
    destroy: function(){
        var me = this;
        return me.callParent(arguments);
    },
    
    onEmailCoach: function(){
        var me = this;
        if (me.person.getCoachPrimaryEmailAddress()) {
            window.location = 'mailto:' + me.person.getCoachPrimaryEmailAddress();
        }
    },

});
