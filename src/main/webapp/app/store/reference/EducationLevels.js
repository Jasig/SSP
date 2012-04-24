Ext.define('Ssp.store.reference.EducationLevels', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.EducationLevel',
    storeId: 'educationLevelsReferenceStore',
	autoLoad: false,
	autoSync: true,

    proxy: {
		type: 'rest',
		url: '/ssp/api/1/reference/educationLevel/',
		actionMethods: {
			create: "POST", 
			read: "GET", 
			update: "PUT", 
			destroy: "DELETE"
		},
		reader: {
			type: 'json'
		},
        writer: {
            type: 'json',
            successProperty: 'success'
        }
	}	
	
});