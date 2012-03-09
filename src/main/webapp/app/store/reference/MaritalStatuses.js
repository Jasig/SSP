Ext.define('Ssp.store.reference.MaritalStatuses', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.MaritalStatusTO',
    storeId: 'maritalStatusesReferenceStore',
	autoLoad: false,
	autoSync: true,

    proxy: {
		type: 'rest',
		url: '/ssp/api/reference/maritalStatus/',
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