Ext.define('Ssp.store.reference.EarlyAlertReferrals', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.EarlyAlertReferral',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('earlyAlertReferral')});
    }
});