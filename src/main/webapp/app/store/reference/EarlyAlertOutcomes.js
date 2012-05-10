Ext.define('Ssp.store.reference.EarlyAlertOutcomes', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.EarlyAlertOutcome',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + 'earlyAlertOutcome/'});
    }
});