Ext.define('Ssp.controller.admin.AbstractReferenceAdminViewController', {
    extend: 'Ext.app.Controller',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties'
    }, 
    views: [ 'admin.forms.AbstractReferenceAdmin'],
       
	init: function() {
		
		this.control({
			'AbstractReferenceAdmin': {
				edit: this.editRecord,
				scope: this
			},
			
			'AbstractReferenceAdmin button[action="add"]': {
				click: this.addRecord,
				scope: this
			},

			'AbstractReferenceAdmin button[action="delete"]': {
				click: this.deleteRecord,
				scope: this
			}
		}); 

		this.callParent(arguments);
    },

	editRecord: function(editor, e, eOpts) {
		var record = editor.record;
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
			failure: this.handleError
		}, this);
	},
	
	addRecord: function(button){
		var item = new Ssp.model.reference.AbstractReference();
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
			failure: this.handleError
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
				failure: this.handleError
		   }, this);
       }else{
    	   Ext.Msg.alert('SSP Error', 'Please select an item to delete.'); 
       }
	},
	
	handleError: function(response) {
		var msg = 'Status Error: ' + response.status + ' - ' + response.statusText;
		Ext.Msg.alert('SSP Error', msg);								
	}
});