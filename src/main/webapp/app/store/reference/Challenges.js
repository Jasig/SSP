Ext.define('Ssp.store.reference.Challenges', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.Challenge',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + 'challenge/'});
    }
});