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
Ext.define('Ssp.controller.person.InstantCaseloadAssignmentViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	appointmentService: 'appointmentService',
    	appEventsController: 'appEventsController',
    	apiProperties: 'apiProperties',
     	appointment: 'currentAppointment',
     	formUtils: 'formRendererUtils',
        person: 'currentPerson',
        personLite: 'personLite',
        personService: 'personService',
        personProgramStatusService: 'personProgramStatusService',
        currentPersonAppointment: 'currentPersonAppointment',
        studentTypesStore: 'studentTypesAllUnpagedStore',
		configurationOptionsUnpagedStore: 'configurationOptionsUnpagedStore'
    },
	config: {
		panelKids: null
	},
    control: {
		
    	'saveButton':{
    		click: 'onSaveClick'
    	},
    	
    	'cancelButton': {
    		click: 'onCancelClick'
    	}
    },
    
	init: function() {
		var me=this;

		me.resetAppointmentModels();
		
		//Loading store here for coach, student_type, and appointment combos due to timing issue between separate controllers
		me.studentTypesStore.addListener("load", me.onStoreLoaded, me, {single:true});
		me.getView().setLoading(true);
		me.studentTypesStore.load(); 
		
		return this.callParent(arguments);
    },

	onStoreLoaded: function(){
		var me = this;
		me.getView().setLoading(false);
		me.appEventsController.assignEvent({eventName: 'instantStudentNameChange', callBackFunc: this.onPersonNameChange, scope: this}); 
	},

	resetAppointmentModels: function() {
		var me=this;
		// initialize the appointment and personAppointment
		var personAppointment = new Ssp.model.PersonAppointment();
		var appointment = new Ssp.model.Appointment();
		me.appointment.data = appointment.data;
		me.currentPersonAppointment.data = personAppointment.data;
	},
	
    initForms: function(){
		
		var caseloadAssignmentView = Ext.ComponentQuery.query('#student')[0];
		
		
    },

    onPersonNameChange: function(){
    	this.updateTitle();
    },
    
    updateTitle: function(){
    	var me=this;
    	me.getView().setTitle( 'Caseload Assignment ' + ((me.person.get('id') != "")?"Edit":"Add") + ' - ' + me.person.getFullName());
    },
    
    onSaveClick: function(button){
		var me=this;
		me.studentTypesStore.clearFilter(true);
		me.doSave();
	},

	// record saving heavy-lifting, independent of any particular event
	doSave: function() {
		var me=this;
		var model=me.person;
		var id = model.get('id');
		var jsonData = new Object();
		var currentPersonAppointment;
		
		
		
		// edit person view
		var personView = Ext.ComponentQuery.query('.editperson')[0];
		var personForm = personView.getForm();

		// coach view
		var coachView = Ext.ComponentQuery.query('.personcoach')[0];
		var coachForm = coachView.getForm();	
		
		// appointment view
		var appointmentView = Ext.ComponentQuery.query('.personappointment')[0];
		var appointmentForm = appointmentView.getForm();


		var formsToValidate = [personForm,
						appointmentForm,
	                 	coachForm];		

		var validateResult = me.formUtils.validateForms( formsToValidate );
		
		// Validate all of the forms
		if ( validateResult.valid ) 
		{
			
			personForm.updateRecord();	

            var coachID = coachForm.findField('coachId').getValue();
            var studentTypeID = appointmentForm.findField('studentTypeId').getValue();

			//set coach and student type
			model.setCoachId( coachID );
			model.setStudentTypeId( studentTypeID );
			// update the appointment
			appointmentForm.updateRecord();
			

			me.getView().setLoading( true );
			
			// ensure props are null if necessary
			jsonData = model.setPropsNullForSave( model.data );
			
			me.personService.save( jsonData, 
	    			               {success:me.savePersonSuccess, 
				                    failure:me.savePersonFailure,
				                    statusCode: {
				                      409: me.savePersonConflict
				                    },
				                    scope: me} );

		}else{
			me.getView().setLoading( false );
			me.formUtils.displayErrors( validateResult.fields );
		}
    },
    
    savePersonSuccess: function( r, scope ){
		var me=scope;
		var personProgramStatus;

    	if (r.id != "")
		{
    		// new student save an Active program status
    		// non-new student w/o status (Early Alert generated but not sent, or logged in, etc) also gets Active status
    		if ( !(r.currentProgramStatusName) )
    		{
    			personProgramStatus = new Ssp.model.PersonProgramStatus();
    			personProgramStatus.set('programStatusId',Ssp.util.Constants.ACTIVE_PROGRAM_STATUS_ID);
    			personProgramStatus.set('effectiveDate', Ext.Date.now() );
    			me.personProgramStatusService.save( 
    					r.id, 
    					personProgramStatus.data, 
    					{
    				success: me.saveProgramStatusSuccess,
                    failure: me.saveProgramStatusFailure,
                    scope: me 
                });
    		} else {
                me.saveProgramStatusSuccessForPersonId(r.id);
    		}
		}else{
			me.getView().setLoading( false );
			Ext.Msg.alert('Error','Error saving student record. Please see your administrator for additional details.');
		}    	
    },
    
    savePersonFailure: function( response, scope ){
    	var me=scope;
		
		var dialogOpts = {
			buttons: Ext.Msg.OK,
			icon: Ext.Msg.ERROR,
			fn: Ext.emptyFn,
			title: 'Duplicate Username',
			msg: 'Username already exists.',
			scope: me
		};
		
		
		var parsedResponseText = Ext.decode(response.responseText);
		
		var responseDetail = parsedResponseText.message;
		
		
		
		if(response.status == 500 && responseDetail.indexOf('ERROR: duplicate key value violates unique constraint "unique_person_username"') !== -1){
			Ext.Msg.show(dialogOpts);
		}
		
    	me.getView().setLoading( false );
    },

	savePersonConflict: function( response, scope ) {
		var me=scope;
		me.savePersonFailure(response, scope);
		var dialogOpts = {
			buttons: Ext.Msg.YESNOCANCEL,
			icon: Ext.Msg.WARNING,
			fn: me.resolvePersonConflict,
			scope: me
		};
		var model=me.person;
		
		var parsedResponseText = Ext.decode(response.responseText);
		var responseDetail = parsedResponseText.detail.details;
		var id = model.get('id');
		if ( id ) {
			dialogOpts.title = "Conflicting Student Record Updates";
			dialogOpts.msg = "Your changes did not save because another user" +
				" modified the student record while you were filling out" +
				" this form. Do you want to save your changes anyway?<br/><br/>" +
				"Press 'Yes' to overwrite the existing record with your changes.<br/>" +
				"Press 'No' to discard your changes and load the existing record into this form.<br/>" +
				"Press 'Cancel' to do nothing and resume editing.";
		} else {
			if("ERROR_USERNAME_EXISTING" === responseDetail.error )
			{
				dialogOpts.title = "Student Already on File";
				dialogOpts.msg = "The student record did not save because another" +
					" student record already exists for the specified username." +
					"<br/><br/>" +
					" Conflicted Student Username: " + responseDetail.conflictingUsername +
					"<br/>" +
					" Conflicted Student School ID: " + responseDetail.conflictingSchoolId +
					"<br/>" +
					" Conflicted Student Key: " + responseDetail.conflictingId +
					"<br/><br/>";
				
				    if(responseDetail.conflictingSchoolId !== responseDetail.originalSchoolId)
				    {
				    	dialogOpts.msg = dialogOpts.msg + "Furthermore the school id does not match the expected school ID for the username.  " +
				    	"The expected school id is:  "+ responseDetail.originalSchoolId;
						dialogOpts.buttons = Ext.Msg.OK;
						dialogOptsicon = Ext.Msg.ERROR;
				    }
				    else
				    {
				    	dialogOpts.msg = dialogOpts.msg + " Do you want to save your changes anyway?" +
						"<br/><br/>" +
				    	"Press 'Yes' to overwrite the existing record with your changes.<br/>" +
				    	"Press 'No' to discard your changes and load the existing record into this form.<br/>" +
				    	"Press 'Cancel' to do nothing and resume editing.";
				    }
				dialogOpts.personId = responseDetail.conflictingId;			
			}
			else
			if("ERROR_SCHOOL_ID_EXISTING" === responseDetail.error )
			{
				dialogOpts.title = "Student Already on File";
				dialogOpts.msg = "The student record did not save because another" +
					" student record already exists for the specified school id." +

					
					" Conflicted Student Username: " + responseDetail.conflictingUsername +
					"<br/>" +
					" Conflicted Student School ID: " + responseDetail.conflictingSchoolId +
					"<br/>" +
					" Conflicted Student Key: " + responseDetail.conflictingId +
					"<br/><br/>";

				    if(responseDetail.originalUsername !== responseDetail.conflictingUsername)
				    {
				    	dialogOpts.msg = dialogOpts.msg + "Furthermore the username does not match the expected username for the school ID.  " +
				    	"The expected school id is:  "+ responseDetail.originalSchoolId;
						dialogOpts.buttons = Ext.Msg.OK;
						dialogOptsicon = Ext.Msg.ERROR;
				    }
				    else
				    {
				    	dialogOpts.msg = dialogOpts.msg + " Do you want to save your changes anyway?" +
						"<br/><br/>" +
				    	"Press 'Yes' to overwrite the existing record with your changes.<br/>" +
				    	"Press 'No' to discard your changes and load the existing record into this form.<br/>" +
				    	"Press 'Cancel' to do nothing and resume editing.";
				    }
				dialogOpts.personId = responseDetail.conflictingId;			
			}				
		}
		Ext.Msg.show(dialogOpts);
	},

	parseConflictingPersonId: function(response) {
		var me=this;
		if ( !(response.responseText) ) {
			return null;
		}
		var parsedResponseText = Ext.decode(response.responseText);
		var responseDetail = parsedResponseText.detail;
		if ( !(responseDetail) ) {
			return null;
		}
		var attemptedLookupTypeInfo = responseDetail.typeInfo;
		if ( !(attemptedLookupTypeInfo) ) {
			return null;
		}
		var attemptedLookupType = attemptedLookupTypeInfo.name;
		if ( !(attemptedLookupType) || "org.jasig.ssp.model.Person" !== attemptedLookupType ) {
			return null;
		}
		var lookupFields = responseDetail.lookupFields;
		if ( !(lookupFields) ) {
			return null;
		}
		return lookupFields.id || null;
	},

	resolvePersonConflict: function(buttonId, text, opt) {
		var me=this;
		if (buttonId === "yes") {
			// User elected to blindly overwrite persistent record w/ current
			// form. Even so, need to pull back the existing record b/c we still
			// need to get the correct username. Do not want the silently
			// calculated default username to override the externally-provided
			// value or a value set by another user.
			me.personService.get( opt.personId, {success:me.doBlindOverwriteOfPersistentPerson,
				failure: me.getPersonFailure, // TODO need to lock form somehow - do *not* want to send the wrong username
				scope: me} );

		} else if ( buttonId === "no" ) {
			// User elected to discard current edits and reload persistent
			// record. Same concerns here w/r/t making sure we're saving
			// over the correct back-end record.
			me.personService.get( opt.personId, {success:me.doReloadWithExistingPersonRecord,
				failure: me.getPersonFailure, // TODO need to lock form somehow - do *not* want to send the wrong username
				scope: me} );
		} else {
			// nothing to do
		}
	},

	doBlindOverwriteOfPersistentPerson: function(r, scope) {
		var me = scope;
		if (!(me.assertMatchingSchoolIds(r))) {
			return;
		}
		me.person.set('id', r.id);
		me.person.data.username = r.username;
		me.doSave();
	},

	doReloadWithExistingPersonRecord: function(r, scope) {
		var me = scope;
		if (!(me.assertMatchingSchoolIds(r))) {
			return;
		}
		// Do this with basically the same mechanism that
		// SearchViewController.js does to launch the edit form in the first
		// place. (Would be a huge patch to get each individual form to
		// reset/reload itself so we just reload the entire view.)
		var model = new Ssp.model.Person();
		me.person.data = model.data;
		me.personLite.set('id', r.id);
		me.resetAppointmentModels();
		me.formUtils.loadDisplay('mainview', 'caseloadassignment', true, {flex:1});
	},

	assertMatchingSchoolIds: function(personLookupResult) {
		var me = this;
		if ( me.person.data.schoolId ) {
			if ( me.person.data.schoolId.toUpperCase() !== personLookupResult.schoolId.toUpperCase() ) {
				// would usually only happen if there's not a record on
				// file with the proposed schoolID, but there is a record
				// on file with the silently calculated username
				Ext.Msg.alert("Form Save Error",
					"Could not overwrite the existing record" +
					" because the system might have found multiple" +
					" conflicting records or detected it was at risk of" +
					" updating the wrong record your edits. Please contact" +
					" your system administrators.");
				return false;
			}
		}
		return true;
	},
    
    saveProgramStatusSuccess: function( r, scope ){
        var me=scope;
        me.saveProgramStatusSuccessForPersonId(r.personId);
    },

    saveProgramStatusSuccessForPersonId: function( personId ) {
        var me = this;
        // reload the created person now that it's been fully initialized on the back end
        me.personService.get(personId, {
            success: me.createdPersonReloadSuccess,
            failure: me.createdPersonReloadFailure,
            scope: me
        });
    },

    saveProgramStatusFailure: function( response, scope ){
        var me=scope;
        me.getView().setLoading( false );
        Ext.Msg.alert('Error','Error saving student record. Please see your administrator for additional details.');
    },

    createdPersonReloadSuccess: function(r, scope) {
        var me=scope;
        // Regenerating the singleton Person's internal
        // data structure ensures populateFromGenericObject() doesn't
        // skip meaningful response fields. E.g. there are click
        // paths that result in the current singleton Person's
        // internal data structure losing the currentProgramStatusName
        // field. And populateFromGenericObject() uses the *target*
        // object's fields to copy values out of the response, not
        // the response object's fields. So in that case currentProgramStatusName
        // simply won't be set on the singleton Person even if the
        // current response contains that field. There's a very similar
        // workaround for this problem
        // in EditPersonViewController.getPersonSuccess().
        var cleanPerson = new Ssp.model.Person();
        me.person.data = cleanPerson.data;
        me.person.populateFromGenericObject( r );
        me.getView().setLoading( false );

        var dialogOpts = {
            buttons: Ext.Msg.OK,
            icon: Ext.Msg.INFO,
            fn: Ext.emptyFn,
            title: '',
            msg: 'Student Information updated successfully',
            scope: me
        };
        Ext.Msg.show(dialogOpts);
        me.getView().close();
        me.appEventsController.getApplication().fireEvent('updateSearchStoreRecord',{person:me.person});
        me.appEventsController.getApplication().fireEvent('loadPerson');
    },

    createdPersonReloadFailure: function(r, scope) {
        var me=scope;
        me.getView().setLoading( false );
        Ext.Msg.alert('Error','Error saving student record. Please see your administrator for additional details.');
    },
    
    getSelectedItemSelectorIdsForTransfer: function(values){
		var selectedIds = new Array();
    	Ext.Array.each(values,function(name, index){
    		if ( name != undefined )
    		{
        		selectedIds.push({"id":name});    			
    		}
		});
    	return selectedIds;
    }, 
    
    onCancelClick: function(button){
		//Commented out for SSP-2720
		//this.person.set('id','');
		//this.personLite.set('id','');
		//this.appEventsController.getApplication().fireEvent('displayStudentRecordView');
    	this.getView().close();
    },
 
    destroy: function() {
        var me=this;
        me.studentTypesStore.clearFilter(true);    
    	me.appEventsController.removeEvent({eventName: 'instantStudentNameChange', callBackFunc: this.onPersonNameChange, scope: this});
		me.studentTypesStore.removeListener("load", me.onStoreLoaded, me, {single:true});

    	return this.callParent( arguments );
    }
});
