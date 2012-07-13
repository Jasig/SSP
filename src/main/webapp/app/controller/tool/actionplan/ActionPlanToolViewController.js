Ext.define('Ssp.controller.tool.actionplan.ActionPlanToolViewController', {
    extend: 'Deft.mvc.ViewController',	
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
        store: 'confidentialityLevelsStore'
    },
    constructor: function() {
    	var me=this;
		
    	// ensure loading of all confidentiality levels in the database
    	me.store.load({
    		params:{limit:50}
    	});
    	
		return me.callParent(arguments);
    }  
});