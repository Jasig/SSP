Ext.define('Ssp.store.EarlyAlertCoordinators', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.PersonLite',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties'
    },
	constructor: function(){
		var me=this;
		Ext.apply(me, {
						proxy: me.apiProperties.getProxy(me.apiProperties.getItemUrl('person')),
						autoLoad: false
					});
		return me.callParent(arguments);
	}
});