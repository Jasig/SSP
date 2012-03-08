Ext.define('Ssp.util.ColumnRendererUtils',{
	extend: 'Ext.Component',

	init: function() {
        console.log('Initialized util.ColumnRendererUtils!');
		
		this.superclass.init.call(this, arguments);
    },	
	
	renderPhotoIcon: function(val) {
	    return '<img src="' + val + '">';
	}
	
});