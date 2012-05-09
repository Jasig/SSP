Ext.define('Ssp.util.ColumnRendererUtils',{
	extend: 'Ext.Component',

	initComponent: function() {
		return this.callParent( arguments );
    },	

	renderTaskName: function(val, metaData, record) {
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<h1>' + record.data.name.toUpperCase() + '</h1>';
		strHtml += '<p>' + record.data.description + '</p>';
		strHtml += '</div>';
	     return strHtml;
	},

	renderTaskDueDate: function(val, metaData, record) {
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<h1>' + Ext.util.Format.date(record.data.dueDate,'m/d/Y') + '</h1>';
		strHtml += '<p>' + record.data.confidentialityLevel + '<br/>' + record.data.createdById + '</p>';
		strHtml += '</div>';
	     return strHtml;
	},
    
	renderPhotoIcon: function(val) {
	    return '<img src="' + val + '">';
	}    	
});