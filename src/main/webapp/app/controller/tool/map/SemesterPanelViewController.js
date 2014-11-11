/*
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
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
			me.appEventsController.loadMaskOn();
			grid.store.remove(record);
			me.validateCourses({
				course: record,
				fromStore: grid.store,
				op: 'DELETE'
			});
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
			
			var course = me.coursesStore.findRecord('code', planCourse.get('code'), 0, false, false, true);
		
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
		me.appEventsController.loadMaskOn();
		var courseOpSpec = {
			course: null,
			fromStore: null,
			op: 'MOVE'
		};
		var previousSemesterPanel = data.view.findParentByType("semesterpanel");
		if(previousSemesterPanel != undefined && previousSemesterPanel != null){
			courseOpSpec.fromStore = data.view.getStore();
		}
		courseOpSpec.course = data.records[0];

		me.setMinHrs(courseOpSpec.course.data.minCreditHours);
		me.setMaxHrs(courseOpSpec.course.data.maxCreditHours);
	
		me.validateCourses(courseOpSpec);
		return true;
    },


	validateCourses: function(courseOpSpec){
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
            success: me.newServiceSuccessHandler('validatedPlan', me.newOnValidateSuccess(courseOpSpec), serviceResponses),
            failure: me.newServiceFailureHandler('validatedFailed', me.onValidateFailure, serviceResponses),
            scope: me,
            isPrivate: true
        });
	},

	newOnValidateSuccess: function(courseOpSpec) {
		var me = this;
		return function(serviceResponses) {
			me.onValidateSuccess(serviceResponses, courseOpSpec);
		};
	},

    onValidateSuccess: function(serviceResponses, courseOpSpec){
		var me = this;
		var mapResponse = serviceResponses.successes.validatedPlan;
		var planAsJsonObject = Ext.decode(mapResponse.responseText);
		me.currentMapPlan.loadFromServer(planAsJsonObject);
		// We need to be sure to update the dirty flag here rather than in the event handlers that initially receive
		// planning operation requests b/c this method may fire after those handlers have already returned and
		// Plan.loadFromServer() above lowers the dirty flag. We have to go with the original dirty state, though,
		// because we won't know for sure whether to mark the plan newly dirty until the user is given the option to
		// cancel out of the change.
		me.currentMapPlan.repopulatePlanStores(me.semesterStores, me.planWasDirty);
		var panel = me.getView();
		if ( courseOpSpec ) {
			var awaitingUser = false;
			if ( courseOpSpec.op === 'MOVE' ) {
				var planCourse = me.currentMapPlan.getPlanCourseFromCourseCode(courseOpSpec.course.get("code"), panel.getItemId());
				var invalidReasons = planCourse.invalidReasons;
				if(!me.currentMapPlan.get("isValid") &&  invalidReasons != null && invalidReasons.length > 1 && courseOpSpec.op === 'MOVE') {
					var message = " \n Are you sure you want to add the course? " +
						planCourse.formattedCourse +
						" generates the following concerns: " +
						invalidReasons;
					awaitingUser = true;
					Ext.MessageBox.confirm("Adding Course Invalidates Plan", message, me.newOnConfirmInvalidCourseOp(courseOpSpec), me);
				}
			} else {
				// Nothing to do because unlike the branch above, we can't reasonably report on the invalidating effects
				// of the operation in terms of the specific course being acted on. Here's we're dealing with a DELETE
				// op, the invalidations caused by whichh are typically felt by *other* courses and we have no good way
				// of calculating which courses those are. We can list out all the courses that have problems, but we
				// don't know which of those problems are *new*.
			}
			if ( !(awaitingUser) ) {
				me.currentMapPlan.dirty = true;
				me.setOrderInTerm();
				me.appEventsController.loadMaskOff();
			}
		} else {
			// Walidating outside the context of an actual user action, e.g. after a rollback. Nothing to do - no
			// user feedback to gather, and the response has already been unmarshalled onto the current plan model,
			// so just fall through clear the spinner.
			me.appEventsController.loadMaskOff();
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

	newOnConfirmInvalidCourseOp: function(courseOpSpec) {
		var me = this;
		return function(buttonId) {
			me.onConfirmInvalidCourseOp(buttonId, courseOpSpec);
		}
	},

	onConfirmInvalidCourseOp: function(buttonId, courseOpSpec){
		var me = this;
    	if(buttonId != 'yes'){
			if ( courseOpSpec.op === 'MOVE' ) {
				var index = me.getView().getStore().findExact('code', courseOpSpec.course.get("code"));
				if(index >= 0){

					// Undo the add to this semester's store (the target of the move)
					me.getView().getStore().removeAt(index);

					// Clean up store internal state after all this thrash. It would be nice if ExtJs has an option to
					// 'remove silently' from a store, but currently that action triggers the 'isDirty' check during
					// our global navigation events.  Since we don't want the cancellation of the drop to trip dirty
					// (see SSP-2032), we delete the store's deletion cache entry created as a side effect of undoing
					// the 'MOVE' operation.
					//
					// Actually, since we have to re-validate the plan after the stores are set back to the right
					// states (SSP-2638), it's not clear whether we really need all this manipulation of private store
					// state after all.
					var rmIdx = null;
					Ext.each(me.getView().getStore().removed, function(record, index, theArray) {
						if ( record.get('code') === courseOpSpec.course.get('code') ) {
							rmIdx = index;
							return false; // break each() loop
						}
						return true; // continue each() loop
					});
					if ( rmIdx !== null ) {
						Ext.Array.erase(me.getView().getStore().removed, rmIdx, 1);
					}

					// Re-add back to the 'source' store handled below -- same mechanism for both 'MOVE' and 'DELETE'
					// ops. Of course, we don't actually allow DELETE undo at this time...
				}
			}

			if(courseOpSpec.fromStore){
				var rec = courseOpSpec.course.copy(); // clone the record
				Ext.data.Model.id(rec);// generate unique id
				courseOpSpec.fromStore.add(rec);
				courseOpSpec.fromStore.sort("orderInTerm", "ASC");
			}
			if ( courseOpSpec.fromStore !== me.getView().getStore() ) {
				me.getView().getStore().sort("orderInTerm", "ASC");
			}
			me.validateCourses();
    	}else{
			me.currentMapPlan.dirty = true;
			me.appEventsController.loadMaskOff();
		}
    },

	newOnValidateFailure: function(courseOpSpec) {
		var me = this;
		return function(responses) {
			me.onValidateFailure(responses, courseOpSpec);
		};
	},

    onValidateFailure: function(responses, courseOpSpec){
    	var me = this;
		if ( courseOpSpec ) {
			// there was a course planning change specified, but couldn't process the validation. This should be
			// a recoverable problem - you'll just be planning w/o a full set of validations. In this case we do
			// know the client-side models were dirtied in some way, so go ahead and mark the model as such.
			me.currentMapPlan.dirty = true;
		} else {
			// Without a courseOpSpec, it was a validation as a side-effect of a rollback so the dirty state should be
			// whatever it was set to before the rolled-back op. Leave it alone...
		}
		me.appEventsController.loadMaskOff();
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
		
		me.getIsImportantTermButton().removeListener("move", me.setTermNoteButton, me);
		me.getView().view.removeListener('drop', me.onDrop, me);
		me.appEventsController.getApplication().removeListener("onAfterPlanLoad", me.updatePastTermButton, me);

		
		 return me.callParent( arguments );
	}
});