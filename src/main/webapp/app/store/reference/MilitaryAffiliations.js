Ext.define('Ssp.store.reference.MilitaryAffiliations', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.MilitaryAffiliation',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('militaryAffiliation')});
    }
});