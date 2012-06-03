Ext.define('Ssp.store.reference.StudentTypes', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.StudentType',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('studentType')});
    }
});