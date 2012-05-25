Ext.define('Ssp.view.tools.actionplan.AddTask', {
	extend: 'Ext.panel.Panel',
	alias: 'widget.addtask',
	mixins: [ 'Deft.mixin.Injectable'],
	inject: {
    	model: 'currentTask'
    },
	width: '100%',
    height: '100%',
	autoScroll: true,
    defaults: {
        anchor: '100%'
    },    
	
    initComponent: function() {
		var editorState = "Edit";
		if (this.model.id=="")
			editorState = "Add";
		console.log(editorState);
		Ext.apply(this,{
						title: editorState + ' Action Plan Tasks',
						items: [{ xtype: 'tasktree', flex:1 },
						        { xtype: 'addtaskform', flex:1 }]
		});
		
		return this.callParent(arguments);
	}
});
