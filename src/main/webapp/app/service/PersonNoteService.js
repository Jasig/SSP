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
Ext.define('Ssp.service.PersonNoteService', {  
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
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personNote') );
    	baseUrl = baseUrl.replace('{id}', id);
    	return baseUrl;
    },

    getPersonNotes: function ( id, callbacks ) {
        var me = this;
        me.doGet(id, callbacks,  me.getBaseUrl( id ) );
    },

    doGet: function( personId, callbacks, url ) {
        var me=this;
        var success = function( response ){
            callbacks.success( Ext.decode(response.responseText), callbacks.scope );
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