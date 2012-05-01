Ext.define('Ssp.view.Search', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.search',
	title: 'Students',
	id: 'Search',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	columnRendererUtils: 'columnRendererUtils',
        studentsStore: 'studentsStore'
    },
	width: '100%',
	height: '100%',

    initComponent: function(){
    	Ext.apply(this,
    			   {
    	            collapsible: false,
    	            collapseDirection: 'left',
    	            store: this.studentsStore,
    		
		    	    columns: [
		    	              { header: "Photo", dataIndex: 'photoUrl', renderer: this.columnRendererUtils.renderPhotoIcon, flex: 50 },		        
		    	              { text: 'Name', xtype:'templatecolumn', tpl:'{firstName} {middleInitial} {lastName}', flex: 50}
		    	          ],
    	          
    	            dockedItems: [{
	    	              xtype: 'pagingtoolbar',
	    	              store: Ext.getStore('Students'),
	    	              dock: 'bottom',
	    	              displayInfo: true
	    	          }]
		    	    });
    	
    	this.callParent(arguments);
    }
});