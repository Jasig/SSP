Ext.define('Ssp.view.tools.earlyalert.EarlyAlertResponseDetails',{
	extend: 'Ext.form.Panel',
	alias : 'widget.earlyalertresponsedetails',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.earlyalert.EarlyAlertResponseDetailsViewController',
    inject: {
    	outcomesStore: 'earlyAlertOutcomesStore',
    	outreachesStore: 'earlyAlertOutreachesStore'
    },
    title: 'Early Alert Response Details',
	initComponent: function() {
		var me=this;
        Ext.applyIf(me, {
        	autoScroll: true,
            items: [{
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
			        anchor: '95%',
			        disabled: true
				},{
		            xtype: 'multiselect',
		            name: 'earlyAlertOutreachIds',
		            fieldLabel: 'Outreach',
		            store: me.outreachesStore,
		            displayField: 'name',
		            valueField: 'id',
		            allowBlank: false,
		            minSelections: 0,
		            anchor: '95%',
		            disabled: true
		        },{
                    xtype: 'displayfield',
                    fieldLabel: 'Comment',
                    anchor: '100%',
                    name: 'comment'
                },{
				   xtype:'earlyalertreferrals',
				   flex: 1,
				   disabled: true
				}],
            
            dockedItems: [{
       		               xtype: 'toolbar',
       		               items: [{
		       		                   text: 'Return to Early Alert List',
		       		                   xtype: 'button',
		       		                   itemId: 'finishButton'
		       		               }]
       		           }]
        });

        return me.callParent(arguments);
    }	
});