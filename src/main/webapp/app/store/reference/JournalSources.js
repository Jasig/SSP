Ext.define('Ssp.store.reference.JournalSources', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.JournalSource',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + 'journalSource/'});
    }
});