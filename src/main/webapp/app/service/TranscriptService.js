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
Ext.define('Ssp.service.TranscriptService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },

    getBaseUrl: function( id ){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personTranscript') );
    	baseUrl = baseUrl.replace('{id}', id);
    	return baseUrl;
    },

    getSummary: function ( personId, callbacks ) {
        var me = this;
        me.doGet(personId, callbacks,  me.getBaseUrl( personId ) + "/summary" );
    },
    
    getFull: function( personId, callbacks ){
        var me = this;
		me.doGet(personId, callbacks,  me.getBaseUrl( personId ) + "/full" );
    },
    
    getTerm: function( personId, callbacks ){
        var me = this;
		me.doGet(personId, callbacks,  me.getBaseUrl( personId ) + "/term" );
    },
    
    getCurrentCourses: function( personId, callbacks ){
        var me = this;
		me.doGet(personId, callbacks,  me.getBaseUrl( personId ) + "/currentcourses" );
    },

    doGet: function( personId, callbacks, url ) {
        var me=this;
        var success = function( response ){
            var r = Ext.decode(response.responseText);
            callbacks.success( r, callbacks.scope );
        };

        var failure = function( response ){
            me.apiProperties.handleError( response );
            callbacks.failure( response, callbacks.scope );
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