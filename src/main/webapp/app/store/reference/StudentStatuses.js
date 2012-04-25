Ext.define('Ssp.store.reference.StudentStatuses', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.StudentStatus',
    storeId: 'studentStatusesReferenceStore',
	autoLoad: false,
	autoSync: true,

    proxy: {
		type: 'rest',
		url: '/ssp/api/1/reference/studentStatus/',
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