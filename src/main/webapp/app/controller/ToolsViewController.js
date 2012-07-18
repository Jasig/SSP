Ext.define('Ssp.controller.ToolsViewController', {
	extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	authenticatedPerson: 'authenticatedPerson',
    	personLite: 'personLite',
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
    	this.appEventsController.assignEvent({eventName: 'loadPerson', callBackFunc: this.onLoadPerson, scope: this});
 
    	if (this.personLite.get('id') != "")
    	{
    		this.loadPerson();
    	}
    },

    destroy: function() {
     	this.appEventsController.removeEvent({eventName: 'loadPerson', callBackFunc: this.onLoadPerson, scope: this});

        return this.callParent( arguments );
    },
    
    onLoadPerson: function(){
    	this.loadPerson();
    },
    
    loadPerson: function(){
		this.getView().getSelectionModel().select(0);
		this.loadTool('profile');  
    },
    
	onItemClick: function(grid,record,item,index){ 
		if (record.get('active'))
		{
			this.loadTool( record.get('toolType') );
		}
	},
	
	loadTool: function( toolType ) {
		var me=this;
		var comp;
		if ( me.authenticatedPerson.hasAccess(toolType.toUpperCase()+'_TOOL') )
		{
			comp = me.formUtils.loadDisplay('tools',toolType, true, {});
		}else{
			me.authenticatedPerson.showUnauthorizedAccessAlert();
		}
	}
});