Ext.define('Ssp.controller.admin.AbstractReferenceAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	authenticatedPerson: 'authenticatedPerson'
    },  
    control: {
		view: {
			beforeedit: 'onBeforeEdit',
			edit: 'editRecord'
		},
		
		'addButton': {
			click: 'addRecord'
		},

		'deleteButton': {
			click: 'deleteConfirmation'
		},
		
		recordPager: '#recordPager'
    },
    
	init: function() {
		return this.callParent(arguments);
    },

    onBeforeEdit: function(){
		var me=this;
		var access = me.authenticatedPerson.hasAccess('ABSTRACT_REFERENCE_ADMIN_EDIT');
		if ( access == false)
		{
			me.authenticatedPerson.showUnauthorizedAccessAlert();
		}
    	return access;
    },
    
	editRecord: function(editor, e, eOpts) {
		var record = e.record;
		var id = record.get('id');
		var jsonData = record.data;
		Ext.Ajax.request({
			url: editor.grid.getStore().getProxy().url+"/"+id,
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
		var me=this;
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
		       	grid.plugins[0].editor.items.getAt(0).selectText();
		       	store.totalCount = store.totalCount+1;
		       	me.getRecordPager().onLoad();
			},
			failure: me.apiProperties.handleError
		}, me);
	},

    deleteConfirmation: function( button ) {
 	   var me=this;
       var grid = button.up('grid');
       var store = grid.getStore();
       var selection = grid.getView().getSelectionModel().getSelection()[0];
       var message;
       if ( selection.get('id') ) 
       {
    	   if ( !Ssp.util.Constants.isRestrictedAdminItemId( selection.get('id')  ) )
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
    		   Ext.Msg.alert('WARNING', 'This item is related to core SSP functionality. Please see a developer to delete this item.'); 
    	   }
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
       			   var r = Ext.decode(response.responseText);
       			   if (r.success==true)
       			   {
       				store.remove( store.getById( id ) );
       				store.totalCount = store.totalCount-1;
       				me.getRecordPager().onLoad();
       			    me.getRecordPager().doRefresh();
       			   }
       		   }
       	    });
       }
	}
});