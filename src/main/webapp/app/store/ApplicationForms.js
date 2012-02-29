Ext.define('Ssp.store.ApplicationForms', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.ApplicationFormTO',
    storeId: 'applicationFormsStore',
	autoLoad: true,

    proxy: {
		type: 'ajax',
		api: {
			read: 'data/forms.json'
		},
		reader: {
			type: 'json',
			root: 'items',
			successProperty: 'success'
		}
	}	
	
});