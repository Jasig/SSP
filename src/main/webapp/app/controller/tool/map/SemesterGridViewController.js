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
Ext.define('Ssp.controller.tool.map.SemesterGridViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject:{
		appEventsController: 'appEventsController',
    	courseService:'courseService',
		electiveStore: 'electiveStore',
		colorsStore: 'colorsStore',
		transcriptStore: 'courseTranscriptsStore',
    	formUtils: 'formRendererUtils',
		currentMapPlan: 'currentMapPlan',
		semesterStores : 'currentSemesterStores',
		mapPlanService:'mapPlanService',
		person: 'currentPerson',
		termsStore: 'termsStore'
    },
    control:{
    	view:{
    		    itemdblclick: 'onItemDblClick'
    		}
    },	
	init: function() {
		var me=this;
		me.appEventsController.assignEvent({eventName: 'onViewCourseNotes', callBackFunc: me.onViewCourseNotes, scope: me});
		me.getView().view.addListener('beforedrop', me.onDrop, me);
		return me.callParent(arguments);
    },
	

    onItemDblClick: function(grid, record, item, index, e, eOpts) {
		var me = this;
		var courseRecord = record;
    		me.coursePlanDetails = Ext.create('Ssp.view.tools.map.CourseNotes',{enableFields : me.getView().enableDragAndDrop});
    		me.coursePlanDetails.parentGrid = me.getView();
    		
    		
			var creditHours = me.coursePlanDetails.query('#creditHours')[0];

			if(courseRecord.modelName == 'Ssp.model.external.Course')
			{
				var planCourse = new Ssp.model.tool.map.SemesterCourse(courseRecord.data);
				var indexOf = grid.store.indexOf(courseRecord);
				var array = new Array();
				array[0] = planCourse;
				grid.store.insert( indexOf != -1 ? indexOf : index ,array);
				grid.store.remove(courseRecord);
			}
			else
			{
				var planCourse = courseRecord;
			}
			me.coursePlanDetails.query('form')[0].getForm().loadRecord(planCourse);
    		creditHours.setValue(planCourse.get('creditHours'));
		    creditHours.setMinValue(planCourse.get('minCreditHours'));
			creditHours.setMaxValue(planCourse.get('maxCreditHours'));
			me.coursePlanDetails.query('#electiveId')[0].select(me.coursePlanDetails.electiveStore.getById(planCourse.get('electiveId')));
    		me.coursePlanDetails.rowIndex = index;
    		me.coursePlanDetails.semesterStore = grid.store;
			me.coursePlanDetails.setTitle(planCourse.get('formattedCourse') + ' - ' + planCourse.get('title'));
    		me.coursePlanDetails.center();
    		me.coursePlanDetails.show();
    },
    
    onDrop: function(node, data, dropRec, dropPosition){
    	var me = this;
    	me.droppedData = data.records[0];
		var termCode = me.getView().findParentByType('semesterpanel').itemId;
		var previousSemesterPanel = data.view.findParentByType("semesterpanel");
				if(previousSemesterPanel != null && previousSemesterPanel != undefined)
				    me.previousTermCode = previousSemesterPanel.getItemId();
		me.getView().setLoading(true);
    	var serviceResponses = {
                failures: {},
                successes: {},
                responseCnt: 0,
                expectedResponseCnt: 1
            }
		me.requiringFormattedCourse = me.droppedData.get('formattedCourse');
    	me.courseService.validateCourse(me.droppedData.get('code'), termCode,  {
            success: me.newServiceSuccessHandler('validCourse', me.onTermValidateSuccess, serviceResponses),
            failure: me.newServiceFailureHandler('validatedFailed', me.onValidateFailure, serviceResponses),
            scope: me
        });
		return true;
    },
    
    onTermValidateSuccess: function(serviceResponses){
		var me = this;
    	me.courseTermValidation = serviceResponses.successes.validCourse;
    	var serviceResponses = {
                failures: {},
                successes: {},
                responseCnt: 0,
                expectedResponseCnt: 1
            }
		me.requiringCourseCode = me.droppedData.get('code');
    	me.courseService.getCourseRequirements(me.droppedData.get('code'),  {
            success: me.newServiceSuccessHandler('courseRequisites', me.onCourseRequirements, serviceResponses),
            failure: me.newServiceFailureHandler('validatedFailed', me.onValidateFailure, serviceResponses),
            scope: me
        });
    	
    },

	onCourseRequirements: function(serviceResponses){
		var me = this;
		me.courseRequisites = serviceResponses.successes.courseRequisites;
		if(me.courseRequisites == null || me.courseRequisites.length <= 0){
			me.onValidateSuccess();
			return;
		}
		me.currentMapPlan.updatePlanCourses(me.semesterStores);
		me.mapPlanService.validate(me.currentMapPlan, me.currentMapPlan.get('isTemplate'), {
            success: me.newServiceSuccessHandler('validatedPlan', me.onValidateSuccess, serviceResponses),
            failure: me.newServiceFailureHandler('validatedFailed', me.onValidateFailure, serviceResponses),
            scope: me,
            isPrivate: true
        });
	},
    
    onValidateSuccess: function(serviceResponses){
		var me = this;
		 me.getView().setLoading(false);
    	var courseRequisites = me.courseRequisites;
		var validationResponse = null;
		if(me.courseRequisites != null && me.courseRequisites.length > 0){
			var mapResponse = serviceResponses.successes.validatedPlan;
			me.currentMapPlan.loadFromServer(Ext.decode(mapResponse.responseText));
			validationResponse = me.currentMapPlan.getCourseValidation(me.requiringCourseCode, courseRequisites);
		}
    	var confirm = {}
    	confirm.valid = true;
    	confirm.title = "";
    	confirm.message = "";
		var record = null;
		for(termCode in me.semesterStores){
			var semesterStore = me.semesterStores[termCode];
			record = semesterStore.findRecord("formattedCourse", me.requiringFormattedCourse);
			if(record){
				break;
			}
		}
    	if(!me.courseTermValidation.valid && validationResponse == null){
    		confirm.valid = false;
    		confirm.title = 'Course Not Avaiable For Term';
    		confirm.message = 'This course is not scheduled to be offered in this term. ';
			if(record){
				record.set("validInTerm", false);
				var invalidReasons = record.get("invalidReasons");
				if(!invalidReasons)
					invalidReasons = "";
				invalidReasons += "Course not in current term.";
				record.set("invalidReasons", invalidReasons);
			}
    	}
		if(me.currentMapPlan.get('isTemplate') === false)
			me.verifyTranscript(record, confirm);
		
    	if(validationResponse != null && !validationResponse.valid){
    		confirm.valid = false;
    		confirm.title = ' Course Generates Following Concerns';
    		confirm.message += validationResponse.message;
			if(record){
				record.set("hasPrerequisites", false);
				var invalidReasons = record.get("invalidReasons");
				if(!invalidReasons)
					invalidReasons = "";
				invalidReasons += validationResponse.message;
				record.set("invalidReasons", invalidReasons);
			}
    	}
    	
    	if(confirm.valid == false){
    		confirm.message += " \n Are you sure you want to add the course?";
    	}
    	if(confirm.valid == false){
    		Ext.MessageBox.confirm(confirm.title, confirm.message, me.handleInvalidCourse, me);
    	}else{
			me.removeCopiedCourse();
		}
    },
    
    verifyTranscript : function(record, confirm){
		var me = this;
    	var transcript = me.transcriptStore.findRecord("formattedCourse", me.requiringFormattedCourse);
		if(transcript){
			var transcriptCourseTermCode = transcript.get('termCode');
			var transcriptTerm = me.termsStore.findRecord("code", transcriptCourseTermCode);
			if(record){
				record.set("isTranscript", true);
				if(record.get("termCode")  != transcriptCourseTermCode);
					record.set("duplicateOfTranscript", true);
			}
		}
		
		if(transcript && record.get("duplicateOfTranscript")){
			confirm.valid = false;
    		confirm.title = ' Course Is Transcript';
    		var termName = "";
    		if(transcriptTerm)
    			termName = transcriptTerm.get("name");
    		
    		confirm.message += 'Student has taken class, previously in term: ' + termName;
		}
    },
    
    handleInvalidCourse: function(buttonId){
		var me = this;
    	if(buttonId != 'yes'){
        	var index = me.getView().getStore().find('code', me.droppedData.get('code'));
			if(index >= 0)
        		me.getView().getStore().removeAt(index);
    	}else{
			me.removeCopiedCourse();
		}
    },
    
	removeCopiedCourse: function(){
		var me = this;
		if(me.previousTermCode == null || me.previousTermCode == undefined)
			return;
			
		var container = me.getView().findParentByType("semesterpanelcontainer");
		var previousSemester = container.queryById(me.previousTermCode);
		if(previousSemester != null && previousSemester != undefined){
			var grid = previousSemester.query("semestergrid")[0];
			if(grid != null && grid != undefined){
				var index = grid.getStore().find('code', me.droppedData.get('code'));
				if(index >= 0)
					grid.getStore().removeAt(index);
			}
		}
	},
	
    onValidateFailure: function(validate){
    	var me = this;
    	 me.getView().setLoading(false);
    },
    
    newServiceSuccessHandler: function(name, callback, serviceResponses) {
        var me = this;
        return me.newServiceHandler(name, callback, serviceResponses, function(name, serviceResponses, response) {
            serviceResponses.successes[name] = response;
        });
    },

    newServiceFailureHandler: function(name, callback, serviceResponses) {
        var me = this;
        return me.newServiceHandler(name, callback, serviceResponses, function(name, serviceResponses, response) {
            serviceResponses.failures[name] = response;
        });
    },

    newServiceHandler: function(name, callback, serviceResponses, serviceResponsesCallback) {
        return function(r, scope) {
            var me = scope;
            serviceResponses.responseCnt++;
            if ( serviceResponsesCallback ) {
                serviceResponsesCallback.apply(me, [name, serviceResponses, r]);
            }
            if ( callback ) {
                callback.apply(me, [ serviceResponses ]);
            }
            me.afterServiceHandler(serviceResponses);
        };
    },

	afterServiceHandler: function(serviceResponses){
		
	},
	destroy: function() {
        var me=this;
		me.appEventsController.removeEvent({eventName: 'onViewCourseNotes', callBackFunc: me.onViewCourseNotes, scope: me});
		return me.callParent( arguments );
    }
});