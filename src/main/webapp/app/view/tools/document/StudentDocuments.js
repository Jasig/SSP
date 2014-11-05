/*
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
Ext.define('Ssp.view.tools.document.StudentDocuments', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.studentdocuments',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.document.StudentDocumentToolViewController',
    inject: {
    	appEventsController: 'appEventsController',
    	columnRendererUtils: 'columnRendererUtils',
    	model: 'currentDocument',
        store: 'documentsStore'
    },
	title: 'Student Documents',	
	width: '100%',
	height: '100%',   
	autoScroll: true,
    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
			border: 0,
			store: this.store,
			dockedItems: [{
		        dock: 'top',
		        xtype: 'toolbar',
		        items: [{
		        			xtype: 'button', 
		        			itemId: 'addButton', 
		        			text:'Add', 
		        			action: 'add'
		        	   },{
		        			xtype: 'button', 
		        			itemId: 'downloadButton', 
		        			text:'Download', 
		        			action: 'download'
		        	   }]
			}],
			
        	columns: [
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'createdDate',
                    text: 'Date Entered'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'name',
                    text: 'Name'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'note',
                    text: 'Note',
                    flex: 1
                },{
	    	        xtype:'actioncolumn',
	    	        width:65,
	    	        header: 'Action',
	    	        items: [{
	    	            icon: Ssp.util.Constants.GRID_ITEM_EDIT_ICON_PATH,
	    	            tooltip: 'Edit Task',
	    	            handler: function(grid, rowIndex, colIndex) {
	    	            	var rec = grid.getStore().getAt(rowIndex);
	    	            	var panel = grid.up('panel');
	    	                panel.model.data=rec.data;
	    	                panel.appEventsController.getApplication().fireEvent('editDocument');
	    	            },
	    	            scope: this
	    	        },{
	    	            icon: Ssp.util.Constants.GRID_ITEM_DELETE_ICON_PATH,
	    	            tooltip: 'Delete Task',
	    	            handler: function(grid, rowIndex, colIndex) {
	    	            	var rec = grid.getStore().getAt(rowIndex);
	    	            	var panel = grid.up('panel');
	    	                panel.model.data=rec.data;
	    	            	panel.appEventsController.getApplication().fireEvent('deleteDocument');
	    	            },
	    	            scope: this
	    	        }]
                }],
                
            viewConfig: {

            }
        });

        me.callParent(arguments);
    }
});