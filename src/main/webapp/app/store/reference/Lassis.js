Ext.define('Ssp.store.reference.Lassis', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.Lassi',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('lassi')});
    }
});