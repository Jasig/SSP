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
Ext.define('Ssp.view.admin.forms.shg.SelfHelpGuidesDisplayAdmin', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.selfhelpguidesdisplayadmin',
	title: 'Self Help Guides Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.shg.SelfHelpGuidesDisplayViewController',
    inject: {
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson',
        columnRendererUtils: 'columnRendererUtils'
    },
    height: '100%',
	width: '100%',

    initComponent: function(){
    	var me=this;
    	Ext.apply(me,
    			{
		          viewConfig: {
		        	
		          },
    		      autoScroll: true,
    		      selType: 'rowmodel',
    		      enableDragDrop: false,
    		      columns: [
    		                { header: 'Self Help Guide',  
    		                  dataIndex: 'name',
    		                  flex: 1 
    		                },
    		                { header: 'Description',  
        		                  dataIndex: 'description',
        		                  flex: 1 
        		             },
    		                { header: 'Active',  
      		                  dataIndex: 'active',
      		                  renderer: me.columnRendererUtils.renderFriendlyBoolean,
      		                  flex: 1 
      		                }
    		           ],
    		        
    		           dockedItems: [
     		       		{
     		       			xtype: 'pagingtoolbar',
     		       		    dock: 'bottom',
     		       		    displayInfo: true,
     		       		    pageSize: me.apiProperties.getPagingSize()
     		       		},
     		              {
     		               xtype: 'toolbar',
     		               items: [{
     		                   text: 'Add',
     		                   iconCls: 'icon-add',
     		                   xtype: 'button',
     		                   hidden: !me.authenticatedPerson.hasAccess('SELF_HELP_GUIDE_DELETE_BUTTON'),
     		                   action: 'add',
     		                   itemId: 'addButton'
     		               }, '-', {
     		                   text: 'Edit',
     		                   iconCls: 'icon-edit',
     		                   xtype: 'button',
     		                   hidden: !me.authenticatedPerson.hasAccess('SELF_HELP_GUIDE_EDIT_BUTTON'),
     		                   action: 'edit',
     		                   itemId: 'editButton'
     		               }, '-' ,{
     		                   text: 'Delete',
     		                   iconCls: 'icon-delete',
     		                   xtype: 'button',
     		                   hidden: !me.authenticatedPerson.hasAccess('SELF_HELP_GUIDE_ADD_BUTTON'),
     		                   action: 'delete',
     		                   itemId: 'deleteButton'
     		               }]
     		           },{
     		               xtype: 'toolbar',
     		               dock: 'top',
     		               items: [{
     	                      xtype: 'label',
     	                       text: 'Click on an existing Guide to Edit or Delete.  Click on "add" to create a new Guide.'
     	                     }]
     		           }]    	
    	});
    	
    	return me.callParent(arguments);
    }
});