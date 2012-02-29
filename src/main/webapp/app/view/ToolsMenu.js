Ext.define('Ssp.view.ToolsMenu', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.ToolsMenu',
	id: 'ToolsMenu',
	store: Ext.getStore('Tools'),
	columns:[ {header: "Assigned Tools",  dataIndex: "name", flex:100} ]
	
});