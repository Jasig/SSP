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
Ext.define('Ssp.service.ExportService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	store: 'directoryPersonSearchStore',
        authenticatedPerson: 'authenticatedPerson'

    },
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    getBaseUrl: function(searchType){
    	var me = this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('exportableCaseload') );
		baseUrl = baseUrl + '/' + searchType;
    	return baseUrl;
    },
    buildExportSearchUrl: function(params) {
        var me=this;

        var activeParams = {};

        for (key in params) {
            if(params[key] && params[key] != null){
                activeParams[key] = params[key];
            }
        }

        var url = me.getBaseUrl('search');
        var encodedUrl = Ext.urlEncode(activeParams);
        return url+'?'+encodedUrl;
    },

    buildExportCaseloadUrl: function( programStatusId, searchType, pagination){
        var me=this;
        var url = me.getBaseUrl(searchType);
        var activeParams = {};

        if(programStatusId)
        {
            activeParams['programStatusId'] = programStatusId;
        }
        activeParams['status'] = 'ACTIVE';
        if ( pagination ) {
            for ( i in pagination ) {
                activeParams[i] = pagination[i];
            }
        }

        var encodedUrl = Ext.urlEncode(activeParams);
        return url+'?'+encodedUrl;
    }
});