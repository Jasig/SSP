Ext.define('Ssp.view.Search', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.search',
	title: 'Students',
	id: 'Search',
    collapsible: false,
    collapseDirection: 'left',
    store: Ext.getStore('Students'),
    
    columns: [
        { header: "Photo", dataIndex: 'photoUrl', renderer: Ssp.util.ColumnRendererUtils.renderPhotoIcon, flex: 50 },		        
        { text: 'Name', xtype:'templatecolumn', tpl:'{firstName} {middleInitial} {lastName}', flex: 50}
    ],
    
    dockedItems: [{
        xtype: 'pagingtoolbar',
        store: Ext.getStore('Students'),
        dock: 'bottom',
        displayInfo: true
    }]

});