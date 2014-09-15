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
Ext.define('Ssp.service.WatchListService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson',
    	appEventsController: 'appEventsController'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    getBaseUrl: function(){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personWatch') );
    	return baseUrl.replace('{id}',me.authenticatedPerson.get('id'));

    },
    getProgramAndStatusAgnosticWatchList: function( store, callbacks ){
    	var me=this;
	    
		// clear the store
		store.removeAll();

		Ext.apply(store.getProxy(),{url: me.getBaseUrl()});

	    store.load({
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
    getWatchList: function( programStatusId, store, callbacks ){
    	var me=this;
	    
		store.removeAll();
		store.currentPage = 1;
		var activeParams = {};
		if(store.params){
			for (key in store.params) {
				if(store.params[key] && store.params[key] != null){
					activeParams[key] = store.params[key];
				}
			}
		}
		
		if(store.params){
			for (key in store.params) {
				if(store.params[key] && store.params[key] != null){
					activeParams[key] = store.params[key];
				}
			}
		}
		
		if(store.extraParams){
			for (key in store.extraParams) {
				if(store.extraParams[key] && store.extraParams[key] != null){
					activeParams[key] = store.extraParams[key];
				}
			}
		}
		
		// Set the Url for the Caseload Store
		// including param definitions because the params need
		// to be applied prior to load and not in a params 
		// definition from the load method or the paging
		// toolbar applied to the SearchView will not
		// apply the params when using next or previous
		// page
		var url;
		if(programStatusId)
		{
			activeParams['programStatusId'] = programStatusId;
		}
		
		activeParams['status'] = 'ACTIVE';
		
		var encodedUrl = Ext.urlEncode(activeParams);
		
		Ext.apply(store.getProxy(),{url: me.getBaseUrl()+'?' + encodedUrl});

	    store.load({
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
    getWatchlistCount: function( programStatusId, callbacks ){
    	var me=this;
	    
		var activeParams = {};
		
		// Set the Url for the Caseload Store
		// including param definitions because the params need
		// to be applied prior to load and not in a params 
		// definition from the load method or the paging
		// toolbar applied to the SearchView will not
		// apply the params when using next or previous
		// page
		var url;
		if(programStatusId)
		{
			activeParams['programStatusId'] = programStatusId;
		}
		
		activeParams['status'] = 'ACTIVE';
		
		var encodedUrl = Ext.urlEncode(activeParams);
		var count;

		 Ext.Ajax.request({
				url: me.getBaseUrl()+'/count?' + encodedUrl,
				method: 'GET',
				headers: { 'Content-Type': 'application/json' },
				success: function(response, view) {
					count = response.responseText;
			        var skipCallBack = me.appEventsController.getApplication().fireEvent('exportCaseload','watchlist', count);
				},
				failure: this.apiProperties.handleError
			}, this);
    }
});