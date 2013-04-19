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
Ext.define('Ssp.view.admin.forms.map.ElectiveAdmin', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.electiveadmin',
	title: 'Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.map.ElectiveViewController',
    inject: {
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson'
    },
	height: '100%',
	width: '100%',
	autoScroll: true,

    initComponent: function(){
    	var me=this;
    	var cellEditor = Ext.create('Ext.grid.plugin.RowEditing',
		                             { clicksToEdit: 2 });
    	Ext.apply(me,
    			{
    		      plugins:cellEditor,
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
    		                   hidden: !me.authenticatedPerson.hasAccess('ABSTRACT_REFERENCE_ADMIN_ADD_BUTTON'),
    		                   action: 'add',
    		                   itemId: 'addButton'
    		               }]
    		              }, {
    		               xtype: 'toolbar',
    		               dock: 'top',
    		               items: [{
    	                      xtype: 'label',
    	                       text: 'Double-click to edit an item.'
    	                     }]
    		               }
    		              ]    	
    	});
    	
    	me.callParent(arguments);
    }
});