Ext.define('Ssp.store.reference.FundingSources', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.FundingSourceTO',
    storeId: 'fundingSourcesReferenceStore',
	autoLoad: false,

    proxy: {
		type: 'ajax',
		api: {
			read: 'data/reference/fundingsources.json'
		},
		reader: {
			type: 'json',
			root: 'fundingSources',
			successProperty: 'success'
		}
	}	
	
});