Ext.define('Ssp.store.reference.ChildCareArrangements', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.ChildCareArrangement',
    storeId: 'childCareArrangementsReferenceStore',
    constructor: function(){
    	this.callParent(arguments);
    	var url = this.getProxy().url;
    	Ext.apply(this.getProxy(),{url: url+'childCareArrangement/'});
    }
});