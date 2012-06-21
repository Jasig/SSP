Ext.define('Ssp.view.tools.journal.DisplayDetails', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.displayjournaldetails',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.journal.DisplayDetailsViewController',
    inject: {
        store: 'journalEntryDetailsStore'
    },
    layout:'fit',
	width: '100%',
	height: '100%',
    initComponent: function(){
    	Ext.apply(this,
    			{
    	    		store: this.store,
    	    		hideHeaders: true,
    	    		
    	    		features: [{
		    	        id: 'group',
		    	        ftype: 'grouping',
		    	        groupHeaderTpl: '{name}',
		    	        hideGroupedHeader: false,
		    	        enableGroupingMenu: false
		    	    }],
		    	    
		    	    columns: [{
		    	        text: '',
		    	        flex: 1,
		    	        sortable: false,
		    	        dataIndex: 'name'
		    	    }]
    			});
    	
    	return this.callParent(arguments);
    }
});