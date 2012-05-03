Ext.define('Ssp.controller.AdminViewController', {
    extend: 'Ext.app.Controller',
    
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
    
    views: [ 'admin.AdminTreeMenu' ],
       
	init: function() {

		this.control({
			'AdminTreeMenu': {
				itemclick : this.treeItemClick,
				scope: this
			}
		}); 

		this.callParent(arguments);
    },	

	/*
	 * Handle selecting an item in the tree grid
	 */
	treeItemClick: function(view,record,item,index,eventObj) {
		if (record.raw != undefined )
			if ( record.raw.form != "")
				this.loadAdmin( record.raw.title, record.raw.form, record.raw.store );         
	},

	loadAdmin: function( title ,form, storeName ) {
		var adminFormsView = Ext.getCmp('AdminForms');
		if (adminFormsView.items.length > 0)
		{
			adminFormsView.removeAll();			
		}
		var store = this[storeName+'Store'];
		var comp = adminFormsView.getComponent( form ); // 'AbstractReferenceAdmin'

		if (comp == undefined)
		{
			comp = Ext.create('Ssp.view.admin.forms.' + form); // AbstractReferenceAdmin			
			adminFormsView.add( comp );
		}

		comp.setTitle(title + ' Admin');
		comp.reconfigure(store); // ,columns
		comp.getStore().load();		
	}
});