Ext.define('Ssp.store.Tasks', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.tool.actionplan.Task',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	currentPerson: 'currentPerson',
    	apiProperties: 'apiProperties'
    },    
	constructor: function(){
		Ext.apply(this, { proxy: this.apiProperties.getProxy( this.apiProperties.getItemUrl('personTask') ),
						  autoLoad: false });
		return this.callParent(arguments);
	},
});