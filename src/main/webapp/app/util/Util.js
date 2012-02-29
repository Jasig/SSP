Ext.define('Ssp.util.Util', {
    
    extend: 'Ext.Component',   		
        		        
    loaderXTemplateRenderer: function(loader, response, active) {
        var tpl = new Ext.XTemplate(response.responseText);
        var targetComponent = loader.getTarget();
        var cleanArr = Ext.create( 'Ssp.util.TemplateDataUtil' ).prepareTemplateData( targetComponent.store );
        targetComponent.update( tpl.apply( cleanArr ) );
        return true;
    }
});