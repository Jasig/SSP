/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
Ext.define('Ssp.controller.admin.AbstractReferenceAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	authenticatedPerson: 'authenticatedPerson', 
    	formRendererUtils: 'formRendererUtils'
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
		}
		
    },
    
	init: function() {
		var me=this;
		return this.callParent(arguments);
    },

    onBeforeEdit: function( editor, e, eOpts ){
		var me=this;
		var access = me.authenticatedPerson.hasAccess('ABSTRACT_REFERENCE_ADMIN_EDIT');
		// Test if the record is restricted content 
		if ( me.authenticatedPerson.isDeveloperRestrictedContent( e.record ) )
		{
			me.authenticatedPerson.showDeveloperRestrictedContentAlert();
			return false;
		}		

		if ( access == false)
		{
			me.authenticatedPerson.showUnauthorizedAccessAlert();
		}
		me.editor = editor;
		var columns = e.grid.columns;
		Ext.each(columns, function(column,editor) {
		if(column.rowEditable === false)
		{
				me.editor.editor.form.findField(column.dataIndex).disable();
		}
		});		
		//Associative Object handling
		var rowEditor = editor.editor;
		var rowFields = rowEditor.items.items;			
		
		Ext.each(rowFields, function(item) {
			if(item.store != undefined && item.store != null) {
				item.store.clearFilter(true);
			}
			
			if(item.associativeField != undefined) {	
				me.formRendererUtils.applyAssociativeStoreFilter(item.store, e.record.get(item.associativeField));
			}
		});
    	return access;
    },
    
	editRecord: function(editor, e, eOpts) {
		var record = e.record;
		var id = record.get('id');
		var jsonData = record.data;
		var store = editor.grid.getStore();
		var persistMethod= record.data.createdDate != null ? 'PUT' : 'POST';
		
		Ext.each(editor.editor.items.items, function(item) {
			if(item.store != undefined && item.store != null) {
				item.store.clearFilter(true);
			}	
			
		});

		Ext.Ajax.request({
			url: editor.grid.getStore().getProxy().url+"/"+id,
			method: persistMethod,
			headers: { 'Content-Type': 'application/json' },
			jsonData: jsonData,
			success: function(response, view) {
				if(persistMethod == "PUT") {
					var r = Ext.decode(response.responseText);
					record.persisted = true;
					
					if (record.dirty) {
						record.commit();
						editor.grid.getSelectionModel().select(record);
						var h = editor.grid.getView().getSelectedNodes()[0];
						Ext.get(h).highlight(Ssp.util.Constants.SSP_EDITED_ROW_HIGHLIGHT_COLOR,
							Ssp.util.Constants.SSP_EDITED_ROW_HIGHLIGHT_OPTIONS);
						
					}
				} else {
					var r = Ext.decode(response.responseText);
					record.populateFromGenericObject(r);
					store.totalCount = store.totalCount+1;
					
					if (record.dirty) {
						record.commit();
						editor.grid.getSelectionModel().select(0);
						
						var h1 = editor.grid.getView().getSelectedNodes()[0];
						Ext.get(h1).highlight(Ssp.util.Constants.SSP_EDITED_ROW_HIGHLIGHT_COLOR,
							Ssp.util.Constants.SSP_EDITED_ROW_HIGHLIGHT_OPTIONS);
						
					}
				}
			},
			failure: this.apiProperties.handleError
		}, this);
	},
	
	addRecord: function(button){
		var me=this;
		var grid = button.up('grid');
		var store = grid.getStore();
		var item = Ext.create( store.model.modelName, {}); // new Ssp.model.reference.AbstractReference();
		
		// Test if the record is restricted content	
		if ( me.authenticatedPerson.isDeveloperRestrictedContent( item ) )
		{
			me.authenticatedPerson.showDeveloperRestrictedContentAlert();
			return false;
		}
		
		//set default values
		Ext.Array.each(grid.columns,function(col,index){
			if(col.defaultValue != null) {
   				item.set(col.dataIndex, col.defaultValue);
   			} else if(col.dataIndex == "sortOrder") {
   				item.set('sortOrder',store.getTotalCount()+1);
			} else {
				if (col.required==true) {       			
	       			item.set(col.dataIndex,'default');
	       		}
   			}
       	});

		store.insert(0, item );
		grid.plugins[0].startEdit(0, 0);
		var editorItems = grid.plugins[0].editor.items;
		if(editorItems.getAt(0).xtype == "textfield") {
			editorItems.getAt(0).selectText();
		}
		
		//initialize combo boxes to first value (defaults
		//to a blank entry otherwise, causes validation issues...)
		Ext.Array.each(editorItems.items, function(item) {
			if(item.xtype == "combo") {
				var valueField = (item.valueField == undefined? "id": item.valueField);
				var store = item.store;
				if(store.data.length == 0)
				{
					store.load();
				}
				var firstComboElement = store.data.items[0];
				item.setValue(firstComboElement.data[valueField]);
			}
		}); 
	},

    deleteConfirmation: function( button ) {
 	   var me=this;
       var grid = button.up('grid');
       var store = grid.getStore();
       var selection = grid.getView().getSelectionModel().getSelection()[0];
       var message;

       if (selection != null && selection.get('id') ) 
       {
    	   // Test if the record is restricted content 
           if ( me.authenticatedPerson.isDeveloperRestrictedContent( selection ) )
    	   {
    			me.authenticatedPerson.showDeveloperRestrictedContentAlert();
    			return false;
    	   }    	   
    	   
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
     	   Ext.Msg.alert('SSP Error', 'Please select an item to delete.'); 
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
       			   if (Boolean(r.success)==true)
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