Ext.define('Ssp.store.reference.Ethnicities', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.EthnicityTO',
    storeId: 'ethnicitiesReferenceStore',
	autoLoad: false,

    proxy: {
		type: 'rest',
		api: {
			read: 'data/reference/ethnicities.json'
			// read: '/ssp/api/reference/ethnicity/'
		},
		reader: {
			type: 'json'
		}
	}	
	
});