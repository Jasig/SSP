Ext.define('Ssp.controller.person.AnticipatedStartDateViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        person: 'currentPerson'
    },
    
	init: function() {
		this.getView().loadRecord( this.person );
		
		return this.callParent(arguments);
    }
});