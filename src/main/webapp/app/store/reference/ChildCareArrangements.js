Ext.define('Ssp.store.reference.ChildCareArrangements', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.ChildCareArrangementTO',
    storeId: 'childCareArrangementsReferenceStore',
	autoLoad: false,

    proxy: {
		type: 'ajax',
		api: {
			read: 'data/reference/childcarearrangements.json'
		},
		reader: {
			type: 'json',
			root: 'childCareArrangements',
			successProperty: 'success'
		}
	}	
	
});