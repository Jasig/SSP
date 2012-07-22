Ext.define('Ssp.controller.ToolsViewController', {
	extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
        appEventsController: 'appEventsController',
    	apiProperties: 'apiProperties',
    	authenticatedPerson: 'authenticatedPerson',
        formUtils: 'formRendererUtils',
    	personLite: 'personLite',
    	toolsStore: 'toolsStore'
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
    	var me=this;
    	me.appEventsController.assignEvent({eventName: 'loadPerson', callBackFunc: me.onLoadPerson, scope: me});
    	me.appEventsController.assignEvent({eventName: 'transitionStudent', callBackFunc: me.onTransitionStudent, scope: me});
 
    	if (me.personLite.get('id') != "")
    	{
    		me.loadPerson();
    	}
    },

    destroy: function() {
    	var me=this;
     	
    	me.appEventsController.removeEvent({eventName: 'loadPerson', callBackFunc: me.onLoadPerson, scope: me});
    	me.appEventsController.assignEvent({eventName: 'transitionStudent', callBackFunc: me.onTransitionStudent, scope: me});

        return me.callParent( arguments );
    },
    
    onLoadPerson: function(){
    	this.loadPerson();
    },
    
    onTransitionStudent: function(){
    	this.selectTool( 'journal' );
    	this.loadTool('journal');
    },
    
    loadPerson: function(){
    	this.selectTool( 'profile' );
    	this.loadTool('profile');  
    },
    
    selectTool: function( toolType ){
    	var tool = this.toolsStore.find( 'toolType', toolType )
		this.getView().getSelectionModel().select( tool );
    },
    
	onItemClick: function(grid,record,item,index){ 
		var me=this;
		if (record.get('active') && me.personLite.get('id') != "")
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