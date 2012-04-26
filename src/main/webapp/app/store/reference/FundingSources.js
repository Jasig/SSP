Ext.define('Ssp.store.reference.FundingSources', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.FundingSource',
    storeId: 'fundingSourcesReferenceStore',
    constructor: function(){
    	this.callParent(arguments);
    	var url = this.getProxy().url;
    	Ext.apply(this.getProxy(),{url: url+'fundingSource/'});
    }
});