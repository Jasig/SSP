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
Ext.define('Ssp.model.reference.CampusEarlyAlertRouting', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'earlyAlertReasonId',type:'string'},
             {name:'person',type:'auto'},
             {name:'groupName',type:'string'},
             {name:'groupEmail',type:'string'},
			 {name:'earlyAlertReason',
                 convert: function(value, record) {
                	 
                	 var obj  = {id:'',name: ''};
                	 if (value != null)
                	 {
                		 if (value != "")
                		 {
                    		 obj.id  = value.id;
                    		 obj.name = value.name;                			 
                		 }
                	 }
   		            return obj;
                 }
   		      },],
             
    getPersonFullName: function(){
    	var me=this;
    	var person;
    	var fullName = "";
    	if (me.get('person') != null)
    	{
    		person = me.get('person');
    		fullName = person.firstName + ' ' + person.lastName;
    	}
    	return fullName;
    }
});