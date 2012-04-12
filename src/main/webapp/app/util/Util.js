Ext.define('Ssp.util.Util', {  
    extend: 'Ext.Component',   		
	
    init: function() {
		this.callParent( arguments );
    }, 
    
    loaderXTemplateRenderer: function(loader, response, active) {
        var tpl = new Ext.XTemplate(response.responseText);
        var targetComponent = loader.getTarget();
        var cleanArr = Ext.create( 'Ssp.util.TemplateDataUtil' ).prepareTemplateData( targetComponent.store );
        targetComponent.update( tpl.apply( cleanArr ) );
        return true;
    }
});