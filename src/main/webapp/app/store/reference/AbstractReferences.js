Ext.define('Ssp.store.reference.AbstractReferences', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.AbstractReference',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties'
    },

	constructor: function(){
		var me=this;
		Ext.apply(me, { 
						    proxy: me.apiProperties.getProxy(''), 
							autoLoad: false,
							autoSync: false,
						    pageSize: me.apiProperties.getPagingSize(),
						    params : {
								page : 0,
								start : 0,
								limit : me.apiProperties.getPagingSize()
							}						
						}
		);
		return me.callParent(arguments);
	}
});