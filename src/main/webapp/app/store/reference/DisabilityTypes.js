Ext.define('Ssp.store.reference.DisabilityTypes', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.DisabilityType',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('disabilityType')});
    }
});