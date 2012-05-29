Ext.define('Ssp.view.tools.earlyalert.EarlyAlertResponse',{
	extend: 'Ext.form.Panel',
	alias : 'widget.earlyalertresponse',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.earlyalert.EarlyAlertResponseViewController',
    inject: {
    	earlyAlert: 'currentEarlyAlert',
        outcomesStore: 'earlyAlertOutcomesStore',
        outreachesStore: 'earlyAlertOutreachesStore',
        referralsStore: 'earlyAlertReferralsStore'
    },
    initComponent: function() {
		Ext.applyIf(this, {
        	title: 'Early Alert Response',
        	defaults:{
        		labelWidth: 200
        	},
            items: [
                {
                	xtype: 'displayfield',
                	fieldLabel: 'Early Alert Response',
                	value: this.earlyAlert.get('name'),
                	anchor: '95%'
                },{
		            xtype: 'multiselect',
		            name: 'outreach',
		            fieldLabel: 'Outreach',
		            store: this.outreachesStore,
		            displayField: 'name',
		            valueField: 'id',
		            allowBlank: false,
		            minSelections: 1,
		            // maxSelections: 3,
		            anchor: '95%'
		        },{
			        xtype: 'combobox',
			        itemId: 'outcomesCombo',
			        name: 'outcomeId',
			        fieldLabel: 'Outcome',
			        emptyText: 'Select One',
			        store: this.outcomesStore,
			        valueField: 'id',
			        displayField: 'name',
			        mode: 'local',
			        typeAhead: true,
			        queryMode: 'local',
			        allowBlank: false,
			        forceSelection: true,
			        anchor: '95%'
				},{
		            xtype: 'multiselect',
		            name: 'referrals',
		            fieldLabel: 'Department and Service Referrals',
		            store: this.referralsStore,
		            displayField: 'name',
		            valueField: 'id',
		            allowBlank: true,
		            minSelections: 0,
		            // maxSelections: 3,
		            anchor: '95%'
		        },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Comment',
                    anchor: '95%',
                    name: 'comment'
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

        return this.callParent(arguments);
    }	
});