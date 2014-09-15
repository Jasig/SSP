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
Ext.define('Ssp.view.admin.forms.campus.CampusAdmin', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.campusadmin',
	title: 'Campus Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.campus.CampusAdminViewController',
    inject: {
        apiProperties: 'apiProperties',
        appEventsController: 'appEventsController',
        authenticatedPerson: 'authenticatedPerson',
		columnRendererUtils: 'columnRendererUtils',
        model: 'currentCampus',
        store: 'campusesAllStore'
    },
    height: '100%',
	width: '100%',
	layout: 'fit',
    initComponent: function(){
        var me=this;
    	Ext.apply(me,
    			{   
    		      autoScroll: true,
    		      store: me.store,
				  cls: 'configgrid',
     		      columns: [{
		    	        xtype:'actioncolumn',
		    	        width: 100,
		    	        header: 'Assign Routings',
		    	        items: [{
			    	            icon: Ssp.util.Constants.GRID_ITEM_EDIT_ICON_PATH,
			    	            tooltip: 'Edit Campus Early Alert Routings',
			    	            handler: function(grid, rowIndex, colIndex) {
			    	            	var rec = grid.getStore().getAt(rowIndex);
			    	            	var panel = grid.up('panel');
			    	                panel.model.data=rec.data;
			    	            	panel.appEventsController.getApplication().fireEvent('editCampusEarlyAlertRoutings');
			    	            },
			    	            scope: me
			    	        }]
     		              },
						  {
	                        header: 'Active',
	                        required: true,
	                        dataIndex: 'objectStatus',
							defaultValue: true,
	                        renderer: me.columnRendererUtils.renderObjectStatus,
	                        flex: 0.10,
	                        field: {
	                            xtype: 'oscheckbox'
	                        }
	                    },
						  
						  { header: 'Name',  
    		                  dataIndex: 'name',
    		                  flex: 50 },
    		                { header: 'Description',
    		                  dataIndex: 'description', 
    		                  flex: 50
    		                }
    		           ],
    		        
    		           dockedItems: [
     		       		{
     		       			xtype: 'pagingtoolbar',
     		       		    dock: 'bottom',
     		       		    displayInfo: true,
     		       		    store: me.store,
     		       		    pageSize: me.apiProperties.getPagingSize()
     		       		},
     		              {
     		               xtype: 'toolbar',
     		               items: [{
     		                   text: 'Add',
     		                   iconCls: 'icon-add',
     		                   xtype: 'button',
     		                   hidden: !me.authenticatedPerson.hasAccess('CAMPUS_ADMIN_ADD_BUTTON'),
     		                   action: 'add',
     		                   itemId: 'addButton'
     		               }, '-', {
     		                   text: 'Edit',
     		                   iconCls: 'icon-edit',
     		                   xtype: 'button',
     		                   hidden: !me.authenticatedPerson.hasAccess('CAMPUS_ADMIN_EDIT_BUTTON'),
     		                   action: 'edit',
     		                   itemId: 'editButton'
     		               }, '-', {
     		                   text: 'Delete',
     		                   iconCls: 'icon-delete',
     		                   hidden: !me.authenticatedPerson.hasAccess('CAMPUS_ADMIN_DELETE_BUTTON'),
     		                   xtype: 'button',
     		                   action: 'delete',
     		                   itemId: 'deleteButton'
     		               }]
     		           }]  	
    	});

    	return me.callParent(arguments);
    }
});