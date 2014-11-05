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
Ext.define('Ssp.model.BulkEmailStudentRequest', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'confidentialityLevelId', type: 'string'},
        {name: 'createJournalEntry', type: 'boolean'},
        {name: 'sendToPrimaryEmail', type: 'boolean'},
        {name: 'sendToSecondaryEmail', type: 'boolean'},
        {name: 'sendToAdditionalEmail', type: 'boolean'},
        {name: 'additionalEmail', type: 'string'},
        {name: 'emailSubject', type: 'string'},
        {name: 'emailBody', type: 'string'},
        {name: 'criteria', type: 'auto'}
    ],
    constructor: function(){
        this.callParent(arguments);
    }
});