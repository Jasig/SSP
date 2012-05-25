Ext.define('Ssp.util.ColumnRendererUtils',{
	extend: 'Ext.Component',

	initComponent: function() {
		return this.callParent( arguments );
    },
    
	renderTaskName: function(val, metaData, record) {
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<p>' + record.get('name').toUpperCase() + '</p>';
		strHtml += '<p>' + record.get('description') + '</p>';
		strHtml += '</div>';
	    return strHtml;
	},

	renderTaskDueDate: function(val, metaData, record) {
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<p>' + Ext.util.Format.date( record.get('dueDate') ,'m/d/Y') + '</p>';
		strHtml += '<p>' + record.get('confidentialityLevel').name.toUpperCase() + '<br/>' + record.getCreatedByPersonName() + '</p>';
		strHtml += '</div>';
	    return strHtml;
	},	
	
	renderGoalName: function(val, metaData, record) {
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<p>' + record.get('name').toUpperCase() + '</p>';
		strHtml += '<p>' + record.get('description') + '</p>';
		strHtml += '</div>';
	    return strHtml;	
	},	
	
	renderConfidentialityLevelName: function(val, metaData, record) {
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<p>' + record.get('confidentialityLevel').name.toUpperCase() + '</p>';
		strHtml += '</div>';
	    return strHtml;		
	},

	renderCreatedByDateAndName: function(val, metaData, record) {
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<p>' + record.get('createdBy').firstName.toUpperCase() + ' ' + record.get('createdBy').lastName.toUpperCase() + '</p>';
        strHtml += '<p>' + Ext.util.Format.date( record.get('createdDate'),'m/d/Y') + '</p>';
        strHtml += '</div>';
	    return strHtml;		
	},
    
	renderPhotoIcon: function(val) {
	    return '<img src="' + val + '">';
	}    	
});