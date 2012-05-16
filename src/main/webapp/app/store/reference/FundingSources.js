Ext.define('Ssp.store.reference.FundingSources', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.FundingSource',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('fundingSource')});
    }
});