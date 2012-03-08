Ext.define('Ssp.store.reference.ChildCareArrangements', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.ChildCareArrangementTO',
    storeId: 'childCareArrangementsReferenceStore',
	autoLoad: false,

    proxy: {
		type: 'ajax',
		url: '/ssp/api/reference/childCareArrangements/',
		/*
		api: {
			read: 'data/reference/childcarearrangements.json'
		},
		*/	
		actionMethods: {
			create: "POST", 
			read: "GET", 
			update: "PUT", 
			destroy: "DELETE"
		},
		reader: {
			type: 'json'
			// ,root: 'childCareArrangements'
			// ,successProperty: 'success'
		},
        writer: {
            type: 'json',
            successProperty: 'success'
        }
	}	
	
});