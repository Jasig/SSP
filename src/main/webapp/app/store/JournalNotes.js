Ext.define('Ssp.store.JournalNotes', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.tool.journal.Note',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	currentPerson: 'currentPerson',
    	apiProperties: 'apiProperties'
    },    
	constructor: function(){
		Ext.apply(this, { proxy: this.apiProperties.getProxy('person/' + '0' + '/journalNote/' ),
						  autoLoad: false });
		return this.callParent(arguments);
	},
});