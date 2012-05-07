Ext.define('Ssp.controller.tool.ProfileToolViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        currentPerson: 'currentPerson'       
    }, 

	init: function() {
		this.getView().loadRecord(this.currentPerson);
		return this.callParent(arguments);
    }
});