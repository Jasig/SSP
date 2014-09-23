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
Ext.define('Ssp.model.Preferences', {
    extend: 'Ext.data.Model',
    fields: [{name:'SEARCH_GRID_VIEW_TYPE',type:'int', defaultValue:1}, // 0 display search, 1 display caseload
    		 {name:'ACTION_PLAN_ACTIVE_VIEW',type:'int',defaultValue:0}, // 0 for Tasks, 1 for Goals
    		 {name:'EARLY_ALERT_SELECTED_NODE',type:'auto', defaultValue:""}, // 0 for Tasks, 1 for Goals
    		 {name:'SEARCH_VIEW_SIZE',type:'string', defaultValue:""}
			 ]
});