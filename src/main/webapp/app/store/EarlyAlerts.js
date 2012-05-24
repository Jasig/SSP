Ext.define('Ssp.store.EarlyAlerts', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.tool.earlyalert.PersonEarlyAlert',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties'
    },    
	constructor: function(){
		Ext.apply(this, { proxy: this.apiProperties.getProxy( this.apiProperties.getItemUrl('personEarlyAlert') ),
						  autoLoad: false });
		return this.callParent(arguments);
	}
});