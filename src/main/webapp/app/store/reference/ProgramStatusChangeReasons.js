Ext.define('Ssp.store.reference.ProgramStatusChangeReasons', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.ProgramStatusChangeReason',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('programStatusChangeReason')});
    }
});