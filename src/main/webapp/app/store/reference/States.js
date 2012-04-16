Ext.define('Ssp.store.reference.States', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.State',
    storeId: 'statesStore',
	autoLoad: false,

	/*
    proxy: {
		type: 'ajax',
		api: {
			read: 'data/reference/states.json'
		},
		reader: {
			type: 'json',
			root: 'states',
			successProperty: 'success'
		}
	}
	*/
	
});