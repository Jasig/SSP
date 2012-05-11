Ext.define('Ssp.controller.admin.journal.DisplayStepsAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	store: 'journalStepsStore',
    	formUtils: 'formRendererUtils',
    	model: 'currentJournalStep'
    },
    config: {
    	containerToLoadInto: 'adminforms',
    	formToDisplay: 'editjournalstep'
    },
    control: {  	
    	'editButton': {
			click: 'onEditClick'
		},
		
		'addButton': {
			click: 'onAddClick'
		},

		'deleteButton': {
			click: 'onDeleteClick'
		}    	
    },       
    
	init: function() {
		this.getView().reconfigure(this.store);
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
		var model = new Ssp.model.reference.JournalStep();
		this.model.data = model.data;
		this.displayEditor();
	},
	
	onDeleteClick: function(button){
	   var grid, store, selection, id, url;
	   grid = button.up('grid');
	   store = grid.getStore();
       record = grid.getView().getSelectionModel().getSelection()[0];
       url = grid.getStore().getProxy();
       if (record) 
       {
    	   id=record.get('id');
		   this.apiProperties.makeRequest({
				url: url+id,
				method: 'DELETE',
				jsonData: '',
				success: function(response, view) {
					var r = Ext.decode(response.responseText);
					store.remove( selection );
				} 
		   });
       }else{
    	   Ext.Msg.alert('SSP Error', 'Please select an item to delete.'); 
       }
	},
	
	displayEditor: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});