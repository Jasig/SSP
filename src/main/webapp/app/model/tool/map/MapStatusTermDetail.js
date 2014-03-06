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
Ext.define('Ssp.model.tool.map.MapStatusTermDetail', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'termCode',type:'string'},
            {name:'termRatio',type:'string'},
            {name:'anomalyNote',type:'string'},
            {name:'anomalyCode',type:'string'}
	],
	
	clearMapPlan:function(){
		var me = this;
		me.clearPlanCourses();
		me.clearTermNotes();
		me.set('ownerId','');
		me.set('personId','');
		me.set('name','');
		me.set('id','');
		me.set('createdBy','');
		me.set('modifiedBy','');
		me.set('contactTitle','');
		me.set('contactPhone','');
		me.set('contactEmail','');
		me.set('contactName','');
		me.set('contactNotes','');
		me.set('studentNotes','');
		//me.set('basedOnTemplateId','');
		me.set('isFinancialAid',false);
		me.set('isImportant',false);
		me.set('isF1Visa',false);
		me.set('academicGoals','');
		me.set('careerLink','');
		me.set('academicLink','');
		me.set('departmentCode','');
		me.set('divisionCode','');
		me.set('isPrivate',false);
		me.set('visibility','AUTHENTICATED');
		me.set('programCode','');
		me.set('createdDate',null);
		me.set('modifiedDate',null);
		me.set('isValid',true);
		me.set('isTemplate',false);
		me.dirty = false;
	}
			

});