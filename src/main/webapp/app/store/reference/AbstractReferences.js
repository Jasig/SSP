Ext.define('Ssp.store.reference.AbstractReferences', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.AbstractReference',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties'
    },

	constructor: function(){
		Ext.apply(this, { 
						    proxy: this.apiProperties.getProxy('reference/'), 
							autoLoad: false,
							autoSync: false,
						    pageSize: this.apiProperties.getPagingSize(),
						    params : {
								page : 0,
								start : 0,
								limit : this.apiProperties.getPagingSize()
							}						
						}
		);
		return this.callParent(arguments);
	}
});