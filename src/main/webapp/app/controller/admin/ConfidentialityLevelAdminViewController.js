Ext.define('Ssp.controller.admin.ConfidentialityLevelAdminViewController', {
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
		record.save({
			success: function(response) {
				record.commit();
				editor.grid.getStore().sync();
			}
		});
	},
	
	addRecord: function(button){
		var item = new Ssp.model.reference.ConfidentialityLevel();
       	var grid = button.up('grid');
       	item.set('name','default');
       	item.save({
       		success: function( response ) {
			   grid.getStore().insert(0, item );
	       	   grid.plugins[0].startEdit(0, 0);
		    }
       	});
	},
	
	deleteRecord: function(button){
	   var grid = button.up('grid');
	   var store = grid.getStore();
       var selection = grid.getView().getSelectionModel().getSelection()[0];
       if (selection) 
       {
    	   selection.destroy({
    		   success: function( response ) {
					store.remove( selection );
			   }
    	   });
       }else{
    	   Ext.Msg.alert('SSP Error', 'Please select an item to delete.'); 
       }
	}
});