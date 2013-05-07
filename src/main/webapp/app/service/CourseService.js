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
Ext.define('Ssp.service.CourseService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },

    getBaseUrl: function(  ){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('course') );
    	return baseUrl;
    },
    
    getProgramUrl: function(  ){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('program') );
    	return baseUrl;
    },
    
    getTagUrl: function(  ){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('tag') );
    	return baseUrl;
    },
    
    getDepartmentUrl: function(  ){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('department') );
    	return baseUrl;
    },
    
    getDivisionUrl: function(  ){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('division') );
    	return baseUrl;
    },

    validateCourse: function ( courseCode, termCode, callbacks ) {
        var me = this;
        var params = [];
        params['courseCode'] = courseCode;
        params['termCode'] = termCode;
        me.doGet(callbacks,  me.getBaseUrl(  ) + "/validateTerm", params);
    },
    
    getFacetedTags: function ( params, callbacks ) {
        var me = this;
        me.doGet(callbacks,  me.getTagUrl(  ), params);
    },
    
    getFacetedDepartments: function ( params, callbacks ) {
        var me = this;
        me.doGet(callbacks,  me.getDepartmentUrl(  ), params);
    },
    
    getFacetedPrograms: function ( params, callbacks ) {
        var me = this;
        me.doGet(callbacks,  me.getProgramtUrl(  ), params);
    },
    
    getFacetedDivisions: function ( params, callbacks ) {
        var me = this;
        me.doGet(callbacks,  me.getDivisiontUrl(  ), params);
    },

    doGet: function( callbacks, url, params ) {
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
            params: params,
            successFunc: success,
            failureFunc: failure,
            scope: me
        });
    }
});