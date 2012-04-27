Ext.define('Ssp.store.reference.ChildCareArrangements', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.ChildCareArrangement',
    storeId: 'childCareArrangementsReferenceStore',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + 'childCareArrangement/'});
    }
});