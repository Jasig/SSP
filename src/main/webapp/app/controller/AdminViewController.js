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
        statesStore: 'statesStore',
        studentStatusesStore: 'studentStatusesStore',
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
		if (record.raw != undefined )
		{
			if ( record.raw.form != "")
			{
				if (record.raw.store != "")
				{
					storeName = record.raw.store;
				}
				this.loadAdmin( record.raw.title, record.raw.form, storeName );         
			}
		}
	},

	loadAdmin: function( title ,form, storeName ) {
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
				comp.reconfigure(store); // ,columns
				comp.getStore().load();
			}
		}
		
		comp.setTitle(title + ' Admin');
	}
});