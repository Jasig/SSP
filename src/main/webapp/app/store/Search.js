Ext.define('Ssp.store.Search', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.SearchPerson',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties'
    },
	constructor: function(){
		var me=this;
		Ext.apply(me, {
							proxy: me.apiProperties.getProxy(me.apiProperties.getItemUrl('personSearch')),
							autoLoad: false,
							autoSync: false,
						    pageSize: me.apiProperties.getPagingSize(),
						    params : {
								page : 0,
								start : 0,
								limit : me.apiProperties.getPagingSize()
							}
						});
		return me.callParent(arguments);
	},
	
	sorters: [{
        property: 'lastName',
        direction: 'ASC'
    }]
});