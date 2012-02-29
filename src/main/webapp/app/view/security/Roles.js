Ext.define('Ssp.view.security.Roles', {
	extend: 'Ext.form.Panel',
	alias : 'widget.Roles',
	id: 'Roles',
	stores: ['security.Roles'],
	width: '100%',
	height: '100%',
    labelWidth:80, 
    frame:true, 
    defaultType:'textfield',
	monitorValid:true,

	stores: ['security.Roles'],

    items:[{
        xtype: 'combobox',
        id: 'securityRoleCombo',
        name: 'roleName',
        fieldLabel: 'Role',
        emptyText: 'Select One',
        store: Ext.getStore('security.Roles'),
        valueField: 'id',
        displayField: 'roleName',
        mode: 'local',
        typeAhead: true,
        queryMode: 'local',
        allowBlank: false,
        forceSelection: true,
        value: 1
	}],
	
	buttons:[{ 
            text:'Select Role',
            id: 'securityRolesButton',
            formBind: true}],
	
	initComponent: function() {
		this.superclass.initComponent.call(this, arguments);
	}	
});