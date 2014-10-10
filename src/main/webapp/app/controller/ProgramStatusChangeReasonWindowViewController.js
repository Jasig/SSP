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
Ext.define('Ssp.controller.ProgramStatusChangeReasonWindowViewController', {
	extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	appEventsController: 'appEventsController',
    	personLite: 'personLite',
    	personProgramStatusService: 'personProgramStatusService'
    },
    control: {
		'saveButton': {
			click: 'onSaveClick'
		},
		
		'cancelButton': {
			click: 'onCancelClick'
		},
		
		programStatusChangeReasonCombo: '#programStatusChangeReasonCombo'
	},

	onSaveClick: function( button ){
		var me=this;
		var personId = me.personLite.get('id');
		var valid = me.getProgramStatusChangeReasonCombo().isValid();
		var reasonId = me.getProgramStatusChangeReasonCombo().getValue();
		var selectedRecord = me.getProgramStatusChangeReasonCombo().findRecordByValue(reasonId);
		var reasonName = selectedRecord.get('name');
		if (valid && reasonId != "")
		{
			if (personId != "")
			{
				personProgramStatus = new Ssp.model.PersonProgramStatus();
				personProgramStatus.set('programStatusId', Ssp.util.Constants.NON_PARTICIPATING_PROGRAM_STATUS_ID);
				personProgramStatus.set('effectiveDate', new Date());
				personProgramStatus.set('programStatusChangeReasonId', reasonId );
				me.getView().setLoading( true );
				me.personProgramStatusService.save(
					personId,
					personProgramStatus.data,
					{
						success: me.newSaveProgramStatusSuccess(personId, reasonId, reasonName),
						failure: me.saveProgramStatusFailure,
						scope: me
					});
			}else{
				Ext.Msg.alert('SSP Error','Please select a student for which to change status');
			}
		}else{
			Ext.Msg.alert('SSP Error','Please correct the highlighted errors in the form');
		}
	},

	newSaveProgramStatusSuccess: function(personId, reasonId, reasonName) {
		var me = this;
		return function(r, scope) {
			me.saveProgramStatusSuccess(personId, reasonId, reasonName, r, scope);
		}
	},

	saveProgramStatusSuccess: function(personId, reasonId, reasonName, r, scope){
		var me=scope;
		try {
			me.appEventsController.getApplication().fireEvent('afterPersonProgramStatusChange', {
				personId: personId,
				programStatusId: Ssp.util.Constants.NON_PARTICIPATING_PROGRAM_STATUS_ID,
				programStatusName: Ssp.util.Constants.NON_PARTICIPATING_PROGRAM_STATUS_NAME,
				programStatusChangeReasonId: reasonId,
				programStatusChangeReasonName: reasonName
			});
		} finally {
			me.getView().setLoading( false );
			me.close();
		}
	},

	saveProgramStatusFailure: function( r, scope){
		var me=scope;
		Ext.Msg.alert('SSP Error','Failed to process program status change. Please contact your system administrator.');
		me.getView().setLoading( false );
		me.close();
    },
    
    onCancelClick: function( button ){
    	this.close();
    },
    
    close: function(){
    	this.getView().close();
    }
});