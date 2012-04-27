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
    pageSize: 40,
    params : {
		page : 0,
		start : 0,
		limit : 40
	},
	constructor: function(){
		Ext.apply(this, { proxy: this.apiProperties.getProxy('reference/') });
		this.callParent(arguments);
	}
});