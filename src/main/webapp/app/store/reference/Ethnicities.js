Ext.define('Ssp.store.reference.Ethnicities', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.Ethnicity',
    storeId: 'ethnicitiesReferenceStore',
	autoLoad: false,
	autoSync: true,

    proxy: {
		type: 'rest',
		url: '/ssp/api/reference/ethnicity/',
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