Ext.define('Ssp.view.tools.sis.Act', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.sisact',
    width: '100%',
    height: '100%',
    autoScroll: true,
    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            columns: [
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    text: 'Type'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    text: 'Score'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    text: 'Status'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    text: 'Date'
                }
            ],
            viewConfig: {

            }
        });

        me.callParent(arguments);
    }
});