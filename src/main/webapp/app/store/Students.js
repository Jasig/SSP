Ext.define('Ssp.store.Students', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.StudentTO',
    storeId: 'studentsStore',
	autoLoad: false,

	/*    
	proxy: {
		type: 'ajax',
		api: {
			read: 'data/students.json'
		},
		reader: {
			type: 'json',
			root: 'students',
			successProperty: 'success'
		}
	}
*/
    proxy: {
		type: 'rest',
		url: '/ssp/api/person/',
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