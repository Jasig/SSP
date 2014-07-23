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
Ext.define('Ssp.service.CaseloadProgramStatusService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	store: 'caseloadProgramStatusesStore'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    getBaseUrl: function(){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('programStatus') );
    	return baseUrl;
    },

    getAll: function( callbacks ){
    	var me=this;
    	var success = function( response, view ){
    		var r = Ext.decode(response.responseText);
    		if (r.rows.length > 0)
	    	{
    		    me.store.removeAll();
    		    var all = Ext.create('Ssp.model.reference.ProgramStatus', {
    		    	name: 'All',
    		    	description: 'All',
    		    	id: 'All'
    		    });
    			me.store.loadData(r.rows);
    			me.store.insert(0,all);
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
			url: me.getBaseUrl(),
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});
    }
});