Ext.define('Ssp.store.security.Roles', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.security.RoleTO',
    storeId: 'rolesStore',
	autoLoad: false,

    proxy: {
		type: 'ajax',
		api: {
			read: 'data/security/userroles.json'
		},
		reader: {
			type: 'json',
			root: 'roles',
			successProperty: 'success'
		}
	}	
	
});