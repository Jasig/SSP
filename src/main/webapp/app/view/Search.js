Ext.define('Ssp.view.Search', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.search',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.SearchViewController',
    inject: {
    	apiProperties: 'apiProperties',
    	columnRendererUtils: 'columnRendererUtils',
        store: 'studentsStore'
    },

    initComponent: function(){
    	Ext.apply(this,
    			   {
    				title: 'Students',
    	            collapsible: true,
    	            collapseDirection: 'left',
    	            store: this.store,
    	        	width: '100%',
    	        	height: '100%',
		    	    columns: [
		    	              { header: "Photo", dataIndex: 'photoUrl', renderer: this.columnRendererUtils.renderPhotoIcon, flex: 50 },		        
		    	              { text: 'Name', xtype:'templatecolumn', tpl:'{firstName} {middleInitial} {lastName}', flex: 50}
		    	          ],
    	          
		    	    dockedItems: [{
		       			xtype: 'pagingtoolbar',
		       		    store: this.store,
		       			dock: 'bottom',
		       		    displayInfo: true,
		       		    pageSize: this.apiProperties.getPagingSize()
		       		},{
		       			xtype: 'toolbar',
		       			dock: 'top',
		       		    items: [
		       		        {
		       		        	xtype: 'label',
		                        text: 'Search'
		       		        },{
		       		        	xtype: 'textfield'
		       		        },{
		       		        	xtype: 'button',
		       		        	itemId: 'searchButton',
		       		        	text: 'GO'
		       		        },{
		       		        	xtype: 'tbspacer',
		       		        	flex: 1
		       		        },{
		       		        	xtype: 'checkbox',
		       		        	boxLabel: 'All Students'
		       		        }
		       		    ]
		       		}]
		    	    });
    	
    	return this.callParent(arguments);
    }
});