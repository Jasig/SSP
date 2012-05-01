Ext.define('Ssp.view.ToolsMenu', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.toolsmenu',
	id: 'ToolsMenu',
	store: Ext.getStore('Tools'),
	columns:[ {header: "Assigned Tools", dataIndex: "name", flex:1} ]
	
});