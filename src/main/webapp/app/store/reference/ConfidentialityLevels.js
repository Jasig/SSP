Ext.define('Ssp.store.reference.ConfidentialityLevels', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.ConfidentialityLevel',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + 'confidentialityLevel/'});
    }
});