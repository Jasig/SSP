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
Ext.define('Ssp.model.SearchPerson', {
    extend: 'Ext.data.Model',
    fields: [{name:'id', type: 'string'},
             {name: 'schoolId', type: 'string'},
             {name: 'firstName', type: 'string'},
             {name: 'middleName', type: 'string'},
             {name: 'lastName', type: 'string'},
             {name: 'photoUrl', type: 'string'},
			 {name: 'coachFirstName', type: 'string'},
			 {name: 'coachLastName', type: 'string'},
             {name: 'currentProgramStatusName', type: 'string'},
             {name:'personId', type: 'string'},
             {name: 'birthDate', type: 'date', dateFormat: 'Y-m-d'},
             {name: 'studentTypeName', type: 'string'},
             {name: 'currentAppointmentStartDate', type: 'date', dateFormat: 'time'},
             {name: 'numberOfEarlyAlerts', type: 'string'},
             {name: 'numberEarlyAlertResponsesRequired', type: 'int'},
             {name: 'studentIntakeComplete', type: 'boolean'},
             {name: 'currentAppointmentStartTime', type: 'date', dateFormat: 'time'},
             {name: 'currentProgramStatusName', type: 'string'},

			 {name:'coachId', type: 'string'},
             {name: 'coach', type: 'auto'},
             {
                 name: 'sortableName',
                 convert: function(value, record) {
                     return record.get('lastName') + ' '+ record.get('firstName');
                 }
             }],

     getFullName: function(){ 
    	var me=this;
     	var firstName = me.get('firstName')? me.get('firstName') : "";
     	var middleName = me.get('middleName')? me.get('middleName') : "";
     	var lastName = me.get('lastName')? me.get('lastName') : "";
     	return firstName + " " + middleName + " " + lastName;
     },
     
     getCoachFullName: function(){
    	var me=this;
		var firstName = me.get('coachFirstName');
		var lastName = me.get('coachLastName');
		if( me.get('coach')){
      		firstName = me.get('coach')? me.get('coach').firstName : "";
      		astName = me.get('coach')? me.get('coach').lastName : "";
		}
      	return firstName + " " + lastName;
     },
     
     getStudentTypeName: function(){
      	return ((this.get('studentTypeName') != null)? this.get('studentTypeName') : "");   	
      }
});