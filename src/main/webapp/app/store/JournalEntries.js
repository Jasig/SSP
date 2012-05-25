Ext.define('Ssp.store.JournalEntries', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.tool.journal.JournalEntry',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	currentPerson: 'currentPerson',
    	apiProperties: 'apiProperties'
    },    
	constructor: function(){
		Ext.apply(this, { proxy: this.apiProperties.getProxy( this.apiProperties.getItemUrl('personJournalEntry') ),
						  autoLoad: false });
		return this.callParent(arguments);
	},
});