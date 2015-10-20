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
Ext.define('Ssp.controller.ProgramStatusChangeReasonWindowViewController', {
	extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	appEventsController: 'appEventsController',
    	personLite: 'personLite',
    	personProgramStatusService: 'personProgramStatusService',
    	textStore: 'sspTextStore'
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

	getActionCallbacks: function() {
		var me = this;
		return me.getView().getActionCallbacks();
	},

	getIsBulk: function() {
		var me = this;
		return me.getView().getIsBulk();
	},

	// special validation function for checking to see that everything has been configured correctly.
	// it's a little tough to figure out exactly which events/lifecycle methods to hook into to get this
	// right b/c we want to dismiss this entire component if the validation fails, and trying to
	// call close() in the middle of any initialization lifecycle typically errors out. So we settled
	// on overriding show() in the view and writing special logic there to invoke this method
	// before allowing the standard show() implementation to proceed.
	beforeShow: function() {
		var me = this;
		if ( me.getIsBulk() ) {
			var okCallback = me.getActionCallbacks() && me.getActionCallbacks().ok;
			if ( !(okCallback) ) {
				// programmer error
                var defaultMsg = 'Unable to process this program status change. Please contact your system administrator.';
                Ext.Msg.alert(
                    me.textStore.getValueByCode('ssp.message.program-status-change.error-title','SSP Error'),
                    me.textStore.getValueByCode('ssp.message.program-status-change.error-message',defaultMsg)
                    );
				return false;
			}
		} else {
			var personId = me.personLite.get('id');
			if ( !(personId) ) {
                var defaultMsg = 'Please select a student for which to change status.';
                Ext.Msg.alert(
                    me.textStore.getValueByCode('ssp.message.program-status-change.error-title','SSP Error'),
                    me.textStore.getValueByCode('ssp.message.program-status-change.select-a-student',defaultMsg)
                    );
				return false;
			}
		}
		return true;
	},

	onSaveClick: function( button ){
		var me=this;
		var valid = me.getProgramStatusChangeReasonCombo().isValid();
		var reasonId = me.getProgramStatusChangeReasonCombo().getValue();
		var selectedRecord = me.getProgramStatusChangeReasonCombo().findRecordByValue(reasonId);
		var reasonName = selectedRecord.get('name');
		if (valid && reasonId != "")
		{
			var okCallback = me.getActionCallbacks() && me.getActionCallbacks().ok;
			if ( okCallback ) {
				me.close();
				var callbackScope = me.getActionCallbacks().scope;
				if ( callbackScope ) {
					okCallback.apply(callbackScope, [reasonId, reasonName]);
				} else {
					okCallback(reasonId, reasonName);
				}
			} else if ( !(me.getIsBulk()) ) {
				var personId = me.personLite.get('id');
				if (personId != "") {
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
					var defaultMsg = 'Please select a student for which to change program status.';
					Ext.Msg.alert(
						me.textStore.getValueByCode('ssp.message.program-status-change.error-title','SSP Error'),
						me.textStore.getValueByCode('ssp.message.program-status-change.select-a-student',defaultMsg)
						);
				}
			} else {
				// Programmer error, and should have been caught on init()
                var defaultMsg = 'Unable to process this program status change. Please contact your system administrator.';
                Ext.Msg.alert(
                    me.textStore.getValueByCode('ssp.message.program-status-change.error-title','SSP Error'),
                    me.textStore.getValueByCode('ssp.message.program-status-change.error-message',defaultMsg)
                    );
			}
		}else{
			var defaultMsg = 'Please correct the highlighted errors in the form.';
			Ext.Msg.alert(
				me.textStore.getValueByCode('ssp.message.program-status-change.highlighted-errors-title','SSP Error'),
				me.textStore.getValueByCode('ssp.message.program-status-change.highlighted-errors-message',defaultMsg)
				);
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
		var defaultMsg = 'Unable to process this program status change. Please contact your system administrator.';
		Ext.Msg.alert(
			me.textStore.getValueByCode('ssp.message.program-status-change.error-title','SSP Error'),
			me.textStore.getValueByCode('ssp.message.program-status-change.error-message',defaultMsg)
			);
		me.getView().setLoading( false );
		me.close();
	},

	onCancelClick: function( button ){
		var me = this;
		me.close();
		var cancelCallback = me.getActionCallbacks() && me.getActionCallbacks().cancel;
		if ( cancelCallback ) {
			var callbackScope = me.getActionCallbacks().scope;
			if ( callbackScope ) {
				cancelCallback.apply(callbackScope, []);
			} else {
				cancelCallback();
			}
		}
	},

	close: function(){
		this.getView().close();
	}
});