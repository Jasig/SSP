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
Ext.define('Ssp.model.CaseloadPerson', {
    extend: 'Ext.data.Model',
    fields: [{name:'personId', type: 'string'},
             {name: 'schoolId', type: 'string'},
             {name:'firstName', type: 'string'},
             {name:'lastName', type: 'string'},
             {name:'middleName', type: 'string'},
             {name: 'birthDate', type: 'date', dateFormat: 'Y-m-d'},
             {name: 'studentTypeName', type: 'string'},
             {name: 'currentAppointmentStartDate', type: 'date', dateFormat: 'time'},
             {name: 'numberOfEarlyAlerts', type: 'string'},
             {name: 'numberEarlyAlertResponsesRequired', type: 'int'},
             {name: 'studentIntakeComplete', type: 'boolean'},
             {name: 'currentAppointmentStartTime', type: 'date', dateFormat: 'time'},
             {name: 'currentProgramStatusName', type: 'string'},
             {
            	 name: 'fullName',
            	 convert: function(value, record) {
            		 return record.get('firstName') + ' '+ record.get('lastName');
            	 }
             }
             ],            
             
     getFullName: function(){ 
      	var firstName = this.get('firstName') || "";
      	var middleName = this.get('middleName') || "";
      	var lastName = this.get('lastName') || "";
      	return firstName + " " + middleName + " " + lastName;
     },
     
     getStudentTypeName: function(){
     	return ((this.get('studentTypeName') != null)? this.get('studentTypeName') : "");   	
     }
});