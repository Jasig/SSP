Ext.define('Ssp.view.admin.forms.campus.EditCampusEarlyAlertRouting',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editcampusearlyalertrouting',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.campus.EditCampusEarlyAlertRoutingViewController',
    inject: {
    	earlyAlertReasonsStore: 'earlyAlertReasonsStore',
    	peopleStore: 'peopleStore'
    },
	title: 'Edit Step',
	initComponent: function() {
        var me=this;
		Ext.applyIf(me, {
            items: [{
                    xtype: 'textfield',
                    fieldLabel: 'Group Name',
                    anchor: '100%',
                    name: 'groupName'
                },{
                    xtype: 'textfield',
                    fieldLabel: 'Group Email',
                    anchor: '100%',
                    name: 'groupEmail'
                },{
			        xtype: 'combobox',
			        name: 'earlyAlertReasonId',
			        itemId: 'earlyAlertReasonCombo',
			        fieldLabel: 'Early Alert Reason',
			        emptyText: 'Select One',
			        store: me.earlyAlertReasonsStore,
			        valueField: 'id',
			        displayField: 'name',
			        mode: 'local',
			        typeAhead: true,
			        queryMode: 'local',
			        allowBlank: false,
			        width: 300
				},{
			        xtype: 'combobox',
			        name: 'person',
			        itemId: 'personCombo',
			        fieldLabel: 'Person',
			        emptyText: 'Select One',
			        store: me.peopleStore,
			        valueField: 'id',
			        displayField: 'firstName',
			        mode: 'local',
			        typeAhead: true,
			        queryMode: 'local',
			        allowBlank: true,
			        width: 300
				}],
            
            dockedItems: [{
       		               xtype: 'toolbar',
       		               items: [{
		       		                   text: 'Save',
		       		                   xtype: 'button',
		       		                   action: 'save',
		       		                   itemId: 'saveButton'
		       		               }, '-', {
		       		                   text: 'Cancel',
		       		                   xtype: 'button',
		       		                   action: 'cancel',
		       		                   itemId: 'cancelButton'
		       		               }]
       		           }]
        });

        return me.callParent(arguments);
    }	
});