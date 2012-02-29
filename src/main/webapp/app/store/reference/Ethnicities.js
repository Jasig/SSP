Ext.define('Ssp.store.reference.Ethnicities', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.EthnicityTO',
    storeId: 'ethnicitiesReferenceStore',
	autoLoad: false,

    proxy: {
		type: 'ajax',
		api: {
			read: 'data/reference/ethnicities.json'
		},
		reader: {
			type: 'json',
			root: 'ethnicities',
			successProperty: 'success'
		}
	}	
	
});