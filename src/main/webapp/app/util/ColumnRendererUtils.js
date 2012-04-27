Ext.define('Ssp.util.ColumnRendererUtils',{
	extend: 'Ext.Component',

	init: function() {
		this.callParent( arguments );
    },	
	
    statics: {
    	renderPhotoIcon: function(val) {
    	    return '<img src="' + val + '">';
    	}    	
    }
});