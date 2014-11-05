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
             {name:'termName', type: 'string'},
             {name:'isDev', type: 'boolean'},
			 {name:'orderInTerm', type: 'int'},
			 {name:'studentNotes', type: 'string'},
			 {name:'contactNotes', type: 'string'},
			 {name:'isImportant', type: 'boolean'},
			 {name:'isTranscript', type: 'boolean', defaultValue:false},
			 {name:'duplicateOfTranscript', type: 'boolean', defaultValue:false},
			 {name:'isValidInTerm',type:'boolean', defaultValue:true, convert: null},
             {name:'hasCorequisites',type:'boolean', defaultValue:true, convert: null},
             {name:'hasPrerequisites',type:'boolean', defaultValue:true, convert: null},
             {name:'invalidReasons',type:'string'},
             {name:'electiveId',type:'string'}
             ],
	constructor: function(planCourse){
					var me = this;
		        	this.callParent(arguments);
					if(planCourse){
						if(planCourse.courseTitle)
							me.set('title', planCourse.courseTitle);
							
						if(planCourse.courseCode)
							me.set('code', planCourse.courseCode);
							
						if(planCourse.courseDescription)
							me.set('description', planCourse.courseDescription);
							
						if(planCourse.isTranscript)
							me.set('isTranscript', planCourse.isTranscript);
						else
							me.set('isTranscript', false);
							
						if(planCourse.duplicateOfTranscript)
							me.set('duplicateOfTranscript', planCourse.duplicateOfTranscript);
						else
							me.set('duplicateOfTranscript', false);
							
						if(planCourse.isImportant)
							me.set('isImportant', planCourse.isImportant);	
						else
							me.set('isImportant', false);
							
						if(planCourse.isValidInTerm)
								me.set('isValidInTerm', planCourse.isValidInTerm);	
							else
								me.set('isValidInTerm', false);
						
						if(planCourse.hasCorequisites)
							me.set('hasCorequisites', planCourse.hasCorequisites);	
						else
							me.set('hasCorequisites', false);
							
						if(planCourse.hasPrerequisites)
								me.set('hasPrerequisites', planCourse.hasPrerequisites);	
							else
								me.set('hasPrerequisites', false);
							
						if(planCourse.invalidReasons)
								me.set('invalidReasons', planCourse.invalidReasons);
						if(planCourse.studentNotes)
							me.set('studentNotes', planCourse.studentNotes);	
						if(planCourse.contactNotes)
							me.set('contactNotes', planCourse.contactNotes);	
						
						if(planCourse.dev)
							me.set('isDev',  planCourse.dev);
						
					}else if(!me.get('creditHours')) {
		        		me.set('creditHours', me.get('minCreditHours'));
					}
		        },
	getBoolean: function(fieldName){
		var me = this;
		if(me.get(fieldName) == 'on' || me.get(fieldName) == true || me.get(fieldName) == 1 || me.get(fieldName) == 'true')
			return true;
		return false;
	},
	isDev: function(){
		return this.get("isDev");
	}
});