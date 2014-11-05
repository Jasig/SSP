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
Ext.define('Ssp.controller.tool.map.SemesterPanelContainerViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject:{
		appEventsController: 'appEventsController',
    	termsStore:'termsStore',
    	mapPlanService:'mapPlanService',
		formUtils: 'formRendererUtils',
		person: 'currentPerson',
		authenticatedPerson: 'authenticatedPerson',
        personLite: 'personLite',
    	currentMapPlan: 'currentMapPlan',
    	mapEventUtils: 'mapEventUtils',
		semesterStores : 'currentSemesterStores',
        electiveStore: 'electivesAllUnpagedStore',
        configStore: 'configurationOptionsUnpagedStore',
        colorStore: 'colorsAllUnpagedStore'
    },
    
	control: {
	    	view: {
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
		var id = me.personLite.get('id');
		
		
		me.configStore.on('load', me.onLoadConfig, me,{single:true});
		
		me.configStore.clearFilter();
		me.configStore.load({
            extraParams: {
                limit: "-1"
            } 
        });
		
		me.semesterPanels = new Array();
		me.yearFieldSets = new Array();
		
		me.editPastTerms = me.configStore.getConfigByName('map_edit_past_terms');
		
		
		me.appEventsController.getApplication().addListener('onBeforePlanLoad', me.onBeforePlanLoad, me);
		me.appEventsController.getApplication().addListener('onAfterPlanLoad', me.updateAllPlanHours, me);
		me.appEventsController.getApplication().addListener('onPlanLoad', me.onPlanLoad, me);
		
		me.appEventsController.assignEvent({eventName: 'onShowMain', callBackFunc: me.onShowMain, scope: me});
		me.appEventsController.assignEvent({eventName: 'onPrintMapPlan', callBackFunc: me.onPrintMapPlan, scope: me});
		me.appEventsController.assignEvent({eventName: 'onShowMapPlanOverView', callBackFunc: me.onShowMapPlanOverView, scope: me});
		me.appEventsController.assignEvent({eventName: 'onEmailMapPlan', callBackFunc: me.onEmailMapPlan, scope: me});
		me.appEventsController.assignEvent({eventName: 'onBumpRequested', callBackFunc: me.onBumpRequested, scope: me});
		me.appEventsController.assignEvent({eventName: 'updateAllPlanHours', callBackFunc: me.updateAllPlanHours, scope: me});


		return me.callParent(arguments);
    },
    
    onLoadConfig: function(){
		this.editPastTerms = this.configStore.getConfigByName('map_edit_past_terms');
	},
	
    resetForm: function() {
        var me = this;
        me.getView().getForm().reset();
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
        };
    },

    // Just to avoid creating needless closures
    noOp: function() {},

	onAfterLayout: function(){
		var me = this;
		me.mapEventUtils.loadCurrentMap();
	},

	fireInitialiseMap: function(){
		var me = this;
		var id = me.personLite.get('id');
	    
	    if (id != "") {
			me.getView().setLoading(true);
			var serviceResponses = {
                failures: {},
                successes: {},
                responseCnt: 0,
                expectedResponseCnt: 1
            }
	    	 me.mapPlanService.getCurrent(id, {
	             success: me.newServiceSuccessHandler('map', me.getMapPlanServiceSuccess, serviceResponses),
	             failure: me.newServiceFailureHandler('map', me.getMapPlanServiceFailure, serviceResponses),
	             scope: me
	         });
	    }
	},
	
	clearSemesterStores: function(){
		var me = this;
		
		for(var index in me.semesterStores){
			delete me.semesterStores[index];
		}
	},
	
	getTerms: function(mapPlan){
		var me = this;
		var terms;
		
		var termsStore = me.termsStore.getCurrentAndFutureTermsStore(true, 5);			
		if(mapPlan){
			var mapTerms = me.termsStore.getTermsFromTermCodes(me.mapPlanService.getTermCodes(mapPlan));
			Ext.Array.forEach(mapTerms, function(mapTerm) {
				if(termsStore.findExact("code", mapTerm.get("code")) < 0)
						termsStore.add(mapTerm);	
			});
		}
		termsStore.sort('startDate','ASC');
		return termsStore.getRange(0);
	},
	
	fillSemesterStores: function(terms){
		var me = this;
		me.clearSemesterStores();
		Ext.Array.forEach(terms, function(term) {
			me.semesterStores[term.get('code')] = new Ssp.store.SemesterCourses();
			me.semesterStores[term.get('code')].termName = term.get('name');
			me.semesterStores[term.get('code')].termCode = term.get('code');
			me.semesterStores[term.get('code')].editPastTerms = me.editPastTerms;
		});
	},
	
	onTermsStoreLoad:function(){
		var me = this;
		me.termsStore.removeListener( "onTermsStoreLoad", me.onTermsStoreLoad, me );
		if(me.termsStore.getTotalCount() == 0){
			me.getView().setLoading(false);
			me.currentMapPlan.clearMapPlan();
			me.currentMapPlan.set('personId',  me.personLite.get('id'));
			me.currentMapPlan.set('ownerId',  me.authenticatedPerson.get('id'));
			me.currentMapPlan.set('name','No Terms Found.');
		}else
			me.fireInitialiseMap();
	},
	
	onCreateNewMapPlan:function(){
		var me = this;
		me.currentMapPlan.clearMapPlan();
		me.currentMapPlan.set('personId',  me.personLite.get('id'));
		me.currentMapPlan.set('ownerId',  me.authenticatedPerson.get('id'));
		me.currentMapPlan.set('name','New Plan');
		me.onCreateMapPlan();
	},
	clearPlanState: function(){
		var me = this;
		me.currentMapPlan.clearMapPlan();		
		view.removeAll();
	},
	onCreateMapPlan:function(){
		var me = this;
		var terms = me.getTerms(me.currentMapPlan);
		
		if(terms.length == 0){
			return;
		}
		me.fillSemesterStores(terms);
		var view  = me.getView().getComponent("semestersets");
		Ext.suspendLayouts();
		view.removeAll(true);
		if(view == null){
			return;
		}		
		var i=0;
		var termsets = [];
		Ext.Array.forEach(terms, function(term) {
			if(termsets.hasOwnProperty(term.get("reportYear"))){
				termsets[term.get("reportYear")][termsets[term.get("reportYear")].length] = term;
			}else{
				termsets[term.get("reportYear")]=[];
				termsets[term.get("reportYear")][0] = term;
			};
		});
		Ext.Array.forEach(me.yearFieldSets, function(fieldSet) {
			fieldSet.removeAll(false);
		});
		var yearViews = new Array();
		var yearView;
		Ext.Array.forEach(termsets, function(termSet) {
			if (termSet) {

					yearView = new Ssp.view.tools.map.PersistentFieldSet({
						xtype : 'fieldset',
						border: 0,
						title : '',
						padding : '2 2 2 2',
						margin : '0 0 0 0',
						layout : 'hbox',
						autoScroll : true,
						minHeight: 204,
						itemId : 'year' + termSet[0].get("reportYear"),
						flex : 1
					});
					me.yearFieldSets[termSet[0].get("reportYear")] = yearView;
				}
				else
				{
					me.yearFieldSets[termSet[0].get("reportYear")].removeAll(false);
					yearView = me.yearFieldSets[termSet[0].get("reportYear")];
				}
			if(Ext.isIE){  //without this check/alteration, scrollbars appear in IE10 per SSP-1308
				yearView.minHeight = 214;
			}
			var semesterPanels = new Array();
			
			Ext.Array.forEach(termSet, function(term) {
				var termCode = term.get('code');
				var panelName = term.get("name");
				var isPast = me.termsStore.isPastTerm(termCode);
				semesterPanels.push(me.createSemesterPanel(panelName, termCode, me.semesterStores[termCode],me.editPastTerms));
			});

			yearView.add(semesterPanels);		
			yearViews.push(yearView);
			
		});
		me.currentMapPlan.repopulatePlanStores(me.semesterStores);
		view.add(yearViews);	
		me.currentMapPlan.dirty = false;
		Ext.resumeLayouts(true);	
		me.appEventsController.getApplication().fireEvent("onAfterPlanLoad");
    },
	
	createSemesterPanel: function(semesterName, termCode, semesterStore,editPastTerms){
		var me = this;
		
		// TODO the boolean logic needs to be changed once we have a proper getConfigAsBoolean() API
		// https://issues.jasig.org/browse/SSP-2591
		var editable = me.editPastTerms === 'true' || me.editPastTerms === true || !me.termsStore.isPastTerm(termCode);
		var semesterPanel = new Ssp.view.tools.map.SemesterPanel({
			store:semesterStore,
			editable: editable
		});	
		me.semesterPanels[termCode] = semesterPanel;
		return semesterPanel;
	},

	onShowMain: function(){
    	var me=this;
    	var mainView = Ext.ComponentQuery.query('mainview')[0];
    	var arrViewItems;
    	
    	if (mainView.items.length > 0)
		{
			mainView.removeAll(false);
		}
		
		arrViewItems = [{xtype:'searchtab',flex: 2},
					    {xtype: 'studentrecord', flex: 4}];
		
		mainView.add( arrViewItems );	
	},	
	
	onBeforePlanLoad: function() {
		var me = this;
		me.currentMapPlan.clearMapPlan();
		me.currentMapPlan.set('personId',  me.personLite.get('id'));
		me.currentMapPlan.set('ownerId',  me.authenticatedPerson.get('id'));
		me.currentMapPlan.set('name','New Plan');		
		me.currentMapPlan.dirty = false;
	},
	onPlanLoad: function() {
		var me = this;
		me.onCreateMapPlan();
	},

  updateAllPlanHours: function(){
		var me = this;
		var parent =  me.getView();
		var panels = parent.query("semesterpanel");
		var planHours = 0;
		var devHours = 0;
		Ext.Array.forEach(panels, function(panel) {
			var store = panel.getStore();
			var semesterBottomDock = panel.getDockedComponent("semesterBottomDock");
			
			var hours = me.updateTermHours(store, semesterBottomDock);
			planHours += hours.planHours;
			devHours += hours.devHours;
		});
		if(Ext.getCmp('currentTotalPlanCrHrs')) {
			Ext.getCmp('currentTotalPlanCrHrs').setValue((planHours/100).toFixed(2));
		}
		if(Ext.getCmp('currentPlanTotalDevCrHrs')) {
			Ext.getCmp('currentPlanTotalDevCrHrs').setValue((devHours/100).toFixed(2));
		}		
	},
	
	updateTermHours: function(store, semesterBottomDock){
		var models = store.getRange(0);
		var totalHours = 0;
		var totalDevHours = 0;
		Ext.Array.forEach(models, function(model) {
			var value = model.get('creditHours');
			/**** TODO the following is in the constructor but drag and drop does not call the constructor *****/
			if(!value){
				value = model.get('minCreditHours');
				model.set('creditHours', value);
			}
			value = parseInt((value * 100).toFixed(0));
			totalHours += value
			if(model.get('isDev')){
				totalDevHours += value;
			}
		});
		var termCreditHours = semesterBottomDock.getComponent('termCrHrs');
		termCreditHours.setText("" +  (totalHours / 100).toFixed(2) + "");
		var hours = new Object();
		hours.planHours = totalHours;
		hours.devHours = totalDevHours;
		return hours;
	},
	

	
	onEmailMapPlan: function(metaData){
		var me = this;
		me.getView().setLoading(true);
		var serviceResponses = {
                failures: {},
                successes: {},
                responseCnt: 0,
                expectedResponseCnt: 1
            }
		me.mapPlanService.email(me.semesterStores, metaData, {
            success: me.newServiceSuccessHandler('emailMap', me.emailMapPlanServiceSuccess, serviceResponses),
            failure: me.newServiceFailureHandler('emailMap', me.emailMapPlanServiceFailure, serviceResponses),
            scope: me,
            isPrivate: true
        });
	},
	
	 emailMapPlanServiceSuccess: function(serviceResponses) {
	        var me = this;
	        var mapResponse = serviceResponses.successes.emailMap;
	       	me.onEmailComplete(mapResponse.responseText);
			me.getView().setLoading(false);
	 },

	emailMapPlanServiceFailure: function() {
		var me = this;
		me.getView().setLoading(false);
	},
	
	onEmailComplete: function(responseText){
		Ext.Msg.alert('SSP Email Service', responseText);
	},
	
	
	onPrintMapPlan: function(metaData){
		var me = this;
		me.getView().setLoading(true);
		var serviceResponses = {
                failures: {},
                successes: {},
                responseCnt: 0,
                expectedResponseCnt: 1
            }
		me.mapPlanService.print(me.semesterStores, metaData, {
            success: me.newServiceSuccessHandler('printMap', me.printMapPlanServiceSuccess, serviceResponses),
            failure: me.newServiceFailureHandler('printMap', me.printMapPlanServiceFailure, serviceResponses),
            scope: me,
            isPrivate: true
        });
	},
	
	onShowMapPlanOverView: function(){
		var me = this;
		me.getView().setLoading(true);
		var serviceResponses = {
                failures: {},
                successes: {},
                responseCnt: 0,
                expectedResponseCnt: 1,
				show: true
            }
		var metaData = new Ssp.model.tool.map.PlanOutputData();
		
		metaData.set('includeCourseDescription', true);
		metaData.set('includeHeaderFooter', true);
		metaData.set('includeTotalTimeExpected', true);
		metaData.set('includeFinancialAidInformation', true);
		
		var planType = "plan";
		if(me.currentMapPlan.get("isTemplate") == true){
			planType = "template";
			metaData.set('outputFormat', 'matrixFormat');
		}else{
			metaData.set('outputFormat', 'fullFormat');
		}
			
		me.mapPlanService.print(me.semesterStores, 
			metaData,
			{
            success: me.newServiceSuccessHandler('printMap', me.printMapPlanServiceSuccess, serviceResponses),
            failure: me.newServiceFailureHandler('printMap', me.printMapPlanServiceFailure, serviceResponses),
            scope: me,
            isPrivate: false
        	},
			planType);
	},
	
	 printMapPlanServiceSuccess: function(serviceResponses) {
	        var me = this;
	        var mapResponse = serviceResponses.successes.printMap;
	       	me.onPrintComplete(mapResponse.responseText, serviceResponses.show);
			me.getView().setLoading(false);
	 },

	printMapPlanServiceFailure: function() {
		var me = this;
		me.getView().setLoading(false);
	},
	
	onPrintComplete: function(htmlPrint, show){
		var me = this;
        var myWindow = window.open('', '', 'width=500,height=600,scrollbars=yes,resizable=1');
        myWindow.document.write(htmlPrint);
		myWindow.document.title = me.currentMapPlan.get("name");
		if(!show)
        	myWindow.print();
	},
	
