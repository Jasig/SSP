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
						proxy: me.apiProperties.getProxy(me.apiProperties.getItemUrl('personCoach')+'/?sort=lastName'),
						autoLoad: false,
						pageSize: -1,
						params : {
							page : 0,
							start : 0,
							limit : -1
						}
					});
		return me.callParent(arguments);
	}
});