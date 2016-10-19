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
Ext.define('Ssp.service.BackgroundJobService', {
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
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('executebackgroundjob') );

    	return baseUrl;
    },
    
    runBackgroundJob: function( backgroundJobName, callbacks ){
		var me = this;
		var url = me.getBaseUrl() + '/' + backgroundJobName;

        var success = function( response ) {
            if ( !Ext.isEmpty(response.responseText) && callbacks != null ) {
                callbacks.success( Ext.decode(response.responseText), callbacks.scope );
            } else {
                failure(response);
            }
        };

        var failure = function( response ){
            if ( callbacks != null ) {
                callbacks.failure(response, callbacks.scope);
            } else {
                return response;
            }
        };

        me.apiProperties.makeRequest({
            url: url,
            method: 'GET',
            successFunc: success,
            failureFunc: failure,
            scope: me
        });
    }
});