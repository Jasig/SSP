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
		var successFunc = function(response ,view){
			var r = Ext.decode(response.responseText);
			if (r.success==true)
			{
				// load the permissions
				me.authenticatedPerson.set('permissions',r.permissions);
			}
		};
		
		me.sessionUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('session') );
        
        // determine the permissions for the current authenticated
		me.apiProperties.makeRequest({
			url: me.sessionUrl+'permissions',
			method: 'GET',
			successFunc: successFunc
		});
        
		// init the main view for the application
        me.getView().add( [{xtype:'mainview'}] );
		
		return this.callParent(arguments);
    }
});