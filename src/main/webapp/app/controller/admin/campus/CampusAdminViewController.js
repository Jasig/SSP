Ext.define('Ssp.controller.admin.campus.CampusAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	appEventsController: 'appEventsController',
    	campusEarlyAlertRouting: 'currentCampusEarlyAlertRouting',
    	campusesStore: 'campusesStore',
    	earlyAlertCoordinatorsStore: 'earlyAlertCoordinatorsStore',
    	earlyAlertReasonsStore: 'earlyAlertReasonsStore',
    	formUtils: 'formRendererUtils',
    	model: 'currentCampus',
    	peopleStore: 'peopleStore'
    },
    config: {
    	containerToLoadInto: 'adminforms',
    	campusEditorForm: 'editcampus',
    	campusEarlyAlertRoutingAdminForm: 'campusEarlyAlertRoutingsAdmin'
    },
    control: {
    	view: {
    		viewready: 'onViewReady'
    	},
    	
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
		me.campusesStore.load();
		me.earlyAlertCoordinatorsStore.load();
		me.earlyAlertReasonsStore.load();
		me.peopleStore.load();
		return this.callParent(arguments);
    },

    onViewReady: function(comp, obj){
    	var me=this;
    	me.appEventsController.assignEvent({eventName: 'editCampusEarlyAlertRoutings', callBackFunc: me.onEditCampusEarlyAlertRoutings, scope: me});
    },    
 
    destroy: function() {
    	var me=this;
    	
    	me.appEventsController.removeEvent({eventName: 'editCampusEarlyAlertRoutings', callBackFunc: me.onEditCampusEarlyAlertRoutings, scope: me});

    	return me.callParent( arguments );
    },

    onEditCampusEarlyAlertRoutings: function(){
		var me=this;
    	var model = new Ssp.model.reference.CampusEarlyAlertRouting();
		me.campusEarlyAlertRouting.data = model.data;
		me.displayCampusEarlyAlertRoutingAdmin();
    }, 
    
	onEditClick: function(button) {
		var grid, record;
		grid = button.up('grid');
		record = grid.getView().getSelectionModel().getSelection()[0];
        if (record) 
        {		
        	this.model.data=record.data;
        	this.displayCampusEditor();
        }else{
     	   Ext.Msg.alert('SSP Error', 'Please select an item to edit.'); 
        }
	},
	
	onAddClick: function(button){
		var model = new Ssp.model.reference.Campus();
		this.model.data = model.data;
		this.displayCampusEditor();
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

	displayCampusEarlyAlertRoutingAdmin: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getCampusEarlyAlertRoutingAdminForm(), true, {});
	},
 	
	displayCampusEditor: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getCampusEditorForm(), true, {});
	}
});