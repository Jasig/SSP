Ext.define('Ssp.store.Students', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.Student',
    storeId: 'studentsStore',
    autoLoad: true,
	proxy: {
		type: 'rest',
		url: '/ssp/api/1/person/',
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