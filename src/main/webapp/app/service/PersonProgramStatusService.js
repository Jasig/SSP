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
Ext.define('Ssp.service.PersonProgramStatusService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    getBaseUrl: function( personId ){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personProgramStatus') );
    	baseUrl = baseUrl.replace("{id}",personId);
		return baseUrl;
    },
	
	getCurrentProgramStatus: function(personId, callbacks){
		var me=this;
		var url = me.getBaseUrl( personId );
       
		var success = function( response, view ){
            if ( response && response.responseText ) {
	    	    var r = Ext.decode(response.responseText);
    		    callbacks.success( r, callbacks.scope );
            } else {
                callbacks.success(null, callbacks.scope)
            }
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
		me.apiProperties.makeRequest({
			url: url + "/current",
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});  
	},

    save: function( personId, jsonData, callbacks ){
		var me=this;
		var url = me.getBaseUrl( personId );
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
	    	callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
		
    	if (personId != "")
    	{
    		id = jsonData.id;

    		// save the program status
    		if (id=="")
    		{
    			// Fix dates for GMT offset to UTC
    			jsonData.effectiveDate = me.formUtils.fixDateOffset( jsonData.effectiveDate );
	
    			me.apiProperties.makeRequest({
        			url: url,
        			method: 'POST',
        			jsonData: jsonData,
        			successFunc: success,
        			failureFunc: failure,
        			scope: me
        		});		
    		}else{
    			
    			// update
        		me.apiProperties.makeRequest({
        			url: url+"/"+id,
        			method: 'PUT',
        			jsonData: jsonData,
        			successFunc: success,
        			failureFunc: failure,
        			scope: me
        		});	
    		}     		
    	}else{
    		Ext.Msg.alert('SSP Error', 'Error determining student to which to save a Program Status. Unable to save Program Status. See your system administrator for assistance.');
    	}  	
    }   
});