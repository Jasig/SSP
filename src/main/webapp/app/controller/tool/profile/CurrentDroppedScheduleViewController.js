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
Ext.define('Ssp.controller.tool.profile.CurrentDroppedScheduleViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	service: 'transcriptService',
        personLite: 'personLite',
        store: 'currentDroppedScheduleStore'
    },
	init: function() {
		var me=this;
		var personId = me.personLite.get('id');

        me.store.removeAll();
        if(personId != ""){
	    	me.getView().setLoading( true );
	    	
			me.service.getCurrentCourses( personId, {
				success: me.getTranscriptSuccess,
				failure: me.getTranscriptFailure,
				scope: me			
			});
		}
		
		return this.callParent(arguments);
    },
    
    getTranscriptSuccess: function( r, scope ){
    	var me=scope;

        var currentScheduleDroppedCourses = [];

        Ext.Array.each(r, function(courseTranscriptRaw) {
                if(courseTranscriptRaw.statusCode === "W" || courseTranscriptRaw.statusCode === "RW"){
					var courseTranscript = Ext.create('Ssp.model.CourseTranscript', courseTranscriptRaw);
					currentScheduleDroppedCourses.push(courseTranscript);
				}
                	
        });


        me.store.loadData(currentScheduleDroppedCourses);
        me.getView().setLoading( false );
    },
    
    getTranscriptFailure: function( response, scope ){
    	var me=scope;
    	me.getView().setLoading( false );  	
    }
});