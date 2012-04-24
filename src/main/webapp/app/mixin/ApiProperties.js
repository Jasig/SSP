Ext.define('Ssp.mixin.ApiProperties', {	
	getContext: function() {
	    var base = document.getElementsByTagName('base')[0];
	    if (base && base.href && (base.href.length > 0)) {
	        base = base.href;
	    } else {
	        base = document.URL;
	    }
	    return base.substr(0, base.indexOf("/", base.indexOf("/", base.indexOf("//") + 2) + 1)) + '/api/1/';
	}
});