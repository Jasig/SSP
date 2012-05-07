Ext.define('Ssp.util.ColumnRendererUtils',{
	extend: 'Ext.Component',

	initComponent: function() {
		return this.callParent( arguments );
    },	
	
	renderPhotoIcon: function(val) {
	    return '<img src="' + val + '">';
	}    	
});