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
    	childCareArrangementsStore: 'childCareArrangementsStore',
    	citizenshipsStore: 'citizenshipsStore',
    	colorsStore: 'colorsStore',
    	confidentialityLevelsStore: 'confidentialityLevelsStore',
    	disabilityAccommodationsStore: 'disabilityAccommodationsStore',
    	disabilityAgenciesStore: 'disabilityAgenciesStore',
    	disabilityStatusesStore: 'disabilityStatusesStore',
    	disabilityTypesStore: 'disabilityTypesStore',
		earlyAlertOutcomesStore: 'earlyAlertOutcomesStore',
		earlyAlertOutreachesStore: 'earlyAlertOutreachesStore',
		earlyAlertReasonsStore: 'earlyAlertReasonsStore',
		earlyAlertReferralsStore: 'earlyAlertReferralsStore',
		earlyAlertSuggestionsStore: 'earlyAlertSuggestionsStore',
    	educationGoalsStore: 'educationGoalsStore',
    	educationLevelsStore: 'educationLevelsStore',
    	electiveStore: 'electiveStore',
    	employmentShiftsStore: 'employmentShiftsStore',
    	ethnicitiesStore: 'ethnicitiesStore',
    	formUtils: 'formRendererUtils',
    	fundingSourcesStore: 'fundingSourcesStore',
    	gendersStore: 'gendersStore',
        journalSourcesStore: 'journalSourcesStore',
        journalStepsStore: 'journalStepsStore',
        journalTracksStore: 'journalTracksStore',
        lassisStore: 'lassisStore',
    	maritalStatusesStore: 'maritalStatusesStore',
    	militaryAffiliationsStore: 'militaryAffiliationsStore',
    	personalityTypesStore: 'personalityTypesStore',
    	programStatusChangeReasonsStore: 'programStatusChangeReasonsStore',
    	referralSourcesStore: 'referralSourcesStore',
    	serviceReasonsStore: 'serviceReasonsStore',
    	specialServiceGroupsStore: 'specialServiceGroupsStore',
        statesStore: 'statesStore',
        studentStatusesStore: 'studentStatusesStore',
        studentTypesStore: 'studentTypesStore',
		tagsStore: 'tagsStore',
    	veteranStatusesStore: 'veteranStatusesStore'
    },

    control: {
		view: {
			itemclick: 'onItemClick'
		}
		
	},
	
	init: function() {
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
					viewConfig: record.raw.viewConfig
				}
				this.loadAdmin( record.raw.title, record.raw.form, storeName, columns, options);
			}
		}
	},

	loadAdmin: function( title ,form, storeName, columns, options ) {
		var me=this;
		var comp = this.formUtils.loadDisplay('adminforms',form, true, options);
		var store = null;
		
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
					me.formUtils.reconfigureGridPanel(comp, store, options, columns);
				}else{
					// comp.reconfigure(store);
					me.formUtils.reconfigureGridPanel(comp, store, options);
				}
				
				comp.getStore().load();
			}
		}
		
		if (Ext.isFunction(comp.setTitle))
			comp.setTitle(title + ' Admin');
	}
});