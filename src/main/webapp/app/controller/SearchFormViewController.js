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
//    'exportSearchButton': {
//		click: 'onExportSearchClick'
//		},	
    	searchActionCombo: {
    		selector: '#searchActionCombo',
    		listeners: {
    			select: 'onSearchActionComboSelect'
    		} 
    	},		
   	'resetStudentSearchButton': {
    		click: 'onResetClick'
    	},
     'myPlans':{
			specialkey: "specialKeyPressed",
	 },   
     'myWatchList':{
			specialkey: "specialKeyPressed",
	 },	 
     'myCaseload':{
			specialkey: "specialKeyPressed",
	 },	
     'schoolId':{
			specialkey: "specialKeyPressed",
	 },	
     'personTableType':{
			specialkey: "specialKeyPressed",
	 },	 
     'birthDate':{
			specialkey: "specialKeyPressed",
	 },	
     'programStatus':{
			specialkey: "specialKeyPressed",
	 },	
     'currentlyRegistered':{
			specialkey: "specialKeyPressed",
	 },	
     'specialServiceGroup':{
			specialkey: "specialKeyPressed",
	 },	 
     'actualStartTerm':{
			specialkey: "specialKeyPressed",
	 },	 	
     'declaredMajor':{
			specialkey: "specialKeyPressed",
	 },	 
     'coachId':{
			specialkey: "specialKeyPressed",
	 },		
     'earlyAlertResponseLate':{
			specialkey: "specialKeyPressed",
	 },		
     'financialAidSapStatusCode':{
			specialkey: "specialKeyPressed",
	 },	 
     'mapStatus':{
			specialkey: "specialKeyPressed",
	 },		
     'planStatus':{
			specialkey: "specialKeyPressed",
	 },	
     'planStatus':{
			specialkey: "specialKeyPressed",
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
		me.appEventsController.assignEvent({eventName: 'exportSearch', callBackFunc: me.onExportSearch, scope: me});

	   	// load program statuses
		me.getProgramStatuses();	
	},
    destroy: function() {
    	var me=this;
    	me.appEventsController.removeEvent({eventName: 'onStudentSearchRequested', callBackFunc: me.onSearchClick, scope: me});
    	me.appEventsController.removeEvent({eventName: 'exportSearch', callBackFunc: me.onExportSearch, scope: me});

	   	return me.callParent( arguments );
    },
    onExportSearch: function(action, count) {
		var me = this;
		count = parseInt(count);
		var maxExport =  parseInt(me.configStore.getConfigByName('ssp_max_export_row_count').trim());
		var message;
		if(count > maxExport)
		{
			message = 'The number of students in your request ('+count+') exceed the export limit ('+maxExport+').  Click Ok to download the maximum'
			+' numbert of records.  Click Cancel to reduce the results by filtering the Program Status or changing the Student Search critieria.  If you cannot reduce the results contact the SSP Administrator';
		}
		else {
			message = count + " students will be exported.  Continue? ";
		}

			Ext.Msg.confirm({
   		     	title:'Confirm',
   		     	msg: message,
   		     	buttons: Ext.Msg.OKCANCEL,
   		     	fn: me.exportSearch,
   		     	scope: me
   			});

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
	exportSearch: function(btnId){
	    if (btnId=="ok")
	    {
			var me=this;		
			me.exportService.search2( 
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
					success: me.exportSearchSuccess,
					failure: me.searchFailure,
					scope: me
			});	
	    }
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
	onSearchActionComboSelect: function( comp, records, eOpts ){
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
			
			if(records.length > 0)
			{
				if(records[0].get('id') === 'EXPORT')
				{
					me.searchService.search2Count( 
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
				}
				if(records[0].get('id') === 'EMAIL')
				{
					me.bulkEmail();
				}
			}
		},  
		bulkEmail: function(){
			var me=this;
			var store = null;
	   		me.emailStudentPopup = Ext.create('Ssp.view.BulkEmailStudentView',{store: me.searchStore});
	   		me.emailStudentPopup.show();
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