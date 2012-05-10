Ext.define('Ssp.view.tools.actionplan.AddTask', {
	extend: 'Ext.panel.Panel',
	alias: 'widget.addtask',
	id: 'AddTask',
	width: '100%',
    height: '100%',
	autoScroll: true,
    defaults: {
        anchor: '100%'
    },    
	initComponent: function() {
		Ext.apply(this,{
						title: 'Add Tasks',
						items: [{ xtype: 'addtaskform', flex:1 }]
		});
		
		return this.callParent(arguments);
	}
});
