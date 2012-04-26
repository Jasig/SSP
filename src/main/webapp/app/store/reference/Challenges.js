Ext.define('Ssp.store.reference.Challenges', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.Challenge',
    storeId: 'challengesReferenceStore',
    constructor: function(){
    	this.callParent(arguments);
    	var url = this.getProxy().url;
    	Ext.apply(this.getProxy(),{url: url+'challenge/'});
    }
});