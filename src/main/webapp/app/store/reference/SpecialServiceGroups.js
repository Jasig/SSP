Ext.define('Ssp.store.reference.SpecialServiceGroups', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.SpecialServiceGroup',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('specialServiceGroup')});
    }
});