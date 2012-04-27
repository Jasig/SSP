Ext.define('Ssp.store.reference.Citizenships', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.Citizenship',
    storeId: 'citizenshipsReferenceStore',
    constructor: function(){
    	this.callParent(arguments);
    	var url = this.getProxy().url;
    	Ext.apply(this.getProxy(),{url: url+'citizenship/'});
    }
});