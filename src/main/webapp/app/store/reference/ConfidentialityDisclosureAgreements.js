Ext.define('Ssp.store.reference.ConfidentialityDisclosureAgreements', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.ConfidentialityDisclosureAgreement',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + 'confidentialityDisclosureAgreement/'});
    }
});