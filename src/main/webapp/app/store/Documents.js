Ext.define('Ssp.store.Documents', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.PersonDocument',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties'
    },    
	constructor: function(){
		Ext.apply(this, { proxy: this.apiProperties.getProxy( this.apiProperties.getItemUrl('personDocument') ),
						  autoLoad: false });
		return this.callParent(arguments);
	}
});