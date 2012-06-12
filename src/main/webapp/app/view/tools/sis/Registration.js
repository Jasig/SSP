Ext.define('Ssp.view.tools.sis.Registration', {
	extend: 'Ext.form.Panel',
	alias: 'widget.sisregistration',
    width: '100%',
    height: '100%',
    autoScroll: true,
    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'displayfield',
                    fieldLabel: 'Academic Status',
                    anchor: '100%'
                },
                {
                    xtype: 'displayfield',
                    fieldLabel: 'Registration Status',
                    anchor: '100%'
                },
                {
                    xtype: 'displayfield',
                    fieldLabel: 'Start Term',
                    anchor: '100%'
                },
                {
                    xtype: 'displayfield',
                    fieldLabel: 'CUM GPA',
                    anchor: '100%'
                }
            ]
        });

        me.callParent(arguments);
    }
});