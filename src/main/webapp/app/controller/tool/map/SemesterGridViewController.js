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
		electiveStore: 'electivesAllUnpagedStore',
		colorsStore: 'colorsStore',
		transcriptStore: 'courseTranscriptsStore',
    	formUtils: 'formRendererUtils',
		currentMapPlan: 'currentMapPlan',
		semesterStores : 'currentSemesterStores',
		mapPlanService:'mapPlanService',
		person: 'currentPerson',
		termsStore: 'termsStore',
		formRendererUtils: 'formRendererUtils'
    },
    control:{
    	view:{
    		    itemdblclick: 'onItemDblClick'
    		}
    },	
	init: function() {
		var me=this;
		me.appEventsController.assignEvent({eventName: 'onViewCourseNotes', callBackFunc: me.onViewCourseNotes, scope: me});
		me.getView().view.addListener('drop', me.onDrop, me);
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
	
			me.electiveStore.clearFilter(true);
			me.electiveStore.load();
			me.formRendererUtils.applyAssociativeStoreFilter(me.electiveStore, record.get('electiveId'));	
		
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
    
    onDrop: function(node, data, overModel, dropPosition, eOpts){
		var me = this;
		var previousSemesterPanel = data.view.findParentByType("semesterpanel");
		if(previousSemesterPanel != undefined && previousSemesterPanel != null){
    		me.droppedFromStore = data.view.getStore();
		}
		me.droppedRecord = data.records[0];
		me.validateCourses();
		return true;
    },


	validateCourses: function(){
		var me = this;
		var serviceResponses = {
                failures: {},
                successes: {},
                responseCnt: 0,
                expectedResponseCnt: 1
            };
		me.setOrderInTerm();
		me.currentMapPlan.updatePlanCourses(me.semesterStores, true);
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
		var mapResponse = serviceResponses.successes.validatedPlan;
		var planAsJsonObject = Ext.decode(mapResponse.responseText);
		me.currentMapPlan.loadFromServer(planAsJsonObject);
		me.planWasDirty = me.currentMapPlan.dirty;
		me.currentMapPlan.repopulatePlanStores(me.semesterStores, me.currentMapPlan.get("isValid"));

		var panel = me.getView().findParentByType("semesterpanel");
		var planCourse = me.currentMapPlan.getPlanCourseFromCourseCode(me.droppedRecord.get("code"), panel.getItemId());
		var invalidReasons = planCourse.invalidReasons;
    	if(!me.currentMapPlan.get("isValid") &&  invalidReasons != null && invalidReasons.length > 1){
    		var message = " \n Are you sure you want to add the course? " 
						+ planCourse.formattedCourse
						+ " generates the following concerns: " 
						+ invalidReasons;
    		Ext.MessageBox.confirm("Adding Course Invalidates Plan", message, me.handleInvalidCourse, me);
    	}else{
			me.setOrderInTerm();
		}
    },

	setOrderInTerm: function(){
		var me = this;
		var store = me.getView().getStore();
		for(i = 0; i < store.getCount(); i++){
			var record = store.getAt(i);
			record.set("orderInTerm", i);
		}
		
	},
    
    handleInvalidCourse: function(buttonId){
		var me = this;
    	if(buttonId != 'yes'){
        	var index = me.getView().getStore().find('code', me.droppedRecord.get("code"));
			if(index >= 0){
        		me.getView().getStore().removeAt(index);
				me.currentMapPlan.dirty = me.planWasDirty;
				me.getView().getStore().sort("orderInTerm", "ASC");
			}
			me.restoreCourse();
    	}else{
			me.currentMapPlan.dirty = true;
		}
    },
    
	restoreCourse: function(){
		var me = this;
		if(me.droppedFromStore){
			var rec = me.droppedRecord.copy(); // clone the record
			Ext.data.Model.id(rec);// generate unique id
			me.droppedFromStore.add(rec);
			me.droppedFromStore.sort("orderInTerm", "ASC");
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