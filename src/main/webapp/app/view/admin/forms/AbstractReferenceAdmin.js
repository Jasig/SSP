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
Ext.define('Ssp.view.admin.forms.AbstractReferenceAdmin', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.abstractreferenceadmin',
	title: 'Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.AbstractReferenceAdminViewController',
    inject: {
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson'
    },
	height: '100%',
	width: '100%',
	autoScroll: true,

    initComponent: function(){
    	var me=this;

    	var cellEditor = Ext.create('Ext.grid.plugin.RowEditing', { 
    							clicksToEdit: 2,
    							controller: me.getController(),
    							listeners: {
	    							cancelEdit: function(rowEditor, item){
	    								item.store.load(item.store.storeLoadOptions);
	    					        }
    							}
    					});
    	     	
    	var addVisible = true;
    	var deleteVisible = true;
    	if(me.interfaceOptions !== undefined) { 
    		addVisible = me.interfaceOptions.addButtonVisible;
    		deleteVisible = me.interfaceOptions.deleteButtonVisible;    		
    	}

    	console.log(me);
    	Ext.apply(me,
    			{
    		      plugins: cellEditor,
     		      viewConfig: {
    	        	  itemId: 'gridView',
    	        	  plugins: {
    	                  ptype: 'gridviewdragdrop',
    	                  dropGroup: 'gridtogrid',
    	                  dragGroup: 'gridtogrid',
    			          enableDrop: me.interfaceOptions !== undefined? me.interfaceOptions.dragAndDropReorder : false,
    			          enableDrag: me.interfaceOptions !== undefined? me.interfaceOptions.dragAndDropReorder : false,
    	        	  }, 
    	        	  listeners:{
    	        		    drop:function(node, data, mouseOverItem, dropPosition, eOpts){
    	        		    	var store = me.store; 
    	        		    	var items = store.data.items;
    	        		    	var droppedItem = data.records[0].data;    	        		    	  	        		    	
    	        		    	var pageNumber = 0;   
    	        		    	var lastItem = items[items.length-1];
    	        		    	
    	        		    	if(items[items.length-1].data.id == droppedItem.id) {
    	        		    		lastItem = items[items.length-2];
    	        		    	}
    	        		    	
    	        		    	while((lastItem.index - store.params.limit * pageNumber) > store.params.limit) {
    	        		    		pageNumber++;
    	        		    	}
    	        		    	
    	        		    	var previousSortOrder = store.params.limit * pageNumber;

    	        	  			var jsonData = [];
    	        	  			
    	        	  			Ext.each(items, function(item) {
    	        	  				if(item.data.id == droppedItem.id
    	        	  					|| item.data.sortOrder == previousSortOrder 
    	        	  					||	(item.data.sortOrder - previousSortOrder) > 1) {

    	        	  					item.data.sortOrder = previousSortOrder + 1; 
    	        	  					jsonData.push(Ext.encode(item.data));  
    	        	  				}
    	        	  				previousSortOrder = item.data.sortOrder;
    	        	  			});
    	        	  			    	        	  			
    	        		    	Ext.Ajax.request({
    	        					url: store.getProxy().url +"/",
    	        					method: 'PUT',
    	        					headers: { 'Content-Type': 'application/json' },
    	        					jsonData: jsonData,
    	        					success: function(response, view) {
    	        						var r = Ext.decode(response.responseText);
    	        						console.log(me);
    	        						me.getController().getRecordPager().onLoad();
    	        						me.getController().getRecordPager().doRefresh();
    	        					},
    	        					failure: me.apiProperties.handleError
    	        				}, this);
    	        				
    	        		    }
    	        		}    	        	  
    		      },  
    		      selType: 'rowmodel',
    		      columns: [
    		                { header: 'Name',  
    		                  dataIndex: 'name',
    		                  field: {
    		                      xtype: 'textfield'
    		                  },
    		                  flex: 50 
    		                 },
    		                { header: 'Description',
    		                  dataIndex: 'description', 
    		                  flex: 50,
    		                  field: {
    		                      xtype: 'textfield'
    		                  }
    		                }
    		           ],
    		        
    		           dockedItems: [
    		       		{
    		       			xtype: 'pagingtoolbar',
    		       		    dock: 'bottom',
    		       		    itemId: 'recordPager',
    		       		    displayInfo: true,
    		       		    pageSize: me.apiProperties.getPagingSize()
    		       		},
    		              {
    		               xtype: 'toolbar',
    		               dock: 'top',
    		               items: [{
    		            	   text: 'Add',
    		            	   iconCls: 'icon-add',
    		            	   xtype: 'button',
    		            	   hidden: !me.authenticatedPerson.hasAccess('ABSTRACT_REFERENCE_ADMIN_ADD_BUTTON') || !addVisible,
    		            	   action: 'add',
    		            	   itemId: 'addButton'
    		               }, '-', {
    		            	   text: 'Delete',
    		            	   iconCls: 'icon-delete',
    		            	   xtype: 'button',
    		            	   hidden: !me.authenticatedPerson.hasAccess('ABSTRACT_REFERENCE_ADMIN_DELETE_BUTTON') || !deleteVisible,
    		            	   action: 'delete',
    		            	   itemId: 'deleteButton'
    		               }]
    		           },{
    		               xtype: 'toolbar',
    		               dock: 'top',
    		               items: [{
    	                      xtype: 'label',
    	                       text: 'Double-click to edit an item.'
    	                     }]
    		           }]    	
    	});
    	
    	me.callParent(arguments);
    }
});