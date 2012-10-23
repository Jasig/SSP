Ext.define('Ssp.store.reference.DisabilityAgencies', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.DisabilityAgency',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('disabilityAgency')});
    }
});