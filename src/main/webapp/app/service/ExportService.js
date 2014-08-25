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
Ext.define('Ssp.service.ExportService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	store: 'directoryPersonSearchStore',
        authenticatedPerson: 'authenticatedPerson'

    },
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    getBaseUrl: function(action){
    	var me = this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('exportableCaseload') );
		baseUrl = baseUrl + '/' + action;
    	return baseUrl;
    },
	searchWithParams: function(params, callbacks) {
		var me=this;

		var activeParams = {};
		
		for (key in params) {
		    if(params[key] && params[key] != null){
				activeParams[key] = params[key];
			}
		}

		
		var url = me.getBaseUrl('search');
		var encodedUrl = Ext.urlEncode(activeParams);
    	window.open(url+'?'+encodedUrl,'_self');
		
	},

    exportCaseload: function( programStatusId, action,callbacks ){
    	var me=this;
		var url = me.getBaseUrl(action);
		var activeParams = {};

		if(programStatusId)
		{
			activeParams['programStatusId'] = programStatusId;
		}
		activeParams['status'] = 'ACTIVE';
		
		var encodedUrl = Ext.urlEncode(activeParams);
    	window.open(url+'?'+encodedUrl,'_self');

    },
    search2: function( 
   		 schoolId,
   		 firstName,
   		 lastName,
   		 programStatus,
   		 specialServiceGroup,
   		 coachId,
   		 declaredMajor,
   		 hoursEarnedMin,
   		 hoursEarnedMax,
   		 gpaEarnedMin,
   		 gpaEarnedMax,
   		 currentlyRegistered,
   		 earlyAlertResponseLate,
   		 sapStatusCode,
   		 mapStatus,
   		 planStatus,
   		 myCaseload,
   		 myPlans,
   		 myWatchList,
   		 birthDate,
   		 actualStartTerm,
   		 personTableType,
   		callbacks ){
   	var me = this;
   	
		me.searchWithParams({
	     schoolId: schoolId,
	     firstName: firstName,
	     lastName: lastName,
		 programStatus: programStatus,
		 specialServiceGroup: specialServiceGroup,
		 coachId: coachId,
		 declaredMajor: declaredMajor,
		 hoursEarnedMin: hoursEarnedMin,
		 hoursEarnedMax: hoursEarnedMax,
		 gpaEarnedMin: gpaEarnedMin,
		 gpaEarnedMax: gpaEarnedMax,
		 // required because false is not sent as a parameter we are depending on null to indicate no search
		 currentlyRegistered: currentlyRegistered == null ? null : new Boolean(currentlyRegistered).toString(),
		 earlyAlertResponseLate: earlyAlertResponseLate,
		 sapStatusCode: sapStatusCode,
		 mapStatus: mapStatus,
		 planStatus: planStatus,
		 myCaseload: myCaseload,
		 myPlans: myPlans,
		 myWatchList: myWatchList,
		 birthDate: birthDate,
		 actualStartTerm: actualStartTerm,
		 personTableType: personTableType
		}, callbacks);
   }
});