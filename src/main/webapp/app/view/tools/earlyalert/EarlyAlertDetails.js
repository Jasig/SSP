Ext.define('Ssp.view.tools.earlyalert.EarlyAlertDetails',{
	extend: 'Ext.form.Panel',
	alias : 'widget.earlyalertdetails',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.earlyalert.EarlyAlertDetailsViewController',
    inject: {
    	campusesStore: 'campusesStore',
    	reasonsStore: 'earlyAlertReasonsStore',
    	suggestionsStore: 'earlyAlertSuggestionsStore'
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
                    fieldLabel: 'Closed Date',
                    anchor: '100%',
                    name: 'closedDate',
                    renderer: Ext.util.Format.dateRenderer('m/d/Y')
                },{
			        xtype: 'combobox',
			        itemId: 'campusCombo',
			        name: 'campusId',
			        fieldLabel: 'Campus',
			        emptyText: 'Select One',
			        store: me.campusesStore,
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
			        xtype: 'combobox',
			        itemId: 'earlyAlertReasonCombo',
			        name: 'earlyAlertReasonId',
			        fieldLabel: 'Early Alert Reason',
			        emptyText: 'Select One',
			        store: me.reasonsStore,
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
		            name: 'earlyAlertSuggestionIds',
		            fieldLabel: 'Suggestions',
		            store: me.suggestionsStore,
		            displayField: 'name',
		            valueField: 'id',
		            allowBlank: false,
		            minSelections: 0,
		            anchor: '95%',
		            disabled: true
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