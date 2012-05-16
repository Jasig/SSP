Ext.define('Ssp.store.reference.JournalTracks', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.JournalTrack',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('journalTrack')});
    }
});