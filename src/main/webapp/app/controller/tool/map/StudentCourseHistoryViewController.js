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
Ext.define('Ssp.controller.tool.map.StudentCourseHistoryViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	service: 'transcriptService',
        personLite: 'personLite',
        store: 'studentCourseHistoryStore',
		termsStore: 'termsStore',
		textStore: 'sspTextStore'
    },

	init: function() {
		var me=this;
        me.store.removeAll();

		var personId = me.personLite.get('id');
		if (personId != "") {
	    	me.getView().setLoading( true );
	    	if (me.termsStore.getTotalCount() <= 0) {
				me.termsStore.addListener("load", me.termStoreLoaded, me, {single:true});
				me.termsStore.load();
			} else {
				me.termStoreLoaded();
			}
	    }

		return this.callParent(arguments);
    },
    
	termStoreLoaded: function() {
		var me = this;
		var personId = me.personLite.get('id');
		if (personId != "") {
			me.service.getFull( personId, {
				success: me.getTranscriptSuccess,
				failure: me.getTranscriptFailure,
				scope: me			
			});
		}
	},
	
    getTranscriptSuccess: function( r, scope ) {
    	var me=scope;
        var courseTranscripts = [];
        var transcript = new Ssp.model.Transcript(r);

        if (transcript) {
            var terms = transcript.get('terms');
            if (terms) {
                Ext.Array.each(terms, function(term) {
                        var courseTranscript = Ext.create('Ssp.model.CourseTranscript', term);
                        var termIndex = me.termsStore.findExact("code", courseTranscript.get("termCode"));
                        if (termIndex >= 0) {
                            var term = me.termsStore.getAt(termIndex);
                            courseTranscript.set("termStartDate", term.get("startDate"));
                        }
                        courseTranscripts.push(courseTranscript);
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
                    } else {
                        var nonCourseTerm = me.termsStore.getCurrentAndFutureTermsStore(true).getAt(termIndexNonCourse);
                    }
                    courseForNonCourseDisplay.set("termStartDate", nonCourseTerm.get("startDate"));

                    courseForNonCourseDisplay.set("title", me.textStore.getValueByCode('ssp.tooltip.map.student-history.non-course','NonCourse: ') + courseForNonCourseDisplay.get('title'));
                    courseForNonCourseDisplay.set("formattedCourse", me.textStore.getValueByCode('ssp.tooltip.map.student-history.overrides','Overrides: ') + nonCourse.targetFormattedCourse);
                    courseTranscripts.push(courseForNonCourseDisplay);
                });
            }
        }

        if (courseTranscripts.length > 0) {
        	me.store.loadData(courseTranscripts);
        	me.store.sort([{
                property : 'termStartDate',
                direction: 'DESC'
            }, {
                property : 'formattedCourse',
                direction: 'ASC'
            }]);
    	}
        me.getView().setLoading(false);
    },
    
    getTranscriptFailure: function( response, scope ) {
    	var me=scope;
    	me.getView().setLoading(false);
    }
});
