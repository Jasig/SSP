Ext.define('Ssp.view.tools.journal.EditJournal',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editjournal',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.journal.EditJournalViewController',
    inject: {
        confidentialityLevelsStore: 'confidentialityLevelsStore',
        journalSourcesStore: 'journalSourcesStore',
        journalTracksStore: 'journalTracksStore'
    },	
    initComponent: function() {
		Ext.applyIf(this, {
        	title: 'Edit Journal',
            defaults: {
            	labelWidth: 150
            },
        	items: [
                {
			        xtype: 'combobox',
			        itemId: 'confidentialityLevelCombo',
			        name: 'confidentialityLevelId',
			        fieldLabel: 'Confidentiality Level',
			        emptyText: 'Select One',
			        store: this.confidentialityLevelsStore,
			        valueField: 'id',
			        displayField: 'name',
			        mode: 'local',
			        typeAhead: true,
			        queryMode: 'local',
			        allowBlank: false,
			        forceSelection: true,
			        anchor: '95%'
				},{
			        xtype: 'combobox',
			        itemId: 'journalSourceCombo',
			        name: 'journalSourceId',
			        fieldLabel: 'Source',
			        emptyText: 'Select One',
			        store: this.journalSourcesStore,
			        valueField: 'id',
			        displayField: 'name',
			        mode: 'local',
			        typeAhead: true,
			        queryMode: 'local',
			        allowBlank: false,
			        forceSelection: true,
			        anchor: '95%'
				},{
			        xtype: 'combobox',
			        itemId: 'journalTrackCombo',
			        name: 'journalTrackId',
			        fieldLabel: 'Journal Track',
			        emptyText: 'Select One',
			        store: this.journalTracksStore,
			        valueField: 'id',
			        displayField: 'name',
			        mode: 'local',
			        typeAhead: true,
			        queryMode: 'local',
			        allowBlank: false,
			        forceSelection: true,
			        anchor: '95%'
				},{
                	xtype: 'label',
                	text: 'Select the details for this session'
                },
                { xtype: 'journaltracktree', fieldLabel: 'testing a label' }
                ,{
                    xtype: 'textareafield',
                    fieldLabel: 'Comment',
                    anchor: '100%',
                    name: 'comment',
                    anchor: '95%'
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