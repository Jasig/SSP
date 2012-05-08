Ext.define('Ssp.view.admin.forms.EditChallengeAdmin',{
	extend: 'Ext.form.Panel',
	height: '100%',
	width: '100%',
	title: 'Edit Challenge',
	initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: 'Challenge',
                    anchor: '100%'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Description',
                    anchor: '100%'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'Tags',
                    anchor: '100%'
                },{
                    xtype: 'combobox',
                    name: 'confidentialityLevelId',
                    fieldLabel: 'Confidentiality Level',
                    emptyText: 'Select One',
                    store: Ext.getStore('reference.ConfidentialityLevels'),
                    valueField: 'id',
                    displayField: 'name',
                    mode: 'local',
                    typeAhead: true,
                    queryMode: 'local',
                    allowBlank: true
            	},{
                    xtype: 'textareafield',
                    fieldLabel: 'Self Help Guide Description',
                    anchor: '100%'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Self Help Guide Question',
                    anchor: '100%'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Show in Student Intake',
                    anchor: '100%'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Active',
                    anchor: '100%'
                }
            ]
        });

        me.callParent(arguments);
    }	
});