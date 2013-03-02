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
Ext.define('Ssp.controller.tool.sis.TranscriptViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	service: 'transcriptService',
        personLite: 'personLite',
        store: 'courseTranscriptsStore'
    },
	init: function() {
		var me=this;
		var personId = me.personLite.get('id');

        me.store.removeAll();

    	me.getView().setLoading( true );
    	
		me.service.getFull( personId, {
			success: me.getTranscriptSuccess,
			failure: me.getTranscriptFailure,
			scope: me			
		});
		
		return this.callParent(arguments);
    },
    
    getTranscriptSuccess: function( r, scope ){
    	var me=scope;

        var courseTranscripts = [];
        var transcript = new Ssp.model.Transcript(r);
        var terms = transcript.get('terms');
        if ( terms ) {
            Ext.Array.each(terms, function(term) {
                Ext.Array.each(term.courses, function(course) {
                    var courseTranscript = new Ssp.model.CourseTranscript(course);
                    courseTranscript.set('termCode', term.code);
                    courseTranscripts.push(courseTranscript);
                });
            });
        }

        me.store.loadData(courseTranscripts);
        me.getView().setLoading( false );
    },
    
    getTranscriptFailure: function( response, scope ){
    	var me=scope;
    	me.getView().setLoading( false );  	
    }
});