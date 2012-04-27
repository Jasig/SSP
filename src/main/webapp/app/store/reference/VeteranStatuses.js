Ext.define('Ssp.store.reference.VeteranStatuses', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.VeteranStatus',
    storeId: 'veteranStatusesReferenceStore',
    constructor: function(){
    	this.callParent(arguments);
    	var url = this.getProxy().url;
    	Ext.apply(this.getProxy(),{url: url+'veteranStatus/'});
    }
});