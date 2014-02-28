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
Ext.define('Ssp.controller.tool.profile.FinancialAidAwardViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
        financialAidFilesStore: 'financialAidFilesAllUnpagedStore',
		transcriptService: 'transcriptService',
        personLite: 'personLite'
    },

	init: function() {
		var me=this;
		
		var id = me.personLite.get('id');
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
		
		return this.callParent(arguments);
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

	afterServiceHandler: function(serviceResponses){
        var me = this;
        if (serviceResponses.responseCnt >= serviceResponses.expectedResponseCnt) {
            me.getView().setLoading(false);
        }
    },
    
    getTranscriptSuccess: function(serviceResponses) {
        var me = this;

		var view = me.getView();
	    var grid =	Ext.ComponentQuery.query("financialaidawards");
	    if(grid && grid.length > 0)
			grid = grid[0];
			
        view.setLoading(false);
        var transcriptResponse = serviceResponses.successes.transcript;

        var transcript = new Ssp.model.Transcript(transcriptResponse);

		var financialAidAwards = transcript.get('financialAidAcceptedTerms');
		
		var storeFAAwards = grid.getStore();
		storeFAAwards.removeAll();
		for(var i = 0; i < financialAidAwards.length; i++){
			var faAward = Ext.create('Ssp.model.external.FinancialAidAward');
			faAward.populateFromExternalData(financialAidAwards[i]);
			storeFAAwards.add(faAward);
		}
    },

    getTranscriptFailure: function() {
    	 // display loader
        me.getView().setLoading(false);
    },
    


	destroy: function() {
	        var me=this;
	        return me.callParent( arguments );
	}
	
});