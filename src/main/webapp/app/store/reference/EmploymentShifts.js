Ext.define('Ssp.store.reference.EmploymentShifts', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.AbstractReferenceTO',
    storeId: 'employmentShiftsReferenceStore',
	autoLoad: false,

    proxy: {
		type: 'ajax',
		api: {
			read: 'data/reference/employmentshifts.json'
		},
		reader: {
			type: 'json',
			root: 'items',
			successProperty: 'success'
		}
	}	
	
});