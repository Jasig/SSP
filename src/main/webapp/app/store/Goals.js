Ext.define('Ssp.store.Goals', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.PersonGoal',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties'
    },    
	constructor: function(){
		Ext.apply(this, { proxy: this.apiProperties.getProxy( this.apiProperties.getItemUrl('personGoal') ),
						  autoLoad: false });
		return this.callParent(arguments);
	},
});