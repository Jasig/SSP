Ext.define('Ssp.store.reference.MaritalStatuses', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.MaritalStatusTO',
    storeId: 'maritalStatusesReferenceStore',
	autoLoad: false,

    proxy: {
		type: 'ajax',
		api: {
			read: 'data/reference/maritalstatuses.json'
		},
		reader: {
			type: 'json',
			root: 'maritalStatuses',
			successProperty: 'success'
		}
	}	
	
});