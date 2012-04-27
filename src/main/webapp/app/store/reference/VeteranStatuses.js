Ext.define('Ssp.store.reference.VeteranStatuses', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.VeteranStatus',
    storeId: 'veteranStatusesReferenceStore',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + 'veteranStatus/'});
    }
});