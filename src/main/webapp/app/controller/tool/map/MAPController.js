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
		semesterStores : 'currentSemesterStores',
		authenticatedPerson : 'authenticatedPerson'
    },
	resetForm: function() {
        var me = this;
        me.getView().getForm().reset();
    },
	init: function() {
		var me=this;
		var personId = me.personLite.get('id');
		if(personId != ""  && me.authenticatedPerson.hasAccess("ACCESS_STUDENT_TRANSCRIPTS"))
		{
			me.loadTranscript();
		}
		me.appEventsController.getApplication().addListener('onBeforePlanLoad', me.onInitEvent, me);
		me.appEventsController.getApplication().addListener('onBeforePlanSave', me.onInitEvent, me);
		me.appEventsController.getApplication().addListener('onRemoveMask', me.onAfterEvent, me);		
		me.appEventsController.getApplication().addListener('onAfterPlanSave', me.onAfterEvent, me);		
		return this.callParent(arguments);
    },
	onInitEvent: function(){
		var me = this;
		me.getView().setLoading(true);
	},
	onAfterEvent: function(){
		var me = this;
		me.getView().setLoading(false);
	},		
	removeMask: function() {
		var me = this;
		me.getView().setLoading(false);
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
	loadTranscript: function(){
		var me = this;
		var personId = me.personLite.get('id');
		if(personId != ""){
			var schoolId = me.person.get('schoolId');
			if(me.transcriptStore.getCount() > 0){
				if(me.transcriptStore.findRecord('schoolId', schoolId, 0, false, false, true))
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
	    
		me.appEventsController.getApplication().removeListener('onBeforePlanLoad', me.onInitEvent, me);
		me.appEventsController.getApplication().removeListener('onBeforePlanSave', me.onInitEvent, me);
		me.appEventsController.getApplication().removeListener('onRemoveMask', me.onAfterEvent, me);		
		me.appEventsController.getApplication().removeListener('onAfterPlanSave', me.onAfterEvent, me);
		
	    me.currentMapPlan.clearMapPlan();
	    return me.callParent( arguments );
	}
});