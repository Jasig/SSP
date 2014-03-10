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
		personProgramStatusService: 'personProgramStatusService',
		programStatusChangeReasonsStore: 'programStatusChangeReasonsStore',
        sspConfig: 'sspConfig',
        formUtils: 'formRendererUtils',
        sapStatusesStore: 'sapStatusesStore',
        financialAidFilesStore: 'financialAidFilesStore',
        textStore:'sspTextStore'
    },
    
    control: {
        nameField: '#studentName',
        photoUrlField: '#studentPhoto',
        
        
        studentIdField: '#studentId',
        birthDateField: '#birthDate',
        studentTypeField: '#studentType',
        programStatusField: '#programStatus',
		programStatusReasonField: '#programStatusReason',
        paymentStatusField: '#paymentStatus',
        f1StatusField: '#f1Status',
        residencyCountyField: '#residencyCounty',
        maritalStatusField: '#maritalStatus',
        genderField: '#gender',
        ethnicityField: '#ethnicity',
		raceField: '#race',
		primaryEmailAddressLabel: '#primaryEmailAddressLabel',
		primaryEmailAddressField: '#primaryEmailAddressField',
		registeredTermsField: '#registeredTerms',
        
        gpaField: '#cumGPA',
        transferHrsField: '#transferHrs',
        
        academicStandingField: '#academicStanding',
        currentRestrictionsField: '#currentRestrictions',
        creditCompletionRateField: '#creditCompletionRate',
        academicProgramsField: '#academicPrograms',
        intendedProgramAtAdmitField: '#intendedProgramAtAdmit',

        fafsaDateField: '#fafsaDate',
        
        financialAidGpaField: '#financialAidGpa',
        creditHoursEarnedField: '#creditHoursEarned',
        creditHoursAttemptedField: '#creditHoursAttempted',
        creditCompletionRateField: '#creditCompletionRate',
        balanceOwedField: '#balanceOwed',
        financialAidRemainingField: '#financialAidRemaining',
        originalLoanAmountField: '#originalLoanAmount',
        sapStatusCodeField: '#sapStatusCode',
        eligibleFederalAidField: '#eligibleFederalAid',
        termsLeftField: '#termsLeft',
        institutionalLoanAmountField: '#institutionalLoanAmount',
        financialAidFileStatusField: '#financialAidFileStatusDetails',
		financialAidAcceptedTermsField: '#financialAidAcceptedTerms',

    },
    init: function(){
        var me = this;
        var id = me.personLite.get('id');
        me.resetForm();
        me.sapStatusCode = null;
        if(me.sapStatusesStore.getTotalCount() <= 0){
			me.sapStatusesStore.load();
        }
        if(me.financialAidFilesStore.getTotalCount() <= 0){
			me.financialAidFilesStore.load();
        }
        if (id != "") {
            // display loader
            me.getView().setLoading(true);
            
            var serviceResponses = {
                failures: {},
                successes: {},
                responseCnt: 0,
                expectedResponseCnt: 4
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
			me.personProgramStatusService.getCurrentProgramStatus(id, {
                success: me.newServiceSuccessHandler('programstatus', me.getCurrentProgramStatusSuccess, serviceResponses),
                failure: me.newServiceFailureHandler('programstatus', me.getCurrentProgramStatusFailure, serviceResponses),
                scope: me
            });
        }
		
		if (!me.programStatusChangeReasonsStore.getTotalCount()) {
			me.programStatusChangeReasonsStore.load({
				params: {
					start: 0,
					limit: 50
				}
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
        me.setFinancialLabels();
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
               
        var nameField = me.getNameField();
		var primaryEmailAddressField = me.getPrimaryEmailAddressField();
		var primaryEmailAddressLabel = me.getPrimaryEmailAddressLabel();
		var photoUrlField = me.getPhotoUrlField();
        var birthDateField = me.getBirthDateField();
        var studentTypeField = me.getStudentTypeField();
        var programStatusField = me.getProgramStatusField();
        var studentIdField = me.getStudentIdField();
        
        var fullName = me.person.getFullName();
		var firstLastName = me.person.getFirstLastName();
        var coachName = me.person.getCoachFullName();
		
		var anticipatedStartYearTermField = Ext.ComponentQuery.query('#anticipatedStartYearTerm', me.getView())[0];
		
        
        // load general student record
        me.getView().loadRecord(me.person);
        
        // load additional values
        nameField.setFieldLabel('');
        nameField.setValue('<span style="color:#15428B">'+'Name'+':  </span>' + firstLastName);
        studentIdField.setFieldLabel('');
        studentIdField.setValue('<span style="color:#15428B">' + me.sspConfig.get('studentIdAlias') + ':  </span>' + me.person.get('schoolId'));
        primaryEmailAddressLabel.setFieldLabel('');
		primaryEmailAddressLabel.setValue('<span style="color:#15428B">'+me.textStore.getValueByCode('ssp.label.school-email')+':  </span>' );
		primaryEmailAddressField.setFieldLabel('');
        primaryEmailAddressField.setValue('<a href="mailto:'+me.handleNull(me.person.get('primaryEmailAddress'))+'" target="_top">'+me.handleNull(me.person.get('primaryEmailAddress'))+'</a>');        
        birthDateField.setFieldLabel('');
        birthDateField.setValue('<span style="color:#15428B">'+me.textStore.getValueByCode('ssp.label.dob')+':  </span>' + me.person.getFormattedBirthDate());
        studentTypeField.setFieldLabel('');
        studentTypeField.setValue('<span style="color:#15428B">Student Type:  </span>' + me.handleNull(me.person.getStudentTypeName()));
        photoUrlField.setSrc(me.person.getPhotoUrl());
        programStatusField.setFieldLabel('');
        programStatusField.setValue('<span style="color:#15428B">SSP Status:  </span>' + me.handleNull(me.person.getProgramStatusName()));
        me.getRegisteredTermsField().setValue(me.person.get('registeredTerms'));
        me.getPaymentStatusField().setValue(me.handleNull(me.person.get('paymentStatus')));
		
		anticipatedStartYearTermField.setFieldLabel('');
		anticipatedStartYearTermField.setValue('<span style="color:#15428B">Anticipated Start Year/Term:  </span>' + me.person.get('anticipatedStartYear') + '/' + me.person.get('anticipatedStartTerm'));
        
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
    
    handleNull: function(value){
		if(value == null || value == undefined || value == 'null')
			return "";
		return value;
	},
	
	setFinancialLabels: function(){
		var me=this;
		me.getFinancialAidFileStatusField().setText('<span style="color:#15428B">FA File:   </span>', false);
        me.getSapStatusCodeField().setText('<span style="color:#15428B">SAP Code:   </span>', false);
        me.getFinancialAidAcceptedTermsField().setText('<span style="color:#15428B">FA Awarded:   </span>', false);
	},
    
    getTranscriptSuccess: function(serviceResponses){
        var me = this;
        var transcriptResponse = serviceResponses.successes.transcript;
        
        var transcript = new Ssp.model.Transcript(transcriptResponse);
        var gpa = transcript.get('gpa');
        if (gpa) {
            var gpaFormatted = Ext.util.Format.number(me.handleNull(gpa.gradePointAverage), '0.00');
            me.getGpaField().setFieldLabel('');
            me.getGpaField().setValue('<span style="color:#15428B">GPA:  </span>' + gpaFormatted);
            me.getAcademicStandingField().setFieldLabel('');
            me.getAcademicStandingField().setValue('<span style="color:#15428B">Standing:  </span>' + me.handleNull(gpa.academicStanding));
			me.getCreditCompletionRateField().setFieldLabel('');
            me.getCreditCompletionRateField().setValue('<span style="color:#15428B">Comp Rate:  </span>' + me.handleNull(gpa.creditCompletionRate) + '%');
            me.getCurrentRestrictionsField().setFieldLabel('');
            me.getCurrentRestrictionsField().setValue('<span style="color:#15428B">Restrictions:  </span>' + me.handleNull(gpa.currentRestrictions));
            me.getCreditHoursEarnedField().setFieldLabel('');
            me.getCreditHoursEarnedField().setValue('<span style="color:#15428B">Hrs Earned:  </span>' + me.handleNull(gpa.creditHoursEarned));
            me.getCreditHoursAttemptedField().setFieldLabel('');
            me.getCreditHoursAttemptedField().setValue('<span style="color:#15428B">Hrs Attempted:  </span>' + me.handleNull(gpa.creditHoursAttempted));
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
        	me.getFafsaDateField().setFieldLabel('');
			me.getFafsaDateField().setValue('<span style="color:#15428B">FAFSA:  </span>' + Ext.util.Format.date(Ext.Date.parse(financialAid.fafsaDate, 'c'),'m/d/Y'));
        	me.getBalanceOwedField().setFieldLabel('');
			me.getBalanceOwedField().setValue('<span style="color:#15428B">Balance:  </span>' + Ext.util.Format.usMoney(financialAid.balanceOwed));
        	me.getFinancialAidRemainingField().setFieldLabel('');
			if(financialAid.financialAidRemaining != null)
				me.getFinancialAidRemainingField().setValue('<span style="color:#15428B">FA Amount:  </span>' + Ext.util.Format.usMoney(financialAid.financialAidRemaining));
			else
				me.getFinancialAidRemainingField().setValue('<span style="color:#15428B">FA Amount:  </span>' );
        	me.getOriginalLoanAmountField().setFieldLabel('');
			if(financialAid.originalLoanAmount != null)
				me.getOriginalLoanAmountField().setValue('<span style="color:#15428B">Loan Amount:  </span>' + Ext.util.Format.usMoney(financialAid.originalLoanAmount));
			else
				me.getOriginalLoanAmountField().setValue('<span style="color:#15428B">Loan Amount:  </span>');
        	me.getFinancialAidGpaField().setFieldLabel('');
			
			me.sapStatusCode = financialAid.sapStatusCode;
			me.getEligibleFederalAidField().setValue(me.handleNull(financialAid.eligibleFederalAid));
			me.getTermsLeftField().setValue(me.handleNull(financialAid.termsLeft));
			me.getInstitutionalLoanAmountField().setValue(me.handleNull(Ext.util.Format.usMoney(financialAid.institutionalLoanAmount)));
			
			me.getSapStatusCodeField().setText('<span style="color:#15428B">SAP Code:   </span><u>' + me.handleNull(financialAid.sapStatusCode) + '</u>', false);
			me.getFinancialAidFileStatusField().setText('<span style="color:#15428B">FA File:   </span><u>' + me.handleNull(financialAid.financialAidFileStatus) + '</u>', false);
        }
        financialAidAwards = transcript.get('financialAidAcceptedTerms');
        if ( financialAidAwards  && financialAidAwards.length > 0) {
        	var model = Ext.create("Ssp.model.external.FinancialAidAward");
           	model.populateFromExternalData(financialAidAwards[0]);
        	me.getFinancialAidAcceptedTermsField().setText('<span style="color:#15428B">FA Awarded:   </span><u>' + model.get("termCode") + " (" +  model.get("acceptedLong") + ")</u>", false);
        }		
    },
    
    getTranscriptFailure: function(){
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
				var creditTypeUpper = creditType.toUpperCase();
                if (creditTypeUpper == 'TRANSFER') {
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
	
	getCurrentProgramStatusSuccess: function(serviceResponses) {
        var me = this;
		var programStatusReason;
		var studentStatus;
		
		var programStatusResponse = serviceResponses.successes.programstatus;
		studentStatus = programStatusResponse['programStatusChangeReasonId'];
		
		var programStatusReasonField = me.getProgramStatusReasonField();
		
		
		if (studentStatus) {
                    programStatusReason = me.programStatusChangeReasonsStore.findRecord('id', studentStatus);
					
                    if (programStatusReason) {
							programStatusReasonField.show();
							programStatusReasonField.setFieldLabel('');
							programStatusReasonField.setValue('<span style="color:#15428B">Reason:  </span>' + programStatusReason.get('name'));
					}
		}
		else
		{
			programStatusReasonField.hide();
		}
		
	},
	
	getCurrentProgramStatusFailure: function() {
		
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
    
	closePopups:function(){
		var me=this;
		if(me.sapCodeInfoPopup != null && !me.sapCodeInfoPopup.isDestroyed)
	    	me.sapCodeInfoPopup.close();
        
        if(me.financialAidFilePopup != null && !me.financialAidFilePopup.isDestroyed)
	    	me.financialAidFilePopup.close();
        
        if(me.financialAidAwardPopup != null && !me.financialAidAwardPopup.isDestroyed)
	    	me.financialAidAwardPopup.close();
	},
	
    destroy: function(){
        var me = this;
    	me.closePopups();
        return me.callParent(arguments);
    },
    
    
    onEmailCoach: function(){
        var me = this;
        if (me.person.getCoachPrimaryEmailAddress()) {
            window.location = 'mailto:' + me.person.getCoachPrimaryEmailAddress();
        }
    },
   
    
    onShowSAPCodeInfo: function(){
    	var me=this;
		if(me.sapCodeInfoPopup == null || me.sapCodeInfoPopup.isDestroyed)
       		me.sapCodeInfoPopup = Ext.create('Ssp.view.tools.profile.SapStatus',{hidden:true,code:me.sapStatusCode});
		me.sapCodeInfoPopup.show();
    },
    
    onShowFinancialAidFileStatuses: function(){
    	var me=this;
		if(me.financialAidFilePopup == null || me.financialAidFilePopup.isDestroyed)
       		me.financialAidFilePopup = Ext.create('Ssp.view.tools.profile.FinancialAidFileViewer',{hidden:true});
		me.financialAidFilePopup.show();
    },
    
    onShowFinancialAidAwards: function(){
    	var me=this;
		if(me.financialAidFilePopup == null || me.financialAidFilePopup.isDestroyed)
       		me.financialAidFilePopup = Ext.create('Ssp.view.tools.profile.FinancialAidAwardViewer',{hidden:true});
		me.financialAidFilePopup.show();
    }
});
