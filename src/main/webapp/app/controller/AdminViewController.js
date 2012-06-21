Ext.define('Ssp.controller.AdminViewController', {
	extend: 'Deft.mvc.ViewController',    
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	campusesStore: 'campusesStore',
    	challengeCategoriesStore: 'challengeCategoriesStore',
        challengesStore: 'challengesStore',
    	challengeReferralsStore: 'challengeReferralsStore',
    	childCareArrangementsStore: 'childCareArrangementsStore',
    	citizenshipsStore: 'citizenshipsStore',
    	confidentialityLevelsStore: 'confidentialityLevelsStore',
		earlyAlertOutcomesStore: 'earlyAlertOutcomesStore',
		earlyAlertOutreachesStore: 'earlyAlertOutreachesStore',
		earlyAlertReasonsStore: 'earlyAlertReasonsStore',
		earlyAlertReferralsStore: 'earlyAlertReferralsStore',
		earlyAlertSuggestionsStore: 'earlyAlertSuggestionsStore',
    	educationGoalsStore: 'educationGoalsStore',
    	educationLevelsStore: 'educationLevelsStore',
    	employmentShiftsStore: 'employmentShiftsStore',
    	ethnicitiesStore: 'ethnicitiesStore',
    	formUtils: 'formRendererUtils',
    	fundingSourcesStore: 'fundingSourcesStore',
    	gendersStore: 'gendersStore',
        journalSourcesStore: 'journalSourcesStore',
        journalStepsStore: 'journalStepsStore',
        journalTracksStore: 'journalTracksStore',
    	maritalStatusesStore: 'maritalStatusesStore',
    	programStatusChangeReasonsStore: 'programStatusChangeReasonsStore',
    	referralSourcesStore: 'referralSourcesStore',
    	serviceReasonsStore: 'serviceReasonsStore',
    	specialServiceGroupsStore: 'specialServiceGroupsStore',
        statesStore: 'statesStore',
        studentStatusesStore: 'studentStatusesStore',
        studentTypesStore: 'studentTypesStore',
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
				this.loadAdmin( record.raw.title, record.raw.form, storeName, columns );         
			}
		}
	},

	loadAdmin: function( title ,form, storeName, columns ) {
		var comp = this.formUtils.loadDisplay('adminforms',form, true, {});
		var store = null;
		
		// set a store if defined
		if (storeName != "")
		{
			store = this[storeName+'Store'];
			// If the store was set, then modify
			// the component to use the store
			if (store != null)
			{
				// pass the columns for editing
				if (columns != null)
				{
					comp.reconfigure(store, columns); // ,columns
				}else{
					comp.reconfigure(store);
				}
				
				comp.getStore().load();
			}
		}
		
		if (Ext.isFunction(comp.setTitle))
			comp.setTitle(title + ' Admin');
	}
});