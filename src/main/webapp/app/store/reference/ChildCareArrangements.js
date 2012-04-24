Ext.define('Ssp.store.reference.ChildCareArrangements', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.ChildCareArrangement',
    storeId: 'childCareArrangementsReferenceStore',
	autoLoad: false,
	autoSync: true,

    proxy: {
		type: 'rest',
		url: '/ssp/api/1/reference/childCareArrangement/',	
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