Ext.define('Ssp.controller.admin.campus.EarlyAlertRoutingsAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	store: 'campusEarlyAlertRoutingsStore',
    	formUtils: 'formRendererUtils',
    	model: 'currentCampus',
    },
    config: {
    	containerToLoadInto: 'campusearlyalertroutingsadmin',
    	formToDisplay: 'editcampusearlyalertrouting',
    	baseUrl: null
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
		var me=this;
		var campusId = me.model.get('id');
		me.baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('campusEarlyAlertRouting') );
		me.baseUrl = me.baseUrl.replace( '{campusId}', campusId );
		if (campusId != "")
		{
			me.store.load( me.baseUrl );
		}
		return this.callParent(arguments);
    },

	onEditClick: function(button) {
		var me=this;
		var grid, record;
		grid = button.up('grid');
		record = grid.getView().getSelectionModel().getSelection()[0];
        if (record) 
        {		
        	me.model.data=record.data;
        	me.displayEditor();
        }else{
     	   Ext.Msg.alert('SSP Error', 'Please select an item to edit.'); 
        }
	},
	
	onAddClick: function(button){
		var me=this;
		var model = new Ssp.model.reference.CampusEarlyAlertRouting();
		me.model.data = model.data;
		me.displayEditor();
	},    
 
    deleteConfirmation: function( button ) {
   	   var me=this;
         var grid = button.up('grid');
         var store = grid.getStore();
         var selection = grid.getView().getSelectionModel().getSelection()[0];
         var message;
         if(selection != null)
         {
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
         }else{
        	Ext.Msg.alert('SSP Error', 'Select an item to delete.'); 
         }
       },	
  	
  	deleteRecord: function( btnId ){
  		var me=this;
  		var url = me.baseUrl;
  		var grid=me.getView();
  		var store = grid.getStore();
  	    var selection = grid.getView().getSelectionModel().getSelection()[0];
       	var id = selection.get('id');
       	if (btnId=="yes")
       	{
       		me.apiProperties.makeRequest({
         		   url: url+"/"+id,
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