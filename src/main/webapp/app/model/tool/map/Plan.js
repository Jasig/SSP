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
Ext.define('Ssp.model.tool.map.Plan', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'name',type:'string'},
             {name:'ownerId',type:'string'},
             {name:'ownerName',type:'string'},
             {name:'personId', type:'string'},
             {name: 'objectStatus', type: 'string'},
             {name: 'modifiedDate', type: 'date', dateFormat: 'time'},             
             {name:'planCourses',
       		  type:'auto',
       		  convert: function(data,model)
	       		  {
	       			  data = (data && !Ext.isArray(data) ) ? [data] : data;
	       			  return data;
	       		  }
              }             
             ],
    hasMany: {model: 'Ssp.model.tool.map.PlanCourse',
    		  name: 'planCourses',
    		  associationKey: 'planCourses'},

	clearPlanCourses:function(){
				var me = this;
				var currentCourses =  me.get('planCourses');
				while(currentCourses.length > 0) {
				    currentCourses.pop(); 
				}
			},
	
	clearMapPlan:function(){
				var me = this;
				me.clearPlanCourses();
				me.set('ownerId','');
				me.set('personId','');
				me.set('name','');
				me.set('id','');
				me.set('createdBy','');
				me.set('modifiedBy','');
				me.set('createdDate',null);
				me.set('modifiedDate',null);
			}
    		        		  
});