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
Ext.define('Ssp.controller.admin.caseload.CaseloadReassignmentTargetViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	store: 'challengeReferralsStore',
    	caseloadService: 'caseloadService',
    	formUtils: 'formRendererUtils',
    	model: 'currentChallengeReferral',
    	reassignCaseloadStore: 'reassignCaseloadStore',
        caseloadStore: 'reassignCaseloadStagingStore',
    	appEventsController: 'appEventsController',
    	textStore: 'sspTextStore'
    },
    config: {
    	containerToLoadInto: 'adminforms',
    	formToDisplay: 'editreferral',
        coachStore: 'coachesStore',
        reassignCaseloadStore: 'reassignCaseloadStore',
    	formToDisplay: 'caseloadreassignment'
    },
    control: {
		'removeButton': {
			click: 'onRemove'
		},   
		'saveButton': {
			click: 'onSave'
		}		
    },       
	init: function() {
		var me=this;
		me.reassignCaseloadStore.removeAll(false);
		me.formUtils.reconfigureGridPanel( me.getView(), me.reassignCaseloadStore);
    	me.appEventsController.assignEvent({eventName: 'studentAdded', callBackFunc: me.onStudentAdded, scope: me});
		return me.callParent(arguments);
    },
    
    destroy: function() {
    	var me = this;
    	me.appEventsController.removeEvent({eventName: 'studentAdded', callBackFunc: me.onStudentAdded, scope: me});
    	return me.callParent(arguments);
    },
    
    onRemove: function(button) {
		var me=this;
        if (me.getView().getSelectionModel().getSelection().length > 0) 
        {		
        	me.reassignCaseloadStore.remove(me.getView().getSelectionModel().getSelection());
    		me.formUtils.reconfigureGridPanel( me.getView(), me.reassignCaseloadStore);
        }else{
			Ext.Msg.alert(
				me.textStore.getValueByCode('ssp.message.caseload-reassignment.error-title','SSP Error'),
				me.textStore.getValueByCode('ssp.message.caseload-reassignment.select-item-to-remove','Please select an item to remove.')
				);
        }
	},
    onSave: function(button) {
    	var me=this;
    	var url=me.getBaseUrl();
    	var jsonData;
    	var coachId = this.getView().query('combobox')[0].getValue();
    	if(!coachId)
    	{
			Ext.Msg.alert(
				me.textStore.getValueByCode('ssp.message.caseload-reassignment.error-title','SSP Error'),
				me.textStore.getValueByCode('ssp.message.caseload-reassignment.select-target-coach','Please select a target coach.')
				);
      	   return;
    	}
    	if(me.reassignCaseloadStore.getCount() < 1)
    	{
			Ext.Msg.alert(
				me.textStore.getValueByCode('ssp.message.caseload-reassignment.error-title','SSP Error'),
				me.textStore.getValueByCode('ssp.message.caseload-reassignment.select-students-to-reassign','Please select a student to reassign.')
				);
      	   return;
    	}
    	var reassignmentRequest = new Ssp.model.tool.caseload.CaseloadReassignmentRequest();
    	reassignmentRequest.set('coachId',coachId);
    	var studentIds = new Array();
    	for(var i=0; i < me.reassignCaseloadStore.getCount(); i++)
    	{
    		studentIds[i] = me.reassignCaseloadStore.getAt(i).get('schoolId');
    	}
    	reassignmentRequest.set('studentIds',studentIds);
    	jsonData = reassignmentRequest.data;
    	var success = function(){
    		var me=this;
    		var defaultMsg = '%COUNT% student(s) have been updated.';
			Ext.Msg.alert(
				me.textStore.getValueByCode('ssp.message.caseload-reassignment.error-title','SSP Error'),
				me.textStore.getValueByCode('ssp.message.caseload-reassignment.students-updated',defaultMsg,{'%COUNT%':me.reassignCaseloadStore.getCount()})
				);

    		me.displayMain();
    	};
    	var failure = function(){
    		var me=this;
			Ext.Msg.alert(
				me.textStore.getValueByCode('ssp.message.caseload-reassignment.error-title','SSP Error'),
				me.textStore.getValueByCode('ssp.message.caseload-reassignment.error-saving','There was an error while trying to save.')
				);
    	};
    	
		me.apiProperties.makeRequest({
			url: url,
			method: 'POST',
			jsonData: jsonData,
			successFunc: success,
			failureFunc: failure,
			scope: me
		});		
	},
	onStudentAdded: function(){
		var me=this;
		me.formUtils.reconfigureGridPanel( me.getView(), me.reassignCaseloadStore);
	},
    getBaseUrl: function(){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personCaseload') );
		return baseUrl;
    },
	displayMain: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});