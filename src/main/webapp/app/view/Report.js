Ext.define('Ssp.view.Report',{
	extend: 'Ext.Component',
	alias: 'widget.sspreport',
	height: 0,
	width: 0,
    autoEl: {tag: 'iframe', cls: 'x-hidden', src: Ext.SSL_SECURE_URL},
    load: function(config){
        this.getEl().dom.src = config.url + (config.params ? '?' + Ext.urlEncode(config.params) : '');
    }
});