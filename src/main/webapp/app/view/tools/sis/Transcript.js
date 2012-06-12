Ext.define('Ssp.view.tools.sis.Transcript', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.sistranscript',
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
                    text: 'Course'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    text: 'Section'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    text: 'Credit'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    text: 'Title'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    text: 'Grade'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    text: 'Term'
                }
            ],
            viewConfig: {

            }
        });

        me.callParent(arguments);
    }
});