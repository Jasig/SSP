Ext.define('Ssp.view.tools.disability.General', {
    extend: 'Ext.form.Panel',
    alias: 'widget.disabilitygeneral',
    height: '100%',
    width: '100%',
    bodyPadding: 0,
    autoScroll: true,
    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'combobox',
                    fieldLabel: 'ODS Status',
                    anchor: '100%'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'If temporary eligibility, please explain',
                    anchor: '100%'
                },
                {
                    xtype: 'displayfield',
                    value: 'Display Field',
                    fieldLabel: 'ODS Registration Date',
                    anchor: '100%'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'ODS Counselor',
                    anchor: '100%'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'Referred to ODS By',
                    anchor: '100%'
                }
            ]
        });

        me.callParent(arguments);
    }
});