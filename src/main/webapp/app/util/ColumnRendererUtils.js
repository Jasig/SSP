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
		strHtml += '<p>' + record.get('confidentialityLevel').name.toUpperCase() + '<br/>' + record.getCreatedByPersonName().toUpperCase() + '</p>';
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

	renderCreatedByDate: function(val, metaData, record) {
	    return Ext.util.Format.date( record.get('createdDate'),'m/d/Y');		
	},	

	renderCreatedBy: function(val, metaData, record) {
	    return record.get('createdBy').firstName.toUpperCase() + ' ' + record.get('createdBy').lastName.toUpperCase();		
	},	
	
	renderCreatedByDateAndName: function(val, metaData, record) {
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<p>' + record.get('createdBy').firstName.toUpperCase() + ' ' + record.get('createdBy').lastName.toUpperCase() + '</p>';
        strHtml += '<p>' + Ext.util.Format.date( record.get('createdDate'),'m/d/Y') + '</p>';
        strHtml += '</div>';
	    return strHtml;		
	},
 
	renderJournalSourceName: function(val, metaData, record) {
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<p>' + record.get('journalSource').name.toUpperCase() + '</p>';
         strHtml += '</div>';
	    return strHtml;		
	},	
	
	renderPhotoIcon: function(val) {
	    return '<img src="' + val + '">';
	},
	
	renderStudentDetails: function(val, metaData, record) {
		var strHtml = '<div>';
        strHtml += '<p>' + record.getFullName() + '</p>';
        strHtml += '<p>' + record.get('schoolId') + '</p>';
        strHtml += '</div>';
	    return strHtml;
	},
	
	renderAddToolIcon: function(value,meta,record,rowIx,ColIx, store) {
	    return (record.get("active")==false)?
	                'addToolIcon':
	                'hideAddToolIcon';
	},
	
	/**
	 * This method is used to return an object with id values
	 * an array format expected by the ExtJS multiSelect or itemSelect
	 * components.
	 * 
	 * Translates: 
	 * [{"id":"1"},{"id":"2"},{"id":"3"}]
	 * 
	 * Into:
	 * ["1","2","3"]
	 */
	getSelectedIdsForMultiSelect: function( arr ){
		var selectedIds = [];
		Ext.each(arr,function(item,index){
			selectedIds.push(item["id"]);
		});
		return selectedIds;
	}
});