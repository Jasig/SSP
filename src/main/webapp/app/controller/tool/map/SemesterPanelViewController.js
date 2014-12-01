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
		me.getView().view.addListener('beforedrop', me.onBeforeDrop, me);
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
		if ( me.termNotesPopUp ) {
			me.termNotesPopUp.destroy();
		}
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
			// cache dirty state now b/c the store.remove() on the next line will dirty the plan.
			var planWasDirty = me.currentMapPlan.isDirty(me.semesterStores);
			// cache orderInTerm for similar reasons, but mainly paranoia (in case model-modifying listeners are
			// attached to remove())
			var origOrderInTerm = record.get('orderInTerm');
			grid.store.remove(record);
			me.validateCourses({
				course: record,
				fromStore: grid.store,
				op: 'DELETE',
				planWasDirty: planWasDirty,
				origOrderInTerm: origOrderInTerm,
				rollback: false,
				revalidateOnRollback: true
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

			if ( me.coursePlanDetails ) {
				me.coursePlanDetails.destroy();
			}
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

	onBeforeDrop: function(node, data, overModel, dropPosition, dropFunction, eOpts) {
		var me = this;
		me.beforeDropState = {
			// cache dirty state now, before anything actually happens, b/c any operation on
			// a source or target store will immediately dirty the plan
			planWasDirty: me.currentMapPlan.isDirty(me.semesterStores),

			// cache the original orderInTerm now b/c this value is immediately
			// recalculated when the course is added to its new target store,
			// but we need it to restore the course correctly to the source store
			// in the event the user cancels the action or there is a fault on
			// validation
			origOrderInTerm: data.records[0].get('orderInTerm')
		};
	},
    
    onDrop: function(node, data, overModel, dropPosition, eOpts){
		var me = this;

		me.appEventsController.loadMaskOn();
		var courseOp = Ext.applyIf({
			course: data.records[0],
			fromStore: null,
			op: 'MOVE',
			rollback: false,
			revalidateOnRollback: true
		}, me.beforeDropState);
		delete me.beforeDropState; // paranoia

		if(data.view.findParentByType("semesterpanel")){
			courseOp.fromStore = data.view.getStore();
		}

		me.setMinHrs(courseOp.course.data.minCreditHours);
		me.setMaxHrs(courseOp.course.data.maxCreditHours);

		me.validateCourses(courseOp);
		return true;
    },


	validateCourses: function(courseOp){
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
            success: me.newServiceSuccessHandler('validatedPlan', me.newOnValidateSuccess(courseOp), serviceResponses),
            failure: me.newServiceFailureHandler('validatedFailed', me.newOnValidateFailure(courseOp), serviceResponses),
            scope: me,
            isPrivate: true
        });
	},

	newOnValidateSuccess: function(courseOp) {
		var me = this;
		return function(serviceResponses) {
			me.onValidateSuccess(serviceResponses, courseOp);
		};
	},

    onValidateSuccess: function(serviceResponses, courseOp){
		var me = this;
		var awaitingUser = false;
		var mapResponse = serviceResponses.successes.validatedPlan;
		var planAsJsonObject = Ext.decode(mapResponse.responseText);
		me.currentMapPlan.clearValidation();
		me.currentMapPlan.loadFromServer(planAsJsonObject);
		// We need to be sure to update the dirty flag here rather than in the event handlers that initially receive
		// planning operation requests b/c this method may fire after those handlers have already returned and
		// Plan.loadFromServer() above lowers the dirty flag. We have to go with the original dirty state, though,
		// because we won't know for sure whether to mark the plan newly dirty until the user is given the option to
		// cancel out of the change.
		me.currentMapPlan.repopulatePlanStores(me.semesterStores, !!(courseOp && courseOp.planWasDirty));
		var panel = me.getView();
		if ( courseOp ) {
			if ( courseOp.op === 'MOVE' && courseOp.fromStore !== me.getView().getStore() ) {
				var planCourse = me.currentMapPlan.getPlanCourseFromCourseCode(courseOp.course.get("code"), panel.getItemId());
				if (planCourse) {
				    var invalidReasons = planCourse.invalidReasons;
                    if(!me.currentMapPlan.get("isValid") &&  invalidReasons != null && invalidReasons.length > 1 && courseOp.op === 'MOVE') {
                        var message = " \n Are you sure you want to add the course? " +
                            planCourse.formattedCourse +
                            " generates the following concerns: " +
                            invalidReasons;
                        awaitingUser = true;
                        Ext.MessageBox.confirm("Adding Course Invalidates Plan", message, me.newOnConfirmInvalidCourseOp(courseOp), me);
                    } else {
                        // no user feedback needed, fall through to dirty flag and spinner mgmt
                    }
                }
			} else {
				// Nothing to do because unlike the branch above, we can't reasonably report on the invalidating effects
				// of the operation in terms of the specific course being acted on. Here's we're dealing with a DELETE
				// op, the invalidations caused by whichh are typically felt by *other* courses and we have no good way
				// of calculating which courses those are. We can list out all the courses that have problems, but we
				// don't know which of those problems are *new*.
			}
		} else {
			// Validating outside the context of an actual user action, e.g. after a rollback. Nothing to do - no
			// user feedback to gather, and the response has already been unmarshalled onto the current plan model,
			// so just fall through to finish the op
		}
		if ( !(awaitingUser) ) {
			me.completeCourseOp(courseOp);
		}
    },

	completeCourseOp: function(courseOp) {
		var me = this;
		if ( courseOp ) {
			me.currentMapPlan.dirty = courseOp.rollback ? courseOp.planWasDirty : true;
		}
		me.setOrderInTerm();
		me.appEventsController.loadMaskOff();
	},

	setOrderInTerm: function(){
		var me = this;
		var store = me.getView().getStore();
		for(i = 0; i < store.getCount(); i++){
			var record = store.getAt(i);
			record.set("orderInTerm", i);
		}
		
	},

	newOnConfirmInvalidCourseOp: function(courseOp) {
		var me = this;
		return function(buttonId) {
			me.onConfirmInvalidCourseOp(buttonId, courseOp);
		}
	},

	onConfirmInvalidCourseOp: function(buttonId, courseOp){
		var me = this;
    	if(buttonId != 'yes'){
			courseOp.rollback = true;
			if ( courseOp.op === 'MOVE' ) {
				// Undo the add to this semester's store (the target of the move), but preventing the corresponding
				// bookkeeping in the store, which would incorrectly mark the entire plan dirty even though we're
				// just trying to cancel out of the operation. (SSP-2032)
				//
				// The 'unjournaled' nature of this removal is less important than it used to be as of SSP-2638 because
				// we typically reload the whole plan again anyway since it needs to be revalidated after this rollback.
				// But it's still important in the case where the /validate API call faults in which case another call
				// to that API after the rollback is pointless.
				me.removeCourseUnjournaled(courseOp.course, me.getView().getStore());
			}

			// Re-add back to the 'source' store handled below -- same mechanism for both 'MOVE' and 'DELETE' ops.

			if(courseOp.fromStore){

				var rec = courseOp.course.copy(); // clone the record

				// Set orderInTerm because on a MOVE, that field will have the value from the *target* store, which is
				// meaningless in the source store. Even so, still have to make an attempt to insert() into the source
				// store at orderInTerm because those fields in the source store have already been re-indexed. E.g.
				// if you have a source store w/ 3 courses and you cancel a move of the 2nd, you'll have two
				// courses in the source store where orderInTerm = 1. So we try to *insert* the course from the canceled
				// move at that orderInTerm, which will put it back in the correct location, ahead of the other course
				// which had been re-indexed to 1. Obviously this only works if the original orderInTerm values had
				// no duplicates. In that case, the resulting sort is unpredictable.
				rec.set('orderInTerm', courseOp.origOrderInTerm);
				courseOp.fromStore.insert(rec.get('orderInTerm'), [ rec ]);

				courseOp.fromStore.sort("orderInTerm", "ASC");

				me.unjournalCourseRemoval(courseOp.course, courseOp.fromStore);
			}
			if ( courseOp.fromStore !== me.getView().getStore() ) {
				me.getView().getStore().sort("orderInTerm", "ASC");
			}
			if ( courseOp.revalidateOnRollback ) {
				me.validateCourses();
			} else {
				me.completeCourseOp(courseOp); // complete 'null' course op, i.e. clean up after a rollback
			}
    	}else{
			me.completeCourseOp(courseOp);
		}
    },

	removeCourseUnjournaled: function(course, store) {
		var me = this;
		var index = store.findExact('code', course.get("code"));
		if(index >= 0){
			store.removeAt(index);
			me.unjournalCourseRemoval(course, store);
		}
	},

	unjournalCourseRemoval: function(course, store) {
		var me = this;
		var rmIdx = null;
		if ( !(store.removed) ) {
			return;
		}
		Ext.each(store.removed, function(record, index, theArray) {
			if ( record.get('code') === course.get('code') ) {
				rmIdx = index;
				return false; // break each() loop
			}
			return true; // continue each() loop
		}, me, true); // 'true' reverses the loop so we're unjournaling the most recent removal of this course
		if ( rmIdx !== null ) {
			Ext.Array.erase(store.removed, rmIdx, 1);
		}
	},

	newOnValidateFailure: function(courseOp) {
		var me = this;
		return function(responses) {
			me.onValidateFailure(responses, courseOp);
		};
	},

    onValidateFailure: function(responses, courseOp){
    	var me = this;
		// Would prefer to just allow the op to proceed albeit without the latest validations, but stores don't
		// seem to be 100% correctly updated if the /validate call comes back with a fault. So we try to just
		// roll back the change instead.
		if ( courseOp ) {
			courseOp.revalidateOnRollback = false;
			me.onConfirmInvalidCourseOp('no', courseOp);
		} else {
			// shouldn't happen b/c the revalidateOnRollback=false should prevent it, but make sure we don't just
			// fall into a endless loop of calling onConfirmInvalidCourseOp()
			me.completeCourseOp();
		}
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

		if ( me.termNotesPopUp ) {
			me.termNotesPopUp.destroy();
		}
		if ( me.coursePlanDetails ) {
			me.coursePlanDetails.destroy();
		}
		me.getIsImportantTermButton().removeListener("move", me.setTermNoteButton, me);
		me.getView().view.removeListener('beforedrop', me.onBeforeDrop, me);
		me.getView().view.removeListener('drop', me.onDrop, me);
		me.appEventsController.getApplication().removeListener("onAfterPlanLoad", me.updatePastTermButton, me);

		
		 return me.callParent( arguments );
	}
});
