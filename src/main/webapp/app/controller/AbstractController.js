Ext.define('Ssp.controller.AbstractController', {
    extend: 'Ext.app.Controller',
    requires: ['Ssp.util.FormRendererUtils'],
		
	init: function() {
        console.log('Initialized Abstract Controller!');
		var formRendererUtils = new Ssp.util.FormRendererUtils(); 
        var config = { formRendererUtils: formRendererUtils };
        Ext.apply( this, config );
        this.callParent(arguments);
    }

});