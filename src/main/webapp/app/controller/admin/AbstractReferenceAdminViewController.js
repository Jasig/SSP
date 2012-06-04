Ext.define('Ssp.controller.admin.AbstractReferenceAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties'
    },

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
		var grid = button.up('grid');
		var store = grid.getStore();
		var item = Ext.create( store.model.modelName, {}); // new Ssp.model.reference.AbstractReference();
		
		// default the name property
		item.set('name','default');
		//additional required columns defined in the Admin Tree Menus Store
		Ext.Array.each(grid.columns,function(col,index){
       		if (col.required==true)
       			item.set(col.dataIndex,'default');
       	});
		
		// If the object type has a sort order prop
		// then set the sort order to the next available
		// item in the database
		if (item.sortOrder != null)
		{
			item.set('sortOrder',store.getTotalCount()+1);
		}

		// Save the item
		Ext.Ajax.request({
			url: grid.getStore().getProxy().url,
			method: 'POST',
			headers: { 'Content-Type': 'application/json' },
			jsonData: item.data,
			success: function(response, view) {
				var r = Ext.decode(response.responseText);
				item.populateFromGenericObject(r);
				store.insert(0, item );
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