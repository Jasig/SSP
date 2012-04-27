Ext.define('Ssp.store.reference.Citizenships', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.Citizenship',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + 'citizenship/'});
    }
});