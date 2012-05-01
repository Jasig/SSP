Ext.define('Ssp.store.reference.ChallengeReferrals', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.ChallengeReferral',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + 'challengeReferral/'});
    }
});