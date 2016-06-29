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
        programStatusChangeReasonsStore: 'programStatusChangeReasonsAllUnpagedStore',
        configStore: 'configStore',
        formUtils: 'formRendererUtils',
        financialAidFilesStore: 'financialAidFilesAllUnpagedStore',
        personRegistrationStatusByTermStore: 'personRegistrationStatusByTermStore',
        configStore: 'configStore',
        textStore: 'sspTextStore',
        mapPlanService: 'mapPlanService',
		currentMapPlan: 'currentMapPlan',
        courseTranscriptsStore: 'courseTranscriptsStore',
        termsStore: 'termsStore',
        termTranscriptsStore: 'termTranscriptsStore',
        careerDecisionStatusesStore: 'careerDecisionStatusesAllStore',
        textStore: 'sspTextStore'
    },
    
    control: {
        genderField: '#gender',
        maritalStatusField: '#maritalStatus',
        ethnicityField: '#ethnicity',
        raceField: '#race',
        gpaLocalField: '#localGPA',
        gpaProgramField: '#programGPA',
        careerStatusField: '#careerStatus',
        residencyCountyField: '#residencyCounty',
        f1StatusField: '#f1Status',
        academicStandingField: '#academicStanding',
        currentRestrictionsField: '#currentRestrictions',
        academicProgramsField: '#academicPrograms',
        intendedProgramAtAdmitField: '#intendedProgramAtAdmit',
        transferHrsField: '#transferHrs',
        
        planProgramField: '#planProgram',
        planCatalogYearField: '#planCatalogYear',
        onPlanField: '#onPlan',
        mapNameField: '#mapName',
        advisorField: '#advisor',
        mapLastUpdatedField: '#mapLastUpdated',
        mapProjectedField: '#mapProjected',
        homeCampusField: '#homeCampus',
        
        cumTermGrid: '#cumTermGrid',
        
        'printPlanButton': {
            selector: '#printPlanButton',
            listeners: {
                click: 'onPrintPlanButtonClick'
            }
        },
        'emailPlanButton': {
            selector: '#emailPlanButton',
            listeners: {
                click: 'onEmailPlanButtonClick'
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

        if (me.financialAidFilesStore.getTotalCount() <= 0) {
            me.financialAidFilesStore.load();
        }
        
        if (!me.programStatusChangeReasonsStore.getTotalCount()) {
            me.programStatusChangeReasonsStore.load();
        }

        return me.callParent(arguments);
    },
    
    onAfterLayout: function(){
        var me = this;
        var id = me.personLite.get('id');
        
        me.getView().getForm().reset();
        
        if (id != "") {
            // display loader
            me.getView().setLoading(true);
            
            var serviceResponses = {
                failures: {},
                successes: {},
                responseCnt: 0,
                expectedResponseCnt: 0
            }
            me.getView().loadRecord(me.person);

            if (!me.personRegistrationStatusByTermStore.getTotalCount()) {
                me.personRegistrationStatusByTermStore.load(id);
            }
            
            me.transcriptService.getSummary(id, {
                success: me.newServiceSuccessHandler('transcript', me.getTranscriptSuccess, serviceResponses),
                failure: me.newServiceFailureHandler('transcript', me.getTranscriptFailure, serviceResponses),
                scope: me
            });

            me.loadCareerStatus();

            if (me.termsStore.getTotalCount() <= 0) {
                me.termsStore.load(function(records, operation, success) {
                   if (!success) {
                       Ext.Msg.alert(
                           me.textStore.getValueByCode('ssp.message.profile-person-details.error-title','SSP Error'),
                           me.textStore.getValueByCode('ssp.message.profile-person-details.unable-to-load','Unable to load Terms. Please see your system administrator for assistance.')
                           );
                   } else {
                       me.fireOnTermsLoad(serviceResponses);
                   }
                });
            } else {
                me.fireOnTermsLoad(serviceResponses);
            }
        }
    },

    loadCareerStatus: function() {
        var me = this;
        var careerStatusUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personCareerDecisionStatus') );
        careerStatusUrl = careerStatusUrl.replace( '{personId}', me.personLite.get("id") );
		me.apiProperties.makeRequest({
   			url: careerStatusUrl,
   			method: 'GET',
   			successFunc: me.careerStatusSuccess,
   			failureFunc: me.careerStatusFailure,
   			scope: me
   		})
    },

    careerStatusSuccess: function(response, scope) {
        var me = this;
        if (response && response.responseText.indexOf("code") > -1) {
            if (me.careerDecisionStatusesStore.getTotalCount() <= 0) {
                me.careerDecisionStatusesStore.load(function(records, operation, success) {
                    if (success) {
                        me.renderCareerStatus(Ext.decode(response.responseText));
                    }
                });
            } else {
                me.renderCareerStatus(Ext.decode(response.responseText));
            }
        }
        me.careerStatusFailure();
    },

    careerStatusFailure: function() {
        //nothing to do
    },

    renderCareerStatus: function(externalCareerStatusObject) {
        var me = this;

        if (externalCareerStatusObject && externalCareerStatusObject.code) {
            var careerStatus = me.careerDecisionStatusesStore.findRecord('code', externalCareerStatusObject.code, 0, false, true, true);
            if (careerStatus) {
                me.getCareerStatusField().setValue(me.handleNull(careerStatus.get('name')));
            } else {
                me.getCareerStatusField().setValue(me.handleNull(externalCareerStatusObject.code));
            }
            me.getCareerStatusField().show();
        }
    },

    fireOnTermsLoad: function(serviceResponses){
        var me = this;
        var id = me.personLite.get('id');

        if (id != "") {
            me.transcriptService.getFull(id, {
                success: me.newServiceSuccessHandler('transcriptFull', me.getTranscriptFullSuccess, serviceResponses),
                failure: me.newServiceFailureHandler('transcriptFull', me.getTranscriptFullFailure, serviceResponses),
                scope: me
            });
            me.transcriptService.getTerm(id, {
                success: me.newServiceSuccessHandler('transcriptTerm', me.getTranscriptTermSuccess, serviceResponses),
                failure: me.newServiceFailureHandler('transcriptTerm', me.getTranscriptTermFailure, serviceResponses),
                scope: me
            });
            me.mapPlanService.getCurrent(id, {
                success: me.newServiceSuccessHandler('map', me.getMapPlanServiceSuccess, serviceResponses),
                failure: me.newServiceFailureHandler('map', me.getMapPlanServiceFailure, serviceResponses),
                scope: me
            });
        }
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

    handleNull: function(value){
        if (value == null || value == undefined || value == 'null') 
            return "";
        return value;
    },
    
    getTranscriptSuccess: function(serviceResponses){
        var me = this;
        var transcriptResponse = serviceResponses.successes.transcript;
        var transcript = new Ssp.model.Transcript(transcriptResponse);
        var gpa = transcript.get('gpa');
        
        if (gpa) {
			me.getAcademicStandingField().setFieldLabel('');
            me.getAcademicStandingField().setValue('<span style="color:#15428B">' + me.textStore.getValueByCode('ssp.label.academic-standing', 'Standing') + ':  </span>' + me.handleNull(gpa.academicStanding));
			me.getCurrentRestrictionsField().setFieldLabel('');
			me.getCurrentRestrictionsField().setValue('<span style="color:#15428B">' + me.textStore.getValueByCode('ssp.label.current-restrictions', 'Restrictions') + ':  </span>' + me.handleNull(gpa.currentRestrictions));

            if (gpa.localGpa) {
                var gpaLocalFormatted = Ext.util.Format.number(me.handleNull(gpa.localGpa), '0.00');
                me.getGpaLocalField().setValue(gpaLocalFormatted);
                me.getGpaLocalField().show();
            }

            if (gpa.programGpa) {
                var gpaProgramFormatted = Ext.util.Format.number(me.handleNull(gpa.programGpa), '0.00');
                me.getGpaProgramField().setValue(gpaProgramFormatted);
                me.getGpaProgramField().show();
            }

            var gpaFormatted = Ext.util.Format.number(me.handleNull(gpa.gradePointAverage), '0.00');
            var grid = me.getCumTermGrid();
            var data = {
                totalCreditCompletionRate: (me.handleNull(gpa.creditCompletionRate) === '' ? '' : (Ext.util.Format.number(gpa.creditCompletionRate, '0.00') + '%')),
                totalCreditHoursAttempted: me.handleNull(Ext.util.Format.number(gpa.creditHoursAttempted, '0.00')),
                totalCreditHoursEarned: me.handleNull(Ext.util.Format.number(gpa.creditHoursEarned, '0.00')),
                totalGradePointAverage: gpaFormatted
            };
            
            grid.getStore().add(data);
        }
        
        var programs = transcript.get('programs');
        if (programs && me.getAcademicProgramsField() && me.getIntendedProgramAtAdmitField()) {
            var programNames = [];
            var intendedProgramsAtAdmit = [];
            Ext.Array.each(programs, function(program){
                if (program.programName && program.programName.length > 0) 
                    programNames.push(program.programName);
                if (program.intendedProgramAtAdmit && program.intendedProgramAtAdmit.length > 0) 
                    intendedProgramsAtAdmit.push(program.intendedProgramAtAdmit);
            });
            me.getAcademicProgramsField().setValue(programNames.join(', '));
            me.getIntendedProgramAtAdmitField().setValue(intendedProgramsAtAdmit.join(', '));
        }
    },
    
    getTranscriptFailure: function(){
    },
    
    
    getTranscriptFullSuccess: function(serviceResponses){
        var me = this;
        var courseTranscripts = [];
        var transcriptFullResponse = serviceResponses.successes.transcriptFull;
        var transferHours = 0;
        var transcript = new Ssp.model.Transcript(transcriptFullResponse);
        var terms = transcript.get('terms');

        if (terms) {
            Ext.Array.each(terms, function(term){
                var courseTranscript = Ext.create('Ssp.model.CourseTranscript', term);
                var termIndex = me.termsStore.getCurrentAndFutureTermsStore(true).findExact("code", courseTranscript.get("termCode"));
                var creditType = courseTranscript.get('creditType');
                var creditTypeUpper = creditType.toUpperCase();
                
                if (creditTypeUpper == 'TRANSFER') {
                    var credit = courseTranscript.get('creditEarned');
                    if (!isNaN(credit)) 
                        transferHours += parseInt((credit * 100).toFixed(0));
                }
                
                if (termIndex < 0) {
                    var tIndex = me.termsStore.findExact("code", courseTranscript.get("termCode"));
                    var term = me.termsStore.getAt(tIndex);
                    if ( term ) {
                        courseTranscript.set("termStartDate", term.get("startDate"));
                    }
                    courseTranscripts.push(courseTranscript);
                }
            });
        }
        var nonCourseItems = transcript.get('nonCourseEntities');
        if (nonCourseItems) {
            Ext.Array.each(nonCourseItems, function(nonCourse) {
                var courseForNonCourseDisplay = Ext.create('Ssp.model.CourseTranscript', nonCourse);
                var termIndexNonCourse = me.termsStore.getCurrentAndFutureTermsStore(true).findExact("code", courseForNonCourseDisplay.get("termCode"));

                if (termIndexNonCourse < 0) {
                    var nonTIndex = me.termsStore.findExact("code", courseForNonCourseDisplay.get("termCode"));
                    var nonCourseTerm = me.termsStore.getAt(nonTIndex);
                    courseForNonCourseDisplay.set("termStartDate", nonCourseTerm.get("startDate"));
                }

                courseForNonCourseDisplay.set("title", me.textStore.getValueByCode('ssp.tooltip.map.student-history.non-course','NonCourse: ') + courseForNonCourseDisplay.get('title'));
                courseForNonCourseDisplay.set("formattedCourse", me.textStore.getValueByCode('ssp.tooltip.map.student-history.overrides','Overrides: ') + nonCourse.targetFormattedCourse);
                courseTranscripts.push(courseForNonCourseDisplay);
            });
        }
        if (courseTranscripts.length > 0) {
            me.courseTranscriptsStore.loadData(courseTranscripts);
            me.courseTranscriptsStore.sort([{
                property: 'termStartDate',
                direction: 'DESC'
            }, {
                property: 'formattedCourse',
                direction: 'ASC'
            }]);
        }
        if (transferHours > 0) {
            me.getTransferHrsField().setValue((transferHours / 100).toFixed(2));
        }
        // is this needed now??
        me.appEventsController.assignEvent({
            eventName: 'emailCoach',
            callBackFunc: me.onEmailCoach,
            scope: me
        });
    },
    
    getTranscriptFullFailure: function(){
        // nothing to do
    },
    
    getTranscriptTermSuccess: function(serviceResponses){
        var me = this;
        var termTranscripts = [];
        var termItems = serviceResponses.successes.transcriptTerm;
        
        Ext.Array.each(termItems, function(rawDatum){
            var termTranscript = Ext.create('Ssp.model.TermTranscript', rawDatum);
            var termIndex = me.termsStore.findExact("code", termTranscript.get("termCode"));
            var paidIndex = me.personRegistrationStatusByTermStore.findExact("termCode", termTranscript.get("termCode"));
            
            if (termIndex >= 0) {
                var term = me.termsStore.getAt(termIndex);
                termTranscript.set("termStartDate", term.get("startDate"));
            }
			if (paidIndex >= 0) {
				var paid = me.personRegistrationStatusByTermStore.getAt(paidIndex);
				termTranscript.set('tuitionPaid', paid.get('tuitionPaid'));
			}
            
            termTranscripts.push(termTranscript);
        });
        
        me.termTranscriptsStore.loadData(termTranscripts);
        
        me.termTranscriptsStore.sort([{
            property: 'termStartDate',
            direction: 'DESC'
        }, {
            property: 'formattedCourse',
            direction: 'ASC'
        }]);
    },
    
    getTranscriptTermFailure: function(response, scope){
        // nothing to do  	
    },
    
    
    getMapPlanServiceSuccess: function(serviceResponses){
        var me = this;
        var mapResponse = serviceResponses.successes.map;
        if (!mapResponse || !mapResponse.responseText || Ext.String.trim(mapResponse.toString()).length == 0) {
            me.getOnPlanField().setValue("Plan Does Not Exist.");
            me.getPrintPlanButton().hide();
            me.getEmailPlanButton().hide();
        }
        else {
            me.currentMapPlan.loadFromServer(Ext.decode(mapResponse.responseText));
            var lastTerm = me.termsStore.getTermsFromTermCodes(me.mapPlanService.getTermCodes(me.currentMapPlan))[0];
            if (me.getOnPlanField()) {
                me.getOnPlanField().setValue("Plan Exists.");
                if (me.getMapNameField()) {
                    me.getMapNameField().setValue(me.currentMapPlan.get("name"));
                }
                me.getMapLastUpdatedField().setValue(me.currentMapPlan.getFormattedModifiedDate());
            }
            if (lastTerm) {
                me.getMapProjectedField().setValue(lastTerm.get("code"));
            }
            me.getPlanCatalogYearField().setValue(me.currentMapPlan.get('catalogYearCode'));
            me.getPlanProgramField().setValue(me.currentMapPlan.get('programCode'));
            
            me.getPrintPlanButton().show();
            me.getEmailPlanButton().show();

            me.updatePlanStatus();
        }
    },
    
    getMapPlanServiceFailure: function(){
        var me = this;
        me.getPrintPlanButton().hide();
        me.getEmailPlanButton().hide();
    },
    
    updatePlanStatus: function(){
        var me = this;
        if (me.currentMapPlan.get('isTemplate') == true || me.currentMapPlan.get('personId') == "") {
            me.getOnPlanField().setValue("");
            return;
        }
        me.getView().setLoading(true);
        var callbacks = new Object();
        var serviceResponses = {
            failures: {},
            successes: {},
            responseCnt: 0,
            expectedResponseCnt: 1
        }
        callbacks.success = me.newServiceSuccessHandler('planStatus', me.onPlanStatusSuccess, serviceResponses);
        callbacks.failure = me.newServiceFailureHandler('planStatus', me.onPlanStatusFailure, serviceResponses);
        callbacks.scope = me;
        me.mapPlanService.planStatus(me.currentMapPlan, callbacks);
    },
    
    onPlanStatusSuccess: function(serviceResponses){
        var me = this;
        me.getView().setLoading(false);
        var planStatus = serviceResponses.successes.planStatus;
        if (planStatus.responseText && planStatus.responseText.length > 1) 
            planStatus = Ext.decode(planStatus.responseText);
        else 
            planStatus = null;
        if (planStatus && planStatus.status == "ON") 
            me.getOnPlanField().setValue("On Plan");
        else 
            if (planStatus && planStatus.status == "OFF") 
                me.getOnPlanField().setValue("Off Plan");
            else 
                if (planStatus && planStatus.status == "ON_TRACK_SUBSTITUTION")
                    me.getOnPlanField().setValue("On Track Substitution");
                else 
                    if (planStatus && planStatus.status == "ON_TRACK_SEQUENCE") 
                        me.getOnPlanField().setValue("On Track Sequence");
                    else 
                        me.getOnPlanField().setValue("No Status");
        var serviceResponses = {
            failures: {},
            successes: {},
            responseCnt: 0,
            expectedResponseCnt: 1
        };
        me.personService.get(me.currentMapPlan.get('ownerId'), {
            success: me.newServiceSuccessHandler('person', me.getPersonSuccess, serviceResponses),
            failure: me.newServiceFailureHandler('person', me.getPersonFailure, serviceResponses),
            scope: me
        });
    },
    
    onPlanStatusFailure: function(){
        var me = this;
        me.getView().setLoading(false);
        me.getOnPlanField().setValue("No Status");
        var serviceResponses = {
            failures: {},
            successes: {},
            responseCnt: 0,
            expectedResponseCnt: 1
        };
        me.personService.get(me.currentMapPlan.get('ownerId'), {
            success: me.newServiceSuccessHandler('person', me.getPersonSuccess, serviceResponses),
            failure: me.newServiceFailureHandler('person', me.getPersonFailure, serviceResponses),
            scope: me
        });
    },

    getPersonSuccess: function(serviceResponses) {
        var me = this;
        var person = serviceResponses.successes.person;
		if(!person) {
			return;
       	} else {
       		var personResponse = serviceResponses.successes.person;
       		var advisor = new Ssp.model.Person();
       		advisor.populateFromGenericObject(personResponse);
	        me.getAdvisorField().setValue(advisor.getFullName());
	        if (me.person.getHomeCampusName() == null || me.person.getHomeCampusName() == "") {
	            me.getHomeCampusField().hide();
	        } else {
	            me.getHomeCampusField().setValue(me.handleNull(me.person.getHomeCampusName()));
	        }
		}
    },

    getPersonFailure: function() {

    },
    
    afterServiceHandler: function(serviceResponses){
        var me = this;
        if (serviceResponses.responseCnt >= serviceResponses.expectedResponseCnt) {
            me.getView().setLoading(false);
        }
    },
    
    onEmailPlanButtonClick: function(button){
        var me = this;
        if (me.emailPlanPopUp == null || me.emailPlanPopUp.isDestroyed) 
            me.emailPlanPopUp = Ext.create('Ssp.view.tools.map.EmailPlan', {
                hidden: true
            });
        me.emailPlanPopUp.emailEvent = 'onEmailCurrentMapPlan';
        me.emailPlanPopUp.show();
    },
    
    onPrintPlanButtonClick: function(button){
        var me = this;
        if (me.printPlanPopUp == null || me.printPlanPopUp.isDestroyed) 
            me.printPlanPopUp = Ext.create('Ssp.view.tools.map.PrintPlan', {
                hidden: true
            });
        me.printPlanPopUp.printEvent = 'onPrintCurrentMapPlan';
        me.printPlanPopUp.show();
    },
    
    onEmailCoach: function(){
        var me = this;
        if (me.person.getCoachPrimaryEmailAddress()) {
            window.location = 'mailto:' + me.person.getCoachPrimaryEmailAddress();
        }
    },
    
    destroy: function(){
        var me = this;

        me.personRegistrationStatusByTermStore.removeListener("load", me.onRegStoreLoaded, me);
        me.appEventsController.removeEvent({
            eventName: 'emailCoach',
            callBackFunc: me.onEmailCoach,
            scope: me
        });
        return me.callParent(arguments);
    }
});
