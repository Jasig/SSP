Ext.define('Ssp.store.Tasks', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.tool.actionplan.TaskGroup',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties'
    },    
	constructor: function(){
		Ext.apply(this, { proxy: this.apiProperties.getProxy( this.apiProperties.getItemUrl('personTaskGroup') ),
						  autoLoad: false });
		return this.callParent(arguments);
	},
	
	groupField: 'group'
});