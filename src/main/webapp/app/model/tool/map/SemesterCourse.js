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
Ext.define('Ssp.model.tool.map.SemesterCourse', {
	extend: 'Ssp.model.AbstractBase',
    fields: [{name:'title', type: 'string'},
			 {name:'code', type: 'string'},
			 {name:'formattedCourse', type: 'string'},
			 {name:'description', type: 'string'},
             {name:'minCreditHours', type: 'float'},
			 {name:'maxCreditHours', type: 'float'},
			 {name:'creditHours', type: 'float'},
             {name:'termCode', type: 'string'},
             {name:'isDev', type: 'boolean'}
             ],
	constructor: function(planCourse){
					var me = this;
		        	this.callParent(arguments);
					if(planCourse){
						if( planCourse.courseTitle)
							me.set('title', planCourse.courseTitle);
						if(planCourse.courseCode)
							me.set('code', planCourse.courseCode);
						if(planCourse.courseDescription)
							me.set('description', planCourse.courseDescription);
						if(!planCourse.minCeditHours  && planCourse.creditHours){
							me.set('minCreditHours', planCourse.creditHours <= 2 ? 0 :  planCourse.creditHours - 2);
							me.set('maxCreditHours', planCourse.creditHours + 2);
						}
						if(planCourse.dev)
							me.set('isDev',  planCourse.dev);
					}else if(!me.get('creditHours')) {
		        		me.set('creditHours', me.get('minCreditHours'));
					}
		        }
});