Ext.define('Ssp.store.reference.FundingSources', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.FundingSourceTO',
    storeId: 'fundingSourcesReferenceStore',
	autoLoad: false,
	autoSync: true,

    proxy: {
		type: 'rest',
		url: '/ssp/api/reference/fundingSource/',
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