Ext.define('Ssp.store.reference.EarlyAlertReasons', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.EarlyAlertReason',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + 'earlyAlertReason/'});
    }
});