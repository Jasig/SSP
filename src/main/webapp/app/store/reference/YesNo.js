Ext.define('Ssp.store.reference.YesNo', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.AbstractReference',
    storeId: 'yesNoReferenceStore',
	autoLoad: false,
    items: [{id: "Y", name: "Yes"},
		    {id: "N", name: "No"}]
});