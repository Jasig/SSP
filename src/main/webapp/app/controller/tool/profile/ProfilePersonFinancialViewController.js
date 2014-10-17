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
Ext.define('Ssp.controller.tool.profile.ProfilePersonFinancialViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        person: 'currentPerson',
     	appEventsController: 'appEventsController',
        personLite: 'personLite',
        personService: 'personService',
        transcriptService: 'transcriptService',
        financialAidFilesStore: 'financialAidFilesAllUnpagedStore'
    },
    
    control: {
		balanceOwedField: '#balanceOwed',
		financialAidGpaField: '#financialAidGpa',
		fafsaDateField: '#fafsaDate',
		financialAidFileStatusField: '#financialAidFileStatusDetails',
		financialAidRemainingField: '#financialAidRemaining',
		termsLeftField: '#termsLeft',
		institutionalLoanAmountField: '#institutionalLoanAmount',
		originalLoanAmountField: '#originalLoanAmount',
		sapStatusCodeField: '#sapStatusCode',
		sapStatusDescriptionField: '#sapStatusDescription',
		
		faAwardsGrid: '#financialAidAwards',
		faFilesGrid: '#financialAidFiles',
		
        view: {
    		afterlayout: {
    			fn: 'onAfterLayout',
    			single: true
    		}
    	}

    },
    init: function(){
        var me = this;
        
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
                expectedResponseCnt: 1
            }
	            
            me.transcriptService.getSummary(id, {
                success: me.newServiceSuccessHandler('transcript', me.getTranscriptSuccess, serviceResponses),
                failure: me.newServiceFailureHandler('transcript', me.getTranscriptFailure, serviceResponses),
                scope: me
            });
        }
		
        me.getView().loadRecord(me.person);
		
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
		if(value == null || value == undefined || value == 'null')
			return "";
		return value;
	},
    
    getTranscriptSuccess: function(serviceResponses) {
        var me = this;
		var view = me.getView();
		var awardsGrid = me.getFaAwardsGrid();
	    if(awardsGrid && awardsGrid.length > 0)
			awardsGrid = awardsGrid[0];
		var filesGrid = me.getFaFilesGrid();
	    if(filesGrid && filesGrid.length > 0)
			filesGrid = filesGrid[0];
			
        view.setLoading(false);
		
        var transcriptResponse = serviceResponses.successes.transcript;
		var transcript = new Ssp.model.Transcript(transcriptResponse);
		var financialAidAwards = transcript.get('financialAidAcceptedTerms');
		var storeFAAwards = awardsGrid.getStore();
		
		storeFAAwards.removeAll();
		
		if (financialAidAwards) {
			for (var i = 0; i < financialAidAwards.length; i++) {
				var faAward = Ext.create('Ssp.model.external.FinancialAidAward');
				faAward.populateFromExternalData(financialAidAwards[i]);
				storeFAAwards.add(faAward);
			}
		}
		
		var financialAidFilesStatuses = transcript.get('financialAidFiles');
		var storeStatuses = filesGrid.getStore();
		
		storeStatuses.removeAll();
		
		if(financialAidFilesStatuses){
			for(i = 0; i < financialAidFilesStatuses.length; i++){
				var fileStatus = Ext.create('Ssp.model.external.FinancialAidFileStatus');
				var status = financialAidFilesStatuses[i];
				var file = me.financialAidFilesStore.findRecord('code', status.financialFileCode, 0, false, false, true);
				fileStatus.set("code", status.financialFileCode);
				fileStatus.set("status", status.fileStatus);
				if(file){
					fileStatus.set("description", file.get('description'));
					fileStatus.set("name", file.get('name'));
				}
				fileStatus.commit();
				storeStatuses.add(fileStatus);
			}
		}
		
		var financialAid = transcript.get('financialAid');
		
		
        
        if (financialAid) {
        	me.getBalanceOwedField().setValue(Ext.util.Format.usMoney(financialAid.balanceOwed));
        	if(financialAid.financialAidGpa != null)
				me.getFinancialAidGpaField().setValue(Ext.util.Format.number(me.handleNull(financialAid.financialAidGpa), '0.00'));
			me.getFafsaDateField().setValue(Ext.util.Format.date(Ext.Date.parse(financialAid.fafsaDate, 'c'),'m/d/Y'));
        	me.getFinancialAidFileStatusField().setValue(me.handleNull(financialAid.financialAidFileStatus));
			if(financialAid.financialAidRemaining != null)
				me.getFinancialAidRemainingField().setValue(Ext.util.Format.usMoney(financialAid.financialAidRemaining));
			me.getTermsLeftField().setValue(me.handleNull(financialAid.termsLeft));
			me.getInstitutionalLoanAmountField().setValue(me.handleNull(Ext.util.Format.usMoney(financialAid.institutionalLoanAmount)));
			if(financialAid.originalLoanAmount != null)
				me.getOriginalLoanAmountField().setValue(Ext.util.Format.usMoney(financialAid.originalLoanAmount));
			me.sapStatusCode = financialAid.sapStatusCode;
			me.getSapStatusCodeField().setValue(me.handleNull(financialAid.sapStatusCode));
			me.getTermsLeftField().setValue(me.handleNull(financialAid.termsLeft));
        }
    },

    getTranscriptFailure: function() {
    	 // nothing to do
    },
    
    afterServiceHandler: function(serviceResponses){
        var me = this;
        if (serviceResponses.responseCnt >= serviceResponses.expectedResponseCnt) {
            me.getView().setLoading(false);
        }
    },
	
    destroy: function(){
        var me = this;
		
        return me.callParent(arguments);
    }
});