/************** Methods Required To Support Bump ********************/
	
	onBumpRequested: function(args){
		var me = this;
		me.clonedMap = me.currentMapPlan.clonePlan();
		me.clonedMap.updatePlanCourses(me.semesterStores);
		var terms = me.clonedMap.getTermCodes()
		if(terms.length <= 0) {
			Ext.Msg.alert('There are no courses to bump. Can not continue.');
			return; 
		}
		var maps = me.createBumpTermMap(args);
		me.maps	= maps;
		if(maps == null)
			return;
		me.bumpCourses(maps, true);
		if(maps.numberToRemoveNoTerm > 0){
			Ext.Msg.alert("Move Plan Aborted", 'Can not move plan, some required terms do not exist. \n Please see administrator to add terms.');
		}else if(maps.numberToRemove > 0){
			var message = 'Completing the move will overwrite ' + maps.numberToRemove + ' courses. Do you wish to?';
			
			var messageBox = Ext.Msg.confirm({
       		     title:'Delete Courses ?',
       		     msg: message,
       		     buttons: Ext.Msg.YESNOCANCEL,
       		     fn: me.completeBump,
       		     scope: me
       		});
			messageBox.msgButtons['yes'].setText("Overwrite Courses");
		    messageBox.msgButtons['no'].setText("Append Courses");
		    messageBox.msgButtons['cancel'].setText("Cancel");
		}else
			me.completeBump('yes');
	},
	
	completeBump: function(btnId){
		if (btnId=="yes" || btnId=="no")
     	{
			var me = this;
			me.maps.overwrite = btnId;
			me.bumpCourses(me.maps);
			me.validatePlan(me.clonedMap);
		}
	},
	
	validatePlan: function(plan){
		var me = this;
		
		me.getView().setLoading(true);
		var serviceResponses = {
                failures: {},
                successes: {},
                responseCnt: 0,
                expectedResponseCnt: 1,
				show: true
            }
		me.mapPlanService.validate(me.clonedMap, me.currentMapPlan.get("isTemplate"),{
            success: me.newServiceSuccessHandler('validatePlan', me.validateMapPlanServiceSuccess, serviceResponses),
            failure: me.newServiceFailureHandler('validatePlan', me.validateMapPlanServiceFailure, serviceResponses),
            scope: me,
            isPrivate: true
        });
	},
	
	validateMapPlanServiceSuccess: function(serviceResponses){
		var me = this;
		me.getView().setLoading(false);
		var mapResponse = serviceResponses.successes.validatePlan;
		
		me.clonedMap.loadFromServer(Ext.decode(mapResponse.responseText));
	    if(me.clonedMap.get("isValid"))
	    	me.onValidateAccept('ok');
		else{
			var validationResponse = me.clonedMap.getPlanValidation();
			if(!validationResponse.valid){
				Ext.Msg.confirm({
       		     	title:'Invalid Courses',
       		     	msg: validationResponse.message + "\n Do you wish to continue with the move?",
       		     	buttons: Ext.Msg.OKCANCEL,
       		     	fn: me.onValidateAccept,
       		     	scope: me
       			});
			}
		}
	},
	
	validateMapPlanServiceFailure: function(serviceResponses){
		var me = this;
		
		me.getView().setLoading(false);
		Ext.Msg.confirm({
  		     title:'Server Error On Validation',
  		     msg: "Server Error unable to validate. Should continue?",
  		     buttons: Ext.Msg.OKCANCEL,
  		     fn: me.onValidateAccept,
  		     scope: me
  		});
	},
	
	onValidateAccept: function(btnId){
		var me = this;
		if(btnId == 'ok'){
			me.mapEventUtils.withMapPlanRenderingDependencies({
					fn: function() {
						me.currentMapPlan.loadPlan(me.clonedMap, true);
						me.onCreateMapPlan();
						me.currentMapPlan.dirty = true;
					},
					scope: me
			});
		}
	},
	
	createBumpTermMap: function(args){
		if(args.endTermCode == args.startTermCode){
			Ext.Msg.alert('No Bump Required', "Bump does not require change, Start Term and End Term the same.");
			return null;
		}
		var me = this;

		var startTermIndex = me.termsStore.findExact('code', args.startTermCode);
		var endTermIndex =  me.termsStore.findExact('code', args.endTermCode);
		if(startTermIndex < 0 || endTermIndex < 0){
				Ext.Msg.alert('Bump not allowed', "Terms to do not fall in allowed range of terms.");
				return null;
		}
				
		var delta = endTermIndex - startTermIndex;
			
		return me.bumpTerms(startTermIndex, delta, args.split);
	},
	
	bumpTerms: function(startTermIndex, delta){
		var me = this;
		var termMap = {};
		var bumpedTermMap = {};
		
		var terms = me.getTerms(me.clonedMap);
		replaceTermCodes = false;
		var numberToRemove = 0;
		Ext.Array.forEach(terms, function(term) {
			var termIndex = me.termsStore.findExact('code', term.get('code'));
			if(termIndex >= 0){
				if (termIndex == startTermIndex){
					replaceTermCodes = true;
				}
				if(replaceTermCodes == false)
					termMap[term.get('code')] = term.get('code');
				else{
					var bumpedTerm = me.termsStore.getAt(termIndex + delta);
					if(bumpedTerm != undefined && bumpedTerm != null){
						if(termMap[bumpedTerm.get('code')] == bumpedTerm.get('code')){
							bumpedTermMap[bumpedTerm.get('code')]  = true;
						}					
						termMap[term.get('code')] = bumpedTerm.get('code');
					}else{
						termMap[term.get('code')] = false;
					}
				}
			}
		});
		var maps = {};
		maps.termMap = termMap;
		maps.bumpedTermMap = bumpedTermMap;
		return maps;
	},
	
	bumpCourses:function(maps, evaluate){
		var me = this;
		if(evaluate){
		 	maps.numberToRemoveNoTerm = 0;
			maps.numberToRemove = 0;
		}
		var planCourses = me.clonedMap.get('planCourses');
		var coursesToDelete = [];
		var k = 0;
		var transcriptedTerms = {};
		var termMap = maps.termMap;
		var bumpedTermMap = maps.bumpedTermMap;
		if(planCourses && planCourses.length > 0){
			var transcriptedTerms = {};
			for(var i = 0; i < planCourses.length; i++){
				var planCourse = planCourses[i];
				if(planCourse.isTranscript  && !planCourse.duplicateOfTranscript){
					transcriptedTerms[termCode] = termCode;
				}
			}
			for(var i = 0; i < planCourses.length; i++){
				var planCourse = planCourses[i];				
				var currentCourseTerm = planCourse.termCode;
				var termCourseCode = termMap[currentCourseTerm];
				if(termCourseCode == false && !planCourse.duplicateOfTranscript){
					maps.numberToRemoveNoTerm++;
					continue;
				}
				if(termCourseCode != null && termCourseCode != ""){
					//Following if statements removes courses from terms that have been bumped and terms that are to be put in transcripted terms
					if(((maps.overwrite == "yes" || evaluate) && bumpedTermMap[currentCourseTerm] == true) || (transcriptedTerms[termCourseCode] != undefined && transcriptedTerms[termCourseCode] != null)){
						if(evaluate){
							if(bumpedTermMap[currentCourseTerm])
								maps.numberToRemove++;
						}else{
							planCourses.splice(i,1);
							i--;
						}
					}else{
						if(!evaluate)
							planCourse.termCode = termCourseCode;
					}
				}
			}
		}
		if(evaluate)
			return;
		var termNotes = me.clonedMap.get("termNotes");
		if(termNotes && termNotes.length > 0){
			for(var i = 0; i < termNotes.length; i++){
				var termNote = termNotes[i];
				var currentNoteTerm = termNote.get('termCode')
				var termCode = termMap[currentNoteTerm];
				if(termCode != null && termCode != ""){
					//Following if statements removes notes from terms that have been bumped and notes that are to be put in transcripted terms
					if(bumpedTermMap[currentNoteTerm] == true || (transcriptedTerms[currentNoteTerm] != undefined && transcriptedTerms[termCode] != null)){
						termNotes.splice(i,1);
					}else{
						termNote.set('termCode', termCode);
					}
				}	
			}
		}
	},
	destroy: function() {
        var me=this;
		if(me.coursePlanDetails != null && !me.coursePlanDetails.isDestroyed){
			me.coursePlanDetails.close();
		}

		Ext.suspendLayouts();
		for (var key in this.semesterPanels) {
			this.semesterPanels[key].destroy();
		}
	
		for (var key in this.yearFieldSets) {
			this.yearFieldSets[key].destroy();
		}
		Ext.resumeLayouts(true);

		me.appEventsController.getApplication().removeListener('onBeforePlanLoad', me.onBeforePlanLoad, me);
		me.appEventsController.getApplication().removeListener('onAfterPlanLoad', me.updateAllPlanHours, me);
		me.appEventsController.getApplication().removeListener('onPlanLoad', me.onPlanLoad, me);
		
		
		me.appEventsController.removeEvent({eventName: 'onShowMain', callBackFunc: me.onShowMain, scope: me});
		me.appEventsController.removeEvent({eventName: 'onPrintMapPlan', callBackFunc: me.onPrintMapPlan, scope: me});
		me.appEventsController.removeEvent({eventName: 'onShowMapPlanOverView', callBackFunc: me.onShowMapPlanOverView, scope: me});
		me.appEventsController.removeEvent({eventName: 'onEmailMapPlan', callBackFunc: me.onEmailMapPlan, scope: me});
		me.appEventsController.removeEvent({eventName: 'onBumpRequested', callBackFunc: me.onBumpRequested, scope: me});
		me.appEventsController.removeEvent({eventName: 'updateAllPlanHours', callBackFunc: me.updateAllPlanHours, scope: me});
		
		return me.callParent( arguments );
    }
});
