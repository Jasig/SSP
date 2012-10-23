Ext.define('Ssp.store.reference.CampusServices', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.CampusService',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('campusService')});
    }
});