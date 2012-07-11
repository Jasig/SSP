Ext.define('Ssp.controller.admin.campus.CampusAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	formUtils: 'formRendererUtils',
    	model: 'currentCampus',
    	store: 'campusesStore'
    },
    config: {
    	containerToLoadInto: 'adminforms',
    	formToDisplay: 'definecampus'
    },
    control: {  	
    	'editButton': {
			click: 'onEditClick'
		},
		
		'addButton': {
			click: 'onAddClick'
		},

		'deleteButton': {
			click: 'deleteConfirmation'
		} 	
    },
	init: function() {
		this.store.load();	
		return this.callParent(arguments);
    },
    
	onEditClick: function(button) {
		var grid, record;
		grid = button.up('grid');
		record = grid.getView().getSelectionModel().getSelection()[0];
        if (record) 
        {		
        	this.model.data=record.data;
        	this.displayEditor();
        }else{
     	   Ext.Msg.alert('SSP Error', 'Please select an item to edit.'); 
        }
	},
	
	onAddClick: function(button){
		var model = new Ssp.model.reference.Campus();
		this.model.data = model.data;
		this.displayEditor();
	},
	
    deleteConfirmation: function( button ) {
  	   var me=this;
        var grid = button.up('grid');
        var store = grid.getStore();
        var selection = grid.getView().getSelectionModel().getSelection()[0];
        var message;
        if ( selection.get('id') ) 
        {
     	   message = 'You are about to delete ' + selection.get('name') + '. Would you like to continue?';
     	      	   
            Ext.Msg.confirm({
    		     title:'Delete?',
    		     msg: message,
    		     buttons: Ext.Msg.YESNO,
    		     fn: me.deleteRecord,
    		     scope: me
    		   });
         }else{
      	   Ext.Msg.alert('SSP Error', 'Unable to delete item.'); 
         }
      },	
 	
 	deleteRecord: function( btnId ){
 		var me=this;
 		var grid=me.getView();
 		var store = grid.getStore();
 	    var selection = grid.getView().getSelectionModel().getSelection()[0];
      	var id = selection.get('id');
      	if (btnId=="yes")
      	{
      		me.apiProperties.makeRequest({
        		   url: store.getProxy().url+"/"+id,
        		   method: 'DELETE',
        		   successFunc: function(response,responseText){
        			   store.remove( store.getById( id ) );
        		   }
        	    });
        }
 	},
	
	displayEditor: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});