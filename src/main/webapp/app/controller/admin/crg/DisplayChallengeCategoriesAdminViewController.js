Ext.define('Ssp.controller.admin.crg.DisplayChallengeCategoriesAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	challengeCategoriesStore: 'challengeCategoriesStore',
    	challengeCategoriesTreeStore: 'challengeCategoriesTreeStore',
    	formUtils: 'formRendererUtils',
    	currentCategory: 'currentChallengeCategory'
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
    	this.challengeCategoriesStore.load({
			scope: this,
		    callback: function(records, operation, success) {
		    	console.log(records);
		    	if (records.length > 0)
		    	{
		    		var categoryNodes = this.createNodes(records);
			    	this.challengeCategoriesTreeStore.setRootNode({
			    	        text: 'Categories',
			    	        expanded: true,
			    	        children: categoryNodes
			    	});
		    	}else{
			    	this.challengeCategoriesTreeStore.setRootNode({
		    	        text: 'Create a Category',
		    	        expanded: true,
		    	        children: []
		    	    });
		    	}
		    }
	    });
    	
		return this.callParent(arguments);
    },
    
    createNodes: function(records){
    	var nodes = [];
    	Ext.each(records, function(name, index) {
    	    nodes.push({
    	    	        text: records[index].get('name'),
    	    	        id: records[index].get('id'),
    	    	        leaf: false
    	    	      });
    	});
    	return nodes;
    },

	onEditClick: function(button) {
		var me = this;
		var record, id, currentCategory, formUtils;
		var url = this.challengeCategoriesStore.getProxy().url;
		var currentCategory = this.currentCategory;
		record = this.getView().getSelectionModel().getSelection()[0];
		if (record) 
        {		
			id = record.get('id');
			
			this.apiProperties.makeRequest({
				url: url+id,
				method: 'GET',
				jsonData: '',
				successFunc: function(response, view){
					var r, comp;
					r = Ext.decode(response.responseText);
					currentCategory.populateFromGenericObject(r);
			    	comp = me.formUtils.loadDisplay('challengeadmin','editchallengecategory', true, {});		
				} 
			});
			
        }else{
     	   Ext.Msg.alert('SSP Error', 'Please select an item to edit.'); 
        }
	},
	
	onAddClick: function(button){
		var comp;
		// TODO- Use a proper object here
		// rather than cleaning the object
		// There is a bug where the object is
		// not refreshed when it's injected into the
		// editing form.
		this.currentCategory.set('id','');
		this.currentCategory.set('name','');
		this.currentCategory.set('description','');
		comp = this.formUtils.loadDisplay('challengeadmin','editchallengecategory', true, {});
	},
	
	onDeleteClick: function(button){
	   console.log('DisplayChallengeCategoriesAdminViewController->onDeleteClick');
	   var treeStore, record, id, url;
	   url = this.challengeCategoriesStore.getProxy().url;
	   treeStore = this.challengeCategoriesTreeStore;
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
					console.log(node);
					node.remove( true );
				} 
		   });

       }else{
    	   Ext.Msg.alert('SSP Error', 'Please select an item to delete.'); 
       }
	}
});