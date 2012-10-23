Ext.define('Ssp.store.reference.PersonalityTypes', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.PersonalityType',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('personalityType')});
    }
});