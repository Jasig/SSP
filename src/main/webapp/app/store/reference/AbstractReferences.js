Ext.define('Ssp.store.reference.AbstractReferences', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.AbstractReference',
    storeId: 'abstractReferencesStore',
    mixins: {apiProperties: 'Ssp.mixin.ApiProperties'},
	autoLoad: false,
	autoSync: true,
    pageSize: 15,
	constructor: function(){
    	var url = this.mixins.apiProperties.getContext.call(this);
    	Ext.apply(this.getProxy(),{url: url+'reference/'});
    	this.callParent(arguments);
	},
    params : {
		page : 0,
		start : 0,
		limit : 15
	},
    proxy: {
		type: 'rest',
		url: '/ssp/',
		actionMethods: {
			create: "POST", 
			read: "GET", 
			update: "PUT", 
			destroy: "DELETE"
		},
		reader: {
			type: 'json'
		},
        writer: {
            type: 'json',
            successProperty: 'success'
        }
	}
});