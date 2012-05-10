Ext.define('Ssp.view.tools.actionplan.Tasks', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.tasks',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	columnRendererUtils: 'columnRendererUtils',
        tasksStore: 'tasksStore',
    },
	width: '100%',
    height: '100%',
    initComponent: function(){
    	
    	var sm = Ext.create('Ext.selection.CheckboxModel');
    	
    	Ext.apply(this,
    			{
    	    		store: this.tasksStore,    		
    	    		selModel: sm,
    	    		features: [{
		    	        id: 'group',
		    	        ftype: 'groupingsummary',
		    	        groupHeaderTpl: '{name}',
		    	        hideGroupedHeader: true,
		    	        enableGroupingMenu: false
		    	    }],
		
		    	    columns: [{
		    	        text: 'Task',
		    	        flex: 1,
		    	        tdCls: 'task',
		    	        sortable: true,
		    	        dataIndex: 'name',
		    	        renderer: this.columnRendererUtils.renderTaskName
		    	    },{
		    	        header: 'Due Date',
		    	        width: 100,
		    	        dataIndex: 'dueDate',
		    	        renderer: this.columnRendererUtils.renderTaskDueDate
		    	    }]    		

    			});
    	
    	return this.callParent(arguments);
    }
});