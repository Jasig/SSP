Ext.define('Ssp.store.reference.Challenges', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.ChallengeTO',
    storeId: 'challengesReferenceStore',
	autoLoad: false,

    proxy: {
		type: 'ajax',
		api: {
			read: 'data/reference/challenges.json'
		},
		reader: {
			type: 'json',
			root: 'challenges',
			successProperty: 'success'
		}
	}	
	
});