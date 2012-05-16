Ext.define('Ssp.store.reference.EarlyAlertSuggestions', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.EarlyAlertSuggestion',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('earlyAlertSuggestion')});
    }
});