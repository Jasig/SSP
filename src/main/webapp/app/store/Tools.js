Ext.define('Ssp.store.Tools', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.Tool',
	autoLoad: false,
    proxy: {
		type: 'ajax',
		api: {
			read: 'data/tools.json'
		},
		reader: {
			type: 'json',
			root: 'tools',
			successProperty: 'success'
		}
	}
});