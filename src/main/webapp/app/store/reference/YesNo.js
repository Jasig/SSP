Ext.define('Ssp.store.reference.YesNo', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.AbstractReferenceTO',
    storeId: 'yesNoReferenceStore',
	autoLoad: false,

    proxy: {
		type: 'ajax',
		api: {
			read: 'data/reference/yesno.json'
		},
		reader: {
			type: 'json',
			root: 'items',
			successProperty: 'success'
		}
	}	
	
});