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
Ext.define('Ssp.model.external.Course', {
    extend: 'Ssp.model.external.AbstractExternal',
    fields: [{name: 'code', type: 'string'},
             {name: 'formattedCourse', type: 'string'},
             {name: 'subjectAbbreviation', type: 'string'},
             {name: 'number', type: 'string'},
             {name: 'title', type: 'string'},
             {name: 'departmentCode', type: 'string'},
             {name: 'divisionCode', type: 'string'},
             {name: 'academicLink', type: 'string'},
             {name: 'tags', type: 'string'},
             {name: 'masterSyllabusLink', type: 'string'},             
             {name: 'description', type: 'string'},
             {name: 'maxCreditHours', type: 'float'},
             {name: 'minCreditHours', type: 'float'},
             {name: 'isDev', type: 'string'}
             ],
	isDev: function(){
			var me = this;
			if(me.get("isDev") == 'Y') return true;	
			if(me.get("isDev") == 'y') return true;	
			if(me.get("isDev") == true) return true;
			if(me.get("isDev") == "true") return true;	
			return false;
	}
});