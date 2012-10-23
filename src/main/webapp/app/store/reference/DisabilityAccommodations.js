Ext.define('Ssp.store.reference.DisabilityAccommodations', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.DisabilityAccommodation',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('disabilityAccommodation')});
    }
});