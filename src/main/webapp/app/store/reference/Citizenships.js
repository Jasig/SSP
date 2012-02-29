Ext.define('Ssp.store.reference.Citizenships', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.CitizenshipTO',
    storeId: 'citizenshipsReferenceStore',
	autoLoad: false,

    proxy: {
		type: 'ajax',
		api: {
			read: 'data/reference/citizenships.json'
		},
		reader: {
			type: 'json',
			root: 'citizenships',
			successProperty: 'success'
		}
	}	
	
});