Ext.define('Ssp.store.Caseload', {
    // extend: 'Ext.data.Store',
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.CaseloadPerson',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties'
    },
	constructor: function(){
		var me=this;
		Ext.apply(me, {
						proxy: me.apiProperties.getProxy(me.apiProperties.getItemUrl('personCaseload')),
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
	}
});