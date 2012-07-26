Ext.define('Ssp.view.tools.earlyalert.EarlyAlertResponseDetails',{
	extend: 'Ext.form.Panel',
	alias : 'widget.earlyalertresponsedetails',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.earlyalert.EarlyAlertResponseDetailsViewController',
    inject: {
    	selectedOutreachesStore: 'earlyAlertResponseDetailsOutreachesStore',
    	selectedReferralsStore: 'earlyAlertResponseDetailsReferralsStore'
    },
    title: 'Early Alert Response Details',
	initComponent: function() {
		var me=this;
        Ext.applyIf(me, {
        	autoScroll: true,
            items: [{
	                xtype: 'displayfield',
	                fieldLabel: 'Created By',
	                anchor: '100%',
	                name: 'createdByPersonName',
	                itemId: 'createdByField'
	            },{
	                xtype: 'displayfield',
	                fieldLabel: 'Created Date',
	                anchor: '100%',
	                name: 'createdDate',
	                itemId: 'createdDateField',
	                renderer: Ext.util.Format.dateRenderer('m/d/Y h:m A')
	            },{
	                xtype: 'displayfield',
	                fieldLabel: 'Outcome',
	                anchor: '100%',
	                itemId: 'outcomeField',
	                name: 'outcome'
	            },{
		            xtype: 'multiselect',
		            name: 'earlyAlertOutreachIds',
		            itemId: 'earlyAlertOutreachList',
		            fieldLabel: 'Suggestions',
		            store: me.selectedOutreachesStore,
		            displayField: 'name',
		            anchor: '95%'
		        },{
		            xtype: 'multiselect',
		            name: 'earlyAlertReferralIds',
		            itemId: 'earlyAlertReferralsList',
		            fieldLabel: 'Referrals',
		            store: me.selectedReferralsStore,
		            displayField: 'name',
		            anchor: '95%'
		        },{
                    xtype: 'displayfield',
                    fieldLabel: 'Comment',
                    anchor: '100%',
                    name: 'comment'
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