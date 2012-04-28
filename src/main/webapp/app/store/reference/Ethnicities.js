Ext.define('Ssp.store.reference.Ethnicities', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.Ethnicity',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + 'ethnicity/'});
    }
});