Ext.define('Ssp.store.reference.Genders', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.AbstractReferenceTO',
    storeId: 'gendersReferenceStore',
	autoLoad: false,

    proxy: {
		type: 'ajax',
		api: {
			read: 'data/reference/genders.json'
		},
		reader: {
			type: 'json',
			root: 'items',
			successProperty: 'success'
		}
	}	
	
});