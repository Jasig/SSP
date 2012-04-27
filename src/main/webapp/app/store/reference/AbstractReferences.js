Ext.define('Ssp.store.reference.AbstractReferences', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.AbstractReference',
    storeId: 'abstractReferencesStore',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties'
    },
	autoLoad: false,
	autoSync: true,
    pageSize: 15,
    params : {
		page : 0,
		start : 0,
		limit : 15
	},
	constructor: function(){
		var url = this.apiProperties.getContext.call(this);
		url += 'reference/';
		Ext.apply(this, { proxy: this.apiProperties.proxy });
    	Ext.apply(this.getProxy(),{url: url});
    	this.callParent(arguments);
	}
});