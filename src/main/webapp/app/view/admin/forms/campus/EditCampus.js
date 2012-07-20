Ext.define('Ssp.view.admin.forms.campus.EditCampus',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editcampus',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.campus.EditCampusViewController',
    inject: {
    	store: 'earlyAlertCoordinatorsStore'
    },
	title: 'Edit Campus',
	initComponent: function() {
		var me=this;
        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: 'Name',
                    anchor: '100%',
                    name: 'name'
                },{
                    xtype: 'textareafield',
                    fieldLabel: 'Description',
                    anchor: '100%',
                    name: 'description'
                },{
			        xtype: 'combobox',
			        name: 'earlyAlertCoordinatorId',
			        itemId: 'earlyAlertCoordinatorCombo',
			        fieldLabel: 'Early Alert Coordinator',
			        emptyText: 'Select One',
			        store: me.store,
			        valueField: 'id',
			        displayField: 'displayFullName',
			        mode: 'local',
			        typeAhead: true,
			        queryMode: 'local',
			        allowBlank: false,
			        width: 300
				}]
        });

        return me.callParent(arguments);
    }	
});