Ext.define('Ssp.view.tools.earlyalert.EarlyAlertDetails',{
	extend: 'Ext.form.Panel',
	alias : 'widget.earlyalertdetails',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.earlyalert.EarlyAlertDetailsViewController',
    inject: {
    	selectedSuggestionsStore: 'earlyAlertDetailsSuggestionsStore'
    },
    title: 'Early Alert Details',
	initComponent: function() {
		var me=this;
        Ext.applyIf(me, {
        	autoScroll: true,
            items: [
                {
                    xtype: 'displayfield',
                    fieldLabel: 'Course Name',
                    anchor: '100%',
                    name: 'courseName'
                },{
                    xtype: 'displayfield',
                    fieldLabel: 'Status',
                    anchor: '100%',
                    name: 'status',
                    itemId: 'statusField'
                },{
                    xtype: 'displayfield',
                    fieldLabel: 'Closed Date',
                    anchor: '100%',
                    name: 'closedDate',
                    renderer: Ext.util.Format.dateRenderer('m/d/Y')
                },{
                    xtype: 'displayfield',
                    fieldLabel: 'Campus',
                    itemId: 'campusField',
                    anchor: '100%',
                    name: 'campus'
                },{
                    xtype: 'displayfield',
                    fieldLabel: 'Reason',
                    itemId: 'earlyAlertReasonField',
                    anchor: '100%',
                    name: 'earlyAlertReason'
                },{
		            xtype: 'multiselect',
		            name: 'earlyAlertSuggestionIds',
		            itemId: 'earlyAlertSuggestionsList',
		            fieldLabel: 'Suggestions',
		            store: me.selectedSuggestionsStore,
		            displayField: 'name',
		            anchor: '95%'
		        },{
                    xtype: 'displayfield',
                    fieldLabel: 'Email CC',
                    anchor: '100%',
                    name: 'emailCC'
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