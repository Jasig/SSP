Ext.define('Ssp.store.reference.AnticipatedStartYears', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.AbstractReference',
    autoLoad: false,
    constructor: function(){
		return this.callParent(arguments);
    },
    data: [{ name: "2010", description: "2010" },
           { name: "2011", description: "2011" },
           { name: "2012", description: "2012" },
           { name: "2013", description: "2013" },
           { name: "2014", description: "2014" },
           { name: "2015", description: "2015" }]
});