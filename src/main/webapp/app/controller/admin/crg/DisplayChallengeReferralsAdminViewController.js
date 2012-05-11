Ext.define('Ssp.controller.admin.crg.DisplayChallengeReferralsAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	model: 'currentChallenge',
    	store: 'challengesStore',
    	treeStore: 'treeStore',
    	treeUtils: 'treeRendererUtils'
    },
    config: {
    	containerToLoadInto: 'adminforms',
    	formToDisplay: 'editchallenge'
    },
    control: {
    	view: {
    		itemexpand: 'onItemExpand'
    	},
    	
        treeView: {
            selector: '.treeview',
            listeners: {
                beforedrop: 'onBeforeDrop'
            }
        }
    
    	/*
    	'editButton': {
			click: 'onEditClick'
		},
		
		'addButton': {
			click: 'onAddClick'
		},

		'deleteButton': {
			click: 'onDeleteClick'
		}*/   	
    },
    
	init: function() {
		var me = this;
		var successFunc = function(response,view){
	    	var r = Ext.decode(response.responseText);
	    	var records = r.rows;
	    	if (records.length > 0)
	    	{
	    		var nodes = me.treeUtils.createNodesFromJson(records);
		    	me.treeStore.setRootNode({
		    	        text: 'root',
		    	        expanded: true,
		    	        children: nodes
		    	});
	    	}else{
		    	me.treeStore.setRootNode({
	    	        text: 'root',
	    	        expanded: true,
	    	        children: []
	    	    });
	    	}		
		};
		
		this.apiProperties.makeRequest({
			url: this.apiProperties.createUrl('reference/challenge/'),
			method: 'GET',
			jsonData: '',
			successFunc: successFunc 
		});
    	
		return this.callParent(arguments);
    },
    
    onItemExpand: function(){
    	console.log('DisplayChallengeReferralsAdminViewController->onItemExpand');
    	// TODO: Retrieve related challenges and display as a subnode of the tree   	
    },

    onBeforeDrop: function(node, data, overModel, dropPosition, dropHandler, eOpts) {
    	dropHandler.wait=true;

    	// handle drop on a folder
        if (!overModel.isLeaf() && dropPosition == 'append')
        {
        	console.log('referralId' + data.records[0].get('id'));
        	console.log('challengeId' + overModel.data.id);
        	console.log("Make a call to add the referral to the challenge.");
        }

        // handle drop inside a folder
        if (dropPosition=='before' || dropPosition=='after')
        	console.log("You can't do that. Drop it on a folder");
        
        dropHandler.cancelDrop;
        
        return 1;
    }
    
    /*
	onEditClick: function(button) {
		var me = this;
		var record, id;
		var url = this.store.getProxy().url;
		record = this.getView().getSelectionModel().getSelection()[0];
		if (record) 
        {		
			id = record.get('id');
			
			this.apiProperties.makeRequest({
				url: url+id,
				method: 'GET',
				jsonData: '',
				successFunc: function(response, view){
					var r = Ext.decode(response.responseText);
					me.model.populateFromGenericObject(r);
			    	me.displayEditor();
				} 
			});
			
        }else{
     	   Ext.Msg.alert('SSP Error', 'Please select an item to edit.'); 
        }
	},
	
	onAddClick: function(button){
		var model = new Ssp.model.reference.Challenge({id:""})
		this.model.data = model.data;
		this.displayEditor();
	},
	
	onDeleteClick: function(button){
	   var treeStore, record, id, url;
	   url = this.store.getProxy().url;
	   treeStore = this.treeStore;
       record = this.getView().getSelectionModel().getSelection()[0];
       if (record) 
       {
    	   id=record.get('id');
		   this.apiProperties.makeRequest({
				url: url+id,
				method: 'DELETE',
				jsonData: '',
				successFunc: function(response, view){
					var r = Ext.decode(response.responseText);
					var node = treeStore.getNodeById(id);
					node.remove( true );
				} 
		   });

       }else{
    	   Ext.Msg.alert('SSP Error', 'Please select an item to delete.'); 
       }
	},
	
	displayEditor: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}*/
});