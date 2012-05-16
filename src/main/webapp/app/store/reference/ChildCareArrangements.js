Ext.define('Ssp.store.reference.ChildCareArrangements', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.ChildCareArrangement',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('childCareArrangement')});
    }
});