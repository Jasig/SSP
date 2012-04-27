Ext.define('Ssp.mixin.ApiProperties', {	
	getContext: function() {
		var apiVersion = "1";
	    var base = document.getElementsByTagName('base')[0];
	    if (base && base.href && (base.href.length > 0)) {
	        base = base.href;
	    } else {
	        base = document.URL;
	    }
	    return base.substr(0, base.indexOf("/", base.indexOf("/", base.indexOf("//") + 2) + 1)) + '/api/' + apiVersion + '/';
	},

    proxy: {
		type: 'rest',
		url: '/ssp/api/1/',
		actionMethods: {
			create: "POST", 
			read: "GET", 
			update: "PUT", 
			destroy: "DELETE"
		},
		reader: {
			type: 'json'
		},
        writer: {
            type: 'json',
            successProperty: 'success'
        }
	}
});