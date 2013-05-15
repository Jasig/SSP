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
             {name:'contactTitle',type:'string'},
             {name:'contactPhone',type:'string'},
             {name:'contactEmail',type:'string'},
             {name:'contactName',type:'string'},
             {name:'contactNotes',type:'string'},
             {name:'studentNotes',type:'string'},
             //{name:'basedOnTemplateId',type:'string'},
             {name:'isFinancialAid',type:'boolean'},
             {name:'isImportant',type:'boolean'},
             {name:'isF1Visa',type:'boolean'},
             {name:'academicGoals',type:'string'},
             {name:'careerLink',type:'string'},
             {name:'academicLink',type:'string'},
             {name:'personId', type:'string'},
             {name: 'objectStatus', type: 'string'},
             {name: 'isTemplate', type: 'boolean'},
			 {name:'departmentCode',type:'string'},
			  {name:'programCode',type:'string'},
			  {name:'divisionCode',type:'string'},
			 {name:'isPrivate',type:'boolean'},
             {name: 'modifiedDate', type: 'date', dateFormat: 'time'},
             
             {name:'planCourses',
       		  type:'auto',
       		  convert: function(data,model)
	       		  {
	       			  data = (data && !Ext.isArray(data) ) ? [data] : data;
	       			  return data;
	       		  }
              },

			{name:'templateCourses',
       		  type:'auto',
       		  convert: function(data,model)
	       		  {
	       			  data = (data && !Ext.isArray(data) ) ? [data] : data;
	       			  return data;
	       		  }
              },
              {name:'termNotes',
           		  type:'auto',
           		  convert: function(data,model)
    	       		  {
    	       			  data = (data && !Ext.isArray(data) ) ? [data] : data;
    	       			  return data;
    	       		  }
                  }  
             ],
    hasMany: [{model: 'Ssp.model.tool.map.PlanCourse',
    		  name: 'planCourses',
    		  associationKey: 'planCourses'},
			  {model: 'Ssp.model.tool.map.PlanCourse',
		    		  name: 'templateCourses',
		    		  associationKey: 'templateCourses'},
    		  {model: 'Ssp.model.tool.map.TermNote',
        		  name: 'termNotes',
        		  associationKey: 'termNotes'}],

	clearPlanCourses:function(){
				var me = this;
				var currentCourses =  me.get('planCourses');
				while(currentCourses.length > 0) {
				    currentCourses.pop(); 
				}
				var currentTemplateCourses =  me.get('templateCourses');
				while(currentTemplateCourses.length > 0) {
				    currentTemplateCourses.pop(); 
				}
			},
			
			
	getTermNoteByTermCode: function(termCode){
		var me = this;
		var termNotes =  me.get('termNotes');
		if(!termNotes){
			termNotes = [];
			me.set('termNotes', termNotes);
		}
		var foundNote;
		termNotes.forEach(function(termNote){
			if(termNote.get('termCode') == termCode)
			{
				foundNote = termNote;
			}
			});
		if(!foundNote){
			foundNote = new Ssp.model.tool.map.TermNote();
			foundNote.set('termCode', termCode);
			termNotes.push(foundNote);
		}
		
		return foundNote;
	},
	clearTermNotes:function(){
				var me = this;
				var termNotes =  me.get('termNotes');
				if(!termNotes)
					return;
				while(termNotes.length > 0) {
					termNotes.pop(); 
				}
			},
	
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
				me.set('programCode','');
				me.set('createdDate',null);
				me.set('modifiedDate',null);
			},
			
	loadFromServer : function(objectData){
		var me = this;
		me.populateFromGenericObject( objectData );
		var termNotes = me.get('termNotes')
		var recordTermNotes = [];
		if(termNotes && termNotes.length > 0)
		termNotes.forEach(function(termNote){
			var recordTermNote = new Ssp.model.tool.map.TermNote();
			recordTermNote.populateFromGenericObject(termNote);
			recordTermNotes.push(recordTermNote);
		})
		me.set('termNotes', recordTermNotes);
	}, 		
	getSimpleJsonData: function(){
		var me = this;
		var simpleData = {};
		var termNotes = me.get('termNotes')
		simpleData.termNotes = [];
		termNotes.forEach(function(termNote){
			simpleData.termNotes.push(termNote.data);
		})
		
		simpleData.ownerId = me.get('ownerId');
		
		simpleData.name = me.get('name');
		simpleData.id = me.get('id');
		simpleData.createdBy = me.get('createdBy');
		simpleData.modifiedBy = me.get('modifiedBy');
		simpleData.contactTitle = me.get('contactTitle');
         simpleData.contactPhone = me.get('contactPhone');
         simpleData.contactEmail = me.get('contactEmail');
         simpleData.contactName = me.get('contactName');
         simpleData.contactNotes = me.get('contactNotes');
         simpleData.studentNotes = me.get('studentNotes');
         //simpleData.basedOnTemplateId = me.get('basedOnTemplateId');
         simpleData.isFinancialAid = me.get('isFinancialAid');
         simpleData.isImportant = me.get('isImportant');
         simpleData.isF1Visa = me.get('isF1Visa');
         simpleData.academicGoals = me.get('academicGoals');
         simpleData.careerLink = me.get('careerLink');
         simpleData.academicLink = me.get('academicLink');
		simpleData.createdDate = me.get('createdDate');
		simpleData.modifiedDate = me.get('modifiedDate');
		if(me.get('isTemplate')){
			simpleData.templateCourses = me.get('planCourses');
			simpleData.departmentCode = me.get('departmentCode');
			simpleData.divisionCode = me.get('divisionCode');
			simpleData.programCode = me.get('programCode');
			if(me.get('isPrivate'))
				simpleData.isPrivate = me.get('isPrivate');
			else
				simpleData.isPrivate = true;
		}else{
			simpleData.planCourses = me.get('planCourses');
			simpleData.personId = me.get('personId');
		}
		return simpleData;
	}
    		        		  
});