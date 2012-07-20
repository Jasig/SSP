Ext.define('Ssp.view.Report',{
	extend: 'Ext.Component',
	alias: 'widget.sspreport',
	height: 0,
	width: 0,
    config: {
    	downloadForm: null,
    	downloadFrame: null
    },
    autoEl: {tag: 'iframe', cls: 'x-hidden', src: Ext.SSL_SECURE_URL},
    initComponent: function(){
    	var me=this;
    	me.downloadForm = Ext.getBody().createChild({
    		tag: 'form'
    		, cls: 'x-hidden'
    		, id: 'sspPortletReportform'
    		, target: 'sspPortletIFrame'
    		});
    	
    	me.downloadFrame = Ext.getBody().createChild({
    		tag: 'iframe'
    		, cls: 'x-hidden'
    		, id: 'sspPortletIFrame'
    		, name: 'iframe'
    		, src: Ext.SSL_SECURE_URL
    		});

    	return me.callParent(arguments);
    },
    
    load: function(config){
    	this.getEl().dom.src = config.url + (config.params ? '?' + Ext.urlEncode(config.params) : '');
    },
    
    postReport: function( args ){
		var me=this;
    	Ext.Ajax.request({
			url: args.url,
			form: me.downloadForm,
			params: args.params,
			isUpload: true,
			headers: { 'Content-Type': 'application/json' },
			success: function(response,responseText){
	  			  //Ext.Msg.alert('Notification','Please download your report.');
	  		},
			failure: function(response, options) {
				Ext.Msg.alert('Notification',response.responseText);
	  	    },
			scope: me
		},me);
    }
});