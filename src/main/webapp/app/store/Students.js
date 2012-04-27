Ext.define('Ssp.store.Students', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.Person',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties'
    },
    autoLoad: false,
	constructor: function(){
		Ext.apply(this, { proxy: this.apiProperties.getProxy('person/') });
		this.callParent(arguments);
	}
});