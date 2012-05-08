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
    	educationGoalsStore: 'educationGoalsStore',
    	educationLevelsStore: 'educationLevelsStore',
    	employmentShiftsStore: 'employmentShiftsStore',
    	ethnicitiesStore: 'ethnicitiesStore',
    	fundingSourcesStore: 'fundingSourcesStore',
    	gendersStore: 'gendersStore',
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
		var adminFormsView = Ext.getCmp('AdminForms');
		var store = null;
		var comp = null;
		
		// clean existing admins
		if (adminFormsView.items.length > 0)
		{
			adminFormsView.removeAll();			
		}
		
		// set a store if defined
		if (storeName != "")
		{
			store = this[storeName+'Store'];
		}
		
		comp = adminFormsView.getComponent( form ); // 'AbstractReferenceAdmin'

		if (comp == undefined)
		{
			comp = Ext.create('Ssp.view.admin.forms.' + form); // AbstractReferenceAdmin			
			adminFormsView.add( comp );
		}

		comp.setTitle(title + ' Admin');
		
		// If the store was set, then modify
		// the component to use the store
		if (store != null)
		{
			comp.reconfigure(store); // ,columns
			comp.getStore().load();
		}
	}
});