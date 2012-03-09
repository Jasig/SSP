Ext.define('Ssp.store.reference.StudentStatuses', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.StudentStatusTO',
    storeId: 'studentStatusesReferenceStore',
	autoLoad: false,
	autoSync: true,

    proxy: {
		type: 'rest',
		url: '/ssp/api/reference/studentStatus/',
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