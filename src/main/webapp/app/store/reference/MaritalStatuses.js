Ext.define('Ssp.store.reference.MaritalStatuses', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.MaritalStatus',
    storeId: 'maritalStatusesReferenceStore',
    constructor: function(){
    	this.callParent(arguments);
    	var url = this.getProxy().url;
    	Ext.apply(this.getProxy(),{url: url+'maritalStatus/'});
    }
});