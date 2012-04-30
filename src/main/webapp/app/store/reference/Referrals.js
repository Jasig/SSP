Ext.define('Ssp.store.reference.Referrals', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.Referral',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + 'referral/'});
    }
});