Ext.define('Ssp.controller.tool.profile.ServicesProvidedViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
        person: 'currentPerson'
    },

	init: function() {
		var me=this;

		return this.callParent(arguments);
    }
});