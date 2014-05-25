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
Ext.define('Ssp.service.SearchService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	storeOld: 'studentsSearchStore',
    	store: 'directoryPersonSearchStore'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    getBaseUrl: function(){
		var me=this;
		return me.apiProperties.createUrl( me.apiProperties.getItemUrl(me.store.getBaseUrlName()) );
    },

	searchWithParams: function(params, callbacks) {
		var me=this;

		me.store.removeAll();
		me.store.currentPage = 1;
		me.store.pageSize = 100;

		// Set params in the url for Search Store
		// because the params need to be applied prior to load and not in a params
		// definition from the load method or the paging
		// toolbar applied to the SearchView will not
		// apply the params when using next or previous page
		var activeParams = {};
	    var birthDate = "";
		for (key in params) {
			if(key != "birthDate" ){
		    	if(params[key] && params[key] != null){
					activeParams[key] = params[key] 
				}
			}else{
				if(params[key] && params[key] != null){
					birthDate = "birthDate=" + params[key] + "&";
				}
			}
		}
		
		var encodedUrl = Ext.urlEncode(activeParams);

		Ext.apply(me.store.getProxy(),{url: me.getBaseUrl()+'?'+ birthDate +encodedUrl});

		me.store.load({
			params: {

			},
			callback: function(records, operation, success) {
				if (success)
				{
					if (callbacks != null)
					{
						callbacks.success( records, callbacks.scope );
					}
				}else{
					if (callbacks != null)
					{
						callbacks.failure( records, callbacks.scope );
					}
				}
			},
			scope: me
		});
	},

    search: function( searchTerm, outsideCaseload, callbacks ){
    	var me = this;
		me.searchWithParams({
			searchTerm: searchTerm,
			outsideCaseload: outsideCaseload
		}, callbacks);
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
    		 birthDate,
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
		 birthDate: birthDate,
		 personTableType: personTableType
		}, callbacks);
    }
});