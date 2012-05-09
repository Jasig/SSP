Ext.define('Ssp.controller.admin.crg.DisplayChallengesAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	challengesStore: 'challengesStore',
    	formUtils: 'formRendererUtils',
    	currentChallenge: 'currentChallenge'
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
		this.getView().reconfigure(this.challengesStore);
		this.challengesStore.load();
		
		return this.callParent(arguments);
    },

	onEditClick: function(button) {
		var grid, comp, record;
		grid = button.up('grid');
		record = grid.getView().getSelectionModel().getSelection()[0];
        if (record) 
        {		
        	this.currentChallenge.data=record.data;
        	comp = this.formUtils.loadDisplay('challengeadmin','editchallenge', true, {});
        }else{
     	   Ext.Msg.alert('SSP Error', 'Please select an item to edit.'); 
        }
	},
	
	onAddClick: function(button){
		var comp;
		this.currentChallenge = new Ssp.model.reference.Challenge();
		comp = this.formUtils.loadDisplay('challengeadmin','editchallenge', true, {});
	},
	
	onDeleteClick: function(button){
	   console.log('DisplayChallengesAdminViewController->onDeleteClick');
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