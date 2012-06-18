Ext.define('Ssp.store.reference.AnticipatedStartTerms', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.AbstractReference',
    autoLoad: false,
    constructor: function(){
		return this.callParent(arguments);
    },
    data: [{ name: "FA", description: "FA" },
           { name: "WI", description: "WI" },
           { name: "SP", description: "SP" },
           { name: "SU", description: "SU" }]
});