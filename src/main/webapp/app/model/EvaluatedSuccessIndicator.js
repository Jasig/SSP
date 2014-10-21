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
Ext.define('Ssp.model.EvaluatedSuccessIndicator', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'id', type: 'string'},
        {name: 'personId', type: 'string'},
        {name: 'indicatorId', type: 'string'},
        {name: 'indicatorName', type: 'string'},
        {name: 'indicatorDescription', type: 'string'},
        {name: 'indicatorCode', type: 'string'},
        {name: 'indicatorModelName', type: 'string'},
        {name: 'indicatorModelCode', type: 'string'},
        {name: 'indicatorGroupCode', type: 'string'},
        {name: 'indicatorSortOrder', type: 'int'},
        {name: 'objectStatus', type: 'string'},
        {name: 'displayValue', type: 'string'},
        {name: 'evaluation', type: 'string'}
    ]
});