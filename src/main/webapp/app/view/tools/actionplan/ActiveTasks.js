Ext.define('Ssp.view.tools.actionplan.ActiveTasks', {
	extend: 'Ext.grid.Panel',    
	width: '100%',
    height: '100%',
    title: 'Active Tasks',
    store: Ext.getStore('Tasks'),
    
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
        dataIndex: 'name'
    }, {
        header: 'Due Date',
        width: 80,
        dataIndex: 'dueDate',
        renderer: Ext.util.Format.dateRenderer('m/d/Y')
    }]
});