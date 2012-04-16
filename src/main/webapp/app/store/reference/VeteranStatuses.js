Ext.define('Ssp.store.reference.VeteranStatuses', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.VeteranStatus',
    storeId: 'veteranStatusesReferenceStore',
	autoLoad: false,
	autoSync: true,

    proxy: {
		type: 'rest',
		url: '/ssp/api/reference/veteranStatus/',
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