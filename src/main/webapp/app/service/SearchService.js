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
Ext.define('Ssp.service.SearchService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	store: 'directoryPersonSearchStore',
    	appEventsController: 'appEventsController'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    getBaseUrl: function(){
		var me=this;
		return me.apiProperties.createUrl( me.apiProperties.getItemUrl(me.store.getBaseUrlName()) );
    },
	searchCountWithParams: function(params, callbacks) {
		var me=this;

		// Set params in the url for Search Store
		// because the params need to be applied prior to load and not in a params
		// definition from the load method or the paging
		// toolbar applied to the SearchView will not
		// apply the params when using next or previous page
		var activeParams = {};

		for (key in params) {
		    if(params[key] && params[key] != null) {
				activeParams[key] = params[key];
			}
		}
		
		var encodedUrl = Ext.urlEncode(activeParams);
		var count;

		Ext.Ajax.request({
		    url: me.getBaseUrl()+'/count?' + encodedUrl,
            method: 'GET',
            headers: { 'Content-Type': 'application/json' },
            success: function(response, view) {
                if (callbacks != null && callbacks.success ) {
                    if ( response && response.responseText ) {
                        callbacks.success.call(callbacks.scope, Ext.decode(response.responseText));
                    } else {
                        callbacks.success.call(callbacks.scope, null);
                    }
                }
            },
            failure: function(response) {
                if (callbacks != null && callbacks.failure) {
                    callbacks.failure.call(callbacks.scope, response);
                } else {
                    this.apiProperties.handleError(response);
                }
            }
		}, this);
	},

	searchWithParams: function(params, callbacks) {
		var me=this;

		me.store.removeAll();
		me.store.currentPage = 1;

		// Set params in the url for Search Store
		// because the params need to be applied prior to load and not in a params
		// definition from the load method or the paging
		// toolbar applied to the SearchView will not
		// apply the params when using next or previous page
		var activeParams = {};
		
		if(me.store.extraParams) {
			for (key in me.store.extraParams) {
				if(me.store.extraParams[key] && me.store.extraParams[key] != null) {
					activeParams[key] = me.store.extraParams[key];
				}
			}
		}
		
		if(me.store.params){
			for (key in me.store.params) {
				if(me.store.params[key] && me.store.params[key] != null) {
					activeParams[key] = me.store.params[key];
				}
			}
		}
		
		for (key in params) {
		    if(params[key] && params[key] != null) {
				activeParams[key] = params[key];
			}
		}

		var encodedUrl = Ext.urlEncode(activeParams);
		Ext.apply(me.store.getProxy(),{url: me.getBaseUrl()+'?' + encodedUrl});

		me.store.load({
			params: {

			},
			callback: function(records, operation, success) {
				if (success) {
					if (callbacks != null) {
						callbacks.success( records, callbacks.scope );
					}
				} else {
					if (callbacks != null) {
						callbacks.failure( records, callbacks.scope );
					}
				}
			},
			scope: me
		});
	},

    search: function( searchTerm, outsideCaseload, callbacks ) {
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
             localGpaMin,
             localGpaMax,
             programGpaMin,
             programGpaMax,
    		 currentlyRegistered,
    		 earlyAlertResponseLate,
    		 sapStatusCode,
    		 planStatus,
    		 planExists,
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
            localGpaMin: localGpaMin,
            localGpaMax: localGpaMax,
            programGpaMin: programGpaMin,
            programGpaMax: programGpaMax,
            // required because false is not sent as a parameter we are depending on null to indicate no search
            currentlyRegistered: currentlyRegistered == null ? null : new Boolean(currentlyRegistered).toString(),
            earlyAlertResponseLate: earlyAlertResponseLate,
            sapStatusCode: sapStatusCode,
            planStatus: planStatus,
            planExists: planExists,
            myCaseload: myCaseload,
            myPlans: myPlans,
            myWatchList: myWatchList,
            birthDate: birthDate,
            actualStartTerm: actualStartTerm,
            personTableType: personTableType
		}, callbacks);
	}
});
