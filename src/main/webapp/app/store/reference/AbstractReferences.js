Ext.define('Ssp.store.reference.AbstractReferences', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.AbstractReference',
    storeId: 'abstractReferencesStore',
    pageSize: 15,
	params : {
		page : 0,
		start : 0,
		limit : 15
	}
	
});