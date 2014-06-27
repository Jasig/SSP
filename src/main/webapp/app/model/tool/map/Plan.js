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
            {name: 'isTemplate', type: 'boolean'},
			{name:'departmentCode',type:'string'},
			{name:'programCode',type:'string'},
			{name:'divisionCode',type:'string'},
			{name:'divisionCode',type:'string'},
			{name:'catalogYearCode',type:'string'},
			{name:'isPrivate',type:'boolean'},
			{name:'visibility',type:'string'},
			{name:'isValid',type:'boolean'},
             
            {name:'planCourses',type:'auto',
				convert: function(data,model){
					data = (data && !Ext.isArray(data) ) ? [data] : data;
					return data;
				}
            },

			{name:'templateCourses',type:'auto',
				convert: function(data,model){
					data = (data && !Ext.isArray(data) ) ? [data] : data;
	       			return data;
	       		}
            },
            
			{name:'termNotes',type:'auto',
				convert: function(data,model){
					data = (data && !Ext.isArray(data) ) ? [data] : data;
    	       		return data;
    	       	}
            }
	],
			
	hasMany:[{model: 'Ssp.model.tool.map.PlanCourse',
				name: 'planCourses',
				associationKey: 'planCourses'},
			{model: 'Ssp.model.tool.map.PlanCourse',
		    	name: 'templateCourses',
		    	associationKey: 'templateCourses'},
			{model: 'Ssp.model.tool.map.TermNote',
        		name: 'termNotes',
        		associationKey: 'termNotes'
			}
	],

	clearPlanCourses:function(){
		var me = this;
		var currentCourses =  me.get('planCourses');
		if(Array.isArray(currentCourses)){
			while(	currentCourses.length > 0){
				currentCourses.pop(); 
			}
		}else{
			me.set('planCourses',[]);
		}
	},	
	
    setIsTemplate: function(value){
        var me = this;
        me.set("isTemplate", value);
		me.dirty = false;
    },
		
	getTermNoteByTermCode: function(termCode){
		var me = this;
		var termNotes =  me.get('termNotes');
		if(!termNotes){
			termNotes = [];
			me.set('termNotes', termNotes);
		}
		var foundNote;
		Ext.Array.forEach(termNotes, function(termNote) {
			if(termNote.get('termCode') == termCode){
				foundNote = termNote;
			}});
		if(!foundNote){
			foundNote = new Ssp.model.tool.map.TermNote();
			foundNote.set('termCode', termCode);
			foundNote.dirty = false;
			termNotes.push(foundNote);
		}
		
		return foundNote;
	},
	
	getTermCodes: function(){ 
		var me = this;
    	var termCodes = [];
    	var i = 0;
    	var planCourses = me.get('planCourses');
    	if(!planCourses){
    		return termCodes;
		}
    	
    	Ext.Array.forEach(planCourses, function(planCourse) {
    	if( Ext.Array.indexOf(termCodes, planCourse.termCode) < 0 ) {
    		termCodes[i++] = planCourse.termCode;
    	}});
    	return termCodes;
    },
	
	hasCourse: function(termCode){
		var me = this;
		var planCourses = me.get('planCourses');
    	if(!planCourses){
    		return false;
		}
    	for(var i = 0; i < planCourses.length; i++){
			var planCourse = planCourses[i];
			if(planCourse.termCode == termCode){
				return true;
			}
		}
    	return false;
	},
	
	clearTermNotes:function(){
		var me = this;
		var termNotes =  me.get('termNotes');
		if(!Array.isArray(termNotes)){
			me.set('termNotes',[]);
			return;
		}
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
		me.set('catalogYearCode','');
		me.set('isPrivate',false);
		me.set('visibility','AUTHENTICATED');
		me.set('programCode','');
		me.set('createdDate',null);
		me.set('modifiedDate',null);
		me.set('isValid',true);
		me.set('isTemplate',false);
		me.dirty = false;
	},
			
	loadFromServer : function(objectData){
		var me = this;
		isTemplate = me.get('isTemplate');
		me.populateFromGenericObject( objectData );
		var termNotes = me.get('termNotes');
		var recordTermNotes = [];
		if(Array.isArray(termNotes) && termNotes.length > 0){
			Ext.Array.forEach(termNotes, function(termNote) {
				var recordTermNote = new Ssp.model.tool.map.TermNote();
				recordTermNote.populateFromGenericObject(termNote);
				recordTermNote.dirty = false;
				recordTermNotes.push(recordTermNote);
			});
		}
		me.set('termNotes', recordTermNotes);
		me.dirty = false;
		me.setIsTemplate(isTemplate);
	}, 
	
	isEmpty: function(str){
		return (!str);
	},
	
	getSimpleJsonData: function(){
		var me = this;
		var simpleData = {};
		var termNotes = me.get('termNotes')
		simpleData.termNotes = [];
		if(Array.isArray(termNotes)){
			Ext.Array.forEach(termNotes, function(termNote) {
				var studentNoteHasValue = !me.isEmpty(termNote.get("studentNotes"));
				var contactNoteHasValue = !me.isEmpty(termNote.get("contactNotes"));
				if(termNote != null && (studentNoteHasValue || contactNoteHasValue || termNote.get("isImportant"))){
					termNote.set("id","");
					termNote.set("objectStatus","ACTIVE");
					simpleData.termNotes.push(termNote.getData());
				}
			});
		}
		
		simpleData.ownerId = me.get('ownerId');
		simpleData.objectStatus = me.getObjectStatus();
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
        simpleData.isFinancialAid = me.getBoolean('isFinancialAid');
        simpleData.isImportant = me.getBoolean('isImportant');
        simpleData.isF1Visa = me.getBoolean('isF1Visa');
        simpleData.academicGoals = me.get('academicGoals');
        simpleData.careerLink = me.get('careerLink');
        simpleData.academicLink = me.get('academicLink');
		simpleData.createdDate = me.get('createdDate');
		simpleData.modifiedDate = me.get('modifiedDate');
		simpleData.isValid = me.get('isValid');
		simpleData.programCode = me.get('programCode');
		simpleData.catalogYearCode = me.get('catalogYearCode');
		simpleData.planCourses = me.get('planCourses');
		
		if(me.get('isTemplate')){
			simpleData.departmentCode = me.get('departmentCode');
			simpleData.divisionCode = me.get('divisionCode');
			simpleData.isPrivate = me.getBoolean('isPrivate');
			simpleData.visibility = me.get('visibility');
		}else{
			simpleData.personId = me.get('personId');
		}
		return simpleData;
	},
	
	clonePlan:function(){
		var me = this;
		var clone = new Ssp.model.tool.map.Plan({id:""});
		var data = me.getSimpleJsonData();
		clone.loadFromServer(data);
		return clone;
	},
	
	loadPlan:function(planToLoad, retainTemplateStatus){
		var me = this;
		var isTemplate = me.get('isTemplate');
		var data = planToLoad.getSimpleJsonData();
		me.loadFromServer(data);
		if(retainTemplateStatus){
			me.get('isTemplate', isTemplate);
		}
	},
	
	getBoolean:function(fieldName){
		var me = this;
		if(me.get(fieldName) == 'on' || me.get(fieldName) == true || me.get(fieldName) == 1 || me.get(fieldName) == 'true'){
			return true;
		}
		return false;
	},
	
	getObjectStatus:function(){
		var me = this;
		if(me.getBoolean('objectStatus') || me.get('objectStatus') == 'ACTIVE'){
			return 'ACTIVE';
		}	
		return 'INACTIVE';
	},
	
	getAsBoolean:function(fieldName, yesValue){
		var me = this;
		if(me.getBoolean(fieldName) || me.get(fieldName) == yesValue){
			return true;
		}
		 return false;
	},
	
	updatePlanCourses: function(semesterStores, useSemesterTermCode){ 
        var me = this;
        
		me.clearPlanCourses();
        var planCourses = new Array();
		var i = 0;
        for(var index in semesterStores){
        	var semesterStore = semesterStores[index];
            var models = semesterStore.getRange();
			
            Ext.Array.forEach(models, function(model) {
            	var planCourse = new Object();
            		planCourse.courseTitle = model.get('title');
            		planCourse.courseCode = model.get('code');
					//TODO This has to do with conflicts with print and save
					if(useSemesterTermCode || !model.get('termCode') || model.get('termCode') == ""){
            			planCourse.termCode = index;
					}	
					else{
						planCourse.termCode = model.get('termCode');
					}	
            		planCourse.creditHours = model.get('creditHours');
            		planCourse.formattedCourse = model.get('formattedCourse');
            		planCourse.courseDescription = model.get('description');
            		planCourse.studentNotes = model.get('studentNotes');
            		planCourse.contactNotes = model.get('contactNotes');
            		planCourse.isImportant = model.get('isImportant') ==  null ? false : model.get('isImportant');
            		planCourse.isTranscript = model.get('isTranscript') ==  null ? false : model.get('isTranscript');
					planCourse.duplicateOfTranscript = model.get('duplicateOfTranscript') ==  null ? false : model.get('duplicateOfTranscript');
            		planCourse.electiveId = model.get('electiveId');
            		planCourse.isValidInTerm = model.get('isValidInTerm');
            		planCourse.hasCorequisites = model.get('hasCorequisites');
            		planCourse.hasPrerequisites = model.get('hasPrerequisites');
            		planCourse.invalidReasons = model.get('invalidReasons');
					planCourse.modifiedBy = model.get('modifiedBy');
					planCourse.modifiedDate = model.get('modifiedDate');
					planCourse.createdBy = model.get('createdBy');
					planCourse.createdDate = model.get('createdDate');
            		planCourse.orderInTerm = model.get('orderInTerm');
					planCourse.objectStatus = 'ACTIVE';
            		planCourse.isDev = model.get('isDev');
            		planCourses[i++] = planCourse;
            });
         }
         me.set('planCourses',planCourses);
    },
    
    validateCourseRequisites: function(courseRequisites, termsStore, coursesStore){
		var me = this;
    	var planCourses = me.get('planCourses');
    	var validationResponse = {valid:true, message:""};
    	if(!planCourses || courseRequisites.length <= 0){
     		return validationResponse;
    	}
    	var requiringCourse;
    	var requiredCourses = [];

		for(var k = 0; k < planCourses.length; k++){
    		course = planCourses[k];
    		if(course.courseCode == courseRequisites[0].requiringCourseCode){
    			requiringCourse = course;
			}
    	}
		
    	for(var k = 0; k < planCourses.length; k++){
    		course = planCourses[k];
    		for(var i = 0; i < courseRequisites.length; i++){
    			if(course.courseCode == courseRequisites[i].requiredCourseCode){
    				course.requisiteCode = courseRequisites[i].requisiteCode;
        			requiredCourses.push(course);
					courseRequisites.splice(i,1);
        			break;
    			}
    		}
    	}
    	
    	if(courseRequisites.length > 0){
    		validationResponse.valid = false;
    		validationResponse.message = " " + courseRequisites.length + " pre/co requisites are not currently on plan: ";
			for(var i = 0; i < courseRequisites.length; i++){
				var course = coursesStore.findRecord('code', courseRequisites[i].requiredCourseCode, 0, false, false, true);
				var formattedCourse = courseRequisites[i].requiredCourseCode + "(C)";
				if(course != null){
					formattedCourse = course.get("formattedCourse");
				}	
				validationResponse.message += formattedCourse + ", ";
			}
			validationResponse.message.slice(0, -2);
    	}
    	
    	if(requiredCourses.length > requiredCourses.length){
    		return validationResponse;
		}
    	var requiringCourseTermIndex = termsStore.findExact("code", requiringCourse.termCode);
    	var startMessageAdded = false;
    	
    	Ext.Array.forEach(requiredCourses, function(requiredCourse) {
    		 var index = termsStore.findExact("code", requiredCourse.termCode);
    		 var startMessage = "The following pre/co requisites are on plan but in the wrong term: ";
    		 var isValid = true;
    		 switch(requiredCourse.requisiteCode){
    		 	case "PRE":
    		 		if(index >= requiringCourseTermIndex){
    		 			isValid = false;
					}
    		 		break;
    		 		
    		 	case "PRE_CO":
    		 		if(index > requiringCourseTermIndex){
    		 			isValid = false;
					}
    		 		break;
    		 	case "CO":
    		 		if(index != requiringCourseTermIndex){
    		 			isValid = false;
					}
    		 		break;	
    		 }
    		 if(isValid == false && startMessageAdded == false){
    			 validationResponse.message += startMessage;
				 validationResponse.valid = false;
    			 startMessageAdded = true;
    		 }
    		 if(isValid == false){
    			 validationResponse.message += requiredCourse.formattedCourse + ", ";
			}
    	});
		if(startMessageAdded){
			validationResponse.message.slice(0, -2);
		}
    	return validationResponse;
    	
    },
	
	isDirty: function(semesterStores){
		var me = this;
		if(me.dirty){
			return true;
		}
			
		if(semesterStores){
			for(var index in semesterStores){
				var semesterStore = semesterStores[index];
				if(semesterStore.getUpdatedRecords().length > 0 || semesterStore.getNewRecords().length > 0 || semesterStore.getRemovedRecords( ).length > 0){
					return true;
				}
			};
		}

		var termNotes = me.get("termNotes");
		if(termNotes){
			for(var i = 0; i < termNotes.length; i++){
				var termNote = termNotes[i];
				if(termNote.dirty){
					return true;
				}
			}
		}
		return false;
	},
	
	getPlanCourseFromCourseCode: function(courseCode, termCode){
		var me = this;
		var planCourses = me.get('planCourses');
		if(planCourses)
		{
			for(var i = 0; i < planCourses.length; i++){
				var planCourse = planCourses[i];
				if(planCourse.courseCode == courseCode){
					if(termCode != null){
						if(planCourse.termCode == termCode){
							return planCourse;
						}
					}else{
						return planCourse;
					}
				}
			}
		}
		return null;
	},
	
	repopulatePlanStores:function(semesterStores, markDirty){
		var me = this;
		var planCourses = me.get('planCourses');
		var courses = {};
		if(planCourses && planCourses.length > 0){
			Ext.Array.forEach(planCourses, function(planCourse) {
				var semesterCourse = new Ssp.model.tool.map.SemesterCourse(planCourse);
				var semesterSet = courses[planCourse.termCode];
				if(!semesterSet){
					semesterSet = [];
				}
				semesterSet.push(semesterCourse);
				courses[planCourse.termCode] = semesterSet;
			}); 
		}
		
		for(termCode in semesterStores){
			semesterStores[termCode].removeAll();
		}
		if(planCourses && planCourses.length > 0){
			for(index in courses){
				var termStore = semesterStores[index];
				termStore.suspendEvents();
				termStore.loadRecords(courses[index], {addRecords:false});
				termStore.sync();
				termStore.resumeEvents();
			}
		}
		if(markDirty != null){
			me.dirty = markDirty;
		}
	},
	
	getPlanValidation: function(){
		var me = this;
		var termInvalidCount = 0;
		var prerequisiteInvalidCount = 0;
		var corequisiteInvalidCount = 0;		
		var termInvalid = "";
		var prerequisiteInvalid = "";
		var corequisiteInvalid = "";
		var validationResponse = {message:"",valid:true};
		if(me.get("isValid")){
			return validationResponse;
		}
		var planCourses = me.get('planCourses');
		for(var i = 0; i < planCourses.length; i++){
			var planCourse = planCourses[i];

			if(planCourse.isValidInTerm == false){
				termInvalidCount++;
				termInvalid += planCourse.formattedCourse + ',';
			}
			if(planCourse.hasCorequisites == false){
				corequisiteInvalidCount++;
				corequisiteInvalid += planCourse.formattedCourse + ',';
			}
			if(planCourse.hasPrerequisits == false){
				prerequisiteInvalidCount++;
				prerequisiteInvalid += planCourse.formattedCourse + ', ';
			}}
		
		validationResponse.message = "The plan has the following invalid courses";
		validationResponse.valid = false;
		if(termInvalidCount > 0){
			validationResponse.message += "\n Invalid Terms: " + termInvalidCount + " : " + termInvalid;
		}
		
		if(prerequisiteInvalidCount > 0){
			validationResponse.message += "\n Terms with invalid prerequisites: " + prerequisiteInvalidCount + " : " + prerequisiteInvalid;
		}
		if(corequisiteInvalidCount > 0){
			validationResponse.message += "\n Terms with invalid corequisites: " + corequisiteInvalidCount + " : " + corequisiteInvalid;
		}
		
		return validationResponse;
	},
	
	clearValidation: function(){
		var me = this;
		me.set("isValid",true);
		var planCourses = me.get("planCourses");
		Ext.Array.forEach(planCourses, function(course) {
			course.invalidReasons = "";
			course.isValidInTerm = true;
			course.hasCorequisites = true;
			course.hasPrerequisites = true;
		});
	},
	
	getCourseValidation: function(requiringCourseCode, courseRequisites){
		var me = this;
		var termInvalidCount = 0;
		var prerequisiteInvalidCount = 0;
		var corequisiteInvalidCount = 0;
		
		var termInvalid = ""
		var prerequisiteInvalid = "";
		var corequisiteInvalid = "";
		var validationResponse = {message:"",valid:true};
		if(me.get("isValid")){
			return validationResponse;
		}
		var planCourses = me.get('planCourses');
		var courseIsValid = true;
		var invalidMessages = "";
		var coreCourseInvalidMessages = "";
		var relatedCoursesInvalid = false;
		for(var i = 0; i < planCourses.length; i++){
			var planCourse = planCourses[i];
			var isARequisite = false;
			var isCoreCourse = false;
			for(var k = 0; k < courseRequisites.length; k++){
				var courseRequisite = courseRequisites[k];
				if(planCourse.courseCode == courseRequisite.requiredCourseCode ||  planCourse.courseCode == courseRequisite.requiringCourseCode){
					
					isARequisite = true;
				}
			}
			if(isARequisite == false)
				continue;
			
			if(planCourse.isValidInTerm === false || planCourse.hasCorequisites == false || planCourse.hasPrerequisits == false){
				courseIsValid = false;
			}else{
				continue;
			}
			
			if(planCourse.courseCode == requiringCourseCode){
				coreCourseInvalidMessages += planCourse.courseCode + " : " + planCourse.invalidReasons + "\n";
			}
			else{
				relatedCoursesInvalid = true;
				invalidMessages +=  planCourse.courseCode + " : " + planCourse.invalidReasons + " \n";
			}
		}
		if(!courseIsValid){
			validationResponse.valid = false;
			validationResponse.message = "Adding course will generate the following concerns: \n" + coreCourseInvalidMessages;
		}
		if(relatedCoursesInvalid){
			if(coreCourseInvalidMessages.length > 0){
				validationResponse.message += "additional concerns were found in related courses: " + invalidMessages;
			}
			else{
				validationResponse.message += invalidMessages;
			}
		}

		return validationResponse;
	}
});