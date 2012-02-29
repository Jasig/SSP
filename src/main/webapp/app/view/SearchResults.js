Ext.define('Ssp.view.SearchResults', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.SearchResults',
	title: 'Students',
	id: 'SearchResults',
    collapsible: false,
    collapseDirection: 'left',
    store: Ext.getStore('Students'),
  
    columns: [
        { header: "Photo", dataIndex: 'photoUrl', renderer: Ext.create('Ssp.util.ColumnRendererUtils').renderPhotoIcon, flex: 50 },		        
        { header: 'Name',  dataIndex: 'name', flex: 50 }
    ],
    
    dockedItems: [{
        xtype: 'pagingtoolbar',
        store: Ext.getStore('Students'),
        dock: 'bottom',
        displayInfo: true
    }]

});