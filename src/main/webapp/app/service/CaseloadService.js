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
Ext.define('Ssp.service.CaseloadService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    getBaseUrl: function(){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personCaseload') );
    	return baseUrl;
    },
    
    getBaseUrlForIdCaseload: function(id){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personCaseloadId') );
		baseUrl = baseUrl.replace('{id}',id);
    	return baseUrl;
    },    

    getCaseload: function( programStatusId, store, callbacks ){
    	var me=this;
	    
		// clear the store
		store.removeAll();

		// Set the Url for the Caseload Store
		// including param definitions because the params need
		// to be applied prior to load and not in a params 
		// definition from the load method or the paging
		// toolbar applied to the SearchView will not
		// apply the params when using next or previous
		// page
		Ext.apply(store.getProxy(),{url: me.getBaseUrl()+'?programStatusId='+programStatusId+'&status=ACTIVE'});

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
    
    getCaseloadById: function( personId, store, callbacks ){
    	var me=this;
    	var success = function( response, view ){
    		var r = Ext.decode(response.responseText);
    		store.removeAll();
	    	if (r.rows.length > 0)
	    	{
	    		store.loadData(r.rows);
	    	}
	    	if (callbacks != null)
	    	{
	    		callbacks.success( r, callbacks.scope );
	    	}	
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	if (callbacks != null)
	    	{
	    		callbacks.failure( response, callbacks.scope );
	    	}
	    };
	    
		me.apiProperties.makeRequest({
			url:me.getBaseUrlForIdCaseload(personId),
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});
    }    
});