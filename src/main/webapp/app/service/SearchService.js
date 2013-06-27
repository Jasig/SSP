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
    	store: 'searchStore'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    getBaseUrl: function(){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personSearch') );
    	return baseUrl;
    },

	searchWithParams: function(params, callbacks) {
		var me=this;

		me.store.removeAll();

		// Set params in the url for Search Store
		// because the params need to be applied prior to load and not in a params
		// definition from the load method or the paging
		// toolbar applied to the SearchView will not
		// apply the params when using next or previous page

		queryStr = "";
		for (var paramName in params) {
			// TODO url encoding?
			if ( queryStr ) {
				queryStr += "&";
			}
			if(params[paramName] || !params[paramName] == '')
			{
				queryStr += paramName + "=" + params[paramName];
			}
		}
		if ( !("sort" in params) ) {
			if ( queryStr ) {
				queryStr += "&";
			}
			queryStr += "sort=lastName";
		}

		Ext.apply(me.store.getProxy(),{url: me.getBaseUrl()+'?'+queryStr});

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
    		 studentId,
    		 programStatus,
    		 coachId,
    		 declaredMajor,
    		 hoursEarnedMin,
    		 hoursEarnedMax,
    		 gpaEarnedMin,
    		 gpaEarnedMax,
    		 currentlyRegistered,
    		 sapStatus,
    		 mapStatus,
    		 planStatus,
    		 myCaseload,
    		callbacks ){
    	var me = this;
		me.searchWithParams({
   		 studentId: studentId,
		 programStatus: programStatus,
		 coachId: coachId,
		 declaredMajor: declaredMajor,
		 hoursEarnedMin: hoursEarnedMin,
		 hoursEarnedMax: hoursEarnedMax,
		 gpaEarnedMin: gpaEarnedMin,
		 gpaEarnedMax: gpaEarnedMax,
		 currentlyRegistered: currentlyRegistered,
		 sapStatus: sapStatus,
		 mapStatus: mapStatus,
		 planStatus: planStatus,
		 myCaseload: myCaseload
		}, callbacks);
    }
});