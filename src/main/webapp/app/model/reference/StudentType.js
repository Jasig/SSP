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
Ext.define('Ssp.model.reference.StudentType', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: [
        {name:'requireInitialAppointment',type:'boolean'},
        // Elective.js or Color.js for documentation about Active/Inactive data loading
	    {name: 'objectStatus', type: 'string', defaultValue: 'ACTIVE', convert: function(value, record){
	        if ( !(record.statusFieldsInitialized) || record.synchronizingStatusFields ) {
	            return value;
	        }
	        record.synchronizingStatusFields = true;
	        record.set('active', 'ACTIVE' === (value && value.toUpperCase()));
	        record.synchronizingStatusFields = false;
	        return value;
	    }},
	    {name: 'active', type: 'boolean', persist: false, convert: function(value, record){
	        if ( !(record.statusFieldsInitialized) ) {
	            record.statusFieldsInitialized = true;
	            return 'ACTIVE' === record.get('objectStatus');
	        }
	
	        if ( record.synchronizingStatusFields ) {
	            return value;
	        }
	        record.synchronizingStatusFields = true;
	        record.set('objectStatus', !(!value) ? 'ACTIVE' : 'INACTIVE');
	        record.synchronizingStatusFields = false;
	        return value;
	    }}]
});