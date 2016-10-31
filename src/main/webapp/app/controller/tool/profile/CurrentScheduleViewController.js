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
Ext.define('Ssp.controller.tool.profile.CurrentScheduleViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        apiProperties: 'apiProperties',
        service: 'transcriptService',
        personLite: 'personLite',
        store: 'currentScheduleStore',
        termsStore: 'termsStore',
        textStore: 'sspTextStore'
    },
    config: {
        participationIndicatorConfig: ''
    },

    init: function() {
        var me = this;
        me.store.removeAll();

        var personId = me.personLite.get('id');
        if (personId != "") {
            me.getView().setLoading(true);
            
            if (me.termsStore.getTotalCount() <= 0) {
                me.termsStore.addListener("load", me.getParticipationIndicatorConfig, me, {
                    single: true
                });
                me.termsStore.load();
            }
            else {
                me.getParticipationIndicatorConfig();
            }
        }
        return this.callParent(arguments);
    },

    getParticipationIndicatorConfig: function () {
        var me = this;
        //NOTE: uuid is hard-coded in liquibase, TODO: in the future consider re-factor schedule and participation server-side
        var url = me.apiProperties.createUrl(me.apiProperties.getItemUrl('successIndicator') + '/ff8777e5-cabf-4237-bf42-0500c39ec8dc');
        me.apiProperties.makeRequest({
            url: url,
            method: 'GET',
            successFunc: me.getParticipationIndicatorSuccess,
            failureFunc: me.termStoreLoaded,
            scope: me
        });
    },

    getParticipationIndicatorSuccess: function(r, scope) {
        var me = this;

        if (r && r.responseText) {
            r = Ext.decode(r.responseText);
            if (r) {
                me.participationIndicatorConfig = Ext.create('Ssp.model.reference.SuccessIndicator', r);
            }
        }
        me.termStoreLoaded();
    },

    termStoreLoaded: function() {
        var me = this;
        var personId = me.personLite.get('id');
        if (personId != "") {
            me.service.getFull(personId, {
                success: me.getScheduleSuccess,
                failure: me.getScheduleFailure,
                scope: me
            });
        }
    },

    getScheduleSuccess: function(r, scope) {
        var me = scope;
        var courseSchedules = [];
        var transcript = new Ssp.model.Transcript(r);

        if (transcript) {
            var items = transcript.get('terms');
            if (items) {
                Ext.Array.each(items, function(item){
                    var courseTranscript = Ext.create('Ssp.model.CourseTranscript', item);
				
                    var termIndex = me.termsStore.getCurrentAndFutureTermsStore(true).findExact("code", courseTranscript.get("termCode"));
                    if (termIndex >= 0) {
                        var term = me.termsStore.getCurrentAndFutureTermsStore(true).getAt(termIndex);
                        courseTranscript.set("termStartDate", term.get("startDate"));

                        if (me.participationIndicatorConfig && me.participationIndicatorConfig.get('objectStatus') == 'ACTIVE' && courseTranscript.get('participation')) {
                            courseTranscript.set("evaluatedParticipationIndicator",
                                me.evaluateCourseParticipationScore(courseTranscript.get('participation')));
                        }
                        courseSchedules.push(courseTranscript);
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
                    courseSchedules.push(courseForNonCourseDisplay);
                });
            }
        }
        
        if (courseSchedules.length > 0) {
            me.store.loadData(courseSchedules);
            me.store.sort([{
                property: 'termStartDate',
                direction: 'ASC'
            }, {
                property: 'formattedCourse',
                direction: 'ASC'
            }]);
        }
        me.getView().setLoading(false);
    },
    
    getScheduleFailure: function(response, scope) {
        var me = scope;
        me.getView().setLoading(false);
    },

    evaluateCourseParticipationScore: function(participation) {
        var me = this;

        if (me.participationIndicatorConfig && participation) {
            var participationMaybeDecimal = parseFloat(participation.trim());

            if (participationMaybeDecimal && participationMaybeDecimal != 'Nan' &&
                me.participationIndicatorConfig.get('evaluationType').trim() === 'SCALE') {

                //numeric (basic numeric range calc only
                if (me.participationIndicatorConfig.get('scaleEvaluationHighTo') &&
                    participationMaybeDecimal >= me.participationIndicatorConfig.get('scaleEvaluationHighFrom') &&
                    participationMaybeDecimal <= me.participationIndicatorConfig.get('scaleEvaluationHighTo')) {

                    return 2;

                } else if (me.participationIndicatorConfig.get('scaleEvaluationMediumTo') &&
                    participationMaybeDecimal >= me.participationIndicatorConfig.get('scaleEvaluationMediumFrom') &&
                        participationMaybeDecimal <= me.participationIndicatorConfig.get('scaleEvaluationMediumTo')) {

                    return 1;

                } else if (me.participationIndicatorConfig.get('scaleEvaluationLowTo') &&
                    participationMaybeDecimal >= me.participationIndicatorConfig.get('scaleEvaluationLowFrom') &&
                    participationMaybeDecimal <= me.participationIndicatorConfig.get('scaleEvaluationLowTo')) {

                    return 0;
                }
            } else if (me.participationIndicatorConfig.get('evaluationType').trim() === 'STRING' &&
                me.participationIndicatorConfig.get('stringEvaluationHigh')) {

                //string (we're just doing basic here, no comma separated arrays etc.)
                if (me.participationIndicatorConfig.get('stringEvaluationHigh') &&
                    participation.trim() === me.participationIndicatorConfig.get('stringEvaluationHigh').trim()) {

                    return 2;

                } else if (me.participationIndicatorConfig.get('stringEvaluationMedium') &&
                    participation.trim() === me.participationIndicatorConfig.get('stringEvaluationMedium').trim()) {

                    return 1;

                } else if (me.participationIndicatorConfig.get('stringEvaluationLow') &&
                    participation.trim() === me.participationIndicatorConfig.get('stringEvaluationLow').trim()) {

                    return 0;
                }
            }
        }
        return -1; //-1 is error, no data, or default
    }
});
