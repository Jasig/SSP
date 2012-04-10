Ext.define('Ssp.controller.AbstractViewController', {
    extend: 'Ext.app.Controller',
    requires: ['Ssp.util.FormRendererUtils'],
    
	init: function() {
		var formRendererUtils = new Ssp.util.FormRendererUtils(); 
        var config = { formRendererUtils: formRendererUtils };
        Ext.apply( this, config );
        this.callParent(arguments);
    }

});