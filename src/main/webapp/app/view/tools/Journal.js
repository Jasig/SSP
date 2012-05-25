Ext.define('Ssp.view.tools.Journal', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.journal',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.journal.JournalToolViewController',
    inject: {
    	columnRendererUtils: 'columnRendererUtils',
        store: 'journalEntriesStore'
    },
	width: '100%',
	height: '100%',
	initComponent: function() {	
    	var sm = Ext.create('Ext.selection.CheckboxModel');
		
		Ext.apply(this, 
				{
		            autoScroll: true,
		            title: 'Journal',

	    		      columns: [
	    		                { header: 'Entered By',  
	    		                  dataIndex: 'createdBy',
	    		                  flex: 1,
	    		                  renderer: this.columnRendererUtils.renderConfidentialityLevelName
	    		                },
	      		                { header: 'Journal Source',
	      		                  dataIndex: 'journalSourceId', 
	      		                  flex: 1,
	    		                },
	      		                { header: 'Confidentiality',
	      		                  dataIndex: 'confidentialityLevel', 
	      		                  flex: 1,
	      		                  renderer: this.columnRendererUtils.renderConfidentialityLevelName
	    		                }
	    		           ],
				    
				    dockedItems: [{
				        dock: 'bottom',
				        xtype: 'toolbar',
				        items: [{
				            tooltip: 'Print the History for this student',
				            text: 'View History',
				            xtype: 'button',
				            itemId: 'viewHistoryButton'
				        }]
				    },{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{
				            tooltip: 'Add Journal Note',
				            text: 'Add',
				            xtype: 'button',
				            itemId: 'addButton'
				        },{
				            tooltip: 'Edit Journal Note',
				            text: 'Edit',
				            xtype: 'button',
				            itemId: 'editButton'
				        }]
				    }]
				});
		
		return this.callParent(arguments);
	}
});