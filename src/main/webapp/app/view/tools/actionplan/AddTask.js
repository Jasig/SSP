Ext.define('Ssp.view.tools.actionplan.AddTask', {
	extend: 'Ext.panel.Panel',
	alias: 'widget.addtask',
	mixins: [ 'Deft.mixin.Injectable'],
	inject: {
    	model: 'currentTask'
    },
	width: '100%',
    height: '100%',
    initComponent: function() {
		Ext.apply(this,{
			autoScroll: true,
			layout: {
				type: 'vbox',
				align: 'stretch'
			},
			title: 'Add Action Plan Tasks',
			items: [{ xtype: 'tasktree', flex: .5 },
			        { xtype: 'addtaskform', flex: .5 }]
		});
		
		return this.callParent(arguments);
	}
});
