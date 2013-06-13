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
Ext.define('Ssp.controller.tool.map.FAViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        apiProperties: 'apiProperties',
        appEventsController: 'appEventsController',
        person: 'currentPerson',
        personLite: 'personLite',
        transcriptService: 'transcriptService',
        sspConfig: 'sspConfig',
		formUtils: 'formRendererUtils'
    },
    control: {

        f1StatusField: '#f1Status',       

        gpaField: '#cumGPA',
        
        academicStandingField: '#academicStanding',
        currentRestrictionsField: '#currentRestrictions',
        currentYearFinancialAidAwardField: '#currentYearFinancialAidAward',
        sapStatusField: '#sapStatus',
        fafsaDateField: '#fafsaDate',
       
        financialAidGpaField: '#financialAidGpa',
        gpa20BHrsNeededField: '#gpa20BHrsNeeded',
        gpa20AHrsNeededField: '#gpa20AHrsNeeded',
        neededFor67PtcCompletionField: '#neededFor67PtcCompletion',
        creditHoursEarnedField: '#creditHoursEarned',
        creditHoursAttemptedField: '#creditHoursAttempted',
        creditCompletionRateField: '#creditCompletionRate',
        balanceOwedField: '#balanceOwed',
        paymentStatusField: '#paymentStatus',
		registeredTermsField: '#registeredTerms',
        financialAidRemainingField: '#financialAidRemaining',
        originalLoanAmountField: '#originalLoanAmount'
    
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
	                expectedResponseCnt: 1
	            }


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

	    handleNull: function(value){
			if(value == null || value == undefined || value == 'null')
				return "";
			return value;
		},

    getTranscriptSuccess: function(serviceResponses) {
        var me = this;
        var transcriptResponse = serviceResponses.successes.transcript;

        var transcript = new Ssp.model.Transcript(transcriptResponse);
        me.getRegisteredTermsField().setValue(me.handleNull(me.person.get('registeredTerms')));
        me.getPaymentStatusField().setValue(me.handleNull(me.person.get('paymentStatus')));
        var gpa = transcript.get('gpa');
        if ( gpa ) {
        	var gpaFormatted = Ext.util.Format.number(gpa.gradePointAverage, '0.00');
			if(gpa.gpaTrendIndicator && gpa.gpaTrendIndicator.length > 0)
				gpaFormatted += "  " + gpa.gpaTrendIndicator;
            me.getGpaField().setValue(gpaFormatted);
            me.getAcademicStandingField().setValue(me.handleNull(gpa.academicStanding));
            me.getCreditCompletionRateField().setValue(me.handleNull(gpa.creditCompletionRate) + '%');
            me.getCurrentRestrictionsField().setValue(me.handleNull(gpa.currentRestrictions))

            me.getCreditHoursEarnedField().setValue(me.handleNull(gpa.creditHoursEarned));
            me.getCreditHoursAttemptedField().setValue(me.handleNull(gpa.creditHoursAttempted));
            
        }
        

        var financialAid = transcript.get('financialAid');
        if ( financialAid ) {
            
        	me.getCurrentYearFinancialAidAwardField().setValue(me.handleNull(financialAid.currentYearFinancialAidAward));
        	me.getSapStatusField().setValue(me.handleNull(financialAid.sapStatus));
        	
        	me.getFafsaDateField().setValue(Ext.util.Format.date(Ext.Date.parse(financialAid.fafsaDate, 'c'),'m/d/Y'));
        	me.getBalanceOwedField().setValue(Ext.util.Format.usMoney(financialAid.balanceOwed));
        	me.getFinancialAidRemainingField().setValue(Ext.util.Format.usMoney(financialAid.financialAidRemaining));
        	me.getOriginalLoanAmountField().setValue(Ext.util.Format.usMoney(financialAid.originalLoanAmount));
        	me.getFinancialAidGpaField().setValue(me.handleNull(financialAid.financialAidGpa));
			me.getGpa20AHrsNeededField().setValue(me.handleNull(financialAid.gpa20AHrsNeeded));
	        me.getGpa20BHrsNeededField().setValue(me.handleNull(financialAid.gpa20BHrsNeeded));
			me.getNeededFor67PtcCompletionField().setValue(me.handleNull(financialAid.neededFor67PtcCompletion));
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
    }

});
