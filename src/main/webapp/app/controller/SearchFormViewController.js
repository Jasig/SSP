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
Ext.define('Ssp.controller.SearchFormViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
        appEventsController: 'appEventsController',
        authenticatedPerson: 'authenticatedPerson',
        caseloadFilterCriteria: 'caseloadFilterCriteria',
        caseloadService: 'caseloadService',
        columnRendererUtils: 'columnRendererUtils',
        exportService: 'exportService',
        formUtils: 'formRendererUtils',
        person: 'currentPerson',
        personLite: 'personLite',
        personService: 'personService',
        personProgramStatusService: 'personProgramStatusService',
        programStatusesStore: 'programStatusesStore',
        programStatusService: 'programStatusService',
        searchCriteria: 'searchCriteria',
        searchService: 'searchService',
        coachesStore: 'coachesStore',
        searchStoreOld: 'studentsSearchStore',
        configStore: 'configurationOptionsUnpagedStore',
        searchStore: 'directoryPersonSearchStore'

    },
    control: {
    	view: {
			afterlayout: {
				fn: 'onAfterLayout',
				single: true
			}
    	},
    'searchStudentButton': {
		click: 'onSearchClick'
		},
//    	searchActionCombo: {
//    		selector: '#searchActionCombo',
//    		listeners: {
//    			select: 'onSearchActionComboSelect'
//    		} 
//    	},		
   	'resetStudentSearchButton': {
    		click: 'onResetClick'
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
     'mapStatus':{
			specialkey: "specialKeyPressed"
	 },		
     'planStatus':{
			specialkey: "specialKeyPressed"
	 },	
     'planStatus':{
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
	 'schoolId': {
	        specialkey: "specialKeyPressed"
	    }
    },
    
	init: function() {
		var me=this;    	

		me.coachesStore.clearFilter(true);

		return me.callParent(arguments);
    },

	onAfterLayout: function(comp, eobj){
		var me=this;
		
		me.appEventsController.assignEvent({eventName: 'onStudentSearchRequested', callBackFunc: me.onSearchClick, scope: me});
		me.appEventsController.assignEvent({eventName: 'onSearchActionComboSelect', callBackFunc: me.onSearchActionComboSelect, scope: me});

	   	// load program statuses
		me.getProgramStatuses();	
	},
    destroy: function() {
    	var me=this;
    	me.appEventsController.removeEvent({eventName: 'onStudentSearchRequested', callBackFunc: me.onSearchClick, scope: me});
		me.appEventsController.removeEvent({eventName: 'onSearchActionComboSelect', callBackFunc: me.onSearchActionComboSelect, scope: me});

        if ( me.emailStudentPopup ) {
            me.emailStudentPopup.destroy();
        }

	   	return me.callParent( arguments );
    },
    searchSuccess: function( r, scope){
    	var me=scope;
    	me.searchStore.pageSize = me.searchStore.data.length;
    	me.getView().setLoading( false );
		me.getView().collapse();
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
				me.getView().query('combobox[name=currentlyRegistered]')[0].value,
				me.getView().query('combobox[name=earlyAlertResponseLate]')[0].value,
				me.getView().query('combobox[name=financialAidSapStatusCode]')[0].value,
				me.getView().query('combobox[name=mapStatus]')[0].value,
				me.getView().query('combobox[name=planStatus]')[0].value,
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
		if(value !== null && value !== undefined && !(value === ""))
			return me.formUtils.toJSONStringifiableDate(value).formattedStr;
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
    	var me=scope;
    	var activeProgramStatusId = "";
    	var programStatus;
    	if ( me.programStatusesStore.getCount() > 0)
    	{
    	}
    },	
    getProgramStatusesFailure: function( r, scope){
    	var me=scope;
    },
    onResetClick: function(button){
		var me=this;
		me.clear();	
		me.searchStore.removeAll();
		me.appEventsController.getApplication().fireEvent("onPersonSearchSuccess");
		
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
	exportSearch: function(criteria) {
		var me = this;
		window.open(me.exportService.buildExportSearchUrl(criteria),'_blank');
	},
	onExportConfirm: function(btnId, criteria){
		var me=this;
		if (btnId=="ok") {
			me.exportSearch(criteria);
		}
	},
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
	newOnBulkEmailConfirm: function (criteria) {
		var me = this;
		return function(btnId) {
			me.onBulkEmailConfirm(btnId, criteria);
		}
	},
	// copy/paste from SearchViewController except for 'criteria' and slightly different search/export invocation.
	// also don't need the 'searchRsltType' handling in the SearchViewController flavor
	promptWithExportCount: function(count, criteria) {
		var me = this;
		var message;
		count = parseInt(count);
		// loadMaskOff() copy/pasted in both prompy*() functions to try to delay that dismissal as long
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
	// copy/paste from SearchViewController except for 'criteria'
	promptWithEmailCount: function(count, criteria) {
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
	newBulkActionCountResultFailureCallback: function(actionType, criteria) {
		var me = this;
		return function(cnt) {
			return me.onBulkActionCountFailure(cnt, actionType, criteria);
		}
	},
	// copy/paste from SearchViewController except for 'criteria'
	newBulkActionCountResultSuccessCallback: function(actionType, criteria) {
		var me = this;
		return function(cnt) {
			return me.onBulkActionCountSuccess(cnt, actionType, criteria);
		}
	},
	// copy/paste from SearchViewController except for 'criteria'
	onBulkActionCountFailure: function(cnt, actionType, criteria) {
		var me = this;
		me.appEventsController.loadMaskOff();
		Ext.Msg.alert('SSP Error', 'Failed to look up the number of records which would be affected by the ' +
			'requested action. Retry or contact your system administrator');
	},
	// copy/paste from SearchViewController except for 'criteria'
	onBulkActionCountSuccess: function(cnt, actionType, criteria) {
		var me = this;
		if ( actionType === 'EXPORT' ) {
			me.promptWithExportCount(cnt, criteria);
		} else if ( actionType === 'EMAIL' ) {
			me.promptWithEmailCount(cnt, criteria);
		}
	},
	onSearchActionComboSelect: function(records){
		var me=this;
		if(!me.getView().getForm().isDirty())
		{
			Ext.Msg.alert('SSP Error', 'Please enter some filter values.');
			return;
		}
		if(!me.getView().getForm().isValid()){
			Ext.Msg.alert('SSP Error', 'One or more search filters are invalid. Problems have been highlighted.');
			return;
		}
		var message = "";
		var valuesInvalid = false;
		if(me.getGpaMin().getValue() > me.getGpaMax().getValue()){
			valuesInvalid = true;
			message += "GPA Min is greater than GPA Maximum. ";
		}

		if(me.getGpaMin().getValue() == null && me.getGpaMax().getValue() != null){
			me.getGpaMin().setValue(0);
		}

		if(me.getHoursEarnedMin().getValue() > me.getHoursEarnedMax().getValue()){
			valuesInvalid = true;
			message += "Hours Earned Min is greater than Hours Earned Maximum. ";
		}

		if(me.getGpaMin().getValue() == null && me.getGpaMax().getValue() != null){
			me.getGpaMin().setValue(0);
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
				programStatus: me.getView().query('combobox[name=programStatus]')[0].value,
				specialServiceGroup: me.getView().query('combobox[name=specialServiceGroup]')[0].value,
				coachId: me.getView().query('combobox[name=coachId]')[0].value,
				declaredMajor: me.getView().query('combobox[name=declaredMajor]')[0].value,
				hoursEarnedMin: me.getView().query('numberfield[name=hoursEarnedMin]')[0].value,
				hoursEarnedMax: me.getView().query('numberfield[name=hoursEarnedMax]')[0].value,
				gpaEarnedMin: me.getView().query('numberfield[name=gpaMin]')[0].value,
				gpaEarnedMax: me.getView().query('numberfield[name=gpaMax]')[0].value,
				currentlyRegistered: me.getView().query('combobox[name=currentlyRegistered]')[0].value == null ? null : new Boolean(me.getView().query('combobox[name=currentlyRegistered]')[0].value).toString(),
				earlyAlertResponseLate: me.getView().query('combobox[name=earlyAlertResponseLate]')[0].value,
				sapStatusCode: me.getView().query('combobox[name=financialAidSapStatusCode]')[0].value,
				mapStatus: me.getView().query('combobox[name=mapStatus]')[0].value,
				planStatus: me.getView().query('combobox[name=planStatus]')[0].value,
				myCaseload: me.getView().query('checkbox[name=myCaseload]')[0].value,
				myPlans: me.getView().query('checkbox[name=myPlans]')[0].value,
				myWatchList: me.getView().query('checkbox[name=myWatchList]')[0].value,
				birthDate: me.dateFieldValueFromName('birthDate'),
				actualStartTerm: me.getView().query('combobox[name=actualStartTerm]')[0].value,
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
	onSearchClick: function(button){
		var me = this;
		if(!me.getView().getForm().isDirty())
		{
	     	Ext.Msg.alert('SSP Error', 'Please enter some filter values.'); 
	     	return;
		}
		if(!me.getView().getForm().isValid()){
			Ext.Msg.alert('SSP Error', 'One or more search filters are invalid. Problems have been highlighted.');
			return;
		}
		var message = "";
		var valuesInvalid = false;
		if(me.getGpaMin().getValue() > me.getGpaMax().getValue()){
			valuesInvalid = true;
			message += "GPA Min is greater than GPA Maximum. ";
		}
		
		if(me.getGpaMin().getValue() == null && me.getGpaMax().getValue() != null){
			me.getGpaMin().setValue(0);
		}
		
		if(me.getHoursEarnedMin().getValue() > me.getHoursEarnedMax().getValue()){
				valuesInvalid = true;
				message += "Hours Earned Min is greater than Hours Earned Maximum. ";
		}

		if(me.getGpaMin().getValue() == null && me.getGpaMax().getValue() != null){
				me.getGpaMin().setValue(0);
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
			me.onSearchClick();
		}
	},
	
	hoursEarnedMinChanged: function(){
		var me=this;
		
		if(me.getHoursEarnedMax().getValue() == null){
				me.getHoursEarnedMax().setValue(me.getHoursEarnedMin().getValue());
				return;
		}
		if(me.getHoursEarnedMin().getValue() > me.getHoursEarnedMax().getValue()){
			me.getHoursEarnedMax().setValue(me.getHoursEarnedMin().getValue());
		}
	},
	
	hoursEarnedMaxChanged: function(){
		var me=this;
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
	},
	
	gpaMinChanged: function(){
		var me=this;
		if(me.getGpaMin().getValue() === null){
			me.getGpaMax().setValue(null);
			return;
		}	
		if(me.getGpaMax().getValue() == null){
			me.getGpaMax().setValue(me.getGpaMin().getValue());
			return;
		}
		if(me.getGpaMin().getValue() > me.getGpaMax().getValue()){
			me.getGpaMax().setValue(me.getGpaMin().getValue());
		}
	},
	
	gpaMaxChanged: function(){
		var me=this;

		if(!me.getGpaMin().getValue()){
			me.getGpaMin().setValue(0);
			return;
		}
			
		if(me.getGpaMin().getValue() > me.getGpaMax().getValue()){
			me.getGpaMin().setValue(me.getGpaMax().getValue());
		}
	},

    searchFailure: function( r, scope){
    	var me=scope;
		me.appEventsController.getApplication().fireEvent("onPersonSearchFailure");
    }
});