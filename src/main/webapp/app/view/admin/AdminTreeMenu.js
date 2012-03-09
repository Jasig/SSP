Ext.define('Ssp.view.admin.AdminTreeMenu', {
	extend: 'Ext.tree.Panel',
	alias : 'widget.AdminTreeMenu',
	id: 'AdminTreeMenu', 
    store: Ext.getStore('admin.AdminTreeMenus'),  
    fields: ['title','form','text']
	/*
	,
    columns: [{
        xtype: 'treecolumn',
        text: 'Administrative Tools',
        flex: 1,
        sortable: true,
        text: 'text',
        dataIndex: 'text'
    }]
    */
}); 