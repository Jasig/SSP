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
			autoScroll: true,
        	title: 'Early Alert Response',
        	defaults:{
        		labelWidth: 200
        	},
            items: [
                {
                	xtype: 'displayfield',
                	fieldLabel: 'Early Alert Response',
                	value: this.earlyAlert.get('courseTitle'),
                	anchor: '95%'
                },{
			        xtype: 'combobox',
			        itemId: 'outcomeCombo',
			        name: 'earlyAlertOutcomeId',
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
					xtype: 'textfield',
					itemId: 'otherOutcomeDescriptionText',
					name: 'earlyAlertOutcomeOtherDescription',
					fieldLabel: 'Other Outcome Description',
					anchor: '95%'
				},{
		            xtype: 'multiselect',
		            name: 'earlyAlertOutreachIds',
		            fieldLabel: 'Outreach',
		            store: this.outreachesStore,
		            displayField: 'name',
		            valueField: 'id',
		            allowBlank: false,
		            minSelections: 0,
		            anchor: '95%'
		        },{
		            xtype: 'multiselect',
		            name: 'earlyAlertReferralIds',
		            fieldLabel: 'Department and Service Referrals',
		            store: this.referralsStore,
		            displayField: 'name',
		            valueField: 'id',
		            allowBlank: true,
		            minSelections: 0,
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