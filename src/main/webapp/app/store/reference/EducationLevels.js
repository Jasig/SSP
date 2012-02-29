Ext.define('Ssp.store.reference.EducationLevels', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.EducationLevelTO',
    storeId: 'educationLevelsReferenceStore',
	autoLoad: false,

    proxy: {
		type: 'ajax',
		api: {
			read: 'data/reference/educationlevels.json'
		},
		reader: {
			type: 'json',
			root: 'educationLevels',
			successProperty: 'success'
		}
	}	
	
});