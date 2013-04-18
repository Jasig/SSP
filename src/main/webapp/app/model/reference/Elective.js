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
             {name: 'objectStatus', type: 'string', convert: function(value, record){
                 if ( !(record.isSettingActiveField) ) {
                     record.isSettingObjectStatusField = true;
                     record.set('active', 'ACTIVE' === (value && value.toUpperCase()));
                     record.isSettingObjectStatusField = false;
                 }
                 return value;
             }},
             // The 'persist' flag here doesn't do any good b/c we don't actually
             // use a proper Ext.data.writer.Writer when sending these things
             // back to the server (see AbstractReferenceAdminViewController).
             // But it still accurately expresses our semantics. This is really
             // a calculated field that exists just to make binding objectStatus
             // to a checkbox a little easier.
             //
             //
             // We want objectStatus and active fields to stay in sync. The
             // good news is the 'convert' functions are called both during
             // initial model deserialization and as a side-effect of
             // the field setter. The bad news is we have to write logic to
             // avoid overflowing the stack when trying to cascade an update
             // from one field into another. Hence all this nasty "isSetting..."
             // business
             {name: 'active', type: 'boolean', persist: false, convert: function(value, record){
                 if ( !(record.isSettingObjectStatusField) ) {
                     record.isSettingActiveField = true;
                     record.set('objectStatus', !(!value) ? 'ACTIVE' : 'INACTIVE');
                     record.isSettingActiveField = false;
                 }
                 return value;
             }}]
});