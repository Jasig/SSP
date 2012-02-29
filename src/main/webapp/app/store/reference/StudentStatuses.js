Ext.define('Ssp.store.reference.StudentStatuses', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.StudentStatusTO',
    storeId: 'studentStatusesReferenceStore',
	autoLoad: false,

    proxy: {
		type: 'ajax',
		api: {
			read: 'data/reference/studentstatuses.json'
		},
		reader: {
			type: 'json',
			root: 'studentStatuses',
			successProperty: 'success'
		}
	}	
	
});