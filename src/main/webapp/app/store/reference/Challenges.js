Ext.define('Ssp.store.reference.Challenges', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.ChallengeTO',
    storeId: 'challengesReferenceStore',
	autoLoad: false,
	autoSync: true,
    proxy: {
		type: 'rest',
		url: '/ssp/api/reference/challenge/',
		actionMethods: {
			create: "POST", 
			read: "GET", 
			update: "PUT", 
			destroy: "DELETE"
		},
		reader: {
			type: 'json'
		},
        writer: {
            type: 'json',
            successProperty: 'success'
        }
	}
	
});