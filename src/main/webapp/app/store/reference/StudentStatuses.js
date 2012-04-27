Ext.define('Ssp.store.reference.StudentStatuses', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.StudentStatus',
    storeId: 'studentStatusesReferenceStore',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + 'studentStatus/'});
    }
});