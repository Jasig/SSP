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
/* http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
Ext.define('Ssp.controller.tool.map.MAPController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
		columnRendererUtils : 'columnRendererUtils',
    	currentMapPlan: 'currentMapPlan',
    	transcriptStore: 'courseTranscriptsStore',
		transcriptService: 'transcriptService',
    	personLite: 'personLite',
		person: 'currentPerson',
		termsStore:'termsStore',
		semesterStores : 'currentSemesterStores'
    },
	resetForm: function() {
        var me = this;
        me.getView().getForm().reset();
    },
	init: function() {
		var me=this;
		var personId = me.personLite.get('id');
        
		if(personId != ""){
	    	me.getView().setLoading( true );
	    	if(me.termsStore.getCount() <= 0){
				me.termsStore.addListener("load", me.termStoreLoaded, me, {single:true});
				me.termsStore.load();
			}else{
				me.termStoreLoaded();
			}
			
	    }
		return this.callParent(arguments);
    },
	
	planDataHasChanged:function(buttonId){
		var me = this;
		if(buttonId == "on" || buttonId == "yes"){
			me.appEventsController.getApplication().fireEvent('onSavePlanRequest', {viewToClose:me.getView()});
		}
	},
	
	templateDataHasChanged:function(buttonId){
		var me = this;
		if(buttonId == "on" || buttonId == "yes"){
			me.appEventsController.getApplication().fireEvent('onSaveTemplateRequest', {viewToClose:me.getView()});
		}
	},
	// added here to support semester panels.
	termStoreLoaded: function(){
		var me = this;
		var personId = me.personLite.get('id');
		if(personId != ""){
			var schoolId = me.person.get('schoolId');
			if(me.transcriptStore.getCount() > 0){
				if(me.transcriptStore.findRecord('schoolId', schoolId))
					return;
				me.transcriptStore.removeAll();
			}
			me.transcriptService.getFull( personId, {
				success: me.getTranscriptSuccess,
				failure: me.getTranscriptFailure,
				scope: me			
			});
		}
	},
	
	getTranscriptSuccess: function( r, scope ){
    	var me=scope;

        var courseTranscripts = [];
        var transcript = new Ssp.model.Transcript(r);
        var terms = transcript.get('terms');
        if ( terms ) {
            Ext.Array.each(terms, function(term) {
                    var courseTranscript = Ext.create('Ssp.model.CourseTranscript', term);
					var termIndex = me.termsStore.findExact("code", courseTranscript.get("termCode"));
					if(termIndex >= 0){
						var term = me.termsStore.getAt(termIndex);
						courseTranscript.set("termStartDate", term.get("startDate"));
					}
                    courseTranscripts.push(courseTranscript);
					
            });
        }

        me.transcriptStore.loadData(courseTranscripts);
		me.transcriptStore.sort([
		    {
		        property : 'termStartDate',
		        direction: 'DESC'
		    },
		    {
		        property : 'formattedCourse',
		        direction: 'ASC'
		    }]);
        me.getView().setLoading( false );
    },
    
    getTranscriptFailure: function( response, scope ){
    	var me=scope;
    	me.getView().setLoading( false );  	
    },
	
	destroy:function(){
	    var me=this;
	    me.currentMapPlan.clearMapPlan();
	    return me.callParent( arguments );
	}
});