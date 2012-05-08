Ext.define('Ssp.controller.ToolsViewController', {
	extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
        currentPerson: 'currentPerson',
        formUtils: 'formRendererUtils',
        appEventsController: 'appEventsController'
    },

    control: {
		view: {
			itemclick: 'onItemClick',
			viewready: 'onViewReady'
		}
		
	},
	
	init: function() {
		return this.callParent(arguments);
    }, 
    
    onViewReady: function(comp, obj){
 		this.appEventsController.getApplication().addListener('loadPerson', function(){
			// this.currentPerson.get('tools') );
			this.getView().getSelectionModel().select(0);
			this.loadTool('profile');
		},this);
    },
    
	onItemClick: function(grid,record,item,index){ 
		this.loadTool( record.get('toolType') );		
	},
	
	loadTool: function( toolType ) {	
		var comp = this.formUtils.loadDisplay('tools',toolType, true, {});
	}
});