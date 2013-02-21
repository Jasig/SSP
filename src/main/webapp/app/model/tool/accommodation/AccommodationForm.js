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
Ext.define('Ssp.model.tool.accommodation.AccommodationForm', {
	extend: 'Ext.data.Model',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties'
    }, 

	fields: [{name: 'person', 
		      convert: function(value, record) {
		            var person  = Ext.create('Ssp.model.Person',{});
		            person.populateFromGenericObject( value );		
		            return person;
		      	}
             },
              {name: 'personDisability', 
   		      convert: function(value, record) {
		            var personDisability = Ext.create('Ssp.model.tool.accommodation.PersonDisability',{});
		            personDisability.populateFromGenericObject( value );
		            if (value !== null)
		            {
		            	personDisability.set('odsRegistrationDate',value.createdDate);
		            }
		            return personDisability;
		      	}
             },
             'personDisabilityAgencies',
             'personDisabilityAccommodations',
             'personDisabilityTypes',
             'referenceData'],

	autoLoad: false,
 	proxy: {
		type: 'rest',
		url: '/ssp/api/1/tool/accommodation/',
		actionMethods: {
			create: "POST", 
			read: "GET", 
			update: "PUT",
			destroy: "DELETE"
		},
		reader: {
			type: 'json'
		},
	    writer: {
	        type: 'json',
	        successProperty: 'success'
	    }
	}
});