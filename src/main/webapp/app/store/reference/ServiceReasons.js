Ext.define('Ssp.store.reference.ServiceReasons', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.ServiceReason',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('serviceReason')});
    }
});