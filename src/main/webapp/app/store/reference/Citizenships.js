Ext.define('Ssp.store.reference.Citizenships', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.CitizenshipTO',
    storeId: 'citizenshipsReferenceStore',
	autoLoad: false,
	autoSync: true,

    proxy: {
		type: 'rest',
		url: '/ssp/api/reference/citizenship/',
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