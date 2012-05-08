Ext.define('Ssp.controller.admin.ConfidentialityLevelAdminViewController', {
    extend: 'Deft.mvc.ViewController',     
    control: {
		view: {
			edit: 'editRecord'
		},
		
		'addButton': {
			click: 'addRecord'
		},

		'deleteButton': {
			click: 'deleteRecord'
		}    	
    },       
	init: function() {
		return this.callParent(arguments);
    },

	editRecord: function(editor, e, eOpts) {
		var record = e.record;
		var id = record.get('id');
		var jsonData = record.data;
		Ext.Ajax.request({
			url: editor.grid.getStore().getProxy().url+id,
			method: 'PUT',
			headers: { 'Content-Type': 'application/json' },
			jsonData: jsonData,
			success: function(response, view) {
				var r = Ext.decode(response.responseText);
				record.commit();
				editor.grid.getStore().sync();
			},
			failure: this.apiProperties.handleError
		}, this);
	},
	
	addRecord: function(button){
		var item = new Ssp.model.reference.ConfidentialityLevel();
       	var grid = button.up('grid');
       	item.set('name','default');

		Ext.Ajax.request({
			url: grid.getStore().getProxy().url,
			method: 'POST',
			headers: { 'Content-Type': 'application/json' },
			jsonData: {"id":"","name":"default","description":""},
			success: function(response, view) {
				var r = Ext.decode(response.responseText);
				item.populateFromGenericObject(r);
				grid.getStore().insert(0, item );
		       	grid.plugins[0].startEdit(0, 0);
			},
			failure: this.apiProperties.handleError
		}, this);
	},
	
	deleteRecord: function(button){
	   var grid = button.up('grid');
	   var store = grid.getStore();
       var selection = grid.getView().getSelectionModel().getSelection()[0];
       var id="";
       if (selection) 
       {
    	   id = selection.get('id');
	   	   Ext.Ajax.request({
				url: grid.getStore().getProxy().url+id,
				method: 'DELETE',
				headers: { 'Content-Type': 'application/json' },
				success: function(response, view) {
					var r = Ext.decode(response.responseText);
					store.remove( selection );
				},
				failure: this.apiProperties.handleError
		   }, this);
       }else{
    	   Ext.Msg.alert('SSP Error', 'Please select an item to delete.'); 
       }
	}
});