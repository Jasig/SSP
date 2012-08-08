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
                	fieldLabel: 'Early Alert Course',
                	value: me.earlyAlert.get('courseName') +  ' ' + me.earlyAlert.get('courseTitle')
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
		            itemId: 'outreachList',
		            fieldLabel: 'Outreach'+Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY,
		            store: me.outreachesStore,
		            displayField: 'name',
		            msgTarget: 'side',
		            valueField: 'id',
		            invalidCls: 'multiselect-invalid',
		            minSelections: 1,
		            allowBlank: false,
		            anchor: '95%'
		        },{
                    xtype: 'textareafield',
                    fieldLabel: 'Comment',
                    anchor: '95%',
                    name: 'comment',
                    allowBlank: false
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