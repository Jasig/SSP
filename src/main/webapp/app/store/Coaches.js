Ext.define('Ssp.store.Coaches', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.Coach',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties'
    },
	constructor: function(){
		var me=this;
		Ext.apply(me, {
						proxy: me.apiProperties.getProxy(me.apiProperties.getItemUrl('personCoach')),
						autoLoad: false
					});
		return me.callParent(arguments);
	}
});