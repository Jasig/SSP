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
Ext.define('Ssp.controller.admin.map.ElectiveViewController', {
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
		
		recordPager: '#recordPager'
    },
    
	init: function() {
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
		
		// Test if the record is restricted content	
		if ( me.authenticatedPerson.isDeveloperRestrictedContent( item ) )
		{
			me.authenticatedPerson.showDeveloperRestrictedContentAlert();
			return false;
		}
		
		// default the name property
		item.set('name',' ');
		//additional required columns defined in the Admin Tree Menus Store
		Ext.Array.each(grid.columns,function(col,index){
       		if (col.required==true)
       			item.set(col.dataIndex,' ');
       	});
		
		item.set('sortOrder',store.getTotalCount()+1);

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
});