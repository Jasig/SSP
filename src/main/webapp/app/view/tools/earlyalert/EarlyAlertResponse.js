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
    	var me=this;
		Ext.applyIf(me, {
			autoScroll: true,
        	title: 'Early Alert Response',
        	defaults:{
        		labelWidth: 200
        	},
            items: [{
                	xtype: 'displayfield',
                	fieldLabel: 'Early Alert Response',
                	value: me.earlyAlert.get('courseTitle'),
                	anchor: '95%'
                },{
			        xtype: 'combobox',
			        itemId: 'outcomeCombo',
			        name: 'earlyAlertOutcomeId',
			        fieldLabel: 'Outcome',
			        emptyText: 'Select One',
			        store: me.outcomesStore,
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
		            store: me.outreachesStore,
		            displayField: 'name',
		            valueField: 'id',
		            allowBlank: false,
		            minSelections: 0,
		            anchor: '95%'
		        },{
                    xtype: 'textareafield',
                    fieldLabel: 'Comment',
                    anchor: '95%',
                    name: 'comment'
                },{
				   xtype:'earlyalertreferrals',
				   flex: 1
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