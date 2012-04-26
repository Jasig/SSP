Ext.define('Ssp.store.reference.Ethnicities', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.Ethnicity',
    storeId: 'ethnicitiesReferenceStore',
    constructor: function(){
    	this.callParent(arguments);
    	var url = this.getProxy().url;
    	Ext.apply(this.getProxy(),{url: url+'ethnicity/'});
    }
});