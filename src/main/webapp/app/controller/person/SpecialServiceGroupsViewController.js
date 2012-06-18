Ext.define('Ssp.controller.person.SpecialServiceGroupsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	specialServiceGroupsStore: 'specialServiceGroupsStore'
    },
    
	init: function() {
		this.specialServiceGroupsStore.load();
		
		return this.callParent(arguments);
    }
});