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
Ext.define('Ssp.model.PersonGoal', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'name',type:'string'},
             {name:'description',type:'string'},
             {name: 'confidentialityLevel',
                 convert: function(value, record) {
                	 var defaultConfidentialityLevelId = Ssp.util.Constants.DEFAULT_SYSTEM_CONFIDENTIALITY_LEVEL_ID;
                	 var obj  = {id:defaultConfidentialityLevelId,name: ''};
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
   		      }]
});