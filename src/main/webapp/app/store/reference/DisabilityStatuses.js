Ext.define('Ssp.store.reference.DisabilityStatuses', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.DisabilityStatus',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('disabilityStatus')});
    }
});