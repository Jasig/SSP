Ext.define('Ssp.store.reference.VeteranStatuses', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.VeteranStatusTO',
    storeId: 'veteranStatusesReferenceStore',
	autoLoad: false,

    proxy: {
		type: 'ajax',
		api: {
			read: 'data/reference/veteranstatuses.json'
		},
		reader: {
			type: 'json',
			root: 'veteranStatuses',
			successProperty: 'success'
		}
	}	
	
});