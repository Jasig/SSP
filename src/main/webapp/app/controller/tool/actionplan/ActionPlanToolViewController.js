Ext.define('Ssp.controller.tool.actionplan.ActionPlanToolViewController', {
    extend: 'Deft.mvc.ViewController',	
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
        store: 'confidentialityLevelsStore'
    },
    constructor: function() {
    	this.store.load();
		return this.callParent(arguments);
    }
});