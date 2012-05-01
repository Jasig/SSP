Ext.define('Ssp.store.reference.AbstractReferences', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.AbstractReference',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties'
    },
	autoLoad: false,
	autoSync: true,
    pageSize: 20,
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