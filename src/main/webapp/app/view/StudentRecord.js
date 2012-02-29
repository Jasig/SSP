Ext.define('Ssp.view.StudentRecord', {
	extend: 'Ext.panel.Panel',
    alias: 'widget.StudentRecord',
    id: 'StudentRecord',
    title: 'Student Record',
    collapsible: true,
    collapseDirection: 'right',
    layout: {
    	type: 'hbox',
    	align: 'stretch'
    },
	dockedItems: {
	            itemId: 'toolbar',
	            xtype: 'toolbar',
	            text:'Add Tools',
	            menu: Ext.create('Ext.menu.Menu', {
		                xtype: 'menu',
		                plain: true,
		                items: [{
		                    xtype: 'buttongroup',
		                    title: 'User options',
		                    columns: 2,
		                    defaults: {
		                        xtype: 'button',
		                        scale: 'large',
		                        iconAlign: 'left'
		                    },
		                    items: [{
		                        text: 'User<br/>manager',
		                        iconCls: 'edit',
		                        width: 90
		                    },{
		                        iconCls: 'add',
		                        width: 'auto',
		                        tooltip: 'Add user',
		                        width: 40
		                    },{
		                        colspan: 2,
		                        text: 'Import',
		                        scale: 'small',
		                        width: 130
		                    },{
		                        colspan: 2,
		                        text: 'Who is online?',
		                        scale: 'small',
		                        width: 130
		                    }]
                		}]
                	})
	    },
});	