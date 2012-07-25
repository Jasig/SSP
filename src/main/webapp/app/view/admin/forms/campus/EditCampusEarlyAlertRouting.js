Ext.define('Ssp.view.admin.forms.campus.EditCampusEarlyAlertRouting',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editcampusearlyalertrouting',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.campus.EditCampusEarlyAlertRoutingViewController',
    inject: {
    	earlyAlertReasonsStore: 'earlyAlertReasonsStore',
    	peopleSearchLiteStore: 'peopleSearchLiteStore',
    	sspConfig: 'sspConfig'
    },
	title: 'Edit Routing Group',
	initComponent: function() {
        var me=this;
		Ext.applyIf(me, {
		    fieldDefaults: {
		        msgTarget: 'side',
		        labelAlign: 'right',
		        labelWidth: 125
		    },
            items: [{
                    xtype: 'textfield',
                    fieldLabel: 'Group Name',
                    width: 500,
                    name: 'groupName',
                    allowBlank: false
                },{
                    xtype: 'textfield',
                    fieldLabel: 'Group Email',
                    name: 'groupEmail',
                    width: 500,
                    vtype:'email',
			        maxLength: 100,
			        allowBlank: false
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
			        width: 500
				},{
		            xtype: 'combo',
		            store: me.peopleSearchLiteStore,
		            displayField: 'displayFullName',
		            emptyText: 'Name or ' + me.sspConfig.get('studentIdAlias'),
		            valueField:'id',
		            typeAhead: false,
		            fieldLabel: 'Person',
		            hideTrigger:true,
		            queryParam: 'searchTerm',
		            allowBlank: true,
		            width: 500,

		            listConfig: {
		                loadingText: 'Searching...',
		                emptyText: 'No matching people found.',
		                getInnerTpl: function() {
		                    return '{firstName} {lastName}';
		                }
		            },
		            pageSize: 10
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