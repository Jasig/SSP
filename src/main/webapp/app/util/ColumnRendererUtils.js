Ext.define('Ssp.util.ColumnRendererUtils',{
	extend: 'Ext.Component',

	initComponent: function() {
		return this.callParent( arguments );
    },	

    renderJournalNote: function(val, metaData, record){
    	return record.data.name;
    },    
    
    renderJournalCreatedDate: function(val, metaData, record){
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<h1>' + Ext.util.Format.date(record.get('createdDate'),'m/d/Y') + '</h1>';
		strHtml += '<p>' + record.get('confidentialityLevel') + '<br/>' + record.getCreatedByPersonName() + '</p>';
		strHtml += '</div>';
	    return strHtml;
    },
    
	renderTaskName: function(val, metaData, record) {
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<h1>' + record.get('name').toUpperCase() + '</h1>';
		strHtml += '<p>' + record.get('description') + '</p>';
		strHtml += '</div>';
	    return strHtml;
	},

	renderTaskDueDate: function(val, metaData, record) {
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<h1>' + Ext.util.Format.date( record.get('dueDate') ,'m/d/Y') + '</h1>';
		strHtml += '<p>' + record.get('confidentialityLevel') + '<br/>' + record.getCreatedByPersonName() + '</p>';
		strHtml += '</div>';
	    return strHtml;
	},
    
	renderPhotoIcon: function(val) {
	    return '<img src="' + val + '">';
	}    	
});