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
Ext.define('Ssp.controller.AdminViewController', {
	extend: 'Deft.mvc.ViewController',    
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	campusesStore: 'campusesStore',
    	campusServicesStore: 'campusServicesStore',
    	challengeCategoriesStore: 'challengeCategoriesStore',
        challengesStore: 'challengesStore',
    	challengeReferralsStore: 'challengeReferralsStore',
    	childCareArrangementsAllStore: 'childCareArrangementsAllStore',
    	citizenshipsAllStore: 'citizenshipsAllStore',    	
    	colorsStore: 'colorsStore',
        colorsUnpagedStore: 'colorsUnpagedStore',
        colorsAllStore: 'colorsAllStore',
        colorsAllUnpagedStore: 'colorsAllUnpagedStore',
    	confidentialityLevelsAllStore: 'confidentialityLevelsAllStore',
    	confidentialityLevelOptionsStore: 'confidentialityLevelOptionsStore',
    	disabilityAccommodationsAllStore: 'disabilityAccommodationsAllStore',
    	disabilityAgenciesAllStore: 'disabilityAgenciesAllStore',
    	disabilityStatusesAllStore: 'disabilityStatusesAllStore',
    	disabilityTypesAllStore: 'disabilityTypesAllStore',
		earlyAlertOutcomesAllStore: 'earlyAlertOutcomesAllStore',
		earlyAlertOutreachesAllStore: 'earlyAlertOutreachesAllStore',
		earlyAlertReasonsAllStore: 'earlyAlertReasonsAllStore',
		earlyAlertReferralsAllStore: 'earlyAlertReferralsAllStore',
		earlyAlertSuggestionsAllStore: 'earlyAlertSuggestionsAllStore',
    	educationGoalsAllStore: 'educationGoalsAllStore',
    	educationLevelsAllStore: 'educationLevelsAllStore',
    	electiveStore: 'electivesStore',
    	electivesAllStore: 'electivesAllStore',
    	employmentShiftsStore: 'employmentShiftsStore',
    	ethnicitiesAllStore: 'ethnicitiesAllStore',
		racesStore: 'racesStore',
		sapStatusesStore: 'sapStatusesStore',
		financialAidFilesStore: 'financialAidFilesStore',
    	formUtils: 'formRendererUtils',
    	fundingSourcesAllStore: 'fundingSourcesAllStore',
    	gendersStore: 'gendersStore',
        journalSourcesAllStore: 'journalSourcesAllStore',
        journalStepsStore: 'journalStepsStore',
        journalTracksAllStore: 'journalTracksAllStore',
        lassisStore: 'lassisStore',
    	maritalStatusesAllStore: 'maritalStatusesAllStore',
    	militaryAffiliationsAllStore: 'militaryAffiliationsAllStore',
    	courseworkHoursAllStore: 'courseworkHoursAllStore',
    	enrollmentStatusesStore: 'enrollmentStatusesStore',
    	completedItemStore: 'completedItemStore',
    	registrationLoadsAllStore: 'registrationLoadsAllStore',
    	personalityTypesStore: 'personalityTypesStore',
    	programStatusChangeReasonsAllStore: 'programStatusChangeReasonsAllStore',
    	referralSourcesAllStore: 'referralSourcesAllStore',
    	serviceReasonsAllStore: 'serviceReasonsAllStore',
    	specialServiceGroupsAllStore: 'specialServiceGroupsAllStore',
        statesStore: 'statesStore',
        studentStatusesAllStore: 'studentStatusesAllStore',
        studentTypesStore: 'studentTypesAllUnpagedStore',
		tagsStore: 'tagsStore',
		textStore: 'textStore',
    	veteranStatusesAllStore: 'veteranStatusesAllStore'
    },

    control: {
		view: {
			itemclick: 'onItemClick'
		}
		
	},
	
	init: function() {
		var me = this;
		me.confidentialityLevelOptionsStore.load();
		return this.callParent(arguments);
    }, 
    
	/*
	 * Handle selecting an item in the tree grid
	 */
	onItemClick: function(view,record,item,index,eventObj) {
		var storeName = "";
		var columns = null;

		if (record.raw != undefined )
		{
			if ( record.raw.form != "")
			{
				if (record.raw.store != "")
				{
					storeName = record.raw.store;
				}
				if (record.raw.columns != null)
				{
					columns = record.raw.columns;
				}
				var options = {
					interfaceOptions: record.raw.interfaceOptions,
					viewConfig: record.raw.viewConfig,
					sort: record.raw.sort
				}
				this.loadAdmin( record.raw.title, record.raw.form, storeName, columns, options);
			}
		}
	},

	// Just to avoid creating needless closures
	noOp: function() {},

	loadAdmin: function( title ,form, storeName, columns, options ) {
		var me=this;
		var comp = this.formUtils.loadDisplay('adminforms',form, true, options);
		var store = null;

		if (Ext.isFunction(comp.setTitle))
			comp.setTitle(title + ' Admin');
		
		// set a store if defined
		if (storeName != "")
		{
			store = me[storeName+'Store'];
			// If the store was set, then modify
			// the component to use the store
			if (store != null)
			{
				// pass the columns for editing
				if (columns != null)
				{
					// comp.reconfigure(store, columns); // ,columns
					me.formUtils.reconfigureGridPanel(comp, store, columns);
				}else{
					// comp.reconfigure(store);
					me.formUtils.reconfigureGridPanel(comp, store);
				}
			}

			if ( options.interfaceOptions.storeDependencies ) {

				var responseDispatcherConfig = {
					remainingOpNames: [],
					afterLastOp: {
						callback: Ext.pass(me.afterStoreDependenciesLoaded, [title ,form, storeName, columns, options, comp, store], me),
						callbackScope: me
					}
				}
				Ext.each(options.interfaceOptions.storeDependencies, function(storeDependency) {
					responseDispatcherConfig.remainingOpNames.push(storeDependency.name);
				});
				var responseDispatcher = Ext.create('Ssp.util.ResponseDispatcher', responseDispatcherConfig);

				Ext.each(options.interfaceOptions.storeDependencies, function(storeDependency) {
					if ( storeDependency.clearFilter ) {
						storeDependency.store.clearFilter();
					}
					storeDependency.store.load(responseDispatcher.setSuccessCallback(storeDependency.name, me.noOp, me));
				});

			} else {
				me.afterStoreDependenciesLoaded(title ,form, storeName, columns, options, comp, store);
			}
		}
	},

	afterStoreDependenciesLoaded: function(title ,form, storeName, columns, options, comp, store) {
		var me = this;
		if ( store ) { // store (not comp.getStore()) existence check to preserve legacy behavior, maybe to let reconfigureGridPanel() change the store?
			// unlike with options.interfaceOptions.storeDependencies handling,
			// no clearFilter() here to preserve legacy behavior
			comp.getStore().load(function() { // load() is on comp.getStore() not store, again to preserve legacy
				if(options.sort != null && options.sort != undefined) {
					var sort = options.sort
					comp.getStore().sort(sort.field, (sort.direction != null && sort.direction != undefined) ? sort.direction : "ASC");
				}
			});
		}
	}
});