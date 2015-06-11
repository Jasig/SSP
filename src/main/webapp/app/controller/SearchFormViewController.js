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
Ext.define('Ssp.controller.SearchFormViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
        appEventsController: 'appEventsController',
        authenticatedPerson: 'authenticatedPerson',
        exportService: 'exportService',
        formUtils: 'formRendererUtils',
        programStatusesStore: 'programStatusesStore',
        programStatusService: 'programStatusService',
        searchService: 'searchService',
        coachesStore: 'coachesStore',
        configStore: 'configurationOptionsUnpagedStore',
        searchStore: 'directoryPersonSearchStore'
    },
    control: {
    	view: {
			afterlayout: {
				fn: 'onAfterLayout',
				single: true
			},
            show: 'onShow',
            hide: 'onHide'
    	},
		'myPlans':{
			specialkey: "specialKeyPressed"
		},
		 'myWatchList':{
			specialkey: "specialKeyPressed"
		},
		'myCaseload':{
			specialkey: "specialKeyPressed"
		},
		'schoolId':{
			specialkey: "specialKeyPressed"
		},
		'personTableType':{
			specialkey: "specialKeyPressed"
		},
		'birthDate':{
			specialkey: "specialKeyPressed"
		},
		'programStatus':{
			specialkey: "specialKeyPressed"
		},
		'currentlyRegistered':{
			specialkey: "specialKeyPressed"
		},
		'specialServiceGroup':{
			specialkey: "specialKeyPressed"
		},
		'actualStartTerm':{
			specialkey: "specialKeyPressed"
		},
		'declaredMajor':{
			specialkey: "specialKeyPressed"
		},
		'coachId':{
			specialkey: "specialKeyPressed"
		},
		'earlyAlertResponseLate':{
			specialkey: "specialKeyPressed"
		},
		'financialAidSapStatusCode':{
			specialkey: "specialKeyPressed"
		},
		'planStatus':{
			specialkey: "specialKeyPressed"
		},
		'planExists':{
			specialkey: "specialKeyPressed"
		},
		'hoursEarnedMin':{
			specialkey: "specialKeyPressed",
			blur: "hoursEarnedMinChanged"
		},
		'hoursEarnedMax':{
			specialkey: "specialKeyPressed",
			blur: 'hoursEarnedMaxChanged'
		},
		'gpaMin':{
			specialkey: "specialKeyPressed",
			blur: 'gpaMinChanged'
		},
		'gpaMax':{
			specialkey: "specialKeyPressed",
			blur: 'gpaMaxChanged'
		},
		'localGpaMin':{
			specialkey: "specialKeyPressed",
			blur: 'gpaMinChanged'
		},
		'localGpaMax':{
			specialkey: "specialKeyPressed",
			blur: 'gpaMaxChanged'
		},
		'programGpaMin':{
			specialkey: "specialKeyPressed",
			blur: 'gpaMinChanged'
		},
		'programGpaMax':{
			specialkey: "specialKeyPressed",
			blur: 'gpaMaxChanged'
		},
		'schoolId': {
			specialkey: "specialKeyPressed"
		}
    },

	init: function() {
		var me=this;    	
		me.coachesStore.clearFilter(true);
		return me.callParent(arguments);
    },

	onAfterLayout: function(comp, eobj) {
		var me=this;
		me.registerBeforeTabChange();
		me.appEventsController.assignEvent({eventName: 'onStudentSearchRequested', callBackFunc: me.onSearch, scope: me});
		me.appEventsController.assignEvent({eventName: 'onSearchActionComboSelect', callBackFunc: me.onSearchActionComboSelect, scope: me});

	   	// load program statuses
		me.getProgramStatuses();	
	},

    destroy: function() {
    	var me=this;
    	me.deRegisterBeforeTabChange();
    	me.appEventsController.removeEvent({eventName: 'onStudentSearchRequested', callBackFunc: me.onSearch, scope: me});
		me.appEventsController.removeEvent({eventName: 'onSearchActionComboSelect', callBackFunc: me.onSearchActionComboSelect, scope: me});

        if ( me.emailStudentPopup ) {
            me.emailStudentPopup.destroy();
        }

        if ( me.customizableExportPopup ) {
            me.customizableExportPopup.destroy();
        }

        me.clear();
        me.searchStore.removeAll();

	   	return me.callParent( arguments );
    },

	getThisTab: function() {
		var me = this;
		return me.getView().tab;
	},

	getTabPanel: function() {
		var me = this;
		return me.tabPanel = me.tabPanel || me.getView().tabPanelAccessor();
	},

	// All of this searchGridPager show/hide stuff is an attempt to ensure the entire search form renders on
	// a 768 vertical resolution. See SearchForm.js for more notes
	getSearchGridPager: function() {
		var me = this;
		return me.getThisTab().queryById('searchGridPager');
	},

	hideSearchGridPager: function() {
		var me = this;
		me.getSearchGridPager().hide();
	},

	showSearchGridPager: function() {
		var me = this;
		me.getSearchGridPager().show();
	},

	onShow: function() {
		var me = this;
		me.hideSearchGridPager();
	},

	onHide: function() {
		var me = this;
		me.showSearchGridPager();
	},

	registerBeforeTabChange: function() {
		var me = this;
		if ( me.getTabPanel() ) {
			me.getTabPanel().on('beforetabchange', me.onBeforeTabChange, me);
		}
	},

	deRegisterBeforeTabChange: function() {
		var me = this;
		if ( me.getTabPanel() ) {
			me.getTabPanel().un('beforetabchange', me.onBeforeTabChange, me);
		}
	},

	onBeforeTabChange: function( tabPanel, newCard, oldCard, eOpts ) {
		var me = this;
		if ( newCard.items.items[0] === me.getThisTab() && !(me.getView().isHidden())) {
			me.hideSearchGridPager();
		}
	},

    searchSuccess: function( r, scope){
    	var me=scope;
    	me.searchStore.pageSize = me.searchStore.data.length;
    	me.getView().setLoading( false );
		me.getView().hide();
		me.appEventsController.getApplication().fireEvent("onPersonSearchSuccess");
    },

    exportSearchSuccess: function( r, scope){
    	var me=scope;
    },

	search: function(){
		var me=this;		
		me.searchService.search2( 
				me.textFieldValueFromName('schoolId'),
				me.textFieldValueFromName('firstName'),
				me.textFieldValueFromName('lastName'),
				me.getView().query('combobox[name=programStatus]')[0].value,
				me.getView().query('combobox[name=specialServiceGroup]')[0].value,
				me.getView().query('combobox[name=coachId]')[0].value,				
				me.getView().query('combobox[name=declaredMajor]')[0].value,
				me.getView().query('numberfield[name=hoursEarnedMin]')[0].value,
				me.getView().query('numberfield[name=hoursEarnedMax]')[0].value,			
				me.getView().query('numberfield[name=gpaMin]')[0].value,
				me.getView().query('numberfield[name=gpaMax]')[0].value,
				me.getView().query('numberfield[name=localGpaMin]')[0].value,
				me.getView().query('numberfield[name=localGpaMax]')[0].value,
                me.getView().query('numberfield[name=programGpaMin]')[0].value,
                me.getView().query('numberfield[name=programGpaMax]')[0].value,
				me.getView().query('combobox[name=currentlyRegistered]')[0].value,
				me.getView().query('combobox[name=earlyAlertResponseLate]')[0].value,
				me.getView().query('combobox[name=financialAidSapStatusCode]')[0].value,
				me.getView().query('combobox[name=planStatus]')[0].value,
				me.getView().query('combobox[name=planExists]')[0].value,
				me.getView().query('checkbox[name=myCaseload]')[0].value,
				me.getView().query('checkbox[name=myPlans]')[0].value,
				me.getView().query('checkbox[name=myWatchList]')[0].value,
				me.dateFieldValueFromName('birthDate'),
				me.getView().query('combobox[name=actualStartTerm]')[0].value,
				me.getView().query('combobox[name=personTableType]')[0].value,
				{
				success: me.searchSuccess,
				failure: me.searchFailure,
				scope: me
		});	
		
	},

	dateFieldValueFromName: function(name){
		var me =  this;
		var value = me.getView().query('datefield[name=' + name + ']')[0].value;
		if (value !== null && value !== undefined && !(value === "")) {
			return me.formUtils.toJSONStringifiableDate(value).formattedStr;
		}
		return null;
	},

	textFieldValueFromName: function(name){
		var me =  this;
		var value = me.getView().query('textfield[name=' + name + ']')[0].value;
		return Ext.String.trim(value ? value: "");
	},
	
	clear: function() {
		var me=this;
		me.getView().getForm().reset();
	},

	getProgramStatuses: function(){
		var me=this;
		me.programStatusService.getAll({
			success:me.getProgramStatusesSuccess, 
			failure:me.getProgramStatusesFailure, 
			scope: me
	    });
	},

	getProgramStatusesSuccess: function( r, scope){
    //	var me=scope;                               //Commented out useless
    //	var activeProgramStatusId = "";
    //	var programStatus;
    //	if ( me.programStatusesStore.getCount() > 0) {      TODO: Finish attempt here or remove
    //	}
    },	
    getProgramStatusesFailure: function( r, scope){
    	//var me=scope;                       //Commented out useless  TODO: Finish attempt here or remove
    },
   
	// copy/paste from SearchViewController
	onBulkProgramStatusChangeFailure: function(resp) {
		var me = this;
		me.appEventsController.loadMaskOff();
		if ( resp && resp.responseText ) {
			// Big chunk of this messaging copy/pasted from EmailStudentForm
			var rspTextStruct = Ext.decode(resp.responseText);
			if ( rspTextStruct.message && rspTextStruct.message.indexOf("Person search parameters matched no records") > -1 ) {
				Ext.Msg.alert('SSP Error','No user records matched your current search criteria. <br/><br/>' +
					'Retry with different search criteria.');
			} else {
				Ext.Msg.alert('SSP Error','There was an issue procesing your bulk program status change request. Please contact your administrator');
			}
		} else {
			Ext.Msg.alert('SSP Error','There was an issue procesing your bulk program status change request. Please contact your administrator');
		}
	},

	// copy/paste from SearchViewController
	onBulkProgramStatusChangeSuccess: function() {
		var me = this;
		me.appEventsController.loadMaskOff();
		Ext.Msg.alert('Bulk Program Status Change Request Queued','Your bulk Program Status change request has ' +
			'been queued successfully. Bulk changes are processed gradually and may not be reflected immediately on-screen.');
	},

	// copy/paste from SearchViewController except for 'criteria', which is passed in instead of being built just in time
	doBulkProgramStatusChange: function(action, criteria, programStatusChangeReasonId, programStatusChangeReasonName) {
		var me = this;

		var model = Ext.create('Ssp.model.BulkProgramStatusChangeRequest', {
			programStatusSpec: Ext.create('Ssp.model.PersonProgramStatus', {
				programStatusId: me.translateProgramStatusActionToDomainId(action),
				programStatusChangeReasonId: programStatusChangeReasonId === undefined ? null : programStatusChangeReasonId
			}).data, // if you just attach the model itself, the way we do JSON serialization will result in a whole bunch
			// of Ext.js infrastructure fields being sent
			criteria: criteria
		});

		var url = me.apiProperties.createUrl( me.apiProperties.getItemUrl('bulk') )+'/programStatus';
		var jsonData = model.data;

		me.apiProperties.makeRequest({
			url: url,
			method: 'POST',
			jsonData: jsonData,
			successFunc: me.onBulkProgramStatusChangeSuccess,
			failureFunc: me.onBulkProgramStatusChangeFailure,
			scope: me
		});
	},

	// copy/paste from SearchViewController except for 'criteria'
	newDoBulkProgramStatusChange: function(action, criteria) {
		var me = this;
		return function(programStatusChangeReasonId, programStatusChangeReasonName) {
			me.doBulkProgramStatusChange(action, criteria, programStatusChangeReasonId, programStatusChangeReasonName);
		}
	},

	// copy/paste from SearchViewController except for 'criteria'
	cancelBulkProgramStatusChange: function(action, criteria) {
		var me = this;
		me.appEventsController.loadMaskOff();
	},

	// copy/paste from SearchViewController except for 'criteria'
	newCancelBulkProgramStatusChange: function(action, criteria) {
		var me = this;
		return function() {
			me.cancelBulkProgramStatusChange(action, criteria);
		}
	},

	// copy/paste from SearchViewController except for 'criteria'
	bulkProgramStatusChange: function(action, criteria) {
		var me = this;
		me.appEventsController.loadMaskOn();
		if(action === 'PROGRAM_STATUS_NON_PARTICIPATING') {
			var dialog = Ext.create('Ssp.view.ProgramStatusChangeReasonWindow', {
				height: 150,
				width: 500,
				isBulk: true,
				actionCallbacks: {
					ok: me.newDoBulkProgramStatusChange(action, criteria),
					cancel: me.newCancelBulkProgramStatusChange(action, criteria),
					scope: me
				}
			}).show();
			if ( !(dialog) || !(dialog.isVisible()) ) {
				if ( dialog && Ext.isFunction(dialog.destroy) ) {
					dialog.destroy();
				}
				me.cancelBulkProgramStatusChange(action, criteria);
			} // otherwise dialog takes care of destroying itself.
		} else {
			me.doBulkProgramStatusChange(action, criteria);
		}
	},

	// copy/paste from SearchViewController
	onBulkWatchChangeFailure: function(resp, action) {
		var me = this;
		var displayName = me.translateWatchActionToDisplayName(action);
		me.appEventsController.loadMaskOff();
		if ( resp && resp.responseText ) {
			// Big chunk of this messaging copy/pasted from EmailStudentForm
			var rspTextStruct = Ext.decode(resp.responseText);
			if ( rspTextStruct.message && rspTextStruct.message.indexOf("Person search parameters matched no records") > -1 ) {
				Ext.Msg.alert('SSP Error','No user records matched your current search criteria. <br/><br/>' +
					'Retry with different search criteria.');
			} else {
				Ext.Msg.alert('SSP Error','There was an issue procesing your bulk ' + displayName + ' request. Please contact your system administrator');
			}
		} else {
			Ext.Msg.alert('SSP Error','There was an issue procesing your bulk ' + displayName + ' request. Please contact your system administrator');
		}
	},

	// copy/paste from SearchViewController
	onBulkWatchChangeSuccess: function(action) {
		var me = this;
		var displayName = me.translateWatchActionToDisplayName(action);
		me.appEventsController.loadMaskOff();

		Ext.Msg.alert('Bulk ' + displayName + ' Request Queued','Your bulk ' + displayName + ' request has ' +
			'been queued successfully. Bulk changes are processed gradually and may not be reflected immediately on-screen.');
	},

	// copy/paste from SearchViewController
	newOnBulkWatchChangeFailure: function(action) {
		var me = this;
		return function(resp) {
			me.onBulkWatchChangeFailure(resp, action);
		}
	},

	// copy/paste from SearchViewController
	newOnBulkWatchChangeSuccess: function(action) {
		var me = this;
		return function() {
			me.onBulkWatchChangeSuccess(action);
		}
	},

	// copy/paste from SearchViewController except for 'criteria'
	bulkWatchChange: function(action, criteria) {
		var me = this;
		me.appEventsController.loadMaskOn();

		var model = {
			watchSpec: {
				operation: me.translateWatchActionToDomainOperation(action),
				watcherId: me.authenticatedPerson.get('id')
			},
			criteria: criteria
		};

		var url = me.apiProperties.createUrl( me.apiProperties.getItemUrl('bulk') )+'/watch';
		var jsonData = model;

		me.apiProperties.makeRequest({
			url: url,
			method: 'POST',
			jsonData: jsonData,
			successFunc: me.newOnBulkWatchChangeSuccess(action),
			failureFunc: me.newOnBulkWatchChangeFailure(action),
			scope: me
		});
	},

	bulkEmail: function(criteria){
		var me=this;
		var store = null;
		if ( me.emailStudentPopup ) {
			me.emailStudentPopup.destroy();
		}
		me.emailStudentPopup = Ext.create('Ssp.view.EmailStudentView', {
			isBulk: true,
			bulkCriteria: criteria
		});
		me.emailStudentPopup.show();
	},

	// Similar purpose to function with same name SearchViewController, but exporting a search result as opposed
	// to caseload/watchlist involves completely different URL building
	exportSearch: function(criteria) {
		var me = this;

		if ( criteria.limit === undefined || criteria.limit === null ) {
			criteria.limit = -1;
		}
		window.open(me.exportService.buildExportSearchUrl(criteria),'_blank');
	},

	// Customizable Search Export
	exportCustomizableSearch: function(criteria) {
		var me = this;

        if ( me.customizableExportPopup ) {
            me.customizableExportPopup.destroy();
        }

        me.customizableExportPopup = Ext.create('Ssp.view.CustomizableExportView', {
            bulkCriteria: criteria
        });

        me.customizableExportPopup.show();
	},

	// copy/paste from SearchViewController except for 'criteria'
	onExportConfirm: function(btnId, criteria){
		var me=this;
		if (btnId=="ok") {
			me.exportSearch(criteria);
		}
	},

	onCustomizableExportConfirm: function(btnId, criteria){
		var me=this;
		if (btnId=="ok") {
			me.exportCustomizableSearch(criteria);
		}
	},

	// copy/paste from SearchViewController except for 'criteria'
	onBulkEmailConfirm: function(btnId, criteria) {
		var me = this;
		if (btnId=="ok") {
			me.bulkEmail(criteria);
		}
	},

	newOnExportConfirm: function (criteria) {
		var me = this;
		return function(btnId) {
			me.onExportConfirm(btnId, criteria);
		}
	},

	newOnCustomExportConfirm: function (criteria) {
		var me = this;
		return function(btnId) {
			me.onCustomizableExportConfirm(btnId, criteria);
		}
	},

	newOnBulkEmailConfirm: function (criteria) {
		var me = this;
		return function(btnId) {
			me.onBulkEmailConfirm(btnId, criteria);
		}
	},

	// copy/paste from SearchViewController except for 'criteria', which requires special currying
	onBulkProgramStatusChangeConfirm: function(btnId, action, criteria) {
		var me = this;
		if (btnId=="ok") {
			me.bulkProgramStatusChange(action, criteria);
		}
	},

	// copy/paste from SearchViewController except for 'criteria', which requires special currying
	onBulkWatchChangeConfirm: function(btnId, action, criteria) {
		var me = this;
		if (btnId=="ok") {
			me.bulkWatchChange(action, criteria);
		}
	},

	// copy/paste from SearchViewController except for 'criteria', which requires special currying
	newOnBulkProgramStatusChangeConfirm: function(action, criteria) {
		var me = this;
		return function(btnId) {
			me.onBulkProgramStatusChangeConfirm(btnId, action, criteria);
		}
	},

	// copy/paste from SearchViewController except for 'criteria', which requires special currying
	newOnBulkWatchChangeConfirm: function(action, criteria) {
		var me = this;
		return function(btnId) {
			me.onBulkWatchChangeConfirm(btnId, action, criteria);
		}
	},

	// copy/paste from SearchViewController except for 'criteria', which requires special currying into the
	// ok/cancel dialog callback, and slightly different search/export invocation.
	promptWithExportCount: function(count, criteria) {
		var me = this;
		var message;
		count = parseInt(count);
		// loadMaskOff() copy/pasted in both prompt*() functions to try to delay that dismissal as long
		// as possible... let all 'background' lookup and computation complete before we re-engage the UI
		me.appEventsController.loadMaskOff();

		Ext.Msg.confirm({
			title:'Confirm',
			msg: count + " student/s will be exported. Continue?",
			buttons: Ext.Msg.OKCANCEL,
			fn: me.newOnExportConfirm(criteria),
			scope: me
		});

	},

	promptWithCustomizableExportCount: function(count, criteria) {
		var me = this;
		var message;
		count = parseInt(count);
		// loadMaskOff() copy/pasted in both prompt*() functions to try to delay that dismissal as long
		// as possible... let all 'background' lookup and computation complete before we re-engage the UI
		me.appEventsController.loadMaskOff();

		Ext.Msg.confirm({
			title:'Confirm',
			msg: count + " student/s will be custom exported. Continue?",
			buttons: Ext.Msg.OKCANCEL,
			fn: me.newOnCustomExportConfirm(criteria),
			scope: me
		});

	},

	// copy/paste from SearchViewController except for 'criteria', which requires special currying into the
	// ok/cancel dialog callback, and minor messaging differences
	promptWithBulkEmailCount: function(count, criteria) {
		var me = this;
		count = parseInt(count);
		var maxEmail =  parseInt(me.configStore.getConfigByName('mail_bulk_message_limit').trim());
		// loadMaskOff() copy/pasted in both prompy*() functions to try to delay that dismissal as long
		// as possible... let all 'background' lookup and computation complete before we re-engage the UI
		me.appEventsController.loadMaskOff();
		if(maxEmail > 0 && count > maxEmail) {
			Ext.Msg.alert('Too Many Search Results','The number of students in your request ('+count+') exceed the ' +
				'bulk email limit ('+maxEmail+'). <br/><br/>Consider exporting results to a CSV file and using that ' +
				'file as input to a third party bulk email application.');
			return;
		} else if ( count === 0 ) {
			Ext.Msg.alert('Too Few Search Results','Cannot send bulk email to an empty search result.');
			return;
		} else {
			Ext.Msg.confirm({
				title:'Confirm',
				msg: count + " student/s will be emailed. Continue?",
				buttons: Ext.Msg.OKCANCEL,
				fn: me.newOnBulkEmailConfirm(criteria),
				scope: me
			});
		}
	},

	// copy/paste from SearchViewController except for 'criteria'
	promptWithBulkProgramStatusChangeCount: function(count, action, criteria) {
		var me = this;
		var message;
		count = parseInt(count);
		var programStatusName = me.translateProgramStatusActionToDisplayName(action);
		// loadMaskOff() copy/pasted in both prompy*() functions to try to delay that dismissal as long
		// as possible... let all 'background' lookup and computation complete before we re-engage the UI

		me.appEventsController.loadMaskOff();
		if ( programStatusName === null ) {
			Ext.Msg.alert('SSP Error', 'Unrecognized target Program Status.');
			return;
		}
		if ( count === 0 ) {
			Ext.Msg.alert('Too Few Search Results','Cannot change Program Status on an empty search result.');
			return;
		}
		var msg = count + " user/s will be considered for transition to the '" + programStatusName + "' status. Users " +
			"already having that status and users who haven't already been created will be unaffected. Continue?";
		if(action === 'PROGRAM_STATUS_NON_PARTICIPATING'){
			msg = count + " user/s will be considered for transition to the '" + programStatusName + "' status. Users " +
				"already having that status and the Reason selected in the next dialog will be unaffected, as will users " +
				"who haven't already been created. Continue?";
		}
		Ext.Msg.confirm({
			title:'Confirm',
			msg: msg,
			buttons: Ext.Msg.OKCANCEL,
			fn: me.newOnBulkProgramStatusChangeConfirm(action, criteria),
			scope: me
		});
	},

	// copy/paste from SearchViewController except for 'criteria'
	promptWithBulkWatchChangeCount: function(count, action, criteria) {
		var me = this;
		var message;
		count = parseInt(count);
		var watchActionName = me.translateWatchActionToDisplayName(action);
		// loadMaskOff() copy/pasted in all prompt*() functions to try to delay that dismissal as long
		// as possible... let all 'background' lookup and computation complete before we re-engage the UI

		me.appEventsController.loadMaskOff();
		if ( watchActionName === null ) {
			Ext.Msg.alert('SSP Error', 'Unrecognized Watch action.');
			return;
		}
		if ( count === 0 ) {
			Ext.Msg.alert('Too Few Search Results','Cannot ' + watchActionName + ' an empty caseload/watchlist/search result.');
			return;
		}
		var msg = count + " user/s will be considered for " + watchActionName + ". Users " +
			"who haven't already been created will be unaffected. Continue?";
		Ext.Msg.confirm({
			title:'Confirm',
			msg: msg,
			buttons: Ext.Msg.OKCANCEL,
			fn: me.newOnBulkWatchChangeConfirm(action, criteria),
			scope: me
		});
	},

	// copy/paste from SearchViewController except for 'criteria'
	onBulkActionCountFailure: function(cnt, action, criteria) {
		var me = this;
		me.appEventsController.loadMaskOff();
		Ext.Msg.alert('SSP Error', 'Failed to look up the number of records which would be affected by the ' +
			'requested action. Retry or contact your system administrator');
	},

	// copy/paste from SearchViewController except for 'criteria'
	onBulkActionCountSuccess: function(cnt, action, criteria) {
		var me = this;
		if ( action === 'EXPORT' ) {
			me.promptWithExportCount(cnt, criteria);
		} else if ( action === 'EMAIL' ) {
			me.promptWithBulkEmailCount(cnt, criteria);
		} else if ( action.indexOf('PROGRAM_STATUS_') === 0 ) {
			me.promptWithBulkProgramStatusChangeCount(cnt, action, criteria);
		} else if ( action.indexOf('WATCH') !== -1 ) {
			me.promptWithBulkWatchChangeCount(cnt, action, criteria);
		} else if ( action === 'CUSTOM_EXPORT' ) {
			me.promptWithCustomizableExportCount(cnt, criteria);
		} else {
			Ext.Msg.alert('SSP Error', 'Unrecognized bulk action request');
		}
	},

	// copy/paste from SearchViewController except for 'criteria'
	newBulkActionCountResultFailureCallback: function(action, criteria) {
		var me = this;
		return function(cnt) {
			return me.onBulkActionCountFailure(cnt, action, criteria);
		}
	},

	// copy/paste from SearchViewController except for 'criteria'
	newBulkActionCountResultSuccessCallback: function(action, criteria) {
		var me = this;
		return function(cnt) {
			return me.onBulkActionCountSuccess(cnt, action, criteria);
		}
	},

	// copy/paste from SearchViewController
	translateProgramStatusActionToDisplayName: function(action) {
		switch (action) {
			case 'PROGRAM_STATUS_ACTIVE':
				return Ssp.util.Constants.ACTIVE_PROGRAM_STATUS_NAME;
			case 'PROGRAM_STATUS_INACTIVE':
				return Ssp.util.Constants.INACTIVE_PROGRAM_STATUS_NAME;
			case 'PROGRAM_STATUS_NON_PARTICIPATING':
				return Ssp.util.Constants.NON_PARTICIPATING_PROGRAM_STATUS_NAME;
			case 'PROGRAM_STATUS_NO_SHOW':
				return Ssp.util.Constants.NO_SHOW_PROGRAM_STATUS_NAME;
		}
		return null;
	},

	translateProgramStatusActionToDomainId: function(action) {
		switch (action) {
			case 'PROGRAM_STATUS_ACTIVE':
				return Ssp.util.Constants.ACTIVE_PROGRAM_STATUS_ID;
			case 'PROGRAM_STATUS_INACTIVE':
				return Ssp.util.Constants.INACTIVE_PROGRAM_STATUS_ID;
			case 'PROGRAM_STATUS_NON_PARTICIPATING':
				return Ssp.util.Constants.NON_PARTICIPATING_PROGRAM_STATUS_ID;
			case 'PROGRAM_STATUS_NO_SHOW':
				return Ssp.util.Constants.NO_SHOW_PROGRAM_STATUS_ID;
		}
		return null;
	},

	translateWatchActionToDomainOperation: function(action) {
		return action;
	},

	translateWatchActionToDisplayName: function(action) {
		switch (action) {
			case 'WATCH':
				return 'Watch';
			case 'UNWATCH':
				return 'Unwatch';
		}
		return null;
	},

	onSearchActionComboSelect: function(records){
		var me=this;

		if(!me.getView().getForm().isDirty()) {
			Ext.Msg.alert('SSP Error', 'Please enter some filter values.');
			return;
		}
		if(!me.getView().getForm().isValid()){
			Ext.Msg.alert('SSP Error', 'One or more search filters are invalid. Problems have been highlighted.');
			return;
		}
		var message = "";
		var valuesInvalid = false;

        if (me.checkGpaFields()) {
        	valuesInvalid = me.checkAllGpaMinLessThanMax(message);
        	message = valuesInvalid[1];
        	valuesInvalid = valuesInvalid[0];
		}

		if (me.checkHoursEarnedFields()) {
            if(me.getHoursEarnedMin().getValue() > me.getHoursEarnedMax().getValue()){
                valuesInvalid = true;
                message += "Hours Earned Min is greater than Hours Earned Maximum. ";
            }
        }

		if(valuesInvalid == true){
			Ext.Msg.alert('SSP Error', message + "Search will return no values.");
			return;
		}

		if(records.length > 0) {
			// TODO this really needs to be moved into a modal dialog, else these counts could come back and be
			// completely irrelevant w/r/t whatever the user is looking at currently. The loading mask is not
			// modal, but it's the short-term stopgap measure to try to discourage people from interacting with
			// the UI while we look up the count.
			me.appEventsController.loadMaskOn();

			var criteria = {
				schoolId: me.textFieldValueFromName('schoolId'),
				firstName: me.textFieldValueFromName('firstName'),
				lastName: me.textFieldValueFromName('lastName'),

				coachId: me.getView().query('combobox[name=coachId]')[0].value,
                declaredMajor: me.getView().query('combobox[name=declaredMajor]')[0].value,
                programStatus: me.getView().query('combobox[name=programStatus]')[0].value,
                specialServiceGroup: me.getView().query('combobox[name=specialServiceGroup]')[0].value,
                sapStatusCode: me.getView().query('combobox[name=financialAidSapStatusCode]')[0].value,
                actualStartTerm: me.getView().query('combobox[name=actualStartTerm]')[0].value,

				hoursEarnedMin: me.getView().query('numberfield[name=hoursEarnedMin]')[0].value,
				hoursEarnedMax: me.getView().query('numberfield[name=hoursEarnedMax]')[0].value,
				gpaEarnedMin: me.getView().query('numberfield[name=gpaMin]')[0].value,
				gpaEarnedMax: me.getView().query('numberfield[name=gpaMax]')[0].value,
				currentlyRegistered: me.getView().query('combobox[name=currentlyRegistered]')[0].value == null ? null : new Boolean(me.getView().query('combobox[name=currentlyRegistered]')[0].value).toString(),
				earlyAlertResponseLate: me.getView().query('combobox[name=earlyAlertResponseLate]')[0].value,
				planStatus: me.getView().query('combobox[name=planStatus]')[0].value,
				planExists: me.getView().query('combobox[name=planExists]')[0].value,
				myCaseload: me.getView().query('checkbox[name=myCaseload]')[0].value,
				myPlans: me.getView().query('checkbox[name=myPlans]')[0].value,
				myWatchList: me.getView().query('checkbox[name=myWatchList]')[0].value,
				birthDate: me.dateFieldValueFromName('birthDate'),
				personTableType: me.getView().query('combobox[name=personTableType]')[0].value
			}

			me.searchService.searchCountWithParams(criteria,
				{
					success: me.newBulkActionCountResultSuccessCallback(records[0].get('id'), criteria),
					failure: me.newBulkActionCountResultFailureCallback(records[0].get('id'), criteria),
					scope: me
				}
			);
		}
	},

	onSearch: function() {
		var me = this;

		if (!me.getView().getForm().isDirty()) {
	     	Ext.Msg.alert('SSP Error', 'Please enter some filter values.'); 
	     	return;
		}
		if(!me.getView().getForm().isValid()) {
			Ext.Msg.alert('SSP Error', 'One or more search filters are invalid. Problems have been highlighted.');
			return;
		}
		var message = "";
		var valuesInvalid = false;

		if (me.checkGpaFields()) {
        	valuesInvalid = me.checkAllGpaMinLessThanMax(message);
            message = valuesInvalid[1];
        	valuesInvalid = valuesInvalid[0];
        }

        if (me.checkHoursEarnedFields()) {
            if(me.getHoursEarnedMin().getValue() > me.getHoursEarnedMax().getValue()){
                    valuesInvalid = true;
                    message += "Hours Earned Min is greater than Hours Earned Maximum. ";
            }
        }
		
		if(valuesInvalid == true){
	     	Ext.Msg.alert('SSP Error', message + "Search will return no values."); 
	     	return;
		}
		me.search();	
	},  
	
	specialKeyPressed: function(field, el){
		var me=this;
		if(el.getKey() == Ext.EventObject.ENTER){
			me.onSearch();
		}
	},
	
	hoursEarnedMinChanged: function(){
		var me=this;
		if (me.checkHoursEarnedFields()) {
            if(me.getHoursEarnedMax().getValue() == null){
                    me.getHoursEarnedMax().setValue(me.getHoursEarnedMin().getValue());
                    return;
            }
            if(me.getHoursEarnedMin().getValue() > me.getHoursEarnedMax().getValue()){
                me.getHoursEarnedMax().setValue(me.getHoursEarnedMin().getValue());
            }
        }
	},
	
	hoursEarnedMaxChanged: function(){
		var me=this;
		if (me.checkHoursEarnedFields()) {
            if(me.getHoursEarnedMax().getValue() == null){
                me.getHoursEarnedMin().setValue(null);
                return;
            }
            if(!me.getHoursEarnedMin().getValue()){
                me.getHoursEarnedMin().setValue(0);
                return;
            }
            if(me.getHoursEarnedMin().getValue() > me.getHoursEarnedMax().getValue()){
                me.getHoursEarnedMin().setValue(me.getHoursEarnedMax().getValue());
            }
        }
	},

	checkHoursEarnedFields: function() {
	    var me=this;
        if (me.getHoursEarnedMin && me.getHoursEarnedMax) {
            return true;
        }
        return false;
	},

	checkGpaFields: function(gpaFieldName){
        var me=this;

        if (gpaFieldName === "local") {
			if (me.getLocalGpaMin && me.getLocalGpaMax) {
				return true;
			}
        } else if (gpaFieldName === "program") {
			if (me.getProgramGpaMin && me.getProgramGpaMax) {
				return true;
			}
        } else if (gpaFieldName === "gpa") {
			if (me.getGpaMin && me.getGpaMax) {
				return true;
			}
        } else if (gpaFieldName === "all") {
            //check all fields
			if (me.getLocalGpaMin && me.getLocalGpaMax && me.getProgramGpaMin && me.getProgramGpaMax && me.getGpaMin && me.getGpaMax) {
				return true;
			}
        } else {
        	//check for any
        	if (me.getLocalGpaMin && me.getLocalGpaMax || me.getProgramGpaMin && me.getProgramGpaMax || me.getGpaMin && me.getGpaMax) {
        		return true;
        	}
        }
        return false;
	},
	
	gpaMinChanged: function(gpaMinChanged) {
		var me=this;

		switch (gpaMinChanged.getName()) {
			case 'localGpaMin':
				if (me.checkGpaFields("local")) {
					if (me.getLocalGpaMin().getValue() === null) {
						me.getLocalGpaMax().setValue(null);
						return;
					}
					if (me.getLocalGpaMax().getValue() == null) {
						me.getLocalGpaMax().setValue(me.getLocalGpaMin().getValue());
						return;
					}
					if (me.getLocalGpaMin().getValue() > me.getLocalGpaMax().getValue()) {
						me.getLocalGpaMax().setValue(me.getLocalGpaMin().getValue());
					}
				}
			break;
			case 'programGpaMin':
				if (me.checkGpaFields("program")) {
					if (me.getProgramGpaMin().getValue() === null) {
						me.getProgramGpaMax().setValue(null);
						return;
					}
					if (me.getProgramGpaMax().getValue() == null) {
						me.getProgramGpaMax().setValue(me.getProgramGpaMin().getValue());
						return;
					}
					if (me.getProgramGpaMin().getValue() > me.getProgramGpaMax().getValue()) {
						me.getProgramGpaMax().setValue(me.getProgramGpaMin().getValue());
					}
				}
			break;
			default:
				if (me.checkGpaFields("gpa")) {
					if (me.getGpaMin().getValue() === null) {
						me.getGpaMax().setValue(null);
						return;
					}
					if (me.getGpaMax().getValue() == null) {
						me.getGpaMax().setValue(me.getGpaMin().getValue());
						return;
					}
					if (me.getGpaMin().getValue() > me.getGpaMax().getValue()) {
						me.getGpaMax().setValue(me.getGpaMin().getValue());
					}
				}
			break;
		}
	},
	
	gpaMaxChanged: function(gpaMaxChanged){
		var me=this;

        switch (gpaMaxChanged.getName()) {
			case 'localGpaMax':
				if (me.checkGpaFields("local")) {
					if ( !me.getLocalGpaMin().getValue() ) {
						me.getLocalGpaMin().setValue(0);
						return;
					}
					if ( me.getLocalGpaMin().getValue() > me.getLocalGpaMax().getValue() ) {
						me.getLocalGpaMin().setValue(me.getLocalGpaMax().getValue());
					}
				}
			break;
			case 'programGpaMax':
				if (me.checkGpaFields("program")) {
					if ( !me.getProgramGpaMin().getValue() ) {
						me.getProgramGpaMin().setValue(0);
						return;
					}
					if ( me.getProgramGpaMin().getValue() > me.getProgramGpaMax().getValue() ) {
						me.getProgramGpaMin().setValue(me.getProgramGpaMax().getValue());
					}
				}
			break;
			default:
				if (me.checkGpaFields("gpa")) {
					if ( !me.getGpaMin().getValue() ) {
						me.getGpaMin().setValue(0);
						return;
					}
					if ( me.getGpaMin().getValue() > me.getGpaMax().getValue() ) {
						me.getGpaMin().setValue(me.getGpaMax().getValue());
					}
				}
			break;
		}
	},

	checkAllGpaMinLessThanMax: function(messageReturn) {
		var me = this;
		var valuesInvalidReturn = false;

		if (me.checkGpaFields("local")) {
			if(me.getLocalGpaMin().getValue() > me.getLocalGpaMax().getValue()){
				valuesInvalidReturn = true;
				messageReturn += "Local GPA Min is greater than Local GPA Maximum. ";
			}

			if ( me.getLocalGpaMin().getValue() == null && me.getLocalGpaMax().getValue() != null ) {
				me.getLocalGpaMin().setValue(0);
			}

			if ( me.getLocalGpaMin().getValue() == null && me.getLocalGpaMax().getValue() != null ) {
				me.getLocalGpaMin().setValue(0);
			}
		} else if (me.checkGpaFields("program")) {
			if(me.getProgramGpaMin().getValue() > me.getProgramGpaMax().getValue()){
				valuesInvalidReturn = true;
				messageReturn += "Program GPA Min is greater than Program GPA Maximum. ";
			}

			if ( me.getProgramGpaMin().getValue() == null && me.getProgramGpaMax().getValue() != null ) {
				me.getProgramGpaMin().setValue(0);
			}

			if ( me.getProgramGpaMin().getValue() == null && me.getProgramGpaMax().getValue() != null ) {
				me.getProgramGpaMin().setValue(0);
			}
		} else {
			if(me.getGpaMin().getValue() > me.getGpaMax().getValue()){
				valuesInvalidReturn = true;
				messageReturn += "GPA Min is greater than GPA Maximum. ";
			}

			if ( me.getGpaMin().getValue() == null && me.getGpaMax().getValue() != null ) {
				me.getGpaMin().setValue(0);
			}

			if ( me.getGpaMin().getValue() == null && me.getGpaMax().getValue() != null ) {
				me.getGpaMin().setValue(0);
			}
		}
		return [valuesInvalidReturn, messageReturn];
	},

    searchFailure: function( r, scope) {
    	var me=scope;
		me.appEventsController.getApplication().fireEvent("onPersonSearchFailure");
    }
});
