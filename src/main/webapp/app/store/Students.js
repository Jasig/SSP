Ext.define('Ssp.store.Students', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.StudentTO',
    storeId: 'studentsStore',
	autoLoad: false,

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
	
});