Ext.define('Ssp.store.reference.Categories', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.Category',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + 'category/'});
    }
});