Ext.define('Ssp.controller.ViewportViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	authenticatedPerson: 'authenticatedPerson'
    },
    config: {
    	sessionUrl: null
    },
	init: function() {
		var me=this;
		
		me.sessionUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('session') );
        
		// init the main view for the application
        me.getView().add( [{xtype:'mainview'}] );
		
		return this.callParent(arguments);
    }
});