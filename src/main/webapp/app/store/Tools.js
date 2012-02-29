Ext.define('Ssp.store.Tools', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.ToolTO',
    storeId: 'toolsStore',
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