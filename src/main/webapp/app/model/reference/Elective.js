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
Ext.define('Ssp.model.reference.Elective', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: [{name: 'code', type: 'string'},
             {name: 'sortOrder', type: 'integer'},

             // Note that these convert() functions set up below are called
             // during object init, deserialization, *and* as a side-effect of
             // record.set('...','...'). Docs at
             // http://docs.sencha.com/extjs/4.1.3/#!/api/Ext.data.Field-cfg-convert
             // (worth drilling into the comments, esp from 'davydotcom'

             {name: 'objectStatus', type: 'string', defaultValue: 'ACTIVE', convert: function(value, record){
                 // 'objectStatus' is part of the back-end API, so handling is
                 // similar but slightly different than 'active' b/c we do
                 // trust 'value' during both initialization and field-to-field
                 // syncs.
                 if ( !(record.statusFieldsInitialized) || record.synchronizingStatusFields ) {
                     // Don't worry about updating active field if this field
                     // is changing b/c the object is being
                     // initialized/deserialized - it will calculate the
                     // correct value for itself as part of that process.
                     //
                     // *Ran into problems  calling record.set() on an instance unbound
                     // to a store will blow up.
                     // blows up b/c there is no store currently assigned. So
                     // definitely want to avoid that. The result is some
                     // fragile code b/c there's no rule that says you
                     return value;
                 }
                 record.synchronizingStatusFields = true;
                 record.set('active', 'ACTIVE' === (value && value.toUpperCase()));
                 record.synchronizingStatusFields = false;
                 return value;
             }},

             // The 'persist' flag here doesn't do any good b/c we don't actually
             // use a proper Ext.data.writer.Writer when sending these things
             // back to the server (see AbstractReferenceAdminViewController).
             // But it still accurately expresses our semantics. This is really
             // a calculated field that exists just to make binding objectStatus
             // to a checkbox a little easier.
             {name: 'active', type: 'boolean', persist: false, convert: function(value, record){
                 if ( !(record.statusFieldsInitialized) ) {
                     // must be during initialization so don't worry about
                     // updating objectStatus. 'active' is a calculated field
                     // when being read from the back end, so ignore 'value'
                     // and derive our state from objectStatus
                     //
                     // Ext.js will guarantee this field is initialized/
                     // deserialized *after* objectStatus.
                     record.statusFieldsInitialized = true;
                     return 'ACTIVE' === record.get('objectStatus');
                 }

                 if ( record.synchronizingStatusFields ) {
                     return value;
                 }

                 // else sync in the other direction
                 record.synchronizingStatusFields = true;
                 record.set('objectStatus', !(!value) ? 'ACTIVE' : 'INACTIVE');
                 record.synchronizingStatusFields = false;
                 return value;
             }}]

});