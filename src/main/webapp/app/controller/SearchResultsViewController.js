Ext.define('Ssp.controller.SearchResultsViewController', {
    extend: 'Ext.app.Controller', 
	
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        currentPerson: 'currentPerson'
    },
    
    views: [
        'SearchResults'
    ],
 
	init: function() {
 		this.control({
			'searchresults': {
				selectionchange: this.handleSelectionChange,
				viewready: this.handleViewReady,
				scope: this
			}
		});
 		
 		this.callParent(arguments);
    },
       
	handleSelectionChange: function(selModel,records,eOpts){ 
		// select the person
		this.currentPerson.data = records[0].data;
		this.application.fireEvent('afterLoadPerson');
	},

	handleViewReady: function(view, eobj){
		view.getSelectionModel().select(0);
	}
	
});