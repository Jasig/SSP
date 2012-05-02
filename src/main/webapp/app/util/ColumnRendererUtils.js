Ext.define('Ssp.util.ColumnRendererUtils',{
	extend: 'Ext.Component',

	init: function() {
		this.callParent( arguments );
    },	
	
	renderPhotoIcon: function(val) {
	    return '<img src="' + val + '">';
	}    	
});