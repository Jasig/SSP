Ext.define('Ssp.store.reference.JournalStepDetails', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.JournalStepDetail',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + 'journalStepDetail/'});
    }
});