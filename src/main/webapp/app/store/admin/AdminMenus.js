Ext.define('Ssp.store.admin.AdminMenus', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.admin.Menu',
    storeId: 'adminMenuStore',
	autoLoad: false,

    proxy: {
		type: 'ajax',
		api: {
			read: 'data/admin/admin-menus.json'
		},
		reader: {
			type: 'json',
			root: 'menus',
			successProperty: 'success'
		}
	}
	
});