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
Ext.define('Ssp.controller.tool.map.SemesterPanelViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
	inject:{
		currentMapPlan:'currentMapPlan',
		appEventsController: 'appEventsController',
    	courseService:'courseService',
		electiveStore: 'electivesAllUnpagedStore',
		transcriptStore: 'courseTranscriptsStore',
    	formUtils: 'formRendererUtils',
		semesterStores : 'currentSemesterStores',
		mapPlanService:'mapPlanService',
		person: 'currentPerson',
		termsStore: 'termsStore',
		formRendererUtils: 'formRendererUtils',
		coursesStore: 'coursesStore'		
	},
	
	control:{
		termNotesButton:{
			selector:"#termNotesButton",
			listeners: {
                click: 'onTermNotesButtonClick'
             }
		},
		isImportantTermButton:'#isImportantTermButton',
		deleteButton:{
			selector:"#deleteButton",
			listeners: {
                click: 'onDeleteButtonClick'
             }
		},
		view: {
			itemdblclick: 'onItemDblClick',
			afterlayout: {
				fn: 'onAfterLayout',
				single: true
			}
    	}
	},
	config:{
		minHrs : '0',
		maxHrs: '0'
	},	
	init: function() {
		var me=this;
		me.appEventsController.getApplication().addListener("onAfterPlanLoad", me.updatePastTermButton, me);
		me.getIsImportantTermButton().addListener("move", me.setTermNoteButton, me);
		me.appEventsController.assignEvent({eventName: 'onViewCourseNotes', callBackFunc: me.onViewCourseNotes, scope: me});
		me.getView().view.addListener('drop', me.onDrop, me);	
		var helpButton = me.getView().tools[0];
		if(me.currentMapPlan.get('isTemplate'))
		{
			helpButton.hidden = true;
		}
		else
		{
			helpButton.hidden = !me.getView().editable;
		}
		return me.callParent(arguments);
    },

	onAfterLayout: function(){
		var me = this;
		me.setTermNoteButton();
	},
	updatePastTermButton: function(){
		var me = this;
		var helpButton = me.getView().tools[0];
		if(me.currentMapPlan.get('isTemplate'))
		{
			helpButton.hidden = true;
		}
		else
		{
			helpButton.hidden = !me.getView().editable;
		}
		me.setTermNoteButton();
	},
	setTermNoteButton: function(){
		var me = this;
		var termNote = me.currentMapPlan.getTermNoteByTermCode(me.getView().itemId);
		var button = me.getTermNotesButton();
		var isImportantTermButton = me.getIsImportantTermButton();
		if(termNote != undefined && termNote != null && termNote.data.isImportant){
			isImportantTermButton.show();
			Ext.select('.importantIconSmall').setStyle('left', '1px');
		}else{
			isImportantTermButton.hide();
		}
		if((termNote != undefined && termNote != null && termNote.data.contactNotes && termNote.data.contactNotes.length > 0) ||
			(termNote.data.studentNotes != undefined && termNote.data.studentNotes.length > 0) ){
			button.setIcon(Ssp.util.Constants.EDIT_TERM_NOTE_ICON_PATH);
			var tooltip = "Term Notes: "
			if(termNote.data.contactNotes && termNote.data.contactNotes.length > 0)
				tooltip += "Contact Notes: " + termNote.data.contactNotes + " ";
			if(termNote.data.studentNotes && termNote.data.studentNotes.length > 0)
					tooltip += "Student Notes: " + termNote.data.studentNotes + " ";	
			button.setTooltip(tooltip);
			return;
		}
	     button.setIcon(Ssp.util.Constants.ADD_TERM_NOTE_ICON_PATH);
	},
	
	onTermNotesButtonClick: function() {
		var me = this;
		if(me.termNotesPopUp == null || me.termNotesPopUp.isDestroyed)
        	me.termNotesPopUp = Ext.create('Ssp.view.tools.map.TermNotes');
		
	    var termNote = me.currentMapPlan.getTermNoteByTermCode(me.getView().itemId);
	    me.termNotesPopUp.query('form')[0].getForm().loadRecord(termNote);
	   
		me.termNotesPopUp.query('[name=saveButton]')[0].addListener('click', me.onTermNotesSave, me, {single:true});
		
        me.termNotesPopUp.center();
        me.termNotesPopUp.show();
    },
    onDeleteButtonClick: function() {
		var me = this;
		var grid = me.getView();
		var record = grid.getSelectionModel().getSelection()[0];
		if(!grid.editable && !me.currentMapPlan.get('isTemplate'))
		{
		 	Ext.Msg.alert('SSP Error', 'You cannot modify old terms.'); 
		    return;
		}
		if(!record)
		{
			 	Ext.Msg.alert('SSP Error', 'Please select an item.'); 
	    }
		else
		{
			grid.store.remove(record);
		}
    },
	onTermNotesSave: function(button){
		var me = this;
		 var termNote = me.currentMapPlan.getTermNoteByTermCode(me.getView().itemId);
		me.termNotesPopUp.query('form')[0].getForm().updateRecord(termNote);
		me.setTermNoteButton();
		me.termNotesPopUp.close();
	},
    onItemDblClick: function(grid, record, item, index, e, eOpts) {
		var me = this;
		var courseRecord = record;
		
    		me.coursePlanDetails = Ext.create('Ssp.view.tools.map.CourseNotes',{enableFields : me.getView().editable});
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
			
			var course = me.coursesStore.findRecord('code', planCourse.get('code'));
		
			if(me.getMinHrs() == 0){
				if(course != null)
					creditHours.setMinValue(course.get('minCreditHours'));
			}
			else
			{
				creditHours.setMinValue(me.getMinHrs());
			}
    		if(me.getMaxHrs() == 0){
				if(course != null)
					creditHours.setMaxValue(course.get('maxCreditHours'));
			}
			else
			{
				creditHours.setMaxValue(me.getMaxHrs());
			}
		    
			me.coursePlanDetails.query('#electiveId')[0].select(me.coursePlanDetails.electiveStore.getById(planCourse.get('electiveId')));
    		me.coursePlanDetails.rowIndex = index;
    		me.coursePlanDetails.semesterStore = grid.store;
			me.coursePlanDetails.setTitle(planCourse.get('formattedCourse') + ' - ' + planCourse.get('title'));
    		me.coursePlanDetails.center();
    		me.coursePlanDetails.show();
			
    },
    
    onDrop: function(node, data, overModel, dropPosition, eOpts){
		var me = this;
		me.getView().setLoading(true);
		var previousSemesterPanel = data.view.findParentByType("semesterpanel");
		if(previousSemesterPanel != undefined && previousSemesterPanel != null){
    		me.droppedFromStore = data.view.getStore();
		}
		me.droppedRecord = data.records[0];
		
		me.setMinHrs(me.droppedRecord.data.minCreditHours);
		me.setMaxHrs(me.droppedRecord.data.maxCreditHours);
		
		
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
		me.planWasDirty = me.currentMapPlan.dirty;
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
		// If the plan came back as valid, then we know a course was added and
		// thus must be dirty, thus the 2nd arg. If not the user is given the
		// option to undo the course add. Also, we need to be sure to update
		// the dirty flag here rather than in onDrop() b/c this
		// function may fire after onDrop() has already returned and
		// Plan.loadFromServer() above lowers the dirty flag
		me.currentMapPlan.repopulatePlanStores(me.semesterStores, me.currentMapPlan.get("isValid") ? true : me.planWasDirty);
		var panel = me.getView();
		var planCourse = me.currentMapPlan.getPlanCourseFromCourseCode(me.droppedRecord.get("code"), panel.getItemId());
		
		var invalidReasons = planCourse.invalidReasons;
    	if(!me.currentMapPlan.get("isValid") &&  invalidReasons != null && invalidReasons.length > 1){
    		var message = " \n Are you sure you want to add the course? " 
						+ planCourse.formattedCourse
						+ " generates the following concerns: " 
						+ invalidReasons;
    		Ext.MessageBox.confirm("Adding Course Invalidates Plan", message, me.handleInvalidCourse, me);
    	}else{
			me.currentMapPlan.dirty = true;
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
        	var index = me.getView().getStore().findExact('code', me.droppedRecord.get("code"));
			if(index >= 0){
        		me.getView().getStore().removeAt(index);
        		//It would be nice if ExtJs has an option to 'remove silently'.  Removing this from the store triggers the
        		// 'isDirty' check done during global navigation.  Since we don't want the cancellation of the drop
        		// to trip dirty (see SSP-2032), we clear out the 'removed' array.
				me.getView().getStore().removed = [];
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
	destroy: function(){
		var me=this;
		me.appEventsController.removeEvent({eventName: 'onViewCourseNotes', callBackFunc: me.onViewCourseNotes, scope: me});
		me.appEventsController.getApplication().removeListener("onAfterPlanLoad", me.updatePastTermButton, me);
		 return me.callParent( arguments );
	}
});