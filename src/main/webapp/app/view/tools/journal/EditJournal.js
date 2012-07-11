Ext.define('Ssp.view.tools.journal.EditJournal',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editjournal',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.journal.EditJournalViewController',
    inject: {
        confidentialityLevelsStore: 'confidentialityLevelsStore',
        journalSourcesStore: 'journalSourcesStore',
        journalTracksStore: 'journalTracksStore',
        model: 'currentJournalEntry'
    },	
    initComponent: function() {
    	Ext.applyIf(this, {
        	title: ((this.model.get('id') == "") ? "Add Journal" : "Edit Journal"),
        	autoScroll: true,
        	defaults: {
            	labelWidth: 150,
            	padding: 5
            },
        	items: [{
			    	xtype: 'datefield',
			    	fieldLabel: 'Entry Date',
			    	itemId: 'entryDate',
			    	altFormats: 'm/d/Y|m-d-Y',
			    	invalidText: '{0} is not a valid date - it must be in the format: 06/02/2012 or 06-02-2012',
			        name: 'entryDate',
			        allowBlank:false
			     },{
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
			        allowBlank: true,
			        forceSelection: false,
			        anchor: '95%'
				},{
		        	xtype: 'label',
		        	text: 'Session Details (Critical Components)'
				},{
					xtype: 'tbspacer',
					flex: 1
				},{
		            tooltip: 'Add Journal Session Details',
		            text: 'Add/Edit Session Details',
		            xtype: 'button',
		            itemId: 'addSessionDetailsButton'
	    	    },
                { xtype: 'displayjournaldetails', autoScroll: true, anchor:'95% 50%' }
				,{
                    xtype: 'textareafield',
                    fieldLabel: 'Comment',
                    itemId: 'commentText',
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