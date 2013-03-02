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
        profileReferralSourcesStore: 'profileReferralSourcesStore',
        profileServiceReasonsStore: 'profileServiceReasonsStore',
        profileSpecialServiceGroupsStore: 'profileSpecialServiceGroupsStore',
        sspConfig: 'sspConfig'
    },
    
    control: {
        nameField: '#studentName',
        
        studentIdField: '#studentId',
        birthDateField: '#birthDate',
        studentTypeField: '#studentType',
        programStatusField: '#programStatus',

        gpaField: '#cumGPA',
        hoursEarnedField: '#hrsEarned',
        hoursAttemptedField: '#hrsAttempted'
    
    },
    init: function(){
        var me = this;
        var id = me.personLite.get('id');
        me.resetForm();

        if (id != "") {
            // display loader
            me.getView().setLoading(true);

            var serviceResponses = {
                personResponse: null,
                personFailure: false,
                transcriptResponse: null,
                transcriptFailure: false,
                responseCnt: 0,
                expectedResponseCnt: 2
            }

            me.transcriptService.getSummary(id, {
                success: me.newTranscriptSuccessHandler(serviceResponses),
                failure: me.newTranscriptFailureHandler(serviceResponses),
                scope: me
            });
            me.personService.get(id, {
                success: me.newPersonSuccessHandler(serviceResponses),
                failure: me.newPersonFailureHandler(serviceResponses),
                scope: me
            });
        }
        
        return me.callParent(arguments);
    },

    resetForm: function() {
        var me = this;
        me.getView().getForm().reset();

        // Set defined configured label for the studentId field
        var studentIdAlias = me.sspConfig.get('studentIdAlias');
        me.getStudentIdField().setFieldLabel(studentIdAlias);

    },

    newTranscriptSuccessHandler: function(serviceResponses) {
        return function(r, scope) {
            var me = scope;
            serviceResponses.transcriptResponse = r;
            serviceResponses.transcriptFailure = false;
            serviceResponses.responseCnt++;
            me.serviceResponseArrived(serviceResponses);
        };
    },

    newTranscriptFailureHandler: function(serviceResponses) {
        return function(r, scope) {
            var me = scope;
            serviceResponses.transcriptResponse = r;
            serviceResponses.transcriptFailure = true;
            serviceResponses.responseCnt++;
            me.serviceResponseArrived(serviceResponses);
        };
    },

    newPersonSuccessHandler: function(serviceResponses) {
        return function(r, scope) {
            var me = scope;
            serviceResponses.personResponse = r;
            serviceResponses.personFailure = false;
            serviceResponses.responseCnt++;
            me.serviceResponseArrived(serviceResponses);
        };
    },

    newPersonFailureHandler: function(serviceResponses) {
        return function(r, scope) {
            var me = scope;
            serviceResponses.personResponse = r;
            serviceResponses.personFailure = true;
            serviceResponses.responseCnt++;
            me.serviceResponseArrived(serviceResponses);
        };
    },

    serviceResponseArrived: function(serviceResponses) {
        var me = this;
        if ( serviceResponses.responseCnt >= serviceResponses.expectedResponseCnt ) {
            me.allServiceResponsesArrived(serviceResponses);
        }
    },

    allServiceResponsesArrived: function(serviceResponses) {
        var me = this;

        me.getView().setLoading(false);

        if ( serviceResponses.personFailure || serviceResponses.transcriptFailure ) {
            return;
        }

        var studentRecordComp = Ext.ComponentQuery.query('.studentrecord')[0];
        var studentCoachButton = Ext.ComponentQuery.query('#emailCoachButton')[0];
        var nameField = me.getNameField();

        var birthDateField = me.getBirthDateField();
        var studentTypeField = me.getStudentTypeField();
        var programStatusField = me.getProgramStatusField();
        var id = me.personLite.get('id');

        // load and render person data
        me.profileSpecialServiceGroupsStore.removeAll();
        me.profileReferralSourcesStore.removeAll();
        me.profileServiceReasonsStore.removeAll();
        if ( serviceResponses.personResponse ) {
            me.person.populateFromGenericObject(serviceResponses.personResponse);

            var fullName = me.person.getFullName();
            var coachName = me.person.getCoachFullName();

            // load special service groups
            if (serviceResponses.personResponse.specialServiceGroups != null) {
                me.profileSpecialServiceGroupsStore.loadData(me.person.get('specialServiceGroups'));
            }

            // load referral sources
            if (serviceResponses.personResponse.referralSources != null) {
                me.profileReferralSourcesStore.loadData(me.person.get('referralSources'));
            }

            // load service reasons
            if (serviceResponses.personResponse.serviceReasons != null) {
                me.profileServiceReasonsStore.loadData(me.person.get('serviceReasons'));
            }

            // load general student record
            me.getView().loadRecord(me.person);

            // load additional values
            nameField.setValue(fullName);
            birthDateField.setValue(me.person.getFormattedBirthDate());
            studentTypeField.setValue(me.person.getStudentTypeName());
            programStatusField.setValue(me.person.getProgramStatusName());
            studentRecordComp.setTitle('Student: ' + fullName + '          ' + '  -   ID#: ' + me.person.get('schoolId'));
            studentCoachButton.setText('<u>Coach: ' + coachName + '</u>');
        }

        // transcript output
        if ( serviceResponses.transcriptResponse ) {
            var transcript = new Ssp.model.Transcript(serviceResponses.transcriptResponse);
            var gpa = transcript.get('gpa');
            if ( gpa ) {
                me.getGpaField().setValue(gpa.gradePointAverage);
                me.getHoursEarnedField().setValue(gpa.creditHoursForGpa);
                me.getHoursAttemptedField().setValue(gpa.creditHoursAttempted);
            }

        }

        me.appEventsController.assignEvent({
            eventName: 'emailCoach',
            callBackFunc: me.onEmailCoach,
            scope: me
        });



        // hide the loader
        me.getView().setLoading(false);
    },

	
	destroy: function() {
        var me=this;
        me.appEventsController.removeEvent({eventName: 'emailCoach', callBackFunc: me.onEmailCoach, scope: me});
        
        return me.callParent( arguments );
    },

    onEmailCoach: function(){
        var me = this;
        if (coachPrimaryEmailAddress != '') {
            window.location = 'mailto:' + me.person.getCoachPrimaryEmailAddress();
        }
    }
});
