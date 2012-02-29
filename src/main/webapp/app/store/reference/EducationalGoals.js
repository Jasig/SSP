Ext.define('Ssp.store.reference.EducationalGoals', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.EducationalGoalTO',
    storeId: 'educationalGoalsReferenceStore',
	autoLoad: false,

    proxy: {
		type: 'ajax',
		api: {
			read: 'data/reference/educationalgoals.json'
		},
		reader: {
			type: 'json',
			root: 'educationalGoals',
			successProperty: 'success'
		}
	}	
	
});