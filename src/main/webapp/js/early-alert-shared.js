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
"use strict";
var ssp = ssp || {};

(function($, fluid) {

    ssp.getConfigValueByName = function(name, options) {
        var rslt = null;
        $.ajax({
            url: options.urls.configByName.replace('CONFIGNAME', options.statusMappingConfigName),
            async: false,
            dataType: 'json',
            error: function(jqXHR, textStatus, errorThrown) {
                // nothing to do
            },
            success: function(data, textStatus, jqXHR) {
                rslt = data && ((data.value === null || data.value === undefined) ? data.defaultValue: data.value);
            },
            type: 'GET'
        });
        return rslt;
    };

    ssp.getStatusCodeMappings = function(options) {
        if ( ssp.statusCodeMappings ) {
            return ssp.statusCodeMappings;
        }
        var rslt;
        $.ajax({
            url: options.urls.enrollmentStatus,
            async: false,
            dataType: 'json',
            error: function(jqXHR, textStatus, errorThrown) {
                // nothing to do
            },
            success: function(data, textStatus, jqXHR) {
            	rslt = data.rows;
            },
            type: 'GET'
        });        
        ssp.statusCodeMappings = rslt;
        return ssp.statusCodeMappings;
    };

    ssp.getStatusCodeName = function(code, options) {
        var mappings = ssp.getStatusCodeMappings(options);
        var defaultMapping;
        if ( !(mappings) ) {
        	return code;
        }
        
        for (var i = 0; i < mappings.length; i++)
        {
        	if(mappings[i].code === 'default')
        	{
        		defaultMapping = mappings[i];
        	}
        	if(mappings[i].code === code)
        	{
        		return mappings[i].name;
        	}
        }
        
        if ( code === "" || code === null || code === undefined ) {
            return ( defaultMapping ) ? defaultMapping.name : code;
        }
        return code;
    };

})(jQuery, fluid);